package view;

import model.gods.Mortal;

import java.util.HashMap;
import java.util.Map;

public class CommonAssetLoader {
    public static final Map<String,String> godsDescriptions = loadFromFile();
    public static final String MORTAL_NAME = new Mortal().getName();

    public static boolean isAGod (String godName){
        return godsDescriptions.containsKey(godName);
    }

    private static Map<String,String> loadFromFile(){
        //TODO
        return new HashMap<>();
    }

    /**
     * Fetches the description of a god
     * @param god The string identifier of the god
     * @return The description of the god's powers
     * @throws IllegalArgumentException The {@param god} is not valid
     */
    protected static String getDescription(String god) {
        if(!isAGod(god)) {
            throw new IllegalArgumentException(ClientErrorMessages.INVALID_GOD);
        }
        return godsDescriptions.get(god);
    }
}
