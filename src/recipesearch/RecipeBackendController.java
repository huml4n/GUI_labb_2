package recipesearch;

import java.awt.*;
import java.util.ArrayList;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;

public class RecipeBackendController {
    ArrayList<String> cuisine = new ArrayList<String>("Sverige","Grekland","Indien","Asien","Afrika","Frankrike");
    ArrayList<String> mainIngredient = new ArrayList<String>("Kött","Fisk","Kyckling","Vegetariskt");
    ArrayList<String> difficulty = new ArrayList<String>("Lätt", "Medel","Svår");
    ArrayList<Integer> maxPrice = new ArrayList<Integer>(); //maxPrice - heltal över noll
    ArrayList<Integer> maxTime = new ArrayList<Integer>(10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150);

    RecipeDatabase db = Rec


// Metoden getRecipes skapar ett nytt sökfilter baserat på variablerna
// cuisine, mainIngredient, difficulty, maxPrice och maxTime och
// returnerar en sorterad lista på recepten enligt beskrivningen ovanför.
    public List<Recipe> getRecipes(){

    }

// Setmetoderna ska användas till att uppdatera variablerna till
// sökfiltret baserat på sökningen användaren gör i gränssnittet.
// Backenden tillåter sökningar på fem attribut hos ett recept. I
// listan nedanför presenteras vilka dessa attribut är och vad de
// heter i backenden samt vilka olika värden de kan ha i databasen.
// Tänk på att ifall det inte är ett av följande följande värden så
// ska null respektive 0 sättas istället.
    public void setCuisine(String cuisine){

    }
    public void setMainIngredient(String mainIngredient){

    }
    public void setDifficulty(String difficulty){

    }
    public void setMaxPrice(int maxPrice){

    }
    public void setMaxTime(int maxTime){

    }
}
