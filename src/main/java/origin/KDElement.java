package origin;

import java.util.List;

public interface KDElement<E extends Number>  {

    public List<E> getKDValues();

    public double kdDistanceTo(KDElement<E> otherElement); //ahhhhh should have made this entire thing type 'double' from the start...

    public int compareTo(KDElement<E> otherElement);

	public int getLastKDDist();
}