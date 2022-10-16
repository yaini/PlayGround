package structural;

public class Bridge {

  public static void main(String[] args) {
    HanaBank bank = new HanaBank(new SavingAccountAPI());
    bank.deposit(10000);
    bank.withdraw(10);
    bank.transferForMonth();
  }
}

interface MoneyAPI {
  int withdraw(int money);

  int deposit(int money);
}

class SavingAccountAPI implements MoneyAPI {

  private int withdrawCnt = 5;
  private int balance;

  @Override
  public int withdraw(int money) {
    if (withdrawCnt > 0) {
      balance -= money;
    } else {
      System.out.println("Can't withdraw money");
    }
    return balance;
  }

  @Override
  public int deposit(int money) {
    balance += money;
    return balance;
  }
}

class CheckingAccountAPI implements MoneyAPI {

  private int balance;

  @Override
  public int withdraw(int money) {
    balance -= money;
    return balance;
  }

  @Override
  public int deposit(int money) {
    balance += money;
    return balance;
  }
}

abstract class Bank {
  private MoneyAPI api;

  Bank(MoneyAPI api) {
    this.api = api;
  }

  public int withdraw(int money) {
    return api.withdraw(money);
  }

  public int deposit(int money) {
    return api.deposit(money);
  }
}

class HanaBank extends Bank {
  HanaBank(MoneyAPI api) {
    super(api);
  }

  public void transferForMonth() {
    for (int i = 0; i < 12; i++) {
      deposit(100);
    }
  }
}
