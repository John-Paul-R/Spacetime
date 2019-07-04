package origin;

public abstract class BotState {

    //These will be considered our fundamental variables.
    private double
        x,
        y,
        energy,
        heading, //global absolute, 0 to 2pi
        velocity;
    private int time;

    //Derivative variables

    //Utility
    private BotState prevState;
    private BotState nextState;

    /*
        <> Initialization <>
    */
    public void init (double x, double y, double energy, double heading, double velocity, long time) {
        this.x = x;
        this.y = y;
        this.energy = energy;
        this.heading = heading;
        this.velocity = velocity;
        this.time = (int) time;
    }

    //Modifiers
    public void setNextState(BotState nextState) {
        this.nextState = nextState;
    }
    public void setPrevState(BotState prevState) {
        this.prevState = prevState;
    }

    //Retrieval
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getEnergy() {
        return energy;
    }
    public double getHeading() {
        return heading;
    }
    public double getVelocity() {
        return velocity;
    }
    public int getTime() {
        return time;
    }

    public BotState getNextState() {
        return nextState;
    }
    public BotState getPrevState() {
        return prevState;
    }
}