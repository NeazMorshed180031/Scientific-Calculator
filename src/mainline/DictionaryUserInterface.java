package mainline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

import FullCalculator.NewEquationSolver;

import dictionary.DictEntry;
import dictionary.Dictionary;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

/*******
 * <p>
 * Title: UserInterface Class.
 * </p>
 *
 * <p>
 * Description: This controller class describes the user interface for the
 * dictionary and perform the functionality of it on its pane
 * </p>
 *
 * <p>
 * Copyright: Lynn Robert Carter ï¿½ 2018-08-04
 * </p>
 *
 * @author Lynn Robert Carter and Neaz Morshed
 *
 *
 * @version 2.03 2018-07-19 Baseline
 * @version 2.10 2018-09-11 It is a User Interface designed for Dictionary.
 * @version 4.20 
 * 29-04-2020 - The Calculator Has been upgraded to store data and manipulate data
 *
 */
public class DictionaryUserInterface {

    private static boolean readingTimerIsRunning = false;
    final Stage editDialog = new Stage();

    // The User Interface widgets used to control the user interface and start and stop the simulation
    /**********************************************************************************************
     * Class Attributes
     **********************************************************************************************/

    // Attributes used to establish the display and control panel within the window
    // provided to us
    private double controlPanelHeight = Mainline.WINDOW_HEIGHT - 180;
    private int marginWidth = 20;
    // Label the Message just above the text field of 'Load the file' button
    private Label label_EnterFile = new Label("Enter the data file's name here:");
    private TextField text_EnterFile = new TextField(); // Add the text field for the button
    private TextField text_SearchWord = new TextField(); // Add the text field for the search button
    private Button button_Load = new Button("Load the Dictionary"); // Add the button for the
    private Button button_search = new Button("Search");
    private Button button_Add = new Button("Add ");
    private Button button_Edit = new Button("Edit");
    private Button button_Delete = new Button("Delete");
    private Button button_RunExpression = new Button("Run Expression");
    private Button button_RunProgram = new Button("Run Program");
    @SuppressWarnings("unused")
    private int numberOfSearchItemsFound = -1;
    
    
    
	Label assignment1 = new Label("How to write print statement -> print " + "Enter the Value of a");
	Label assignment2 = new Label("How to write input statement -> input a=");

    // The attributes used to inform the user if the file name specified exists or not
    // The attributes used to specify and assess the validity of the data file that
    // defines the game
    private String str_FileName; // The string that the user enters for the file name
    private Scanner scanner_Input = null; // The Scanners used to evaluate whether or not the
    private Label FileFound = new Label(""); // label the messages for the file found
    private Label FIleNotFound = new Label(""); // label the messages for the file not found
    private Label readLine = new Label(); // Label to display the number of lines found
    // These attributes are used to tell the user, in detail, about errors in the input data
    private String errorMessage_FileContents = "";
    private Label message_ErrorDetails = new Label();
    // This attribute hold a reference to the Pane that roots the user interface's window
    private Pane window;
    // These attributes put a graphical frame around the portion of the window that receives the
    // black squares representing alive cells
    private Rectangle rect_outer = new Rectangle(0, 0, Mainline.WINDOW_WIDTH, controlPanelHeight - 5);
    private Rectangle rect_middle = new Rectangle(5, 5, Mainline.WINDOW_WIDTH - 10, controlPanelHeight - 15);
    // that will be displayed
    private Rectangle rect_inner = new Rectangle(6, 6, Mainline.WINDOW_WIDTH - 12, controlPanelHeight - 17);
    private TextArea blk_Text = new TextArea(""); // This attribute holds the text
    private ComboBox<String> editComboBox = new ComboBox<String>();
    private List<DictEntry> editDefinitionList = new Vector<DictEntry>();
    private Label lbl_1SelectDefinition = new Label("");
    private Label lbl_2EditDefinition = new Label("");
    private Button btn_SaveAddedChanges = new Button("Add");
    private Button btn_SaveEditChanges = new Button("Save");
    private Button btn_run = new Button("Run");
    
    private Button btn_runEquation = new Button("Run Equation");
    private Button btn_input = new Button("Input");
    
    private Button btn_DeletePopup = new Button("Delete");
    private Label lbl_2aWord = new Label("Input Name of the Equation");
    private TextField fld_2aWord = new TextField();
    private Label lbl_2bDefinition = new Label("Input the equation");
    private TextArea txt_2bDefinition = new TextArea();
    private TextArea txt_2bDefinition2 = new TextArea();
    private Label lbl_3SaveDefinition = new Label("");

    // The application scans the prospective input files to make sure that the file
    // conforms to the
    // application specification and makes it difficult for hacker to take control
    // of the
    // computer by feeding the application data that corrupts the operating system.
    private int ndxSaveEdit;
    // Boolean flag to let the system know when first read is done and we are now
    // appending
    private boolean firstRead = true;
    // This is the attribute that holds the reference to the dictionary
    private Dictionary theDictionary = null;

    private String searchString; // The attribute that holds the string the user wishes to search for
    private int numberEntriesFormatted; // A counter used to display how many entries have been formatted

