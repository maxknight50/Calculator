package calculator;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Stack;


/**
 * Maxine Knight, Zachary Taylor 
 * Homework 4, CIS 484 
 * Additional functions: Order of operations, delete (one character at a time) and clear, decimal
 */
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
    Button delete = new Button("<-- Del");
    Button clear = new Button("Clear");
    Button decimal = new Button(".");
    TextField calc = new TextField(); // Stores calculation

    Label message = new Label("Welcome!");
    Button save = new Button("Save");
    Button load = new Button("Load");

    String fSize = "-fx-font: 24 garamond;";

    //create gridpanes
    GridPane overallPane = new GridPane();
    GridPane buttonPane = new GridPane();
    GridPane tickerPane = new GridPane();
    GridPane delClear = new GridPane();

    // Create stacks and ListView
    Stack opStack = new Stack();
    Stack<Double> numStack = new Stack<>();
    ListView<String> calculationList = new ListView<>();

    // Create file variables
    private FileInputStream readFile;
    private ObjectInputStream readCalculatorData;

    /****************************
     * Start method
     * *************************/
    @Override
    public void start(Stage primaryStage) throws IOException {

        // File identification
        try {
            String filePath1 = "calculatorData.dat";
            File calcFile = new File(filePath1);

            if (doesFileExist(calcFile) == false) { // If file does not exits, create
                System.out.println("File 1 created: " + calcFile.createNewFile());
            } else {
                System.out.println("File 1 exists"); // If file exists, create ObjectInputStream
                this.readFile = new FileInputStream("calculatorData.dat");
                this.readCalculatorData = new ObjectInputStream(readFile);
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        // Set TextField for calculation to not editable
        calc.setEditable(false);
        calc.setDisable(false);
        
        // Call method to increase size of all buttons
        setButtonSize();
        
        // Call method to set the Buttons to the GridPanes
        setButtonsAndPane();

        primaryStage = new Stage();
        Scene primaryScene = new Scene(overallPane, 580, 450);
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("DukeCalc v 0.1");
        primaryStage.show();

        // Calls the addToScreen method in order to display the numbers and operators when selected
        number1.setOnAction(e -> addNumToScreen(number1.getText()));
        number2.setOnAction(e -> addNumToScreen(number2.getText()));
        number3.setOnAction(e -> addNumToScreen(number3.getText()));
        number4.setOnAction(e -> addNumToScreen(number4.getText()));
        number5.setOnAction(e -> addNumToScreen(number5.getText()));
        number6.setOnAction(e -> addNumToScreen(number6.getText()));
        number7.setOnAction(e -> addNumToScreen(number7.getText()));
        number8.setOnAction(e -> addNumToScreen(number8.getText()));
        number9.setOnAction(e -> addNumToScreen(number9.getText()));
        number0.setOnAction(e -> addNumToScreen(number0.getText()));
        decimal.setOnAction(e -> addNumToScreen(decimal.getText()));

        // Calls the addOpToScreen method for operators
        plusSign.setOnAction(e -> addOpToScreen(plusSign.getText()));
        minusSign.setOnAction(e -> addOpToScreen(minusSign.getText()));
        division.setOnAction(e -> addOpToScreen(division.getText()));
        multi.setOnAction(e -> addOpToScreen(multi.getText()));

        // Equals calls the calculate method
        equals.setOnAction(e -> calculate(calc.getText()));

        // Clear button clears the calculation TextField
        clear.setOnAction(e -> calc.clear());

        // Delete button deletes one character at a time
        delete.setOnAction(e -> {
            int size = calc.getText().length();
            if (size != 0) { // Make sure TextField isn't empty
                calc.deleteText(size - 1, size);
            }
        });

        /*************************************************
         * Save button --> Save data from ListView to file
         * **********************************************/
        save.setOnAction(e -> { // On save select, save listView to file
            try {
                FileOutputStream output = new FileOutputStream("calculatorData.dat");
                ObjectOutputStream objectOutput = new ObjectOutputStream(output); // Create OutputStream for passed in file

                for (int i = 0; i < calculationList.getItems().size(); i++) { // Loop through all objects in the list
                    objectOutput.writeObject(calculationList.getItems().get(i)); // Write the object to the file
                }
                objectOutput.close();

            } catch (IOException ex) {
                System.out.println(ex);
            }
            message.setText("Save successful.");
        });

        /**
         * **********************************
         * Load button --> Load data from file
         * ***********************************
         */
        load.setOnAction(e -> {
            try { // For to-do listView
                while (true) {
                    String calcData = (String) readCalculatorData.readObject(); // Read the object from the file
                    calculationList.getItems().add(calcData); // Add it to the listView
                }
            } catch (EOFException ee) {

            } catch (IOException | ClassNotFoundException ex) {
                System.out.println(ex);
            }
            message.setText("Load successful.");
        });
    }

    public void addNumToScreen(String toDisplay) { // Adds the number to the text field
        calc.appendText(toDisplay);
    }

    public void addOpToScreen(String toDisplay) { // Add the operator to the text field with spaces
        calc.appendText(" " + toDisplay + " ");
    }

    /**
     * ******************************************************
     * Method called when equals operator is selected
     * ******************************************************
     */
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
                            numStack.add(i, num); // Add the calculated number back to the stack
                        } else if (opStack.get(i).equals("*")) {
                            num = firstNum * secondNum;
                            numStack.add(i, num);
                        }
                        opStack.remove(i); // Once completed, remove the operator from the stack
                        i -= 1; // Since an item was removed from the stack, decrease i
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
                            numStack.add(i, num);
                        } else if (opStack.get(i).equals("-")) {
                            num = firstNum - secondNum;
                            numStack.add(i, num);
                        }
                        opStack.remove(i);
                        i -= 1;
                    }
                }
                message.setText(""); // Set message back to blank
                if (numStack.get(0) % 1 != 0) { // Remainder would not be zero, so decimal
                    calculationList.getItems().add(calc.getText() + " = " + numStack.get(0)); // Add calculation and answer to list
                } else {
                    calculationList.getItems().add(calc.getText() + " = " + numStack.get(0).intValue());
                }
                numStack.clear();
                opStack.clear();
                calc.clear();
            }
        } catch (Exception e) {
            message.setText("Error. Please revise your equation."); // Send message to user to revise equation
        }
    }
    
    public void setButtonSize() {
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
        decimal.setPrefHeight(50.0);
        decimal.setPrefWidth(50.0);
        decimal.setStyle(fSize);
        clear.setPrefHeight(50.0);
        clear.setPrefWidth(50.0);
        clear.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        delete.setPrefHeight(50.0);
        delete.setPrefWidth(50.0);
        delete.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }
    
    public void setButtonsAndPane() {
        buttonPane.setHgap(0);
        buttonPane.setVgap(0);
        buttonPane.setPadding(new Insets(10, 10, 10, 10));
        
        // Set numbers to button pane
        buttonPane.add(calc, 0, 0, 4, 1);
        buttonPane.add(number1, 0, 1);
        buttonPane.add(number2, 1, 1);
        buttonPane.add(number3, 2, 1);
        buttonPane.add(number4, 0, 2);
        buttonPane.add(number5, 1, 2);
        buttonPane.add(number6, 2, 2);
        buttonPane.add(number7, 0, 3);
        buttonPane.add(number8, 1, 3);
        buttonPane.add(number9, 2, 3);
        buttonPane.add(number0, 1, 4);
        buttonPane.add(decimal, 0, 4);
        buttonPane.add(plusSign, 3, 1);
        buttonPane.add(minusSign, 3, 2);
        buttonPane.add(division, 3, 3);
        buttonPane.add(multi, 3, 4);
        buttonPane.add(equals, 2, 4);
        buttonPane.add(clear, 0, 6, 2, 1);
        buttonPane.add(delete, 2, 6, 3, 1);

        calculationList.setMinWidth(280);

        // Right side ticker pane
        tickerPane.add(message, 0, 0);
        tickerPane.add(calculationList, 0, 1);
        delClear.add(save, 0, 1);
        delClear.add(load, 0, 3);

        overallPane.add(tickerPane, 1, 0, 1, 2);
        overallPane.add(delClear, 2, 0);
        overallPane.add(buttonPane, 0, 0);
    }

    public boolean doesFileExist(File file) { // Checks if file exists
        return file.exists();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
