package com.mygdx.game;
import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;

/**
 *Created by Rafael Fiedler on 18.02.2017.
 */
public class BackgroundEffects {
    private PointLight pointLight;
    int x = 0;
    int y = 0;
    int altx = 0;
    int alty = 0;
    int h = 0;
    int type = -1;
    int action = 0;
    int timer = 0;
    float width = 64;
    float height= 64;
    int trigger = -1;
    boolean activ = false;

   public BackgroundEffects(int x, int y , int h, int type, int action, int timer, int trigger){
       this.x = x;
       this.y = y;
       this.h =h;
       this.type = type;
       this.action = action;
       this.timer = timer;
       this.trigger = trigger;
       altx = x;
       alty = y;
       if(Main.isLigthEnable) {
           pointLight = new PointLight(Main.rayHandler, 10, Color.WHITE, 10, -200, -200);
           pointLight.setSoftnessLength(200);
       }
   }

   public void backgroundEffectsProcess() {
       if (type != -1) {
           if (Main.timer == timer) {
               activ = true;
           }
           if (activ == true) {
               if (action == 1) {
                   x += 3;
                   if(x > 1528){
                       type =-1;
                       activ = false;
                   }
               }
               if (action == 2) {
                   x -= 3;
                   if(x < -128){
                       type =-1;
                       activ = false;
                   }
               }
               if (action == 3) {
                   x += 2;
                   y += 2;
                   if(x > 1528 && y > 900){
                       type =-1;
                       activ = false;
                   }
               }
               if (action == 4) {
                   x -= 2;
                   y -= 2;
                   if(x < -128 && y < -128){
                       type =-1;
                       activ = false;
                   }
               }
               if (action == 5) {
                   x += 2;
                   y -= 2;
                   if(x > 1528 && y < -128){
                       type =-1;
                       activ = false;
                   }
               }
               if(type == 2) {
                   if (trigger == 0) {
                       trigger = 32;
                       for (int z = 0; z < Main.effect.length; z++) {
                           if (Main.effect[z].type == -1) {
                               Main.effect[z].createEfect(x, y, 400, 3);
                               break;
                           }
                       }
                       for (int z = 0; z < Main.tile.length; z++) {
                           if (Main.tile[z].type == -1) {
                               Main.tile[z].x = x;
                               Main.tile[z].y = y;
                               Main.tile[z].type = 18+(int)((Math.random() * 3) + 1);
                               Main.sound[1].play();
                               Main.lastBackgroundObject++;
                               break;
                           }
                       }
                       for (int z = 0; z < Main.effect.length; z++) {
                           if (Main.effect[z].type == -1) {
                               Main.effect[z].createEfect(x + 32, y, 400, 2);
                               break;
                           }
                       }
                       for (int z = 0; z < Main.effect.length; z++) {
                           if (Main.effect[z].type == -1) {
                               Main.effect[z].createEfect(x - 32, y, 400, 2);
                               break;
                           }
                       }
                       for (int i = 0; i < Main.tile.length; i++) {
                           if (Main.tile[i].type >= 130 && Main.tile[i].type <= 134 && Main.rectangleCollision(x,y,width,height,Main.tile[i].x,Main.tile[i].y,64,64)) {
                               Main.tile[i].type = 19;
                               Main.sound[1].play();
                               break;
                           }
                       }
                       for (int i = 0; i < Main.tower.length; i++) {
                           if (Main.tower[i].type !=- 1 && Main.rectangleCollision(x,y,64,64,Main.tower[i].x,Main.tower[i].y,64,64)) {
                               Main.tower[i].health -= 20;
                               break;
                           }
                       }
                   } else if (trigger != -1) {
                       trigger--;
                   }
               }

               Main.batch.draw( Main.textures[269+type],x, y,width/2,height/2,width,height,1,1,h);
               if(type == 1) {
                   if (trigger == 0) {
                       height-=0.2;
                       width-=0.2;
                     if (width < 50){
                         for (int z = 0; z < Main.effect.length; z++) {
                             if (Main.effect[z].type == -1) {
                                 Main.effect[z].createEfect(x, y, 400, 3);
                                 break;
                             }
                         }
                         for (int z = 0; z < Main.effect.length; z++) {
                             if (Main.effect[z].type == -1) {
                                 Main.effect[z].createEfect(x + 32, y, 400, 2);
                                 break;
                             }
                         }
                         for (int z = 0; z < Main.effect.length; z++) {
                             if (Main.effect[z].type == -1) {
                                 Main.effect[z].createEfect(x - 32, y, 400, 2);
                                 break;
                             }
                         }
                         for (int i = 0; i < Main.tile.length; i++) {
                             if (Main.tile[i].type >= 130 && Main.tile[i].type <= 134 && Main.rectangleCollision(x,y,width,height,Main.tile[i].x,Main.tile[i].y,64,64)) {
                                 Main.tile[i].type = 19;
                                 break;
                             }
                         }
                         for (int i = 0; i < Main.tower.length; i++) {
                             if (Main.tower[i].type !=- 1 && Main.rectangleCollision(x,y,width,height,Main.tower[i].x,Main.tower[i].y,64,64)) {
                                 Main.tower[i].health -= 20;
                                 break;
                             }
                         }
                         for (int z = 0; z < Main.tile.length; z++) {
                             if (Main.tile[z].type == -1) {
                                 Main.tile[z].x = x;
                                 Main.tile[z].y = y;
                                 Main.tile[z].type = 18+(int)((Math.random() * 3) + 1);
                                 Main.sound[1].play();
                                 //Main.lastBackgroundObject++;
                                 break;
                             }
                         }
                         type=-1;
                         x=-300;
                         if (Main.isLigthEnable) {
                             pointLight.remove();
                         }
                     }
                     if(Main.isLigthEnable) {
                         pointLight.setPosition(32 + x, y + 32);
                     }
                       Main.batch.draw( Main.textures[298], x+width/4,y+height/4,width/2,height/2,width,height,1,1,90+h);
                   } else if (trigger != -1) {
                       trigger--;
                   }
                   if(trigger ==-1){
                       if((int)((Math.random() * 1000) + 0) == 1){
                           trigger = 10;
                       }
                   }
               }
           }
       }
   }
}
