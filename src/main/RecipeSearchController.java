
package main;

import java.net.URL;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import se.chalmers.ait.dat215.lab2.Ingredient;
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

    // Detailed view components
    @FXML
    public ImageView imgDetailedRecipe;
    @FXML
    public Button btnCloseDetailedRecipe;
    @FXML
    public Label labelDetailedRecipeName;
    @FXML
    public TextArea labelDetailedIngredients;
    @FXML
    public TextArea labelDetailedDescription;
    @FXML
    public TextArea labelDetailedInstruction;
    @FXML
    public Pane paneDetailed;
    @FXML
    public ImageView imgDetailedDifficuly;
    @FXML
    public ImageView imgDetailedCuisine;
    @FXML
    public ImageView imgDetailedIngredient;
    @FXML
    public Label labelDetailedPrice;
    @FXML
    public Label labelDetailedTime;

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
        populateMainIngredientComboBox();
        populateCuisineComboBox();

    }

    public void openDetailedPane(Recipe recipe) {
        populateDetailPane(recipe);
        paneDetailed.toFront();
    }

    @FXML
    public void closeDetailedPane() {
        paneSearch.toFront();
    }

    @FXML
    public void mouseTrap(Event e){
        e.consume();
    }

    void populateDetailPane(Recipe recipe) {
        labelDetailedRecipeName.setText(recipe.getName());
        imgDetailedRecipe
                .setImage(recipe.getFXImage(imgDetailedRecipe.getFitWidth(), imgDetailedRecipe.getFitHeight()));
        imgDetailedCuisine.setImage(getImage(recipe.getCuisine()));
        imgDetailedDifficuly.setImage(getImage(recipe.getDifficulty()));
        imgDetailedIngredient.setImage(getImage(recipe.getMainIngredient()));
        labelDetailedDescription.setText(recipe.getDescription());
        labelDetailedIngredients.setText(getIngredientsString(recipe.getIngredients()));
        labelDetailedInstruction.setText(recipe.getInstruction());
        labelDetailedTime.setText(recipe.getTime() + " minuter");
        labelDetailedPrice.setText(recipe.getPrice() + " kr");

    }

    private String getIngredientsString(List<Ingredient> ingredientsList) {
        String ingredients = "";
        for (Ingredient item : ingredientsList) {
            String itemString = item.toString();
            ingredients += itemString + '\n';
        }
        return ingredients;
    }

    public Image getImage(String imageObj) {
        String iconPath;

        switch (imageObj) {
            case "Sverige" -> iconPath = "main/resources/icon_flag_sweden.png";
            case "Afrika" -> iconPath = "main/resources/icon_flag_africa.png";
            case "Asien" -> iconPath = "main/resources/icon_flag_asia.png";
            case "Frankrike" -> iconPath = "main/resources/icon_flag_france.png";
            case "Grekland" -> iconPath = "main/resources/icon_flag_greece.png";
            case "Indien" -> iconPath = "main/resources/icon_flag_india.png";
            case "Lätt" -> iconPath = "main/resources/icon_difficulty_easy.png";
            case "Medel" -> iconPath = "main/resources/icon_difficulty_medium.png";
            case "Svår" -> iconPath = "main/resources/icon_difficulty_hard.png";
            case "Kött" -> iconPath = "main/resources/icon_main_meat.png";
            case "Fisk" -> iconPath = "main/resources/icon_main_fish.png";
            case "Kyckling" -> iconPath = "main/resources/icon_main_chicken.png";
            case "Vegetarisk" -> iconPath = "main/resources/icon_main_veg.png";
            default -> iconPath = "main/resources/icon_difficulty_easy.png";
        }
        return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
    }

    public Image getSquareImage(Image image) {

        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;

        if (image.getWidth() > image.getHeight()) {
            width = (int) image.getHeight();
            height = (int) image.getHeight();
            x = (int) (image.getWidth() - width) / 2;
            y = 0;
        }

        else if (image.getHeight() > image.getWidth()) {
            width = (int) image.getWidth();
            height = (int) image.getWidth();
            x = 0;
            y = (int) (image.getHeight() - height) / 2;
        }

        else {
            // Width equals Height, return original image
            return image;
        }
        return new WritableImage(image.getPixelReader(), x, y, width, height);
    }

    private void populateCuisineComboBox() {
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
                        } else {
                            Image icon = null;
                            String iconPath;
                            try {
                                switch (item) {
                                    case "Sverige" -> iconPath = "main/resources/icon_flag_sweden.png";
                                    case "Afrika" -> iconPath = "main/resources/icon_flag_africa.png";
                                    case "Asien" -> iconPath = "main/resources/icon_flag_asia.png";
                                    case "Frankrike" -> iconPath = "main/resources/icon_flag_france.png";
                                    case "Grekland" -> iconPath = "main/resources/icon_flag_greece.png";
                                    case "Indien" -> iconPath = "main/resources/icon_flag_india.png";
                                    default -> iconPath = null;
                                }
                                icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
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
        comboCuisine.setButtonCell(cellFactory.call(null));
        comboCuisine.setCellFactory(cellFactory);
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
                        } else {
                            Image icon = null;
                            String iconPath;
                            try {
                                switch (item) {
                                    case "Kött" -> iconPath = "main/resources/icon_main_meat.png";
                                    case "Fisk" -> iconPath = "main/resources/icon_main_fish.png";
                                    case "Kyckling" -> iconPath = "main/resources/icon_main_chicken.png";
                                    case "Vegetarisk" -> iconPath = "main/resources/icon_main_veg.png";
                                    default -> iconPath = null;
                                }
                                icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
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