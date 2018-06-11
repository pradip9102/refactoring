introduce-foreign-method:java

---

1. Create a new method in the client class.

2. In this method, create a parameter to which the object of the utility class will be passed. If this object can be obtained from the client class, you do not have to create such a parameter.

3. Extract the relevant code fragments to this method and replace them with method calls.

4. Leave a comment for the method, labeling it as *Foreign method*  and requesting that the method be placed in a utility class if this becomes possible in the future.



---

```
class Account {
  // ...
  double schedulePayment() {
    Date paymentDate = new Date(previousDate.getYear(), previousDate.getMonth(), previousDate.getDate() + 7);

    // Issue a payment using paymentDate.
    // ...
  }
}
```

---

```
class Account {
  // ...
  double schedulePayment() {
    Date paymentDate = nextWeek(previousDate);

    // Issue a payment using paymentDate.
    // ...
  }

  /**
   * Foreign method. Should be in the Date class.
   */
  public static Date nextWeek(Date arg) {
    return new Date(arg.getYear(), arg.getMonth(), arg.getDate() + 7);
  }
}
```

---

###### Set step 1


#|en| Let's look at *Introduce Foreign Method*  using the example of a bank account class.

Select "Date paymentDate = new Date(previousDate.getYear(), previousDate.getMonth(), previousDate.getDate() + 7)"


#|en| This class has code that opens a new billing period one week in the future from the current time.


#|en| Ideally, the `Date` class would have a method for getting a date seven days in the future (something resembling `previousDate.nextWeek()`) but it does not, and, what's pretty sad, it is standard so we cannot change it.

Go to the end of "Account"


#|en| What we can do though is create a "foreign" method in its own class.

Print:
```


  public Date nextWeek() {
    return new Date(previousDate.getYear(), previousDate.getMonth(), previousDate.getDate() + 7);
  }
```

###### Set step 2

Go to parameters of "nextWeek"


#|en| To make the method more universal, we will add a parameter of the `Date` class to it. Essentially, we are extending the functionality of the object passed in this parameter.

Print "Date arg"

Select "previousDate" in "nextWeek"

Replace "arg"

Go to type of "nextWeek"


#|en| You should also declare the method static to make it accessible from other code not associated with `Account`.

Print "static "

###### Set step 3

Select "new Date(previousDate.getYear(), previousDate.getMonth(), previousDate.getDate() + 7)"


#|en| The method can now be used in the other code.

Print "nextWeek(previousDate)"

###### Set step 4

Go to before "nextWeek"


#|en| For the finishing touch, let's add a comment to the "foreign method" about its purpose and our intentions. That will help to avoid potential confusion regarding its use. And if a new class is created in the program later for storing additional date-related functions, this method can be easily found and moved to a better place.

Print:
```

  /**
   * Foreign method. Should be in the Date class.
   */
```


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
