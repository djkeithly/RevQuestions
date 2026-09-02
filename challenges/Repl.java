package challenges;

public class Repl {
    private double balance;

    public Repl(){
        balance = 0;
    }

    public void checkBalance(){
        System.out.printf("You have $%.2f\n", balance);
    }

    public void deposit(double depo){
        if(depo <= 0){
            System.out.println("Error: Deposit must be greater than zero.");
        } else {
            balance += depo;
            System.out.println("Deposit Complete.");
            System.out.printf("Balance is now at $%.2f\n", balance);
        }
    }

    public void withdraw(double with){
        if(with <= 0){
            System.out.println("Error: Withdraw must be greater than zero");
        } else if (balance - with < 0){
            System.out.println("Account decline: insufficient funds to withdraw.");
        } else {
            balance = balance - with;
            System.out.println("Withdraw Complete.");
            System.out.printf("Balance is now at $%.2f\n", balance);
        }
    }
}
