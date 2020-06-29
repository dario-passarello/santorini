package view;

import javafx.scene.image.Image;

/**
 * This class contains the methods used to handle the files of the gods
 */
public class GodAssetsBundle {

    public static final String GOD_CARDS_PATH = "/gods/cards/";
    public static final String GOD_FIGURES_PATH = "/gods/figures/";

    private final String name;
    private final String description;
    private final int id;

    /**
     * The constructor method
     * @param godName The name of the god
     * @param description The description of the god
     * @param ID The unique ID number
     */
    public GodAssetsBundle(String godName, String description, int ID){
        this.name = godName;
        this.description = description;
        this.id = ID;
    }

    /**
     * This constructor creates a copy of an object of the same type
     * @param toCopy The object to copy
     */
    public GodAssetsBundle(GodAssetsBundle toCopy){
        this.name = toCopy.name;
        this.description = toCopy.description;
        this.id = toCopy.id;
    }

    /**
     * Getter method
     * @return The string containing the path of the image of the god
     */
    public String getGodCardsPath(){
        return GOD_CARDS_PATH + id + ".png";
    }

    /**
     * Getter method
     * @return The string containing the path of the figure of the god
     */
    public String getGodFiguresPath() {
        return GOD_FIGURES_PATH + id + ".png";
    }

    /**
     * Standard Getter
     * @return The name of the god
     */
    public String getName() {
        return name;
    }

    /**
     * Standard getter
     * @return The descritpion of the god
     */
    public String getDescription() {
        return description;
    }

    /**
     * Standard getter
     * @return The ID of the god
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method
     * @return The image of this god
     */
    public Image loadGodCardImage(){
        return new Image(AssetLoader.class.getResourceAsStream(getGodCardsPath()));
    }

    /**
     * Getter method
     * @return The figure of this god
     */
    public Image loadGodFigureImage() {
        return new Image(AssetLoader.class.getResourceAsStream(getGodFiguresPath()));
    }
}
