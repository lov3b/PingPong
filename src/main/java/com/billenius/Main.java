package com.billenius;

import javax.swing.*;
import java.awt.*;


public class Main extends JFrame {
    public Main() throws HeadlessException {
        super("Pling Pong!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new Pong(this));
        pack();
        setVisible(true);

        Timer timer = new Timer(10, e -> {
            repaint();
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {

                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                     IllegalAccessException ignored) {
            }
            new Main();
        });
    }
}
