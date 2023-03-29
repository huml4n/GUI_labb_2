
package recipesearch;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.layout.FlowPane;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;


public class RecipeSearchController implements Initializable {
    RecipeBackendController backend;
    public RecipeSearchController(RecipeBackendController backend){
        this.backend = backend;
    }
    @FXML
    public ComboBox<String> mainIngredient;
    @FXML
    public ComboBox<String> cuisine;
    @FXML
    public RadioButton levelEasy;
    @FXML
    public RadioButton levelMedium;
    @FXML
    public RadioButton levelHard;
    @FXML
    public RadioButton levelAll;
    @FXML
    public Spinner<Integer> maxPrice;
    @FXML
    public Slider maxTime;

    ArrayList<String> cuisineList = new ArrayList<String>(Arrays.asList("Sverige","Grekland","Indien","Asien","Afrika","Frankrike"));
    ArrayList<String> mainIngredientList = new ArrayList<String>(Arrays.asList("Kött","Fisk","Kyckling","Vegetariskt"));
    ArrayList<String> difficultyList = new ArrayList<String>(Arrays.asList("Lätt","Medel","Svår"));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainIngredient.setItems(FXCollections.observableArrayList(mainIngredientList));
        cuisine.setItems(FXCollections.observableArrayList(cuisineList));

    }

    private void updateBackend(){
        backend.setCuisine(cuisine.getValue());
        backend.setMainIngredient(mainIngredient.getValue());
        if(levelEasy.isSelected()) backend.setDifficulty("Easy");
        if(levelMedium.isSelected()) backend.setDifficulty("Medium");
        if(levelHard.isSelected()) backend.setDifficulty("Hard");
        if(levelAll.isSelected()) backend.setDifficulty("All");
        backend.setMaxTime((int) maxTime.getValue());
        backend.setMaxPrice(maxPrice.getValue());
    }

}