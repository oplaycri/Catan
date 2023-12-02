package game;


import map.*;

import static game.Game.State.*;
import static map.Building.*;

import java.util.Random;

/**
 * Simulates the Catan-game logic.
 * <p>
 * Every action a player can take is represented by a method, requesting
 * and, if granted, executing that action. The tools, through which checks and actions can be made lay
 * mostly in {@link map.Map}.
 */
public class Game {
	private Random random = new Random();
	private State state;
	private Player[] players;
	private int curPlayer;
	private int initCounter = 0;
	private Map map;

	public Game(int playNum) {
		players = new Player[playNum];
		curPlayer = random.nextInt(playNum);
		state = INIT;
		init();
	}

	/**
	 * Initializes the Game and Map. Must be called First
	 *
	 * @return true on success, false if in wrong state.
	 */
	public boolean init() {
		if (state != State.INIT) {
			return false;
		}
		map = new Map();
		map.init();
		state = INITIAL_PLACEMENT_1;
		return true;
	}

	/**
	 * Request that the building be placed inside the container and be given ownership to the player.
	 * <p>
	 * Determines the state of the game and acts accordingly.
	 * There will be no modification of the game on rejection.
	 *
	 * @param player the requesting Player
	 * @param b the building to be placed
	 * @param container the container
	 * @return true if allowed, false otherwise. Does nothing on false
	 */
	public boolean place(Player player, Building b, BuildingContainer container) {
		if (state == INITIAL_PLACEMENT_1) {
			return initialPlacement(player, b, container, 1);
		}
		if (state == INITIAL_PLACEMENT_2) {
			return initialPlacement(player, b, container, 2);
		}
		return false; //TODO Implement other state cases
	}

	/**
	 * Round 2 of initial placement.
	 *
	 * @param round expect 1 or 2
	 * @see Game#place(Player, Building, BuildingContainer)
	 */
	private boolean initialPlacement(Player player, Building b, BuildingContainer container, int round) {
		if (players[curPlayer] != player || (round != 1 && round != 2)) {
			return false;
		}
		// Check for expected building
		Structure s = player.getStructure(b);
		if(round == 1) {
			switch (b) {
				case SETTLEMENT -> {
					if (s.getLeft() != s.INITIAL_LEFT - (round - 1)) {
						return false;
					}
				}
				case ROAD -> {
					Structure settlement = player.getStructure(SETTLEMENT);
					if (s.getLeft() != s.INITIAL_LEFT - (round - 1)
							|| settlement.getLeft() != settlement.INITIAL_LEFT - round) {
						return false;
					}
				}
				default -> {
					return false;
				}
			}
		}
		// Check position
		if (!map.checkValidPlacement(b, container, player, true)) {
			return false;
		}
		//TODO Place the damn building

		// Used for determining end. 1 full turn => switch. Do not change the current player.
		initCounter++;
		if (initCounter == players.length) {
			initCounter = 0;
			state = (state == INITIAL_PLACEMENT_1) ? INITIAL_PLACEMENT_2 : DICE_ROLL;
			return true;
		}
		// Clockwise or counterclockwise
		if(round == 1) {
			curPlayer = (curPlayer + 1) % players.length;
		} else {
			curPlayer = (curPlayer - 1) % players.length;
		}
		return true;

	}

	/**
	 * Round 2 of initial placement.
	 *
	 * @see Game#place(Player, Building, BuildingContainer)
	 */
	private boolean initialPlacement2(Player player, Building b, BuildingContainer container) {
		if (players[curPlayer] != player) {
			return false;
		}
		switch (b) {
			case SETTLEMENT -> {
				if (player.getStructure(b).getLeft() != 5) {
					return false;
				}
			}
			case ROAD -> {
				if (player.getStructure(b).getLeft() != 15 || player.getStructure(b).getLeft() != 4) {
					return false;
				}

			}
		}
		if (!map.checkValidPlacement(b, container, player, true)) {
			return false;
		}
		initCounter++;
		if (initCounter == players.length) {
			state = State.DICE_ROLL;
			return true;
		}
		curPlayer = (curPlayer - 1 + players.length) % players.length;
		return true;
	}

	/**
	 * Performs a (double-)dice roll for the game.
	 * <p>
	 * State transition between turns is handled here and should be called after {@link Game#endTurn(Player)}
	 * or after {@link Game#initialPlacement2(Player, Building, BuildingContainer)}.
	 * Depending on the roll, the next state will be a normal turn or "the robber's turn".
	 *
	 * @return True if in correct state.
	 */
	private boolean rollDice() {
		if (state != DICE_ROLL) {
			return false;
		}
		// Roll the dice
		int res1 = random.nextInt(6) + 1;
		int res2 = random.nextInt(6) + 1;
		int res = res1 + res2;
		map.updateResources(res);
		if (res == 7) {
			state = MOVE_ROBBER;
			return true;
		}
		state = TURN;
		return true;
	}

	public boolean endTurn(Player player) {
		return true;
	}

	public boolean makeOffer(Player player, Offer offer) {
		return true;
	}

	public boolean engageOffer(Player player, Offer offer, boolean accept) {
		return true;
	}

	public boolean tradeOverseas(Player player, Terrain sold, Terrain bought) {
		return true;
	}

	public boolean moveRobber(Player player, Tile tile) {
		if (state != MOVE_ROBBER || players[curPlayer] != player) {
			return false;
		}
		if (tile == map.findRobber()) {
			return false;
		}
		map.moveRobber(tile);
		for (Player p : players) {
			if (p != player && map.canSteal(p)) {
				state = STEAL;
				return true;
			}
		}
		state = TURN;
		return true;
	}

	public boolean buyCard(Player player) {
		return true;
	}

	public boolean useCard(Player player) {
		return true;
	}

	public boolean steal(Player player, Player target) {
		if (state != STEAL || player != players[curPlayer] || player == target || !map.canSteal(target)) {
			return false;
		}
		// Decrement
		Resource res = target.getRandomResource();
		res.setAmount(res.getAmount() - 1);
		// Increment
		res = player.getResource(res.name);
		res.setAmount(res.getAmount() + 1);

		return true;
	}

	public Map getMap() {
		return map;
	}

	private boolean checkWin(Player player) {
		return true;
	}

	// Min. 1 game.Player guaranteed by connection.Server
	enum State {
		INIT,
		INITIAL_PLACEMENT_1,
		INITIAL_PLACEMENT_2,
		DICE_ROLL,
		GIVE_ROBBER,
		MOVE_ROBBER,
		STEAL,
		TURN, WON
	}

}

