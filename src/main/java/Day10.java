
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day10 {
    public static String INPUT_FILE = "src/main/InputFile/Input_D10";
    public static int VAL_LOW = 17;
    public static int VAL_HIGH = 61;
    public static int THE_BOT_ID;

    public static void main(String[] args) throws FileNotFoundException {

        Map<Integer, Bot> bots = new HashMap<>();
        Map<Integer, Integer> outputs = new HashMap<>();

        // Read file, save chips and instructions to each bot
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
        String line;
        try {

            while ((line = br.readLine()) != null) {

                if (line.contains("value")) {
                    String[] arr = line.split(" ");
                    saveChipToBot(bots, Integer.valueOf(arr[1]), Integer.valueOf(arr[5]));
                }

                if (line.contains("gives")) {
                    String[] arr = line.split(" ");
                    saveInstructionToBot(bots, line, Integer.valueOf(arr[1]));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        startProcess(bots, outputs);
        System.out.println("Bot Number: " + THE_BOT_ID);
        System.out.println("Multiplied value: " + outputs.get(0) * outputs.get(1) * outputs.get(2));
    }

    /* continuously find the executable bot until none is left */
    public static void startProcess(Map<Integer, Bot> bots, Map<Integer, Integer> outputs) {
        while (getInitialBot(bots) != null) {
            Bot bot = getInitialBot(bots);
            if (bot.getInstruction() != null) {
                processInstruction(bots, outputs, bot);
            }
        }
    }

    /* process instruction, give away chips to other bots or outputs */
    public static void processInstruction(Map<Integer, Bot> bots, Map<Integer, Integer> outputs, Bot bot) {

        String[] arr = bot.getInstruction().split(" ");

        if (arr[5].equals("bot")) {
            int lowBotTargetId = Integer.valueOf(arr[6]);
            giveChipToBot(lowBotTargetId, bot.getLow(), bots, outputs);
            bot.setLow(0);
        }

        if (arr[5].equals("output")) {
            int lowOutputTargetId = Integer.valueOf(arr[6]);
            outputs.put(lowOutputTargetId, bot.getLow());
            bot.setLow(0);
        }

        if (arr[10].equals("bot")) {
            int highBotTargetId = Integer.valueOf(arr[11]);
            giveChipToBot(highBotTargetId, bot.getHigh(), bots, outputs);
            bot.setHigh(0);
        }

        if (arr[10].equals("output")) {
            int highOutputTargetId = Integer.valueOf(arr[11]);
            outputs.put(highOutputTargetId, bot.getHigh());
            bot.setHigh(0);
        }

    }

    /* get a bot which has both value and able to start implement the instruction */
    public static Bot getInitialBot(Map<Integer, Bot> bots) {
        int initialBot;
        for (Map.Entry<Integer, Bot> bot : bots.entrySet()) {
            if (bot.getValue().getLow() > 0 && bot.getValue().getHigh() > 0) {
                initialBot = bot.getKey();
                return bots.get(initialBot);
            }
        }
        return null;
    }

    /* save instruction to bot */
    public static void saveInstructionToBot(Map<Integer, Bot> bots, String line, int botValue) {
        Bot bot = bots.get(botValue);
        if (bot != null) {
            bot.setInstruction(line);
        } else {
            bot = new Bot();
            bot.setInstruction(line);
            bots.put(botValue, bot);
        }
    }

    /* give a specific-valued chip to bot */
    public static void saveChipToBot(Map<Integer, Bot> bots, int chipValue, int botValue) {
        Bot bot = bots.get(botValue);
        if (bot != null) {
            bot.receiveChip(chipValue);
        } else {
            bot = new Bot();
            bot.receiveChip(chipValue);
            bots.put(botValue, bot);
        }
    }

    /* give chip to a certain bot and check the ability of the receiver bot */
    public static void giveChipToBot(int receiverBotId, int value, Map<Integer, Bot> map, Map<Integer, Integer> outputs) {
        // Insert value into bot on HashMap
        Bot bot = map.get(receiverBotId);
        if (bot != null) {
            bot.receiveChip(value);
            // Check if values are what we are looking for. If true mark the bot ID.
            if (bot.getLow() == VAL_LOW && bot.getHigh() == VAL_HIGH) {
                THE_BOT_ID = receiverBotId;
            }

            // If has both values, and also has instruction, read instruction and call recursively
            if (bot.getInstruction() != null && bot.getLow() > 0 && bot.getHigh() > 0) {
                processInstruction(map, outputs, bot);
            }
        }
    }
}

/* Bot Object, with low-value chip, high-value chip and instruction */
@Getter
@Setter
class Bot {

    private int low;
    private int high;
    private String instruction;

    public void receiveChip(int value) {
        if (this.low == 0) {
            if (value < this.high) this.low = value;
            else {
                this.low = this.high;
                this.high = value;
            }
        } else if (this.high == 0) {
            if (value > this.low) this.high = value;
            else {
                this.high = this.low;
                this.low = value;
            }
        }
    }
}
