package creational;

public class FactoryMethod {

    public static void main(String[] args){

        Bank bank = new Bank();

        Account saveAccount = bank.createAccount(AccountType.SAVE, "yaini");
        saveAccount.deposit(100);

        CheckingAccount checkAccount = (CheckingAccount) bank.createAccount(AccountType.CHECK, "yaini");
        checkAccount.withdraw(100);

    }
}

enum AccountType { SAVE, CHECK }

class Bank extends AccountFactory{

    @Override
    protected Account createAccount(AccountType type, String holder) {
        switch ( type ){
            case SAVE:
                return new SavingAccount(holder);
            case CHECK:
                return new CheckingAccount(holder);
            default:
                throw new RuntimeException("Invalid Type");
        }
    }
}

abstract class AccountFactory{

    protected abstract Account createAccount(AccountType type, String holder);

}

abstract class Account{

    public String holder;
    public int balance;
    public abstract void deposit(int money);

    public Account(String holder){
        this.holder = holder;
    }
}

class SavingAccount extends Account{

    public SavingAccount(String holder) {
        super(holder);
    }

    @Override
    public void deposit(int money) {
        this.balance += money;
    }
}

class CheckingAccount extends Account{

    public CheckingAccount(String holder) {
        super(holder);
    }

    @Override
    public void deposit(int money) {
        this.balance -= money;
    }

    public void withdraw(int money){
        this.balance -= money;
    }
}