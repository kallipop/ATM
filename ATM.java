import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ATM{
    private Account currentAccount;
    private ArrayList<Account> accountList ;
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public ATM(ArrayList<Account> accounts){
        accountList = accounts;
    }

    public void start() throws Exception{
        while(true){
            login();
            if(currentAccount != null){
                showMenu();
            }
        }
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
            System.out.println("3. Check balance ");
            System.out.println("4. Exit ");

            String option = in.readLine();

            switch (option) {
                case "1":
                    System.out.println("Enter the ammount of money you want to withdraw");
                    Double with_ammount = Double.parseDouble(in.readLine());
                    withdrawMoney(with_ammount);
                    break;
            
                case "2":
                    System.out.println("Enter the ammount of money you want to deposit");
                    Double depo_ammount = Double.parseDouble(in.readLine());
                    depositMoney(depo_ammount);
                    break;
                
                case "3":
                    System.out.println("Your current balance is " + checkBalance()); 

                    break;

                case "4":
                    System.out.println("Exiting...");
                    currentAccount = null;
                    flag =false;
                    break; 

                default:
                    System.err.println("There is no such an option");
                    break;
            }}

        
    }



    public void withdrawMoney(double ammount){
        currentAccount.withdraw(ammount);
    }

    public void depositMoney(double ammount){
        currentAccount.deposit(ammount);
    }

    public double checkBalance(){
        return currentAccount.getBalance();
    }
}