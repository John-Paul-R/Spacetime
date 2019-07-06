package origin;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;

import robocode.KeyEvent;
import robocode.Rules;
import origin.world.*;

public class GunManager extends AComponentManager {

    enum TargeterENUM {
        ALL,
        HEURISTIC_1,
        CLOSEST,
        GREATEST_THREAT,
        LEAST_THREAT;
    }
    enum PredictionMethod {
        NONE,//HOT equivalent (but will run slower, as it still goes through the prediction loop until the bullet would hit)
        LINEAR,
        CIRCULAR,
        AVG,
        KNN_PIF,
        KNN_DISPLACEMENT;//This works like a guess factor system would, interested only in where they are at the time the bullet would hit (relative to where they were when it was fired)
    }
    enum GMode {
        PIF, //Any form of iterative prediction. Takes in a state, gives you the next state based on the PredictionMethod provided. Repeats until a condition (provided by the TargetMode) is met (usually until the bullet would have hit the farthest possible prediciton)
        EQUATION,   //Allows one to save CPU time compared to PIF. Only applicable for methods where equation is known (like in circular, linear, avg, or regressions)
        KNN_DISPLACEMENT; //KNN_DISPLACEMENT is unique and needs its own GunMode... (Not sure this is true, will figure out when I actually make it)
    }
    class PredictionSynthesizer {
        private WorldState workingState;
        private boolean isIncomplete;
        PredictionSynthesizer() {
            workingState = new WorldState(data.getActiveBots(), false);
            isIncomplete = true;
        }
        boolean isIncomplete() {
            return isIncomplete;
        }
        void updateWorkingState(int bulletTime, Point2D.Double selfLocation, double bulletVelocity, WorldState newState) {
            double range = bulletTime*bulletVelocity;
            HashMap<String, BotState> botStates = newState.getInRange(range, selfLocation);
            if (botStates.size() == newState.getBotStates().size())
            {
                isIncomplete = false;
            }
            workingState.addBotStates(botStates);
        }
        WorldState getFinalState() {
            return workingState;
        }
    }
    abstract static class ALLGunMode {
        static final class PIF {
            //NOTE, This applies the prediction to EVERY BOT in the world
            //ADDITIONALLY, it is up to the state to provide support for the various prediction methods (in the form of a predictNextState(PredictionMethod m) method).
            static WorldState predictState(WorldState state, PredictionMethod method, PredictionSynthesizer predictor, Point2D.Double fireLocation, double bulletVelocity) {
                //Point2D.Double predictedLocation = null;
                Point2D.Double myLocation = fireLocation;
                WorldState cState = state;
                int timeCount = 1;
                // TimeSegmentedWorld
                while (predictor.isIncomplete()) {
                    predictor.updateWorkingState(timeCount, myLocation, bulletVelocity, cState);
                    cState = cState.predictNextState();
                    timeCount++;
                }
                
                return predictor.getFinalState();
            }
        }
        static final class EQUATION {
            
        }
    }

    private WorldState predictedState;
    //Predictor method. Takes a state, then calls a certain type of predictor to get the next state (circular, linear, knn, regression, etc.)
    GunManager() {

    }

	@Override
	public void roundStart() {
		
	}

	@Override
	public void update() {
        /*
        //Set current state to target DEPRECATED
        if (el.size()>0) {
            c=el.getLast();
        }
        //Switch fire mode to KNN after condition is met
        
        if (el.size()>100) {
            useKNN = true;
        }
        */
    }

	@Override
	public void execute() {
        predictedState = ALLGunMode.PIF.predictState(new WorldState(data.getActiveBots(), true), PredictionMethod.KNN_PIF, new PredictionSynthesizer(), new Point2D.Double(self.getX(), self.getY()), Rules.getBulletSpeed(2.1));
        //BEGIN Gun
        //Todo virtual guns
        /*
        EData c = null;
        if (el.size()>0 && !useKNN) {
            //System.out.println("["+getTime()+"] Firing with HOT");
                //p=el.get(el.size()-2);
                double a=Utils.normalRelativeAngle(c.ab-getGunHeadingRadians());
                self.setTurnGunRightRadians(a);
            setFire(2.1);
        } else if (useKNN) {
            //Knn Fire mode
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
                while (nextIndex >= el.size()) {
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
        */
        //END Gun
	}

	@Override
	public void keyControl(KeyEvent e) {
		
	}

	@Override
	public void paint(Graphics2D g, int[] paintOptions) {
		predictedState.paintBots(g);
	}

    protected void setStrCode() {
        super.strCode = "GUN_MANAGER";
    }
    String getStrCode() {
        return super.strCode;
    }


}
