/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer;

import com.vobis.tankengineer.entity.*;
import com.vobis.tankengineer.gui.GuiBase;
import com.vobis.tankengineer.gui.GuiStartMenu;
import com.vobis.tankengineer.level.Level;
import com.vobis.tankengineer.level.LevelType;
import com.vobis.tankengineer.render.ModelLoader;
import com.vobis.tankengineer.render.Screen;
import org.newdawn.slick.*;

import java.util.Random;
import java.util.Stack;

/**
 * @author Shaun
 */
public class TankEngineer extends BasicGame {

    private static final String GAME_TITLE = "Tank Engineer v0.1";
    private static final int TARGET_FPS = 100;
    private static final int TARGET_SPF = 1000 / TARGET_FPS;
    private static final int SCROLL_BOUNDS = 20;

    public static TankEngineer gameInstance;

    public Stack<GuiBase> guiStack = new Stack<>();
    public Entity hovered;
    public Level gameWorld;
    public Entity trackedEntity;
    public Screen screen;
    public int time;
    public Input input;
    public GameContainer container;
    public Random rand;

    public TankEngineer() {
        super(GAME_TITLE);
        gameInstance = this;
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        ModelLoader.loadAllModels();

        container.setMultiSample(2);
        container.setAlwaysRender(true);

        screen = new Screen();
        this.container = container;
        this.input = container.getInput();

        this.rand = new Random();
        screen.wantedScale = .75f;

        setGUI(new GuiStartMenu());
    }

    public void setGUI(GuiBase gui) {
        if (!guiStack.isEmpty()) {
            guiStack.pop();
        }

        if (gui != null) {
            guiStack.push(gui);
        }
    }

    public void addGUI(GuiBase gui) {
        if (gui != null) {
            guiStack.push(gui);
        }
    }

    public void popGui() {
        if (!guiStack.isEmpty()) {
            guiStack.pop();
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        time += delta;
        //Fix bug where nodes are being added to the wrong vehicle
        if (time >= TARGET_SPF) {
            time = 0;

            GuiBase gui = null;

            if (!guiStack.isEmpty()) {
                gui = guiStack.peek();
                gui.update();
            }

            if (gui == null || !gui.blocksInput()) {

                if (trackedEntity != null) {
                    screen.xScroll = trackedEntity.pos.x;
                    screen.yScroll = trackedEntity.pos.y;

                    if (trackedEntity.isDead()) {
                        trackedEntity = null;
                    }
                } else {
                    int mx = input.getMouseX();
                    int my = input.getMouseY();

                    if (mx < SCROLL_BOUNDS) {
                        screen.xScroll -= 10 / screen.scale;
                    } else if (mx > container.getWidth() - SCROLL_BOUNDS) {
                        screen.xScroll += 10 / screen.scale;
                    }

                    if (my < SCROLL_BOUNDS) {
                        screen.yScroll -= 10 / screen.scale;
                    } else if (my > container.getHeight() - SCROLL_BOUNDS) {
                        screen.yScroll += 10 / screen.scale;
                    }
                }

                if (Math.abs(screen.wantedScale - screen.scale) < .001f) {
                    screen.scale = screen.wantedScale;
                } else {
                    screen.scale += (screen.wantedScale - screen.scale) * .05f;
                }

                if (gameWorld != null) {
                    hovered = gameWorld.getEntityAtPoint(screen.toWorldSpace(input.getMouseX(), input.getMouseY()));

                    if (hovered != null) {

                        gameWorld.hovered = hovered;

                        if (gameWorld.getLevelType() == LevelType.NORMAL) {
                            hovered = hovered.getTopLevelParent();
                        }

                        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                            Entity topLevel = hovered.getTopLevelParent();

                            if (gameWorld.getLevelType() != LevelType.TANK_CREATOR) {
                                if (topLevel instanceof EntityModular) {
                                    trackedEntity = topLevel;
                                }
                            }
                        }
                    }

                    if (input.isKeyPressed(Input.KEY_ESCAPE)) {
                        if (gameWorld.getLevelType() != LevelType.TANK_CREATOR) {
                            trackedEntity = null;
                        }
                    }
                }
            }

            screen.update(this);

            if (gameWorld != null) {

                gameWorld.update();

                switch (gameWorld.getLevelType()) {
                    case EDITOR:
                        screen.wantedScale = .5f;
                        break;
                    case NORMAL:
                        screen.wantedScale = .75f;
                        break;
                    case TANK_CREATOR:
                        screen.wantedScale = 1.25f;
                        break;
                }
            }
        }
    }

    public Vector2 getMousePosition() {
        return new Vector2(input.getMouseX(), input.getMouseY());
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        if (!guiStack.isEmpty()) {
            guiStack.peek().mouseMoved(newx, newy);
        }
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if (!guiStack.isEmpty()) {
            guiStack.peek().mouseClicked(x, y, clickCount);
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        screen.render(g, this);
    }

    public void setGameWorld(Level gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void initTestWorld() {
        gameWorld = new Level();

        EntityTank enemy = new EntityTank();
        enemy.setModel(ModelLoader.loadModel("Alien_Body"));
        enemy.setSpeed(2f);
        gameWorld.addEntity(enemy, 600, 100);

        EntityCannon enemyCannon = new EntityCannon();
        enemyCannon.setModel(ModelLoader.loadModel("Alien_Cannon"));
        enemyCannon.setTurnSpeed(10f);
        enemy.insertModule(0, enemyCannon);

        EntityTargetFinder targetFinder2 = new EntityTargetFinder();
        enemy.insertModule(2, targetFinder2);

        EntityLocator enemyLocator = new EntityLocator();
        enemyCannon.insertModule(0, enemyLocator);

        EntityLaser enemyLaser = new EntityLaser();
        enemyCannon.insertModule(1, enemyLaser);
    }
}
