package origin;

import java.awt.Graphics2D;

import robocode.KeyEvent;

public abstract class AComponentManager {

    private String strCode;
    private Minigun self;
    private Database data;

    public void init(Minigun self, Database data)
    {
        this.self = self;
        this.data = data;
    }

    public abstract void roundStart();

    public abstract void execute();
    
    public abstract void keyControl(KeyEvent e);
    
    public abstract void paint(Graphics2D g, int[] paintOptions);
    
    public abstract void update();
}