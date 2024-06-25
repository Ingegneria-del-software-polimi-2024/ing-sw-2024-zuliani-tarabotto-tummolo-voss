package model.objective;

import model.placementArea.Coordinates;

import java.util.Arrays;
import java.util.List;

/**
 * shape has relative coordinates (coordinate (0,0) is omitted) to a precise card of the pattern,
 * it will be given as parameter to the method of the placement area searching for specific patterns
 */
public enum Shape {
    /**
     * The Toprightl.
     */
    TOPRIGHTL{
        private final Coordinates[] COORDINATES = {/*new Coordinates(0, 0),*/ new Coordinates(-1, -1), new Coordinates(-1, -3)};
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};

    },
    /**
     * The Topleftl.
     */
    TOPLEFTL{
        private final Coordinates[] COORDINATES = {/*new Coordinates(0, 0),*/ new Coordinates(+1, -1), new Coordinates(+1, -3)};
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};

    },
    /**
     * The Bottomrightl.
     */
    BOTTOMRIGHTL{
        private final Coordinates[] COORDINATES = {/*new Coordinates(0, 0),*/ new Coordinates(-1, +1), new Coordinates(-1, +3)};
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    },
    /**
     * The Bottomleftl.
     */
    BOTTOMLEFTL{
        private final Coordinates[] COORDINATES = {/*new Coordinates(0, 0),*/ new Coordinates(+1, +1), new Coordinates(+1, +3)};

        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    },
    /**
     * The Ascendingdiagonal.
     */
    ASCENDINGDIAGONAL{
        private final Coordinates[] COORDINATES = {/*new Coordinates(0, 0),*/ new Coordinates(-1, -1), new Coordinates(-2, -2)};
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    },
    /**
     * The Descendingdiagonal.
     */
    DESCENDINGDIAGONAL{
        private final Coordinates[] COORDINATES = new Coordinates[]{/*new Coordinates(0, 0),*/ new Coordinates(-1, +1), new Coordinates(-2, +2)};
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    };

    /**
     * Gets coordinates.
     *
     * @return the list of coordinates
     */
    public abstract List<Coordinates> getCoordinates();
}