    /**********************************************************************************************
     * Constructors
     **********************************************************************************************/
    /**********
     * This constructor established the user interface with all of the graphical
     * elements that are use to make the user interface work.
     *
     * @param theRoot This parameter is the Pane that JavaFX expects the application
     *                to use when it sets up the GUI elements.
     */
    public DictionaryUserInterface(Pane theRoot) {
        window = theRoot; // Save a reference to theRoot so we can update that Pane during the
        // application's execution

        editDialog.initModality(Modality.APPLICATION_MODAL);
        // editDialog.initOwner(primaryStage);

        // This attributes fills the color of the border frame for the output
        rect_outer.setFill(Color.LIGHTGRAY);
        rect_middle.setFill(Color.BLACK);
        rect_inner.setFill(Color.WHITE);
        
        
        setupLabelUI(assignment1, "Arial", 16, 200, Pos.CENTER, 20, 20);
		assignment1.setTextFill(Color.web("#2ECC71"));

		setupLabelUI(assignment2, "Arial", 16, 200, Pos.CENTER, 20, 40);
		assignment2.setTextFill(Color.web("#2ECC71"));
        

        // Setup the text area into the window and just within the above frame and make
        // it not editable
        setupTextAreaUI(blk_Text, "Monaco", 14, 10, 200, Mainline.WINDOW_WIDTH - 125, controlPanelHeight + 270, false);

        // Label the text field message for entering the filename.
        setupLabelUI(label_EnterFile, "Arial", 18, Mainline.WINDOW_WIDTH - 20, Pos.BASELINE_LEFT, marginWidth,
                controlPanelHeight + 80);

        // Establish the link check point to check if a file of that name exists and if
        // so, whether or not the data is valid
        text_EnterFile.textProperty().addListener((observable, oldValue, newValue) -> {
            checkFileValidation();
        });

        // Setup the Load button to load the file which display the diction in the
        // window and console
        setupButtonUI(button_Load, "Arial", 18, 120, Pos.BASELINE_LEFT, Mainline.WINDOW_WIDTH - 210,
                controlPanelHeight + 120);

        // Establish the text input widget so the user can enter the name of the file
        // and load it
        setupTextUI(text_EnterFile, "Arial", 18, Mainline.WINDOW_WIDTH / 5, Pos.BASELINE_LEFT, marginWidth,
                controlPanelHeight + 120, true);

        // Setup the event handling of the load button so that when press it call the
        // method to display the dictionary
        button_Load.setOnAction((event) -> {
            ShowDictionary();
        });

        // Establish the link check point to check if a search string exists or not.
        text_SearchWord.textProperty().addListener((observable, oldValue, newValue) -> {
            checkSearchString();
        });

        // Setup the search button which is pressed by the user to search the word
        setupButtonUI(button_search, "Arial", 18, 120, Pos.BASELINE_LEFT, Mainline.WINDOW_WIDTH - 210,
                controlPanelHeight + 160);

        setupButtonUI(button_Add, "Arial", 18, 120, Pos.BASELINE_LEFT, Mainline.WINDOW_WIDTH - 450,
                controlPanelHeight + 100);

        setupButtonUI(button_Edit, "Arial", 18, 120, Pos.BASELINE_LEFT, Mainline.WINDOW_WIDTH - 300,
                controlPanelHeight + 100);

        setupButtonUI(button_Delete, "Arial", 18, 120, Pos.BASELINE_LEFT, Mainline.WINDOW_WIDTH - 150,
                controlPanelHeight + 100);


        setupButtonUI(button_RunExpression, "Arial", 18, 120, Pos.BASELINE_LEFT, Mainline.WINDOW_WIDTH - 20,
                controlPanelHeight + 100);
        
        
        setupButtonUI(button_RunProgram, "Arial", 18, 120, Pos.BASELINE_LEFT, Mainline.WINDOW_WIDTH  -15,
                controlPanelHeight + 160);
        


        // Establish the search input widget so that user can enter the name of the
        // alphabet or word that he wants to search for
        setupTextUI(text_SearchWord, "Arial", 18, Mainline.WINDOW_WIDTH / 2, Pos.BASELINE_LEFT, marginWidth,
                controlPanelHeight + 160, true);

        // SetUp the event handling of the search button so that it calls the method for
        // the searching
        button_search.setOnAction((event) -> {
            performSearch();
        });

        button_RunExpression.setOnAction((event) -> {
            RunExpressionPopup();
        });
        
        button_RunProgram.setOnAction((event) -> {
        	RunProgrampopup();
        });

        button_Add.setOnAction((event) -> {
            setupDictionaryAdditionPopup();
        });

        button_Edit.setOnAction((event) -> {
            setupDictionaryEditPopup();
        });

        button_Delete.setOnAction((event) -> {
            setupDictionaryDeletePopup();
        });

        // Disable the button as they appear grayed out in starting
        button_Load.setDisable(true);
        button_search.setDisable(true);
        button_Add.setDisable(false);
        button_Edit.setDisable(false);
        button_Delete.setDisable(false);
        button_RunExpression.setDisable(false);
        button_RunProgram.setDisable(false);

        // Setup the Label in the window to display the number of definations present in
        // the dictionary.
        setupLabelUI(readLine, "Arial", 18, Mainline.WINDOW_WIDTH - 20, Pos.BASELINE_LEFT, marginWidth,
                controlPanelHeight);

        // The following set up the control panel messages for messages and information about errors

        // setup the label for the message which come when we enter the right file name
        setupLabelUI(FileFound, "Arial", 18, 150, Pos.BASELINE_LEFT, 350, controlPanelHeight);
        FileFound.setStyle("-fx-text-fill: green; -fx-font-size: 18;");

        // Setup the label for the message which come when we file is not present or
        // wrong filename.
        setupLabelUI(FIleNotFound, "Arial", 18, 150, Pos.BASELINE_LEFT, 350, controlPanelHeight);
        FIleNotFound.setStyle("-fx-text-fill: red; -fx-font-size: 18;");

        // SetUp the labeling of the message at the time of error messages details
        setupLabelUI(message_ErrorDetails, "Arial", 16, Mainline.WINDOW_WIDTH, Pos.BASELINE_LEFT, 20,
                controlPanelHeight);
        message_ErrorDetails.setStyle("-fx-text-fill: red; -fx-font-size: 16;");

        // Place all of the just-initialized GUI elements into the pane with the
        // exception of the
        // Stop button.
        theRoot.getChildren().addAll(rect_outer, rect_middle, rect_inner, FileFound,
                FIleNotFound, message_ErrorDetails, label_EnterFile, text_EnterFile, blk_Text, button_Load, readLine,assignment1,assignment2);

        //Responsible for loading the Dictionary file once name is typed
//		str_FileName = "First20K.txt";
//		if (new File(str_FileName).exists())
//		ShowDictionary();
    }


