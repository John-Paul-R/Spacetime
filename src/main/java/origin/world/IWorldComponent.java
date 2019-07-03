package origin.world;

interface IWorldComponent<T> {
    
    public T getNextState(WorldState currentWorldState); //This is the current PREDICTED world state (starting at the current actual world state)
}