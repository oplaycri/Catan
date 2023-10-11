package graphics;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GamePanel gamePanel;
    public GameFrame(){
        setTitle("Catan");
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(31, 49, 28));
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        gamePanel = new GamePanel();
        add(gamePanel);
        setVisible(true);
    }
}
