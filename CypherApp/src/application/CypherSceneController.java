package application;

import java.io.IOException;

import java.util.HashMap;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.lang.Iterable;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CypherSceneController {
	// Importing scene builder components
	@FXML private ToggleButton ccToggle;
	@FXML private Button encryptButton;
	@FXML private Button decryptButton;
	@FXML private Button HowToButton;
	@FXML private TextArea plaintextBox;
	@FXML private TextArea cyphertextBox;
	@FXML private TextField cypherkeyBox;
	
	private Boolean ccFlag;
	private Boolean encryptModeFlag;
	private Boolean decryptModeFlag;
	private String encryptionKeyword;

	
	public CypherSceneController() {}
	
	@FXML
	public void initialize () {
		encryptModeFlag = true;
		decryptModeFlag = false;
		ccFlag = true;
		//setHashMaps();
	}
	
	@FXML
	public void ccToggle() {
		System.out.println("Caesar Cypher pressed");
		ccFlag = true;
		
	}
	
	@FXML
	public void encryptPressed () {
		System.out.println("Encrypt Start");
		//testingInTerminal();
		if (ccFlag) {
			cyphertextBox.setText(caesarCypher());
			
		}
		
	}
	
		
	@FXML
	public void decryptPressed () {
		System.out.println("Decrypt Start");
		//testingInTerminal();
		if (ccFlag) {
			plaintextBox.setText(decryptCaesarCipher());
		}
	}
	
	@FXML
	public void HowToButtonPressed () {
		System.out.println("How to button pressed");
		String content = "First, Click the cipher that you want to apply to your message\n"
				+ "To encrypt your messsage, enter plain text into plain text box and add a number to the cipher key. Then press encrypt\n"
				+ "The output will be placed into the cipher box at the bottom\n "
				+ "\n"
				+ "To decrypt your message, enter the cipher text into the cipher text box at the bottom and the correct number to the cipher key. Then press decrypt\n"
				+ "The output will be placed in the plain text box at the top";
		Alert alert = new Alert(AlertType.NONE);
		alert.setTitle("How to Scene");
		alert.setHeaderText("How to Use Application");
		alert.setContentText(content);
		alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		alert.showAndWait();
	}
	

	// Parsing text from text boxes
	private String parseTextArea(TextArea textBox) {
		return textBox.getText();
	}
	
	// Parsing text from text fields 	
	private String parseTextField (TextField textField) {
		return textField.getText();
	}	
	
	// Main caesar cipher method 
	private String caesarCypher () {
		String cypher = "";
		ArrayList<Integer> cipherIntStream = new ArrayList<>();
		
		// Parsing plain text from text box
		String plainText = parseTextArea(plaintextBox);
		// Extracting text from input text box
		String cipherKey = parseTextField(cypherkeyBox);
		
		
		// Check for valid text and key entered
		if (checkKey(cipherKey)) {

			// Sanitizing number key and converting key to int
			int cipherInt = Integer.parseInt(cipherKey);
			if (cipherInt >= 24) {
				cipherInt = cipherInt % 24;
			}
			// Converting plain text to num for cipher change
			
			for (int i=0; i < plainText.length(); i++) {
				char currentChar = plainText.charAt(i);
				int currentInt = (int) currentChar;
				cipherIntStream.add(i, currentInt);			
			}		
			
			// Applying cipher to Intstream and converting int to char
			for (int i=0; i < cipherIntStream.size(); i++) {
				int currentInt = cipherIntStream.get(i);
				if ( (currentInt >= 97) && (currentInt <= 122) ) {
					int tempInt = currentInt + cipherInt;
					if (tempInt - 122 > 0) {
						currentInt = (tempInt - 122) + 96;
					} else {
						currentInt = tempInt;
					}
				} else if ((currentInt >= 65) && (currentInt <= 90)) {
					int tempInt = currentInt + cipherInt;
					if (tempInt - 90 > 0) {
						currentInt = (tempInt - 90) + 64;
					} else {
						currentInt = tempInt;
					}
				}
				
				String currentChar = Character.toString((char) currentInt);
				cypher += currentChar;
				
			}
			
		} else {
			cypher = "Please enter a valid NUMBER key or check your message doesn't contain any special characters";
		}
					
		return cypher;
	}
	
	private String decryptCaesarCipher () {
		String plainText = "";
		ArrayList<Integer> cipherIntStream = new ArrayList<>();
		
		// Parsing plain text from text box
		String cipherText = parseTextArea(cyphertextBox);
		
		// Extracting text from input text box
		String cipherKey = parseTextField(cypherkeyBox);
		
		
		// Check for valid text and key entered
		if (checkKey(cipherKey)) {
			
			// Sanitizing number key and converting key to int
			int cipherInt = Integer.parseInt(cipherKey);
			if (cipherInt >= 24) {
				cipherInt = cipherInt % 24;
			}
			
			// Converting plain text to num for cipher change
			
			for (int i=0; i < cipherText.length(); i++) {
				char currentChar = cipherText.charAt(i);
				int currentInt = (int) currentChar;
				cipherIntStream.add(i, currentInt);	
			}

			// Undoing cipher with key
			for (int i=0; i < cipherIntStream.size(); i++) {
				int currentInt = cipherIntStream.get(i);
				if ( (currentInt >= 97) && (currentInt <= 122) ) {
					int tempInt = currentInt - cipherInt;
					if (tempInt - 96 < 0) {
						currentInt = (tempInt - 96) + 122;
					} else {
						currentInt = tempInt;
					}
				} else if ((currentInt >= 65) && (currentInt <= 90)) {
					int tempInt = currentInt - cipherInt;
					if (tempInt - 63 < 0) {
						currentInt = (tempInt - 63) + 90;
					} else {
						currentInt = tempInt;
					}
				}
				
				String currentChar = Character.toString((char) currentInt);
				plainText += currentChar;
				
			}
			
		} else {
			plainText = "Please enter a valid NUMBER key or check your message doesn't contain any special characters";
		}
		return plainText;
		
	}
	
	
	private void testingInTerminal () {
		char x = 'A';
		int ascii = (int) x;
		System.out.println(ascii);
		System.out.println(plaintextBox.getText());
		System.out.println(plaintextBox.getParagraphs());
		System.out.println((char) ascii);
		
	}
	
	private boolean checkKey(String textField) {
		// Defining variables
		boolean validCipher;
	
		// Checking and sanitizing cipher key
		try {
			int tempNum = Integer.parseInt(textField);
			if (tempNum >= 0) {
				validCipher = true;
			} else {
				validCipher = false;
			}
			
		} catch (Exception e) {
			validCipher = false;
		}
		
		
		return validCipher;
	}
	

	
	
	
	
	
}

