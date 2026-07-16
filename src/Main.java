import javafx.stage.Stage;

import java.util.ArrayList;

import javafx.application.Application;

public class Main extends Application{

    public void start(Stage stage){
        AccountManager accountManager = new AccountManager();
        ArrayList<Account> accounts = accountManager.loadAccounts();

        accountManager.loadTransactions(accounts);
        

        LoginFrame loginFrame = new LoginFrame(stage,accountManager);
        loginFrame.show();

        stage.setMaximized(true);
    }


    public static void main(String[] args) {
        launch();


    }
    
}
