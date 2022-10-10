package structural;

public class Adapter {
  public static void main(String[] args) {
    CardClassAdapter classAdapter = new CardClassAdapter();
    classAdapter.pay();

    CardObjectAdapter objectAdapter = new CardObjectAdapter(new TrafficCard());
    objectAdapter.pay();
  }
}

interface Card {
  void pay();
}

class TrafficCard {

  public void trafficPay() {
    System.out.println("Traffic Card Pay");
  }
}

class CardObjectAdapter implements Card {

  TrafficCard trafficCard;

  public CardObjectAdapter(TrafficCard trafficCard) {
    this.trafficCard = trafficCard;
  }

  @Override
  public void pay() {
    trafficCard.trafficPay();
  }
}

class CardClassAdapter extends TrafficCard implements Card {

  @Override
  public void pay() {
    this.trafficPay();
  }
}
