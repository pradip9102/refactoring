replace-type-code-with-subclasses:java

###

1.en. Use <a href="/self-encapsulate-field">Self Encapsulate Field</a> to create a getter for the field that contains type code.


2.en. Make the superclass constructor private. Create a static factory method with the same parameters as the superclass constructor.


3.en. Create a unique subclass for each value of the coded type. In it, redefine the getter of the coded type so that it returns the corresponding value of the coded type.


4.en. Delete the field with type code from the superclass. Make its getter abstract.


5.en. Now that you have subclasses, you can start to move the fields and methods from the superclass to corresponding subclasses


6.en. When everything movable has been moved, use <a href="/replace-conditional-with-polymorphism">Replace Conditional with Polymorphism</a> in order to get rid of conditionals that use type code once and for all.




###

```
class Employee {
  // ...
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  public int type;

  public Employee(int arg) {
    type = arg;
  }

  public int monthlySalary;
  public int commission;
  public int bonus;
  public int payAmount() {
    switch (type) {
      case ENGINEER:
        return monthlySalary;
      case SALESMAN:
        return monthlySalary + commission;
      case MANAGER:
        return monthlySalary + bonus;
      default:
        throw new RuntimeException("Incorrect Employee Code");
    }
  }
}
```

###

```
abstract class Employee {
  // ...
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  abstract public int getType();

  public static Employee create(int type) {
    switch (type) {
      case ENGINEER:
        return new Engineer();
      case SALESMAN:
        return new Salesman();
      case MANAGER:
        return new Manager();
      default:
        throw new RuntimeException("Incorrect Employee Code");
    }
  }

  public int monthlySalary;
  public int payAmount() {
    return monthlySalary;
  }
}

class Engineer extends Employee {
  @Override public int getType() {
    return Employee.ENGINEER;
  }
}

class Salesman extends Employee {
  public int commission;
  @Override public int getType() {
    return Employee.SALESMAN;
  }
  @Override public int payAmount() {
    return monthlySalary + commission;
  }
}

class Manager extends Employee {
  public int bonus;
  @Override public int getType() {
    return Employee.MANAGER;
  }
  @Override public int payAmount() {
    return monthlySalary + bonus;
  }
}
```

###

Set step 1


#|en| Let's look at <i>Replace Type Code With Subclasses</i>, using an payroll class as our example. We have several types of employees, which affects their salary values.


Select "public int |||type|||"


#|en| We start by applying <a href="/self-encapsulate-field">Self-Encapsulate Field</a> to the employee type.


Select "|||public||| int type"

Replace "private"

Go to before "public Employee"

Print:
```

  public int getType() {
    return type;
  }

```

Select "switch (|||type|||)"

Replace "getType()"

Set step 2

Select parameters of "public Employee"


#|en| Since the <code>Employee</code> constructor uses type code as a parameter, we should replace it with a factory method.


Go to before "public Employee"

Print:
```

  public static Employee create(int type) {
    return new Employee(type);
  }
```

Wait 500ms

Select "|||public||| Employee"

Replace "private"

Set step 3

Select 1st "ENGINEER"


#|en| Now we can start converting <code>Engineer</code> to a subclass. First create the subclass itself…


Go to the end of file

Print:
```


class Engineer extends Employee {
}
```

Go to the end of "Engineer"


#|en| …then create the method to replace the type code.


Print:
```

  @Override public int getType() {
    return Employee.ENGINEER;
  }
```

Select body of "create"


#|en| We need to change the factory method as well so that it creates the necessary object.


Print:
```
    switch (type) {
      case ENGINEER:
        return new Engineer();
      default:
        return new Employee(type);
    }
```

Go to the end of file


#|en| Continue these actions one by one, until all code has been replaced by subclasses.


Print:
```


class Salesman extends Employee {
  @Override public int getType() {
    return Employee.SALESMAN;
  }
}
```

Go to:
```
      case ENGINEER:
        return new Engineer();|||
```

Print:
```

      case SALESMAN:
        return new Salesman();
```

Wait 1000ms

Go to the end of file

Print:
```


class Manager extends Employee {
  @Override public int getType() {
    return Employee.MANAGER;
  }
}
```

Go to:
```
      case SALESMAN:
        return new Salesman();|||
```

Print:
```

      case MANAGER:
        return new Manager();
```

Select:
```
  private int type;


```

Set step 4


#|en| Then we can eliminate the field with type code in <code>Employee</code>…


Remove selected


Go to:
```
  |||public int getType() {
    return type;
  }
```


#|en| …and make <code>getType</code> an abstract method.


Print "abstract "

Select:
```
  abstract public int getType()||| {
    return type;
  }|||
```

Replace ";"

Go to before "Employee"


#|en| This will make the <code>Employee</code> class abstract as well.


Print "abstract "

Select:
```
      default:
        |||return new Employee(type);|||

```


#|en| After all these changes, we can no longer create <code>Employee</code> objects as the default implementation. So it is important to remember to get rid of the type field only after creating all subclasses.


Replace:
```
throw new RuntimeException("Incorrect Employee Code");
```

Select whole of "private Employee"

Remove selected

Select "switch (type) {" in "create"


#|en| Note that we ended up creating another big <code>switch</code> operator. Generally speaking this <a href="/smells/switch-statements">gives off a bad whiff</a> but once refactoring is done, it will be the only operator remaining in the code.


Set step 5

Select:
```
  public int monthlySalary;
  public int commission;
  public int bonus;
```
+ Select name of "payAmount"


#|en| After creating the subclasses, use <a href="/push-down-method">Push Down Method</a> and <a href="/push-down-field">Push Down Field</a> on all methods and fields that relate to only specific types of employees.



#|en| In our case, we will create <code>payAmount</code> methods in each of the subclasses and move payroll calculations there for the relevant types of employees.


Select:
```
  public int commission;

```

Remove selected

Go to the start of "Salesman"

Print:
```

  public int commission;
```

Select:
```
      case SALESMAN:
        return monthlySalary + commission;

```

Remove selected

Go to the end of "Salesman"

Print:
```

  @Override public int payAmount() {
    return monthlySalary + commission;
  }
```

Wait 500ms

Select:
```
  public int bonus;

```


#|en| Do the same thing with the manager class.


Remove selected

Go to the start of "Manager"

Print:
```

  public int bonus;
```

Select:
```
      case MANAGER:
        return monthlySalary + bonus;

```

Remove selected


Go to the end of "Manager"

Print:
```

  @Override public int payAmount() {
    return monthlySalary + bonus;
  }
```

Set step 6

Select body of "payAmount"


#|en| After all the code has been moved to the subclasses, you can either declare the method in the superclass abstract or else leave the default implementation there (which is what we will do).


Print:
```
    return monthlySalary;
```




#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
