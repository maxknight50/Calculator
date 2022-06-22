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
import java.util.Stack;

public class Calculator extends Application {

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
    TextField calc = new TextField();

    TextField temp = new TextField();
    Button save = new Button("Save");
    Button load = new Button("Load");

    String fSize = "-fx-font: 24 garamond;";

//   Font font1 = new Font("Garamond", Font.BOLD,20); 
//create gridpanes
    GridPane overallPane = new GridPane();
    GridPane buttonPane = new GridPane();
    GridPane calculation = new GridPane();
    GridPane tickerPane = new GridPane();

    Stack opStack = new Stack();
    Stack<Double> numStack = new Stack<>();

    @Override
    public void start(Stage primaryStage) throws IOException {
        calc.setEditable(false);
        calc.setDisable(false);

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
        buttonPane.add(plusSign, 4, 0);
        buttonPane.add(minusSign, 4, 1);
        buttonPane.add(division, 4, 2);
        buttonPane.add(multi, 4, 3);
        buttonPane.add(equals, 2, 3);

        calculation.add(calc, 0, 0);

        tickerPane.add(save, 0, 1);
        tickerPane.add(load, 1, 1);

        overallPane.add(calculation, 0, 0);
        overallPane.add(tickerPane, 1, 0);
        overallPane.add(buttonPane, 0, 1);

        primaryStage = new Stage();
        Scene primaryScene = new Scene(overallPane, 550, 400);
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("DukeCalc v 0.1");
        primaryStage.show();

        // Calls the addToScreen method in order to display the numbers and operators
        number1.setOnAction(e -> addToScreen(number1.getText()));
        number2.setOnAction(e -> addToScreen(number2.getText()));
        number3.setOnAction(e -> addToScreen(number3.getText()));
        number4.setOnAction(e -> addToScreen(number4.getText()));
        number5.setOnAction(e -> addToScreen(number5.getText()));
        number6.setOnAction(e -> addToScreen(number6.getText()));
        number7.setOnAction(e -> addToScreen(number7.getText()));
        number8.setOnAction(e -> addToScreen(number8.getText()));
        number9.setOnAction(e -> addToScreen(number9.getText()));
        number0.setOnAction(e -> addToScreen(number0.getText()));

        plusSign.setOnAction(e -> addToScreen(" " + plusSign.getText() + " "));
        minusSign.setOnAction(e -> addToScreen(" " + minusSign.getText() + " "));
        division.setOnAction(e -> addToScreen(" " + division.getText() + " "));
        multi.setOnAction(e -> addToScreen(" " + multi.getText() + " "));

        equals.setOnAction(e -> calculate(calc.getText()));
    }

    public void addToScreen(String toDisplay) {
        calc.appendText(toDisplay);
    }

    public void calculate(String calculation) {
        int result = 0;
        try {
            String[] array = calculation.split(" "); // Split by space
            for (String a : array) {
                if (a.equals("+") || a.equals("-") || a.equals("/") || a.equals("*")) {
                    opStack.push(a); // Push onto operator stack
                } else {
                    numStack.push(Double.parseDouble(a)); // Push onto number stack
                }
            }
            // 3 + 5 / 1 * 4
            // Op stack: * / + 
            // Num stack: 3 (5 1) 4
            // / at 1
            // 5 at 1, 1 at 2
            // 5 removed, 1 at 1

            double num = 0;
            double firstNum = 0;
            double secondNum = 0;
            while (numStack.size() >= 2) {
                for (int i = 0; i < opStack.size(); i++) { // Multiplication/Division specific loop
                    if (opStack.get(i).equals("*") || opStack.get(i).equals("/")) {
                        firstNum = numStack.elementAt(i); // Get the number located before the operator
                        secondNum = numStack.elementAt(i + 1); // Get the number located after the operator
                        numStack.removeElementAt(i); // Remove first number
                        numStack.removeElementAt(i); // Remove second number, which has switched to the first number's index

                        if (opStack.get(i).equals("/")) {
                            num = firstNum / secondNum; // Perform calculation
                            System.out.println(firstNum + " / " + secondNum + " = " + num);
                            numStack.add(i, num); // Add the calculated number back to the stack
                        } else if (opStack.get(i).equals("*")) {
                            num = firstNum * secondNum;
                            System.out.println(firstNum + " * " + secondNum + " = " + num);
                            numStack.add(i, num);
                        }
                        opStack.remove(i); // Once completed, remove the operator from the stack
                        i -= 1;

                    }
                }
                for (int i = 0; i < opStack.size(); i++) { // Addition/Subtraction specific loop
                    if (opStack.get(i).equals("+") || opStack.get(i).equals("-")) { 
                        firstNum = numStack.elementAt(i); // Get the number located before the operator
                        secondNum = numStack.elementAt(i + 1); // Get the number located after the operator
                        numStack.removeElementAt(i); // Remove first number
                        numStack.removeElementAt(i); // Remove second number, which has switched to the first number's index

                        if (opStack.get(i).equals("+")) {
                            num = firstNum + secondNum;
                            System.out.println(firstNum + " + " + secondNum + " = " + num);
                            numStack.add(i, num);
                        } else if (opStack.get(i).equals("-")) {
                            num = firstNum - secondNum;
                            System.out.println(firstNum + " - " + secondNum + " = " + num);
                            numStack.add(i, num);
                        }
                        opStack.remove(i);
                        i -= 1;
                    }
                }

                System.out.println("Final result: " + numStack.get(0));
                numStack.clear();
                opStack.clear();
                calc.clear();

//                int topNum = numStack.pop();
//                int botNum = numStack.pop();
//                String operator = opStack.pop().toString();
//                System.out.println(topNum + " " + operator + " " + botNum);
//                switch (operator) {
//                    case "+":
//                        result = topNum + botNum;
//                        break;
//                    case "-":
//                        result = topNum - botNum;
//                        break;
//                    case "*":
//                        result = topNum * botNum;
//                        break;
//                    case "/":
//                        result = topNum / botNum;
//                        break;
//                }
//                numStack.push(result);
            }

            // Second stack object for symbols
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
