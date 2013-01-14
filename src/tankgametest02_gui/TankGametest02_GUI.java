package tankgametest02_gui;

import configuration.config;
import it.randomtower.engine.ResourceManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author SUDHEERA
 */
public class TankGametest02_GUI extends StateBasedGame {

    public static int GAME_STATE = 1;

    public TankGametest02_GUI(String title) {
        super(title);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new TankGametest02_GUI("Tank Game"));
        app.setDisplayMode(1280, 650, false);
        app.setTargetFrameRate(60);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        try {
            ResourceManager.loadResources("data/resources.xml");
            config.loadData();
            
        } catch (IOException ex) {
            Logger.getLogger(TankGametest02_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        addState(new GameWorld(GAME_STATE, gc));
        enterState(GAME_STATE);
    }
}
