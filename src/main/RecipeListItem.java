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
    }

    @FXML
    protected void onClick(Event event) {
        parentController.openDetailedPane(recipe);
    }
}