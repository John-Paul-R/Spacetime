/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package origin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import robocode.*;
import robocode.util.Utils;
public class Minigun extends AdvancedRobot {
    
    static LinkedList<EData> el;
    double sd=1;
    boolean scanned = false;
    double /*_x, _y,*/_w, _h;
    //long _t;
    long time;
    final static int _k = 2;
    double[] px=new double[_k], py=new double[_k];
    double[][] p;
    double xx=0, yy=0;
    static double[] maxValArr;
    static boolean useKNN = false;
    private static DataManager data;
    private static AComponentManager
        gun,
        radar,
        move;

    private void init() {
        setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
        setTurnRadarRightRadians(4*Math.PI);
        _w=getBattleFieldWidth();
        _h=getBattleFieldHeight();

        data = new DataManager(this);
        radar = new RadarManager();
        move = new MoveManager();
        gun = new GunManager();

        radar.init(this, data);
        move.init(this, data);
        gun.init(this, data);
        
        if (getRoundNum()<1) {
            el = new LinkedList<EData>();
            initKNN();
        }
    }

    private void roundStart() {
        data.roundStart();
        radar.roundStart();
        move.roundStart();
        gun.roundStart();
    }

    public void run() {
        //Round One Only
        if (getRoundNum() == 0) {
            this.init();
        }
        
        //Each New Round
        this.roundStart();

        //Main Loop (Each Turn)
        while(true)
        {
            //Update info in main bot object about position & other variables so that we can use
            //the overrided methods (like getX()) (thereby avoiding repeated calls to Robocode client)
            //This also occurs in the onScannedRobot method, as robots move AFTER execute()
            //(cant put this right after execute() however, because robots are paused until its completion)
            this.update();
            //Update Components (Change modes if needed)
            data.update();
            radar.update();
            move.update();
            gun.update();

            //execute all
            radar.execute();
            move.execute();
            gun.execute();

            EData c = null;
            if (el.size()>100) {
                useKNN = true;
            }
            if (el.size()>0)
                c=el.getLast();
            if (el.size()>0 && !useKNN) {
                //System.out.println("["+getTime()+"] Firing with HOT");
                    //p=el.get(el.size()-2);
                    double a=Utils.normalRelativeAngle(c.ab-getGunHeadingRadians());
                    setTurnGunRightRadians(a);
                setFire(2.1);
            } else if (useKNN) {//Begin using knn gun
                //System.out.println("["+getTime()+"] Firing with KNN");
                long t=1;
                EData cState = c;
                ArrayList<EData> cStates = null;
                p = new double[100][2];
                while (Rules.getBulletSpeed(2.1)*t < Point2D.distance(cState.x, cState.y, getX(), getY())) //predict into the future far enough (for a given bullet speed)
                {
                    //try {
                    ArrayList<EData> ncStates = knn(_k, cState);
                    int i = 0;
                    int nextIndex = Integer.MAX_VALUE;
                    while (nextIndex >= el.size())
                    {
                        nextIndex = el.indexOf(ncStates.get(i))+1;
                    }
                    {
                        
                        if (ncStates != null)
                        {
                            cStates = ncStates;
                        }
                        cState = new EData(cState, ncStates.get(0), el.get(nextIndex));
                        xx = cState.x;
                        yy = cState.y;
                        //System.out.println("TEST");
                        if (t <= 100) {
                            p[(int)t-1] = new double[] {xx, yy};
                        }
                    }
                        
                        //cState = ;
                    //} catch (Exception e)
                    //{
                    //    System.out.println("["+getTime()+"] Hit list time boundary.");
                    //}
                    
                    t++;
                    //System.out.println(t);

                }
                //cStates = knn(_k, cState);
                
                for (int i=0; i < _k; i++)
                {
                    if (cStates != null)
                    {
                        EData cS = null;
                        EData c1 = cStates.get(i);
                        int nIndex = el.indexOf(c1)+1;
                        if (nIndex < el.size())
                        {
                            cS = new EData(cState, c1, el.get(el.indexOf(c1)+1));
                            px[i]=cS.x;
                            py[i]=cS.y;
                        }
                        
                        //System.out.println("test");
                    }
                }
                double a=Utils.normalRelativeAngle(Math.atan2(cState.x-getX(), cState.y-getY())-getGunHeadingRadians());
                setTurnGunRightRadians(a);
            }
            //System.out.println(c);
            if (!scanned)
            {
                System.out.println("Not scanned on turn " + getTime() +"!");
                setTurnGunRightRadians(Rules.GUN_TURN_RATE_RADIANS);
                setTurnRadarRightRadians(Rules.RADAR_TURN_RATE_RADIANS);
                setTurnRightRadians(Rules.MAX_TURN_RATE_RADIANS);
            }
                
            scanned = false;
            setFire(2.1);
            execute();
        }
    }

