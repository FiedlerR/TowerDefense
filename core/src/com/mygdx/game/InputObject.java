package com.mygdx.game;

import com.badlogic.gdx.Input;

/**
 * Created by Rafael Fiedler on 11.03.2017.
 */
public class InputObject implements Input.TextInputListener {
    int modul;

    public InputObject(int modul) {
        super();
        this.modul = modul;
    }

    @Override
    public void input(String text) {
        try{
            if(Integer.parseInt(text) < 0){
                return;
            }
        }catch(Exception e)
        {
            return;
        }
        switch (modul) {
            case 1:
            try {
                Main.tile = new Tile[500 + Integer.parseInt(text)];
                Main.setObject();
                Main.initWorld("0");
                Main.loadObject("0");
                Main.restartWellenKI();
            } catch (Exception e) {
            }
            break;
            case 2:
                try {
                    Main.effect = new Effects[Integer.parseInt(text)];
                    Main.setObject();
                    Main.initWorld("0");
                    Main.loadObject("0");
                    Main.restartWellenKI();
                } catch (Exception e) {
                }
                break;
        }
    }

    @Override
    public void canceled() {

    }
}
