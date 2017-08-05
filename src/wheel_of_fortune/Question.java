/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheel_of_fortune;

/**
 *
 * @author Karol
 */
public class Question {
  
  private String text;
  private String imgPath;
  
  public Question(String text, String imgPath) {
    this.text = text;
    this.imgPath = imgPath;
  }

  public String getText() {
    return text;
  }

  public String getImgPath() {
    return imgPath;
  }
  
  
}
