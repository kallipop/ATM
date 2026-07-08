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

    public void saveAccounts(ArrayList<Account> accounts) {
        try{
            FileWriter myWriter = new FileWriter("accounts.txt");
            for (Account account : accounts) {
                myWriter.write(account.getOwnerName() + "," + account.getAccountNumber() + "," + account.getPIN() + "," + account.getBalance() + "\n");
            }
            myWriter.close();
            System.out.println("Accounts saved successfully.");
        
        }catch(Exception e){
            System.out.println("An error occurred while saving accounts: " + e.getMessage());
        }
    }

    
}
