package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;

import java.awt.*;

/**
 * Created by Rafael Fiedler on 18.02.2017.
 */
public class Button {
    float x;
    float y;
    int type;
    int width;
    int height;
    String text;
    int add = 0;

    public Button(int x, int y, int width, int height, int textureType, String text) {
        this.x = x;
        this.y = y;
        this.type = textureType;
        this.width = width;
        this.height = height;
        this.text = text;
        this.add = 0;//Main.getCenterX(width, text);

    }

    public Button(int x, int y, int width, int height, int textureType, String text, int textX) {
        this.x = x;
        this.y = y;
        this.type = textureType;
        this.width = width;
        this.height = height;
        this.text = text;
        this.add = textX;
    }


    public void processButton() {
        if (Main.rectangleCollision(Main.getMouseX(), Main.getMouseY(), 1, 1, x, y, width, height)) {
            Main.batch.draw(Main.guiElements[type + 1], x, y, width, height);
        } else {
            Main.batch.draw(Main.guiElements[type], x, y, width, height);
        }
       // Main.menuFont.draw(Main.batch, text, x + 5 + add, y + height - height / 3);
        Main.layout.setText(Main.menuFont,text, Color.WHITE,width, Align.center,false);
        Main.menuFont.draw(Main.batch,Main.layout,x,y+(height+Main.layout.height)/2);
    }

    public boolean clicked() {
        if (System.currentTimeMillis() - Main.wait >= 300 && Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Main.rectangleCollision(Main.getMouseX(), Main.getMouseY(), 1, 1, x, y, width, height)) {
            Main.wait = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public String toString() {
        return x + "/" + y;
    }
}
