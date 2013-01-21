package entities;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author SUDHEERA
 */
public class AIPlayer extends Entity {

    private Image playerImage;
    public int helth = 100;
    private int playerNo;
    private int direction = 0; // 0 North , 1 East , 2 South ,3 West 
    private int coins = 0;
    private int points = 0;
    boolean isShot = false;

    public AIPlayer(float x, float y, int playerNo, int direction) {
        super(x, y);
        
        this.playerNo = playerNo;
        this.direction = direction;
        this.depth = 10;
        
        playerImage = ResourceManager.getImage("me_up");
        setGraphic(playerImage);
        setHitBox(0, 0, playerImage.getWidth(), playerImage.getHeight());
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {

        super.update(container, delta);
        System.out.println("done advance");
    }
}
