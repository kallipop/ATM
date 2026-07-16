import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.*;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;public class ATMFrame{
    private Stage stage;
    private Account currentAccount;
    private AccountManager accountManager;
    private VBox layout;

    public ATMFrame(Stage stage, Account account, AccountManager accountManager){
        this.stage = stage;
        this.currentAccount = account;
        this.accountManager = accountManager;
    }

    public void show(){

        layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));


        showMainMenu();

        Scene scene = new Scene(layout, 1000, 700);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("ATM");
        stage.show();
        stage.setMaximized(true);
        layout.requestFocus();
    }

    public void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
            
        
    }



    private void showMainMenu(){
        layout.getChildren().clear();

        Label welcome = new Label("Welcome, " + currentAccount.getOwnerName());

        Label balanceLabel = new Label("Balance: €" + String.format("%.2f", currentAccount.getBalance()));
        balanceLabel.setId("balance");

        Button infoButton = new Button("Account Information");
        Button withdrawButton = new Button("Withdraw");
        Button depositButton = new Button("Deposit");
        Button transferButton = new Button("Transfer");
        Button transactionHistoryButton = new Button("Transaction History");
        Button logoutButton = new Button("Logout");
        Button changePINButton = new Button("Change PIN");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(120);
        grid.setVgap(40);

        grid.add(withdrawButton, 0, 0);
        grid.add(depositButton, 0, 1);
        grid.add(transferButton, 0, 2);
        grid.add(transactionHistoryButton, 0, 3);
        grid.add(infoButton, 1, 0);
        grid.add(changePINButton,1,1);
        grid.add(logoutButton, 1, 2);

        grid.setAlignment(Pos.CENTER);
        

        VBox box = createBox();


        box.getChildren().addAll(welcome,balanceLabel,grid);

        layout.getChildren().add(box);
              infoButton.setOnAction(e ->{
            showInfoScreen();

        });

        transactionHistoryButton.setOnAction(e ->{
            showTransactionHistoryScreen();
        });


        changePINButton.setOnAction(e ->{
            showChangePINScreen();
        });
        withdrawButton.setOnAction(e ->{
            showFastCashScreen();
        });
        depositButton.setOnAction(e ->{
           showDepositScreen();

        });
        transferButton.setOnAction(e ->{
            showTransferScreen();
        });

        logoutButton.setOnAction(e ->{
            showLogoutScreen();
        });

       

    }

    public void showChangePINScreen(){
        layout.getChildren().clear();
        layout.setAlignment(Pos.CENTER);

        Label title = new Label("Change PIN");
        title.setId("title");

        PasswordField oldPassword = new PasswordField();
        oldPassword.setPromptText("Current PIN");

        PasswordField newPassword = new PasswordField();
        newPassword.setPromptText("New PIN");

        PasswordField confirmNewPassword = new PasswordField();
        confirmNewPassword.setPromptText("Confirm new PIN");

        Button back = new Button("Back to menu");
        Button confirm = new Button("Confirm");

        back.setOnAction(e ->{
            showMainMenu();
        });

        confirm.setOnAction(e ->{
            try{
                int oldPIN = Integer.parseInt(oldPassword.getText());
                int newPIN = Integer.parseInt(newPassword.getText());
                int confirmPIN = Integer.parseInt(confirmNewPassword.getText());

                if(!currentAccount.hasPIN(oldPIN)){
                    showError("Wrong current PIN");
                    return;
                }

                if(newPIN != confirmPIN){
                    showError("PINs do not match");
                    return;
                }

                currentAccount.changePIN(newPIN);
                layout.getChildren().clear();


                Label success = new Label("PIN changed successfully");
                success.setId("title");

                Button backMenu = new Button("Back to menu");

                backMenu.setOnAction(ex ->{
                    showMainMenu();
                });

                VBox box = createSmallBox();

                box.getChildren().addAll(success,backMenu);

                layout.getChildren().add(box);


            }catch(NumberFormatException ex){
                showError("Invalid PIN");
            }

        });

        VBox box = createBox();


        box.getChildren().addAll(oldPassword,newPassword,confirmNewPassword,back,confirm);
       

        layout.getChildren().add(box);


    }
    public void withdrawAmount(double money){
        if(money<=0){
                    showError("Withdrawal amount must be positive");
                    return;
                }

                if(money > currentAccount.getBalance()){
                    showError("Insufficient funds.");
                    return;
                }

                currentAccount.withdraw(money);

                layout.getChildren().clear();
                layout.setAlignment(Pos.CENTER);

                Label success = new Label("Withdrawal completed successfully");
                success.setId("title");

                Label balance = new Label("New balance: €" + String.format("%.2f", currentAccount.getBalance()));
                balance.setId("balance");

                Button backMenu = new Button("Back to menu");

                backMenu.setOnAction(ev ->{
                    showMainMenu();
                });

                VBox box = createSmallBox();
               
                box.getChildren().addAll(success,balance,backMenu);

                layout.getChildren().add(box);
    }

    public void showFastCashScreen(){
        layout.getChildren().clear();

        Label title = new Label("Fast Cash");
        title.setId("title");

        layout.setAlignment(Pos.CENTER);


        Button twenty = new Button("€20");
        Button forty = new Button("€40");
        Button fifty = new Button("€50");
        Button hundred = new Button("€100");
        Button twoHundred = new Button("€200");

        Button other = new Button("Other amount");
        Button back = new Button("Back to menu");

        back.setOnAction(e ->{
            showMainMenu();
        });

        twenty.setOnAction(e ->{
           withdrawAmount(20);
        });

        forty.setOnAction(e ->{
            withdrawAmount(40);
        });

        fifty.setOnAction(e ->{
            withdrawAmount(50);
        });

        hundred.setOnAction(e ->{
            withdrawAmount(100);
        });

        twoHundred.setOnAction(e ->{
            withdrawAmount(200);
        });

        other.setOnAction(e ->{
            showWithdrawScreen();
        });



        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(20);

        grid.add(twenty,0,0);
        grid.add(forty,1,0);
        grid.add(fifty,2,0);
        grid.add(hundred,0,1);
        grid.add(twoHundred,1,1);
        grid.add(other,2,1);

        VBox box = createBox();

        box.getChildren().addAll(title,grid,back);

        layout.getChildren().add(box);
        



    }

    public void showWithdrawScreen(){
        layout.getChildren().clear();
        Label title = new Label("Withdraw");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter the amount");

        Button confirm = new Button("Confirm");
        Button back = new Button("Back");

        layout.setAlignment(Pos.CENTER);
        VBox box = createBox();


        box.getChildren().addAll(title,amountField,confirm,back);
        layout.getChildren().addAll(box);


        confirm.setOnAction(e ->{
            try{
                double money = Double.parseDouble(amountField.getText());

                withdrawAmount(money);
            }catch(NumberFormatException ex){
                showError("Invalid amount entered");
            }
        });
        back.setOnAction(e ->{
            showMainMenu();

        });


    }

    public void showDepositScreen(){
        layout.getChildren().clear();

        Label title = new Label("Deposit");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");

        Button confirm = new Button("Confirm");
        Button back = new Button("Back");

        confirm.setOnAction(e ->{
            try{
                double money = Double.parseDouble(amountField.getText());

                if(money <=0){
                    showError("Deposit amount must be positive");
                    return;
                }

                currentAccount.deposit(money);
                layout.getChildren().clear();
                layout.setAlignment(Pos.CENTER);

                Label success = new Label("Deposit completed successfully");
                success.setId("title");

                Label balance = new Label("New balance: €" + String.format("%.2f", currentAccount.getBalance()));
                balance.setId("balance");

                Button backMenu = new Button("Back to menu");

                backMenu.setOnAction(ev ->{
                    showMainMenu();
                });

                VBox box = createSmallBox();

                box.getChildren().addAll(success,balance,backMenu);

                layout.getChildren().add(box);
            }catch(NumberFormatException ex){
                showError("Invalid amount entered");
            }
        });

        back.setOnAction(e ->{
            showMainMenu();
        });

        layout.setAlignment(Pos.CENTER);

        VBox box = createBox();

        box.getChildren().addAll(title,amountField,confirm,back);

        layout.getChildren().add(box);
    }

    public void showTransactionHistoryScreen(){
        layout.getChildren().clear();

        Label title = new Label("Transaction History");
        Button back = new Button("Back");

        VBox historyBox = new VBox(10);
        historyBox.setAlignment(Pos.TOP_LEFT);
        ScrollPane scroll = new ScrollPane(historyBox);

        ArrayList<Transaction> transactions = currentAccount.getTransactions();

        if(transactions.isEmpty()){
            Label emptyHistory = new Label("No transactions found");
            historyBox.getChildren().add(emptyHistory);
        }else{
            for(Transaction t : transactions){
                Label transaction = new Label(t.getDate() + "\n" + t.getType()+ ": €"+ String.format("%.2f",t.getAmount())+ "\n-------------------");
                historyBox.getChildren().add(transaction);
            }
        }

        back.setOnAction(e ->{
            showMainMenu();
        });

        VBox box = createBox();
        box.getChildren().addAll(title,scroll,back);
    
        layout.getChildren().add(box);
    }

    public void showTransferScreen(){
        layout.getChildren().clear();

        Label title = new Label("Transfer");
        Button confirm = new Button("Confirm");
        Button back = new Button("Back");

        TextField accountField = new TextField();
        accountField.setPromptText("Enter the target account number");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        confirm.setOnAction(e ->{
            try{
                int targetAccountNumber = Integer.parseInt(accountField.getText());
                double money = Double.parseDouble(amountField.getText());

                if(money <=0){
                    showError("Transfer amount must be positive");
                    return;
                }

                Account targetAccount = accountManager.findAccount(targetAccountNumber);

                if(targetAccount ==null){
                    showError("Target account not found");
                    return;
                }

                if(targetAccount == currentAccount){
                    showError("Cannot transfer money to the same account");
                    return;
                }

                if(money > currentAccount.getBalance()){
                    showError("Insufficient funds");
                    return;
                }

                currentAccount.transfer(money, targetAccount);
                layout.getChildren().clear();
                layout.setAlignment(Pos.CENTER);

                Label success = new Label("Transfer completed successfully");
                success.setId("title");

                Label balance = new Label("New balance: €" + String.format("%.2f", currentAccount.getBalance()));
                balance.setId("balance");

                Button backMenu = new Button("Back to menu");

                backMenu.setOnAction(ev ->{
                    showMainMenu();
                });

                VBox box = createSmallBox();

                box.getChildren().addAll(success,balance,backMenu);

                layout.getChildren().add(box);
            }catch(NumberFormatException ex){
                showError("Invalid account number or amount");
            }
        });



        back.setOnAction(e ->{
            showMainMenu();
        });

        VBox box = createBox();


        box.getChildren().addAll(title,accountField,amountField,confirm,back);
    
        layout.getChildren().add(box);
    }

    public void showLogoutScreen(){
        layout.getChildren().clear();

        Label title = new Label("End session?");
        Label msg = new Label("Are you sure you want to logout?");
        Button yes = new Button("Yes");
        Button no = new Button("No");

        yes.setOnAction(e ->{
        accountManager.saveAccounts();
        accountManager.saveTransactions();

        LoginFrame loginFrame = new LoginFrame(stage, accountManager);
        loginFrame.show();

        });

        no.setOnAction(e ->{
            showMainMenu();
        });

        VBox box = createBox();
        box.getChildren().addAll(title,msg,yes,no);
        layout.getChildren().add(box);

    }

    public void showInfoScreen(){
        layout.getChildren().clear();

        Label title = new Label("Account information");
        title.setId("title");

        Label owner = new Label("Owner: "+ currentAccount.getOwnerName());

        Label number = new Label("Account Number: "+ currentAccount.getAccountNumber());

        Label balance = new Label("Balance: €"+ String.format("%.2f", currentAccount.getBalance()));
        balance.setId("balance");

        Button back = new Button("Back");

        back.setOnAction(e ->{
            showMainMenu();
        });

        layout.setAlignment(Pos.CENTER);
        VBox box = createBox();

        box.getChildren().addAll(title,owner,number,balance,back);

        layout.getChildren().add(box);
        


    }

    private VBox createBox(){
        VBox box = new VBox(25);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(50));
        box.setPrefWidth(700);
        box.setPrefHeight(500);

        box.getStyleClass().add("box");

        return box;
    }

    private VBox createSmallBox(){
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(40));
        box.setPrefWidth(400);
        box.setPrefHeight(250);

        box.getStyleClass().add("box");

        return box;
    }
}