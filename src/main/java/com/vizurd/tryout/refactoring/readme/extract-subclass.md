extract-subclass:java

---

1. Create a new subclass from the class of interest.

2. If you need additional data to create objects from a subclass, create a constructor and add the necessary parameters to it. Do not forget to call the constructor's parent implementation.

3. Find all calls to the constructor of the parent class. When the functionality of a subclass is necessary, replace the parent constructor with the subclass constructor.

4. Move the necessary methods and fields from the parent class to the subclass. Do this via <a href="/push-down-method">Push Down Method</a> and <a href="/push-down-field">Push Down Field</a>. It is simpler to start by moving the methods first. This way, the fields remain accessible throughout the whole process: from the parent class prior to the move, and from the subclass itself after the move is complete.

5. After the subclass is ready, find all the old fields that controlled the choice of functionality. Delete these fields by using polymorphism to replace all the operators in which the fields had been used.



---

```
class JobItem {
  private int quantity;
  private int unitPrice;
  private Employee employee;
  private boolean isLabor;

  public JobItem(int quantity, int unitPrice, boolean isLabor, Employee employee) {
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.isLabor = isLabor;
    this.employee = employee;
  }
  public int getTotalPrice() {
    return quantity * getUnitPrice();
  }
  public int getQuantity() {
    return quantity;
  }
  public int getUnitPrice() {
    return (isLabor) ? employee.getRate() : unitPrice;
  }
  public Employee getEmployee() {
    return employee;
  }
}

class Employee {
  private int rate;
  public Employee(int rate) {
    this.rate = rate;
  }
  public int getRate() {
    return rate;
  }
}

// Somewhere in client code
Employee kent = new Employee(50);
JobItem j1 = new JobItem(5, 0, true, kent);
JobItem j2 = new JobItem(15, 10, false, null);
int total = j1.getTotalPrice() + j2.getTotalPrice();
```

---

```
abstract class JobItem {
  private int quantity;

  protected JobItem(int quantity) {
    this.quantity = quantity;
  }
  public int getTotalPrice() {
    return quantity * getUnitPrice();
  }
  public int getQuantity() {
    return quantity;
  }
  public abstract int getUnitPrice();
}

class PartsItem extends JobItem {
  private int unitPrice;

  public PartsItem(int quantity, int unitPrice) {
    super(quantity);
    this.unitPrice = unitPrice;
  }
  @Override public int getUnitPrice() {
    return unitPrice;
  }
}

class LaborItem extends JobItem {
  private Employee employee;

  public LaborItem(int quantity, Employee employee) {
    super(quantity);
    this.employee = employee;
  }
  public Employee getEmployee() {
    return employee;
  }
  @Override public int getUnitPrice() {
    return employee.getRate();
  }
}

class Employee {
  private int rate;
  public Employee(int rate) {
    this.rate = rate;
  }
  public int getRate() {
    return rate;
  }
}

// Somewhere in client code
Employee kent = new Employee(50);
JobItem j1 = new LaborItem(5, kent);
JobItem j2 = new PartsItem(15, 10);
int total = j1.getTotalPrice() + j2.getTotalPrice();
```

---

###### Set step 1


#|en| We start with the `JobItem` class, which tracks the time and materials used to fix a client's car in a local garage. This class is also responsible for calculating the price client should pay.

Select name of "getUnitPrice"


#|en| The price usually consists of several items. First, it's the fixed cost of certain parts. Second, it's the cost of a mechanic's time, multiplied by his rate (that can be taken directly from the `Employee` class).


#|en| So, the price is calculated in several ways, all of which sit in a single class. And that starts to smell as a *Large Class* .


#|en| As a solution, we could extract the `LaborItem` subclass and move all code, which are associated with manual work, to that subclass. Then we could leave only fixed amounts in the original class.

Go to after "JobItem"


#|en| So, let's create a subclass.

Print:
```


class LaborItem extends JobItem {
}
```

###### Set step 2

Select name of "LaborItem"


#|en| Now we should start to push down the labor-related methods.


#|en| Above all, we need a constructor because `JobItem` does not have the constructor we need, one that would accept only the employee object and number of hours spent.

Select parameters of "public JobItem"


#|en| For now, we will copy the signature of the parent constructor.

