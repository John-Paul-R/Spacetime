package origin;

public class SelfState extends ABotState {

    public SelfState(Minigun self) {
        super.init(self.getX(), self.getY(), self.getEnergy(), self.getHeading(), self.getVelocity(), self.getTime());
    }
}