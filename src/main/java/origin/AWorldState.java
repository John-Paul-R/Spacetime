package origin;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public abstract class AWorldState {

    protected HashMap<String, List<EnemyState>> botStates;

    public abstract WorldState predictNextState(HashMap<String, Bot> bots);

    public abstract void paintBots(Graphics2D g);

    public void addBotStates(HashMap<String, List<EnemyState>> states) {
        botStates.putAll(states);
    }
    public void addSingleBotStates(HashMap<String, EnemyState> states) {
        for (Entry<String, EnemyState> en : states.entrySet()) {
            botStates.get(en.getKey()).add(en.getValue());
        }
    }
    
    public void initBotStatesMap(HashMap<String, List<EnemyState>> botStates) {
        this.botStates = botStates;
    }

    public HashMap<String, List<EnemyState>> getStatesInRange(double range, Point2D.Double location) {
        double x = location.getX();
        double y = location.getY();
        HashMap<String, List<EnemyState>> out = new HashMap<String, List<EnemyState>>();
        for (Entry<String, List<EnemyState>> en : botStates.entrySet()) {
            LinkedList<EnemyState> eStates = new LinkedList<EnemyState>();
            int count = 0;
            for ( EnemyState eState : en.getValue()) {
                System.out.println("count: " + count);
                count++;
                if (Point2D.distance(x, y, eState.getX(), eState.getY()) <= range) {
                    eStates.add(eState);
                }
            }
            out.put(en.getKey(), eStates);

        }
        //System.out.println("Completed 'getInRange'");
        return out;
    }

    public LinkedList<EnemyState> getAllBotStatesUnlinked() {
        LinkedList<EnemyState> states = new LinkedList<EnemyState>();
        for ( List<EnemyState> e : botStates.values() ) {
            states.addAll(e);
        }
        return states;
    }
    
    public ArrayList<EnemyState> getBotStates(String name, int numStates) {
        List<EnemyState> list = botStates.get(name);
        ArrayList<EnemyState> out = new ArrayList<EnemyState>(numStates);//TODO make sure this list contains the "nearest"
        for (int i=0; i<list.size(); i++) {
            out.add(list.get(i));
        }
        return out;
    }

    public List<EnemyState> getBotStateList(String name) {
        return botStates.get(name);
    }

}