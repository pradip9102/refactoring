replace-method-with-method-object:java

###

1.en. Create a new class. Name it based on the purpose of the method that you are refactoring.



2.en. In the new class, create a private field for storing a reference to an instance of the class in which the method was previously located.



3.en. In addition, create a private field for each local variable and parameter of the method.



4.en. Create a constructor that accepts all parameters of the original method and initializes the relevant private fields.



5.en. Declare the main method and copy the code of the original method to it, replacing the local variables with private fields.



6.en. Replace the body of the original method in the original class by creating a method object and calling its main method.





###

```
class Account {
  // ...
  int gamma(int inputVal, int quantity, int yearToDate) {
    int importantValue1 = (inputVal * quantity) + delta();
    int importantValue2 = (inputVal * yearToDate) + 100;
    if ((yearToDate - importantValue1) > 100) {
      importantValue2 -= 20;
    }
    int importantValue3 = importantValue2 * 7;
    // and so on…
    return importantValue3 - 2 * importantValue1;
  }
  // ...
}
```

###

```
class Account {
  // ...
  int gamma(int inputVal, int quantity, int yearToDate) {
    return new Gamma(this, inputVal, quantity, yearToDate).compute();
  }
  // ...
}

class Gamma {
  private final Account account;
  private int importantValue1;
  private int importantValue2;
  private int importantValue3;
  private int inputVal;
  private int quantity;
  private int yearToDate;
  public Gamma(Account source, int inputValArg, int quantityArg, int yearToDateArg) {
    this.account = source;
    inputVal = inputValArg;
    quantity = quantityArg;
    yearToDate = yearToDateArg;
  }
  public int compute() {
    importantValue1 = (inputVal * quantity) + account.delta();
    importantValue2 = (inputVal * yearToDate) + 100;
    importantThing();
    importantValue3 = importantValue2 * 7;
    // and so on…
    return importantValue3 - 2 * importantValue1;
  }
  void importantThing() {
    if ((yearToDate - importantValue1) > 100) {
      importantValue2 -= 20;
    }
  }
}
```

###

Set step 1


#|en| A thorough example would require an entire chapter, so we will demonstrate this refactoring on a method that does not require it (for this reason, it's better not to question the logic of the method – it was devised without any grand plan in mind).


Select name of "gamma"


#|en| We see that one of the class methods has many sophisticated calculations and entanglement of local variables. All this makes it hard to refactor the class.



#|en| Let's convert this method to a separate class so that the local variables become fields of the class. That will isolate it and ease the further refactoring.


Go to the end of file


#|en| So, let's create a new class.


Print:
```


class Gamma {
}
```

Set step 2


#|en| First, create an immutable field for storing the source object, in the <code>Gamma</code> class.


Go to the end of "Gamma"

Print:
```

  private final Account account;
```

Set step 3


Select 1st "importantValue1"
+Select 1st "importantValue2"
+Select 1st "importantValue3"


#|en| Move all variables from the method that we want to separate…


Go to the end of "Gamma"
Print:
```

  private int importantValue1;
  private int importantValue2;
  private int importantValue3;
```

Select "gamma(int inputVal, int quantity, int yearToDate)"


#|en| …and create fields for each of the method's parameters.


Go to the end of "Gamma"

Print:
```

  private int inputVal;
  private int quantity;
  private int yearToDate;
```

Set step 4

Go to the end of "Gamma"


#|en| Create a constructor that will accept the method's parameters and store them in class fields for further use.


Print:
```

  public Gamma(Account source, int inputValArg, int quantityArg, int yearToDateArg) {
    this.account = source;
    inputVal = inputValArg;
    quantity = quantityArg;
    yearToDate = yearToDateArg;
  }
```


#C|en| Let's compile and test to check for errors in your code.
#S All is well, nothing has managed to break yet.


Set step 5

Select whole "gamma" in "Account"


#|en| Now you can move the original method.


Go to the end of "Gamma"

Print:
```

  public int compute() {
    int importantValue1 = (inputVal * quantity) + delta();
    int importantValue2 = (inputVal * yearToDate) + 100;
    if ((yearToDate - importantValue1) > 100) {
      importantValue2 -= 20;
    }
    int importantValue3 = importantValue2 * 7;
    // and so on…
    return importantValue3 - 2 * importantValue1;
  }
```

Select "int " in body of "compute"


#|en| It's time to replace local variables with fields.


Remove selected

Select "delta()" in "compute"


#|en| Modify any calls to the <code>Account</code> methods so that they are run via the <code>account</code> field.


Print "account.delta()"

Set step 6

Select body of "int gamma"


#|en| Then simply replace the body of the old method with a call to the method in the new class.


Print:
```
    return new Gamma(this, inputVal, quantity, yearToDate).compute();
```


#C|en| Let's compile and test to check for errors in your code.
#S Everything is working great!


Select:
```
    if ((yearToDate - importantValue1) > 100) {
      importantValue2 -= 20;
    }
```


#|en| The benefit of this refactoring is that you can now easily apply <a href="/extract-method">Extract Method</a> to the <code>compute()</code> method without worrying about passing correct arguments between sub-methods.


Go to the end of "Gamma"

Print:
```

  void importantThing() {
    if ((yearToDate - importantValue1) > 100) {
      importantValue2 -= 20;
    }
  }
```

Wait 500ms

Select in "compute":
```
    if ((yearToDate - importantValue1) > 100) {
      importantValue2 -= 20;
    }
```

Replace "    importantThing();"

Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
