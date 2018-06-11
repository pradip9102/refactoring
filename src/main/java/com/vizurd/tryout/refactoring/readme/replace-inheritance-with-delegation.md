replace-inheritance-with-delegation:java

---

1. Create a field in the subclass for holding the superclass. During the initial stage, place the current object in it.

2. Change the subclass methods so that they use the superclass object instead of `this`.

3. Remove the inheritance declaration from the subclass.

4. Change the initialization code of the field in which the former superclass is stored by creating a new object.



---

```
class Engine {
  //…
  private double fuel;
  private double CV;

  public double getFuel() {
    return fuel;
  }
  public void setFuel(double fuel) {
    this.fuel = fuel;
  }
  public double getCV() {
    return CV;
  }
  public void setCV(double cv) {
    this.CV = cv;
  }
}

class Car extends Engine {
  // ...
  private String brand;
  private String model;

  public String getName() {
    return brand + " " + model + " (" + getCV() + "CV)";
  }
  public String getModel() {
    return model;
  }
  public void setModel(String model) {
    this.model = model;
  }
  public String getBrand() {
    return brand;
  }
  public void setBrand(String brand) {
    this.brand = brand;
  }
}
```

---

```
class Engine {
  //…
  private double fuel;
  private double CV;

  public double getFuel() {
    return fuel;
  }
  public void setFuel(double fuel) {
    this.fuel = fuel;
  }
  public double getCV() {
    return CV;
  }
  public void setCV(double cv) {
    this.CV = cv;
  }
}

class Car {
  // ...
  private String brand;
  private String model;
  protected Engine engine;

  public Car() {
    this.engine = new Engine();
  }
  public String getName() {
    return brand + " " + model + " (" + engine.getCV() + "CV)";
  }
  public String getModel() {
    return model;
  }
  public void setModel(String model) {
    this.model = model;
  }
  public String getBrand() {
    return brand;
  }
  public void setBrand(String brand) {
    this.brand = brand;
  }
}
```

---

###### Set step 1


#|en| Let's try out one more refactoring using a `Car` class that is inherited from the `Engine` as our example.

Select "getCV()" in "Car"


#|en| At first, inheritance seemed a good and noble idea… But later we found that cars use only one engine's property (volume, to be precise).

Go to the start of "Car"


#|en| So it would have been more efficient to delegate to the `Engine` class for getting the necessary properties or methods.


#|en| Let's start refactoring by creating a field for storing a reference to an engine object.

Go to "String model;|||"

Print:
```

  protected Engine engine;
```

Select "Engine |||engine|||"


#|en| For now we will fill this field with the current object (this can be done in the constructor).

Go to before "getName"

Print:
```

  public Car() {
    this.engine = this;
  }
```

###### Set step 2

Select "getCV()" in "Car"


#|en| Then we should change all access points to the Engine's fields and methods so that they go through the newly created field. In our case, this happens in only one place. 


Print "engine.getCV()"

###### Set step 3

Select " extends Engine"


#|en| Now we can remove the inheritance declaration from the `Car` class.

Remove selected

###### Set step 4

Select "engine = |||this|||"


#|en| All that's left to do is create a new engine object for filling the field of the associated object.

Replace "new Engine()"


#C|en| Let's start the final testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
