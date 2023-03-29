package recipesearch;

//import java.awt.*;

import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;

import java.util.List;


public class RecipeBackendController {


    private String difficulty = null;
    private String cuisine = null;
    private String mainIngredient = null;
    private Integer maxPrice = null; //maxPrice - heltal över noll
    private Integer maxTime = null; //10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150

    //Skriv funktion som kollar om det är över noll.

    //ArrayList<Integer> maxTime = new ArrayList<Integer>(Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150));

    RecipeDatabase db = RecipeDatabase.getSharedInstance();


    // Metoden getRecipes skapar ett nytt sökfilter baserat på variablerna
// cuisine, mainIngredient, difficulty, maxPrice och maxTime och
// returnerar en sorterad lista på recepten enligt beskrivningen ovanför.
    public List<Recipe> getRecipes() {
        return db.search(new SearchFilter(difficulty, maxTime, cuisine, maxPrice, mainIngredient));
    }

    // Setmetoderna ska användas till att uppdatera variablerna till
// sökfiltret baserat på sökningen användaren gör i gränssnittet.
// Backenden tillåter sökningar på fem attribut hos ett recept. I
// listan nedanför presenteras vilka dessa attribut är och vad de
// heter i backenden samt vilka olika värden de kan ha i databasen.
// Tänk på att ifall det inte är ett av följande följande värden så
// ska null respektive 0 sättas istället.
    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public void setMainIngredient(String mainIngredient) {
        this.mainIngredient = mainIngredient;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }
}
