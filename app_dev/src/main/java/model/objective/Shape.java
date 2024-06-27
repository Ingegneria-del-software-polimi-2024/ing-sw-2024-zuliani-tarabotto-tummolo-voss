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
     * The Toprightl shape with specific coordinates.
     */
    TOPRIGHTL{

        /**
         * An array of Coordinates representing the relative positions for the specific shape.
         * The coordinate (0,0) is omitted.
         */
        private final Coordinates[] COORDINATES = {/*new Coordinates(0, 0),*/ new Coordinates(-1, -1), new Coordinates(-1, -3)};

        /**
         * This method returns a list of Coordinates for the specific shape.
         *
         * @return the list of coordinates
         */
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};

    },

    /**
     * The Topleftl shape with specific coordinates.
     */
    TOPLEFTL{

        /**
         * An array of Coordinates representing the relative positions for the specific shape.
         * The coordinate (0,0) is omitted.
         */
        private final Coordinates[] COORDINATES = {/*new Coordinates(0, 0),*/ new Coordinates(+1, -1), new Coordinates(+1, -3)};

        /**
         * This method returns a list of Coordinates for the specific shape.
         *
         * @return the list of coordinates
         */
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};

    },

    /**
     * The Bottomrightl shape with specific coordinates.
     */
    BOTTOMRIGHTL{

        /**
         * An array of Coordinates representing the relative positions for the specific shape.
         * The coordinate (0,0) is omitted.
         */
        private final Coordinates[] COORDINATES = {/*new Coordinates(0, 0),*/ new Coordinates(-1, +1), new Coordinates(-1, +3)};

        /**
         * This method returns a list of Coordinates for the specific shape.
         *
         * @return the list of coordinates
         */
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    },

    /**
     * The Bottomleftl shape with specific coordinates.
     */
    BOTTOMLEFTL{

        /**
         * An array of Coordinates representing the relative positions for the specific shape.
         * The coordinate (0,0) is omitted.
         */
        private final Coordinates[] COORDINATES = {/*new Coordinates(0, 0),*/ new Coordinates(+1, +1), new Coordinates(+1, +3)};

        /**
         * This method returns a list of Coordinates for the specific shape.
         *
         * @return the list of coordinates
         */
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    },

    /**
     * The Ascendingdiagonal shape with specific coordinates.
     */
    ASCENDINGDIAGONAL{

        /**
         * An array of Coordinates representing the relative positions for the specific shape.
         * The coordinate (0,0) is omitted.
         */
        private final Coordinates[] COORDINATES = {/*new Coordinates(0, 0),*/ new Coordinates(-1, -1), new Coordinates(-2, -2)};

        /**
         * This method returns a list of Coordinates for the specific shape.
         *
         * @return the list of coordinates
         */
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    },

    /**
     * The Descendingdiagonal shape with specific coordinates.
     */
    DESCENDINGDIAGONAL{

        /**
         * An array of Coordinates representing the relative positions for the specific shape.
         * The coordinate (0,0) is omitted.
         */
        private final Coordinates[] COORDINATES = new Coordinates[]{/*new Coordinates(0, 0),*/ new Coordinates(-1, +1), new Coordinates(-2, +2)};

        /**
         * This method returns a list of Coordinates for the specific shape.
         *
         * @return the list of coordinates
         */
        public List<Coordinates> getCoordinates(){return Arrays.asList(COORDINATES);};
    };

    /**
     * This method returns the list of coordinates for the specific shape.
     *
     * @return the list of coordinates
     */
    public abstract List<Coordinates> getCoordinates();
}


