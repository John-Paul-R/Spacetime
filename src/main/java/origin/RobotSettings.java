package origin;

import java.util.ArrayList;
import java.util.EnumMap;

final class RobotSettings {
    // ALL manager classes should refer to this class in their init method AND in their update method
    // in order to load the desired settings at the start of the game and each turn (in case something was changed by another manager) 
    
    private static EnumMap<Settings, Integer> settings;

    RobotSettings(String defaults) {
        settings = new EnumMap<Settings, Integer>(Settings.class);
        if (defaults == "" || defaults == null) //initialize defaults
        {
            /*                      Setting                        Value */
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

        } else {
            //TODO SettingsFromFile
        }
    }
    interface Setting {}
    static enum Settings {
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
    }

    
}