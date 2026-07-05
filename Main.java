import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Account> accounts = new ArrayList<>();


        accounts.add(new Account("Olivia Smith",222,222,1000000));
        accounts.add(new Account("David Thomas",111,111,10));
        accounts.add(new Account("Lily Allen",333,333,700));
        accounts.add(new Account("Michael Brown",555,555,9000));

        try{
           ATM atm = new ATM(accounts);
           atm.start();
        }catch(Exception e){System.out.println("ATM crashed"+ e.getMessage());};


    }
    
}
