pull-up-constructor-body:java

###

1.en. Create a constructor in a superclass.


2.en. Extract the common code from the beginning of the constructor of each subclass to the superclass constructor. Before doing so, try to move as much common code as possible to the beginning of the constructor.


3.en. Place the call for the superclass constructor in the first line in the subclass constructors.




###

```
class Employee {
  // ...
  protected String name;
  protected String id;
}
   
class Manager extends Employee {
  // ...
  private int grade;
  public Manager(String name, String id, int grade) {
    this.name = name;
    this.id = id;
    this.grade = grade;
  }
}
```

###

```
class Employee {
  // ...
  protected String name;
  protected String id;
  protected Employee(String name, String id) {
    this.name = name;
    this.id = id;
  }
}
   
class Manager extends Employee {
  // ...
  private int grade;
  public Manager(String name, String id, int grade) {
    super(name, id);
    this.grade = grade;
  }
}
```

###

Set step 1


#|en| Let's look at <i>Pull Up Constructor Body</i> using manager and employee classes. In this case, <code>Employee</code> does not have any constructor and its fields are filled in the <code>Manager</code> class, which is actually used in the program.



#|en| So if we want to create another <code>Employee</code> subclass, we must duplicate parts of the <code>Manager</code> constructor in order to initialize the <code>Employee</code> fields.



#|en| Instead, we can pull up part of the body of the <code>Manager</code> constructor to its superclass.


Go to the end of "Employee"


#|en| Let's define the constructor in the <code>Employee</code> class and make it protected. That will work as default implementation and let subclasses call it inside their own constructors.


Print:
```

  protected Employee() {
  }
```

Set step 2

Select "String name" in "Manager"
+Select "String id" in "Manager"
+Select "this.name = name;" in "Manager"
+Select "this.id = id;" in "Manager"


#|en| Move the code to initialize the fields in the superclass to the created constructor.


Go to parameters of "protected Employee"

Print "String name, String id"

Wait 500ms

Select in "Manager":
```
    this.name = name;
    this.id = id;

```

Remove selected

Wait 500ms

Go to start of "protected Employee"

Print:
```

    this.name = name;
    this.id = id;
```

Set step 3

Select name of "protected Employee" 


#|en| At this point, the new constructor can be called inside <code>Manager</code> constructor as <code>super</code>.


Go to start of "public Manager"

Print:
```

    super(name, id);
```


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
