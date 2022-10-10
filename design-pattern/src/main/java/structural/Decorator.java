package structural;

public class Decorator {
  public static void main(String[] args) {
    Wallet decoratedWallet = new ColorDecorator(new ZipperDecorator(new SimpleWallet()));
    decoratedWallet.custom();
  }
}

interface Wallet {
  void custom();
}

class SimpleWallet implements Wallet {

  @Override
  public void custom() {
    System.out.println("My Wallet");
  }
}

class WalletDecorator implements Wallet {
  private Wallet decoratedWallet;

  public WalletDecorator(Wallet decoratedWallet) {
    this.decoratedWallet = decoratedWallet;
  }

  @Override
  public void custom() {
    decoratedWallet.custom();
  }
}

class ColorDecorator extends WalletDecorator {
  ColorDecorator(Wallet decoratedWallet) {
    super(decoratedWallet);
  }

  @Override
  public void custom() {
    super.custom();
    System.out.println("Color My wallet");
  }
}

class ZipperDecorator extends WalletDecorator {
  ZipperDecorator(Wallet decoratedWallet) {
    super(decoratedWallet);
  }

  @Override
  public void custom() {
    super.custom();
    System.out.println("Zip My wallet");
  }
}
