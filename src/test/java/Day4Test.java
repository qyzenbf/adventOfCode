import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {
    @Test
    void testSortByValue() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("b", 1);
        map.put("a", 1);
        map.put("c", 2);
        map.put("d", 0);
        Map<String, Integer> sorted = Day4.sortByValue(map);
        StringBuilder commonLetters = new StringBuilder();
        for (HashMap.Entry<String, Integer> entry : sorted.entrySet()) {
            commonLetters.append(entry.getKey());
        }
        assertEquals("cabd", commonLetters.toString());
    }

    @Test
    void testCommonChars() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 1);
        map.put("c", 2);
        map.put("d", 0);
        map.put("e", 1);
        assertEquals("cabed", Day4.commonChars(map));
    }

    @Test
    void testStoreChars() {
        HashMap<String, Integer> map = new HashMap<>();
        String name = "aab";
        Day4.storeChars(name, map);
        assertEquals(1, map.get("b"));
        assertEquals(2, map.get("a"));
    }

    @Test
    void testCheckNorthPole() {
        //kloqemlib-lygbzq-pqloxdb-991[lbqod]
        String name_northPole = "kloqemliblygbzqpqloxdb";
        String sector_northPole = "991";
        assertEquals(991, Day4.checkNorthPole(name_northPole, sector_northPole));
        assertEquals(0, Day4.checkNorthPole("abcdefgh", "123"));
    }

    @Test
    void testReadFile() {
        Day4.INPUT_FILE = "NOT/EXIST";
        assertThrows(IOException.class, () -> {
            Day4.main(null);
        });
    }

}