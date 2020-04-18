package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Rafael Fiedler on 26.02.2017.
 */
public class Loader {

    public static void loadSound() {
        for (int i = 0; i < 3; i++) {
            Main.sound[i] = Gdx.audio.newSound(Gdx.files.internal("sound/sound" + (i + 1) + ".wav"));
        }
    }

    public static void loadTexture(int length) {
        Main.flag = new Texture(Gdx.files.internal("graphic/flag.png"));
        Texture temp = new Texture(Gdx.files.internal("graphic/tileSheet.png"));
        TextureRegion tempRegion [][]= TextureRegion.split(temp,128,128);
        for (int i = 0;i < tempRegion.length;i++) {
            for (int j = 0; j < tempRegion[i].length; j++) {
                if (j+(i*tempRegion[i].length) >= length) {
                    return;
                }
                Main.textures[j+(i*tempRegion[i].length)] = tempRegion[i][j];
            }
        }
        /*for (int i = 1; i < 302; i++) {
            String numb = "";
            if (i < 10) {
                numb = "00" + i;
            } else if (i < 100) {
                numb = "0" + i;
            } else {
                numb = i + "";
            }
            Main.background[i] = new Texture(Gdx.files.internal("graphic/towerDefense_tile" + numb + ".png"));
        }*/
    }

    public static void loadLevel() {
        String maps[] = new String[]{"World_1", "World_2", "World_3", "World_4", "World_5","World_6", "World_7", "World_8", "World_9", "World_10"};
        for (int j = 0; j < maps.length; j++) {
            try {
                String map = maps[j];
                Main.level.add(new LevelElements(20 + 1366 * j, -740, 1326, 690, map));
            } catch (Exception e) {
                break;
            }
        }
    }

    public static void loadGui() {
        for (int i = 0; i < Main.guiElements.length; i++) {
            Main.guiElements[i] = new Texture(Gdx.files.internal("graphic/gui/guiElement_" + i + ".png"));
        }
    }

    public static void loadFont() {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter;
        Main.menuFont = new BitmapFont(Gdx.files.internal("graphic/gui/Arial_35.fnt"));
        Main.siteTitle = new BitmapFont(Gdx.files.internal("graphic/gui/Arial_60.fnt"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("graphic/gui/kenvector_future.ttf"));
        parameter.size = 130;
        Main.bigFont = generator.generateFont(parameter);
        Main.layout = new GlyphLayout();
    }

}
