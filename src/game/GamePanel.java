package game;

import game.objects.GameObject;
import game.objects.Mario;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener {

    private static final int CELL_SIZE = 16;
    private static final int MOVE_SPEED = 8;

    public ImageHelper img = new ImageHelper();

    private Timer redrawTimer;
    private Timer physicsTimer;

    private ArrayList<GameObject> gameObjects;
    private ArrayList<GameObject> deadObjects;

    private Mario mario;

    private int cameraX;

    public Clip themeSound;

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(ArrayList<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public ArrayList<GameObject> getDeadObjects() {
        return deadObjects;
    }

    public Timer getPhysicsTimer() {
        return physicsTimer;
    }

    public int getCameraX() {
        return cameraX;
    }

    public void updateCamera(int increment){
        cameraX += increment;
    }

    private void drawGameObject(Graphics2D g2d,GameObject go) {
        float zoomFactor = this.getHeight() / 176f;

        g2d.drawImage(go.getSprite(),
                (int)((go.getX()-cameraX)*zoomFactor),
                (int)((10*CELL_SIZE-go.getY())*zoomFactor),
                (int)(go.getWidth()*zoomFactor),
                (int)(go.getHeight()*zoomFactor),
                null);
    }

    public void drawColliders(Graphics2D g2d, GameObject go) {
        g2d.setColor(Color.green);
        float zoomFactor = this.getHeight() / 176f;

        for(Collider collider : go.getColliders()) {
            Rectangle rect = collider.getRect();
            g2d.drawRect((int)((go.getX()+rect.x-cameraX)*zoomFactor),
                    (int)((10*CELL_SIZE-(go.getY()-rect.y))*zoomFactor),
                    (int)(rect.width*zoomFactor),
                    (int)(rect.height*zoomFactor));
        }
    }

    public void printGameObjectLocations() {
        for(GameObject go : gameObjects){
            System.out.println("GameObj : " + go.toString() + "  X: " + go.getX() + " Y: " + go.getY());
        }
        System.out.println("Mario : " + "  X: " + mario.getX() + " Y: " + mario.getY());
    }

    public GamePanel() {
        // init game objects
        gameObjects = new ArrayList<>();
        deadObjects = new ArrayList<>();
        MapLoader.loadMap(this,"map1.txt");

        // init mario
        mario = new Mario(this);
        gameObjects.add(mario);

        redrawTimer = new Timer(20, (ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();

        physicsTimer = new Timer(50,(ActionEvent e) -> {
            updatePhysics();
        });
        physicsTimer.start();

        // background music
        themeSound = AudioPlayer.playAudio("theme.wav");
    }


    private void updatePhysics() {
        for(GameObject go : gameObjects){
            go.updatePhysics();
        }
        //mario.updatePhysics();

        //handle dead objects
        for(GameObject go : deadObjects){
            getGameObjects().remove(go);
        }
        deadObjects = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.black);
        g2d.fillRect(0,0,this.getWidth(),this.getHeight());

        float zoomFactor = this.getHeight() / 176f;

        // game drawings here
        g2d.drawImage(img.background_grid,-(int)(cameraX*zoomFactor),
                0,
                (int)(img.background.getWidth()*zoomFactor),
                (int)(img.background.getHeight()*zoomFactor),
                null);

        // draw level elements
        for(GameObject go : gameObjects){
            drawGameObject(g2d,go);
        }

        // draw colliders (for debug)
//        for(GameObject go : gameObjects){
//            drawColliders(g2d,go);
//        }
//        drawColliders(g2d,mario);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case 38:
                mario.jump();
                break;
            case 39: //Right Arrow
                mario.moveRight();
                break;
            case 37: //Left Arrow
                mario.moveLeft();
                break;

            case 90: //Z
                printGameObjectLocations();
                break;
        }
//        System.out.println(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        mario.stop();
    }
}
