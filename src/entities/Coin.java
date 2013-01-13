package entities;

import it.randomtower.engine.ME;
import it.randomtower.engine.ResourceManager;
import org.newdawn.slick.*;

/**
 *
 * @author SUDHEERA
 */
public class Coin extends Cell {

    public static String COIN = "coin";
    private Image coin;
    private int life;
    private int value;
    int timelived = 0;
    private int fireRate = 50;
    private int miliCount = 0;
    private int milis = fireRate;
    private int miliStep = milis / 5;

    public Coin(float x, float y, int value, int life) {

        super(x, y);
        this.value = value;
        this.life = life;
        this.x = super.x;
        this.y = super.y;
        depth = 10;
        coin = ResourceManager.getImage("coin");
        setGraphic(coin);
        setHitBox(0, 0, coin.getWidth(), coin.getHeight());
        addType(COIN);

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);

        g.setColor(Color.black);
        g.drawString(value + "", x + 5, y + 10);
        g.drawString(life + "", x + 5, y + 30);

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
