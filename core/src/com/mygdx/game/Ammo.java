package com.mygdx.game;

import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;


/**
 * Created by Rafael Fiedler on 18.02.2017.
 */
public class Ammo {
    public int type = -1;
    float y;
    float x;
    float vx;
    float vy;
    int zielX;
    int zielY;
    double oneTik;
    int mode;
    int inik;
    int speed;
    int damage;
    PointLight pointLight;
    float h = 0;
    double lifeTime = 0;

    public void create(int x, int y, int zielX, int zielY, int type, int mode, float h, int tower) {
        lifeTime = 250;
        this.x = x;
        this.y = y;
        this.zielX = zielX;
        this.zielY = zielY;
        this.type = type;
        float a =zielX-x;
        float b =zielY-y;
         vx = (float) (a/Math.sqrt(a*a+b*b));
         vy = (float) (b/Math.sqrt(a*a+b*b));
        if(type == 3){
            speed = 10;
            damage = 80;
        }else{
            speed = 30;
            damage = 20;
        }
        this.mode = mode;
        this.h = h;
        inik = tower;
        if (Main.isLigthEnable) {
            pointLight = new PointLight(Main.rayHandler, 20, Color.WHITE, 20, -200, -200);
            pointLight.setSoft(true);
            pointLight.setSoftnessLength(128);
        }
    }


    void ammoProcess() {
        oneTik = Gdx.graphics.getDeltaTime() *60;
        if (type != -1) {
            if (lifeTime < 0) {
                type =-1;
            }else{
                lifeTime-=oneTik;
            }
            if (type == 1 || type == 3) {
                    x += vx * oneTik*speed;
                    y += vy * oneTik*speed;
                for (int i = 0; i < Main.enemy.length; i++) {
                    if (Main.enemy[i].type != -1 && Math.abs(distance((int) Main.enemy[i].x, (int) Main.enemy[i].y, x, y)) < 32) {
                        Main.enemy[i].health -= damage;
                        if (Main.enemy[i].health <= 0) {
                            Main.score += 5 + (Main.enemy[i].type * 5);
                            for (int z = 0; z < Main.yK.length; z++) {
                                if (Main.alpha[z] <= 0) {
                                    Main.yK[z] = (int) Main.enemy[i].y;
                                    Main.xK[z] = (int) Main.enemy[i].x;
                                    Main.alpha[z] = 1;
                                    Main.value[z] = 5 + (Main.enemy[i].type * 5);
                                    break;
                                }
                            }
                            Main.enemy[i].type = -1;
                            Main.tower[inik].kills++;

                        }
                        for (int z = 0; z < Main.effect.length; z++) {
                            if (Main.effect[z].type == -1) {
                                int b = 0;
                                int h = 0;
                                if (zielX > x) {
                                    b = +32;
                                } else if (zielX < x) {
                                    b = -32;
                                }
                                if (zielY > y) {
                                    h = +32;
                                } else if (zielY < y) {
                                    h = -32;
                                }
                                if (type == 3) {
                                    Main.effect[z].createEfect(x + b + (int) ((Math.random() * 16) + 0), y + h + (int) ((Math.random() * 16) + 0), 440, (int) ((Math.random() * 3) + 1), 100);
                                } else {
                                    Main.effect[z].createEfect(x + b + (int) ((Math.random() * 16) + 0), y + h + (int) ((Math.random() * 16) + 0), 440, (int) ((Math.random() * 3) + 1));
                                }
                                break;
                            }
                        }
                        if (Main.speedUp == 1) {
                            Main.sound[1].play(0.25f);
                        }
                        type = -1;
                    }
                }
                    if (Math.abs(distance(x, y, zielX, zielY)) <= 15) {
                        for (int z = 0; z < Main.effect.length; z++) {
                            if (Main.effect[z].type == -1) {
                                Main.effect[z].createEfect(x + (int) ((Math.random() * 16) + 0), y + (int) ((Math.random() * 16) + 0), 440, (int) ((Math.random() * 3) + 1));
                                break;
                            }
                        }
                        if (Main.speedUp == 1) {
                            Main.sound[1].play(0.10f);
                        }
                        type = -1;
                    }

                }
                    if (Main.isLigthEnable) {
                        pointLight.update();
                        pointLight.setPosition(x + 32, y + 32);
                    }
                    if (type == 3) {
                        Main.batch.draw(Main.textures[251], x, y, 32, 32, 64, 64, 1, 1, h );
                    } else {
                        Main.batch.draw(Main.textures[295], x, y, 32, 32, 64, 64, 1, 1, h);
                    }
            } else if (type == 2) {
                if (zielX < x) {
                    x -= (x - zielX) / 10;

                } else if (zielX > x) {
                    x += (zielX - x) / 10;
                }

                if (zielY < y) {
                    y -= (y - zielY) / 10;
                } else if (zielY > y) {
                    y += (zielY - y) / 10;
                }
                    Main.batch.draw(Main.textures[275], x, y, 32, 32, 64, 64, 1, 1, h);

                if (Main.tower[mode].type != -1 && Math.abs(distance(zielX, zielY, x, y)) < 32) {
                    Main.tower[mode].health -= 10;
                    type = -1;
                }
            if (type == -1) {
                if (Main.isLigthEnable) {
                    pointLight.remove();
                }
            }
        }
    }

    public int distance(float x1, float y1, float x2, float y2) {
        return (int) (Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
    }

}