    /**********************************************************************************************
     * Helper methods - Used to set up the JavaFX widgets and simplify the code
     * above
     **********************************************************************************************/

    /**********
     * Private local method to initialize the standard fields for a label
     */
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
     * Private local method to initialize the standard fields for a text field
     */
    private void setupTextAreaUI(TextArea t, String ff, double f, double x, double y, double w, double h, boolean e) {
        t.setFont(Font.font(ff, f));
        t.setPrefWidth(w);
        t.setPrefHeight(h);
        t.setLayoutX(x);
        t.setLayoutY(y);
        t.setEditable(e);
        t.setWrapText(true);
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

    /**********************************************************************************************
     * Action methods - Used cause things to happen with the set up or during the
     * simulation
     **********************************************************************************************/
    /**********
     * This routine checks, after each character is typed, to see if a file of that
     * name is found, it checks to see if the contents conforms to the
     * specification. If it does, the Load button is enabled and a green message is
     * displayed If it does not, the Load button is disabled and a red error message
     * is displayed If a file is not found, a warning message is displayed and the
     * button is disabled. If the input is empty, all the related messages are
     * removed and the Load button is disabled.
     */
    private void checkFileValidation() {
        str_FileName = text_EnterFile.getText().trim(); // Whenever the text area for the file name is changed
        if (str_FileName.length() <= 0) { // this routine is called to see if it is a valid filename.
            FileFound.setText(""); // Reset the messages
            FIleNotFound.setText(""); // to empty
            scanner_Input = null;
        } else // If there is something in the file name text area
            try { // this routine tries to open it and establish a scanner.
                scanner_Input = new Scanner(new File(str_FileName));

                // There is a readable file there... this code checks the data to see if it is
                // valid
                // for this application (Basic user input errors are GUI issues, not analysis
                // issues.)
                if (fileContentsAreValid()) {
                    FileFound.setText("File found and the contents are valid!");
                    message_ErrorDetails.setText("");
                    FIleNotFound.setText("");
                    button_Load.setDisable(false);
                }
                // If the methods returns false, it means there is a problem with input file
                else { // and the method has set up a String to explain what the issue is
                    FileFound.setText("");
                    FIleNotFound.setText("File found, but the contents are not valid!");
                    message_ErrorDetails.setText(errorMessage_FileContents);
                    button_Load.setDisable(true);
                }
            } catch (FileNotFoundException e) { // If an exception is thrown, the file name
                FileFound.setText(""); // that the button to run the analysis is
                FIleNotFound.setText("File not found!"); // not enabled.
                message_ErrorDetails.setText("");
                scanner_Input = null;
                theDictionary = null;
                button_Load.setDisable(true);
            }
    }

    /**********
     * This method reads in the contents of the data file and discards it as quickly
     * as it reads it in order to verify that the data meets the input data
     * specifications and helps reduce the change that invalid input data can
     * lead to some kind of hacking.
     *
     * @return true - when the input file *is* valid when the input file data is
     *         *not* valid - The method also sets a string with
     */
    private boolean fileContentsAreValid() {

        // Declare and initialize data variables used to control the method
        @SuppressWarnings("unused")
        int numberOfLinesInTheInputFile = 0; // This attribute sets the number of lines set during the first read
        String firstLine = "";

        // Read in the first line and verify that it has the proper header
        if (scanner_Input.hasNextLine()) {
            firstLine = scanner_Input.nextLine().trim(); // Fetch the first line from the file
            if (firstLine.equalsIgnoreCase("Demonstration")) // See if it is what is expected
                numberOfLinesInTheInputFile = 1; // If so, count it as one line
            else { // If not, issue an error message
                return false; // and return false
            }
        } else {
            // If the execution comes here, there was no first line in the file
            return false;
        }

        // Process each and every subsequent line in the input to make sure that none
        // are too long
        while (scanner_Input.hasNextLine()) {
            numberOfLinesInTheInputFile++; // Count the number of input lines

            // Read in the line
            String inputLine = scanner_Input.nextLine();

            // Verify that the input line is not larger than 250 characters...
            if (inputLine.length() > 250) {
                //				// If it is larger than 250 characters, display an error message on the console
                //				System.out.println("\n***Error*** Line " + numberOfLinesInTheInputFile + " contains "
                //						+ inputLine.length() + " characters, which is greater than the limit of 250.");

                // Stop reading the input and tell the user this data file has a problem
                return false;
            }
        }
        // Should the execution reach here, the input file appears to be valid
        errorMessage_FileContents = ""; // Clear any messages
        return true; // End of file - data is valid
    }

    /**********
     * This method reads in the contents of the data file and Once the it is done,
     * the method displays the content took to the UI. After all of the attributes
     * are reset, a check for a valid file name for reading is made as well as a
     * check for the string search.
     */

    private void ShowDictionary() {
        fileOpener();

        button_search.setDisable(true);
        button_Add.setDisable(false);
        button_Edit.setDisable(false);
        button_Delete.setDisable(false);
        button_RunExpression.setDisable(false);
        button_RunProgram.setDisable(false);
        window.getChildren().remove(button_Load);

        // Show the Display button until more is read in or the analysis in performed.
        try {
            final Scanner dataReader = new Scanner(new File(str_FileName));
            if (firstRead)
                theDictionary = new Dictionary();
            theDictionary.defineDictionary(dataReader);
        } catch (FileNotFoundException e) {
            // Since we have already done this check, this exception should never happen
            System.out.println("***Error*** A truly unexpected error occured.");
        }
        // This displays the results of the analysis on the main window of the user
        // interface and the console
        String result = "The Number of Definitions: " + theDictionary.getNumEntries() + "\n";
        readLine.setText(result);
        result += theDictionary.listAll(); // Append the contents of the dictionary
        // blk_Text.setText(result);

        window.getChildren().add(button_search); // add the button search in place of the load button
        window.getChildren().add(button_Add);
        window.getChildren().add(button_Edit);
        window.getChildren().add(button_Delete);
        window.getChildren().add(button_RunExpression);
        window.getChildren().add(button_RunProgram);
        label_EnterFile.setText("Enter Search text:"); // add the label message just above the seach field
        label_EnterFile.setLayoutY(80);
        //text_EnterFile.setText(""); // leave the search test field empty

        button_Load.setDisable(false); // disbale the load button so that search button will come in that place
        window.getChildren().remove(text_EnterFile); // remove the text field of load button from the window
        window.getChildren().add(text_SearchWord); // place the search field in place of load button field in widow
    }

    
    
    
    
    

    public void fileOpener() {

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(str_FileName)));
            String line;
            line = br.readLine();

