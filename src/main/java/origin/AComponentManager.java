package origin;

import java.awt.Graphics2D;

import robocode.KeyEvent;

public abstract class AComponentManager implements IManager {

    private String strCode;
    private Minigun self;
    private DataManager data;

    public void init(Minigun self, DataManager data)
    {
        this.self = self;
        this.data = data;
    }
    
}