/*
private String caesarCypher () {
	String cypher = "";
	ArrayList<Integer> cipherIntStream = new ArrayList<>();
	
	// Parsing plain text from text box
	String plainText = parseTextArea(plaintextBox);
	// Extracting text from input text box
	String cipherKey = parseTextField(cypherkeyBox);
	
	
	// Check for valid text and key entered
	if (checkKey(cipherKey)) {

		// Sanitizing number key and converting key to int
		int cipherInt = Integer.parseInt(cipherKey);
		if (cipherInt >= 24) {
			cipherInt = cipherInt % 24;
		}
		// Converting plain text to num for cipher change
		
		for (int i=0; i < plainText.length(); i++) {
			char currentChar = plainText.charAt(i);
			int currentInt = (int) currentChar;
			if (((65 <= currentInt) && (currentInt <= 90)) || ((97 <= currentInt) && (currentInt <= 122))) {
				cipherIntStream.add(i, currentInt - 64);
			} else { 
				cipherIntStream.add(i, currentInt);
			}
			
		}		
		
		// Applying cipher to Intstream and converting int to char
		for (int i=0; i < cipherIntStream.size(); i++) {
			int currentInt = cipherIntStream.get(i); 
			if ( (currentInt >= 33) && (currentInt <= 58) ) {
				int tempInt = currentInt + cipherInt + 64;
				if (tempInt - 122 > 0) {
					currentInt = (tempInt - 122) + 96;
				} else {
					currentInt = tempInt;
				}
			} else if ((currentInt >= 0) && (currentInt <= 26)) {
				int tempInt = currentInt + cipherInt + 64;
				if (tempInt - 90 > 0) {
					currentInt = (tempInt - 90) + 63;
				} else {
					currentInt = tempInt;
				}
			}
			
			String currentChar = Character.toString((char) currentInt);
			cypher += currentChar;
			
		}
		
	} else {
		cypher = "Please enter a valid NUMBER key or check your message doesn't contain any special characters";
	}
	
	
			
	return cypher;
}

*/
