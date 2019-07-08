package origin;

interface IWorldComponent<T> {
    
    public T predictNextState(WorldState currentWorldState); //This is the current PREDICTED world state (starting at the current actual world state)
}