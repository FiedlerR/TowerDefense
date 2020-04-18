package com.mygdx.game;

import box2dLight.RayHandler;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Main extends ApplicationAdapter {
    public static boolean loadLevel = false;
    static boolean continueGame = false;
    public static boolean isLigthEnable;
    public static int health = 100;
    public static Enemy[] enemy = new Enemy[150];
    public static Ammo[] ammo = new Ammo[30];
    public static Tower[] tower = new Tower[200];
    private static OrthographicCamera camera;
    public static Effects[] effect;
    public static int timer = 3600;
    public static SpriteBatch batch;
    public static String world = "0";
    public static int screen = -1;
    static int killScore;
    public static long setScore;
    private static boolean buildMode = false;
    static int[][] pathFlags = new int[50][2];
    static Tile[] tile;
    static int lastFlag = 0;
  //  static Texture[] background = new Texture[302];
    static Texture[] guiElements = new Texture[19];
    static int lastBackgroundObject = 0;
    static Texture flag;
    static long wait = 0;
    static int welle = 0;
    private static int scoreChange = 0;
    static long score = 6000000;
    private static BackgroundEffects[] backgroundEffects = new BackgroundEffects[13];
    static int isSelected;
    static Sound[] sound = new Sound[30];
    static RayHandler rayHandler;
    //static FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    static BitmapFont menuFont;
    static BitmapFont siteTitle;
    static BitmapFont bigFont;
    static int loaded = 0;
    private static int mapWidth = 300;
    private static int mapHeight = 300;
    static ArrayList<LevelElements> level = new ArrayList<>();
    private static int[] unitType = new int[5];
    private static int type1 = 5;
    private static int type2 = 0;
    private static int type3 = 0;
    private static int type4 = 0;
    private static int type5 = 0;
    static GlyphLayout layout;
    static int[] xK = new int[20];
    static int[] yK = new int[20];
    static int[] value = new int[20];
    static float[] alpha = new float[20];
    private boolean buildPlace = false;
    private boolean lightOn = true;
    private float light = 1;
    private long touchedConter = 0;
    private boolean touche = true;
    private int worldX = 0;
    private int worldY = 0;
    private int actLevel = 0;
    private Button backMap;
    private float Y = 0;
    private int X = 1400;
    private float goUp = 10;
    private boolean side = false;
    private boolean GOUP = false;
    private Button optionButtons[] = new Button[5];
    private Button restart;
    private Button menu;
    private Button save;
    private int sin = 0;
    private Button exitCredits;
    private int gamestart = 3;
    private Button mainMenu;
    private Button speedButton;
    private Button forward;
    private Button backward;
    private static boolean nightDay;
    private int countWorld = 1;
    private int type = -1;
    private Button gui[] = new Button[4];
    private com.badlogic.gdx.physics.box2d.World w;
    public static int speedUp = 1;
    static TextureRegion[] textures  = new TextureRegion[302];

    static void setObject() {
        for (int i = 0; i < tile.length; i++) {
            tile[i] = new Tile();
        }
        for (int i = 0; i < ammo.length; i++) {
            ammo[i] = new Ammo();
        }
        for (int i = 0; i < effect.length; i++) {
            effect[i] = new Effects();
        }
        backgroundEffects[0] = new BackgroundEffects(-64, 700, -45, 1, 5, 240, 0);
        backgroundEffects[1] = new BackgroundEffects(-64, 764, -45, 1, 5, 240, 0);
        backgroundEffects[2] = new BackgroundEffects(-0, 626, -45, 1, 5, 240, 0);
        for (int i = 0; i < 13; i++) {
            backgroundEffects[i] = new BackgroundEffects(-200, 0, 0, 0, 0, 0, 0);
        }
    }

    public static void loadGame(String file) {//load towers enemys score tiles
        String data = "";
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            Preferences prefs = Gdx.app.getPreferences("gameSave");
            data = prefs.getString(file, "null");
        } else {
            try {
                data = GameLib.loadFile("data/" + file + ".save");
            } catch (Exception e) {
                return;
            }
        }
        health = GameLib.extractInfoInt(data, "<health>", "</health>");

        for (int i = 0; i < tower.length; i++) {
            String dat = GameLib.extractInfo(data, "<tower" + i + ">", "</tower" + i + ">");
            if (dat.equals("none")) {
                break;
            }
            tower[i] = new Tower();
            tower[i].type = GameLib.extractInfoInt(dat, "<type>", "</type>");

            tower[i].x = GameLib.extractInfoInt(dat, "<x>", "</x>");
            tower[i].y = GameLib.extractInfoInt(dat, "<y>", "</y>");
            tower[i].firerate = GameLib.extractInfoInt(dat, "<firerate>", "</firerate>");
            tower[i].fireRate = GameLib.extractInfoInt(dat, "<fireRate>", "</fireRate>");
            tower[i].zielAngle = GameLib.extractInfoInt(dat, "<zielAngle>", "</zielAngle>");
            tower[i].tower = GameLib.extractInfoInt(dat, "<tower>", "</tower>");
            tower[i].h = GameLib.extractInfoInt(dat, "<h>", "</h>");
            tower[i].kills = GameLib.extractInfoInt(dat, "<kills>", "</kills>");
            tower[i].maxHealth = GameLib.extractInfoInt(dat, "<maxHealth>", "</maxHealth>");
            tower[i].health = GameLib.extractInfoInt(dat, "<health>", "</health>");
        }

        score = GameLib.extractInfoLong(data, "<score>", "</score>");
        type1 = GameLib.extractInfoInt(data, "<type1>", "</type1>");
        type2 = GameLib.extractInfoInt(data, "<type2>", "</type2>");
        type3 = GameLib.extractInfoInt(data, "<type3>", "</type3>");
        type4 = GameLib.extractInfoInt(data, "<type4>", "</type4>");
        type5 = GameLib.extractInfoInt(data, "<type5>", "</type5>");
        unitType = new int[type1 + type2 + type3 + type4 + type5];
        for (int i = 0; i < unitType.length; i++) {
            unitType[i] = GameLib.extractInfoInt(data, "<unitType" + i + ">", "</unitType" + i + ">");
        }
        setEnemys();
    }

    static void initWorld(String world) {
        lastBackgroundObject = 0;
        lastFlag = 0;
        for (int i = 0; i < tower.length; i++) {
            tower[i] = new Tower(-200, -200, -1, -1);
        }
        for (int i = 0; i < enemy.length; i++) {
            enemy[i] = new Enemy(-200, -200, -1, 0, 0);
        }
        for (int i = 0; i < effect.length; i++) {
            effect[i] = new Effects();
        }
        for (int i = 0; i < ammo.length; i++) {
            ammo[i] = new Ammo();
        }
        String line = GameLib.loadFileInternal("data/world/" + world + ".xml");
        System.out.println("data/world/" + world + ".xml");
        try {
            lastFlag = GameLib.extractInfoInt(line, "<lastFlag>", "</lastFlag>");
        } catch (Exception e) {
        }
        for (int i = 0; ; i++) {
            if (line.indexOf("<flagX" + i + ">") != -1) {
                pathFlags[i][0] = GameLib.extractInfoInt(line, "<flagX" + i + ">", "</flagX" + i + ">");
                pathFlags[i][1] = GameLib.extractInfoInt(line, "<flagY" + i + ">", "</flagY" + i + ">");
            } else {
                break;
            }
        }
        mapHeight = GameLib.extractInfoInt(line, "<mapHeight>", "</mapHeight>");
        mapWidth = GameLib.extractInfoInt(line, "<mapWidth>", "</mapWidth>");
        rayHandler.setAmbientLight(GameLib.extractInfoFloat(line, "<ambientLight>", "</ambientLight>"));
        if (!world.equals("0")) {
            nightDay = GameLib.extractInfoBoolean(line, "<dayNight>", "<dayNight>");
        }
        score = GameLib.extractInfoLong(line, "<startScore>", "</startScore>");
    }

    static int getMouseX() {
        return (int) camera.unproject(new Vector3(Gdx.input.getX(), 0, 0)).x;
    }

    static int getMouseY() {
        return (int) camera.unproject(new Vector3(0, Gdx.input.getY(), 0)).y;
    }

    public static void loadObject(String world) {
        String file = GameLib.loadFileInternal("data/world/" + world + ".bin");

        for (int i = 0; i < tile.length; i++) {
            tile[i] = new Tile();
            String input = GameLib.extractInfo(file, "<object" + i + ">", "</object" + i + ">");
            if (!input.equals("none")) {
                tile[i].type = GameLib.extractInfoInt(input, "<type>", "</type>");
                tile[i].x = GameLib.extractInfoInt(input, "<x>", "</x>");
                tile[i].y = GameLib.extractInfoInt(input, "<y>", "</y>");
                tile[i].buildPlace = GameLib.extractInfoBoolean(input, "<buildPlace>", "</buildPlace>");
                lastBackgroundObject = i;
            } else {
                tile[i].type = -1;
            }
        }
        //lastBackgroundObject = 0;
        /*for (int z = 0; z < tile.length; z++) {
            if (tile[z].type >= 0) {
                lastBackgroundObject++;
            }
        }*/
    }

    public static boolean rectangleCollision(double x1, double y1, double b1, double h1, double x2, double y2,
                                             double b2, double h2) {
        return !(x1 > x2 + b2 || x1 + b1 < x2 || y1 > y2 + h2 || y1 + h1 < y2);
    }

    static void printNumbers(String text, int x, int y, int zusatz, boolean change,int size) {
        int width = 0;
        for (int i = 0; i < text.length(); i++) {
            try {
                batch.draw(textures[276 + Integer.parseInt("" + text.charAt(i))], x + (i * size/2), y, size, size);
                width = x + (i * size/2) + size/2;
            } catch (Exception e) {
                return;
            }

        }
        if (zusatz != -1) {
            batch.draw(textures[zusatz], width, y, 64, 64);
        }
        if (scoreChange != 0 && change) {
            text = "" + scoreChange;
            if (scoreChange < 0) {
                text = text.replace("-", "");
                batch.draw(textures[300], width + 32, y, 64, 64);
            } else {
                batch.draw(textures[289], width + 32, y, 64, 64);
            }
            width += 64;
            for (int i = 0; i < text.length(); i++) {
                batch.draw(textures[276 + Integer.parseInt("" + text.charAt(i))], width + (i * 32), y, 64, 64);
            }
            scoreChange = 0;
        }
    }

    private static void setEnemys() {
        String line = GameLib.loadFileInternal("data/world/" + world + "_armyKI.xml");
        int startIndex = GameLib.extractInfoInt(line, "<startPath>", "</startPath>");
        int x = GameLib.extractInfoInt(line, "<startWidth>", "</startWidth>");
        int y = GameLib.extractInfoInt(line, "<startHeight>", "</startHeight>");
        enemy = new Enemy[unitType.length];
        for (int i = 0; i < unitType.length; i++) {
            int h = 0;
            if (x < pathFlags[startIndex][0]) {
                x -= 64;
                h = 0;
            } else if (x > pathFlags[startIndex][0]) {
                x += 64;
                h = 180;
            }
            if (y < pathFlags[startIndex][1]) {
                y -= 64;
                h = 90;
            } else if (y > pathFlags[startIndex][1]) {
                y += 64;
                h = 270;
            }
            enemy[i] = new Enemy(x, y, unitType[i], startIndex, h);
        }
    }

    public static void restartWellenKI() {
        unitType = new int[5];
        type1 = 5;
        type2 = 0;
        type3 = 0;
        type4 = 0;
        type5 = 0;
        for (int i = 0; i < unitType.length; i++) {
            unitType[i] = 1;
        }
        setEnemys();
    }

    void drawCenterText(BitmapFont font, String text, int x, int y, int width) {
        Main.layout.setText(font, text, Color.WHITE, width, Align.center, false);
        font.draw(batch, layout, x, y);
    }

    void drawPointLabel() {
        for (int i = 0; i < yK.length; i++) {
            if (alpha[i] >= 0) {
                Main.layout.setText(menuFont, "+" + value[i], new Color(1, 1, 1, alpha[i]), 20, Align.center, false);
                menuFont.draw(batch, layout, xK[i], yK[i]);
                yK[i]++;
                alpha[i] -= 0.01;
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void create() {
        rayHandler = new RayHandler(w);
        rayHandler.setAmbientLight(1.0f);
        rayHandler.setBlur(true);
        rayHandler.setBlurNum(8);
        setConfig();
        setGUI();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1366, 768);
        //camera.setToOrtho(false, 1920, 1080);
        batch = new SpriteBatch(4000);
        Loader.loadFont();
    }

    private void setConfig() {
        String input = GameLib.loadFile("data/config.xml");
        effect = new Effects[GameLib.extractInfoInt(input, "<effectCache>", "</effectCache>")];
        tile = new Tile[500 + GameLib.extractInfoInt(input, "<tileCache>", "</tileCache>")];
        isLigthEnable = GameLib.extractInfoBoolean(input, "<lightEffect>", "</lightEffect>");
        nightDay = GameLib.extractInfoBoolean(input, "<nightDay>", "</nightDay>");
    }

    private void setMusic(String path, float volume, boolean loop) {
        Music music = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.play();
        music.setVolume(volume);
        music.setLooping(loop);
    }

    private void setGUI() {
        gui[0] = new Button(593, 385, 220, 65, 0, "Play");
        gui[1] = new Button(593, 285, 220, 65, 0, "Options");
        gui[2] = new Button(593, 185, 220, 65, 0, "Credits");
        gui[3] = new Button(593, 85, 220, 65, 2, "Exit");
        backMap = new Button(20, -805, 215, 65, 2, "Back");
        mainMenu = new Button(1070, 700, 280, 65, 2, "Mainmenu");//Haptmenü
        menu = new Button(383, 234, 300, 65, 2, "Menu");//menu
        restart = new Button(683, 234, 300, 65, 0, "Restart");// neustart
        speedButton = new Button(1285, 620, 65, 65, 15, "", 0);
        backward = new Button(0, -420, 64, 64, 9, "");
        forward = new Button(1302, -420, 64, 64, 7, "");
        save = new Button(1070, 620, 200, 64, 0, "Save");//speichern
        exitCredits = new Button(20, 5, 215, 65, 2, "Back");

        optionButtons[0] = new Button(483, 515, 200, 50, 0, "change");
        optionButtons[1] = new Button(483, 415, 200, 50, 0, "change");

        if (isLigthEnable) {
            optionButtons[2] = new Button(583, 315, 200, 50, 0, "enable");
        } else {
            optionButtons[2] = new Button(583, 315, 200, 50, 2, "disable");
        }
        if (nightDay) {
            optionButtons[3] = new Button(583, 215, 200, 50, 0, "enable");
        } else {
            optionButtons[3] = new Button(583, 215, 200, 50, 2, "disable");
        }
    }

    @Override
    public void resize(int i, int i1) {
        camera.update();
    }

    @Override
    public void render() {
        for (int k = 0; k < speedUp; k++) {
            if (gamestart == 1) {
                Loader.loadTexture(302);
                Loader.loadGui();
                Loader.loadLevel();
                Loader.loadSound();
                setObject();
                initWorld("0");
                loadObject("0");
                restartWellenKI();
                sound[2].loop();
                setMusic("music/DST-TowerDefenseTheme_1.mp3", 1.5f, true);
                gamestart = 0;
            }
            if (gamestart == 0) {
                globalLight();
                Gdx.gl.glClearColor(0, 0, 0.2f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                camera.update();
                batch.setProjectionMatrix(camera.combined);
                if (!loadLevel && (screen == 0 || buildMode)) {
                    handleInput();
                }
                batch.begin();
                if (!loadLevel) {
                    processBackgroundEffects();
                    drawBackground();
                    if (!buildMode) {
                        for (int i = 0; i < effect.length; i++) {
                            effect[i].effectPorcess();
                        }
                        checkAllEnemys();
                        processObjects();
                    } else {
                        buildInterface();
                    }
                    if (health <= 0) {
                        health = 0;
                    }
                } else {
                    loadScreen();
                }
                batch.end();
                if (isLigthEnable || nightDay) {
                    rayHandler.setCombinedMatrix(camera);
                    rayHandler.updateAndRender();
                }
                batch.begin();
                if (screen == 0 && !loadLevel) {
                    drawPointLabel();
                    if (isSelected != -1) {
                        tower[isSelected].drawMenu();
                        drawButtonLight();
                    }
                    printNumbers("" + score, worldX + 10, worldY + 700, 287, true,64);
                    printNumbers("" + health, worldX + 10, worldY + 636, 286, false,64);
                    if (mainMenu.clicked()) {
                        speedUp = 1;
                        speedButton.setType(speedUp == 8 ? 17 : 15);
                        screen = -1;
                        setConfig();
                        resetGUI();
                    }
                    if (speedButton.clicked()) {
                        speedUp = speedUp == 8 ? 1 : 8;
                        speedButton.setType(speedUp == 8 ? 17 : 15);
                    }
                    if (!buildMode) {
                        mainMenu.processButton();
                        speedButton.processButton();
                        save.processButton();
                    }
                    if (save.clicked()) {
                        saveGame();
                    }
                    if (health == 0) {
                        speedUp = 1;
                        speedButton.setType(speedUp == 8 ? 17 : 15);
                        drawGameOverPanel();
                    }
                } else if (screen == -1) {
                    drawMainMenu();
                } else if (screen == -2) {
                    drawMapSelect();
                } else if (screen == -3) {
                    drawOptions();
                } else if (screen == -4) {
                    drawCredits();
                }
               // GameMethods.checkTilemap(textures);
                batch.end();
            } else {
                batch.begin();
                menuFont.draw(Main.batch, "Loading,Please wait...", 480, 384);//Laden,Bitte warten...
                batch.end();
                batch.setProjectionMatrix(camera.combined);
                camera.update();
                gamestart--;
            }
        }
    }

    private void buildInterface() {
        if (type > 0) {
            batch.draw(textures[type], getMouseX(), getMouseY(), 64, 64);
        }
        if (type == -2) {
            for (int i = 0; i < lastFlag; i++) {
                batch.draw(flag, pathFlags[i][0], pathFlags[i][1]);
                printNumbers("" + i, pathFlags[i][0], pathFlags[i][1] - 64, -1, false,64);
            }
        }
    }

    private void checkAllEnemys() {
        boolean test = false;
        for (int i = 0; i < enemy.length; i++) {
            enemy[i].pathProcess();
            if (enemy[i].type != -1) {
                test = true;
            }
        }
        if (!test) {
            wellenKI();
        }
    }

    private void processObjects() {
        for (int i = 0; i < tower.length; i++) {
            if (tower[i].type != -1) {
                tower[i].towerProcess();
            }
        }
        for (int i = 0; i < ammo.length; i++) {
            ammo[i].ammoProcess();
        }
        for (Tower x:tower) {
            if (x.type != -1) {
                x.drawTowerInformation();
            }
        }
        for (int i = 0; i < 13; i++) {
            Main.batch.draw(Main.textures[292 + backgroundEffects[i].type], backgroundEffects[i].x + 64, backgroundEffects[i].y + 64, 32, 32, 64, 64, 1, 1, backgroundEffects[i].h);
        }
        for (int i = 0; i < 13; i++) {
            backgroundEffects[i].backgroundEffectsProcess();
        }
    }

    private void drawCredits() {
        batch.draw(guiElements[6], 20, 90, 1326, 650);
        drawCenterText(siteTitle, "Credits", 0, 700, 1366);
        drawCenterText(menuFont, "Programmer:", 0, 600, 1366);
        drawCenterText(menuFont, "Rafael Fiedler", 0, 560, 1366);
        drawCenterText(menuFont, "Sprites and Graphics:", 0, 500, 1366);
        drawCenterText(menuFont, "Kenney (www.kenney.nl)", 0, 460, 1366);
        drawCenterText(menuFont, "Music:", 0, 400, 1366);
        drawCenterText(menuFont, "DST (Opengameart.org)", 0, 360, 1366);
        drawCenterText(menuFont, "Sounds:", 0, 300, 1366);
        drawCenterText(menuFont, "Luke.RUSTLTD (Opengameart.org)", 0, 260, 1366);
        drawCenterText(menuFont, "Library:", 0, 200, 1366);
        drawCenterText(menuFont, "LibGdx(+Box2d+freeType)", 0, 160, 1366);
        exitCredits.processButton();
        if (exitCredits.clicked()) {
            screen = -1;
        }
    }

    private void saveGame() {//save towers enemys score tiles
        String data = "<saveData>";
        data += "<health>" + health + "</health>";
        for (int i = 0; i < tower.length; i++) {
            data += "<tower" + i + ">";
            data += "<type>" + tower[i].type + "</type>";
            data += "<x>" + tower[i].x + "</x>";
            data += "<y>" + tower[i].y + "</y>";
            data += "<fireRate>" + tower[i].fireRate + "</fireRate>";
            data += "<firerate>" + tower[i].firerate + "</firerate>";
            data += "<zielAngle>" + tower[i].zielAngle + "</zielAngle>";
            data += "<tower>" + tower[i].tower + "</tower>";
            data += "<h>" + tower[i].h + "</h>";
            data += "<kills>" + tower[i].kills + "</kills>";
            data += "<maxHealth>" + tower[i].maxHealth + "</maxHealth>";
            data += "<health>" + tower[i].health + "</health>";
            data += "</tower" + i + ">";
        }

        data += "<score>" + score + "</score>";
        data += "<type1>" + type1 + "</type1>";
        data += "<type2>" + type2 + "</type2>";
        data += "<type3>" + type3 + "</type3>";
        data += "<type4>" + type4 + "</type4>";
        data += "<type5>" + type5 + "</type5>";
        for (int i = 0; i < unitType.length; i++) {
            data += "<unitType" + i + ">" + unitType[i] + "</unitType" + i + ">";
        }
        data += "</saveData>";
        GameLib.saveFile("data/" + world + ".save", data, world);
    }

    private void resetGUI() {
        gui[0].setPosition(593, 385);
        gui[1].setPosition(593, 285);
        gui[2].setPosition(593, 185);
        gui[3].setPosition(593, 85);
        backMap.setPosition(20, -805);
        Y = 0;
        actLevel = 0;
        backward.y = -420;
        forward.y = -420;
        for (int i = 0; i < level.size(); i++) {
            level.get(i).setPosition(20 + 1366 * i, -740);
        }
        welle = 0;
        world = "0";
        initWorld("0");
        loadObject("0");
        restartWellenKI();
    }

    private void loadScreen() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        menuFont.draw(Main.batch, "Loading,Please wait...", 483, 384);//Laden,Bitte warten...
        if (loaded < 33) {
            Main.initWorld(world);
            loaded = 33;
        } else if (loaded < 66) {
            loadObject(world);
            if (continueGame) {
                Main.loadGame(world);
            }
            loaded = 66;
        } else {
            if (!continueGame) {
                restartWellenKI();
                health = 100;
            }
            loaded = 100;
        }
        if (loaded == 100) {
            continueGame = false;
            loadLevel = false;
        }
    }

    private void processBackgroundEffects() {
        if (timer == 3600) {
            timer = 0;
            backgroundEffects[0] = new BackgroundEffects(-128, 700, -45, 1, 5, 240, -1);
            backgroundEffects[1] = new BackgroundEffects(-128, 764, -45, 1, 5, 240, -1);
            backgroundEffects[2] = new BackgroundEffects(-128, 626, -45, 1, 5, 240, -1);

            int random = (int) ((Math.random() * 600) + 0);
            backgroundEffects[3] = new BackgroundEffects(-128, random, 0, 1, 1, 1000, -1);
            backgroundEffects[4] = new BackgroundEffects(-192, random + 64, 0, 1, 1, 1000, -1);
            backgroundEffects[5] = new BackgroundEffects(-192, random - 64, 0, 1, 1, 1000, -1);

            backgroundEffects[6] = new BackgroundEffects((int) ((Math.random() * 1300) - 800), -500, 45, 2, 3, 2000, 1);
            random = (int) ((Math.random() * 300) + 0);
            backgroundEffects[7] = new BackgroundEffects(1464 + (int) ((Math.random() * 1000) + 0), 100 + random, 180, 1, 2, 2700, -1);
            backgroundEffects[8] = new BackgroundEffects(1464 + (int) ((Math.random() * 1000) + 0), 400 + random / 2, 180, 1, 2, 2700, -1);
            backgroundEffects[9] = new BackgroundEffects(1464 + (int) ((Math.random() * 1000) + 0), 600 + random / 2, 180, 1, 2, 2700, -1);

            if (welle >= 3) {
                random = (int) ((Math.random() * 300));
                backgroundEffects[10] = new BackgroundEffects(1464, random, 180, 2, 2, 240, 1);
                backgroundEffects[11] = new BackgroundEffects(1464, 100 + random, 180, 2, 2, 240, 1);
                backgroundEffects[12] = new BackgroundEffects(1464, 200 + random, 180, 2, 2, 240, 1);
            }
        } else {
            timer++;
        }
    }

    private void globalLight() {
        if (nightDay) {
            rayHandler.setAmbientLight(light);
            if (lightOn) {
                light -= 0.0005;
            } else {
                light += 0.0005;
            }
            if (light <= 0.1) {
                lightOn = false;
            }
            if (light >= 1) {
                lightOn = true;
            }
        }
    }

    private void handleInput() {
        if (buildMode) {
            if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                for (int i = 0; i < tile.length; i++) {
                    if (tile[i].type != -1 && rectangleCollision(getMouseX(), getMouseY(), 1, 1, tile[i].x, tile[i].y, 64, 64)) {
                        for (int z = i; z < tile.length - 1; z++) {
                            tile[z] = tile[z + 1];
                        }
                        //tile[i].type = -1;
                        lastBackgroundObject--;
                        break;
                    }
                }
            }
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                if (type > 0 && System.currentTimeMillis() - wait >= 300) {
                    tile[lastBackgroundObject] = new Tile(getMouseX(), getMouseY(), type, buildPlace);
                    lastBackgroundObject++;
                    System.out.println("TileSet" + lastBackgroundObject);
                    wait = System.currentTimeMillis();
                }
                if (type == -2 && System.currentTimeMillis() - wait >= 300 && lastFlag < pathFlags.length) {
                    pathFlags[lastFlag][0] = (getMouseX() / 64) * 64;
                    pathFlags[lastFlag][1] = (getMouseY() / 64) * 64;
                    lastFlag++;
                    wait = System.currentTimeMillis();
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if (type + 1 < textures.length && System.currentTimeMillis() - wait >= 200) {
                    type++;
                    System.out.println("Tile:"+type);
                    wait = System.currentTimeMillis();
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.TAB) && buildMode && System.currentTimeMillis() - wait >= 200) {// bebaubarer Platz
                buildPlace = buildPlace ? false : true;
                System.out.println("BuildPlace: " + buildPlace);
                wait = System.currentTimeMillis();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.E) && System.currentTimeMillis() - wait >= 300) {//nächste map
                countWorld++;
                System.out.println(countWorld);
                world = "world_" + countWorld;
                lastBackgroundObject = 0;
                lastFlag = 0;
                for (int i = 0; i < enemy.length; i++) {
                    enemy[i] = new Enemy(-200, -200, -1, 0, 0);
                }
                for (int i = 0; i < ammo.length; i++) {
                    ammo[i] = new Ammo();
                }
                for (int i = 0; i < tile.length; i++) {
                    tile[i].type = -1;
                }
                wait = System.currentTimeMillis();
                System.out.println(world);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.N)) {//alles löschen
                lastBackgroundObject = 0;
                lastFlag = 0;
                for (int i = 0; i < enemy.length; i++) {
                    enemy[i] = new Enemy(-200, -200, -1, 0, 0);
                }
                for (int i = 0; i < ammo.length; i++) {
                    ammo[i] = new Ammo();
                }
                for (int i = 0; i < tile.length; i++) {
                    tile[i].type = -1;
                }
                for (int i = 0; i < pathFlags.length; i++) {
                    pathFlags[i][0] = 0;
                    pathFlags[i][1] = 0;
                    lastFlag = 0;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {//map speichern
                if (System.currentTimeMillis() - wait >= 100) {
                    save(world + "", "bin", "", "data/world/");
                    wait = System.currentTimeMillis();
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && System.currentTimeMillis() - wait >= 200) {// einen  typ runter
                if (type - 1 > 0) {
                    type--;
                    System.out.println("Tile:"+type);
                    wait = System.currentTimeMillis();
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && System.currentTimeMillis() - wait >= 200) {//pfad setzen
                type = -2;
                wait = System.currentTimeMillis();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && System.currentTimeMillis() - wait >= 200) {//plaziermodus beenedn
                type = -1;
                wait = System.currentTimeMillis();
            }
        }

        boolean towerSelect = true;
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (System.currentTimeMillis() - wait >= 200) {
                towerSelect = false;
            }
            if (!buildMode) {
                handleButtonClick();

                for (int i = 0; i <= lastBackgroundObject; i++) {
                    if (System.currentTimeMillis() - wait >= 500 && System.currentTimeMillis() - touchedConter >= 500 && score >= 200) {
                        for (int z = 0; z < tower.length; z++) {
                            if (tower[z].type != -1 && rectangleCollision(getMouseX(), getMouseY(), 1, 1, tower[z].x, tower[z].y, 64, 64)) {
                                return;
                            }
                        }
                        for (Tile element : tile) {
                            //for (int k = 0; k < tile.length; k++) {
                            if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, element.x, element.y, 64, 64) && !element.buildPlace) {
                                return;
                            }
                        }
                        for (int z = 0; z < tower.length; z++) {
                            if (tower[z].type == -1) {
                                tower[z] = new Tower(getGrid(getMouseX())
                                        , getGrid(getMouseY()), 1, z);
                                score -= 200;

                                for (Tile element : tile) {
                                    if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, element.x, element.y, 64, 64) && element.type >= 130 &&  element.type <= 137) {
                                        element.type =-1;
                                    }
                                }
                                wait = System.currentTimeMillis();
                                return;
                            }
                        }
                        break;
                    }
                    if (System.currentTimeMillis() - wait >= 200 && !touche) {
                        if (i < tower.length && tower[i].type >= 1 && rectangleCollision(getMouseX(), getMouseY(), 1, 1, tower[i].x, tower[i].y, 64, 64)) {
                            isSelected = i;
                            wait = System.currentTimeMillis();
                            towerSelect = true;
                            break;
                        }
                    }
                }
            }

            if (!towerSelect && isSelected != -1) {
                isSelected = -1;
            }
            touche = true;
        } else {
            touchedConter = System.currentTimeMillis();
            touche = false;
        }

        mapControl();

    }

    private int getGrid(int pos) {
        return (pos / 64) * 64;
    }

    private void mapControl() {
        if (Gdx.input.getX() >= Gdx.graphics.getWidth() - 25 && worldX < mapWidth) {
            camera.translate(3, 0, 0);
            worldX += 3;
            camera.update();
        }
        if (Gdx.input.getX() < 25 && worldX > 0) {
            camera.translate(-3, 0, 0);
            worldX -= 3;
            camera.update();
        }
        if (Gdx.input.getY() >= Gdx.graphics.getHeight() - 25 && worldY > 0) {
            camera.translate(0, -3, 0);
            worldY -= 3;
            camera.update();
        }
        if (Gdx.input.getY() < 25 && worldY < mapHeight) {
            camera.translate(0, 3, 0);
            worldY += 3;
            camera.update();
        }
    }

    private void handleButtonClick() {
        if (Main.isSelected != -1 && System.currentTimeMillis() - wait >= 300) {
            int newX = 633;
            int newY = 0;
            if (tower[isSelected].health < tower[isSelected].maxHealth) {
                if (tower[isSelected].type == 1) {
                    if (score >= 400 && rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX + 100, newY, 100, 100)) {
                        tower[isSelected].type = 2;
                        tower[isSelected].firerate = tower[isSelected].firerate / 2;
                        tower[isSelected].maxHealth = 200;
                        tower[isSelected].health = tower[isSelected].maxHealth;
                        score -= 400;
                        wait = System.currentTimeMillis();
                    }
                    if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX, newY, 100, 100)) {
                        score += tower[isSelected].health * 2;
                        tower[isSelected].health = 0;
                        wait = System.currentTimeMillis();
                    }
                    if (score >= (tower[isSelected].maxHealth - tower[isSelected].health) && rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX - 100, newY, 100, 100)) {
                        score -= tower[isSelected].maxHealth - tower[isSelected].health;
                        tower[isSelected].health = tower[isSelected].maxHealth;
                        wait = System.currentTimeMillis();
                    }
                } else if (tower[isSelected].type == 2) {
                    if (score >= 600 && rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX + 100, newY, 100, 100)) {
                        tower[isSelected].type = 3;
                        tower[isSelected].firerate = tower[isSelected].firerate * 4;
                        tower[isSelected].maxHealth = 300;
                        tower[isSelected].health = tower[isSelected].maxHealth;
                        score -= 600;
                        wait = System.currentTimeMillis();
                    }
                    if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX, newY, 100, 100)) {
                        score += tower[isSelected].health * 2;
                        tower[isSelected].health = 0;
                        wait = System.currentTimeMillis();
                    }
                    if (score >= (tower[isSelected].maxHealth - tower[isSelected].health) && rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX - 100, newY, 100, 100)) {
                        score -= tower[isSelected].maxHealth - tower[isSelected].health;
                        tower[isSelected].health = tower[isSelected].maxHealth;
                        wait = System.currentTimeMillis();
                    }
                } else {
                    if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX + 50, newY, 100, 100)) {
                        score += tower[isSelected].health * 2;
                        tower[isSelected].health = 0;
                        wait = System.currentTimeMillis();
                    }
                    if (score >= (tower[isSelected].maxHealth - tower[isSelected].health) && rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX - 50, newY, 100, 100)) {
                        score -= tower[isSelected].maxHealth - tower[isSelected].health;
                        tower[isSelected].health = tower[isSelected].maxHealth;
                        wait = System.currentTimeMillis();
                    }
                }
            } else {
                if (tower[isSelected].type == 1) {
                    if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX - 50, newY, 100, 100)) {
                        score += tower[isSelected].health * 2;
                        tower[isSelected].health = 0;
                        wait = System.currentTimeMillis();
                    }
                    if (score >= 400 && rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX + 50, newY, 100, 100)) {
                        tower[isSelected].type = 2;
                        tower[isSelected].firerate = tower[isSelected].firerate / 2;
                        tower[isSelected].maxHealth = 200;
                        tower[isSelected].health = tower[isSelected].maxHealth;
                        score -= 400;
                        wait = System.currentTimeMillis();
                    }
                } else if (tower[isSelected].type == 2) {
                    if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX - 50, newY, 100, 100)) {
                        score += tower[isSelected].health * 2;
                        tower[isSelected].health = 0;
                        wait = System.currentTimeMillis();
                    }
                    if (score >= 600 && rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX + 50, newY, 100, 100)) {
                        tower[isSelected].type = 3;
                        tower[isSelected].firerate = tower[isSelected].firerate * 4;
                        tower[isSelected].maxHealth = 300;
                        tower[isSelected].health = tower[isSelected].maxHealth;
                        score -= 600;
                        wait = System.currentTimeMillis();
                    }
                } else if (tower[isSelected].type != -1) {
                    if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX, newY, 100, 100)) {
                        score += tower[isSelected].health * 2;
                        tower[isSelected].health = 0;
                        wait = System.currentTimeMillis();
                    }
                }
            }
        }
    }

    private void drawButtonLight() {
        int newX = 633;
        int newY = 0;
        if (tower[isSelected].health < tower[isSelected].maxHealth) {
            if (tower[isSelected].type == 1) {
                if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX + 100, newY, 100, 100)) {
                    batch.draw(textures[15], newX + 100, newY, 100, 100);
                    scoreChange = -400;
                }
                if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX, newY, 100, 100)) {
                    batch.draw(textures[17], newX, newY, 100, 100);
                    scoreChange = (+tower[isSelected].health * 2);
                }
                if (score >= (tower[isSelected].maxHealth - tower[isSelected].health) && rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX - 100, newY, 100, 100)) {
                    batch.draw(textures[16], newX - 100, newY, 100, 100);
                    scoreChange = -(tower[isSelected].maxHealth - tower[isSelected].health);
                }
            } else if (tower[isSelected].type == 2) {
                if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX + 100, newY, 100, 100)) {
                    batch.draw(textures[15], newX + 100, newY, 100, 100);
                    scoreChange = -600;
                }
                if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX, newY, 100, 100)) {
                    batch.draw(textures[17], newX, newY, 100, 100);
                    scoreChange = (+tower[isSelected].health * 2);
                }
                if (score >= (tower[isSelected].maxHealth - tower[isSelected].health) && rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX - 100, newY, 100, 100)) {
                    batch.draw(textures[16], newX - 100, newY, 100, 100);
                    scoreChange = -(tower[isSelected].maxHealth - tower[isSelected].health);
                }
            } else {
                if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX + 50, newY, 100, 100)) {
                    batch.draw(textures[17], newX + 50, newY, 100, 100);
                    scoreChange = (+tower[isSelected].health * 2);
                }
                if (score >= (tower[isSelected].maxHealth - tower[isSelected].health) && rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX - 50, newY, 100, 100)) {
                    batch.draw(textures[16], newX - 50, newY, 100, 100);
                    scoreChange = -(tower[isSelected].maxHealth - tower[isSelected].health);
                }
            }
        } else {
            if (tower[isSelected].type == 1) {
                if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX - 50, newY, 100, 100)) {
                    batch.draw(textures[17], newX - 50, newY, 100, 100);
                    scoreChange = (+tower[isSelected].health * 2);
                }
                if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX + 50, newY, 100, 100)) {
                    batch.draw(textures[15], newX + 50, newY, 100, 100);
                    scoreChange = -400;
                }
            } else if (tower[isSelected].type == 2) {
                if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX - 50, newY, 100, 100)) {
                    batch.draw(textures[17], newX - 50, newY, 100, 100);
                    scoreChange = (+tower[isSelected].health * 2);
                }
                if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX + 50, newY, 100, 100)) {
                    batch.draw(textures[15], newX + 50, newY, 100, 100);
                    scoreChange = -600;
                }
            } else {
                if (rectangleCollision(getMouseX(), getMouseY(), 1, 1, newX, newY, 100, 100)) {
                    batch.draw(textures[17], newX, newY, 100, 100);
                    scoreChange = (+tower[isSelected].health * 2);
                }
            }
        }
    }

    private void drawBackground() {
        for (Tile x : tile) {
            if (x.type != -1 && x.type != -2) {
              //  System.out.println(x.type);
                batch.draw(textures[x.type], x.x, x.y, 64, 64);
            }

        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    private void save(String name, String typ, String h, String path) {
        h += "<mapHeight>0</mapHeight> <mapWidth>0</mapWidth> <ambientLight>1</ambientLight> <dayNight>true</dayNight> <startScore>600</startScore>";
        h += "<lastFlag>" + lastFlag + "</lastFlag>";
        String obj = "";
        for (int i = 0; i < tile.length; i++) {
            obj += "<object" + i + ">";
            obj += "<type>" + tile[i].type + "</type>";
            obj += "<x>" + tile[i].x + "</x>";
            obj += "<y>" + tile[i].y + "</y>";
            obj += "<buildPlace>" + tile[i].buildPlace + "</buildPlace>";
            obj += "</object" + i + ">";
        }
        GameLib.saveFile(path + name + "." + typ, obj);
        System.out.println("save");
        for (int i = 0; i < lastFlag; i++) {
            h += "<flagX" + i + ">" + pathFlags[i][0] + "</flagX" + i + ">";
            h += "<flagY" + i + ">" + pathFlags[i][1] + "</flagY" + i + ">";
        }
        //h += "<lastArmy>0</lastArmy>";
        GameLib.saveFile(path + name + ".xml", h);
    }

    private void drawMainMenu() {
        if (GOUP) {
            if (gui[3].y < 985 && goUp > 0) {
                gui[0].y += goUp * Gdx.graphics.getDeltaTime();
                gui[1].y += goUp * Gdx.graphics.getDeltaTime();
                gui[2].y += goUp * Gdx.graphics.getDeltaTime();
                gui[3].y += goUp * Gdx.graphics.getDeltaTime();
                Y += goUp * Gdx.graphics.getDeltaTime();
                if (gui[3].y >= 985 && !side) {
                    screen = -2;
                    goUp = 600;
                } else if (gui[3].y >= 985) {
                    screen = -3;
                    goUp = -600;
                }
            } else if (gui[3].y > 85 && goUp < 0) {
                gui[0].y += goUp * Gdx.graphics.getDeltaTime();
                gui[1].y += goUp * Gdx.graphics.getDeltaTime();
                gui[2].y += goUp * Gdx.graphics.getDeltaTime();
                gui[3].y += goUp * Gdx.graphics.getDeltaTime();
                Y += goUp * Gdx.graphics.getDeltaTime();
            } else {
                side = false;
                goUp = 0;
                GOUP = false;
                Y = 0;
            }
        } else {
            if (gui[0].clicked()) {
                GOUP = true;
                goUp = 600;
            }
            if (gui[1].clicked()) {
                GOUP = true;
                side = true;
                goUp = 600;
            }
            if (gui[2].clicked()) {
                screen = -4;
            }
            if (gui[3].clicked()) {
                Texture.clearAllTextures(Gdx.app);
                Gdx.app.exit();
            }
        }
        for (Button button : gui) {
            button.processButton();
        }
        bigFont.draw(Main.batch, "Tower Defense", 20, 650 + Y);
    }

    private void drawMapSelect() {
        for (LevelElements element : level) {
            element.process();
        }
        if ((int) level.get(actLevel).x != 20) {
           // System.out.println(level.get(actLevel).x);
            double speed = Gdx.graphics.getDeltaTime() * 1000;
            if (level.get(actLevel).x > 20) {
                for (LevelElements element : level) {
                    double deltaSpeed = (level.get(actLevel).x - speed) <= 20 ? (level.get(actLevel).x) - 20 : speed;
                    element.x -= deltaSpeed;
                    element.actPositions();
                }
            }
            if (level.get(actLevel).x < 20) {
                double deltaSpeed = (level.get(actLevel).x + speed) > 20 ? 20 - level.get(actLevel).x : speed;
                for (LevelElements element : level) {
                    element.x += deltaSpeed;
                    element.actPositions();
                }
            }
        } else {

            if (forward.clicked() && actLevel < level.size() - 1) {
                actLevel++;
            }
            if (backward.clicked() && actLevel > 0) {
                actLevel--;
            }
        }
        forward.x += (int) (Math.sin(Math.toRadians(sin)) * 10);
        backward.x -= (int) (Math.sin(Math.toRadians(sin)) * 10);
        if (actLevel > 0) {
            backward.processButton();
        }
        if (actLevel < level.size() - 1) {
            forward.processButton();
        }
        forward.x -= (int) (Math.sin(Math.toRadians(sin)) * 10);
        backward.x += (int) (Math.sin(Math.toRadians(sin)) * 10);
        sin += Gdx.graphics.getDeltaTime() * 300;
        if (sin >= 180) {
            sin = 0;
        }
        backMap.processButton();
        if (GOUP) {
            if (backMap.y < 5 && goUp > 0) {
                backMap.y += goUp * Gdx.graphics.getDeltaTime();
                forward.y += goUp * Gdx.graphics.getDeltaTime();
                backward.y += goUp * Gdx.graphics.getDeltaTime();
                for (LevelElements element : level) {
                    element.y += goUp * Gdx.graphics.getDeltaTime();
                    element.actPositions();
                }
            } else if (backMap.y > -805 && goUp < 0) {
                backMap.y += goUp * Gdx.graphics.getDeltaTime();
                forward.y += goUp * Gdx.graphics.getDeltaTime();
                backward.y += goUp * Gdx.graphics.getDeltaTime();
                for (LevelElements element : level) {
                    element.y += goUp * Gdx.graphics.getDeltaTime();
                    element.actPositions();
                }
                if (backMap.y < -805) {
                    screen = -1;
                }
            } else {
                goUp = 0;
                GOUP = false;
            }
        } else {
            backMap.processButton();
            if (backMap.clicked()) {
                goUp = -600;
                GOUP = true;
            }
        }
    }

    private void drawOptions() {
        if (X > 0 && side && goUp < 0) {
            X += goUp*Gdx.graphics.getDeltaTime();
            if (X == 0) {
                GOUP = false;
                side = false;
            }
        } else if (X < 1400 && side && goUp > 0) {
            X += goUp*Gdx.graphics.getDeltaTime();
            if (X >= 1400) {
                screen = -1;
                goUp = -600;
                side = false;
            }
        } else {
            side = false;
            GOUP = false;
        }
        batch.draw(guiElements[6], 20 + X, 90, 1326, 650);
        backMap.x += X;
        backMap.y += 810;
        if (backMap.clicked()) {
            goUp = +600;
            side = true;
            GOUP = true;
            saveOptions();
        }
        backMap.processButton();
        backMap.x -= X;
        backMap.y -= 810;
        drawCenterText(siteTitle, "Options", X, 700, 1366);
        for (int j = 0; j < 4; j++) {
            optionButtons[j].x += X;
            optionButtons[j].processButton();
            optionButtons[j].x -= X;
        }
        if (optionButtons[0].clicked()) {
            Gdx.input.getTextInput(new InputObject(1), "change max. Objects", "", "new value");
        }
        if (optionButtons[1].clicked()) {
            Gdx.input.getTextInput(new InputObject(2), "change max. Objects", "", "new value");
        }
        if (optionButtons[2].clicked()) {
            if (optionButtons[2].text.equalsIgnoreCase("enable")) {
                optionButtons[2] = new Button(583, 315, 200, 50, 2, "disable");
                isLigthEnable = false;
            } else {
                isLigthEnable = true;
                optionButtons[2] = new Button(583, 315, 200, 50, 0, "enable");
            }
            Main.setObject();
            Main.initWorld("0");
            Main.loadObject("0");
            restartWellenKI();
        }
        if (optionButtons[3].clicked()) {
            if (optionButtons[3].text.equalsIgnoreCase("enable")) {
                nightDay = false;
                rayHandler.setAmbientLight(1.0f);
                optionButtons[3] = new Button(583, 215, 200, 50, 2, "disable");
            } else {
                nightDay = true;
                optionButtons[3] = new Button(583, 215, 200, 50, 0, "enable");
            }
        }
        menuFont.draw(Main.batch, " objects: " + (tile.length - 500), 683 + X, 550);// max Objekte:
        menuFont.draw(Main.batch, " effetcs: " + (effect.length), 683 + X, 450);//
        drawCenterText(menuFont, "Maximum number of objects", X, 600, 1366);
        drawCenterText(menuFont, "Maximum number of effects", X, 500, 1366);
        drawCenterText(menuFont, "Lighteffects", X, 400, 1366);
        drawCenterText(menuFont, "Day and Night", X, 300, 1366);
    }

    private void saveOptions() {
        String config = "<?xml version = \"1.0\" encoding = \"UTF-8\"?>\n" +
                "<config>\n" +
                "\t<effectCache>" + (effect.length) + "</effectCache>\n" +
                "\t<tileCache>" + (tile.length - 500) + "</tileCache>\n" +
                "\t<lightEffect>" + isLigthEnable + "</lightEffect>\n" +
                "\t<nightDay>" + nightDay + "</nightDay>\n" +
                "</config>\n";
        GameLib.saveFile("data/config.xml", config);
    }

    private void drawGameOverPanel() {

        Main.batch.draw(Main.guiElements[6], 383, 234, 600, 300);
        drawCenterText(siteTitle, "Game Over", 0, 524, 1366);
        if (score > 100000) {
            setScore += 10000;
            score -= 10000;
        }
        if (score > 10000) {
            setScore += 1000;
            score -= 1000;
        }
        if (score > 1000) {
            setScore += 100;
            score -= 100;
        }
        if (score > 100) {
            setScore += 10;
            score -= 10;
        }
        if (score > 10) {
            setScore += 10;
            score -= 10;
        }
        if (score > 0) {
            setScore += 1;
            score -= 1;
        }
        drawCenterText(menuFont, "Score: " + setScore, 0, 360, 1366);
        drawCenterText(menuFont, "Kills: " + killScore, 0, 420, 1366);
        restart.processButton();
        menu.processButton();
        if (restart.clicked()) {
            saveScore();
            Main.loadLevel = true;
            Main.loaded = 0;
            Main.screen = 0;
            health = 100;
            welle = 0;
            setScore = 0;
        }
        if (menu.clicked()) {
            saveScore();
            speedUp = 1;
            speedButton.setType(15);
            screen = -1;
            setConfig();
            resetGUI();
            health = 100;
            setScore = 0;
        }
    }

    private void saveScore() {
        if (GameLib.extractInfoInt(GameLib.loadFile("data/world/" + world + "_playerDat.xml"), "<score>", "</score>") < (score + setScore)) {
            GameLib.saveFile("data/world/" + world + "_playerDat.xml", "<score>" + (score + setScore) + "</score>");
            for (LevelElements element : level) {
                //for (int i = 0; i < level.size(); i++) {
                if (element.name.equals(world)) {
                    element.score = setScore + score;
                }
            }
        }

    }

    private void wellenKI() {
        unitType = Arrays.copyOf(unitType, unitType.length + 2);
        System.arraycopy(unitType, 0, unitType, 2, unitType.length - 2);
        unitType[0] = 1;
        unitType[1] = 1;
        type1 += 2;
        if (type1 > 25) {
            type2 += 2;
            unitType = Arrays.copyOf(unitType, unitType.length + 2);
            System.arraycopy(unitType, 0, unitType, 2, unitType.length - 2);
            unitType[0] = 2;
            unitType[1] = 2;
            int random = (int) (Math.random() * unitType.length);
            if (unitType[random] < 2) {
                unitType[random] = 2;
                type2++;
            }
        }
        if (type2 > 25) {
            type3 += 2;
            unitType = Arrays.copyOf(unitType, unitType.length + 2);
            System.arraycopy(unitType, 0, unitType, 2, unitType.length - 2);
            unitType[0] = 3;
            unitType[1] = 3;
            int random = (int) (Math.random() * unitType.length);
            if (unitType[random] < 3) {
                unitType[random] = 3;
                type3++;
            }
        }
        if (type3 > 15) {
            type4++;
            unitType = Arrays.copyOf(unitType, unitType.length + 2);
            System.arraycopy(unitType, 0, unitType, 2, unitType.length - 2);
            unitType[0] = 4;
            unitType[1] = 4;
            int random = (int) (Math.random() * unitType.length);
            if (unitType[random] < 4) {
                unitType[random] = 4;
                type4++;
            }
        }
        if (type4 > 15) {
            type5++;
            unitType = Arrays.copyOf(unitType, unitType.length + 1);
            System.arraycopy(unitType, 0, unitType, 1, unitType.length - 1);
            unitType[0] = 5;
            unitType[(int) (Math.random() * unitType.length)] = 5;
            int random = (int) (Math.random() * unitType.length);
            if (unitType[random] < 5) {
                unitType[random] = 5;
                type5++;
            }
        }
        setEnemys();
    }


}

