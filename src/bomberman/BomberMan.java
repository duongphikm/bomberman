/*
 * In this class you will instentialte other classes and game playe will be
 * implemented here.
 */
package bomberman;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class BomberMan {

    static CopyOnWriteArrayList<Player> players = new CopyOnWriteArrayList<Player>();
    static CopyOnWriteArrayList<Enemy> enemies = new CopyOnWriteArrayList<Enemy>();
//    static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    static final int SIZE = 15;
    static final int height = 14;
    static Map map;
    static MapGui frame;
    JFrame menu;
    static int[][] intMap = new int[][]{
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
        {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1},
        {1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
        {1, 0, 0, 2, 0, 2, 0, 0, 0, 0, 2, 2, 0, 1},
        {1, 0, 1, 2, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1},
        {1, 0, 0, 2, 0, 2, 0, 0, 0, 0, 2, 0, 0, 1},
        {1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
        {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 2, 0, 1},
        {1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
        {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1},
        {1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
        {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public static void main(String args[]) {
//        new menu();

        new BomberMan().startGame(15,14,intMap);

    }

    public void startGame(int _Width, int  _height, int[][] _intMap) {
        System.out.println("Starting Game");
        map = new Map(intMap, _Width, _height);
        addEnemies(4);
        addPlayers(1);
        frame = new MapGui("Bomberman by Hassan", map);

    }

    public void addEnemies(int n) {
        for (int i = 0; i < n; i++) {
            enemies.add(new Enemy(new Position(550 + 5, 350 + 5)));
        }
    }

    public void addPlayers(int n) {
        if (n >= 1) {
            players.add(new Player(new Position(60, 30)));
        }
        if (n >= 2) {
            players.add(new Player(new Position(660, 630)));
        }
    }

    public static void saveGame() {
        ArrayList saveData = new ArrayList();
        ArrayList<Position> enemyPositions = new ArrayList<Position>();
        ArrayList<Position> playerPositions = new ArrayList<Position>();
//        ArrayList<Position> enemyPositions = new ArrayList<Position>();
        for (Enemy i : enemies) {
            enemyPositions.add(i.getPosition());
        }
        for (Player j : players) {
            playerPositions.add(j.getPosition());
        }
        saveData.add(enemyPositions);
        saveData.add(playerPositions);
        saveData.add(intMap);
        try {
            FileOutputStream fileOut1 = new FileOutputStream("gameSaveData.txt");
            ObjectOutputStream out1 = new ObjectOutputStream(fileOut1);
            out1.writeObject(saveData);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void loadGame() {
        ArrayList saveData = null;
        try {
            FileInputStream fileIn = new FileInputStream("gameSaveData.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            saveData = (ArrayList) in.readObject();
        } catch (Exception i) {
            i.printStackTrace();
        }

        ArrayList<Position> enemyPositions = (ArrayList) saveData.get(0);
        ArrayList<Position> playerPositions = (ArrayList) saveData.get(1);

        CopyOnWriteArrayList<Enemy> newEnemies = new CopyOnWriteArrayList<Enemy>();
        CopyOnWriteArrayList<Player> newPlayers = new CopyOnWriteArrayList<Player>();
        //   Map newMap = ((Map)saveData.get(2));
        for (Position i : enemyPositions) {
            newEnemies.add(new Enemy(i));
        }
        enemies = newEnemies;

        for (Position i : playerPositions) {
            newPlayers.add(new Player(i));
        }
        players = newPlayers;
        intMap = (int[][]) saveData.get(2);
        map = new Map(intMap, SIZE,height);
        frame.stopTimers();
        frame.dispose();
        frame = new MapGui("Bomberman by Hassan", map);
    }
}
