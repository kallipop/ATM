import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ATM{
    private Account currentAccount;
    private ArrayList<Account> accountList ;
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private AccountManager accountManager;
    private boolean running ;
    private boolean menuRunning;

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

        Account foundAccount = findAccount(accountNumber);

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

        menuRunning= true;

        while(menuRunning){
        
            System.out.println("Choose a number for your option");
            System.out.println("-------------------------");
            System.out.println("1. Withdraw ");
            System.out.println("2. Deposit ");
            System.out.println("3.Transfer money ");
            System.out.println("4. Account information ");
            System.out.println("5. Transaction history ");
            System.out.println("6. Logout ");
            System.out.println("7. Exit ATM ");


            String option = in.readLine();

            switch (option) {
                case "1":
                    handleWithdrawal();
                    break;
            
                case "2":
                    handleDeposit();
                    break;
                
                case "3":
                    handleTransfer();
                    break;
                    

                case "4":
                    showAccountInfo();
                    break;
                     

                case "5":
                    transactionHistory();
                    break;

                case "6":
                    logout();
                    break;

                case "7":
                    exitATM();
                    break;

                default:
                    System.err.println("There is no such an option");
                    break;
            }}

        
    }

    private double readDoubleInput() throws Exception {
        while (true) {
            try {
                return Double.parseDouble(in.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public void handleWithdrawal() throws Exception {
        System.out.println("Enter the amount of money you want to withdraw");
        Double with_amount = readDoubleInput();
        withdrawMoney(with_amount);

    }

    public void handleDeposit() throws Exception {
        System.out.println("Enter the amount of money you want to deposit");
        Double depo_amount = readDoubleInput();
        depositMoney(depo_amount);

    }

    public void handleTransfer() throws Exception{
        System.out.println("Enter the account number you want to transfer money to");
        int transfer_account_number = Integer.parseInt(in.readLine());

        Account targetAccount = findAccount(transfer_account_number);


        if(targetAccount != null){
            System.out.println("Enter the amount of money you want to transfer");
            Double transfer_amount = readDoubleInput();
            currentAccount.transfer(transfer_amount, targetAccount);
                    

        }else{
            System.out.println("There is no account with this number");
        }

    }

    public void showAccountInfo(){
        System.out.println("Account number: " + currentAccount.getAccountNumber());
        System.out.println("Owner name: " + currentAccount.getOwnerName());
        System.out.println("Balance: " + currentAccount.getBalance());
        
    }

    

    public void transactionHistory() {
        currentAccount.showTransactionHistory();
    }

    public Account findAccount(int accountNumber) {
        for (Account ac : accountList){
            if (ac.hasAccountNumber(accountNumber)){
                return ac;
            }
        }
        return null;

    }

    public void logout(){
        System.out.println("Logging out...");
        menuRunning = false;
        currentAccount = null;
    }

    public void exitATM(){
        System.out.println("Exiting the ATM. Thank you for using our services.");
        accountManager.saveAccounts(accountList);
        accountManager.saveTransactions(accountList);
        running = false;
        menuRunning = false;
        currentAccount = null;
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