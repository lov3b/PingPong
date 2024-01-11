package com.billenius;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class PongBackground extends JPanel {
    private static final int DASH_LENGTH = 10;
    private static final int DASH_SPACE = 10;
    private static final Color STRIPES_BACKGROUND = new Color(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(), 100);

    @NotNull
    private RadialGradientPaint getRadialGradientPaint() {
        double radius = Math.sqrt(Math.pow(getWidth() / 2d, 2) + Math.pow(getHeight() / 2d, 2));

        // Bright in center, dark in corners
        return new RadialGradientPaint(
                new Point2D.Double(getWidth() / 2d, getHeight() / 2d),
                (float) radius,
                new float[]{0f, 1f},
                new Color[]{Color.BLUE.brighter(), Color.BLUE.darker().darker().darker().darker()}
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        RadialGradientPaint radialGradientPaint = getRadialGradientPaint();
        g2d.setPaint(radialGradientPaint);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw the dashed line
        g2d.setColor(STRIPES_BACKGROUND);
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{DASH_LENGTH, DASH_SPACE}, 0));
        g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());

        g2d.dispose();
    }
}
