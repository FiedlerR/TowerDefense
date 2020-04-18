package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Rafael on 25.12.2017.
 */
public class GameMethods {

    static void checkTilemap(TextureRegion[] regions) {
        try {
            int y = 0;
            int x = 0;
            for (int i = 0; i < regions.length; i++) {
                System.out.println("!!!#"+i);
                Main.batch.draw(regions[i], x , y );
                if (x >= 1300) {
                    x = 0;
                    y += regions[i].getRegionHeight();
                } else {
                    x += regions[i].getRegionWidth();
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