Go to the start of "LaborItem"

Print:
```

  public LaborItem(int quantity, int unitPrice, boolean isLabor, Employee employee) {
    super(quantity, unitPrice, isLabor, employee);
  }
```

Select parameters of "public LaborItem"


#|en| That is enough to make the new subclass stop displaying errors. However, this constructor is unwieldy: some arguments are necessary for `LaborItem` and others are not. We will fix this a little later.

###### Set step 3

Select 1st "new JobItem"


#|en| During the next step, we need to search for references to the `JobItem` constructor and cases when the `LaborItem` constructor should be called instead.

Print "new LaborItem"

Select "|||JobItem||| j1"


#|en| At this point we will not touch the variable type, changing only the type of the constructor.


#|en| That is because the new type should be used only where necessary. We do not yet have a specific interface for the subclass, so it is better to not declare any varieties.

Select parameters of "public JobItem"
+ Select parameters of "public LaborItem"


#|en| That is the perfect time to perform housekeeping on the lists of constructors parameters. Let's apply <a href="/remove-parameter">Remove Parameter</a> to each of them.

Select name of "public JobItem"


#|en| First, we need to refer to the parent class. We create a new constructor and declare the previous one protected (the subclass still needs it).

Select visibility of "public JobItem"

Wait 500ms

Print "protected"

Go to after "protected JobItem"

Print:
```

  public JobItem(int quantity, int unitPrice) {
    this(quantity, unitPrice, false, null);
  }
```

Select "15, 10|||, false, null|||"


#|en| External constructor calls should now use the new constructor.

Remove selected

Wait 500ms


#C|en| Let's compile and test, just in case any errors appeared.
#S Everything is OK! We can keep going.

Select "LaborItem(int quantity, |||int unitPrice, boolean isLabor, |||Employee employee)"


#|en| Now, we apply <a href="/remove-parameter">Remove Parameter</a> to the subclass constructor to get rid of unnecessary parameters.

Remove selected

Wait 500ms

Select "super(quantity, |||unitPrice|||, isLabor, employee)"

Replace "0"

Wait 500ms

Select "super(quantity, 0, |||isLabor|||, employee)"

Replace "true"

Wait 500ms

Select "new LaborItem(5, |||0, true, |||kent)"

Remove selected

Wait 500ms

###### Set step 4

Select name of "JobItem"


#|en| Subsequently we can push down parts of `JobItem` to the subclass. First look at the methods.

Select whole of "getEmployee"


#|en| Start with applying <a href="/push-down-method">Push Down Method</a> to `getEmployee`. 


Remove selected

Go to the end of "LaborItem"

Print:
```

  public Employee getEmployee() {
    return employee;
  }
```

Select "|||private||| Employee employee;"


#|en| Since the `employee` field will be pushed down to the subclass later, for now we will declare it protected.

Print "protected"

Select ", Employee employee" in "JobItem"
+ Select "|||, employee|||)"



#|en| Once the `employee` field is protected, we can clean up the constructors so that `employee` is initialized only in the subclass.

Remove selected

Select:
```
    this.employee = employee;

```

Remove selected

Go to the end of "public LaborItem"

Print:
```

    this.employee = employee;
```

###### Set step 5

Select "private boolean isLabor;"


#|en| The `isLabor` field is used to indicate information now implied by the hierarchy, so the field can be removed<br/><br/>The best way to do so is to first use <a href="/self-encapsulate-field">Self-Encapsulate Field</a> and then override the getter in subclasses so that it return own fixed value (such methods usually called "polymorphic constant method").


#|en| So we declare `isLabor` getters in both classes.

Go to the end of "JobItem"

Print:
```

  protected boolean isLabor() {
    return false;
  }
```

Go to the end of "LaborItem"

Print:
```

  protected boolean isLabor() {
    return true;
  }
```


#|en| Now replace use of the field with calls to the getters.

Select "isLabor" in "getUnitPrice"

Replace "isLabor()"

Select:
```
  private boolean isLabor;

```
+ Select ", boolean isLabor"
+ Select "|||, true|||)"
+ Select:
```
    this.isLabor = isLabor;

```


#|en| Remove the `isLabor` field.

Remove selected


