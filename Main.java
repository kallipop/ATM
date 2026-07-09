import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Account> accounts = new ArrayList<>();


       AccountManager accountManager = new AccountManager();
         accounts = accountManager.loadAccounts();
         accountManager.loadTransactions(accounts);

        try{
           ATM atm = new ATM(accounts,accountManager);
           atm.start();
        }catch(Exception e){System.out.println("ATM crashed"+ e.getMessage());};


    }
    
}
