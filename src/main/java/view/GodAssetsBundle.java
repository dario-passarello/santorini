package view;

import javafx.scene.image.Image;

public class GodAssetsBundle {

    public static final String GOD_CARDS_PATH = "/gods/cards/";

    private final String name;
    private final String description;
    private final int id;

    public GodAssetsBundle(String godName, String description, int ID){
        this.name = godName;
        this.description = description;
        this.id = ID;
    }

    public GodAssetsBundle(GodAssetsBundle toCopy){
        this.name = toCopy.name;
        this.description = toCopy.description;
        this.id = toCopy.id;
    }

    public String getGodCardsPath(){
        return GOD_CARDS_PATH + id + ".png";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public Image loadGodCardImage(){
        return new Image(AssetLoader.class.getResourceAsStream(getGodCardsPath()));
    }

}
