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

    private boolean opponent;
    private Image playerImage;
    public static String PLAYERTYPE = "player";
    public int helth = 100;
    private int playerNo;
    private int direction = 0; // 0 North , 1 East , 2 South ,3 West 
    private String globleUpdate;
    private int coins=0;
    private int points=0;
    boolean isShot=false;

    public void setGlobleUpdate(String globleUpdate) {
        this.globleUpdate = globleUpdate;
    }

    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public void setPosition(float x, float y,int direction){
        this.x=x;
        this.y=y;
        this.setDirection(direction);
    }

    public Player(float x, float y, boolean opponent, int playerNo, int direction) {
        super(x, y);
        this.opponent = opponent;
        this.playerNo = playerNo;
        this.direction = direction;
        depth = 10;
        this.opponent = opponent;

        if (opponent) {
            playerImage = ResourceManager.getImage("opponent_up");
        } else {
            playerImage = ResourceManager.getImage("me_up");
        }
        setGraphic(playerImage);
        setHitBox(x, y, playerImage.getWidth(), playerImage.getHeight());
        addType(PLAYERTYPE);
        
        globleUpdate = "P"+playerNo+";"+(int)x+","+(int)y+";"+direction+";0;"+helth+";"+coins+";"+points;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);
        
        String[] data = globleUpdate.split(";");
        String[] positionUpdate = data[1].split(",");
        x=config.startX + config.gap * Integer.parseInt(positionUpdate[0]);
        y=config.startY + config.gap * Integer.parseInt(positionUpdate[1]);
        this.setDirection(Integer.parseInt(data[2]));
        int shot=Integer.parseInt(data[3]);
        if(shot==1){
            isShot=true;
        }
        helth=Integer.parseInt(data[4]);
        coins=Integer.parseInt(data[5]);
        points=Integer.parseInt(data[6]);
      
        renderDirection(direction);
    }

    private void renderDirection(int direction) {
         switch (direction) {
            case 0:
                if (opponent) {
                    playerImage = ResourceManager.getImage("opponent_up");
                } else {
                    playerImage = ResourceManager.getImage("me_up");
                }
                setGraphic(playerImage);
                break;
            case 1:
                if (opponent) {
                    playerImage = ResourceManager.getImage("opponent_right");
                } else {
                    playerImage = ResourceManager.getImage("me_right");
                }
                setGraphic(playerImage);
                break;
            case 2:
                if (opponent) {
                    playerImage = ResourceManager.getImage("opponent_down");
                } else {
                    playerImage = ResourceManager.getImage("me_down");
                }
                setGraphic(playerImage);
                break;
            case 3:
                if (opponent) {
                    playerImage = ResourceManager.getImage("opponent_left");
                } else {
                    playerImage = ResourceManager.getImage("me_left");
                }
                setGraphic(playerImage);
                break;
        }
    }
}
