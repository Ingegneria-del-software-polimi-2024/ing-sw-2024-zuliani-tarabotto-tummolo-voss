package model.placementArea;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Coordinates test.
 */
class CoordinatesTest {
    /**
     * Test coordinates.
     *
     * @param args the args
     */
    @Test
    public static void testCoordinates(String[] args){
        Coordinates a = new Coordinates(0, 0);
        Coordinates b = new Coordinates(1, 2);
        Coordinates c = new Coordinates(0, 0);
        System.out.println(a.equals(b));
        System.out.println(a.equals(c));
        System.out.println(b.equals(c));
        assert a.equals(b) == false;
        assert a.equals(c) == true;
        assert b.equals(c) == false;
    }

}