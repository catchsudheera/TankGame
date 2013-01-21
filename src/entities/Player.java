package entities;

import configuration.config;
import it.randomtower.engine.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author SUDHEERA
 */

public class Player extends Cell {

    private Image playerImage;
    public static String PLAYERTYPE = "player";
    private int helth = 100;
    private int playerNo;
    private int direction = 0; // 0 North , 1 East , 2 South ,3 West 
    private String globleUpdate;
    private int coins = 0;
    private int points = 0;
    private boolean isShot = false;

  

    public Player(float x, float y, int playerNo, int direction) {

        super(x, y);

        this.playerNo = playerNo;
        this.direction = direction;
        this.depth = 10;


        playerImage = ResourceManager.getImage("opponent_up");

        setGraphic(playerImage);
        setHitBox(0, 0, playerImage.getWidth(), playerImage.getHeight());
        addType(PLAYERTYPE);

        globleUpdate = "P" + playerNo + ";" + (int) x + "," + (int) y + ";" + direction + ";0;" + helth + ";" + coins + ";" + points;
    }

    public void deductCoins(int amount) {
        this.coins -= amount;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public void setGlobleUpdate(String globleUpdate) {
        this.globleUpdate = globleUpdate;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setPosition(float x, float y, int direction) {
        this.x = x;
        this.y = y;
        this.setDirection(direction);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);

        collisionHandle();
        globleUpdate();
        playerImageDirection(direction);
    }

    private void playerImageDirection(int direction) {
        switch (direction) {
            case 0:
                playerImage = ResourceManager.getImage("opponent_up");
                setGraphic(playerImage);
                break;
            case 1:
                playerImage = ResourceManager.getImage("opponent_right");
                setGraphic(playerImage);
                break;
            case 2:
                playerImage = ResourceManager.getImage("opponent_down");
                setGraphic(playerImage);
                break;
            case 3:
                playerImage = ResourceManager.getImage("opponent_left");
                setGraphic(playerImage);
                break;
        }
    }

    public String getPointsTableEntry() {
        String spacing = config.pointTableColumnSpacing;
        return String.format("%10s", playerNo) + spacing + String.format("%10s", points) + spacing + String.format("%10s", coins) + spacing + String.format("%10s", helth + "%");
    }

    private void globleUpdate() {

        String[] data = globleUpdate.split(";");
        String[] positionUpdate = data[1].split(",");
        x = config.startX + config.gap * Integer.parseInt(positionUpdate[0]);
        y = config.startY + config.gap * Integer.parseInt(positionUpdate[1]);
        this.setDirection(Integer.parseInt(data[2]));
        int shot = Integer.parseInt(data[3]);
        if (shot == 1) {
            isShot = true;
        }
        helth = Integer.parseInt(data[4]);
        coins = Integer.parseInt(data[5]);
        points = Integer.parseInt(data[6]);
    }

    private void collisionHandle() {
        String[] checkForCollisin = {Coin.COIN, LifePack.LIFE};
        collide(checkForCollisin, x, y);

    }
}