            while ((line = br.readLine()) != null) {

                blk_Text.appendText(line);
                blk_Text.appendText("\n");

            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * Given the user has changed the search text string or has changed the file
     * name, reset the search message and set up the search button if there is a
     * dictionary, there are dictionary entries in the dictionary, the system is not
     * reading in a new dictionary, and there is at least one character in the
     * search text field.
     */
    private void checkSearchString() {

        // If there isn't a dictionary, or there are no dictionary entries in the
        // dictionary or
        // the system is still reading in the dictionary, disable the search button and
        // return
        if (theDictionary == null || theDictionary.getNumEntries() <= 0 || readingTimerIsRunning) {
            button_search.setDisable(true);
            button_Add.setDisable(true);
            button_Edit.setDisable(true);
            button_Delete.setDisable(true);
            button_RunExpression.setDisable(true);
            button_RunProgram.setDisable(true);
            return;
        }
        // If there is a searchable dictionary and the length of the search string is
        // positive,
        // enable the search button else disable it
        searchString = text_SearchWord.getText().trim();
        if (searchString.length() > 0) {
            button_search.setDisable(false);
            button_Add.setDisable(false);
            button_Edit.setDisable(false);
            button_Delete.setDisable(false);
            button_RunExpression.setDisable(false);
            button_RunProgram.setDisable(false);
        } else {
            button_search.setDisable(true);
            button_Add.setDisable(false);
            button_Edit.setDisable(false);
            button_Delete.setDisable(false);
            button_RunExpression.setDisable(false);
            button_RunProgram.setDisable(false);
        }
    }


    /**
     * Search for all words that contain the specified search string. along with
     * their definitions to the display on the UI.
     */
    public void performSearch() throws NullPointerException {
        searchString = text_SearchWord.getText().trim();

        // Verify that there is a dictionary and there is a search string.
        if (theDictionary != null && searchString.length() > 0) {

            // Set the search string in the dictionary class
            theDictionary.setSearchString(searchString);

            // Create an array of dictionary entries to hold those entries that match the
            // search string
            // We assume there can be no more than all possible dictionary entries returned
            int numFound = 0;
            DictEntry[] found = new DictEntry[theDictionary.getNumEntries()];
            // Keep searching for matching dictionary entries and saving them until no more
            // are found
            DictEntry matchingentry = theDictionary.findNextEntry();
            while (matchingentry != null) {
                found[numFound++] = matchingentry;
                matchingentry = theDictionary.findNextEntry();
            }
            //System.out.println(new Integer(numFound).toString());
            // Update the GUI with the number of entries found
            label_EnterFile.setText("The number of search items found: " + new Integer(numFound).toString());
            label_EnterFile.setLayoutY(80);
            // Reset the search result message

            text_SearchWord.setText("");

            // Start with an empty screen and append each matching entry onto the end of the
            // display
            blk_Text.setText(""); // display the screen blank
            String result = ""; // empty string
            blk_Text.setText(result);

            for (numberEntriesFormatted = 0; numberEntriesFormatted < numFound; numberEntriesFormatted++) {
                result = result + found[numberEntriesFormatted].toString();

            }
            blk_Text.setText(result); // display all the search value in the UI.
        }
    }

    private void setupDictionaryDeletePopup() {

   
        Group dictionaryEditControls = new Group();

   
        theDictionary.setSearchString(text_SearchWord.getText());

        // Reset the comboBox list and the list of definitions back to empty
        editComboBox.getItems().clear();
        editDefinitionList.clear();
        editDialog.setTitle("Select and Delete");

        // Fetch the matching items and place them into a list
        for (int ndx = 0; ndx < theDictionary.getNumEntries(); ndx++) {
            DictEntry d = theDictionary.findNextEntry();
            editComboBox.getItems().add(d.getWord());
            editDefinitionList.add(d);
        }

        // Set up the pop-up window
        Scene dialogScene = new Scene(dictionaryEditControls, 400, 200);

        // Set up the fields for the edit pop-up window
        setupLabelUI(lbl_1SelectDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 10);
        // setupTextAreaUI(txt_2bDefinition, "Arial", 14, 450, 140, 30, 180, true);
        setupLabelUI(lbl_3SaveDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 120);

        // Set up the comboBox to select one of the dictionary items that matched
        editComboBox.setLayoutX(45);
        editComboBox.setLayoutY(35);
        editComboBox.getSelectionModel().selectFirst();
        editComboBox.setOnAction((event) -> {
            loadTheEditDefinitionData();
        });

        // Set the screen so we can see it
        editDialog.setScene(dialogScene);

        // Load in the current word and definition data into the edit fields
        loadTheEditDefinitionData();

        setupButtonUI(btn_DeletePopup, "Arial", 12, 50, Pos.BASELINE_LEFT, 30, 150);
        btn_DeletePopup.setOnAction((event) -> {
            DeleteDefinitionData();
        });

        // Populate the pop-up window with the GUI elements
        dictionaryEditControls.getChildren().addAll(editComboBox, lbl_1SelectDefinition, lbl_3SaveDefinition,
                btn_DeletePopup);

        // Show the pop-up window
        editDialog.show();

    }

    private void DeleteDefinitionData() {
        @SuppressWarnings("unused")
        String fileName = "First20K.txt";
        @SuppressWarnings("unused")
        String lineToRemove = fld_2aWord.getText();

        System.out.println("File is saved");
        ndxSaveEdit = editComboBox.getSelectionModel().getSelectedIndex();

        theDictionary.remove(ndxSaveEdit);
        System.out.println("File is saved");
        int numEntriesWritten = 0;
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream("First20K.txt"));
            out.write("Dictionary\n");
            for (int i = 0; i < theDictionary.getNumEntries(); i++) {
                out.println(theDictionary.getDictEntry(i));
                numEntriesWritten++;
            }
            out.close();
        } catch (FileNotFoundException exception) {
            System.exit(1);
        }
        System.out.println("The system wrote out " + numEntriesWritten + " entries.");

