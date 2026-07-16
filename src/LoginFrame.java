import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
public class LoginFrame {
    private Stage stage;
    private AccountManager accountManager;
    private int attempts =3;

    public LoginFrame(Stage stage, AccountManager accountManager) {
        this.stage = stage;
        this.accountManager = accountManager;
    
    }

    public void show() {
        Label title = new Label("ATM Login");
        title.setId("title");

        TextField accountField = new TextField();
        accountField.setPromptText("Enter account number");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter PIN");

        Button loginButton = new Button("Login");


        loginButton.setOnAction(e -> {
            try{
            int accountNumber = Integer.parseInt(accountField.getText());
            int pin = Integer.parseInt(passwordField.getText());

            Account account = accountManager.findAccount(accountNumber);

            if (account != null && account.hasPIN(pin)) {
                System.out.println("Login successful");
                ATMFrame atmFrame = new ATMFrame(stage,account,accountManager);
                atmFrame.show();
            } else {
                attempts --;
                Alert alert = new Alert(Alert.AlertType.ERROR);

                if (attempts >0){
                    alert.setContentText("Wrong PIN. \nAttempts remaining: "+ attempts);
                }else{
                    alert.setContentText("Too many failed attempts.");
                    loginButton.setDisable(true);
                }
                alert.setTitle("Login Failed");
                alert.show();
            }
        }catch(NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Account number and PIN must contain only numbers");
            alert.show();
        }
        });


        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        VBox.setMargin(title, new Insets(-80.,0,30,0));

      
        VBox box = new VBox(25);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(50));
        box.setPrefWidth(700);
        box.setPrefHeight(500);

        box.getStyleClass().add("box");

        box.getChildren().addAll(title, accountField, passwordField, loginButton);

        layout.getChildren().add(box);

        Scene scene = new Scene(layout, 1000, 700);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("ATM");
        stage.setMaximized(true);
        stage.show();
        layout.requestFocus();


    }
    
}
