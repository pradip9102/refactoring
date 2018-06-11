replace-subclass-with-fields:java

###

1. Apply <a href="/replace-constructor-with-factory-method">Replace Constructor with Factory Method</a> to the subclasses.

2. Replace subclass constructor calls with superclass factory method calls.

3. In the superclass, declare fields for storing the values of each of the subclass methods that return constant values.

4. Create a protected superclass constructor for initializing the new fields.

5. Create or modify the existing subclass constructors so that they call the new superclass constructor.

6. Implement each constant method in the parent class so that it returns the value of the corresponding field. Then remove the method from the subclass.

7. If the subclass constructor has additional functionality, use <a href="/inline-method">Inline Method</a> to incorporate the constructor into the superclass factory method.

8. Delete the subclass.



###

```
abstract class Person {
  abstract boolean isMale();
  abstract char getCode();
}

class Male extends Person {
  @Override boolean isMale() {
    return true;
  }
  @Override char getCode() {
    return 'M';
  }
}
class Female extends Person {
  @Override boolean isMale() {
    return false;
  }
  @Override char getCode() {
    return 'F';
  }
}

// Client code
Person kent = new Male();
System.out.print("Person's gender is: " + kent.getCode());
```

###

```
class Person {
  static Person createMale() {
    return new Person(true, 'M');
  }
  static Person createFemale() {
    return new Person(false, 'F');
  }
  protected Person(boolean isMale, char code) {
    this.isMale = isMale;
    this.code = code;
  }

  private final boolean isMale;
  private final char code;

  boolean isMale() {
    return isMale;
  }
  char getCode() {
    return code;
  }
}

// Client code
Person kent = Person.createMale();
System.out.print("Person's gender is: " + kent.getCode());
```

###

###### Set step 1


#|en| Let's look at *Replace Subclass With Fields* , using the example of the same person class and its gender subclasses.

Select "return true"
+ Select "return false"
+ Select "return 'M'"
+ Select "return 'F'"


#|en| The only difference between the subclasses is that they have implementations of the abstract methods that return hard-coded constants. It is preferable to get rid of such classes.

Go to the beginning of "Person"


#|en| First use <a href="/replace-constructor-with-factory-method">Replace Constructor With Factory Method</a>. In our case, we need the factory method for each subclass.

Print:
```

  static Person createMale() {
    return new Male();
  }
  static Person createFemale() {
    return new Female();
  }
```

###### Set step 2

Select "Person kent = |||new Male()|||"


#|en| Then replace all calls to subclass constructors with calls to the relevant factory methods.

Print "Person.createMale()"


#|en| After replacing all these calls, the code should not contain any more mentions of the subclasses.

###### Set step 3

Go to after "createFemale"


#|en| Now, in the parent class, we should declare fields for each method that returns constants in subclasses.

Print:
```


  private final boolean isMale;
  private final char code;

```

###### Set step 4

Go to after "createFemale"


#|en| Add a protected constructor to the parent class.

Print:
```

  protected Person(boolean isMale, char code) {
    this.isMale = isMale;
    this.code = code;
  }
```

###### Set step 5

Go to the start of "Male"


#|en| Add constructors that call this new constructor in subclasses.

Print:
```

  Male() {
    super(true, 'M');
  }
```

Go to the start of "Female"

Print:
```

  Female() {
    super(false, 'F');
  }
```


#C|en| Then we can compile and test.
#S Everything is OK! We can keep going.

###### Set step 6

Select "  abstract boolean isMale();"


#|en| The fields are created and initialized, but are not yet used. Now we can get the fields "in the game" by placing access methods in the parent class and removing subclass methods.

Print:
```
  boolean isMale() {
    return isMale;
  }
```

Wait 500ms

Select:
```
  @Override boolean isMale() {
    return true;
  }

```

Remove selected

Wait 500ms

Select:
```
  @Override boolean isMale() {
    return false;
  }

```

Remove selected

Select "  abstract char getCode();"

Replace:
```
  char getCode() {
    return code;
  }
```

Wait 500ms

Select:
```
  @Override char getCode() {
    return 'M';
  }

```

Remove selected

Wait 500ms

Select:
```
  @Override char getCode() {
    return 'F';
  }

```

Remove selected

###### Set step 7

Select "|||abstract||| class Person"
+ Select "new Male()"
+ Select "new Female()"


#|en| All subclasses are empty at this point. That allows us to remove the "abstract" keyword from the Person class and use its constructor instead the ones from subclasses (that we could simply remove).

Select "|||abstract |||class Person"

Remove selected

Wait 500ms

Select "new Male()"

Replace "new Person(true, 'M')"

###### Set step 8

Select whole "Male"


#|en| The `Male` class should now be removed.

Remove selected


#C|en| Compile and test to make sure nothing has been broken by mistake.
#S Looking good! Let's do the same with the `Female` class.


Select "new Female()"

Replace "new Person(false, 'F')"

Wait 500ms

Select whole "Female"
+Select:
```
|||
|||// Client code
```

Remove selected


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
