package origin;

import java.awt.Graphics2D;

import robocode.KeyEvent;

public abstract class AComponentManager implements IManager {

    protected String strCode;
    protected Minigun self;
    protected DataManager data;

    public void init(Minigun s, DataManager d)
    {
        self = s;
        data = d;
    }
    
    protected abstract void setStrCode();
}