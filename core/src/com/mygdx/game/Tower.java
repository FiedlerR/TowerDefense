package com.mygdx.game;


import com.badlogic.gdx.Gdx;

/**
 * Created by Rafael Fiedler on 18.02.2017.
 */
public class Tower {
    int type = -1;
    int health = 100;
    int maxHealth = 100;
    int x = 0;
    int y = 0;
    double fireRate = 0;
    double firerate = 30;
    float h = 0;
    int zielAngle = 0;
    int tower;
    int kills = 0;
    float oneTik;

    public Tower(int x, int y, int type, int tower) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.tower = tower;
        kills = 0;
    }

    public Tower() {

    }

    public void towerProcess() {
        oneTik = Gdx.graphics.getDeltaTime() *60;
        if (health <= 0) {
            type = -1;
        }
        for (int i = 0; i < Main.enemy.length; i++) {
            if (Main.enemy[i].type != -1 && distance((int)x + 32, (int)y + 32, (int)Main.enemy[i].x + 32, (int)Main.enemy[i].y + 32) <= 300) {
                if (Main.enemy[i].y < y - 32 && Main.enemy[i].x > x + 32) {
                    float disty = (y - 32) - Main.enemy[i].y;
                    float distx = Main.enemy[i].x - (x - 32);
                    zielAngle = 360 - Math.abs((int) Math.toDegrees(Math.atan(disty / distx)));
                } else if (Main.enemy[i].y < y - 32 && Main.enemy[i].x <= x + 32) {
                    float disty = (y - 32) - Main.enemy[i].y;
                    float distx = (x - 32) - Main.enemy[i].x;
                    zielAngle = 180 + Math.abs((int) Math.toDegrees(Math.atan(disty / distx)));
                } else if (Main.enemy[i].y > y - 32 && Main.enemy[i].x > x + 32) {
                    float disty = Main.enemy[i].y - (y - 32);
                    float distx = Main.enemy[i].x - (x - 32);
                    zielAngle = Math.abs((int) Math.toDegrees(Math.atan(disty / distx)));
                } else if (Main.enemy[i].y > y - 32 && Main.enemy[i].x <= x + 32) {
                    float disty = Main.enemy[i].y - (y - 32);
                    float distx = (x - 32) - Main.enemy[i].x;
                    zielAngle = 180 - Math.abs((int) Math.toDegrees(Math.atan(disty / distx)));
                }
                if (zielAngle >= h - 5 && zielAngle <= h + 5) {
                    fire((int)Main.enemy[i].x, (int)Main.enemy[i].y,i);
                }
                break;
            }
        }

        if (fireRate > 0) {
            fireRate-=oneTik;
        }

        if (zielAngle > h) {
            if (h < 90 && zielAngle > 270) {
                h-=oneTik;
                if (h <= 0) {
                    h = 360;
                }
            } else {
                h += oneTik;
            }
        } else if (zielAngle < h) {
            if (h >= 270 && zielAngle < 180) {
                h+=oneTik;
                if (h >= 360) {
                    h = 0;
                }
            } else {
                h -= oneTik;
            }
        }
        if (zielAngle > h) {
            if (h < 90 && zielAngle > 270) {
                h-=oneTik;
                if (h <= 0) {
                    h = 360;
                }
            } else {
                h += oneTik;
            }
        } else if (zielAngle < h) {
            if (h >= 270 && zielAngle < 180) {
                h+=oneTik;
                if (h >= 360) {
                    h = 0;
                }
            } else {
                h -= oneTik;
            }
        }

        Main.batch.draw(Main.textures[180], x, y,64,64);
        if (type == 1) {
            Main.batch.draw(Main.textures[249], x, y, 32, 32, 64, 64, 1, 1, h);
        } else if (type == 2) {
            Main.batch.draw(Main.textures[250], x, y, 32, 32, 64, 64, 1, 1, h);
        } else if (type == 3) {
            Main.batch.draw(Main.textures[226], x, y, 32, 32, 64, 64, 1, 1, h);
        }
    }

    public void drawTowerInformation(){
        Main.batch.draw(Main.textures[301], x, y+32,64, 64);
        Main.batch.draw(Main.textures[299], x, y+32, (health / (float)maxHealth) * 64, 64);
        if(kills >= 1000) {
            Main.printNumbers("" + kills, x+5, y+22, -1, false,24);
        }else if(kills >= 100) {
            Main.printNumbers("" + kills, x, y+10, -1, false,32);
        }else if(kills >= 10) {
            Main.printNumbers("" + kills, x-5, y, -1, false,48);
        }else{
            Main.printNumbers("" + kills, x, y, -1, false,64);
        }
    }

    void drawMenu() {
        if (Main.isSelected == tower) {
            int newX = 633;
            int newY = 0;
            if (health < maxHealth) {
                if (type == 1) {
                    Main.batch.draw(Main.textures[88], newX + 100, newY, 100, 100);
                    Main.batch.draw(Main.textures[250], newX + 112, newY + 6, 88, 88);
                    Main.batch.draw(Main.textures[90], newX, newY, 100, 100);
                    Main.batch.draw(Main.textures[89], newX - 100, newY, 100, 100);
                } else if (type == 2) {
                    Main.batch.draw(Main.textures[88], newX + 100, newY, 100, 100);
                    Main.batch.draw(Main.textures[226], newX + 112, newY + 6, 88, 88);
                    Main.batch.draw(Main.textures[90], newX, newY, 100, 100);
                    Main.batch.draw(Main.textures[89], newX - 100, newY, 100, 100);
                } else {
                    Main.batch.draw(Main.textures[89], newX - 50, newY, 100, 100);
                    Main.batch.draw(Main.textures[90], newX + 50, newY, 100, 100);
                }
            } else {
                if (type == 1) {
                    Main.batch.draw(Main.textures[90], newX - 50, newY, 100, 100);
                    Main.batch.draw(Main.textures[88], newX + 50, newY, 100, 100);
                    Main.batch.draw(Main.textures[250], newX + 56, newY + 6, 88, 88);
                } else if (type == 2) {
                    Main.batch.draw(Main.textures[90], newX - 50, newY, 100, 100);
                    Main.batch.draw(Main.textures[88], newX + 50, newY, 100, 100);
                    Main.batch.draw(Main.textures[226], newX + 56, newY + 6, 88, 88);
                } else {
                    Main.batch.draw(Main.textures[90], newX, newY, 100, 100);
                }
            }

        }
    }

    private void fire(int zielX, int zielY, int mode) {
        if (fireRate <= 0) {
            for (int i = 0; i < Main.ammo.length; i++) {
                if (Main.ammo[i].type == -1) {
                    if (Main.speedUp == 1){
                        Main.sound[0].play(0.10f);
                    }
                    if (type != 3) {
                        Main.ammo[i].create(x, y, zielX, zielY, 1, mode, h, tower);
                    } else {
                        Main.ammo[i].create(x, y, zielX, zielY, 3, mode, h, tower);
                    }
                    fireRate = firerate;
                    break;
                }
            }

        }
    }

    public int distance(int x1, int y1, int x2, int y2) {
        return (int) (Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
    }

    public String toString() {
        return x + "/" + y + "#" + type;
    }
}
