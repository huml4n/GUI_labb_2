package recipesearch;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class RecipeSearch extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        RecipeBackendController backend = new RecipeBackendController();
        RecipeSearchController search = new RecipeSearchController(backend);
        
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("recipesearch/resources/RecipeSearch");
        
        Parent root = FXMLLoader.load(getClass().getResource("recipe_search.fxml"), bundle);
        
        Scene scene = new Scene(root, 800, 500);
        
        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
