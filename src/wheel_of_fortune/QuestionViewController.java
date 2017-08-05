package wheel_of_fortune;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Karol
 */
public class QuestionViewController implements Initializable {
  
  private Stage stage;
  private Question question;
  @FXML
  private void handleOnKeyPressed(KeyEvent event) {
    if (event.getCode().equals(KeyCode.SPACE)) {
      stage.close();
    }
  }
  
  public void setQuestion(Question question) {
    this.question = question;
  }
  
  public void setStage(Stage stage) {
    this.stage = stage;
  }

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  
  
}
