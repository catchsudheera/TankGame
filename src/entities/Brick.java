
package entities;

import it.randomtower.engine.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author SUDHEERA
 */

public class Brick extends Cell{
     public static String SOLID_WALL ="solid";
     public static String BLANK_WALL ="blank";
     private int helth=100;
     private Image brick; 
             
    public Brick(float x, float y) {
        super(x, y);
        helth = 100;
        this.x=super.x;
        this.y=super.y;
        depth = 10;
        brick = ResourceManager.getImage("brick100");
        setGraphic(brick);
        setHitBox(0, 0, brick.getWidth(), brick.getHeight());
        addType(SOLID_WALL);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);
    }
    
    
    
    
    
}
