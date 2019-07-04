package origin;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import origin.world.Bot;
import robocode.KeyEvent;

public class DataManager {

    private Minigun self;
    private Bot<SelfState> selfBot;
    private HashMap<String, Bot<EnemyState>> activeBots;
    private HashMap<String, Bot<EnemyState>> disabledBots;
    private String selfName;

    public DataManager(Minigun self) {
        this.self = self;
        selfBot = new Bot<SelfState>();
        selfName = "self";
        activeBots = new HashMap<String, Bot<EnemyState>>();
        disabledBots = new HashMap<String, Bot<EnemyState>>();
    }

    public void roundStart() {
        //add all bots to active map and remove all bots from disabled list
        activeBots = getAllBots();
        disabledBots.clear();
    }

    public HashMap<String, Bot<EnemyState>> getActiveBots() {
        return activeBots;
    }
    public HashMap<String, Bot<EnemyState>> getDisabledBots() {
        return activeBots;
    }
    public HashMap<String, Bot<EnemyState>> getAllBots() {
        HashMap<String, Bot<EnemyState>> allBots = new HashMap<String, Bot<EnemyState>>();
        allBots.putAll(activeBots);
        allBots.putAll(disabledBots);

        return allBots;
    }

    public void update() {
        selfBot.addState(self.getCurrentState());
    }

    public void keyControl(KeyEvent e) {

    }

    public void paint(Graphics2D g, int[] paintOptions) {

    }

	public Bot<SelfState> getSelfBot() {
		return selfBot;
	}

	public String selfName() {
		return selfName;
	}


}