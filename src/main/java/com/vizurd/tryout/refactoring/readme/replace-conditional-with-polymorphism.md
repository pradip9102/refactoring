replace-conditional-with-polymorphism:java

###

1. If the conditional is in a method that performs other actions as well, perform <a href="/extract-method">Extract Method</a>.


2. For each hierarchy subclass, redefine the method that contains the conditional and copy the code of the corresponding conditional branch to that location.


3. Delete this branch from the conditional.


4. Repeat replacement until the conditional is empty. Then delete the conditional and declare the method abstract.




###

```
class Employee {
  // ...
  private EmployeeType type;
  public int getTypeCode() {
    return type.getTypeCode();
  }

  public int monthlySalary;
  public int commission;
  public int bonus;
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
}
class Engineer extends EmployeeType {
  @Override public int getTypeCode() {
    return EmployeeType.ENGINEER;
  }
}
class Salesman extends EmployeeType {
  @Override public int getTypeCode() {
    return EmployeeType.SALESMAN;
  }
}
class Manager extends EmployeeType {
  @Override public int getTypeCode() {
    return EmployeeType.MANAGER;
  }
}
```

###

```
class Employee {
  // ...
  private EmployeeType type;
  public int getTypeCode() {
    return type.getTypeCode();
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


#|en| Let's take a look at this refactoring in the context of code for calculating payroll for different types of employees (see <a href="/replace-type-code-with-state-strategy">Replace Type Code with State/Strategy</a>).



Select body of "payAmount"


#|en| See that big conditional inside the `payAmount()` method? Let's try to get rid of it.



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
