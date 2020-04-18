package com.mygdx.game;


import com.badlogic.gdx.Gdx;

/**
 * Created by Rafael Fiedler on 11.03.2017.
 */
public class Enemy {
    int type = -1;
    float x = 0;
    float y = 0;
    int pathIndex = 0;
    int health = 100;
    public boolean first;
    public int ticker;
    public int zielAngle = 0;
    public int h1 = 0;
    public int fireRate;
    public int speed;
    int angleSpeed = 0;
    int rotation = 0;
    boolean tik = true;


    public Enemy(int x, int y, int type, int startIndex,int h) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.rotation =h;
        if (type != 5) {
            speed = 120;
        } else {
            speed = 60;
            angleSpeed = -3;
        }
        health = 25 + (type * 50);
        if (type == 2) {
            health = 225;
        } else if (type == 3) {
            health = 450;
        } else if (type == 3) {
            health = 850;
        } else if (type == 4) {
            health = 1000;
        } else if (type == 5) {
            health = 5000;
        } else {
            health = 25 + (type * 50);
        }
        pathIndex = startIndex+1;

        if (Main.pathFlags[pathIndex][0] < x) {
            rotation = 180;
        }else if (Main.pathFlags[pathIndex][0] > x) {
            rotation = 0;
        }else if (Main.pathFlags[pathIndex][1] < y) {
            rotation = 270;
        }else{
            rotation = 90;
        }
    }


    public void pathProcess() {
        float deltaSpeed = Gdx.graphics.getDeltaTime() *speed;
        System.out.println(Gdx.graphics.getDeltaTime());
        if (type >= 0) {
            if (ticker == 15) {
                if (tik) {
                    tik = false;
                } else {
                    tik = true;
                }
                ticker = 0;
            }
            ticker++;
            if (pathIndex == Main.lastFlag) {
                if (Main.health > 0) {
                    Main.health -= 10 + (20 + type);
                    if (Main.health <= 0) {
                        Main.setScore = 0;
                        Main.killScore = 0;
                        for (int i = 0; i < Main.tower.length; i++) {
                            Main.setScore += Main.tower[i].kills*50;
                            Main.killScore +=Main.tower[i].kills;
                            Main.tower[i].type = -1;
                        }
                    }
                }
                type = -1;
                return;
            }


            if (distance((int)x,(int)y, Main.pathFlags[pathIndex][0], Main.pathFlags[pathIndex][1]) <= 0) {
                pathIndex++;
                first = true;
            } else {
                int startRotate = (2 * angleSpeed) * -1;
                if (x < Main.pathFlags[pathIndex][0]) {
                    x += ((Main.pathFlags[pathIndex][0]-x) < deltaSpeed) ? (Main.pathFlags[pathIndex][0]-x):deltaSpeed;
                    if (first) {
                        rotation = 0;
                        ticker = 0;
                        tik = true;
                        first = false;
                    }
                    if (pathIndex + 1 < Main.lastFlag && (Main.pathFlags[pathIndex][0] - x) <= 32 + startRotate) {
                        if (Main.pathFlags[pathIndex + 1][1] < y + 32 + startRotate && rotation != 270) {
                            rotation -= 5 + angleSpeed;
                            if (rotation == -90) {
                                rotation = 270;
                            }
                        } else if (Main.pathFlags[pathIndex + 1][1] > y + 32 + startRotate && rotation != 90) {
                            rotation += 5 + angleSpeed;
                            if (rotation == 90) {
                                rotation = 90;
                            }
                        }
                    } else if (type != 5) {
                        if (tik) {
                            rotation = 5;
                        } else {
                            rotation = -5;
                        }
                    }
                } else if (x > Main.pathFlags[pathIndex][0]) {
                    x -= ((x-Main.pathFlags[pathIndex][0]) <= deltaSpeed) ? (x-Main.pathFlags[pathIndex][0]):deltaSpeed;
                    if (first) {
                        rotation = 180;
                        ticker = 0;
                        tik = true;
                        first = false;
                    }
                    if (pathIndex + 1 < Main.lastFlag && (x - Main.pathFlags[pathIndex][0]) <= 32 + startRotate) {
                        if (Main.pathFlags[pathIndex + 1][1] > y + 32 + startRotate && rotation != 90) {
                            rotation -= 5 + angleSpeed;
                            if (rotation == 90) {
                                rotation = 90;
                            }
                        } else if (Main.pathFlags[pathIndex + 1][1] < y + 32 + startRotate && rotation != 270) {
                            rotation += 5 + angleSpeed;
                            if (rotation == 270) {
                                rotation = 270;
                            }
                        }
                    } else if (type != 5) {
                        if (tik) {
                            rotation = 185;
                        } else {
                            rotation = 175;
                        }
                    }
                }
                if (y < Main.pathFlags[pathIndex][1]) {
                    y +=((Main.pathFlags[pathIndex][1]-y) <= deltaSpeed) ? (Main.pathFlags[pathIndex][1]-y):deltaSpeed;
                    if (first) {
                        rotation = 90;
                        ticker = 0;
                        tik = true;
                        first = false;
                    }
                    if (pathIndex + 1 < Main.lastFlag && (Main.pathFlags[pathIndex][1] - y) <= 32 + startRotate) {
                        if (Main.pathFlags[pathIndex + 1][0] < x + 32 + startRotate && rotation != 180) {
                            rotation += 5 + angleSpeed;
                            if (rotation == 180) {
                                rotation = 180;
                            }
                        } else if (Main.pathFlags[pathIndex + 1][0] > x + 32 + startRotate && rotation != 0) {
                            rotation -= 5 + angleSpeed;
                            if (rotation == 0) {
                                rotation = 0;
                            }
                        }
                    } else if (type != 5) {
                        if (tik) {
                            rotation = 95;
                        } else {
                            rotation = 85;
                        }
                    }
                } else if (y > Main.pathFlags[pathIndex][1]) {
                    y -= ((y-Main.pathFlags[pathIndex][1]) <= deltaSpeed) ? (y-Main.pathFlags[pathIndex][1]):deltaSpeed;
                    if (first) {
                        rotation = 270;
                        ticker = 0;
                        tik = true;
                        first = false;
                    }
                    if (pathIndex + 1 < Main.lastFlag && (y - Main.pathFlags[pathIndex][1]) <= 32 + startRotate) {
                        if (Main.pathFlags[pathIndex + 1][0] < x + 32 + startRotate && rotation != 180) {
                            rotation -= 5 + angleSpeed;
                            if (rotation == 180) {
                                rotation = 180;
                            }
                        } else if (Main.pathFlags[pathIndex + 1][0] > x + 32 + startRotate && rotation != 0) {
                            rotation += 5 + angleSpeed;
                            if (rotation == 360) {
                                rotation = 0;
                            }
                        }
                    } else if (type != 5) {
                        if (tik) {
                            rotation = 275;
                        } else {
                            rotation = 265;
                        }
                    }
                }
            }
            if (type == 1) {
                Main.batch.draw(Main.textures[245], x, y,32,32,64,64,1,1,rotation);
            } else if (type == 2) {
                Main.batch.draw(Main.textures[246], x, y, 32, 32, 64, 64, 1, 1, rotation);
            } else if (type == 3) {
                Main.batch.draw(Main.textures[247], x, y, 32, 32, 64, 64, 1, 1, rotation);
            } else if (type == 4) {
                Main.batch.draw(Main.textures[248], x, y, 32, 32, 64, 64, 1, 1, rotation);
            } else if (type == 5) {
                if (fireRate > 0) {
                    fireRate--;
                }


                for (int i = 0; i < Main.tower.length; i++) {
                    if (Main.tower[i].type != -1 && zielAngle >= h1 - 5 && zielAngle <= h1 + 5 && distance((int)x + 32,(int) y + 32, Main.tower[i].x + 32, Main.tower[i].y + 32) <= 200) {
                        if (fireRate == 0) {
                            for (int z = 0; z < Main.ammo.length; z++) {
                                if (Main.ammo[z].type == -1) {
                                    Main.ammo[z].create((int)x, (int)y, Main.tower[i].x, Main.tower[i].y, 2, i, 0, -1);
                                    fireRate = 160;
                                    break;
                                }
                            }
                        }
                        if (Main.tower[i].y < y - 32 && Main.tower[i].x > x + 32) {
                            float disty = (y - 32) - Main.tower[i].y;
                            float distx = Main.tower[i].x - (x - 32);
                            zielAngle = 360 - Math.abs((int) Math.toDegrees(Math.atan(disty / distx)));
                        } else if (Main.tower[i].y < y - 32 && Main.tower[i].x <= x + 32) {
                            float disty = (y - 32) - Main.tower[i].y;
                            float distx = (x - 32) - Main.tower[i].x;
                            zielAngle = 180 + Math.abs((int) Math.toDegrees(Math.atan(disty / distx)));
                        } else if (Main.tower[i].y > y - 32 && Main.tower[i].x > x + 32) {
                            float disty = Main.tower[i].y - (y - 32);
                            float distx = Main.tower[i].x - (x - 32);
                            zielAngle = Math.abs((int) Math.toDegrees(Math.atan(disty / distx)));
                        } else if (Main.tower[i].y > y - 32 && Main.tower[i].x <= x + 32) {
                            float disty = Main.tower[i].y - (y - 32);
                            float distx = (x - 32) - Main.tower[i].x;
                            zielAngle = 180 - Math.abs((int) Math.toDegrees(Math.atan(disty / distx)));
                        }
                        break;
                    }
                }
                if (zielAngle > h1) {
                    h1 += 1;
                } else if (zielAngle < h1) {
                    if (h1 >= 270 && zielAngle < 180) {
                        h1 += 1;
                        if (h1 >= 360) {
                            h1 = 0;
                        }
                    } else {
                        h1 -= 1;
                    }
                }
                if (zielAngle > h1) {
                    h1 += 1;
                } else if (zielAngle < h1) {
                    if (h1 >= 270 && zielAngle < 180) {
                        h1 += 1;
                        if (h1 >= 360) {
                            h1 = 0;
                        }
                    } else {
                        h1 -= 1;
                    }
                }
                if (zielAngle > h1) {
                    h1 += 1;
                } else if (zielAngle < h1) {
                    if (h1 >= 270 && zielAngle < 180) {
                        h1 += 1;
                        if (h1 >= 360) {
                            h1 = 0;
                        }
                    } else {
                        h1 -= 1;
                    }
                }
                if (zielAngle > h1) {
                    h1 += 1;
                } else if (zielAngle < h1) {
                    if (h1 >= 270 && zielAngle < 180) {
                        h1 += 1;
                        if (h1 >= 360) {
                            h1 = 0;
                        }
                    } else {
                        h1 -= 1;
                    }
                }
                Main.batch.draw(Main.textures[268], x, y, 32, 32, 64, 64, 1, 1, rotation);
                Main.batch.draw(Main.textures[291], x, y, 32, 32, 64, 64, 1, 1, h1);
            }
        }
    }

    public int distance(int x1, int y1, int x2, int y2) {
        return (int) (Math.sqrt(Math.pow(x2 - (x1 - 0), 2) + Math.pow(y2 - (y1 - 0), 2)));
    }
}
