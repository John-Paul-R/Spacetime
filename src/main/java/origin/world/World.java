package origin.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import origin.*;

public class World {

    //World Components
    private Minigun selfObj;//TODO figure out how to put my own planned movements into this algorithm
    private Bot<SelfState> self;
    private ArrayList<Bot<EnemyState>> activeBots;
    private ArrayList<Bot<EnemyState>> eliminatedBots;

    //Prediction Cache
    private LinkedList<WorldState> predicted; //A List containing the most recently predicted worldstates (in order of time)
    private long predictionStartTime; //The time of the first predicted state in the list

    public World() {

    }

    public void init(Minigun self, Database data) {
        activeBots = data.getBots();
        eliminatedBots = new ArrayList<Bot<EnemyState>>(activeBots.size());
    }
    public void roundStart() {
        activeBots.addAll(eliminatedBots);
    }

    public WorldState PIF() {
        return null;
    }
}