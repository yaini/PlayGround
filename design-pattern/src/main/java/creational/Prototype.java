package creational;

import java.util.ArrayList;
import java.util.List;

public class Prototype {
    public static void main(String[] args){
        MoneyFactory moneyFactory = new MoneyFactory(new Coin(100));

        List<Money> wallet = new ArrayList<>();
        for( int i=0; i<10; i++ ){
            wallet.add(moneyFactory.makeMoney());
        }
    }
}

class Money implements Cloneable{
    int price;

    public Object clone() {
        try {
            Money copy = (Money) super.clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}

class Coin extends Money{

    Coin(int price){
        this.price = price;
    }
}

class MoneyFactory{

    private Money money;

    public MoneyFactory(Money money){
        this.money = money;
    }

    public Money makeMoney(){
        return (Money) money.clone();
    }

}