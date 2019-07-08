package origin;

import java.util.Collection;
import java.util.HashMap;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Map.Entry;

import origin.*;

public class WorldState implements Predictable<WorldState> {

    private int time;
    private HashMap<String, BotState> botStates;
    HashMap<String, Bot> activeBots;

    public WorldState(HashMap<String, Bot> activeBots, boolean doInitialize) {
        if (doInitialize) {
            HashMap<String, BotState> botStates = new HashMap<String, BotState>();
            HashMap<String, Bot> bots = activeBots;
            for (Entry<String, Bot> e : bots.entrySet())
            {
                Bot bot = e.getValue();
                botStates.put(e.getKey(), bot.getCurrentState());
                System.out.println("WorldState added " + e.getKey() + " to botStates");
            }
            this.botStates = botStates;
            this.activeBots = activeBots;
        } else {
            this.time = 0;
            this.botStates = new HashMap<String, BotState>();
            this.activeBots = activeBots;
        }
    }
    public WorldState(int time, HashMap<String, BotState> botStates, HashMap<String, Bot> activeBots) {
        this.time = time;
        this.botStates = botStates;
        this.activeBots = activeBots;
    }

    public HashMap<String, BotState> getInRange(double range, Point2D.Double location) {
        double x = location.getX();
        double y = location.getY();
        HashMap<String, BotState> out = new HashMap<String, BotState>();
        for (Entry<String, BotState> en : botStates.entrySet()) {
            BotState e = en.getValue();
            //System.out.println(e);
            if (Point2D.distance(x, y, e.getX(), e.getY()) <= range) {
                out.put(en.getKey(), e);
            }
        }
        //System.out.println("Completed 'getInRange'");
        return out;
    }

    public void addBotStates(HashMap<String, BotState> states) {
        botStates.putAll(states);
    }
    public Collection<BotState> getBotStates() {
        return botStates.values();
    }

    //@Override
    public WorldState predictNextState() {
        HashMap<String, Bot> enemies = activeBots;
        HashMap<String, Bot> bots = new HashMap<String, Bot>();
        HashMap<String, BotState> newBotStates = new HashMap<String, BotState>(); //BotStates that we are about to predict
        HashMap<String, BotState> botStates = this.botStates; //Current BotStates (stored globally in this WorldState)
        int cTime = this.time;
        //bots.putAll(enemies);//todo add self states to predicitons
        for ( HashMap.Entry<String, Bot> e : enemies.entrySet()) {
            String name = e.getKey();
            Bot bot = e.getValue();
            
            newBotStates.put(name, bot.predictNextState(this));

        }

        return new WorldState(cTime+1, newBotStates, activeBots);
    }

    public int getTime() {
        return time;
    }

    public void paintBots(Graphics2D g) {
        for (BotState e : botStates.values())
        {
            g.drawRect((int)(e.getX()-18), (int)(e.getY()-18), 36, 36);
        }
    }
}
