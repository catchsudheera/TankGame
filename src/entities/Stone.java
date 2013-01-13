
package entities;

import it.randomtower.engine.ResourceManager;
import org.newdawn.slick.Image;



/**
 *
 * @author SUDHEERA
 */
public class Stone extends Cell{
    public static String SOLID_WALL ="solid";
    private Image stone;

    public Stone(float x, float y) {
        super(x, y);
        this.x=super.x;
        this.y=super.y;
        depth = 10;
        stone = ResourceManager.getImage("stone");
        setGraphic(stone);
        setHitBox(0, 0, stone.getWidth(), stone.getHeight());
        addType(SOLID_WALL);
    }
    
    
}
