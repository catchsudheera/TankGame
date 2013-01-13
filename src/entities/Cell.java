package entities;

import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import configuration.config;

/**
 *
 * @author SUDHEERA
 */
public class Cell extends Entity {

    final static int gap = config.gap;
    final static int startX = config.startX;
    final static int startY = config.startY;
   
    private float posX;
    private float posY;


    public Cell(float x, float y) {
        super(x, y);
        posX = x;
        posY = y;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);
       
       
    }

    public float getPosX() {
        return x;
    }

    public float getPosY() {
        return y;
    }
}
