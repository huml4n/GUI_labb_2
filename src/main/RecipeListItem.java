package main;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.io.IOException;

public class RecipeListItem extends AnchorPane {
    private RecipeSearchController parentController;
    private Recipe recipe;

    @FXML
    private Label labelListItem;
    @FXML
    private ImageView imgListItem;
    @FXML
    private ImageView imgCuisine;
    @FXML
    private ImageView imgIngredient;
    @FXML
    private ImageView imgDifficulty;
    @FXML
    private Label labelTime;
    @FXML
    private Label labelPrice;
    @FXML
    private Label labelDescription;

    public RecipeListItem(Recipe recipe, RecipeSearchController recipeSearchController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recipe_listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.recipe = recipe;
        this.parentController = recipeSearchController;
        this.imgListItem.setImage(recipe.getFXImage(imgListItem.getFitWidth(), imgListItem.getFitHeight()));
        this.labelListItem.setText(recipe.getName());
        this.labelTime.setText(recipe.getTime() + " minuter");
        this.labelPrice.setText(recipe.getPrice() + " kr");
        this.labelDescription.setText(recipe.getDescription());
        this.imgIngredient.setImage(parentController.getImage(recipe.getMainIngredient()));
        this.imgDifficulty.setImage(parentController.getImage(recipe.getDifficulty()));
        this.imgCuisine.setImage(parentController.getImage(recipe.getCuisine()));

    }

    @FXML
    protected void onClick(Event event) {
        parentController.openDetailedPane(recipe);
    }

}