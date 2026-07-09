import java.util.ArrayList;

public class Account {
    private int accountNumber;
    private int PIN;
    private double balance;
    private String ownerName;
    private ArrayList<Transaction> transactions ;

    public Account(String name, int number, int pin, double balance){
        accountNumber=number;
        PIN=pin;
        this.balance = balance;
        ownerName=name;
        transactions = new ArrayList<>();

    }

    public double getBalance(){
        return balance;
    }

    public int getPIN(){
        return PIN;
    }


    public int getAccountNumber(){
        return accountNumber;
    }

    public String getOwnerName(){
        return ownerName;
    }

    public boolean hasAccountNumber(int number){
        return accountNumber == number;
    }

    public boolean hasPIN(int pin ){
        return PIN == pin;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
    public void deposit(double amount){
       if (amount >0){
        balance+=amount;
        transactions.add(new Transaction(accountNumber,"Deposit", amount, java.time.LocalDate.now().toString()));
    } else {
        System.out.println("Deposit amount must be positive.");}

    }

    public void withdraw(double amount){
        if(amount >0 &&balance>=amount){
            balance -= amount;
            transactions.add(new Transaction(accountNumber,"Withdrawal", amount, java.time.LocalDate.now().toString()));
        }else{
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    public void showTransactionHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction.getDate() + " - " + transaction.getType() + ": $" + transaction.getAmount());
            }
        }
    }

    public void addTransaction(String type, double amount, String date) {
        transactions.add(new Transaction(accountNumber, type, amount, date));
    }

    public void transfer(double amount, Account targetAccount) {
        if (this == targetAccount) {
            System.out.println("Cannot transfer to the same account.");
            return;
        }
        
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            targetAccount.balance += amount;

            transactions.add(new Transaction(accountNumber, "Transfer to " + targetAccount.getAccountNumber(), amount, java.time.LocalDate.now().toString()));
            targetAccount.addTransaction("Transfer from " + this.accountNumber, amount, java.time.LocalDate.now().toString());
        } else {
            System.out.println("Insufficient funds or invalid amount for transfer.");
        }
    }

    
}