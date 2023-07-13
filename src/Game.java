import java.util.Random;

public class Game {
    private Random random = new Random();
    // Min. 1 Player guaranteed by Server
    enum State {
        INIT,

    }
    Player[] players;
    int curPlayer;
    Map map;

    public Game(int playNum, Map map) {
        players = new Player[playNum];
        curPlayer = random.nextInt(playNum);
        this.map = map;
    }

    public void init(){
        map = new Map();
        map.init();
    }
}
