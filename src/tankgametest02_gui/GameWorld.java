package tankgametest02_gui;

import communicator.Communicator;
import configuration.config;
import entities.*;
import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.World;
import it.randomtower.engine.entity.Entity;
import java.io.IOException;
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
    private int playerInitX, playerInitY; // this variables declares the initial position of the player
    private Player[] opponentPlayer = new Player[4];
    private Player player;
    private boolean playersCreated = false;

    public GameWorld(int id, GameContainer gc) {
        super(id, gc);
    }

    private void setup(StateBasedGame game) throws IOException {

        com = Communicator.GetInstance();

        arena = new Cell[GRIDSIZE][GRIDSIZE];
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                arena[i][j] = new Cell(config.startX + config.gap * i, config.startY + config.gap * j);
//                arena[i][j] = new Coin(arena[i][j].getPosX(), arena[i][j].getPosY());
//                this.add(arena[i][j]);
            }
        }

        String reciveData;

        com.sendData(config.C2S_INITIALREQUEST);
        reciveData = com.reciveData();

//        System.out.println(reciveData+"*****");

        //I:P4:8,4;7,6;9,3;1,3;3,2;2,1;4,8;6,8;2,4;4,7:6,3;3,1;5,3;7,2;0,4;8,6;0,8;2,3;7,1;7,8:8,3;3,6;1,4;9,8;4,3;8,1;6,7;4,2;5,7;3,8#
        //S:P0;0,0;0:P1;0,9;0:P2;9,0;0:P3;9,9;0:P4;5,5;0#

        String[] section = reciveData.split(":");
        //set player me
        setPlayer(section[1]);
//        System.out.println("player sec : "+section[1]);

        //set bricks
        setBricks(section[2].split(";"));
//         System.out.println("brick sec : "+section[2]);

        //set stones
        setStones(section[3].split(";"));
//         System.out.println("stone sec : "+section[3]);

        //set water
        setWater(section[4].split(";"));
