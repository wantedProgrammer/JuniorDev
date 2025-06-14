import java.util.Date;

public class Accounts implements Comparable{
    // Variables
    static int nextAccNo = 10;
    int accNo;
    String owner;
    City city;
    char gender;
    double balance;
    Date openDate;

    // Constructor
    public Accounts() {
    }
    // Constructor with parameters
    public Accounts(String owner, City city, char gender) {
        accNo = nextAccNo;
        nextAccNo+=10;
        this.owner = owner;
        this.city = city;
        this.gender = gender;

        balance = 0.0;
        openDate = null;
    }
    // Constructor
    public Accounts(int accNo, String owner, City city, char gender, double balance) {
        this.accNo = accNo;
        this.owner = owner;
        this.city = city;
        this.gender = gender;
        setBalance(balance);
    }
    // Setter
    public void setBalance(double b) {
        balance = b > 0.0 ? b: 0.0;
    }
    // toString


    @Override
    public String toString() {
        return accNo + " " + owner
                +      " " + city.cityName
                + " " + gender + " " + balance + " " + openDate;
    }
    // Compare
    @Override
    public int compareTo(Object o) {
        return this.owner.compareTo(((Accounts) o) .owner);
    }
    // Deposit
    public void deposit(double amount){
        if(amount > 0){
            setBalance(balance + amount);
        }
    }
    // Withdraw
    public double withdraw(double amount){
        if(amount > 0){
            if(amount < balance){
                setBalance(balance - amount);
            }
            else{
                amount = balance;
                setBalance(0.0);
            }
            return amount;
        }
        return 0.0;
    }
}
