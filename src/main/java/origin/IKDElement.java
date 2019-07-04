package origin;

import java.util.List;

public interface IKDElement<E extends Comparable<E>>  {

    public List<E> getKDValues();

    public E kdDistanceTo(IKDElement<E> otherElement);

    public int compareTo(IKDElement<E> otherElement);
}