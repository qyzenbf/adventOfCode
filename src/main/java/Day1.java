import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day1 {
    public static String INPUT_FILE = "/Users/QYZ/Desktop/adventOfCode/src/main/InputFile/Input_D1";
    private static String NORTH = "NORTH";
    private static String SOUTH = "SOUTH";
    private static String EAST = "EAST";
    private static String WEST = "WEST";
    public static List<Point> visitedPoints = new ArrayList<Point>();
    public static Point visitedTwice = null;

    public static void main(String[] args) throws Exception {
        List<String> instructions = new ArrayList<String>();
        String currentFace = NORTH;
        Point initialPoint = new Point(0, 0);
        Point currentPoint = new Point(0, 0);

        instructions = readFile(new ArrayList<String>());

        for (String instruction : instructions) {
            int stepNum = Integer.parseInt(instruction.substring(1));
            if (instruction.contains("L")) {
                //stepNum = Integer.parseInt(instruction.substring(instruction.indexOf("L") + 1));
                currentPoint = move("L", stepNum, currentPoint, currentFace);
                currentFace = updateFace("L", currentFace);
            } else if (instruction.contains("R")) {
                //stepNum = Integer.parseInt(instruction.substring(instruction.indexOf("R") + 1));
                currentPoint = move("R", stepNum, currentPoint, currentFace);
                currentFace = updateFace("R", currentFace);
            }
        }

        System.out.println("Last Point distance: " + getDistance(initialPoint, currentPoint));
        System.out.println("Visited Twice Point distance: " + getDistance(initialPoint, visitedTwice));
    }

    public static int getDistance(Point initial, Point current) {
        return Math.abs(current.y - initial.y) + Math.abs(current.x - initial.x);
    }

    public static ArrayList<String> readFile(ArrayList<String> instructions) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
        String input;

        while ((input = br.readLine()) != null) {
            instructions.addAll(Arrays.asList(input.split(", ")));
        }

        return instructions;
    }

    public static String updateFace(String direction, String currentFace) {

        if (direction.equals("L")) {
            if (currentFace.equals(NORTH)) {
                return WEST;
            } else if (currentFace.equals(SOUTH)) {
                return EAST;
            } else if (currentFace.equals(EAST)) {
                return NORTH;
            } else if (currentFace.equals(WEST)) {
                return SOUTH;
            }

        } else if (direction.equals("R")) {
            if (currentFace.equals(NORTH)) {
                return EAST;
            } else if (currentFace.equals(SOUTH)) {
                return WEST;
            } else if (currentFace.equals(EAST)) {
                return SOUTH;
            } else if (currentFace.equals(WEST)) {
                return NORTH;
            }
        }

        return null;
    }

    public static Point move(String direction, int stepNum, Point currentPoint, String currentFace) {
        int moveX = 0;
        int moveY = 0;

        if (direction.equals("L")) {

            if (currentFace.equals(NORTH)) {
                moveX = (-1) * stepNum;
            } else if (currentFace.equals(SOUTH)) {
                moveX = stepNum;
            } else if (currentFace.equals(EAST)) {
                moveY = stepNum;
            } else if (currentFace.equals(WEST)) {
                moveY = (-1) * stepNum;
            }
        } else if (direction.equals("R")) {
            if (currentFace.equals(NORTH)) {
                moveX = stepNum;
            } else if (currentFace.equals(SOUTH)) {
                moveX = (-1) * stepNum;
            } else if (currentFace.equals(EAST)) {
                moveY = (-1) * stepNum;
            } else if (currentFace.equals(WEST)) {
                moveY = stepNum;
            }
        }
        addVisited(currentPoint, moveX, moveY);
        currentPoint.translate(moveX, moveY);
        return currentPoint;
    }

    public static boolean isVisited(Point currentPoint, List<Point> visitedPoints) {
        for (Point visited : visitedPoints) {
            if (visited.x == currentPoint.x && visited.y == currentPoint.y) {
                return true;
            }
        }
        return false;
    }

    public static void addVisited(Point currentPoint, int moveX, int moveY) {

        int currentX = currentPoint.x;
        int currentY = currentPoint.y;

        int finalX = currentX + moveX;
        int finalY = currentY + moveY;

        if (moveX != 0) {
            if (moveX > 0) {
                for (int i = currentX + 1; i <= finalX; i++) {
                    Point current = new Point(i, currentY);
                    if (visitedTwice == null && isVisited(current, visitedPoints)) {
                        visitedTwice = new Point(current.x, current.y);
                    }
                    visitedPoints.add(current);
                }
            } else if (moveX < 0) {
                for (int i = currentX - 1; i >= finalX; i--) {
                    Point current = new Point(i, currentY);
                    if (visitedTwice == null && isVisited(current, visitedPoints)) {
                        visitedTwice = new Point(current.x, current.y);
                    }
                    visitedPoints.add(current);
                }
            }
        } else if (moveY != 0) {
            if (moveY > 0) {
                for (int i = currentY + 1; i <= finalY; i++) {
                    Point current = new Point(currentX, i);
                    if (visitedTwice == null && isVisited(current, visitedPoints)) {
                        visitedTwice = new Point(current.x, current.y);
                    }
                    visitedPoints.add(current);
                }
            } else if (moveY < 0) {
                for (int i = currentY - 1; i >= finalY; i--) {
                    Point current = new Point(currentX, i);
                    if (visitedTwice == null && isVisited(current, visitedPoints)) {
                        visitedTwice = new Point(current.x, current.y);
                    }
                    visitedPoints.add(current);
                }
            }
        }


    }


}
