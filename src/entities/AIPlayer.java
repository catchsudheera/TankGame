package entities;

import it.randomtower.engine.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author SUDHEERA
 */
public class AIPlayer extends Player {

    private Image playerImage;
    private int direction;

    public AIPlayer(float x, float y, int playerNo, int direction) {
        super(x, y, playerNo, direction);

        playerImage = ResourceManager.getImage("me_up");
        this.direction=direction;
        setGraphic(playerImage);
        setHitBox(0, 0, playerImage.getWidth(), playerImage.getHeight());
        addType(PLAYERTYPE);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);
        
        collisionHandle();
        playerImageDirection(direction);
        System.out.println("AI player running");
        
    }
    
    public void moveRight(){
          x+=x+configuration.config.gap;
    }

    private void playerImageDirection(int direction) {
        switch (direction) {
            case 0:
                playerImage = ResourceManager.getImage("me_up");
                setGraphic(playerImage);
                break;
            case 1:
                playerImage = ResourceManager.getImage("me_right");
                setGraphic(playerImage);
                break;
            case 2:
                playerImage = ResourceManager.getImage("me_down");
                setGraphic(playerImage);
                break;
            case 3:
                playerImage = ResourceManager.getImage("me_left");
                setGraphic(playerImage);
                break;
        }
    }
    
    private void collisionHandle() {
        String[] checkForCollisin = {Coin.COIN, LifePack.LIFE};
        collide(checkForCollisin, x, y);

    }
}
