package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Rafael Fiedler on 18.02.2017.
 */

class LevelElements {
    float x;
    float y;
    String name;
    int width;
    int height;
    Texture texture;
    Button start;
    Button resume;
    float textureX;
    float textureY;
    float textX;
    float textY;
    int textureWidth;
    int textureHeight;
    long score = 0;

    public LevelElements(int x, int y, int width, int height, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.width = width;
        this.height = height;
        try {
            texture = new Texture(Gdx.files.internal("data/world/" + name + ".png"));
        }catch (Exception e){
            texture = new Texture(Gdx.files.internal("data/world/World_1.png"));
        }
        start = new Button(x + width - 220, y - height - 65, 220, 65, 0, "Play");
        resume = new Button(x + width - 320, y + 70, 260, 65, 0, "Continue", 20);//Continue
        try {
            String conf = GameLib.loadFile("data/world/" + name + "_playerDat.xml");
            score = GameLib.extractInfoLong(conf, "<score>", "</score>");
        }catch (Exception e){
            GameLib.saveFile("data/world/" + name + "_playerDat.xml", "<score>"+0+"</score>");
        }
        actPositions();
    }

    public void process() {
        Main.batch.draw(Main.guiElements[6], x, y, width, height);
        Main.batch.draw(texture, textureX, textureY, textureWidth, textureHeight);
        Main.menuFont.draw(Main.batch, name.replace("_", " "), x + 40, y + 70);
        Main.menuFont.draw(Main.batch, "Score: " + score, x + 400, y + 70);
        start.processButton();
        resume.processButton();
        if (resume.clicked()) {
            Main.loadLevel = true;
            Main.loaded = 0;
            Main.world = name;
            Main.screen = 0;
            Main.welle = 0;
            Main.continueGame = true;
        }
        if (start.clicked()) {
            Main.loadLevel = true;
            Main.loaded = 0;
            Main.world = name;
            Main.screen = 0;
            Main.welle = 0;
            Main.continueGame = false;
        }
    }

    void actPositions() {
        textureWidth = width - 80;
        textureHeight = height - 120;
        textureX = x + 40;
        textureY = y + 100;
        textX = x + 90 + width / 2;
        textY = y + height - 50;
        start.x = x + width - 220;
        start.y = y - 65;
        resume.x = x + width - 300;
        resume.y = y + 20;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        actPositions();
    }
}
