import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {
    public static String INPUT_FILE = "/Users/QYZ/Desktop/adventOfCode/src/main/InputFile/Input_D4";
    private static String PATTERN = "(?<name>[\\D-]+)(?<sector>\\d+)(?<checksum>[\\[\\w\\]]+)";

    public static void main(String[] args) throws FileNotFoundException {
        int roomsIdSum = 0;
        int northPoleId = 0;

        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                Pattern r = Pattern.compile(PATTERN);
                Matcher m = r.matcher(line);
                if (m.find()) {
                    // Dashes not necessary
                    String name = m.group("name").replace("-", "");
                    String sector = m.group("sector");
                    String checksum = m.group("checksum").replace("[", "").replace("]", "");

                    HashMap<String, Integer> letters = new HashMap<>();
                    storeLetter(name, letters);
                    String commonLetters = commonLetters(letters);

                    if (commonLetters.equals(checksum)) {
                        roomsIdSum = roomsIdSum + Integer.parseInt(sector);
                        northPoleId = northPoleId == 0 ? checkNorthPole(name, sector) : northPoleId;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Sum of sector ID: " + roomsIdSum);
        System.out.println("Section ID where North Pole objects stored: " + northPoleId);
    }

    public static int checkNorthPole(String name, String sector) {
        StringBuilder decryptedName = new StringBuilder(name);
        int northPoleId = 0;

        int rotation = Integer.parseInt(sector) % 26;

        for (int i = 0; i < name.length(); i++) {
            char currentChar = name.charAt(i);
            char rotatedChar;

            // Char cannot be bigger than 122
            if (currentChar + rotation > 122) {
                rotatedChar = (char) (currentChar + rotation - 26);
            } else {
                rotatedChar = (char) (currentChar + rotation);
            }

            decryptedName.setCharAt(i, rotatedChar);
        }

        if (decryptedName.toString().contains("northpoleobjects")) {
            northPoleId = Integer.parseInt(sector);
        }
        return northPoleId;
    }


    public static void storeLetter(String name, HashMap<String, Integer> letters) {
        for (int i = 0; i < name.length(); i++) {
            String character = String.valueOf(name.charAt(i));
            if (letters.containsKey(character)) {
                int oldValue = letters.get(character);
                letters.replace(character, oldValue + 1);
            } else {
                letters.put(character, 1);
            }
        }
    }

    public static String commonLetters(Map<String, Integer> letters) {

        Map<String, Integer> sorted = sortByValue(letters);
        StringBuilder commonLetters = new StringBuilder();

        for (HashMap.Entry<String, Integer> entry : sorted.entrySet()) {
            commonLetters.append(entry.getKey());
        }

        return commonLetters.toString().substring(0, 5);
    }

    // sort DES by value and key
    public static <K extends Comparable<? super K>, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {

        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                if (o1.getValue() != o2.getValue()) {
                    if (o1.getValue().compareTo(o2.getValue()) > 0) return -1;
                    else return 1;
                } else {
                    // Compare alphabetically
                    return o1.getKey().compareTo(o2.getKey());
                }
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
