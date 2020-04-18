package com.mygdx.game;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

/**
 *Created by Rafael Fiedler on 18.02.2017.
 */
public class Effects {
    float x = 0;
    float y = 0;
    int timer = 120;
    int type = -1;
    int t = 0;
    private PointLight pointLight;
    private boolean lowLigth;

    void createEfect(float x,float y,int timer,int type){
        this.timer = timer;
        this.type = type;
        this.x = x;
        this.y = y;
        t = 0;
        if(Main.isLigthEnable) {
            pointLight = new PointLight(Main.rayHandler, 150, Color.WHITE, 20, x + 32, y + 32);
            pointLight.setSoft(true);
        }
    }
    void createEfect(float x,float y,int timer,int type,int lightWidth){
        this.timer = timer;
        this.type = type;
        this.x = x;
        this.y = y;
        t = 0;
        if(Main.isLigthEnable) {
            pointLight = new PointLight(Main.rayHandler, 50, Color.WHITE, lightWidth, x + 32, y + 32);
            pointLight.setSoft(true);
            lowLigth = true;
        }
    }

    void effectPorcess() {
        if(Main.isLigthEnable && lowLigth){
            pointLight.setDistance(pointLight.getDistance()-1);
        }
        if (type != -1) {
                if(t >= 10){
                    if(Main.isLigthEnable) {
                        pointLight.remove();
                    }
                    t =-1;
                }else if(t != -1){
                    t++;
                }
                Main.batch.draw(Main.textures[18+type], x,y, 64, 64);
            if (timer == 0) {
                type = -1;
                return;
            } else {
                timer--;
            }
        }
    }
}
