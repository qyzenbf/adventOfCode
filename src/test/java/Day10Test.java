import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {

    @Test
    void testBot() {
        Bot bot = new Bot();
        bot.receiveChip(2);
        assertEquals(2, bot.getHigh());

        bot.receiveChip(3);
        assertEquals(2, bot.getLow());
        assertEquals(3, bot.getHigh());

        bot.setHigh(0);
        bot.receiveChip(4);
        assertEquals(4, bot.getHigh());
        assertEquals(2, bot.getLow());

        bot.setHigh(0);
        bot.receiveChip(1);
        assertEquals(2, bot.getHigh());
        assertEquals(1, bot.getLow());

    }

    @Test
    void testSaveChipToBot() {
        Map<Integer, Bot> bots = new HashMap<>();
        bots.put(1, new Bot());
        Day10.saveChipToBot(bots, 1, 1);
        assertEquals(1, bots.get(1).getHigh());

        Day10.saveChipToBot(bots, 2, 2);
        assertEquals(2, bots.get(2).getHigh());
    }

    @Test
    void testSaveInstructionToBot() {
        Map<Integer, Bot> bots = new HashMap<>();
        bots.put(1, new Bot());
        Day10.saveInstructionToBot(bots, "instruction", 1);
        assertEquals("instruction", bots.get(1).getInstruction());

        Day10.saveInstructionToBot(bots, "INSTRUCTION", 2);
        assertEquals("INSTRUCTION", bots.get(2).getInstruction());
    }

    @Test
    void testGetInitialBot() {
        Map<Integer, Bot> bots = new HashMap<>();
        bots.put(1, new Bot());
        assertEquals(null, Day10.getInitialBot(bots));

        bots.get(1).setHigh(3);
        assertEquals(null, Day10.getInitialBot(bots));

        bots.get(1).setLow(2);
        assertEquals(bots.get(1), Day10.getInitialBot(bots));
    }

    @Test
    void testGiveChipToBot() {

        Map<Integer, Bot> bots = new HashMap<>();
        Map<Integer, Integer> outputs = new HashMap<>();

        bots.put(2, new Bot());
        Day10.giveChipToBot(2, 2, bots, outputs);
        assertEquals(2, bots.get(2).getHigh());

        bots.get(2).setInstruction("bot 2 gives low to output 0 and high to output 1");
        Day10.giveChipToBot(2, 1, bots, outputs);
        assertEquals(new Integer(1), outputs.get(0));
        assertEquals(new Integer(2), outputs.get(1));

        bots.put(3, new Bot());
        Day10.giveChipToBot(3, Day10.VAL_LOW, bots, outputs);
        Day10.giveChipToBot(3, Day10.VAL_HIGH, bots, outputs);
        assertEquals(3, Day10.THE_BOT_ID);

    }

    @Test
    void testProcessInstruction() {
        Map<Integer, Bot> bots = new HashMap<>();
        Map<Integer, Integer> outputs = new HashMap<>();

        bots.put(1, new Bot());
        bots.get(1).setLow(1);
        bots.get(1).setHigh(2);
        bots.get(1).setInstruction("bot 1 gives low to output 0 and high to output 1");
        Day10.processInstruction(bots, outputs, bots.get(1));
        assertEquals(new Integer(1), outputs.get(0));
        assertEquals(new Integer(2), outputs.get(1));

        bots.put(2, new Bot());
        bots.put(3, new Bot());
        bots.get(2).setLow(1);
        bots.get(2).setHigh(2);
        bots.get(2).setInstruction("bot 2 gives low to bot 3 and high to bot 3");
        Day10.processInstruction(bots, outputs, bots.get(2));
        assertEquals(1, bots.get(3).getLow());
        assertEquals(2, bots.get(3).getHigh());

    }

    @Test
    void testStartProcess() {
        Map<Integer, Bot> bots = new HashMap<>();
        Map<Integer, Integer> outputs = new HashMap<>();

        bots.put(1, new Bot());
        bots.get(1).setLow(1);
        bots.get(1).setHigh(2);
        bots.get(1).setInstruction("bot 1 gives low to output 0 and high to output 1");
        Day10.startProcess(bots, outputs);
        assertEquals(new Integer(1), outputs.get(0));
        assertEquals(new Integer(2), outputs.get(1));
    }

    @Test
    void testReadFile() {
        Day10.INPUT_FILE = "NOT/EXIST";
        assertThrows(IOException.class, () -> {
            Day10.main(null);
        });
    }
}