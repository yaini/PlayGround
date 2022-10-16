package creational;

public class AbstractFactory {

  public static void main(String[] args) {
    Card creditCard = CardFactory.getCreditCard(new HyundaiCardFactory());
    Card debitCard = CardFactory.getDebitCard(new KBCardFactory());
  }
}

interface CardAbstractFactory {
  Card createCreditCard();

  Card createDebitCard();
}

class HyundaiCardFactory implements CardAbstractFactory {

  @Override
  public Card createCreditCard() {
    return new CreditCard();
  }

  @Override
  public Card createDebitCard() {
    return new DebitCard();
  }
}

class KBCardFactory implements CardAbstractFactory {

  @Override
  public Card createCreditCard() {
    return new CreditCard();
  }

  @Override
  public Card createDebitCard() {
    return new DebitCard();
  }
}

class CardFactory {
  public static Card getCreditCard(CardAbstractFactory cardFactory) {
    return cardFactory.createCreditCard();
  }

  public static Card getDebitCard(CardAbstractFactory cardFactory) {
    return cardFactory.createDebitCard();
  }
}

class DebitCard extends Card {}

class CreditCard extends Card {}

abstract class Card {
  int payment;
}
