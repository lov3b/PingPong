package com.billenius.VisibleObjects;

import com.billenius.GetDimension;
import com.billenius.Utils;
import com.billenius.Vector2D.PaddleDirection;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerPaddle implements VisibleObject {

    protected static final int height = 80, width = 20;
    private static final int MOVE_PER_TICK = 5;
    private final int[] upDown;
    private final Color color;
    private boolean movingDown = false, movingUp = false;
    private final GetDimension gameDimension;
    private final Side side;
    private int y = Integer.MIN_VALUE;

    public PlayerPaddle(Side side, GetDimension gameDimension) {
        upDown = getUpDown(side);
        this.gameDimension = gameDimension;
        this.side = side;
        color = (side == Side.LEFT ? Color.YELLOW : Color.RED).brighter().brighter();
    }


    protected int getX() {
        if (side == Side.LEFT)
            return 0;
        return gameDimension.get().width - width;
    }

    protected int getY() {
        return y;
    }

    private static int[] getUpDown(Side side) {
        return side == Side.LEFT ? new int[]{KeyEvent.VK_W, KeyEvent.VK_S} : new int[]{KeyEvent.VK_UP, KeyEvent.VK_DOWN};
    }


    public void paint(Graphics2D g2) {
        if (!isSpawned())
            return;
        move();
        g2.setColor(color);
        g2.fillRect(getX(), getY(), width, height);
    }

    protected int getHeight() {
        return height;
    }

    protected int getWidth() {
        return width;
    }

    private void move() {
        // We can't move both up and down. Don't move if we are down
        if (movingUp && movingDown)
            return;

        boolean canMoveDown = getY() + getHeight() < gameDimension.get().getHeight();
        boolean canMoveUp = getY() > 0;
        if (movingUp && canMoveUp)
            y -= MOVE_PER_TICK;
        else if (movingDown && canMoveDown)
            y += MOVE_PER_TICK;
    }

    public void startMoving(int keyCode) {
        if (!Utils.arrayContains(upDown, keyCode))
            return;
        if (keyCode == upDown[0])
            movingUp = true;
        else movingDown = true;
    }

    public void stopMoving(int keyCode) {
        if (!Utils.arrayContains(upDown, keyCode))
            return;
        movingUp = false;
        movingDown = false;
    }

    protected PaddleDirection getPaddleDirection() {
        if (movingUp != movingDown)
            return PaddleDirection.NOT_MOVING;
        else if (movingUp)
            return PaddleDirection.UP;
        return PaddleDirection.DOWN;
    }

    @Override
    public void spawn() {
        y = gameDimension.get().height / 2 - height / 2;
    }

    @Override
    public boolean isSpawned() {
        return getY() != Integer.MIN_VALUE;
    }

    @Override
    public void deSpawn() {
        y = Integer.MIN_VALUE;
    }

    public Side getSide() {
        return side;
    }
}
