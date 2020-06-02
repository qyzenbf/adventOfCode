
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;



public class Day1Test {

    @Test
    public void testReadFile() throws Exception {
        Day1.INPUT_FILE = "NOT/EXIST";
        assertThrows(IOException.class, () -> {
            Day1.readFile(new ArrayList<>());
        });

        Day1.INPUT_FILE = "src/main/InputFile/Input_D1";
        assertEquals("R1", Day1.readFile(new ArrayList<>()).get(0));

    }

    @Test
    public void testGetDistance() {
        assertEquals(14, Day1.getDistance(new Point(-2, -3), new Point(4, 5)));
    }

    @Test
    public void testUpdateFace() {
        //turn left when facing North, South, East and West
        String faceNorthTurnLeft = Day1.updateFace("L", "NORTH");
        assertEquals("WEST", faceNorthTurnLeft);

        String faceSouthTurnLeft = Day1.updateFace("L", "SOUTH");
        assertEquals("EAST", faceSouthTurnLeft);

        String faceEastTurnLeft = Day1.updateFace("L", "EAST");
        assertEquals("NORTH", faceEastTurnLeft);

        String faceWestTurnLeft = Day1.updateFace("L", "WEST");
        assertEquals("SOUTH", faceWestTurnLeft);

        //turn right when facing North, South, East and West
        String faceNorthTurnRight = Day1.updateFace("R", "NORTH");
        assertEquals("EAST", faceNorthTurnRight);

        String faceSouthTurnRight = Day1.updateFace("R", "SOUTH");
        assertEquals("WEST", faceSouthTurnRight);

        String faceEastTurnRight = Day1.updateFace("R", "EAST");
        assertEquals("SOUTH", faceEastTurnRight);

        String faceWestTurnRight = Day1.updateFace("R", "WEST");
        assertEquals("NORTH", faceWestTurnRight);

        //Wrong Input, return null
        String wrongInput1 = Day1.updateFace(" ", "NORTH");
        assertNull(wrongInput1);

        String wrongInput2 = Day1.updateFace("R", " ");
        assertNull(wrongInput2);
    }

    @Test
    public void testMove() {

        //turn left when facing North, South, East and West
        Point Left_1_North = Day1.move("L", 1, new Point(0, 0), "NORTH");
        assertEquals(new Point(-1, 0), Left_1_North);

        Point Left_1_South = Day1.move("L", 1, new Point(0, 0), "SOUTH");
        assertEquals(new Point(1, 0), Left_1_South);

        Point Left_1_East = Day1.move("L", 1, new Point(0, 0), "EAST");
        assertEquals(new Point(0, 1), Left_1_East);

        Point Left_1_West = Day1.move("L", 1, new Point(0, 0), "WEST");
        assertEquals(new Point(0, -1), Left_1_West);

        //turn right when facing North, South, East and West
        Point Right_1_North = Day1.move("R", 1, new Point(0, 0), "NORTH");
        assertEquals(new Point(1, 0), Right_1_North);

        Point Right_1_South = Day1.move("R", 1, new Point(0, 0), "SOUTH");
        assertEquals(new Point(-1, 0), Right_1_South);

        Point Right_1_East = Day1.move("R", 1, new Point(0, 0), "EAST");
        assertEquals(new Point(0, -1), Right_1_East);

        Point Right_1_West = Day1.move("R", 1, new Point(0, 0), "WEST");
        assertEquals(new Point(0, 1), Right_1_West);

        //wrong Input, return current point
        Point wrong = Day1.move("A", 1, new Point(0, 0), "B");
        assertEquals(new Point(0, 0), wrong);

    }

    @Test
    public void testIsValid() {
        assertTrue(Day1.isVisited(new Point(0, 0), new ArrayList<>(Arrays.asList(new Point(0, 0), new Point(0, 1)))));
        assertFalse(Day1.isVisited(new Point(0, 0), new ArrayList<>(Arrays.asList(new Point(1, 0), new Point(0, 1)))));

    }

    @Test
    public void testAddVisited() {
        //y-direction
        Day1.visitedTwice = null;
        Day1.visitedPoints = new ArrayList<>(Arrays.asList(new Point(0, 2), new Point(2, 0), new Point(0, -1), new Point(-1, 0)));
        //no point is visited twice
        Day1.addVisited(new Point(0, 0), 1, 0);
        assertNull(Day1.visitedTwice);
        //point(2,0) is the first point visited twice
        Day1.addVisited(new Point(1, 0), 1, 0);
        assertEquals(new Point(2, 0), Day1.visitedTwice);
        //point(-1,0) is the first point visited twice
        Day1.visitedTwice = null;
        Day1.addVisited(new Point(0, 0), -2, 0);
        assertEquals(new Point(-1, 0), Day1.visitedTwice);


        //x-direction
        Day1.visitedTwice = null;
        Day1.visitedPoints = new ArrayList<>(Arrays.asList(new Point(0, 2), new Point(2, 0), new Point(0, -1), new Point(-1, 0)));
        //no point is visited twice
        Day1.addVisited(new Point(0, 0), 0, 1);
        assertNull(Day1.visitedTwice);
        //point(0,2) is the first point visited twice
        Day1.addVisited(new Point(0, 1), 0, 1);
        assertEquals(new Point(0, 2), Day1.visitedTwice);
        //point(0,-1) is the first point visited twice
        Day1.visitedTwice = null;
        Day1.addVisited(new Point(0, 0), 0, -2);
        assertEquals(new Point(0, -1), Day1.visitedTwice);

    }

   // @Test
    public void testMain() {
        Day1.INPUT_FILE = "NOT/EXIST";
        assertThrows(IOException.class, () -> {
            Day1.main(null);
        });
    }
}