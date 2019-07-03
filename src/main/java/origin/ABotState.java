package origin;

public class ABotState {

    //These will be considered our fundamental variables.
    private double
        x,
        y,
        energy,
        heading, //global absolute, 0 to 2pi
        velocity;
    private int time;

    //Derivative variables

    //Secondary variables (Note: tsl = time since last)
    int tslBulletFired;
    int tslDeceleration;

    //Dynamic variables (Essentially a mini cache, so that things do not need to be recalculated within one turn)
    private double lastDist;
    private int turnCalculatedLastDist;

    //Utility
    private ABotState prevState;
    private ABotState nextState;

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
    public void setNextState(ABotState nextState) {
        this.nextState = nextState;
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
}