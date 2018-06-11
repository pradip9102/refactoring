replace-delegation-with-inheritance:java

###

1. Make the class a subclass of the delegate class.


2. Place the current object in a field containing a reference to the delegate object.


3. Delete the methods with simple delegation one by one. If their names were different, use <a href="/rename-method">Rename Method</a> to give all the methods a single name.


4. Replace all references to the delegate field with references to the current object.


5. Remove the delegate field.




###

```
class Person {
  private String name;

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getLastName() {
    return name.substring(name.lastIndexOf(' ') + 1);
  }
}

class Employee {
  protected Person person;

  public Employee() {
    this.person = new Person();
  }
  public String getName() {
    return person.getName();
  }
  public void setName(String name) {
    person.setName(name);
  }
  @Override public String toString() {
    return "Emp: " + person.getLastName();
  }
}
```

###

```
class Person {
  private String name;

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getLastName() {
    return name.substring(name.lastIndexOf(' ') + 1);
  }
}

class Employee extends Person {
  @Override public String toString() {
    return "Emp: " + getLastName();
  }
}
```

###

Set step 1


#|en| We have an `Employee` class that delegates certain work to the `Person` class.



#|en| Inheritance would be more appropriate here since the employee class needs practically all data from `Person`.



#|en| Let's start refactoring by declaring the `Employee` as a subclass of `Person`.


Go to "class Employee|||"

Print " extends Person"


#C|en| Right after that we should compile and run autotests to make sure there are no conflicting methods. These issues can arise if identically named methods return values of different types or generate different exceptions. For all such issues, use  <a href="/rename-method">Rename Method</a>.
#S No such difficulties are present in our simple example.


Set step 2

Select "new Person()"


#|en| Next, force the field, which contained reference to a `Person` object, to reference its own object. We will get rid of it later, but for now it will keep the code working.


Print "this"

Set step 3

Select whole "getName" in "Employee"
+ Select whole "setName" in "Employee"


#|en| We also should remove all simple delegate methods from `Employee`, such as `getName` and `setName`. If we forget to remove them, a stack overflow will occur due to infinite recursion.


Remove selected

Set step 4

Select "person."


#|en| Then we get rid of delegation calls by calling our object's methods.


Remove selected

Set step 5

Select:
```
  protected Person person;

  public Employee() {
    this.person = this;
  }

```


#|en| After all replacements, we can finally remove the field of the associated object and it's initialization code, which are no longer necessary.


Remove selected


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
