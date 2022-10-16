package structural;

public class Facade {
  public static void main(String[] args) {
    Computer computer = new Computer();
    computer.on();
  }
}

class Computer {

  PowerSupply powerSupply;
  OperatingSystem operatingSystem;
  BootLoader bootLoader;

  Computer() {
    this.powerSupply = new PowerSupply();
    this.operatingSystem = new OperatingSystem();
    this.bootLoader = new BootLoader();
  }

  public void on() {
    powerSupply.on();
    if (operatingSystem.check()) {
      bootLoader.load();
      operatingSystem.execute();
    }
  }
}

class PowerSupply {
  public void on() {
    System.out.println("power on ...");
  }
}

class OperatingSystem {
  public boolean check() {
    System.out.println("check os ... ");
    return true;
  }

  public void execute() {
    System.out.println("os execute ...");
  }
}

class BootLoader {
  public void load() {
    System.out.println("bootloader load ...");
  }
}
