package origin;

import java.awt.Graphics2D;

import robocode.KeyEvent;

interface IManager {

    public abstract void roundStart(Minigun self);

    public abstract void update();

    public abstract void execute();
    
    public abstract void keyControl(KeyEvent e);
    
    public abstract void paint(Graphics2D g, int[] paintOptions);
    
}