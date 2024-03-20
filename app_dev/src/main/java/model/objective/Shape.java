package model.objective;

import model.placementArea.Coordinates;

import java.util.Arrays;
import java.util.List;

/*
shape has relative coordinates to a precise card of the pattern, it will be given as parameter to the method of the placement area searching for specific patterns
 */
public enum Shape {
    TOPRIGHTL{
        private final Coordinates[] COORDINATES = new Coordinates[]{new Coordinates(0, 0), new Coordinates(-1, -1), new Coordinates(-1, -2)};
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    },
    TOPLEFTL{
        private final Coordinates[] COORDINATES = new Coordinates[]{new Coordinates(0, 0), new Coordinates(+1, -1), new Coordinates(+1, -2)};
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};

    },
    BOTTOMRIGHTL{
        private final Coordinates[] COORDINATES = new Coordinates[]{new Coordinates(0, 0), new Coordinates(-1, +1), new Coordinates(-1, +2)};
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    },
    BOTTOMLEFTL{
        private final Coordinates[] COORDINATES = new Coordinates[]{new Coordinates(0, 0), new Coordinates(+1, +1), new Coordinates(+1, +2)};

        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    },
    ASCENDINGDIAGONAL{
        private final Coordinates[] COORDINATES = new Coordinates[]{new Coordinates(0, 0), new Coordinates(-1, -1), new Coordinates(-2, -2)};
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    },
    DESCENDINGDIAGONAL{
        private final Coordinates[] COORDINATES = new Coordinates[]{new Coordinates(0, 0), new Coordinates(-1, +1), new Coordinates(-2, +2)};
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    };

    //public abstract void countShapeRepetitions(PlacementArea placementArea); //è necessario??
    public abstract List<Coordinates> getCoordinates();
}


