/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.awt.font.*;


public class Calculator extends Application{

    // create buttons
    Button number1 = new Button("1");
    Button number2 = new Button("2");
    Button number3 = new Button("3");
    Button number4 = new Button("4");
    Button number5 = new Button("5");
    Button number6 = new Button("6");
    Button number7 = new Button("7");
    Button number8 = new Button("8");
    Button number9 = new Button("9");
    Button number0 = new Button("0");
    Button plusSign = new Button("+");
    Button minusSign = new Button("-");
    Button division = new Button("/");
    Button multi = new Button("*");
    Button equals = new Button("=");
    Button save = new Button("Save");
    Button load = new Button("Load");
    
 
   String fSize = "-fx-font: 24 garamond;";
   
//   Font font1 = new Font("Garamond", Font.BOLD,20); 

//create gridpanes
    
    GridPane overallPane = new GridPane();
    GridPane buttonPane = new GridPane();
    GridPane newPane = new GridPane();
    
  @Override
  public void start(Stage primaryStage) throws IOException{
      
      buttonPane.setHgap(0);
      buttonPane.setVgap(0);
      buttonPane.setPadding(new Insets(10, 10, 10, 10));
   

      number1.setPrefHeight(50.0);
      number1.setPrefWidth(50.0);
      number1.setStyle(fSize);
      number2.setPrefHeight(50.0);
      number2.setPrefWidth(50.0);
      number2.setStyle(fSize);
      number3.setPrefHeight(50.0);
      number3.setPrefWidth(50.0);
      number3.setStyle(fSize);
      number4.setPrefHeight(50.0);
      number4.setPrefWidth(50.0);
      number4.setStyle(fSize);
      number5.setPrefHeight(50.0);
      number5.setPrefWidth(50.0);
      number5.setStyle(fSize);
      number6.setPrefHeight(50.0);
      number6.setPrefWidth(50.0);
      number6.setStyle(fSize);
      number7.setPrefHeight(50.0);
      number7.setPrefWidth(50.0);
      number7.setStyle(fSize);
      number8.setPrefHeight(50.0);
      number8.setPrefWidth(50.0);
      number8.setStyle(fSize);
      number9.setPrefHeight(50.0);
      number9.setPrefWidth(50.0);
      number9.setStyle(fSize);
      number0.setPrefHeight(50.0);
      number0.setPrefWidth(50.0);
      number0.setStyle(fSize);
      plusSign.setPrefHeight(50.0);
      plusSign.setPrefWidth(50.0);
      plusSign.setStyle(fSize);
      minusSign.setPrefHeight(50.0);
      minusSign.setPrefWidth(50.0);
      minusSign.setStyle(fSize);
      division.setPrefHeight(50.0);
      division.setPrefWidth(50.0);
      division.setStyle(fSize);
      multi.setPrefHeight(50.0);
      multi.setPrefWidth(50.0);
      multi.setStyle(fSize);
      equals.setPrefHeight(50.0);
      equals.setPrefWidth(50.0);
      equals.setStyle(fSize);
      
     
     
     
     
     
     
      buttonPane.add(number1, 0, 0);
      buttonPane.add(number2, 1, 0);
      buttonPane.add(number3, 2, 0);
      buttonPane.add(number4, 0, 1);
      buttonPane.add(number5, 1, 1);
      buttonPane.add(number6, 2, 1);
      buttonPane.add(number7, 0, 2);
      buttonPane.add(number8, 1, 2);
      buttonPane.add(number9, 2, 2);
      buttonPane.add(number0, 1, 3);
      buttonPane.add(plusSign, 4,0);
      buttonPane.add(minusSign, 4, 1);
      buttonPane.add(division, 4, 2);
      buttonPane.add(multi, 4, 3);
      buttonPane.add(equals, 2, 3);
//      buttonPane.add(save, 5,3);
//      buttonPane.add(load, 6, 3);
      overallPane.add(buttonPane, 0, 0);
      
      
      
      primaryStage = new Stage();
        Scene primaryScene = new Scene(overallPane, 600, 550);
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("DukeCalc v 0.1");
        primaryStage.show();
  }
    
    
    
    
    
    public static void main(String[] args) {
        launch (args);
    }
 
}
