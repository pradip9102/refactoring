introduce-local-extension:java

###


1. Create a new extension class and make it the inheritor of the utility class.




2. Create a constructor that uses the parameters of the constructor of the utility class.




3. Create an alternative "converting" constructor that accepts only an object of the original class in its parameters.



4. Create new extended methods in the class. Move foreign methods from other classes to this class or else delete the foreign methods if their functionality is already present in the extension.



5. Replace use of the utility class with the new extension class in places where its functionality is needed.





###

```
class Account {
  // ...
  double schedulePayment() {
    Date paymentDate = nextWeek(previousDate);

    // Issue a payment using paymentDate.
    // ...
  }

  /**
   * Foreign method. Should be on Date class.
   */
  private static Date nextWeek(Date arg) {
    return new Date(arg.getYear(), arg.getMonth(), arg.getDate() + 7);
  }
}
```

###

```
class Account {
  // ...
  double schedulePayment() {
    Date paymentDate = new MfDateSub(previousDate).nextWeek();

    // Issue a payment using paymentDate.
    // ...
  }
}

// Local extension class.
class MfDateSub extends Date {
  public MfDateSub(String dateString) {
    super(dateString);
  }
  public MfDateSub(Date arg) {
    super(arg.getTime());
  }
  public Date nextWeek() {
    return new Date(getYear(), getMonth(), getDate() + 7);
  }
}
```

###

Set step 1


#|en| *Introduction of a local extension*  can be performed in two ways: by creating either subclass or a wrapper class. In this example, we will use inheritance.



#|en| First, we create a new subclass of the original `Date` class.


Go to the end of file

Print:
```


// Local extension class.
class MfDateSub extends Date {
}
```

Set step 2

Go to the start of "MfDateSub"


#|en| Then repeat the original's constructors via simple delegation.


Print:
```

  public MfDateSub(String dateString) {
    super(dateString);
  }
```

Set step 3


#|en| Now we should add a converting constructor that accepts the original as an argument.


Go to the end of "MfDateSub"

Print:
```

  public MfDateSub(Date arg) {
    super(arg.getTime());
  }
```

Set step 4

Select whole "nextWeek"


#|en| When the class constructors are ready, you can add new methods to it or move foreign methods form other classes. Let's move the `nextWeek()` method with the help of <a href="/move-method">Move Method</a>.


Go to the end of "MfDateSub"

Print:
```

  private static Date nextWeek(Date arg) {
    return new Date(arg.getYear(), arg.getMonth(), arg.getDate() + 7);
  }
```

Select parameters of "nextWeek" in "MfDateSub"


#|en| The method parameter is no longer needed since the method is inside the `Date` subclass. Thus, the needed data can be taken from its own object.


Remove selected

Select "arg." in "nextWeek" in "MfDateSub"

Remove selected

Select "|||private static||| Date nextWeek" in "MfDateSub"


#|en| In addition, the method stops being static and private – after all, we need to be able to call it from other classes.


Replace "public"

Wait 500ms

Set step 5

Select "nextWeek(previousDate)"


#|en| Now we replace all usages of the old foreign method with our new extension class.


Print "new MfDateSub(previousDate).nextWeek()"

Select whole "nextWeek" in "Account"
+ Select:
```

  /**
   * Foreign method. Should be on Date class.
   */

```

#|en| After all changes are complete, we remove the external method from the client class.


Remove selected


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
