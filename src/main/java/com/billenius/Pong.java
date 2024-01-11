package com.billenius;

import com.billenius.VisibleObjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Pong extends PongBackground implements KeyListener, ComponentListener {
    private final PlayerPaddle[] playerPaddles = new PlayerPaddle[2];
    private final VisibleObject[] visibleObjects;

    public Pong(JFrame root) {
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        addKeyListener(this);
        addComponentListener(this);
        playerPaddles[0] = new PlayerPaddle(Side.LEFT, this::getSize);
        playerPaddles[1] = new PlayerPaddle(Side.RIGHT, this::getSize);
        Score score = new Score(this::getSize, 5, (playerWon) -> {
            String[] choices = new String[]{"Yes", "No"};
            deSpawnAll();
            int choice = JOptionPane.showOptionDialog(this,
                    String.format("Player %d won. Do you want to play again?", playerWon + 1),
                    "Game Finished",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choices,
                    choices[1]
            );
            if (choice == 0)
                spawnAll();
            else {
                root.dispatchEvent(new WindowEvent(root, WindowEvent.WINDOW_CLOSING));
            }
        });
        Ball ball = new Ball(this::getSize, playerPaddles, score);
        visibleObjects = new VisibleObject[]{playerPaddles[0], playerPaddles[1], ball, score};
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (VisibleObject visibleObject : visibleObjects)
            visibleObject.paint(g2);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (PlayerPaddle playerPaddle : playerPaddles)
            playerPaddle.startMoving(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (PlayerPaddle playerPaddle : playerPaddles)
            playerPaddle.stopMoving(e.getKeyCode());
    }

    @Override
    public void componentResized(ComponentEvent e) {
        spawnAll();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    private void spawnAll() {
        for (VisibleObject visibleObject : visibleObjects)
            visibleObject.spawn();
    }

    private void deSpawnAll() {
        for (VisibleObject visibleObject : visibleObjects)
            visibleObject.deSpawn();
    }

}
