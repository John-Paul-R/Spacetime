package origin;

import robocode.ScannedRobotEvent;

public class EnemyState extends ABotState {

    private double
        bearing, //relative to our bot, -pi to pi
        absBearing, //global, 0 to 2pi
        lateralBearing, /*Derivative stat. Is the bearing of the enemy bot relative to the straight line from us to them.
                        NOTE: Consider assuming that they are always facing us (thus limiting the angles to -pi/2 to pi/2 or 0 to pi)*/
        distance;

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

}

