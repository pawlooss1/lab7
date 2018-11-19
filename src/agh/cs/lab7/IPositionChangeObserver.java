package agh.cs.lab7;

/**
 * Created by student33 on 2018-11-19.
 */
public interface IPositionChangeObserver {
    void positionChanged(Position oldPosition, Position newPosition);
}
