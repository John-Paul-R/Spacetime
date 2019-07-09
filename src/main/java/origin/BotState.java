package origin;

import java.util.ArrayList;
import java.util.List;

public abstract class BotState implements KDElement {

    //These will be considered our fundamental variables.
    protected double
        x,
        y,
        energy,
        heading, //global absolute, 0 to 2pi
        velocity;
    protected int time;

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

    //TODO Implement this
    @Override
    public List<Double> getKDValues() {
        List<Double> out = new ArrayList<Double>(4);
        out.add(Math.random());
        out.add(Math.random());
        out.add(Math.random());
        out.add(Math.random());
        return out;
    }
    @Override
    public double kdDistanceTo(KDElement otherElement) {
        return 0;
    }
    @Override
    public double getLastKDDist() {
        return 0;
    }
    @Override
    public int compareTo(KDElement otherElement) {
        return 0;
    }
}