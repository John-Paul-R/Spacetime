package origin.world;
/**
 * @param <T> The state type (EnemyState or SelfState) to be used in the instance of this class
*/

import java.util.LinkedList;

public class Bot<T> implements IWorldComponent<T> {

    private BotStateCollection<T> states;
    private T cState;

    //List of the latest predicted states
    private LinkedList<T> predictedStates;

    public Bot() {
        states = new BotStateCollection<T>();
        
    }

    @Override
    public T getNextState(WorldState inputWorldState) {
        return null;
    }

}