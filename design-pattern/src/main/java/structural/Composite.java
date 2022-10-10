package structural;

import java.util.ArrayList;
import java.util.List;

public class Composite {
  public static void main(String[] args) {
    Folder root = new Folder();
    root.add(new File());
    root.add(new Folder());

    for (Component component : root.child) {
      component.operation();
    }
  }
}

interface Component {
  void operation();
}

class File implements Component {

  @Override
  public void operation() {
    System.out.println("This is File");
  }
}

class Folder implements Component {

  List<Component> child = new ArrayList<>();

  @Override
  public void operation() {
    System.out.println("This is Folder");
  }

  public void add(Component component) {
    child.add(component);
  }

  public void remove() {
    child = new ArrayList<>();
  }

  public List<Component> getChild() {
    return child;
  }
}
