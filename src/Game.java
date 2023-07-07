import java.util.LinkedList;
import java.util.Random;

public class Game {
    Player[] players;
    Map map;

    public Game(int playNum, Map map) {
        players = new Player[playNum];
        this.map = map;
        initGame();
    }

    public void initGame(){
    }
}
