package game;

import map.BuildingContainer;
import map.Map;
import map.Tile;

import java.util.Random;

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
        state = State.INIT;
    }

    /**
     * Initializes the Game and Map
     */
    public boolean init() {
        if (state != State.INIT) {
            return false;
        }
        map = new Map();
        map.init();
        state = State.INITIAL_PLACEMENT_1;
        return true;
    }

    /**
     * Round 1 of initial placement
     */
    private boolean initialPlacement1(Player player, BuildingContainer.Building b, BuildingContainer container) {
        if (players[curPlayer] != player) {
            return false;
        }
        switch (b) {
            case Settlement -> {
                if (player.getSettlements_left() != 5) {
                    return false;
                }
            }
            case Road -> {
                if (player.getRoads_left() != 15 || player.getSettlements_left() != 4) {
                    return false;
                }

            }
        }
        if (!map.checkValidPlacement(b, container, player, true)) {
            return false;
        }
        initCounter++;
        if (initCounter == players.length) {
            initCounter = 0;
            state = State.INITIAL_PLACEMENT_2;
            return true;
        }
        curPlayer = (curPlayer + 1) % players.length;
        return true;

    }

    /**
     * Round 2 of initial placement
     */
    private boolean initialPlacement2(Player player, BuildingContainer.Building b, BuildingContainer container) {
        if (players[curPlayer] != player) {
            return false;
        }
        switch (b) {
            case Settlement -> {
                if (player.getSettlements_left() != 5) {
                    return false;
                }
            }
            case Road -> {
                if (player.getRoads_left() != 15 || player.getSettlements_left() != 4) {
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

    public boolean rollDice() {
        if (state != State.DICE_ROLL) {
            return false;
        }
        int res1 = random.nextInt(6) + 1;
        int res2 = random.nextInt(6) + 1;
        int res = res1 + res2;
        map.updateResources(res);
        if (res == 7) {
            state = State.MOVE_ROBBER;
            return true;
        }
        state = State.TURN;
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

    public boolean place(Player player, BuildingContainer.Building b, BuildingContainer container) {
        if (state == State.INITIAL_PLACEMENT_1) {
            return initialPlacement1(player, b, container);
        }
        if (state == State.INITIAL_PLACEMENT_2) {
            return initialPlacement2(player, b, container);
        }
        return true;
    }

    public boolean tradeOverseas(Player player, Tile.Resource sold, Tile.Resource bought) {
        return true;
    }

    public boolean moveRobber(Player player, Tile tile) {
        if (state != State.MOVE_ROBBER || players[curPlayer] != player) {
            return false;
        }
        if (tile == map.findRobber()) {
            return false;
        }
        for (Player p : players) {
            if (p != player && map.canSteal(p)) {
                state = State.STEAL;
                return true;
            }
        }
        state = State.TURN;
        return true;
    }

    public boolean buyCard(Player player) {
        return true;
    }

    public boolean useCard(Player player) {
        return true;
    }

    public boolean steal(Player player, Player target) {
        if (player==target || !map.canSteal(target)) {
            return false;
        }

        return true;
    }

    private boolean checkWin(Player player) {
        return true;
    }

    // Min. 1 game.Player guaranteed by connection.Server
    private enum State {
        INIT,
        INITIAL_PLACEMENT_1,
        INITIAL_PLACEMENT_2,
        DICE_ROLL,
        MOVE_ROBBER,
        STEAL,
        TURN, WON
    }
}
