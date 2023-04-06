package recipesearch;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;

import java.util.List;

public class RecipeBackendController {

    private final RecipeDatabase db;
    private String cuisine;
    private String mainIngredient;
    private String difficulty;
    private Integer maxTime = 0;
    private Integer maxPrice = 0;
    public String[] cuisineArr = {"Alla", "Sverige", "Grekland", "Indien", "Asien", "Afrika", "Frankrike"};
    public String[] mainIngredientArr = {"Alla", "Kött", "Fisk", "Kyckling", "Vegetarisk"};
    public String[] difficultyArr = {"Alla", "Lätt", "Mellan", "Svår"};
    public Integer[] timeArr = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150};
    public RecipeBackendController(RecipeDatabase db){
         this.db = db;
    }
    public List<Recipe> getRecipes(){
        return db.search(new SearchFilter(difficulty, maxTime, cuisine, maxPrice, mainIngredient));
    }
    public void setCuisine(String cuisine){
        for (String s : cuisineArr){
            if (cuisine.equals(s)) {
                this.cuisine = cuisine;
                return;
            }
        }
        this.cuisine = null;
    }
    public void setMainIngredient(String mainIngredient) {
        for (String s : mainIngredientArr){
            if (mainIngredient.equals(s)) {
                this.mainIngredient = mainIngredient;
                return;
            }
        }
        this.mainIngredient = null;
    }
    public void setDifficulty(String difficulty){
        for (String s : difficultyArr){
            if (difficulty.equals(s)) {
                this.difficulty = difficulty;
                return;
            }
        }
        this.difficulty = null;
    }
    public void setMaxPrice(Integer maxPrice){
        this.maxPrice = Math.max(maxPrice, 0);
    }
    public void setMaxTime(Integer maxTime){
        for (Integer i : timeArr) {
            if (maxTime.equals(i)){
                this.maxTime = maxTime;
                return;
            }
        }
        this.maxTime = 0;
    }
}
