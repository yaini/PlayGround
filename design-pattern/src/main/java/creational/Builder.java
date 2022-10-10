package creational;

public class Builder {
  public static void main(String[] args) {
    Wallet myWallet =
        new Wallet.WalletBuilder(1000).identificationCard(true).creditCard(200).build();
  }
}

class Wallet {
  private final int cash;
  private int creditCard;
  private boolean identificationCard;

  private Wallet(WalletBuilder builder) {
    this.cash = builder.cash;
    this.creditCard = builder.creditCard;
    this.identificationCard = builder.identificationCard;
  }

  public static class WalletBuilder {
    // Required
    private final int cash;

    // Optional
    private int creditCard;
    private boolean identificationCard;

    public WalletBuilder(int cash) {
      this.cash = cash;
    }

    public WalletBuilder creditCard(int creditCard) {
      this.creditCard = creditCard;
      return this;
    }

    public WalletBuilder identificationCard(boolean identificationCard) {
      this.identificationCard = identificationCard;
      return this;
    }

    public Wallet build() {
      return new Wallet(this);
    }
  }
}
