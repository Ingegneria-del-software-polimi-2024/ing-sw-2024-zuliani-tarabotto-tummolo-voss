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
     * The TOPRIGHTL enum constant represents a shape with specific relative coordinates.
     */
    TOPRIGHTL{
        /**
         * The COORDINATES array represents the relative coordinates for a specific shape.
         * The coordinate (0,0) is omitted.
         */
        private final Coordinates[] COORDINATES = {/*new Coordinates(0, 0),*/ new Coordinates(-1, -1), new Coordinates(-1, -3)};

        /**
         * This method returns a list of coordinates for the specific shape.
         * @return List of Coordinates for the specific shape.
         */
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};

    },

    /**
     * The TOPLEFTL enum constant represents a shape with specific relative coordinates.
     */
    TOPLEFTL{
        /**
         * The COORDINATES array represents the relative coordinates for a specific shape.
         * The coordinate (0,0) is omitted.
         */
        private final Coordinates[] COORDINATES = {/*new Coordinates(0, 0),*/ new Coordinates(+1, -1), new Coordinates(+1, -3)};

        /**
         * This method returns a list of coordinates for the specific shape.
         * @return List of Coordinates for the specific shape.
         */
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};

    },

    /**
     * The BOTTOMRIGHTL enum constant represents a shape with specific relative coordinates.
     */
    BOTTOMRIGHTL{

        /**
         * The COORDINATES array represents the relative coordinates for a specific shape.
         * The coordinate (0,0) is omitted.
         */
        private final Coordinates[] COORDINATES = {/*new Coordinates(0, 0),*/ new Coordinates(-1, +1), new Coordinates(-1, +3)};

        /**
         * This method returns a list of coordinates for the specific shape.
         * @return List of Coordinates for the specific shape.
         */
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    },

    /**
     * The BOTTOMLEFTL enum constant represents a shape with specific relative coordinates.
     */
    BOTTOMLEFTL{
        /**
         * The COORDINATES array represents the relative coordinates for a specific shape.
         * The coordinate (0,0) is omitted.
         */
        private final Coordinates[] COORDINATES = {/*new Coordinates(0, 0),*/ new Coordinates(+1, +1), new Coordinates(+1, +3)};

        /**
         * This method returns a list of coordinates for the specific shape.
         * @return List of Coordinates for the specific shape.
         */
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    },

    /**
     * The ASCENDINGDIAGONAL enum constant represents a shape with specific relative coordinates.
     */
    ASCENDINGDIAGONAL{

        /**
         * The COORDINATES array represents the relative coordinates for a specific shape.
         * The coordinate (0,0) is omitted.
         */
        private final Coordinates[] COORDINATES = {/*new Coordinates(0, 0),*/ new Coordinates(-1, -1), new Coordinates(-2, -2)};

        /**
         * This method returns a list of coordinates for the specific shape.
         * @return List of Coordinates for the specific shape.
         */
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    },

    /**
     * The DESCENDINGDIAGONAL enum constant represents a shape with specific relative coordinates.
     */
    DESCENDINGDIAGONAL{
        /**
         * The COORDINATES array represents the relative coordinates for a specific shape.
         * The coordinate (0,0) is omitted.
         */
        private final Coordinates[] COORDINATES = new Coordinates[]{/*new Coordinates(0, 0),*/ new Coordinates(-1, +1), new Coordinates(-2, +2)};

        /**
         * This method returns a list of coordinates for the specific shape.
         * @return List of Coordinates for the specific shape.
         */
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    };

    /**
     * This method returns a list of coordinates for the specific shape.
     * @return List of Coordinates for the specific shape.
     */
    public abstract List<Coordinates> getCoordinates();
}


