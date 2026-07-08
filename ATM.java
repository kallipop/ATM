import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ATM{
    private Account currentAccount;
    private ArrayList<Account> accountList ;
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private AccountManager accountManager;
    private boolean running ;

    public ATM(ArrayList<Account> accounts, AccountManager accountManager){
        accountList = accounts;
        this.accountManager = accountManager;
    }

    public void start() throws Exception{
        running = true;
        while(running){
            login();
            if(currentAccount != null){
                showMenu();
            }
        }
        System.out.println("ATM closed");
    }

    public void login() throws Exception{

        int attempts =0;
        currentAccount = null;
        System.out.println("Enter the number of the account ");
        int accountNumber =Integer.parseInt(in.readLine());

        Account foundAccount = null ;

        for (Account ac : accountList){
            if (ac.hasAccountNumber(accountNumber)){
                foundAccount =ac;
                break;
            }
        }

        if (foundAccount != null){
        while(attempts<3){
        System.out.println("Enter the PIN for the account");
        int pin =Integer.parseInt(in.readLine());
        if (foundAccount.hasPIN(pin)){
            currentAccount = foundAccount;
            break;
        }else {
            attempts++;
            System.out.println("Wrong PIN...attempts left " + (3-attempts));
            
                }}
        
        if(currentAccount == null){
            System.out.println("Too many attempts");
        }
            
        
        }else{
            System.out.println("There is no account with this number");
        }




    }

    public void showMenu() throws Exception{

        boolean flag = true;

        while(flag){
        
            System.out.println("Choose a number for your option");
            System.out.println("-------------------------");
            System.out.println("1. Withdraw ");
            System.out.println("2. Deposit ");
            System.out.println("3.Transfer money ");
            System.out.println("4. Account information ");
            System.out.println("5. Transaction history ");
            System.out.println("6. Exit ");


            String option = in.readLine();

            switch (option) {
                case "1":
                    System.out.println("Enter the amount of money you want to withdraw");
                    Double with_amount = Double.parseDouble(in.readLine());
                    withdrawMoney(with_amount);
                    break;
            
                case "2":
                    System.out.println("Enter the amount of money you want to deposit");
                    Double depo_amount = Double.parseDouble(in.readLine());
                    depositMoney(depo_amount);
                    break;
                
                case "3":
                    System.out.println("Enter the account number you want to transfer money to");
                    int transfer_account_number = Integer.parseInt(in.readLine());
                    System.out.println("Enter the amount of money you want to transfer");
                    Double transfer_amount = Double.parseDouble(in.readLine());
                    transferMoney(transfer_account_number, transfer_amount, accountList);
                    break;

                case "4":
                    System.out.println("Account number: " + currentAccount.getAccountNumber());
                    System.out.println("Owner name: " + currentAccount.getOwnerName());
                    System.out.println("Balance: " + currentAccount.getBalance());
                    break;
                     

                case "5":
                    transactionHistory();
                    break;

                case "6":
                    System.out.println("Exiting the ATM. Thank you for using our services.");
                    accountManager.saveAccounts(accountList);
                    running = false;
                    flag = false;
                    currentAccount = null;
                    break;

                default:
                    System.err.println("There is no such an option");
                    break;
            }}

        
    }

    public void transactionHistory() {
        currentAccount.showTransactionHistory();
    }

    public void transferMoney(int accountNumber, double amount, ArrayList<Account> accounts){
        Account targetAccount = null;
        for (Account ac : accounts){
            if (ac.hasAccountNumber(accountNumber)){
                targetAccount = ac;
                break;
            }
        }

        if (targetAccount != null){
            if(currentAccount.getBalance() >= amount && amount > 0){
                currentAccount.withdraw(amount);
                targetAccount.deposit(amount);
                System.out.println("Transfer successful.");
            } else {
                System.out.println("Insufficient funds or invalid amount.");
            }
        } else {
            System.out.println("Target account not found.");
        }
    }
     


    public void withdrawMoney(double amount){
        currentAccount.withdraw(amount);
    }

    public void depositMoney(double amount){
        currentAccount.deposit(amount);
    }

    public double checkBalance(){
        return currentAccount.getBalance();
    }
}