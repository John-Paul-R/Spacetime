package origin;
/**
 * @param <T> The state type (EnemyState or SelfState) to be used in the instance of this class
*/

import java.util.ArrayList;
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
    private LinkedList<EnemyState> nextStatesCache;
    private EnemyState latestPredictedState;

    public Bot(String name) {
        states = new KDTree<EnemyState>(RobotSettings.BOTSTATE_KD_BIN_SIZE, RobotSettings.BOTSTATE_NUM_KD_DIMS);
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

/*     public List<EnemyState> predictNextState(WorldState inputWorldState, int numBranches) {
        int k = 2;
        List<KDElement> knnStates = states.getKNN(inputWorldState.activeBots.get(name).getCurrentState(), numBranches);
        LinkedList<EnemyState> nextStates = new LinkedList<EnemyState>();
        for (KDElement e : knnStates) {
            nextStates.add( ((EnemyState) e).getNextState() );
        }
        return nextStates;
    } */

    public List<EnemyState> getKNNStates(WorldState inputWorldState, int numBranches) {
        int k = numBranches;
        List<EnemyState> startStates = inputWorldState.getBotStates(name, numBranches);
        List<EnemyState> knnStates = new LinkedList<EnemyState>();
        LinkedList<EnemyState> nextStatesCache = new LinkedList<EnemyState>();
        this.nextStatesCache = nextStatesCache;

        for (int i=0; i < startStates.size(); i++) { //Run KNN with k=numBranches for all input states in startStates (which are frome inputWorldState)

            List<KDElement> clist = states.getKNN(startStates.get(i), k);

            for (KDElement e : clist) {
                EnemyState ee = ((EnemyState) e);
                knnStates.add(ee);
                nextStatesCache.add( ee );//.getNextState()//TODO reimplement
            }
        }
        
        return nextStatesCache;
    }
    public List<EnemyState> getNextStatesCache() {
        return this.nextStatesCache;
    }
    
    public KDTree<EnemyState> getStateTree() {
        return states;
    }

}