        System.out.println("File is saved");
        File theDataFile = new File("First20K.txt");
        if (theDataFile.exists())
            System.out.println("File Exists");

        else
            System.out.println("File does not exist");
        try {
            theDataFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //

        // Hide and close the pop-up window
        editDialog.hide();
        editDialog.close();

    }

    private void setupDictionaryAdditionPopup() {
        //

        Group dictionaryEditControls = new Group();

        theDictionary.setSearchString(text_SearchWord.getText());

        // Reset the comboBox list and the list of definitions back to empty
        editComboBox.getItems().clear();
        editDefinitionList.clear();

        // Set up the pop-up window
        Scene dialogScene = new Scene(dictionaryEditControls, 500, 400);

        setupLabelUI(lbl_2aWord, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 10);
        setupTextUI(fld_2aWord, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 50, true);
        setupLabelUI(lbl_2bDefinition, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 100);
        setupTextAreaUI(txt_2bDefinition, "Arial", 14, 30, 140, 450, 150, true);
        setupLabelUI(lbl_3SaveDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 335);

        fld_2aWord.setText("");
        txt_2bDefinition.setText("");

        // Set the screen so we can see it
        editDialog.setScene(dialogScene);

        // Load in the current word and definition data into the edit fields

        //System.out.println("Ok");
        setupButtonUI(btn_SaveAddedChanges, "Arial", 12, 50, Pos.BASELINE_LEFT, 30, 360);
        btn_SaveAddedChanges.setOnAction((event) -> {
            saveTheAddDefinitionData();
        });

        // Populate the pop-up window with the GUI elements
        dictionaryEditControls.getChildren().addAll(lbl_2aWord, fld_2aWord, lbl_2bDefinition, txt_2bDefinition,
                lbl_3SaveDefinition, btn_SaveAddedChanges);

        // Show the pop-up window
        editDialog.show();

    }

