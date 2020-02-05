package game;

import game.objects.BrickBlock;
import game.objects.Enemy;
import game.objects.GameObject;
import game.objects.RandomBlock;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapLoader {

    public static void loadMap(GamePanel gp, String mapName){
        try {
            Scanner scn = new Scanner(new File("./assets/maps/" + mapName));
            ArrayList<GameObject> gameObjects = new ArrayList<>();

            int j = 0;
            while (scn.hasNext()) {
                String line = scn.nextLine();

                if (j > 11) {
                    System.err.println("malformed map file.");
                    return;
                }

                String segments[] = line.split(" ");
                for (int i = 0; i < segments.length; i++) {
                    int s = Integer.parseInt(segments[i]);
                    switch (s){
                        case 1:
                            gameObjects.add(new BrickBlock(gp,i,j));
                            break;
                        case 2:
                            gameObjects.add(new RandomBlock(gp,i,j));
                            break;
                        case 3:
                            gameObjects.add(new Enemy(gp,i,j));
                            break;

                        //add more cell types here
                    }
                }
                j++;
            }
            scn.close();
            gp.setGameObjects(gameObjects);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
