package origin.world;

import java.util.Collection;
import java.util.HashMap;

import origin.BotState;
import origin.DataManager;
import origin.EnemyState;
import origin.SelfState;
import robocode.ScannedRobotEvent;

public class WorldState implements ITimeDependent<WorldState> {

    private int time;
    private HashMap<String, BotState> botStates;

    public WorldState(int time, HashMap<String, BotState> botStates) {
        this.time = time;
        this.botStates = botStates;
    }

    //@Override
    public WorldState predictNextState(DataManager data) {
        HashMap<String, Bot<EnemyState>> enemies = data.getActiveBots();
        Bot<SelfState> sBot = data.getSelfBot();
        HashMap<String, Bot<? extends BotState>> bots = new HashMap<String, Bot<? extends BotState>>();
        HashMap<String, BotState> newBotStates = new HashMap<String, BotState>(); //BotStates that we are about to predict
        HashMap<String, BotState> botStates = this.botStates; //Current BotStates (stored globally in this WorldState)
        int cTime = this.time;
        bots.putAll(enemies);
        bots.put(data.selfName(), sBot);
        for ( HashMap.Entry<String, Bot<EnemyState>> e : enemies.entrySet()) {
            String name = e.getKey();
            Bot<EnemyState> bot = e.getValue();
            
            bot.predictNextState(this);

        }
        return null;
    }

    public int getTime() {
        return time;
    }
}
