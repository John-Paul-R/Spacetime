package origin.world;

public class WorldState implements ITimeDependent<WorldState> {

    private int time;

    public WorldState(int time) {
        this.time = time;
    }

    @Override
    public WorldState predictNextState() {
        return null;
    }

    public int getTime() {
        return time;
    }
}
