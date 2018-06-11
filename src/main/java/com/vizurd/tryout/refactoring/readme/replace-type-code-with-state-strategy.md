replace-type-code-with-state-strategy:java

###

1. Use <a href="/self-encapsulate-field">Self Encapsulate Field</a> to create a getter for the field that contains type code.


2. Create a new class that will play the role of *state*  (or *strategy* ). Create an abstract getter of the coded field in it.


3. Create state subclasses for each value of the coded type.


4. In the abstract state class, create a static factory method that accepts the value of the coded type as a parameter. Depending on this parameter, the factory method will create objects of various states. For this, in its code create a large conditional; it will be the only one when refactoring is complete.


5. In the original class, change the type of the coded field to the state class. In the field's setter, call the factory state method for getting new state objects.


6. Move the fields and methods from the superclass to the corresponding state subclasses.


7. When everything movable has been moved, use <a href="/replace-conditional-with-polymorphism">Replace Conditional with Polymorphism</a> in order to get rid of conditionals that use type code once and for all.




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
class Employee {
  // ...
  private EmployeeType type;

  public Employee(int arg) {
    type = EmployeeType.newType(arg);
  }
  public int getTypeCode() {
    return type.getTypeCode();
  }
  public void setTypeCode(int arg) {
    type = EmployeeType.newType(arg);
  }

  public int monthlySalary;
  public int commission;
  public int bonus;
  public int payAmount() {
    return type.payAmount(this);
  }
}

abstract class EmployeeType {
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  abstract public int getTypeCode();
  public static EmployeeType newType(int code) {
    switch (code) {
      case ENGINEER:
        return new Engineer();
      case SALESMAN:
        return new Salesman();
      case MANAGER:
        return new Manager();
      default:
        throw new IllegalArgumentException("Incorrect Employee Code");
    }
  }

  abstract public int payAmount(Employee employee);
}
class Engineer extends EmployeeType {
  @Override public int getTypeCode() {
    return EmployeeType.ENGINEER;
  }
  @Override public int payAmount(Employee employee) {
    return employee.monthlySalary;
  }
}
class Salesman extends EmployeeType {
  @Override public int getTypeCode() {
    return EmployeeType.SALESMAN;
  }
  @Override public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.commission;
  }
}
class Manager extends EmployeeType {
  @Override public int getTypeCode() {
    return EmployeeType.MANAGER;
  }
  @Override public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.bonus;
  }
}
```

###

Set step 1


#|en| Let's look at *Replace Type Code with State/Strategy*  in the context of the payroll class considered earlier. We have several types of employees; these types are used to calculate the salary amount for each particular employee.


Select "public int |||type|||"


#|en| Let's start by applying <a href="/self-encapsulate-field">Self-Encapsulate Field</a> to the employee type.


Select "|||public||| int type"

Replace "private"

Go to after "public Employee"

Print:
```

  public int getType() {
    return type;
  }
  public void setType(int arg) {
    type = arg;
  }
```

Wait 500ms

Select "switch (|||type|||) {"

Replace "getType()"


Select whole "setType"


#|en| We assume that the company is progressive and enlightened and so allows its managers to ascend to engineers. So the type code can be changed and using subclasses to eliminate type coding is not possible. This causes us to use the <a href="https://refactoring.guru/design-patterns/state">State</a> pattern.


Set step 2

Go to the end of file


#|en| Declare the state class (as an abstract class with an abstract method for returning type code).


Print:
```


abstract class EmployeeType {
  abstract public int getTypeCode();
}
```

Set step 3


#|en| Now create subclasses for each type of employee.



Print:
```

class Engineer extends EmployeeType {
  @Override public int getTypeCode() {
    return Employee.ENGINEER;
  }
}
class Salesman extends EmployeeType {
  @Override public int getTypeCode() {
    return Employee.SALESMAN;
  }
}
class Manager extends EmployeeType {
  @Override public int getTypeCode() {
    return Employee.MANAGER;
  }
}
```

Set step 4

Go to the end of "EmployeeType"


#|en| Create a static method in the state class. It will return an instance of the necessary subclass, depending on the value accepted.


Print:
```

  public static EmployeeType newType(int code) {
    switch (code) {
      case Employee.ENGINEER:
        return new Engineer();
      case Employee.SALESMAN:
        return new Salesman();
      case Employee.MANAGER:
        return new Manager();
      default:
        throw new IllegalArgumentException("Incorrect Employee Code");
    }
  }
