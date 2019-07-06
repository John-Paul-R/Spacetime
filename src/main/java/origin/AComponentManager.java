package origin;

public abstract class AComponentManager implements IManager {

    protected String strCode;
    protected Minigun self;
    protected DataManager data;

    public void init(Minigun s, DataManager d)
    {
        self = s;
        data = d;
    }
    public void roundStart(Minigun self) {
        this.self = self;
    }
    protected abstract void setStrCode();
}