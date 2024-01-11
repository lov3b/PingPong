package com.billenius.VisibleObjects;

import com.billenius.GetDimension;

import java.awt.*;
import java.util.Arrays;

public class Score implements VisibleObject {
    private static final Color COLOR = Color.BLUE.brighter().brighter().brighter();
    private final int[] playerScores = new int[]{0, 0};
    private final int winningScore;
    private final WinAction winAction;
    private final GetDimension gameDimension;
    private boolean spawned = false;

    public Score(GetDimension gameDimension, int winningScore, WinAction winAction) {
        this.gameDimension = gameDimension;
        this.winAction = winAction;
        this.winningScore = winningScore;
    }

    public void incrementPlayerScore(int player) {
        playerScores[player]++;
        int playerWon = playerHasWon();
        if (playerWon != -1)
            winAction.run(playerWon);
    }

    public int playerHasWon() {
        for (int i = 0; i < playerScores.length; i++) {
            if (playerScores[i] == winningScore) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public void spawn() {
        spawned = true;
        Arrays.fill(playerScores, 0);
    }

    @Override
    public boolean isSpawned() {
        return spawned;
    }

    @Override
    public void deSpawn() {
        spawned = false;
    }

    private static final Color SHADOW_COLOR = Color.GRAY;
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final int SHADOW_OFFSET = 3;

    public void paint(Graphics2D g2) {
        if (!isSpawned())
            return;
        /* We don't want to have the minus anywhere else than the in the middle */
        String measure = "0 - 0";
        String score = String.format("%d - %d", playerScores[0], playerScores[1]);

        g2.setColor(SHADOW_COLOR);
        Font font = g2.getFont().deriveFont(Font.BOLD, 42f);
        g2.setFont(font);
        FontMetrics metrics = g2.getFontMetrics();
        int width = metrics.stringWidth(measure);
        int height = metrics.getHeight();
        int x = gameDimension.get().width / 2 - width / 2;
        int y = height - 3;

        // Draw shadow
        g2.setColor(SHADOW_COLOR);
        g2.drawString(score, x + SHADOW_OFFSET, y + SHADOW_OFFSET);

        // Create gradient from white to yellow from left to right across the text
        GradientPaint gradient = new GradientPaint(
                x, y, Color.YELLOW.brighter().brighter(), // start point for gradient (left edge of text)
                x + width, y, Color.RED.brighter().brighter()); // end point for gradient (right edge of text)

        // Apply gradient and draw text
        g2.setPaint(gradient);
        g2.drawString(score, x, y);
    }
}
