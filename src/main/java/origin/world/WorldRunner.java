package origin.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import origin.*;

public class WorldRunner {

    //World Components
    private Minigun selfObj;//TODO figure out how to put my own planned movements into this algorithm
    //private Bot<SelfState> self;
    private HashMap<String, Bot> activeBots;
    private HashMap<String, Bot> eliminatedBots;

    //Prediction Cache
    private LinkedList<WorldState> predicted; //A List containing the most recently predicted worldstates (in order of time)
    private long predictionStartTime; //The time of the first predicted state in the list

    public WorldRunner() {

    }

    public void init(Minigun self, DataManager data) {
        activeBots = data.getActiveBots();
        eliminatedBots = new HashMap<String, Bot>();
    }
    public void roundStart() {
        activeBots.putAll(eliminatedBots);
        eliminatedBots.clear();
    }

    public WorldState PIF() {
        //Go through all bots and get their next BotState and update Wave/Bullet positions
        
        return null;
    }
}