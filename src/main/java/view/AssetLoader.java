package view;

import com.google.gson.*;
import model.gods.Mortal;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class contains all the method required to handle the text Information about the Gods in the configuration file
 */
public class AssetLoader {

    public static final String JSON_FILE_PATH = "/gods/gods.json";
    private static final Map<String,GodAssetsBundle> godAssetsIndexForName =
            loadAssetsFromFile(GodAssetsBundle::getName);
    private static final Map<Integer,GodAssetsBundle> godAssetsIndexForID =
            loadAssetsFromFile(GodAssetsBundle::getId);
    public static final String MORTAL_NAME = new Mortal().getName();

    /**
     * Fetches the Godresourcebundle of a god
     * @param god The string identifier of the god
     * @return The bundle of gods resources
     * @throws IllegalArgumentException The god is not valid
     */
    public static GodAssetsBundle getGodAssetsBundle(String god) {
        if(!isAGod(god)) {
            throw new IllegalArgumentException(ClientErrorMessages.INVALID_GOD);
        }
        return godAssetsIndexForName.get(god);
    }

    /**
     * This method returns the AssetBundle of a god based on the ID
     * @param id The ID parameter
     * @return The corresponding Resource Bundle of the god
     */
    public static GodAssetsBundle getGodAssetsBundle(int id){
        if(!isValidID(id)){
            throw new IllegalArgumentException(ClientErrorMessages.INVALID_GOD);
        }
        return godAssetsIndexForID.get(id);
    }

    /**
     * This method checks if the Name of the god exists in the current list of names
     * @param godName The String paramenter
     * @return True if the name is among the available gods. False otherwise
     */
    public static boolean isAGod (String godName){
        return godAssetsIndexForName.containsKey(godName);
    }

    /**
     * This method checks if the ID of the god exists in the current list of names
     * @param id The ID parameter
     * @return True if the name is among the available gods. False otherwise
     */
    public static boolean isValidID(int id){
        return godAssetsIndexForID.containsKey(id);
    }


    private static <T> Map<T,GodAssetsBundle> loadAssetsFromFile(Function<GodAssetsBundle,T> keyMapper) {
        Gson gson = new Gson();
        StringBuilder jsonBuilder = new StringBuilder();
        Scanner jsonScanner = new Scanner(AssetLoader.class.getResourceAsStream(JSON_FILE_PATH));
        while(jsonScanner.hasNext()){
            jsonBuilder.append(jsonScanner.nextLine());
        }
        JsonArray godsContainer = JsonParser.parseString(jsonBuilder.toString()).getAsJsonObject().getAsJsonArray("gods");
        GodAssetsBundle[] cont = gson.fromJson(godsContainer,GodAssetsBundle[].class);
            return Arrays.stream(cont)
                    .collect(Collectors.toMap(keyMapper,Function.identity()));
    }


    /**
     * This method is a getter. It fetches the Name of the god based on the ID parameter
     * @param id The ID parameter
     * @return The name of the corresponding god
     */
    public static String getGodNameFromID(Integer id){
        return getGodAssetsBundle(id).getName();
    }
}
