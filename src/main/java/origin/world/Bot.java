package origin.world;
/**
 * @param <T> The state type (EnemyState or SelfState) to be used in the instance of this class
*/

import java.util.LinkedList;
import java.util.List;

import origin.KDTree;
import origin.RobotSettings;
import origin.RobotSettings.Settings;
import origin.EnemyState;
import origin.KDElement;

public class Bot {

    private KDTree<EnemyState> states;
    private EnemyState cState;
    private EnemyState pState;
    private String name;
    //List of the latest predicted states
    private LinkedList<EnemyState> predictedStates;
    private EnemyState LatestPredictedState;

    public Bot(String name) {
        states = new KDTree<EnemyState>(RobotSettings.valueOf(Settings.KD_BIN_SIZE), RobotSettings.valueOf(Settings.NUM_KD_DIMS));
        this.name = name;
    }
    
    public void addState(EnemyState state) {
        states.add(state);
        cState = state;
    }
    /*public T getState(int time) { //TODO see if there is a way to do this in better than O(n) time
        return states.getFromTime(time);
    }*/
    public EnemyState getCurrentState() {
        return cState;
    }
    public EnemyState getPreviousState() {
        return pState;
    }

    public EnemyState predictNextState(WorldState inputWorldState) {
        EnemyState knnState = ((EnemyState) states.getKNN(inputWorldState.activeBots.get(name).getCurrentState(), 2).get(0)).getNextState();
        return knnState;
    }
    public KDTree<EnemyState> getStateTree() {
        return states;
    }

}