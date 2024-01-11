package com.billenius.VisibleObjects;

import java.awt.*;

public interface VisibleObject {
    void spawn();

    boolean isSpawned();
    void deSpawn();

    void paint(Graphics2D g2);

}