    private void setupDictionaryEditPopup() {

        // Set up the pop-up modal dialogue window
        editDialog.setTitle("");
        Group dictionaryEditControls = new Group();

      
        theDictionary.setSearchString(text_SearchWord.getText());

        // Reset the comboBox list and the list of definitions back to empty
        editComboBox.getItems().clear();
        editDefinitionList.clear();

        // Fetch the matching items and place them into a list

        for (int ndx = 0; ndx < theDictionary.getNumEntries(); ndx++) {
            DictEntry d = theDictionary.findNextEntry();
            editComboBox.getItems().add(d.getWord());
            editDefinitionList.add(d);
        }

        // Set up the pop-up window
        Scene dialogScene = new Scene(dictionaryEditControls, 500, 400);

        // Set up the fields for the edit pop-up window
        setupLabelUI(lbl_1SelectDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 10);
        setupLabelUI(lbl_2EditDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 75);
        setupLabelUI(lbl_2aWord, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 100);
        setupTextUI(fld_2aWord, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 120, true);
        setupLabelUI(lbl_2bDefinition, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 155);
        setupTextAreaUI(txt_2bDefinition, "Arial", 14, 30, 180, 450, 150, true);
        setupLabelUI(lbl_3SaveDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 335);

        // Set up the comboBox to select one of the dictionary items that matched
        editComboBox.setLayoutX(25);
        editComboBox.setLayoutY(35);
        editComboBox.getSelectionModel().selectFirst();
        editComboBox.setOnAction((event) -> {
            loadTheEditDefinitionData();
        });

        // Set the screen so we can see it
        editDialog.setScene(dialogScene);

        // Load in the current word and definition data into the edit fields
        loadTheEditDefinitionData();

        setupButtonUI(btn_SaveEditChanges, "Arial", 12, 50, Pos.BASELINE_LEFT, 30, 360);
        btn_SaveEditChanges.setOnAction((event) -> {
            saveTheEditDefinitionData();
        });

        // Populate the pop-up window with the GUI elements
        dictionaryEditControls.getChildren().addAll(editComboBox, lbl_1SelectDefinition, lbl_2EditDefinition,
                lbl_2aWord, fld_2aWord, lbl_2bDefinition, txt_2bDefinition, lbl_3SaveDefinition, btn_SaveEditChanges);

        // Show the pop-up window
        editDialog.show();
    }

    ;


    private void RunExpressionPopup() {

        // Set up the pop-up modal dialogue window
        editDialog.setTitle("");
        Group dictionaryEditControls = new Group();

        // Fetch the search string that user had specified. We know that since the
        // number of
        // search items must be greater than zero for the code to call this method, so
        // there
        // had to be a search string, it had to be greater than zero characters, and at
        // least
        // one dictionary item matched it.
        theDictionary.setSearchString(text_SearchWord.getText());

        // Reset the comboBox list and the list of definitions back to empty
        editComboBox.getItems().clear();
        editDefinitionList.clear();

        // Fetch the matching items and place them into a list

        for (int ndx = 0; ndx < theDictionary.getNumEntries(); ndx++) {
            DictEntry d = theDictionary.findNextEntry();
            editComboBox.getItems().add(d.getWord());
            editDefinitionList.add(d);
        }

        // Set up the pop-up window
        Scene dialogScene = new Scene(dictionaryEditControls, 800, 600);

        // Set up the fields for the edit pop-up window
        setupLabelUI(lbl_1SelectDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 10);
        setupLabelUI(lbl_2EditDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 75);
        setupLabelUI(lbl_2aWord, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 100);
        setupTextUI(fld_2aWord, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 120, true);
        setupLabelUI(lbl_2bDefinition, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 155);
        setupTextAreaUI(txt_2bDefinition, "Arial", 14, 30, 180, 450, 150, true);
        setupTextAreaUI(txt_2bDefinition2, "Arial", 14, 30, 400, 350, 100, true);
        setupLabelUI(lbl_3SaveDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 335);

        // Set up the comboBox to select one of the dictionary items that matched
        editComboBox.setLayoutX(25);
        editComboBox.setLayoutY(35);
        editComboBox.getSelectionModel().selectFirst();
        editComboBox.setOnAction((event) -> {
            loadTheEditDefinitionData();
        });

        // Set the screen so we can see it
        editDialog.setScene(dialogScene);

        // Load in the current word and definition data into the edit fields
        loadTheEditDefinitionData();

        setupButtonUI(btn_run, "Arial", 12, 50, Pos.BASELINE_LEFT, 30, 360);
        btn_run.setOnAction((event) -> {
            Run();

        });

        // Populate the pop-up window with the GUI elements
        dictionaryEditControls.getChildren().addAll(editComboBox, lbl_1SelectDefinition, lbl_2EditDefinition,
                lbl_2aWord, fld_2aWord, lbl_2bDefinition, txt_2bDefinition, lbl_3SaveDefinition, btn_run, txt_2bDefinition2);

        // Show the pop-up window
        editDialog.show();
    }


