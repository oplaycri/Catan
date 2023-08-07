package game;

import map.BuildingContainer;
import map.Map;

import java.util.Random;

public class Game {
    private Random random = new Random();
    // Min. 1 game.Player guaranteed by connection.Server
    private enum State {
        INIT,
        INITIAL_PLACEMENT,
        WON
    }
    private State state;
    Player[] players;
    int curPlayer;
    Map map;

    public Game(int playNum, Map map) {
        players = new Player[playNum];
        curPlayer = random.nextInt(playNum);
        this.map = map;
        state = State.INIT;
    }
    /**
     * Initializes the Game and Map
     * */
    public boolean init(){
        if(state != State.INIT){
            return false;
        }
        map = new Map();
        map.init();
        state = State.INITIAL_PLACEMENT;
        return true;
    }

    public boolean initialPlacement(Player player, BuildingContainer.Building b, BuildingContainer container){
        if(state != State.INITIAL_PLACEMENT){
            return false;
        }
        if(players[curPlayer] != player){
            return false;
        }
        if()
    }
    public boolean endTurn(Player player){
        return true;
    }
    public boolean makeOffer(Player player, Offer offer){
        return true;
    }
    public boolean engageOffer(Player player, Offer offer, boolean accept){
        return true;
    }
    public boolean place(Player player, BuildingContainer.Building b, BuildingContainer buildingContainer){
        return true;
    }
    public boolean tradeOverseas(Player player, Tile.Resource sold, Tile.Resource bought){
        return true;
    }
    public boolean placeRobber(Player player, Tile tile){
        return true;
    }
    public boolean buyCard(Player player){
        return true;
    }
    public boolean useCard(Player player){
        return true;
    }
    public boolean steal(Player player, Player target){
        return true;
    }
    private boolean checkWin(Player player){
        return true;
    }
}
