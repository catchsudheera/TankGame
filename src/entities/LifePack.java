package entities;

import it.randomtower.engine.ME;
import it.randomtower.engine.ResourceManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author SUDHEERA
 */
public class LifePack extends Cell {

    int life;
    private Image lifeImage;
    private int timelived = 0;

    public LifePack(float x, float y, int life) {
        super(x, y);

        this.life = life;
        lifeImage = ResourceManager.getImage("lifepack");
        setGraphic(lifeImage);
        setHitBox(0, 0, lifeImage.getWidth(), lifeImage.getHeight());
        addType("life");

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);
        g.setColor(Color.black);
        g.drawString(life+"", x+5, y+20);
        
    }
    
    

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);

        timelived += delta;
        if (timelived >= life) {

            ME.world.remove(this);
        
        }


    }
}
