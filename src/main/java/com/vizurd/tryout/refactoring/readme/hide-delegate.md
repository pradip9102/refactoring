hide-delegate:java

###

1. For each *delegate class*  method that is called in the code, create a method in the *server class*  that delegates calls to the *delegate class* .


2. Change the client code so that it calls the methods of the *server-class* .


3. If after all these changes you no longer need direct access to the *delegate class* , you can proceed to remove access to the *delegate class*  from the *server class* . 




###

```
class Person {
  private Department department;

  public Department getDepartment() {
    return department;
  }
  public void setDepartment(Department arg) {
    department = arg;
  }
}

class Department {
  private String chargeCode;
  private Person manager;

  public Department(Person manager) {
    this.manager = manager;
  }
  public Person getManager() {
    return manager;
  }

  //…
}

// Somewhere in client code
manager = john.getDepartment().getManager();
```

###

```
class Person {
  private Department department;

  public void setDepartment(Department arg) {
    department = arg;
  }
  public Person getManager() {
    return department.getManager();
  }
}

class Department {
  private String chargeCode;
  private Person manager;

  public Department(Person manager) {
    this.manager = manager;
  }
  public Person getManager() {
    return manager;
  }

  //…
}

// Somewhere in client code
manager = john.getManager();
```

###

Set step 1


#|en| Let's look at *Hide Delegate*  using the classes representing an employee and the employee's department as an example.


Select "manager = john.getDepartment().getManager();"


#|en| If the client needs to know the manager of a certain employee, the client must first know in which department that person works.



#|en| That way, along with the manager value, client code gets full access to the `Department` object and its other fields. If that doesn't look very safe to you… you're right.


Set step 2

Go to the end of "Person"


#|en| This association can be reduced by hiding the `Department` class from the client, by creating a simple delegate method in `Person`.


Print:
```

  public Person getManager() {
    return department.getManager();
  }
```

Set step 3

Select "john.getDepartment().getManager();"


#|en| Now let's modify the code so that it uses the new method.


Print "john.getManager();"

Select whole "getDepartment"


#|en| Once all necessary methods have been delegated, we can remove the method in the `Person` class that provided access to the `Department` instance.


Remove selected


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
