import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {
    public static String INPUT_FILE = "src/main/InputFile/Input_D4";

    public static void main(String[] args) throws FileNotFoundException {
        int roomsIdSum = 0;
        int northPoleId = 0;

        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
        String line;

        try {
            while ((line = br.readLine()) != null) {

                String[] arr = line.split("-");
                String name = "";
                for (int i = 0; i < arr.length - 1; i++) {
                    name += arr[i];
                }
                String sector = arr[arr.length - 1].substring(0, arr[arr.length - 1].indexOf("["));
                String checksum = arr[arr.length - 1].substring(arr[arr.length - 1].indexOf("[")).replace("[", "").replace("]", "");

                HashMap<String, Integer> letters = new HashMap<>();
                storeChars(name, letters);
                String commonLetters = commonChars(letters);

                if (commonLetters.equals(checksum)) {
                    roomsIdSum = roomsIdSum + Integer.parseInt(sector);
                    northPoleId = northPoleId == 0 ? checkNorthPole(name, sector) : northPoleId;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Sum of sector ID: " + roomsIdSum);
        System.out.println("Section ID where North Pole objects stored: " + northPoleId);
    }

    /* Decrypted name of each input, find the north pole object */
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

    /* store each character of name into a HashMap */
    public static void storeChars(String name, HashMap<String, Integer> chars) {
        for (int i = 0; i < name.length(); i++) {
            String character = String.valueOf(name.charAt(i));
            if (chars.containsKey(character)) {
                int oldValue = chars.get(character);
                chars.replace(character, oldValue + 1);
            } else {
                chars.put(character, 1);
            }
        }
    }

    /* find the most common 5 letters in the name, sort by frequency of occurrence, with ties broken by alphabetization */
    public static String commonChars(Map<String, Integer> chars) {
        Map<String, Integer> sorted = sortByValue(chars);
        StringBuilder commonLetters = new StringBuilder();

        for (HashMap.Entry<String, Integer> entry : sorted.entrySet()) {
            commonLetters.append(entry.getKey());
        }
        return commonLetters.toString().substring(0, 5);
    }

    /* sort in DESCENDING, by value and then by key */
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
