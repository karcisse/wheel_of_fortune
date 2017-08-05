/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheel_of_fortune;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author Karol
 */
public class Wheel_of_fortune extends Application {
  
  private EventHandler<KeyEvent> keyListener;    
  public Scene scene;
  
  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
   
    Parent root;
    
    root = loader.load();
    
    String image = getClass().getResource("/bckg.jpg").toString();
    
    scene = new Scene(root);
    
    scene.getRoot().requestFocus();
    scene.getRoot().setStyle("-fx-background-image: url('" + image + "'); " +
           "-fx-background-position: center center; " +
           "-fx-background-repeat: stretch;");
    
    
    stage.setMaximized(true);
    stage.setFullScreen(true);
    stage.setTitle("Wheel of fortune, watch out!");
    stage.setScene(scene);
    ((FXMLDocumentController) loader.getController()).setScene(scene);
    stage.show();
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
  
}