    private static boolean showGraphs = true;
    public void onKeyPress(KeyEvent e) {
        if (e.getSourceEvent().getKeyChar() == 'q') {
            showGraphs = !showGraphs;
        }
    }
    public void onPaint(Graphics2D g) {
        if (useKNN) {
            //predicted paths
            final int osize = 10;
            final int ohsize = osize / 2;
            final int rsize = 36;
            final int rhsize = rsize / 2;
            int iPath = 0;
            g.setColor(new Color(255, 255, 255, 50));
            while (iPath < p.length && p[iPath][0] != 0.0D && p[iPath][1] != 0.0D) {
                g.fillOval((int)p[iPath][0]-ohsize, (int)p[iPath][1]-ohsize, osize, osize);
                g.fillRect((int)p[iPath][0]-rhsize, (int)p[iPath][1]-rhsize, rsize, rsize);
                iPath++;
            }
    
            //predicted endpoints
            g.setColor(Color.RED);
            for (int i=0; i < _k; i++)
            {
                g.drawOval((int)px[i]-18, (int)py[i]-18, 36, 36);
    
            }
            //closest neighbor endpoint
            g.setColor(Color.GREEN);
            g.drawOval((int)xx-18, (int)yy-18, 36, 36);
        
            if (showGraphs)
            {
                
                
                int w = (int)_w, h = (int)_h;
                final int[][] gDims = new int[][] {{1000, 300}};
                final int[][] gLoc  = new int[][] {{w-gDims[0][0], 0}};
                final int pointSize = 4;
                final int hpointSize = pointSize/2;

                g.drawLine(gLoc[0][0], gLoc[0][1], gLoc[0][0], gLoc[0][1]+gDims[0][1]);
                g.drawLine(gLoc[0][0], gLoc[0][1], gLoc[0][0]+gDims[0][0], gLoc[0][1]);

                double step = (double)gDims[0][0]/(double)el.size();
                double x = 0;
                int ys[][] = new int[3][el.size()];
                int xs[] = new int[el.size()];
                int count = 0;
                for (EData e : el)
                {
                    ys[0][count] = (int)(e.getLastDist()*1000);
                    ys[1][count] = (int)(e.h*10);
                    ys[2][count] = (int)(e.v*10);
                    //ys[2][count] = (int)(e.*10);
                    x += step;
                    xs[count] = (int)x;
                    //g.fillOval((int)(gLoc[0][0] +x -hpointSize), (int)(gLoc[0][1] +y -hpointSize), pointSize, pointSize);
                    //System.out.println(step + "\t\t\t" + x);
                    count++;
                }
                g.setColor(new Color(150, 255, 150, 200));
                g.drawPolyline(xs, ys[0], ys[0].length);
                g.setColor(new Color(255, 150, 150, 200));
                g.drawPolyline(xs, ys[1], ys[1].length);
                g.setColor(new Color(150, 150, 255, 200));
                g.drawPolyline(xs, ys[2], ys[2].length);
                //g.setColor(new Color(255, 150, 255, 200));
                //g.drawPolyline(xs, ys[3], ys[3].length);
            }
        }

        
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        this.update();
        if(el.size()>0) {
            el.add(new EData(e, el.getLast(), this));
        } else {
            el.add(new EData(e, null, this));
        }
        sd*=-1;
        double an = Utils.normalRelativeAngle(el.getLast().ab-getRadarHeadingRadians());
        setTurnRadarRightRadians(an+Math.signum(an)*Math.PI/8);
        //if (an==0)
        //setTurnRadarRightRadians(2*Math.PI);
        scanned = true;
    }

    public void initKNN() {
        maxValArr=new double[_k];
        for (int i=0; i < _k; i++)
        {
            maxValArr[i] = Double.MAX_VALUE;
        }
    }
    
    public ArrayList<EData> knn(int k, EData state) {
        LinkedList<EData> eL = el;
        EData c = state;
        ArrayList<EData> knn = new ArrayList<EData>(k);
        double[] nDist = maxValArr.clone();
        double nDistMax = Double.MAX_VALUE;
        int maxIndex = k-1;

        for (EData e : eL)
        {
            if (e.t!=c.t)
            {
                
                double eDist = e.distanceTo(c);
                if (e.getLastDist() < nDistMax)
                {
                    //System.out.println(knn.size());
                    EData max = null;
                    if (knn.size() == k)
                    {
                        max = Collections.max(knn);
                        knn.remove(max);
                        
                        //IDK IF THIS IS WHERE THIS SHOULD GO:
                        //nDist[maxIndex] = eDist;
                        //maxIndex = getMaxIndex(nDist);
                        max =Collections.max(knn);
                        nDistMax = max.getLastDist();
                    }
                        
                    knn.add(e);
                    
                    
                }

                


                /* for (int i=0; i < k, i++)
                {
                    if (eDist < nDist[i])
                    {
                        knn[i]=e;
                        nDist = eDist[i];
                    }
                } */

            }
            
        }
        knn.sort(new Comparator<EData>() {
                    
            public int compare(EData d1, EData d2)
            {
                return (int)(d2.getLastDist() - d1.getLastDist());
            }
        });
        return knn;
    }
    
    private int getMaxIndex(double[] arr) {
        double max = Double.MIN_VALUE;
        int mIndex = -1;
        for (int i=0; i < arr.length; i++)
        {
            if (arr[i] > max)
            {
                mIndex = i;
                max = arr[i];
            }
        }
        return mIndex;
    }


    private double
        x,
        y,
        heading,
        gunHeading,
        radarHeading;

    private void update() {
        x = super.getX();
        y = super.getY();
        heading = super.getHeadingRadians();
        gunHeading = super.getGunHeadingRadians();
        radarHeading = super.getRadarHeadingRadians();
        time = super.getTime();
        //System.out.println(heading);
    }


    //Overrides
    @Override
    public double getX() {
        return this.x;
    }
    @Override
    public double getY() {
        return this.y;
    }
    @Override
    public double getHeadingRadians() {
        return this.heading;
    }
    @Override
    public double getHeading() {
        return this.heading;
    }
    @Override
    public double getGunHeadingRadians() {
        return this.gunHeading;
    }
    @Override
    public double getGunHeading() {
        return this.gunHeading;
    }
    @Override
    public double getRadarHeadingRadians() {
        return this.radarHeading;
    }
    @Override
    public double getRadarHeading()
    {
        return this.radarHeading;
    }
    @Override
    public long getTime()
    {
        return this.time;
    }

	public SelfState getCurrentState() {
		return null;
	}
    
}