    /**********
     * This method populates the word/phrase to be defined and the definition fields
     * to be edited based on the comboBox selection the user has performed
     */
    private void loadTheEditDefinitionData() {
        System.out.println("loadTheEditDefinitionData()");
        // Fetch the index of the selected item
        ndxSaveEdit = editComboBox.getSelectionModel().getSelectedIndex();

        // If the index is < 0, this implies that the default item has been selected...
        // it is
        // index location zero.
        if (ndxSaveEdit < 0)
            ndxSaveEdit = 0;

        // Fetch the selected items
        DictEntry d = editDefinitionList.get(ndxSaveEdit);

        // Extract the word or phrase being defined from the item
        fld_2aWord.setText(d.getWord());

        // Extract the definition from the item
        Scanner definitionScanner = new Scanner(d.getDefinition().substring(1));
        String theDefinition = "";

        // Remove the lab from the beginning of line of the definition
        while (definitionScanner.hasNextLine()) {
            String line = definitionScanner.nextLine();
            if (line.length() > 0 && line.charAt(0) == '\t')
                theDefinition += line.substring(1);
            theDefinition += '\n';
        }

        // Save the definition
        txt_2bDefinition.setText(theDefinition);


        // Close the Scanner object
        definitionScanner.close();
    }

    private void saveTheAddDefinitionData() {
        String w = fld_2aWord.getText();
        String d = txt_2bDefinition.getText();
        d = d.trim();
        d = addSpaceToEachLine(d).trim();
        theDictionary.addEntry(w, "\n\t" + d + "\n\n");

        System.out.println("\nIndex for the save: " + ndxSaveEdit);
        System.out.println("\nThe word of phrase for the save:\n" + w);
        System.out.println("\nThe definition for the word or phrase for the save:\n" + d);

        int numEntriesWritten = 0;
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream("First20K.txt"));
            out.write("Demonstration\n");
            for (int i = 0; i < theDictionary.getNumEntries(); i++) {
                out.println(theDictionary.getDictEntry(i));
                numEntriesWritten++;
            }
            out.close();
        } catch (FileNotFoundException exception) {
            System.exit(1);
        }
        System.out.println("The system wrote out " + numEntriesWritten + " entries.");

        System.out.println("File is saved");
        File theDataFile = new File("First20K.txt");
        if (theDataFile.exists())
            System.out.println("File Exists");

        else
            System.out.println("File does not exist");
        try {
            theDataFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Hide and close the pop-up window
        editDialog.hide();
        editDialog.close();
    }

    private void saveTheEditDefinitionData() {
        // This is a stub to show that the save operation will indeed save the altered
        // text,
        // but it is a stub... it does not actually change the dictionary. This method
        // must
        // be altered to actually save the changes and re-sort the dictionary.
        String saveThisWord = fld_2aWord.getText();

        String addedSpaceText = addSpaceToEachLine(txt_2bDefinition);

        String saveThisDefinition = "\n" + addedSpaceText;
        DictEntry d = editDefinitionList.get(ndxSaveEdit);
        d.setWord(saveThisWord);
        d.setDefn(saveThisDefinition);
        performSearch();
        System.out.println("\nIndex for the save: " + ndxSaveEdit);
        System.out.println("\nThe word of phrase for the save:\n" + saveThisWord);
        System.out.println("\nThe definition for the word or phrase for the save:\n" + saveThisDefinition);

        int numEntriesWritten = 0;
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream("First20K.txt"));
            out.write("Demonstration\n");
            for (int i = 0; i < theDictionary.getNumEntries(); i++) {
                out.println(theDictionary.getDictEntry(i));
                numEntriesWritten++;
            }
            out.close();
        } catch (FileNotFoundException exception) {
            System.exit(1);
        }
        System.out.println("The system wrote out " + numEntriesWritten + " entries.");

        System.out.println("File is saved");

        File theDataFile = new File("First20K.txt");
        if (theDataFile.exists())
            System.out.println("File Exists");

        else
            System.out.println("File does not exist");
        try {
            theDataFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Hide and close the pop-up window
        editDialog.hide();
        editDialog.close();
    }

    private String addSpaceToEachLine(TextArea textArea) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String line : textArea.getText().split("\\n")) {
            arrayList.add("\t" + line);
        }
        // Creating it into one string;
        String toReturn = "";
        for (int i = 0; i <= arrayList.size() - 1; i++) {
            toReturn += arrayList.get(i) + "\n";
        }
        return toReturn;
    }

    private String addSpaceToEachLine(String text) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String line : text.split("\\n")) {
            arrayList.add("\t" + line);
        }
        // Creating it into one string;
        String toReturn = "";
        for (int i = 0; i <= arrayList.size() - 1; i++) {
            toReturn += arrayList.get(i) + "\n";
        }
        return toReturn;
    }

    public void Run() {
        String userInput = txt_2bDefinition.getText();
        String answer = NewEquationSolver.solveEquation(userInput.trim());
        txt_2bDefinition2.setText("The Result is:"+answer);
    }
    
    private void RunProgrampopup() {

        // Set up the pop-up modal dialogue window
        editDialog.setTitle("");
        Group dictionaryEditControls = new Group();

        // Fetch the search string that user had specified. We know that since the
        // number of
        // search items must be greater than zero for the code to call this method, so
        // there
        // had to be a search string, it had to be greater than zero characters, and at
        // least
        // one dictionary item matched it.
        theDictionary.setSearchString(text_SearchWord.getText());

        // Reset the comboBox list and the list of definitions back to empty
        editComboBox.getItems().clear();
        editDefinitionList.clear();

        // Fetch the matching items and place them into a list

        for (int ndx = 0; ndx < theDictionary.getNumEntries(); ndx++) {
            DictEntry d = theDictionary.findNextEntry();
            editComboBox.getItems().add(d.getWord());
            editDefinitionList.add(d);
        }

        // Set up the pop-up window
        Scene dialogScene = new Scene(dictionaryEditControls, 800, 600);

        // Set up the fields for the edit pop-up window
        setupLabelUI(lbl_1SelectDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 10);
        setupLabelUI(lbl_2EditDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 75);
        setupLabelUI(lbl_2aWord, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 100);
        setupTextUI(fld_2aWord, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 120, true);
        setupLabelUI(lbl_2bDefinition, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 155);
        setupTextAreaUI(txt_2bDefinition, "Arial", 14, 30, 180, 450, 150, true);
        setupTextAreaUI(txt_2bDefinition2, "Arial", 14, 30,400, 350, 100, true);
        setupLabelUI(lbl_3SaveDefinition, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 335);

        // Set up the comboBox to select one of the dictionary items that matched
        editComboBox.setLayoutX(25);
        editComboBox.setLayoutY(35);
        editComboBox.getSelectionModel().selectFirst();
        editComboBox.setOnAction((event) -> {
            loadTheEditDefinitionData();
        });

        // Set the screen so we can see it
        editDialog.setScene(dialogScene);

        // Load in the current word and definition data into the edit fields
        loadTheEditDefinitionData();

        setupButtonUI(btn_input, "Arial", 12, 50, Pos.BASELINE_LEFT, 30, 360);
        btn_input.setOnAction((event) -> {
            showProgramInConsole(txt_2bDefinition.getText());
        });
        
        
        setupButtonUI(btn_runEquation, "Arial", 12, 50, Pos.BASELINE_LEFT, 140, 360);
        btn_runEquation.setOnAction((event) -> {
            executeProgram(txt_2bDefinition2.getText());
        });
        
        btn_runEquation.setVisible(true);
        

        // Populate the pop-up window with the GUI elements
        dictionaryEditControls.getChildren().addAll(editComboBox, lbl_1SelectDefinition, lbl_2EditDefinition,
                lbl_2aWord, fld_2aWord, lbl_2bDefinition, txt_2bDefinition, lbl_3SaveDefinition, btn_input,txt_2bDefinition2, btn_runEquation);

        // Show the pop-up window
        editDialog.show();
    }


