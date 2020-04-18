package com.mygdx.game;

import java.io.Serializable;

/**
 * Created by Rafael Fiedler on 18.02.2017.
 */
public class Tile implements Serializable {
    int type = -1;
    int x = 0;
    int y = 0;
    boolean buildPlace;

    public Tile(int x, int y, int type, boolean buildPlace) {
        this.x = (x / 64) * 64;
        this.y = (y / 64) * 64;
        this.type = type;
        this.buildPlace = buildPlace;
    }

    public Tile() {
        type = -1;
        buildPlace = true;
    }
}
