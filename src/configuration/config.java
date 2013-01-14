package configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 *
 * @author SUDHEERA
 */
public class config {

    public static String C2S_INITIALREQUEST;
    public static String UP;
    public static String DOWN;
    public static String LEFT;
    public static String RIGHT;
    public static String SHOOT;
    public static String SERVER_IP;
    public static int SERVER_PORT;
    public static int CLIENT_PORT;
    public static int gap;
    public static int startX;
    public static int startY;
    public static int GRIDSIZE;
//        public static int PLAYER_HEALTH ;
//        public static int MAP_SIZE =;
//        public static int MAX_BRICKS =;
//        public static int MAX_OBSTACLES = ;
    public static String pointTableColumnSpacing;
    public static int pointTableRowSpacing;
    public static int textPositionX;
    public static int textPositionY;

    public static void loadData() throws FileNotFoundException, IOException {
       
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("configuration/config_data.properties");

        //loading properites from properties file
        props.load(fis);

        //Communication
        C2S_INITIALREQUEST = props.getProperty("C2S_INITIALREQUEST");
        UP = props.getProperty("UP");
        DOWN = props.getProperty("DOWN");
        LEFT = props.getProperty("LEFT");
        RIGHT = props.getProperty("RIGHT");
        SHOOT = props.getProperty("SHOOT");
        SERVER_IP = props.getProperty("SERVER_IP");
        SERVER_PORT = Integer.parseInt(props.getProperty("SERVER_PORT"));
        CLIENT_PORT = Integer.parseInt(props.getProperty("CLIENT_PORT"));
        //C2S_INITIALREQUEST = props.getProperty("C2S_INITIALREQUEST");


        //Map 
        gap = Integer.parseInt(props.getProperty("gap"));
        startX = Integer.parseInt(props.getProperty("startX"));
        startY = Integer.parseInt(props.getProperty("startY"));
        GRIDSIZE = Integer.parseInt(props.getProperty("GRIDSIZE"));

        //Point table
        int len = Integer.parseInt(props.getProperty("point_table_spacing_column"));
        char[] charArray = new char[len];
        Arrays.fill(charArray, ' ');
        pointTableColumnSpacing = new String(charArray);
        pointTableRowSpacing = Integer.parseInt(props.getProperty("point_table_spacing_row"));
        textPositionX = Integer.parseInt(props.getProperty("textPositionX"));
        textPositionY = Integer.parseInt(props.getProperty("textPositionY"));




    }
}