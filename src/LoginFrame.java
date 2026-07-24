import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.image.*;

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

        Label error = new Label();
        error.getStyleClass().add("error-label");


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

                if (attempts >0){
                    error.setText("Wrong PIN. Attempts remaining: "+ attempts);
                }else{
                    error.setText("Too many failed attempts.");
                    loginButton.setDisable(true);
                }
            }
        }catch(NumberFormatException ex){
            error.setText("Account number and PIN must contain only numbers");
        }
        });

        passwordField.setOnAction(e->{loginButton.fire();});

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

      
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(50));
        box.setPrefWidth(450);
        box.setMaxWidth(450);
        box.setMaxHeight(550);
        box.setPrefHeight(550);

        box.getStyleClass().add("box");

        Image image = new Image(getClass().getResourceAsStream("/resources/atm-machine.png"));

        ImageView icon = new ImageView(image);
        icon.setFitHeight(70);
        icon.setFitWidth(70);
        icon.setPreserveRatio(true);

        box.getChildren().addAll(icon,title, accountField, passwordField,error, loginButton);

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