```

Select "switch (code)"


#|en| As you can see, here we are introducing a large `switch` operator. That's not great news, but once we are done with refactoring, this operator will be the only one in the code and will be run only when a type is changed.



#C|en| Let's compile and test to check for errors in the code.
#S Everything is OK! We can keep going.


Set step 5

Select "private |||int||| type"


#|en| Now we need to connect the created subclasses to `Employee` by modifying the access methods for the type code and constructor.


Print "EmployeeType"

Wait 500ms

Select:
```
  public int getType() {
    return |||type|||;
  }
```

Replace "type.getTypeCode()"

Wait 500ms

Select:
```
    type = |||arg|||;
```



#|en| The setter body and constructor are replaced with a call to the factory method.


Print "EmployeeType.newType(arg)"

Select name of "setType"
+ Select name of "getType"


#|en| Since access methods now return a code, not the type object itself, we should rename them to make things clear to future readers.


Select "setType("

Replace "setTypeCode("

Select "getType("

Replace "getTypeCode("



Select:
```

  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

```


#|en| We complete this step by moving all type code constants from `Employee` to `EmployeeType`.


Remove selected

Go to the beginning of "EmployeeType"

Print:
```

  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

```

Wait 500ms

Select "Employee." in "newType"

Remove selected

Wait 500ms

Select:
```
      case||| |||ENGINEER:
        return monthlySalary;
      case||| |||SALESMAN:
        return monthlySalary + commission;
      case||| |||MANAGER:
```

Replace " EmployeeType."

Wait 500ms

Select "|||Employee|||.ENGINEER" in "Engineer"
+ Select "|||Employee|||.MANAGER" in "Manager"
+ Select "|||Employee|||.SALESMAN" in "Salesman"

Wait 500ms

Type "EmployeeType"

Set step 6


#|en| Everything is now ready for applying <a href="/replace-conditional-with-polymorphism">Replace Conditional With Polymorphism</a>.


Select body of "payAmount"


#|en| First extract the implementation of `payAmount` to a new method in a type class.


Go to the end of "EmployeeType"

Print:
```


  public int payAmount() {
    switch (getTypeCode()) {
      case EmployeeType.ENGINEER:
        return monthlySalary;
      case EmployeeType.SALESMAN:
        return monthlySalary + commission;
      case EmployeeType.MANAGER:
        return monthlySalary + bonus;
      default:
        throw new RuntimeException("Incorrect Employee Code");
    }
  }
```

Select "monthlySalary" in "EmployeeType"
+Select "commission" in "EmployeeType"
+Select "bonus" in "EmployeeType"


#|en| We need datа from the `Employee` object, so in the method we create the parameter to which the main `Employee` object will be passed.


Go to "payAmount(|||) {" in "EmployeeType"

Print "Employee employee"

Select "monthlySalary" in "EmployeeType"

Replace "employee.monthlySalary"

Select "commission" in "EmployeeType"

Replace "employee.commission"

Select "bonus" in "EmployeeType"

Replace "employee.bonus"

Select body of "payAmount"


#|en| After these actions, we can set up delegation from the `Employee` class.


Print "    return type.payAmount(this);"


#|en| Then start moving code to subclasses. Create `payAmount` methods in each of the subclasses and move payroll calculations there for the relevant employee types.


Go to the end of "class Engineer"

Print:
```

  @Override public int payAmount(Employee employee) {
    return employee.monthlySalary;
  }
```

Wait 1000ms

Go to the end of "class Salesman"

Print:
```

  @Override public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.commission;
  }
```

Wait 1000ms


Go to the end of "class Manager"

Print:
```

  @Override public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.bonus;
  }
```

Set step 7

Select name of "payAmount" in "EmployeeType"


#|en| Now that the methods have been created, you can make the `payAmount` method in `EmployeeType`  abstract.


Select:
```
  public int payAmount(Employee employee) {
    switch (getTypeCode()) {
      case EmployeeType.ENGINEER:
        return employee.monthlySalary;
      case EmployeeType.SALESMAN:
        return employee.monthlySalary + employee.commission;
      case EmployeeType.MANAGER:
        return employee.monthlySalary + employee.bonus;
      default:
        throw new RuntimeException("Incorrect Employee Code");
    }
  }
```

Replace:
```
  abstract public int payAmount(Employee employee);
```



#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
