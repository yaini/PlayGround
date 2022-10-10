package creational;

public class Singleton {
  public static void main(String[] args) {
    IdentificationCard.getInstance();
  }
}

class IdentificationCard {

  private static IdentificationCard instance;

  private IdentificationCard() {}
  ;

  public static IdentificationCard getInstance() {
    if (instance == null) {
      return new IdentificationCard();
    } else {
      return instance;
    }
  }
}
