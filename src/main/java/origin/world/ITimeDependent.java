package origin.world;

interface ITimeDependent<T> {
    T predictNextState();
}