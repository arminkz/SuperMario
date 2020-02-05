package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameMenu extends JFrame {

    public GameMenu(){
        setTitle("Super Mario Bros");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(400,300);
        setLayout(new BorderLayout());

        JPanel buttonsC = new JPanel();
        //buttonsC.setBackground(Color.black);

        buttonsC.setLayout(new BoxLayout(buttonsC,BoxLayout.Y_AXIS));
        JButton startButton = new JButton("Start Game");

        JLabel difficulty = new JLabel("Difficulty :");

        JPanel radioPane1 = new JPanel();
        JRadioButton easy = new JRadioButton("Easy");
        JRadioButton medium = new JRadioButton("Medium");
        JRadioButton hard = new JRadioButton("Hard");
        radioPane1.add(easy);
        radioPane1.add(medium);
        radioPane1.add(hard);

        JLabel players = new JLabel("Players :");

        JPanel radioPane2 = new JPanel();
        JRadioButton single = new JRadioButton("Singleplayer");
        JRadioButton multi = new JRadioButton("Multiplayer");
        radioPane2.add(single);
        radioPane2.add(multi);

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficulty.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener((ActionEvent e) -> {
            new GameWindow();
            dispose();
        });

        buttonsC.add(Box.createRigidArea(new Dimension(0,20)));
        buttonsC.add(startButton);
        buttonsC.add(Box.createRigidArea(new Dimension(0,20)));
        buttonsC.add(difficulty);
        buttonsC.add(radioPane1);
        buttonsC.add(Box.createRigidArea(new Dimension(0,5)));
        buttonsC.add(radioPane2);

        getContentPane().add(buttonsC);
        setVisible(true);
    }

    public static void main(String[] args) {
        new GameMenu();
    }

}
