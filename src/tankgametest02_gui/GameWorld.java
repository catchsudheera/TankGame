package tankgametest02_gui;

import communicator.Communicator;
import configuration.config;
import entities.*;
import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.World;
import it.randomtower.engine.entity.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author SUDHEERA
 */
class GameWorld extends World {

    public static int GRIDSIZE = config.GRIDSIZE;
    private Image background, arenaImage;
    static public Cell[][] arena;
    private Communicator com;
    private ArrayList<Entity> players = new ArrayList<>();
    private int AIplayerNo;

    public GameWorld(int id, GameContainer gc) {
        super(id, gc);
    }

    private void setup(StateBasedGame game) throws IOException {

        //get instance of communicator class
        com = Communicator.GetInstance();

        arena = new Cell[GRIDSIZE][GRIDSIZE];
        //create the arena
        createArena();

        String reciveData;
        com.sendData(config.C2S_INITIALREQUEST);
        do {

            reciveData = com.reciveData();

            if (reciveData.equals("PLAYERS_FULL")) {
                //exit the game to a window which states the situation
                System.out.println("PLAYERS_FULL or GAME_ALREADY_STARTED");
                System.exit(0);
            }
            if ((!reciveData.equals("ALREADY_ADDED")) && (!(reciveData.charAt(0) == 'I')) && ((!reciveData.equals("GAME_ALREADY_STARTED")))) {
                com.sendData(config.C2S_INITIALREQUEST);
            }

        } while (!(reciveData.charAt(0) == 'I'));

        // System.out.println("@@@@@"+reciveData);
        //I:P4:8,4;7,6;9,3;1,3;3,2;2,1;4,8;6,8;2,4;4,7:6,3;3,1;5,3;7,2;0,4;8,6;0,8;2,3;7,1;7,8:8,3;3,6;1,4;9,8;4,3;8,1;6,7;4,2;5,7;3,8#
        //S:P0;0,0;0:P1;0,9;0:P2;9,0;0:P3;9,9;0:P4;5,5;0#


        String[] section = reciveData.split(":");  //break into sections
        AIplayerNo = Integer.parseInt(section[1].charAt(1) + "");   //set AIplayer number 
        setBricks(section[2].split(";")); //set bricks
        setStones(section[3].split(";")); //set stones
        setWater(section[4].split(";"));  //set water

        do {
            reciveData = com.reciveData();
        } while (!(reciveData.charAt(0) == 'S'));

        setPlayers(reciveData.split(":")); //set players from the initiation message

    }

    private void setBricks(String[] brick) {

        for (int i = 0; i < brick.length; i++) {
            String[] coordinates = brick[i].split(",");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);

            arena[x][y] = new Brick(arena[x][y].getPosX(), arena[x][y].getPosY());
            add(arena[x][y]);
        }
    }

    private void setStones(String[] stone) {

        for (int i = 0; i < stone.length; i++) {
            String[] coordinates = stone[i].split(",");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            arena[x][y] = new Stone(arena[x][y].getPosX(), arena[x][y].getPosY());
            add(arena[x][y]);
        }

    }

    private void setWater(String[] water) {

        for (int i = 0; i < water.length; i++) {

            String[] coordinates = water[i].split(",");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            arena[x][y] = new Water(arena[x][y].getPosX(), arena[x][y].getPosY());
            add(arena[x][y]);
        }
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        super.init(gc, game);
        gc.setAlwaysRender(true);
        gc.setUpdateOnlyWhenVisible(false);

        //create background
        createBackground();

        container.setAlwaysRender(true);
        try {
            setup(game);
        } catch (IOException ex) {
            Logger.getLogger(GameWorld.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("IOException @setup()" + ex.toString());
        }

    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        super.update(gc, game, delta);

        String reciveData = com.reciveData();
        //  System.out.println("#####"+reciveData);
        String[] section = reciveData.split(":");

        if (section[0].equals("C")) {
            setCoins(section);
        } else if (section[0].equals("L")) {
            setLifePacks(section);
        } else if (section[0].equals("G")) {
            updatePlayers(section);
        }


    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {


        //render background
        g.drawImage(background, 0, -130);
        g.drawImage(arenaImage, 20, 20);
        super.render(gc, game, g);

        //render Points table
        setPointsTable(g);

    }

    private void setCoins(String[] section) {

        String[] coord = section[1].split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);
        int lifetime = Integer.parseInt(section[2]);
        int value = Integer.parseInt(section[3]);

        arena[x][y] = new Coin(arena[x][y].getPosX(), arena[x][y].getPosY(), value, lifetime);
        add(arena[x][y]);
        System.out.println("C: " + x + "," + y + " " + "value = " + value);

    }

    private void setLifePacks(String[] section) {

        String[] coord = section[1].split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);
        int lifetime = Integer.parseInt(section[2]);

        arena[x][y] = new LifePack(arena[x][y].getPosX(), arena[x][y].getPosY(), lifetime);
        add(arena[x][y]);

        System.out.println("L: " + x + "," + y + "  " + "life = " + lifetime);
    }

    private void createBackground() {
        background = ResourceManager.getImage("background_with_pt");

        Entity back = new Entity(0, -130, background) {
        };
        back.depth = -100;
        arenaImage = ResourceManager.getImage("arena");
        Entity fore = new Entity(20, 20, background) {
        };
        fore.depth = -100;

    }

    private void setPointsTable(Graphics g) {

        String spaceBetColumns = config.pointTableColumnSpacing;
        int spaceBetRows = config.pointTableRowSpacing;
        int textPositionX = config.textPositionX;
        int textPositionY = config.textPositionY;

        g.setLineWidth(g.getLineWidth() * 3);
        g.setColor(Color.yellow);
        g.drawString(String.format("%10s", "Player ID") + spaceBetColumns + String.format("%10s", "Points") + spaceBetColumns + String.format("%10s", "Coins") + spaceBetColumns + String.format("%10s", "Helth"), textPositionX, textPositionY);



        for (int i = 0; i < players.size(); i++) {
            if (i == AIplayerNo) {
                continue;
            }
            String pointsTableEntry = ((Player) players.get(i)).getPointsTableEntry();
            g.drawString(pointsTableEntry, textPositionX, textPositionY + spaceBetRows * (i + 1));

        }


    }

    private void setPlayers(String[] playerSection) {

        for (int i = 0; i < 5; i++) {

            int x, y, direction, no;
            String[] data = playerSection[i + 1].split(";");
            no = Integer.parseInt(data[0].charAt(1) + "");
            String[] position = data[1].split(",");
            x = Integer.parseInt(position[0]);
            y = Integer.parseInt(position[1]);
            direction = Integer.parseInt(data[2]);

            Entity newPlayer;

            if (no == AIplayerNo) {
                newPlayer = new AIPlayer(arena[x][y].getPosX(), arena[x][y].getPosY(), no, direction);
            } else {
                newPlayer = new Player(arena[x][y].getPosX(), arena[x][y].getPosY(), no, direction);
            }

            add(newPlayer);
            players.add(newPlayer);
        }

    }

    private void updatePlayers(String[] section) {

        for (int i = 0; i < players.size(); i++) {

            if (i == AIplayerNo) {
                //update the AI player code goes here
                //not yet implemented
                //continue;
                AIPlayer AIget = (AIPlayer) players.get(i);

            } else {
                Player get = (Player) players.get(i);
                get.setGlobleUpdate(section[i + 1]);
            }
        }

    }

    private void createArena() {

        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                arena[i][j] = new Cell(config.startX + config.gap * i, config.startY + config.gap * j);
            }
        }
    }
}
