package origin;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import origin.world.Bot;
import robocode.KeyEvent;

import robocode.ScannedRobotEvent;

public class DataManager {

    private Minigun self;
    //private Bot<SelfState> selfBot;
    private HashMap<String, Bot> activeBots;
    private HashMap<String, Bot> disabledBots;
    private String selfName;

    public DataManager(Minigun self) {
        this.self = self;
        //selfBot = new Bot<SelfState>();
        selfName = "self";
        activeBots = new HashMap<String, Bot>();
        disabledBots = new HashMap<String, Bot>();
    }

    public void roundStart() {
        //add all bots to active map and remove all bots from disabled list
        activeBots = getAllBots();
        disabledBots.clear();
    }

    public HashMap<String, Bot> getActiveBots() {
        return activeBots;
    }
    public HashMap<String, Bot> getDisabledBots() {
        return disabledBots;
    }
    public HashMap<String, Bot> getAllBots() {
        HashMap<String, Bot> allBots = new HashMap<String, Bot>();
        allBots.putAll(activeBots);
        allBots.putAll(disabledBots);

        return allBots;
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if (!activeBots.containsKey(e.getName())) {
            activeBots.put(e.getName(), new Bot());
        }
        activeBots.get(e.getName()).addState(new EnemyState(e, self));
    }

    public void update() {
        //selfBot.addState(self.getCurrentState());
    }

    public void keyControl(KeyEvent e) {

    }

    public void paint(Graphics2D g, int[] paintOptions) {

    }

	//public Bot<SelfState> getSelfBot() {
    //return selfBot;
	//}

	public String selfName() {
		return selfName;
	}


}