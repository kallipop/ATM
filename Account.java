public class Account {
    private int accountNumber;
    private int PIN;
    private double balance;
    private String ownerName;

    public Account(String name, int number, int pin, double balance){
        accountNumber=number;
        PIN=pin;
        this.balance = balance;
        ownerName=name;

    }

    public double getBalance(){
        return balance;
    }

    public boolean hasAccountNumber(int number){
        return accountNumber == number;
    }

    public boolean hasPIN(int pin ){
        return PIN == pin;
    }

    public void deposit(double ammount){
        balance+=ammount;
    }

    public void withdraw(double ammount){
        if(balance-ammount>=0){
            balance -= ammount;
        }else{
            System.out.println("Insufficient funds.");
        }
    }
}