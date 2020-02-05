package game;

import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Super Mario Bros v0.1");
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //this.setUndecorated(true);

        this.setSize(1000,704);

        GamePanel gp = new GamePanel();
        this.add(gp);
        this.addKeyListener(gp);

        this.setVisible(true);

    }

}
