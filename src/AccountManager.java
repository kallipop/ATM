import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
public class AccountManager {
    private ArrayList<Account> accountList;

    public AccountManager(){
        accountList = new ArrayList<>();
    }

    public ArrayList<Account> getAccountList() {
        return accountList;
    }

    public ArrayList<Account> loadAccounts() {

        File file = new File("accounts.txt");
        
        try (Scanner myReader = new Scanner(file)){
            while (myReader.hasNextLine()){
                String data = myReader.nextLine();
                String[] parts = data.split(",");
                accountList.add(new Account(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Double.parseDouble(parts[3])));
            }
        }catch(FileNotFoundException e){
            System.out.println("An error occurred while loading accounts: " + e.getMessage());
        }
        return accountList;
    }

    public void saveAccounts() {
        try{
            FileWriter myWriter = new FileWriter("accounts.txt");
            for (Account account : accountList) {
                myWriter.write(account.getOwnerName() + "," + account.getAccountNumber() + "," + account.getPIN() + "," + account.getBalance() + "\n");
            }
            myWriter.close();
            System.out.println("Accounts saved successfully.");
        
        }catch(Exception e){
            System.out.println("An error occurred while saving accounts: " + e.getMessage());
        }
    }

    public void loadTransactions(ArrayList<Account> accounts) {
        File file = new File("transactions.txt");
        
        try (Scanner myReader = new Scanner(file)){
            while (myReader.hasNextLine()){
                String data = myReader.nextLine();
                String[] parts = data.split(",");
                int accountNumber = Integer.parseInt(parts[0]);
                String type = parts[1];
                double amount = Double.parseDouble(parts[2]);
                String date = parts[3];

               for (Account ac : accounts){
                    if (ac.hasAccountNumber(accountNumber)){
                        ac.addTransaction(type,amount,date);
                        break;
                    }
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("An error occurred while loading transactions: " + e.getMessage());
        }
    }

    public void saveTransactions() {
        try{
            FileWriter myWriter = new FileWriter("transactions.txt");
            for (Account ac : accountList) {
                for (Transaction transaction : ac.getTransactions()) {
                myWriter.write(transaction.getAccountNumber() + "," + transaction.getType() + "," + transaction.getAmount() + "," + transaction.getDate() + "\n");
            }}
            myWriter.close();
            System.out.println("Transactions saved successfully.");
        
        }catch(Exception e){
            System.out.println("An error occurred while saving transactions: " + e.getMessage());
        }
    }

    
    public Account findAccount(int accountNumber) {
        for (Account ac : accountList){
            if (ac.hasAccountNumber(accountNumber)){
                return ac;
            }
        }
        return null;

    }
    

    
}
