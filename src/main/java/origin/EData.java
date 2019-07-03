package origin;

import robocode.ScannedRobotEvent;
import origin.Minigun;

class EData implements Comparable<EData> {
    double x,y,h,v,d,ab,en,b,cwd,cwb,t;//x, y, heading, velocity, energy, bearing, closest wall dist, closest wall bearing, time of data
    double td,wti,ldd,wlh;//time since last decel, wave time to impact, last dodge direction, waves since last hit
    
    double lastDist;
    
    EData(ScannedRobotEvent e, EData p, Minigun self)
    {
        b=e.getBearingRadians();
        h=e.getHeadingRadians();
        v=e.getVelocity();
        d=e.getDistance();
        en=e.getEnergy();

        ab =(b+self.getHeadingRadians());
        x=self.getX()+Math.sin(ab)*d;
        y=self.getY()+Math.cos(ab)*d;
        t=self.getTime();
        if (p!=null)
        {

        }
    }
    EData(EData start, EData nearest, EData next)
    {
        double[] a = start.getDataArr();
        double[] b = nearest.getDataArr();
        double[] c = next.getDataArr();
        
        



        this.b= a[0]+(c[0]-b[0]);
        h=      robocode.util.Utils.normalRelativeAngle(a[1]+(c[1]-b[1]));
        v=      a[2]+(c[2]-b[2]);
        d=      a[3]+(c[3]-b[3]);
        en=     a[4]+(c[4]-b[4]);
        ab=     a[5]+(c[5]-b[5]);
        x=      (c[6]-b[6]);
        y=      (c[7]-b[7]);
        
        t=      a[8]+(c[8]-b[8]);
        double distTraveled = Math.sqrt(x*x + y*y);
        double travelDirection = Math.atan2(x, y);
        x = a[6]+(distTraveled * Math.sin(travelDirection - b[1] + h));
        y = a[7]+(distTraveled * Math.cos(travelDirection - b[1] + h));
    }
    public double[] getDataArr()
    {
        return new double[] {b,h,v,d,en,ab,x,y,t};
    }
    public double distanceTo(EData other)
    {
        double hw = Math.pow((h-other.h)/Math.PI/2/10, 2);//robocode.util.Utils.normalAbsoluteAngle(h-other.h)/Math.PI/2/10, 2);
        //double vw = Math.pow((v-other.v)/800, 2);
        double vw = Math.pow((Math.abs(v)-Math.abs(other.v))/800, 2);
        double tdw = 0;Math.pow((td-other.td)/10000, 2);
        double dw = 0;Math.pow((d-other.d)/10000, 2);

        double eDist = Math.sqrt(hw + vw + tdw + dw);

        lastDist = eDist;

        return eDist;
    }
    public double getLastDist()
    {
        return lastDist;
    }
    @Override
    public int compareTo(EData other)
    {

        return (int)(this.getLastDist() - other.getLastDist());

    }
}