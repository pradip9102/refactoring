extract-superclass:java

###

1.en. Create an abstract superclass.


2.en. Use <a href="/pull-up-field">Pull Up Field</a>, <a href="/pull-up-method">Pull Up Method</a>, and <a href="/pull-up-constructor-body">Pull Up Constructor Body</a> to move the common functionality to a superclass. Start with the fields, since in addition to the common fields you will need to move the fields that are used in the common methods.


3.en. Look for places in the client code where use of subclasses can be replaced with your new class (such as in type declarations).




###

```
class Employee {
  private String name;
  private int annualCost;
  private String id;

  public Employee(String name, String id, int annualCost) {
    this.name = name;
    this.id = id;
    this.annualCost = annualCost;
  }
  public int getAnnualCost() {
    return annualCost;
  }
  public String getId() {
    return id;
  }
  public String getName() {
    return name;
  }
}

class Department {
  private String name;
  private Vector staff = new Vector();

  public Department(String name) {
    this.name = name;
  }
  public int getTotalAnnualCost() {
    int result = 0;
    Iterator i = staff.iterator();
    while (i.hasNext()) {
      Employee each = (Employee) i.next();
      result += each.getAnnualCost();
    }
    return result;
  }
  public int getHeadCount() {
    return staff.size();
  }
  public Enumeration getStaff() {
    return staff.elements();
  }
  public void addStaff(Employee arg) {
    staff.addElement(arg);
  }
  public String getName() {
    return name;
  }
}
```

###

```
abstract class Party {
  protected String name;

  protected Party(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }
  public abstract int getAnnualCost();
  public abstract int getHeadCount();
}

class Employee extends Party {
  private int annualCost;
  private String id;

  public Employee(String name, String id, int annualCost) {
    super(name);
    this.id = id;
    this.annualCost = annualCost;
  }
  @Override public int getAnnualCost() {
    return annualCost;
  }
  public String getId() {
    return id;
  }
  @Override public int getHeadCount() {
    return 1;
  }
}

class Department extends Party {
  private Vector items = new Vector();

  public Department(String name) {
    super(name);
  }
  @Override public int getAnnualCost() {
    int result = 0;
    Iterator i = items.iterator();
    while (i.hasNext()) {
      Party each = (Party) i.next();
      result += each.getAnnualCost();
    }
    return result;
  }
  @Override public int getHeadCount() {
    int headCount = 0;
    Iterator i = items.iterator();
    while (i.hasNext()) {
      Party each = (Party) i.next();
      headCount += each.getHeadCount();
    }
    return headCount;
  }
  public Enumeration getItems() {
    return items.elements();
  }
  public void addItem(Party arg) {
    items.addElement(arg);
  }
}
```

###

Set step 1

Select name of "Employee"
+ Select name of "Department"


#|en| Let's look at <i>Extract Superclass</i> using the example of employees and their department.


Select "private String name"


#|en| These classes have several traits in common. First, as with employees, departments also have names.


Select "private int annualCost"
+ Select name of "getAnnualCost"
+ Select name of "getTotalAnnualCost"


#|en| Second, for both classes there is an annual budget, although the calculation ways are slightly different.



#|en| For this reason, it would be good to extract these aspects to a shared parent class.


Go to before "Employee"


#|en| To start, we create a new parent class, and we define the existing classes as subclasses of it.


Print:
```



```

Go to:
```
|||

class Employee
```

Print:
```
abstract class Party {
}
```

Wait 500ms

Go to "class Employee|||"

Replace " extends Party"

Wait 500ms

Go to "class Department|||"

Replace " extends Party"

Wait 500ms

Set step 2

Select:
```
  private String name;
```


#|en| Now we can start pulling up code to the parent class. Usually it is simpler to employ <a href="/pull-up-field">Pull Up Field</a> first.


Go to start of "Party"

Print:
```

  protected String name;
```

Select:
```
  private String name;

```

Remove selected

Select whole of "getName"


#|en| Then use <a href="/pull-up-method">Pull Up Method</a> on the methods for accessing the field.


