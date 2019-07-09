package origin;

import java.util.List;
import java.util.ArrayList;

import robocode.ScannedRobotEvent;

public class EnemyState extends BotState implements KDElement {

    private double
        bearing, //relative to our bot, -pi to pi
        absBearing, //global, 0 to 2pi
        lateralBearing, /* Derivative stat. Is the bearing of the enemy bot relative to the straight line from us to them.
                        NOTE: Consider assuming that they are always facing us (thus limiting the angles to -pi/2 to pi/2 or 0 to pi)*/
        distance;

    //Secondary variables (Note: tsl = time since last)
    private int tslBulletFired;
    private int tslDeceleration;
    

    //Dynamic variables (Essentially a mini cache, so that things do not need to be recalculated within one turn)
    private double lastDist;
    private int turnCalculatedLastDist;

    //Utility
    protected EnemyState prevState;
    protected EnemyState nextState;

    private EnemyState deltaState;

    public EnemyState(double x, double y, double energy, double heading, double velocity, long time) {
        super.init(x, y, energy, heading, velocity, time);
    }
    public EnemyState(EnemyState base, EnemyState delta) {

        x = base.getX() + delta.getX();
        y = base.getY() + delta.getY();
        energy = base.getEnergy() + delta.getEnergy();
        heading = base.getHeading() + delta.getHeading();
        velocity = base.getVelocity() + delta.getVelocity();
        time = base.getTime() + delta.getTime();

    }


    public EnemyState(ScannedRobotEvent e, Minigun self) {

        bearing = e.getBearingRadians();
        double heading = e.getHeadingRadians();
        double velocity = e.getVelocity();
        distance = e.getDistance();
        double energy = e.getEnergy();

        absBearing = (bearing + self.getHeadingRadians());
        double x = self.getX() + Math.sin(absBearing) * distance;
        double y = self.getY() + Math.cos(absBearing) * distance;
        long time = self.getTime();

        super.init(x, y, energy, heading, velocity, time);
    }

    public double getBearing() {
        return bearing;
    }

    public double getAbsBearing() {
        return absBearing;
    }

    public double getLatBearing() {
        return lateralBearing;
    }

    public double getDistance() {
        return bearing;
    }

    public double getLastDist() { // TODO update this so that it checks if dist needs to be updated
        return lastDist;
    }

    public EnemyState getNextState() {
        return nextState;
    }

    public EnemyState getPrevState() {
        return prevState;
    }

    public void setNextState(EnemyState nextState) {
        this.nextState = nextState;
        this.setDeltaState(new EnemyState(nextState.getX() - this.x, nextState.getY() - y,
                nextState.getEnergy() - energy, nextState.getHeading() - heading, nextState.getVelocity() - velocity,
                nextState.getTime() - time));
    }
    public EnemyState getDeltaState() {
        return deltaState;
    }

    public void setDeltaState(EnemyState deltaState) {
        this.deltaState = deltaState;
    }
    public void setPrevState(EnemyState prevState) {
        this.prevState = prevState;
    }

    //TODO Implement this
    private double lastKDDist = 0;
    @Override
    public List<Double> getKDValues() {
        List<Double> out = new ArrayList<Double>(4);
        out.add(heading/(Math.PI*2));//
        out.add(velocity/8);//;////
        out.add(0D);//((double)tslDeceleration)/100);//()));
        out.add(1/((double)tslDeceleration+2D));//
        //add 36/distance
        //combine heading/velocity to make two more useful values
        return out;
    }
    @Override
    public double kdDistanceTo(KDElement otherElement) {
        double sum = 0;
        List<Double> vals = this.getKDValues();
        List<Double> otherVals = otherElement.getKDValues();
        for (int i=0; i < vals.size(); i++)
        {
            sum += Math.pow(vals.get(i)-otherVals.get(i), 2);
        }
        lastKDDist = Math.sqrt(sum);
        return lastKDDist;
    }
    @Override
    public double getLastKDDist() {
        return lastKDDist;
    }
    @Override
    public int compareTo(KDElement otherElement) {
        return 0;
    }
	public void setTLD(int tld) {
        this.tslDeceleration = tld;
	}
    public int getTLD() {
        return this.tslDeceleration;
    }

}

