
package entities;

import it.randomtower.engine.ResourceManager;
import org.newdawn.slick.Image;



/**
 *
 * @author SUDHEERA
 */
public class Water extends Cell{
     public static String WATER ="water";
     private Image water;

    public Water(float x, float y) {
        super(x, y);
        this.x=super.x;
        this.y=super.y;
        depth = 10;
        water = ResourceManager.getImage("water");
        setGraphic(water);
        setHitBox(0, 0, water.getWidth(), water.getHeight());
        addType(WATER);
    }
    
    
}
