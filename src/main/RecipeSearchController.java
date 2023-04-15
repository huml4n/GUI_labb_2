
package main;

import java.net.URL;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;

public class RecipeSearchController implements Initializable {

    @FXML
    public FlowPane flowResults;
    @FXML
    public ComboBox<String> comboIngredient;
    @FXML
    public ComboBox<String> comboCuisine;
    @FXML
    public RadioButton radioAll;
    @FXML
    public RadioButton radioEasy;
    @FXML
    public RadioButton radioMedium;
    @FXML
    public RadioButton radioHard;
    @FXML
    public Spinner<Integer> spinPrice;
    @FXML
    public Slider slideTime;
    @FXML
    public Label labelTime;
    @FXML
    public ImageView imgDetailedRecipe;
    @FXML
    public Button btnCloseDetailedRecipe;
    @FXML
    public Label labelDetailedRecipeName;
    @FXML
    public Pane paneDetailed;
    @FXML
    public SplitPane paneSearch;

    private Map<String, RecipeListItem> recipeListItemMap = new HashMap<>();
    private Map<RadioButton, String> radioButtonMap = new HashMap<>();
    List<Recipe> recipeList;

    RecipeDatabase db = RecipeDatabase.getSharedInstance();
    RecipeBackendController backend = new RecipeBackendController(db);

    private void updateRecipeList() {
        flowResults.getChildren().clear();
        recipeList = backend.getRecipes();
        for (Recipe recipe : recipeList) {
            flowResults.getChildren().add(recipeListItemMap.get(recipe.getName()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (Recipe recipe : backend.getRecipes()) {
            recipeListItemMap.put(recipe.getName(), new RecipeListItem(recipe, this));
        }
        radioButtonMap.put(radioEasy, "Lätt");
        radioButtonMap.put(radioHard, "Svår");
        radioButtonMap.put(radioMedium, "Mellan");
        radioButtonMap.put(radioAll, "Alla");
        updateRecipeList();
        comboIngredient.getItems().addAll(backend.mainIngredientArr);
        comboIngredient.getSelectionModel().select("Alla");
        comboCuisine.getItems().addAll(backend.cuisineArr);
        comboCuisine.getSelectionModel().select("Alla");
        comboIngredient.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                backend.setMainIngredient(t1);
                updateRecipeList();
            }
        });
        comboCuisine.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                backend.setCuisine(t1);
                updateRecipeList();
            }
        });
        ToggleGroup difficultyToggleGroup = new ToggleGroup();
        radioAll.setToggleGroup(difficultyToggleGroup);
        radioEasy.setToggleGroup(difficultyToggleGroup);
        radioMedium.setToggleGroup(difficultyToggleGroup);
        radioHard.setToggleGroup(difficultyToggleGroup);
        radioAll.setSelected(true);
        difficultyToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if (difficultyToggleGroup.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) difficultyToggleGroup.getSelectedToggle();
                    backend.setDifficulty(radioButtonMap.get(selected));
                    updateRecipeList();
                }
            }
        });
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 40, 10);
        spinPrice.setValueFactory(valueFactory);
        spinPrice.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                backend.setMaxPrice(t1);
                updateRecipeList();
            }
        });
        spinPrice.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1) {
                    Integer value = Integer.valueOf(spinPrice.getEditor().getText());
                    backend.setMaxPrice(value);
                    updateRecipeList();
                }
            }
        });
        slideTime.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNum, Number newNum) {
                Integer value = newNum.intValue() * 10;
                labelTime.setText(value + " min");
                if (value != null && !newNum.equals(oldNum) && !slideTime.isValueChanging()) {
                    backend.setMaxTime(value);
                    updateRecipeList();
                }
            }
        });

    }

    public void openDetailedPane(Recipe recipe) {
        populateDetailPane(recipe);
        paneDetailed.toFront();
    }

    @FXML
    public void closeDetailedPane() {
        paneSearch.toFront();
    }

    void populateDetailPane(Recipe recipe) {
        labelDetailedRecipeName.setText(recipe.getName());
        imgDetailedRecipe
                .setImage(recipe.getFXImage(imgDetailedRecipe.getFitWidth(), imgDetailedRecipe.getFitHeight()));
    }

    private void populateMainIngredientComboBox() {
        Callback<ListView<String>, ListCell<String>> cellFactory = new Callback<ListView<String>, ListCell<String>>() {

            @Override
            public ListCell<String> call(ListView<String> p) {

                return new ListCell<String>() {

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        setText(item);

                        if (item == null || empty) {
                            setGraphic(null);
                        }

                        else {
                            Image icon = null;
                            String iconPath;
                            try {
                                switch (item) {

                                    case "Kött":
                                        iconPath = "RecipeSearch/resources/icon_main_meat.png";
                                        icon = new Image(this.getClass().getResourceAsStream("/home/casino/workspace/GUI/GUI_labb_2/src/main/resources/icon_main_meat.png"));
                                        break;
                                    case "Fisk":
                                        iconPath = "RecipeSearch/resources/icon_main_fish.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Kyckling":
                                        iconPath = "RecipeSearch/resources/icon_main_chicken.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Vegetarisk":
                                        iconPath = "RecipeSearch/resources/icon_main_veg.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                }
                            } catch (NullPointerException ex) {
                                // This should never happen in this lab but could load a default image in case
                                // of a NullPointer
                            }
                            ImageView iconImageView = new ImageView(icon);
                            iconImageView.setFitHeight(12);
                            iconImageView.setPreserveRatio(true);
                            setGraphic(iconImageView);
                        }
                    }
                };
            }
        };
        comboIngredient.setButtonCell(cellFactory.call(null));
        comboIngredient.setCellFactory(cellFactory);
    }
}