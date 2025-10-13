package application;
	
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			URL fxmlLocation = getClass().getResource("CypherScene.fxml");
			loader.setLocation(fxmlLocation);
			CypherSceneController csc = new CypherSceneController();
			loader.setController(csc);
			VBox root = loader.load();
			
			Scene scene = new Scene(root,1280,800);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