Go to end of "Party"

Print:
```


  public String getName() {
    return name;
  }
```

Wait 500ms

Select in "Employee":
```

  public String getName() {
    return name;
  }
```
+ Select in "Department":
```

  public String getName() {
    return name;
  }
```

Remove selected

Select:
```
this.name = name;
```


#|en| The fields should be protected from the public, but for this we must first do <a href="/pull-up-constructor-body">Pull Up Constructor Body</a> to initialize them.


Go to before "getName" in "Party"

Print:
```

  protected Party(String name) {
    this.name = name;
  }
```


#|en| In the subclasses, we can go ahead and remove code initialization, placing parent constructor calls there instead.


Select "this.name = name" in "Employee"
+ Select "this.name = name" in "Department"

Replace "super(name)"


#|en| The name has been moved, which leaves us only the annual budget.


Select name of "getAnnualCost"
+ Select name of "getTotalAnnualCost"


#|en| The <code>getTotalAnnualCost</code> and <code>getAnnualCost</code> methods have the same purpose, so they should have the same name. Use <a href="/rename-method">Rename Method</a> to give them the same name.


Select name of "getTotalAnnualCost"

Replace "getAnnualCost"


#|en| The bodies of the methods are currently different, so we cannot use <a href="/pull-up-method">Pull Up Method</a>. On the other hand, we can declare an abstract method with the same name in the parent class.


Go to the end of "Party"

Print:
```

  public abstract int getAnnualCost();
```

Wait 500ms

Select "||| ||| public int getAnnualCost" in "Employee"
+Select "||| ||| public int getAnnualCost" in "Department"

Replace "  @Override"

Wait 500ms

Set step 3

Select name of "Party"


#|en| Having made these changes, let's look at clients of both classes to determine whether we can make them use the new parent class.


Select "|||Employee||| each = (|||Employee|||" in "Department"


#|en| One of the clients of the classes is the <code>Department</code> class itself, which contains a collection of employee classes. The <code>getAnnualCost</code> method uses only the annual budget calculation method, which is now declared in <code>Party</code>.


Print "Party"

Select name of "Department"


#|en| This behavior offers a new opportunity. We can consider using the <a href="https://refactoring.guru/design-patterns/composite">Composite</a> pattern on <code>Department</code> and <code>Employee</code>.



#|en| That allows including one department in another. The result is new functionality, so strictly speaking this goes beyond refactoring.


Select "Vector" in "Department"


#|en| Be that as it may, if the Composite pattern were necessary, we would get it by changing the type of the <code>staff</code> field.


Select "|||staff||| =" in "Department"
+Select "|||staff|||." in "Department"


#|en| After that, it would be good to give the list a more generic name.


Replace "items"

Wait 500ms

Select name of "getStaff" in "Department"
+Select name of "addStaff" in "Department"


#|en| And appropriately edit the <code>getStaff</code> and <code>addStaff</code> methods.


Select name of "getStaff" in "Department"

Replace "getItems"

Wait 500ms

Select name of "addStaff" in "Department"

Replace "addItem"

Wait 500ms

Select "Employee" in "Department"

Print "Party"

Wait 500ms

Select body of "getHeadCount"


#|en| To complete the Composite pattern, the <code>getHeadCount</code> method should be made recursive.


Print:
```
    int headCount = 0;
    Iterator i = items.iterator();
    while (i.hasNext()) {
      Party each = (Party) i.next();
      headCount += each.getHeadCount();
    }
    return headCount;
```


#|en| But for this approach to work, we must create an equivalent method in <code>Employee</code> that simply returns <code>1</code>.


Go to the end of "Employee"

Print:
```

  public int getHeadCount() {
    return 1;
  }
```

Go to the end of "Party"


#|en| After that this method should also be declared abstract in the parent class.


Print:
```

  public abstract int getHeadCount();
```

Wait 500ms

Select "||| ||| public int getHeadCount" in "Employee"
+Select "||| ||| public int getHeadCount" in "Department"

Replace "  @Override"

Wait 500ms


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
