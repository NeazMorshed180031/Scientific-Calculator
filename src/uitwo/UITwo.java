package uitwo;

import Table.TableMain;
import Table.Entries;
import fsm.FSM;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UITwo extends Application {

    private final String[] Units_Operand = { "m", "km", "s", "min", "h", "day", "Fahrenheit", "Celsius", "N/A" };

    private File file;
    private boolean isFileEmpty = true;

    private Label type = new Label("Type");
    private ComboBox<String> typeComboBox = new ComboBox<>();
    private Label name = new Label("Name");
    private TextField nameTextField = new TextField();
    private Label fsmName = new Label("");
    private Label value = new Label("Value");
    private TextField valueTextField = new TextField();
    private Label fsmValue = new Label("");
    private Label errorValue = new Label("Error");
    private TextField errorTextField = new TextField();
    private Label fsmError = new Label("");
    private Label unitLabel = new Label("Unit");
    private ComboBox<String> unitsComboBox = new ComboBox<>();
    private Button tableViewButton = new Button("Table View");
    private Button exitButton = new Button("Exit");
    private Button addButton = new Button("Add");
    private Label message = new Label("");

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Add new value");

        typeComboBox.getItems().addAll("Constant","Variable");

        setupLabelUI(type, "Arial", 18, 50, Pos.CENTER_LEFT, 20, 15);
        setupComboBox(typeComboBox, 150, 10);

        setupLabelUI(name, "Arial", 18, 50, Pos.CENTER_LEFT, 20, 65);
        setupTextUI(nameTextField, "Arial", 18, 200, Pos.CENTER_LEFT, 150, 60, true);
        setupLabelUI(fsmName, "Arial", 18, 200, Pos.CENTER_LEFT, 150, 95);

        setupLabelUI(value, "Arial", 18, 50, Pos.CENTER_LEFT, 20, 135);
        setupTextUI(valueTextField, "Arial", 18, 200, Pos.CENTER_LEFT, 150, 130, true);
        setupLabelUI(fsmValue, "Arial", 18, 200, Pos.CENTER_LEFT, 150, 165);

        setupLabelUI(errorValue, "Arial", 18, 50, Pos.CENTER_LEFT, 20, 255);
        setupTextUI(errorTextField, "Arial", 18, 200, Pos.CENTER_LEFT, 150, 245, true);
        setupLabelUI(fsmError, "Arial", 18, 200, Pos.CENTER_LEFT, 150, 280);

        setupLabelUI(unitLabel, "Arial", 18, 50, Pos.CENTER_LEFT, 20, 345);
        setupComboBox(unitsComboBox, 150, 340);

        setupButtonUI(addButton, "Arial", 18, 100, Pos.CENTER, 20, 400);
        setupButtonUI(tableViewButton, "Arial", 18, 100, Pos.CENTER, 160, 400);
        setupButtonUI(exitButton, "Arial", 18, 100, Pos.CENTER, 300, 400);

        setupLabelUI(message, "Arial", 18, 400, Pos.CENTER_LEFT, 20, 470);

        fsmValue.setTextFill(Color.web("#FF0000"));
        fsmError.setTextFill(Color.web("#FF0000"));
        fsmName.setTextFill(Color.web("#FF0000"));
        message.setTextFill(Color.web("#FF0000"));

        // To close the current window.
        exitButton.setOnAction(event -> stage.close());

        addButton.setOnAction(event -> {
            file = new File(System.getProperty("user.dir") + "//repo.txt");
            try {
                if (file.createNewFile()) {
                    writeIntoFile();
                } else {
                    writeIntoFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // To open the table view.
        tableViewButton.setOnAction(event -> {
            new TableMain().start(new Stage());
        });

        for (int i = 0; i <= Units_Operand.length-1; i++) {
            unitsComboBox.getItems().add(Units_Operand[i]);
        }

        nameTextField.textProperty().addListener((observable, oldvalue, newValue) -> {
            addButton.setDisable(true);
            fsmName.setText("");
            fsmName.setText(FSM.checkVariableName(nameTextField.getText()));
            if (fsmName.getText().isEmpty() || fsmName.getText().equals("")) {
                addButton.setDisable(false);
            }
        });

        valueTextField.textProperty().addListener((observable, oldvalue, newValue) -> {
            addButton.setDisable(false);
            fsmValue.setText(FSM.checkMeasureValue(valueTextField.getText()));
            if (!fsmValue.getText().isEmpty()) addButton.setDisable(true);
            message.setText("");
        });

        errorTextField.textProperty().addListener((observable, oldvalue, newValue) -> {
            addButton.setDisable(false);
            fsmError.setText(FSM.checkMeasureValue(errorTextField.getText()));
            if (!fsmError.getText().isEmpty()) addButton.setDisable(true);
            message.setText("");
        });

        Pane pane = new Pane();

        Scene scene = new Scene(pane, 700, 500);
        stage.setScene(scene);
        stage.show();

        pane.getChildren().addAll(type, typeComboBox, name, nameTextField, fsmName, value, valueTextField, fsmValue, errorTextField, errorValue, fsmError, unitsComboBox, unitLabel
        , tableViewButton, addButton, exitButton, message);

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y) {
        l.setFont(Font.font(ff, f));
        l.setMinWidth(w);
        l.setAlignment(p);
        l.setLayoutX(x);
        l.setLayoutY(y);
    }

    /**********
     * Private local method to initialize the standard fields for a text field
     */
    private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e) {
        t.setFont(Font.font(ff, f));
        t.setMinWidth(w);
        t.setMaxWidth(w);
        t.setAlignment(p);
        t.setLayoutX(x);
        t.setLayoutY(y);
        t.setEditable(e);
    }

    /**********
     * Private local method to initialize the standard fields for a button
     */
    private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y) {
        b.setFont(Font.font(ff, f));
        b.setMinWidth(w);
        b.setAlignment(p);
        b.setLayoutX(x);
        b.setLayoutY(y);
    }

    private void setupRadioButtonUI(RadioButton b, String ff, double f, Pos p, double x, double y) {
        b.setFont(Font.font(ff, f));
        b.setAlignment(p);
        b.setLayoutX(x);
        b.setLayoutY(y);
    }

    private void setupComboBox(ComboBox<String> b, double x, double y) {
        b.setLayoutX(x);
        b.setLayoutY(y);
    }

    // To save inside the repository
    public void writeIntoFile() {

        if (!fsmName.getText().isEmpty() || !fsmError.getText().isEmpty() || !fsmValue.getText().isEmpty()) {
            message.setText("Errors exists! Can't add!");
            return;
        }

        String name = "", measuredValue = "", errorValue = "", unit = "" , type = "";

        try {
            name = nameTextField.getText().trim();
            measuredValue = valueTextField.getText().trim();
            errorValue = errorTextField.getText().trim();
            unit = unitsComboBox.getSelectionModel().getSelectedItem().trim();
            type = typeComboBox.getSelectionModel().getSelectedItem().trim();

            if (name.isEmpty() || measuredValue.isEmpty() || errorValue.isEmpty()) {
                message.setText("Please fill all fields!");
                return;
            }

        } catch (Exception e) {
            message.setText("Please fill all fields!");
            return;
        }

        if (doNameExists(name)) {
            message.setText("Name already exists!");
            return;
        }

        try {
            message.setText("");
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(name + "," + measuredValue + "," + errorValue + "," + unit + "," + type + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean doNameExists(String name) {
        ArrayList<Entries> entries = Entries.readRepo();
        int i = 0;
        while (i <= entries.size()-1) {
            String x = entries.get(i).name.trim();
            if (x.equals(name)) return true;
            i++;
        }
        return false;
    }

}