//         System.out.println("water sec : "+section[4]);

    }

    private void setBricks(String[] brick) {
        int[][] brickXY = new int[2][brick.length];


        for (int i = 0; i < brick.length; i++) {
            String[] coordinates = brick[i].split(",");
            brickXY[0][i] = Integer.parseInt(coordinates[0]);
            brickXY[1][i] = Integer.parseInt(coordinates[1]);

        }

        for (int i = 0; i < brickXY[0].length; i++) {

            int x = brickXY[0][i];
            int y = brickXY[1][i];
            arena[x][y] = new Brick(arena[x][y].getPosX(), arena[x][y].getPosY());
            add(arena[x][y]);
        }


    }

    private void setStones(String[] stone) {

        int[][] stoneXY = new int[2][stone.length];


        for (int i = 0; i < stone.length; i++) {
            String[] coordinates = stone[i].split(",");
            stoneXY[0][i] = Integer.parseInt(coordinates[0]);
            stoneXY[1][i] = Integer.parseInt(coordinates[1]);

        }

        for (int i = 0; i < stoneXY[0].length; i++) {

            int x = stoneXY[0][i];
            int y = stoneXY[1][i];
            arena[x][y] = new Stone(arena[x][y].getPosX(), arena[x][y].getPosY());
            add(arena[x][y]);
        }


    }

    private void setWater(String[] water) {

        int[][] waterXY = new int[2][water.length];


        for (int i = 0; i < water.length; i++) {
            String[] coordinates = water[i].split(",");
            waterXY[0][i] = Integer.parseInt(coordinates[0]);
            if (i == water.length - 1) {
                String[] split = coordinates[1].split("#");
                coordinates[1] = split[0];
            }
            waterXY[1][i] = Integer.parseInt(coordinates[1]);


        }
        //   System.out.println("XXX water value "+ waterXY[1][water.length-1]  );

        for (int i = 0; i < waterXY[0].length; i++) {

            int x = waterXY[0][i];
            int y = waterXY[1][i];
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
        String[] section = reciveData.split(":");

        if (section[0].equals("C")) {
            setCoins(section);
        } else if (section[0].equals("L")) {
            setLifePacks(section);
        } else if (section[0].equals("G")) {

            updatePlayers(section);

        } else if (section[0].equals("S")) {
            setOtherplayers(reciveData.split(":"));
            playersCreated=true;
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
        String[] split = section[3].split("#");
        int value = Integer.parseInt(split[0]);


        arena[x][y] = new Coin(arena[x][y].getPosX(), arena[x][y].getPosY(), value, lifetime);
        add(arena[x][y]);
        System.out.println("C: " + x + "," + y +" " + "value = " + value);

    }

    private void setLifePacks(String[] section) {

        String[] coord = section[1].split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);
        String[] split = section[2].split("#");
        int lifetime = Integer.parseInt(split[0]);



        arena[x][y] = new LifePack(arena[x][y].getPosX(), arena[x][y].getPosY(), lifetime);
        add(arena[x][y]);

        System.out.println("L: " + x + ","+ y +"  " + "life = " + lifetime);
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

    private void setPlayer(String section) {
        //System.out.println("section = "+section);

        int x = -1, y = -1;
        int playerNo = Integer.parseInt(section.charAt(1) + ""); //get player no from the String

        if (playerNo == 0) { //top left
            x = 0;
            y = 0;
        } else if (playerNo == 1) { //bottom left
            x = 0;
            y = GRIDSIZE - 1;
        } else if (playerNo == 2) {//top right
            x = GRIDSIZE - 1;
            y = 0;
        } else if (playerNo == 3) {//bottom right
            x = GRIDSIZE - 1;
            y = GRIDSIZE - 1;
        } else if (playerNo == 4) {//middle
            x = GRIDSIZE / 2;
            y = GRIDSIZE / 2;
        } else {
            System.out.println("error setting player on the grid: in GAMEWORLD class");
        }

        // System.out.println(x + " " + y + "grid");
        player = new Player(arena[x][y].getPosX(), arena[x][y].getPosY(), false, playerNo, 0);
        add(player);

        playerInitX = x;
        playerInitY = y;
    }

    private void setPointsTable(Graphics g) {

        String spaceBetColumns = config.pointTableColumnSpacing;
        int spaceBetRows = config.pointTableRowSpacing;
        int textPositionX = config.textPositionX;
        int textPositionY = config.textPositionY;

        g.setLineWidth(g.getLineWidth() * 3);
        g.setColor(Color.yellow);
        g.drawString(String.format("%10s", "Player ID") + spaceBetColumns + String.format("%10s", "Points") + spaceBetColumns + String.format("%10s", "Coins") + spaceBetColumns + String.format("%10s", "Helth"), textPositionX, textPositionY);
        
//        g.drawString("65666  626  5615 5665 65156",  textPositionX, (textPositionX + 2 + 1) * spaceBetRows);
        if (playersCreated) {
            
            for (int i = 0; i < 4; i++) {
                String pointsTableEntry = opponentPlayer[i].getPointsTableEntry();
                
                g.drawString(pointsTableEntry, textPositionX, textPositionY+spaceBetRows*(i+1));
               
          
                
                
            }

        }
    }

    private void setOtherplayers(String[] playerSection) {

        for (int i = 0; i < 4; i++) {
            int x, y, direction, no;
            String[] data = playerSection[i + 1].split(";");
            no = Integer.parseInt(data[0].charAt(1) + "");
            String[] position = data[1].split(",");
            x = Integer.parseInt(position[0]);
            y = Integer.parseInt(position[1]);

            if (playerInitX == x && playerInitY == y) {
                continue;
            }

            if (data[2].endsWith("#")) {
                data[2] = data[2].split("#")[0];
            }
            direction = Integer.parseInt(data[2]);

            opponentPlayer[i] = new Player(arena[x][y].getPosX(), arena[x][y].getPosY(), true, no, direction);
            add(opponentPlayer[i]);
            // opponentPlayer[i] = (Player) arena[x][y];

        }
        
    }

    private void updatePlayers(String[] section) {

        for (int i = 0; i < 5; i++) {

            if (i == 4) {
                
                //update the AI player code goes here
                //not yet implemented
                continue;
            }
            opponentPlayer[i].setGlobleUpdate(section[i + 1]);
        }

    }
}
