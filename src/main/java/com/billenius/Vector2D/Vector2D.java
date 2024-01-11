package com.billenius.Vector2D;

import com.billenius.VisibleObjects.Side;
import com.billenius.VisibleObjects.Wall;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Random;

public class Vector2D {
    private static final Random random = new Random();
    private static final double HYPOTENUSE = 2;
    double x, y;

    /**
     * Creates a vector and randomizes the angle
     */
    public Vector2D() {
        double angle = Math.toRadians(random.nextFloat() % 60 - 30);
        int sign = random.nextBoolean() ? 1 : -1;
        x = Math.cos(angle) * HYPOTENUSE * sign;
        y = Math.sin(angle) * HYPOTENUSE * sign;
    }


    public void bump(@NotNull BumpType bumpType, @Nullable PaddleDirection paddleDirection) {
        if (bumpType == BumpType.BOTH) {
            x = -x;
            y = -y;
        } else if (bumpType == BumpType.WALL) {
            y = -y;
        } else if (bumpType == BumpType.PADDLE) {
            x = -x;
            double angle = 0;
            if (paddleDirection != PaddleDirection.NOT_MOVING) {
                double degrees = random.nextDouble() % 5 + 15;
                degrees *= paddleDirection == PaddleDirection.UP ? -1 : 1;
                angle = Math.toRadians(degrees);

                // Adjusting y direction based on the angle
                y = Math.sin(angle) * HYPOTENUSE;
            }
        }
    }

    /**
     * This will modify the Point
     */
    public void applyVector(Point point) {
        point.x += (int) Math.round(x);
        point.y += (int) Math.round(y);
    }

    public Side horizontalDirection() {
        return x > 0 ? Side.RIGHT : Side.LEFT;
    }

    public Wall verticalDirection() {
        return y > 0 ? Wall.DOWN : Wall.UP;
    }

}

