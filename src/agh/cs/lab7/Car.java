package agh.cs.lab7;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Student33 on 2018-10-15.
 */
public class Car {
    private MapDirection orientation;
    private Position position;
    private LinkedList<IPositionChangeObserver> observers = new LinkedList<IPositionChangeObserver>();

    public Car(IWorldMap map){
        Position defaultPosition = new Position(2, 2);
        if(map.canMoveTo(defaultPosition)) {
            this.orientation = MapDirection.NORTH;
            this.position = defaultPosition;
        }
        else
            throw new IllegalArgumentException("Position " + defaultPosition.toString() + " is already occupied");
    }
    public Car(IWorldMap map, Position initialPosition){
        if(map.canMoveTo(initialPosition)) {
            this.orientation = MapDirection.NORTH;
            this.position = initialPosition;
        }
        else
            throw new IllegalArgumentException("Position " + initialPosition.toString() + " is already occupied");
    }
    public Car(IWorldMap map, int x, int y){
        Position initialPosition = new Position(x, y);
        if(map.canMoveTo(initialPosition)) {
            this.orientation = MapDirection.NORTH;
            this.position = initialPosition;
        }
        else
            throw new IllegalArgumentException("Position " + initialPosition.toString() + " is already occupied");
    }
    public Position getPosition(){
        return this.position;
    }
    public String toString(){
        switch (this.orientation){
            case NORTH:
                return "^";
            case SOUTH:
                return ",";
            case EAST:
                return "<";
            case WEST:
                return ">";
        }
        return "";
    }
    public void move(MoveDirection direction, IWorldMap map){
        if(direction == MoveDirection.LEFT) {
            this.orientation = this.orientation.previous();
            return;
        }
        if(direction == MoveDirection.RIGHT) {
            this.orientation = this.orientation.next();
            return;
        }
        int delta_x = 0;
        int delta_y = 0;
        switch (this.orientation) {
            case NORTH:
                delta_y = 1;
                break;
            case SOUTH:
                delta_y = -1;
                break;
            case EAST:
                delta_x = 1;
                break;
            case WEST:
                delta_x = -1;
                break;
        }
        Position delta = new Position(delta_x, delta_y);
        Position newPosition;
        if(direction == MoveDirection.FORWARD)
            newPosition = this.position.add(delta);
        else
            newPosition = this.position.sub(delta);
        if(map.canMoveTo(newPosition))
            updatePosition(this.position, newPosition);
            this.position = newPosition;
    }
    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }
    public void updatePosition(Position oldPosition, Position newPosition){
        for(IPositionChangeObserver observer: observers){
            observer.positionChanged(oldPosition, newPosition);
        }
    }
    public static void main(String[] args){
        try {
            MoveDirection[] directions = new OptionsParser().parse(args);


            List<HayStack> myHayStacks = new ArrayList<HayStack>();
            Position positionOfMyStack = new Position(7, -4);
            myHayStacks.add(new HayStack(positionOfMyStack));
            positionOfMyStack = new Position(-4, 7);
            myHayStacks.add(new HayStack(positionOfMyStack));
            positionOfMyStack = new Position(3, 6);
            myHayStacks.add(new HayStack(positionOfMyStack));
            positionOfMyStack = new Position(2, 0);
            myHayStacks.add(new HayStack(positionOfMyStack));

            IWorldMap map = new UnboundedMap(myHayStacks);
            map.place(new Car(map));
            map.place(new Car(map, 4, 4));
            map.run(directions);
            System.out.print(map.toString());
        } catch (IllegalArgumentException ex){
            System.out.println(ex);
            System.exit(1);
        }
    }

}
