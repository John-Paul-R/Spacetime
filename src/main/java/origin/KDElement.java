package origin;

import java.util.List;

public interface KDElement<E extends Number>  {

    public List<E> getKDValues();

    public E kdDistanceTo(KDElement<E> otherElement);

    public int compareTo(KDElement<E> otherElement);
}