package entities;

import it.randomtower.engine.ME;
import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.*;

/**
 *
 * @author SUDHEERA
 */
public class LifePack extends Cell {

    int life;
    private Image lifeImage;
    private int timelived = 0;
     public static String LIFE = "LIFE";

    public LifePack(float x, float y, int life) {
        super(x, y);

        this.life = life;
        lifeImage = ResourceManager.getImage("lifepack");
        setGraphic(lifeImage);
        setHitBox(0, 0, lifeImage.getWidth(), lifeImage.getHeight());
        addType(LIFE);

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
    
       @Override
    public void collisionResponse(Entity other) {
        
        ME.world.remove(this);
//        System.out.println("removed : "+(x-configuration.config.startX)/configuration.config.gap+","+(y-configuration.config.startX)/configuration.config.gap);
        
        
    }
}
