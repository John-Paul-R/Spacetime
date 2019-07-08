package origin;

public abstract class BotState {

    //These will be considered our fundamental variables.
    protected double
        x,
        y,
        energy,
        heading, //global absolute, 0 to 2pi
        velocity;
    private int time;

    //Derivative variables

    //Utility


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