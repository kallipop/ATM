public class Transaction {
    private String type;
    private double amount;
    private String date;
    private int accountNumber;

    public Transaction(int accountNumber,String type, double amount, String date){
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.accountNumber = accountNumber;
    }

    public String getType() {
        return type;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
    
}
