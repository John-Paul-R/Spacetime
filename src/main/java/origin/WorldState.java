package origin;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class WorldState extends AWorldState {

    public WorldState() {
        
        this.botStates = new HashMap<String, List<EnemyState>>();
    }
    public WorldState(HashMap<String, Bot> bots) {
        HashMap<String, List<EnemyState>> botStates = new HashMap<String, List<EnemyState>>();
        for (Entry<String, Bot> e : bots.entrySet())
        {
            Bot bot = e.getValue();
            ArrayList<EnemyState> cStates = new ArrayList<EnemyState>(1);
            cStates.add(bot.getCurrentState());
            botStates.put(e.getKey(), cStates);
            System.out.println("WorldState added " + e.getKey() + " to botStates");
        }
        this.botStates = botStates;
    }
    //Commented out and replaced with initBotStatesMap(), as this constructor conflicts with the one above because of how Java handles generics as paramaters
/*  public WorldState(HashMap<String, List<EnemyState>> botStates) {
        this.botStates = botStates;
    } */

    public WorldState predictNextState(HashMap<String,Bot> activeBots) {
        HashMap<String, List<EnemyState>> newEnemyStates = new HashMap<String, List<EnemyState>>(); //EnemyStates that we are about to predict
        //bots.putAll(enemies);//todo add self states to predicitons
        for ( HashMap.Entry<String, Bot> e : activeBots.entrySet()) {
            String name = e.getKey();
            Bot bot = e.getValue();
            
            newEnemyStates.put(name, bot.predictNextState(this, RobotSettings.NUM_BOT_PREDICTION_BRANCHES));
        }
        WorldState out = new WorldState();
        out.initBotStatesMap(newEnemyStates);
        return out;
    }

    public void paintBots(Graphics2D g) {
        int count = 0;
        for (List<EnemyState> el : botStates.values()) {
            for (EnemyState e : el) {
                g.drawRect((int)(e.getX()-18), (int)(e.getY()-18), 36, 36);
                g.drawLine( (int)e.getX(), (int)e.getY(), (int)(e.getX()+36*Math.sin(e.getHeading())), (int)(e.getY()+36*Math.cos(e.getHeading())) );
                count++;
            }
        }
        g.drawChars(String.valueOf(count).toCharArray(), 0, String.valueOf(count).length(), 100, 100);
    }
}