#|en| After the changes, the constructors in `JobItem` are identical and, for this reason, could be put together.

Select whole "public JobItem"

Remove selected

Select visibility of "protected JobItem"

Replace "public"

Select "isLabor" in "getUnitPrice"


#|en| Now look at the uses of the `isLabor` methods. They should be refactored using <a href="/replace-conditional-with-polymorphism">Replace Conditional With Polymorphism</a>.

Select body of "getUnitPrice"

Replace "    return unitPrice;"

Wait 500ms

Go to the end of "LaborItem"

Print:
```

  public int getUnitPrice() {
    return employee.getRate();
  }
```

Select whole "isLabor" in "JobItem"
+Select whole "isLabor" in "LaborItem"


#|en| Then it becomes clear that `isLabor` methods are not used anywhere and can be safely removed from all classes.

Remove selected

Select name of "JobItem"


#|en| After pushing methods down to a subclass, you can consider moving some of the fields as well. We can apply <a href="/push-down-field">Push Down Field</a> to these fields. In some cases, this is impossible because the fields are still used in the context of superclass.


#|en| In our case, everything is ready for us to move the `employee` field to `LaborItem`.

Select:
```
  protected Employee employee;

```

Remove selected

Go to start of "LaborItem"

Print:
```

  private Employee employee;

```

Select:
```
  private int unitPrice;
```


#C|en| Compile and test, just in case any errors appeared.
#S Everything is OK! We can keep going.

#|en| So extraction of `LaborItem` is complete. But one more thing remains. Since the price of spare parts (`unitPrice`) is used only in the `JobItem` class and is not needed in `LaborItem`, we can go one step further and apply <a href="/extract-subclass">Extract Subclass</a> to `JobItem` again and create a class that represents spare parts.

Go to after "JobItem"


#|en| Create a `PartsItem` subclass and change the client code to use the constructor of the created subclass.

Print:
```


class PartsItem extends JobItem {
  public PartsItem(int quantity, int unitPrice) {
    super(quantity, unitPrice);
  }
}
```

Wait 500ms

Select "new |||JobItem|||"

Replace "PartsItem"

Wait 500ms

Select ", int |||unitPrice|||" in "JobItem"
+Select "this.unitPrice = unitPrice;" in "JobItem"
+Select name of "getUnitPrice" in "JobItem"


#|en| Before pushing down the `unitPrice` field we must first push down its initialization code, as well as the method in which it is used.

Select "|||private||| int unitPrice"


#|en| To avoid compilation errors, change the visibility of the field, making it accessible to the `PartsItem` class.

Replace "protected"

Wait 500ms

Select:
```
    this.unitPrice = unitPrice;

```


#|en| So, push down initialization code of the field.

Remove selected

Go to end of "public PartsItem"

Print:
```

    this.unitPrice = unitPrice;
```

Wait 500ms

Select ", int unitPrice" in "JobItem"

Remove selected

Wait 500ms

Select ", unitPrice" in "PartsItem"
+Select ", 0" in "LaborItem"

Wait 500ms

Remove selected

Wait 500ms

Select name of "getUnitPrice" in "JobItem"


#|en| Then push down the `getUnitPrice` method.

Select body of "getUnitPrice" in "JobItem"

Remove selected

Wait 500ms

Go to end of "PartsItem"

Print:
```

  public int getUnitPrice() {
    return unitPrice;
  }
```

Wait 500ms

Select whole "getUnitPrice" in "JobItem"


#|en| After that, declare the parent method as abstract.

Print:
```

```
Go to after "getQuantity" in "JobItem"
Print:
```

  public abstract int getUnitPrice();
```

Wait 500ms

Select "|||||| public int getUnitPrice"

Replace " @Override"

Wait 500ms

Select name of "JobItem"


#|en| And with it, a `JobItem` class.

Go to "|||class JobItem"
Print " "
Go to "||| class JobItem"
Print "abstract"

Wait 500ms

Select visibility of "public JobItem"

Replace "protected"

Wait 500ms

Select:
```
  protected int unitPrice;

```


#|en| Finally, we push down the field itself.

Remove selected

Go to start of "PartsItem"

Wait 500ms

Print:
```

  private int unitPrice;

```

Wait 500ms


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
