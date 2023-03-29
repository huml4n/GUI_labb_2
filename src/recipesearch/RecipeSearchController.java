
package recipesearch;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

import se.chalmers.ait.dat215.lab2.RecipeDatabase;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;


public class RecipeSearchController implements Initializable {

    @FXML
    Combo


    RecipeDatabase db = RecipeDatabase.getSharedInstance();

    //db.search(new SearchFilter(difficulty, maxTime, cuisine, maxPrice, mainIngredient));

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}