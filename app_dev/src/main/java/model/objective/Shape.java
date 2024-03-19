package model.objective;

import model.placementArea.Coordinates;
import model.placementArea.Coordinates;
import model.enums.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

//IMPORTANT
// every shape has a set of coordinates, referred to a reference card, the reference card is the top/bottom right/left
// depending on the type of objective: we should start checking in the placement area from the top (right/left depending on the objective)
// except when checking for
// LShape objectives, for them we should start to check from the bottom (right/left depending on the objective)

//Shape has a list of relative coordinates, they represent the offset from a standard point
public class Shape {
   private HashMap<Coordinates, Element> shape;
   private String shapeName;

    public HashMap<Coordinates, Element> getShape() {return shape;}

    public String getShapeName() {return shapeName;}

    //returns the element corresponding to the position (x,y) in the shape
    public Element getShapeElement(Coordinates coordinates){return shape.get(coordinates);}

    //returns the List of relative Coordinates belonging to the shape
    public List<Coordinates> getShapeShadow(){return new ArrayList<>(shape.keySet());}

    public int getLeftestX(){return shape.keySet().stream().map(Coordinates::getX).min((x, y)->x<y? x : y).orElse(0);}
    public int getRightestX(){return shape.keySet().stream().map(Coordinates::getX).max((x, y) -> x>y? x : y).orElse(0);}
    public int getHighestY(){return shape.keySet().stream().map(Coordinates::getY).max((x, y) -> x>y? x : y).orElse(0);}
    public int getLowestY(){return shape.keySet().stream().map(Coordinates::getY).min((x, y)->x<y? x : y).orElse(0);}
}

