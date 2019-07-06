package origin;

import java.util.List;

import robocode.ScannedRobotEvent;

public class EnemyState extends BotState implements KDElement {

    private double
        bearing, //relative to our bot, -pi to pi
        absBearing, //global, 0 to 2pi
        lateralBearing, /* Derivative stat. Is the bearing of the enemy bot relative to the straight line from us to them.
                        NOTE: Consider assuming that they are always facing us (thus limiting the angles to -pi/2 to pi/2 or 0 to pi)*/
        distance;

    //Secondary variables (Note: tsl = time since last)
    int tslBulletFired;
    int tslDeceleration;

    //Dynamic variables (Essentially a mini cache, so that things do not need to be recalculated within one turn)
    private double lastDist;
    private int turnCalculatedLastDist;

    //Utility
    protected EnemyState prevState;
    protected EnemyState nextState;

    public EnemyState(double x, double y, double energy, double heading, double velocity, long time) {
        super.init(x, y, energy, heading, velocity, time);
    }
    public EnemyState(ScannedRobotEvent e, Minigun self) {
        
        bearing = e.getBearingRadians();
        double heading = e.getHeadingRadians();
        double velocity = e.getVelocity();
        distance = e.getDistance();
        double energy = e.getEnergy();
        
        absBearing  = (bearing+self.getHeadingRadians());
        double x    = self.getX()+Math.sin(absBearing)*distance;
        double y    = self.getY()+Math.cos(absBearing)*distance;
        long time   = self.getTime();
        
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
    public double getLastDist() { //TODO update this so that it checks if dist needs to be updated
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
    }
    public void setPrevState(EnemyState prevState) {
        this.prevState = prevState;
    }


    @Override
    public double kdDistanceTo(KDElement otherElement) {
        return 0;
    }
    @Override
    public int compareTo(KDElement otherElement) {
        return 0;
    }
    @Override
    public List<Double> getKDValues() {
        return null;
    }
    @Override
    public int getLastKDDist() {
        return 0;
    }

}

