replace-constructor-with-factory-method:java

###

1. Create a factory method. Place a call to the current constructor in it.

2. Replace all constructor calls with calls to the factory method.

3. Declare the constructor private.

4. Investigate the constructor code and try to isolate the code not directly related to constructing an object of the current class, moving such code to the factory method.



###

```
class Employee {
  // ...
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  public Employee(int type) {
    this.type = type;
  }
}

// Some clinet code.
Employee eng = new Employee(Employee.ENGINEER);
```

###

```
class Employee {
  // ...
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  public static Employee create(int type) {
    switch (type) {
      case ENGINEER:
        return new Engineer();
      case SALESMAN:
        return new Salesman();
      case MANAGER:
        return new Manager();
      default:
        return new Employee(type);
    }
  }
  private Employee(int type) {
    this.type = type;
  }
}
class Engineer extends Employee {
  // ...
}
class Salesman extends Employee {
  // ...
}
class Manager extends Employee {
  // ...
}

// Some clinet code.
Employee eng = Employee.create(Employee.ENGINEER);
```

###

###### Set step 1


#|en| Say, we have a class for creating employees…


Select parameters in "public Employee"


#|en|< …in which the employee type is set via constructor's parameter.

Select "new Employee"


#|en| The client code calls the constructor directly.


#|en| So what if we wanted to create subclasses for each type of employee?


Go to after "Employee"

Print:
```

class Engineer extends Employee {
  // ...
}
class Salesman extends Employee {
  // ...
}
class Manager extends Employee {
  // ...
}
```

Select "new Employee"


#|en| We would have to rewrite it, since we cannot return anything from the `Employee` constructor other than `Employee` objects (and we need `Engineer`).


#|en| And if something changes again later, we will have to create even more subclasses and may well have to adjust the constructor calls… again.


#|en| The alternative is to create a <b>factory method</b> – a special static method that returns objects of different classes depending on particular parameters.

Go to before "public Employee"


#|en| The `Employee` class is the best place to store the factory method because it will probably survive any changes in the subclasses.

Print:
```

  public static Employee create(int type) {
    return new Employee(type);
  }
```


#|en| At this stage, the factory method will call the current constructor but we will soon change this.

###### Set step 2

Select "eng = |||new Employee|||"


#|en| Now find all direct calls to the constructors and replace them with calls to the factory method.

Print "Employee.create"

###### Set step 3

Select visibility of "public Employee"


#|en| Once the changes are complete, you can hide the constructor from outside eyes by making it private.

Print "private"

###### Set step 4

Select body of "create"


#|en| In addition, you can create a conditional in the factory method to return an object of the necessary class depending on the parameter passed.

Print:
```
    switch (type) {
      case ENGINEER:
        return new Engineer();
      case SALESMAN:
        return new Salesman();
      case MANAGER:
        return new Manager();
      default:
        return new Employee(type);
    }
```


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
