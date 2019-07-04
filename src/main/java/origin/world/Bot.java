package origin.world;
/**
 * @param <T> The state type (EnemyState or SelfState) to be used in the instance of this class
*/

import java.util.LinkedList;

public class Bot<T> implements IWorldComponent<T> {

    private BotStateCollection<T> states;
    private T cState;
    private T pState;

    //List of the latest predicted states
    private LinkedList<T> predictedStates;
    private T LatestPredictedState;

    public Bot() {
        states = new BotStateCollection<T>();
    }
    
    public void addState(T state) {
        states.add(state);
    }
    public T getState(int time) { //TODO see if there is a way to do this in better than O(n) time
        return states.getFromTime(time);
    }
    public T getCurrentState() {
        return cState;
    }
    public T getPreviousState() {
        return pState;
    }
    @Override
    public T predictNextState(WorldState inputWorldState) {
        return null;
    }

}