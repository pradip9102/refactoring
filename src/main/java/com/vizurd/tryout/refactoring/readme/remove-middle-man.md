remove-middle-man:java

###

1.en. Create a getter for accessing the <i>delegate-class</i> object from the <i>server-class</i> object.


2.en. Replace calls to delegating methods in the <i>server-class</i> with direct calls for methods in the <i>delegate-class</i>.




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

  public Department(Person arg) {
    manager = arg;
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

  public Department(Person arg) {
    manager = arg;
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

Set step 1


#|en| Let's look at <i>Remove Middle Man</i>, using the example of classes that represent an employee and the employee's department (the reverse of the situation in the <i>Hide Delegate</i> example).


Select "manager = john.getManager();"


#|en| To learn who a person's manager is, the client makes a query in the <code>Person</code> class itself.


Select body of "getManager"


#|en| This is a simple structure that encapsulates an instance of the <code>Department</code> class. But if there are many such methods, the <code>Person</code> class will have too many simple delegates. In this case, we should get rid of the middle men.


Go to before "setDepartment"


#|en| To start, let's create a method for delegate access.


Print:
```

  public Department getDepartment() {
    return department;
  }
```
Set step 2


#|en| Then, review each delegate method of <code>Person</code> and find the code that uses it. Modify the code so that it first gets the delegate class (<code>Department</code>), and then directly calls the necessary method through the delegate method.


Select name "getManager"


#|en| Here is how it will look for the method for getting the manager.


Select "john.|||getManager()|||"


#|en| First find the places where it is used.



#|en| Then change the code so that it calls delegate methods directly.


Print "getDepartment().getManager()"

Select whole "getmanager"


#|en| After all replacements are done, the <code>getManager()</code> delegate method can be removed from the <code>Person</code> class.


Remove selected


#|en| And finally: removing all delegate methods is not always necessary. It may be more convenient to maintain some delegation, so see what is best for your particular situation.



#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
