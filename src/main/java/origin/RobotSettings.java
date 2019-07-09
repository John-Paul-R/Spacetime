package origin;

import java.util.EnumMap;

public final class RobotSettings {
    // ALL manager classes should refer to this class in their init method AND in their update method
    // in order to load the desired settings at the start of the game and each turn (in case something was changed by another manager)
    
    private static EnumMap<Settings, Integer> settings;
    public static final int NUM_BOT_PREDICTION_BRANCHES = 20;
    public static final int BOTSTATE_NUM_KD_DIMS = 4;
    public static final int BOTSTATE_KD_BIN_SIZE = 250;
    RobotSettings(String defaults) {
        
        if (defaults == "" || defaults == null) //initialize defaults
        {
            /*                      Setting                        Value */
            init();

        } else {
            //TODO SettingsFromFile
        }
    }
    public static void init() {
        settings = new EnumMap<Settings, Integer>(Settings.class);
        settings.put(Settings.  FULL_SPIN           ,  0  );
        settings.put(Settings.  PREDICT_MODE        ,  0  );
        settings.put(Settings.  MOVE_MODE           ,  0  );
        settings.put(Settings.  IS_1v1              ,  0  );
        settings.put(Settings.  PAINT_KNN_GRAPHS    ,  0  );
        settings.put(Settings.  PAINT_KNN_ENDPOINTS ,  0  );
        settings.put(Settings.  PAINT_KNN_PATHS     ,  0  );
        settings.put(Settings.  PAINT_WAVES         ,  0  );
        settings.put(Settings.  PAINT_MOVE_OPTIONS  ,  0  );
        settings.put(Settings.  PAINT_GUN_STATS     ,  0  );
        settings.put(Settings.  ENABLE_GUN          ,  1  );
        settings.put(Settings.  ENABLE_MOVEMENT     ,  1  );
        settings.put(Settings.  GUN_MODE_OVERRIDE   , -1  );
        settings.put(Settings.  MOVE_MODE_OVERRIDE  , -1  );

    }
    public static int valueOf(Settings setting) {
        return settings.get(setting);
    }
    public static enum Settings {
        //DynamicInternal
        FULL_SPIN,
        PREDICT_MODE,
        MOVE_MODE,
        IS_1v1,
        //DynamicUser
        PAINT_KNN_GRAPHS,
        PAINT_KNN_ENDPOINTS,
        PAINT_KNN_PATHS,
        PAINT_WAVES,
        PAINT_MOVE_OPTIONS,
        PAINT_GUN_STATS,
        ENABLE_GUN,
        ENABLE_MOVEMENT,
        GUN_MODE_OVERRIDE, //Default -1
        MOVE_MODE_OVERRIDE, //Default -1
        //Static
        /*FULL_SPIN,
        PREDICT_MODE,
        MOVE_MODE,
        IS_1v1,*/
        NUM_KD_DIMS,
        KD_BIN_SIZE,
    }

    
}