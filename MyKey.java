/**
 *
 * @author Deep Parikh, Mehak Bhatia, Hritik Puri, Aditi Dwivedi
 */

package mykey;

import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;



public class MyKey extends Application {

	final private char[] qwerty = {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 
		'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};
	final private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	final private int[] layout = {13, 16, 12, 17, 11, 18, 10, 14, 15, 2, 7, 3, 
		6, 4, 22, 24, 25, 1, 8, 5, 0, 9, 21, 23, 20, 19};
	private static char[] newLayout = new char[26];
	private static int[] total = new int[26];
	private static boolean curr = true;
	
	
	private void getalpha () {
		int[] sorted = new int[26], copy = new int[26];
		int max, temp;
		
		for (int i = 0; i < 26; i++) {
			sorted[i] = i;
			copy[i] = total[i];
		}
		
		for (int i = 0; i < 26; i++) {
			max = i;
			for (int j = i + 1; j < 26; j++) {
				if (copy[j] > copy [max])
					max = j;
			}
			temp = copy[i];
			copy[i] = copy [max];
			copy [max] = temp;
			temp = sorted[i];
			sorted[i] = sorted [max];
			sorted [max] = temp;
			
			newLayout [layout[i]] = alphabet.charAt (sorted[i]);
		}
	}
	
	
	
    public static void main (String[] args) {
        launch (args);
    }
	
	
	
	@Override
	public void init() {
		newLayout = Arrays.copyOf(qwerty, 26);
		try {
			Scanner sc = new Scanner (new File ("total.txt"));
		
			for (int i = 0; i < 26; i++)
				total[i] = sc.nextInt();
			
			getalpha();
		}
		catch (Exception e) {
			System.out.println(e);
		}

	}
    
	
	
    @Override
	public void start (Stage primaryStage) {
		
		primaryStage.setTitle ("MyKey Simulator");
		
		Button primaryPrediction = new Button();
		Button addToDictionary = new Button();
		Button secondaryPrediction = new Button();
		
		primaryPrediction.setPrefSize (105, 25);
		addToDictionary.setPrefSize (105, 25);
		secondaryPrediction.setPrefSize (105, 25);
		
		HBox predictionBox = new HBox();
		predictionBox.setSpacing(5);
		predictionBox.getChildren().
				addAll (addToDictionary, primaryPrediction, secondaryPrediction);
		
		TextField input = new TextField();
		input.setPrefSize (310, 25);
		
		input.setOnMouseClicked (e -> {
			input.setEditable(false);
		});
		
		Button key[] = new Button[26];
		
		HBox row1 = new HBox();
		row1.setSpacing(5);
		for (int i = 0; i < 10; i++) {
			key[i] = new Button (String.valueOf (newLayout[i]));
			key[i].setPrefSize (25, 25);
			key[i].setUserData (String.valueOf (newLayout[i]));
			row1.getChildren().add(key[i]);
		}
		
		HBox row2 = new HBox();
		row2.setPadding(new Insets (0, 0, 0, 10));
		row2.setSpacing(5);
		for (int i = 10; i < 19; i++) {
			key[i] = new Button (String.valueOf (newLayout[i]));
			key[i].setPrefSize (25, 25);
			key[i].setUserData (String.valueOf (newLayout[i]));
			row2.getChildren().add(key[i]);
		}
		
		HBox row3Keys = new HBox();
		row3Keys.setSpacing(5);
		row3Keys.setPadding(new Insets (0, 0, 0, 30));
		for (int i = 19; i < 26; i++) {
			key[i] = new Button (String.valueOf (newLayout[i]));
			key[i].setPrefSize (25, 25);
			key[i].setUserData (String.valueOf (newLayout[i]));
			row3Keys.getChildren().add (key[i]);
		}
		
		for (Button b: key)
		{
			b.setOnMouseClicked ((MouseEvent click) -> {
				input.appendText (((String) ((Node) click.getSource()).getUserData()));
				total [alphabet.indexOf (((String) b.getUserData()).charAt(0))]++;
			});
		}
		
/*		Image icon = new Image(getClass().getResourceAsStream("ok.png"));
		ImageView upd =  new ImageView (icon);
		upd.setPreserveRatio(true);
		upd.setFitHeight(15);
		
		Button update = new Button ();
		update.setGraphic (upd);
		update.setPrefSize (25, 25);
		update.setUserData ((boolean) false);
		
		HBox row3 = new HBox();
		row3.setSpacing (15);
		row3.getChildren().addAll (row3Keys, update);
		
		update.setOnMouseClicked (new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {
				int[] sorted = new int[26], copy = new int[26];
				int max, temp;
				for (int i = 0; i < 26; i++) {
					sorted[i] = i;
					copy[i] = total[i];
				}	for (int i = 0; i < 26; i++) {
					max = i;
					for (int j = i + 1; j < 26; j++) {
						if (copy[j] > copy [max])
							max = j;
					}
					
					temp = copy[i];
					copy[i] = copy [max];
					copy [max] = temp;
					temp = sorted[i];
					sorted[i] = sorted [max];
					sorted [max] = temp;
					
					newLayout [layout[i]] = alphabet.charAt (sorted[i]);
				}
			}
		});*/
		
		VBox keyboard = new VBox();
		keyboard.setSpacing(5);
		keyboard.getChildren().addAll (row1, row2, row3Keys);
		
		BorderPane componentLayout = new BorderPane();
		componentLayout.setPadding (new Insets (10, 10, 10, 10));
		componentLayout.setTop (predictionBox);
		componentLayout.setCenter (input);
		componentLayout.setBottom (keyboard);
		
		Scene scene = new Scene (componentLayout, 343, 180);
		
		EventHandler press = (EventHandler<KeyEvent>) (KeyEvent press1) -> {
			if (press1.getCode().isLetterKey()) {
				char c = newLayout [new String (qwerty).indexOf ((press1.getText()).toUpperCase())];
				input.appendText (String.valueOf(c));
				total [c - 'A']++;
			}
		};
		
		scene.setOnKeyPressed (press);
		
		primaryStage.setScene (scene);
		primaryStage.setResizable (false);
		primaryStage.show();
	}
	
	
	
	@Override
	public void stop() {
		try {
			FileWriter fw = new FileWriter ("total.txt");
			
			fw.write (total[0] + "");
			for (int i = 1; i < 26; i++)
				fw.write("\n" + total[i]);
			
			fw.close();
		}
		
		catch (Exception e) {
			
		}
	}
}
