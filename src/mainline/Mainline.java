package mainline;


import mainline.DictionaryUserInterface;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/*******
 * <p>
 * Title: Mainline to run the PKMT-II project.
 * </p>
 * 
 * <p>
 * Description: A JavaFX demonstration application: This controller class is the
 * entry point for this JavaFX application.
 * </p>
 * 
 * <p>
 * Copyright: Lynn Robert Carter ©  2018-08-04
 * </p>
 * 
 * @author Neaz Morshed
 * 
 * @version 1.00 2018-07-18 Baseline
 * @version 2.00 2019-11-12 This class gets update by the implementation of the
 *          all the three tabs performing different actions.
 * @version 4.20 
 * 29-04-2020 - The Calculator Has been upgraded to store data and manipulate data
 * 
 */

public class Mainline extends Application {

	public DictionaryUserInterface DictionaryGUI;

	public static double WINDOW_WIDTH;
	public static double WINDOW_HEIGHT;

	/**********
	 * This method is the root of the application from it, the foundations of the
	 * application are establish, the GUI is linked to the methods of various
	 * classes and the setup is performed.
	 * 
	 * This method queries the environment to determine the size of the window that
	 * is at the heart of the Graphical User Interface (GUI). The method is called
	 * with a single parameter that specified the Stage object that JavaFX
	 * applications use.
	 * 
	 * The method starts by creating a Pane object, calls the GUI to instantiate the
	 * GUI widgets using that Pane, creates a Scene using that Pane as a window of a
	 * size that will fit the specifics of the system running the application. Once
	 * the Scene is set, it is shown to the user, and at that moment the application
	 * changes from a programmed sequence of actions set by the programmer into a
	 * set of actions determined by the user by means of the various GUI elements
	 * the user selects and uses.
	 * 
	 * @param theStage is a Stage object that is passed in to the methods and is
	 *                 used to set up the the controlling object for the
	 *                 application's user interface
	 */
	public void start(Stage theStage) throws Exception {

		// Determine the actual visual bounds for this display
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		// set Stage boundaries to the visual bounds so the window does not totally fill
		// the screen
		WINDOW_WIDTH = primaryScreenBounds.getWidth() - primaryScreenBounds.getMinX() - 100;
		if (WINDOW_WIDTH > 1000)
			WINDOW_WIDTH = 1000;
		WINDOW_HEIGHT = primaryScreenBounds.getHeight() - primaryScreenBounds.getMinY() - 100;
		if (WINDOW_HEIGHT > 160)
			WINDOW_HEIGHT = 160;

		theStage.setTitle("Programmable Calculator@ Neaz Morshed"); // Label the stage's window
		Pane root = new Pane(); // Create a pane within the window
		Pane AddresstheRoot = new Pane(); // Create a pane within the Window for addressesUI
		Pane DictionarytheRoot = new Pane(); // Create a pane within the window for DictionaryUI
		Pane NtsAndTdltheRoot = new Pane();
		TabPane tabPane = new TabPane(); // Create an empty tab pane with the default placement

		// creating the pane for the border
		BorderPane borderPane = new BorderPane();

		// Create User Interface to access Addresses elements
//		AddressesGUI = new Contact(AddresstheRoot);

		// Call the method using the class object
//		AddressesGUI.appendAddress();

		// Create the User Interface to access the Dictionary elements
		DictionaryGUI= new DictionaryUserInterface(DictionarytheRoot);

//		NotesAndToDoListGUI = new NotesAndToDoList(NtsAndTdltheRoot);
		

	

		// Creating the tab for dictionary
		Tab dictionary = new Tab();

		// Creating the tab for the Notes and To-Do list
	

		// Name the contact tab as 'Contacts'
	

		// Set the content and access the elements of Addresses UI
		

		// Name the dictionary tab as 'Dictionary'
		dictionary.setText("Caclulator");

		// Set the content and access the elements of Dictionary
		dictionary.setContent(DictionarytheRoot);

		// Set the text and leave blank
		
		

		tabPane.getTabs().addAll(dictionary); // Add all the tabs in the tabPane

		borderPane.setCenter(tabPane); // Set the tabPane into the border of the window

		root.getChildren().add(borderPane);

		// Create the scene using the GUI window and the size that
		// was computed earlier along with the addition of its property that set the
		// height and width of the border pane

		Scene theScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		borderPane.prefHeightProperty().bind(theScene.heightProperty());
		borderPane.prefWidthProperty().bind(theScene.widthProperty());

		theStage.setScene(theScene); // Set the scene on the stage and
		theStage.show(); // show the stage to the user

		// When the stage is shown to the user, the pane within the window is now
		// visible. This means
		// that the labels, fields, and buttons of the Graphical User Interface (GUI)
		// are visible and
		// it is now possible for the user to select input fields and enter values into
		// them, click on
		// buttons, and read the labels, the results, and the error messages.
	}

	/*******************************************************************************************************
	 * This is the method that launches the JavaFX application
	 * 
	 * @param args are the program parameters and they are not used by this program.
	 * 
	 */
	public static void main(String[] args) { // This method may not be required
		launch(args); // for all JavaFX applications using
	} // other IDEs.
}