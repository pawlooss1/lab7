package agh.cs.lab7;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

abstract public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected LinkedHashMap<Position,Car> cars = new LinkedHashMap<Position,Car>();

    public boolean place(Car car){
        Position carPosition = car.getPosition();
        if(canMoveTo(carPosition)){
            cars.put(carPosition, car);
            return true;
        }
        else
            throw new IllegalArgumentException("Position " + carPosition.toString() + " is already occupied");
    }
    public void run(MoveDirection[] directions){
        if (cars.size() == 0)
            return;
        List<Car> carList = new ArrayList<Car>(cars.values());
        for(int i = 0; i<directions.length; i++){
            Car carToMove = carList.get(i%carList.size());
            carToMove.move(directions[i], this);
        }
    }
    public boolean isOccupied(Position position){
        return this.objectAt(position) != null;
    }
    public Object objectAt(Position position){
        return cars.getOrDefault(position, null);
    }
    public void positionChanged(Position oldPosition, Position newPosition){
        cars.remove(oldPosition);
        cars.put(newPosition, (Car) this.objectAt(newPosition));
    }
}
