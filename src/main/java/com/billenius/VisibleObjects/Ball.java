package com.billenius.VisibleObjects;

import com.billenius.GetDimension;
import com.billenius.Vector2D.BumpType;
import com.billenius.Vector2D.Vector2D;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

import static java.util.Objects.nonNull;

public class Ball implements VisibleObject {
    private static final int RADIUS = 12;
    private static final Color COLOR = Color.WHITE;
    Vector2D vector = null;
    /** NULL before the ball is spawned */
    Point point = null;
    GetDimension gameDimension;
    PlayerPaddle[] playerPaddles;
    Score score;

    public Ball(GetDimension gameDimension, PlayerPaddle[] playerPaddles, Score score) {
        this.gameDimension = gameDimension;
        this.playerPaddles = playerPaddles;
        this.score = score;
    }

    @Override
    public void spawn() {
        Dimension dimension = gameDimension.get();
        point = new Point(dimension.width / 2, dimension.height / 2);
        vector = new Vector2D();
    }

    @Override
    public boolean isSpawned() {
        return point != null;
    }

    @Override
    public void deSpawn() {
        point = null;
        vector = null;
    }

    @Override
    public void paint(Graphics2D g2) {
        if (!isSpawned())
            return;
        Side scored = sideScored();
        if (nonNull(scored)){
            score.incrementPlayerScore(scored.toInt());
            spawn();
            return;
        }
        move();
        vector.applyVector(point);
        Stroke stroke = g2.getStroke();

        g2.setStroke(new BasicStroke(0));
        g2.setColor(COLOR);
        g2.fillOval(point.x, point.y, RADIUS, RADIUS);
        g2.setStroke(stroke);
    }

    private @Nullable Side sideScored() {
        Dimension dimension = gameDimension.get();
        if (point.x >= dimension.width)
            return Side.LEFT;
        if (point.x <= 0)
            return Side.RIGHT;
        return null;
    }

    public void move() {
        Wall wall = collidedWithWall();
        PlayerPaddle paddle = collidedWithPaddle(playerPaddles);

        if (nonNull(wall) && nonNull(paddle)) vector.bump(BumpType.BOTH, null);
        else if (nonNull(wall) && vector.verticalDirection() == wall) {
            vector.bump(BumpType.WALL, null);
        } else if (nonNull(paddle) && vector.horizontalDirection() == paddle.getSide()) {
            vector.bump(BumpType.PADDLE, paddle.getPaddleDirection());
        } else vector.applyVector(point);
    }


    @Nullable PlayerPaddle collidedWithPaddle(PlayerPaddle[] playerPaddles) {
        for (PlayerPaddle playerPaddle : playerPaddles) {
            int paddleX = playerPaddle.getX();
            int paddleY = playerPaddle.getY();
            int paddleWidth = playerPaddle.getWidth();
            int paddleHeight = playerPaddle.getHeight();

            // Check if the ball is within the x and y range of the paddle
            if (point.x + RADIUS >= paddleX && point.x - RADIUS <= paddleX + paddleWidth / 2 &&
                    point.y + RADIUS >= paddleY && point.y - RADIUS <= paddleY + paddleHeight) {
                return playerPaddle;
            }
        }
        return null;
    }


    protected int getHeight() {
        return RADIUS;
    }

    public @Nullable Wall collidedWithWall() {
        Dimension dimension = gameDimension.get();
        if (point.y <= 0)
            return Wall.UP;
        if (point.y + getHeight() >= dimension.height)
            return Wall.DOWN;
        return null;
    }
}

