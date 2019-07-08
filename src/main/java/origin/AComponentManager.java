package origin;

public abstract class AComponentManager implements IManager {

    protected String strCode;
    protected static Minigun self;
    protected static DataManager data;

    public void init(Minigun s, DataManager d)
    {
        self = s;
        data = d;
    }
    public void roundStart(Minigun s) {
        self = s;
    }
    protected abstract void setStrCode();
}