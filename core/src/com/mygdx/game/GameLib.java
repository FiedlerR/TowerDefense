package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Rafael on 25.01.2017.
 */
abstract public class GameLib {

    public static String loadFile(String pathName) {
        FileHandle file = Gdx.files.local(pathName);
        System.out.println("Load:"+pathName);
        try{
            return file.readString();
        }catch (Exception e){
            System.err.println("FileError("+pathName+")");
            return  "";
        }
    }

    public static String loadFileInternal(String pathName) {
        FileHandle file = Gdx.files.internal(pathName);
        System.out.println("Load(internal):"+pathName);
        try{
            return file.readString();
        }catch (Exception e){
            System.err.println("FileError("+pathName+")");
            return  "";
        }
    }

    static void saveFile(String pathName, String data) {
            FileHandle file = Gdx.files.local(pathName);
            file.writeString(data, false);
    }

    static void saveFile(String pathName, String data,String world) {
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            Preferences prefs = Gdx.app.getPreferences("gameSave");
            prefs.putString(world, data);
            prefs.flush();
        }else {
            FileHandle file = Gdx.files.local(pathName);
            file.writeString(data, false);
        }
    }

    private void addToFile(String pathName, String data) {
        FileHandle file = Gdx.files.local(pathName);
        file.writeString(data, true);
    }

    protected static String extractInfo(String text, String string1, String string2) {
        if (!(text.contains(string1)) || !(text.contains(string2))) {
            return "none";
        }
        return text.substring(text.indexOf(string1) + string1.length(), text.indexOf(string2, text.indexOf(string1) + string1.length()));
    }

    protected static Boolean extractInfoBoolean(String text, String string1, String string2) {
        String i = "";
        try {
            i = text.substring(text.indexOf(string1) + string1.length(), text.indexOf(string2, text.indexOf(string1) + string1.length()));
        } catch (StringIndexOutOfBoundsException e) {

        }
        if (i.equalsIgnoreCase("true")) {
            return true;
        } else {
            return false;
        }
    }

    protected static int extractInfoInt(String text, String string1, String string2) {
        if (!(text.contains(string1)) || !(text.contains(string2))) {
            return 0;
        }
        try {
            return Integer.parseInt(text.substring(text.indexOf(string1) + string1.length(), text.indexOf(string2, text.indexOf(string1) + string1.length())));
        } catch (Exception e) {
            return -1;
        }
    }

    public static float extractInfoFloat(String text, String string1, String string2) {
        if (!(text.contains(string1)) || !(text.contains(string2))) {
            return 0;
        }
        try {
            return Float.parseFloat(text.substring(text.indexOf(string1) + string1.length(), text.indexOf(string2, text.indexOf(string1) + string1.length())));
        } catch (Exception e) {
            return -1;
        }
    }

    public static long extractInfoLong(String text, String string1, String string2) {
        if (!(text.contains(string1)) || !(text.contains(string2))) {
            return 0;
        }
        try {
            return Long.parseLong(text.substring(text.indexOf(string1) + string1.length(), text.indexOf(string2, text.indexOf(string1) + string1.length())));
        } catch (Exception e) {
            return -1;
        }
    }

}
