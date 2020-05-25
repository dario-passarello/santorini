package view;

import com.google.gson.*;
import model.gods.Mortal;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

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
     * @throws IllegalArgumentException The {@param god} is not valid
     */
    public static GodAssetsBundle getGodAssetsBundle(String god) {
        if(!isAGod(god)) {
            throw new IllegalArgumentException(ClientErrorMessages.INVALID_GOD);
        }
        return godAssetsIndexForName.get(god);
    }

    public static GodAssetsBundle getGodAssetsBundle(int id){
        if(!isValidID(id)){
            throw new IllegalArgumentException(ClientErrorMessages.INVALID_GOD);
        }
        return godAssetsIndexForID.get(id);
    }

    public static boolean isAGod (String godName){
        return godAssetsIndexForName.containsKey(godName);
    }

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


    public static String getGodNameFromID(Integer id){
        return getGodAssetsBundle(id).getName();
    }
}