//    print "Enter the value of a"
//    input a=55
//    input b=7989
//    input c=8
//    input d=9
//    input e=4
//    Difference=(a+b+c)*(d+e)
//    print Difference

    private void showProgramInConsole(String program) {
        txt_2bDefinition2.clear();
        program = program.trim();
        for (String line : program.split("\\n")) {
            line = line.trim();
            if (line.startsWith("print")) {
                line = line.replace("print", "");
                line = line.replace("\"", "");
                line = line.trim();
                txt_2bDefinition2.appendText(line + "\n");
                System.out.println(line);
            } else if (line.startsWith("input")) {
                line = line.replace("input", "");
                line = line.trim();
                txt_2bDefinition2.appendText(line + "\n");
                System.out.println(line);
            } else if (line.contains("=")){
                txt_2bDefinition2.appendText(line + "\n");
            }
        }
    }

//    Enter the value of a
//    a=55
//    b=7989
//    c=8
//    d=9
//    e=4
//    Difference
    private void executeProgram(String program) {
        String operation = "";
        String equation = "";
        Map<String, String> map = new HashMap<>();
        program = program.trim();

        try {
            for (String line : program.split("\\n")) {
                if (line.contains("=")) {
                    String[] data = line.split("=");
                    map.put(data[0], data[1]);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading program!");
        }

        if (map.containsKey("Difference")) {
            operation = "-";
            equation = map.get("Difference");
        } else if (map.containsKey("Sum")) {
            operation = "+";
            equation = map.get("Sum");
        }

        String newEquation = "";
        for (int i = 0; i <= equation.length()-1; i++) {
            char x = equation.charAt(i);
            if (Character.isAlphabetic(x)) {
                newEquation += map.get("" + x);
            } else {
                newEquation += x;
            }
        }

        newEquation = newEquation.replace("*", operation);
        txt_2bDefinition2.setText(newEquation + "\n");

        String answer = NewEquationSolver.solveEquation(newEquation);
        txt_2bDefinition2.appendText("Answer : " + answer);
    }

}