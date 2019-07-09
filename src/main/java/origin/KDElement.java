package origin;

import java.util.List;

public interface KDElement {

    public List<Double> getKDValues();

    public double kdDistanceTo(KDElement otherElement); //ahhhhh should have made this entire thing type 'double' from the start...

    public int compareTo(KDElement otherElement);

    public double getLastKDDist();

	public boolean selectionConditionIsMet(KDElement target);
    
}