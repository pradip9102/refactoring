move-method:java

---

1. Check all features used by the old method in its class. It may be a good idea to move them as well.

2. Check whether the method has been declared in superclasses and subclasses. If not, declare a new method in the recipient class and move the code of the old method to it.

3. Replace the body of the old method with delegation to the new method.

4. Check whether you can get rid of the old method entirely.



---

```
class Account {
  // ...
  private AccountType type;
  private int daysOverdrawn;

  public double overdraftCharge() {
    if (type.isPremium()) {
      double result = 10;
      if (daysOverdrawn > 7) {
        result += (daysOverdrawn - 7) * 0.85;
      }
      return result;
    }
    else {
      return daysOverdrawn * 1.75;
    }
  }
  public double bankCharge() {
    double result = 4.5;
    if (daysOverdrawn > 0) {
      result += overdraftCharge();
    }
    return result;
  }
}

class AccountType {
  // ...
}
```

---

```
class Account {
  // ...
  private AccountType type;
  private int daysOverdrawn;

  public double bankCharge() {
    double result = 4.5;
    if (daysOverdrawn > 0) {
      result += type.overdraftCharge(this);
    }
    return result;
  }
}

class AccountType {
  // ...
  public double overdraftCharge(Account account) {
    if (isPremium()) {
      double result = 10;
      if (account.getDaysOverdrawn() > 7) {
        result += (account.getDaysOverdrawn() - 7) * 0.85;
      }
      return result;
    }
    else {
      return account.getDaysOverdrawn() * 1.75;
    }
  }
}
```

---

###### Set step 1


#|en| Let's look at *Move Method*  using a bank account class as an example.

Select "private AccountType |||type|||"


#|en| Say, there will be several new account types, and each account type will have different rules for how to calculate overdraft fees, when the bank's customer attempts to spend more money than is available.


#|en| We want to move the method for calculating this overdraft fee to inside the class that represents the account type.

Select name of "overdraftCharge"


#|en| First, we need to decide whether to move only `overdraftCharge()` method or, perhaps, some other related code, fields or methods. 


Select "private AccountType |||type|||"


#|en| The `type` field stores the account type. There is no reason to move it anywhere else.

Select "private int |||daysOverdrawn|||"


#|en| Moving the `daysOverdrawn` field would not make sense either, since its value will be different in every other account.

Select name of "overdraftCharge"


#|en| So, it looks like we going to move only the `overdraftCharge()` method.

###### Set step 2

Select whole "overdraftCharge"


#|en| Awesome, let's copy the `overdraftCharge()` method to the `AccountType` class.

Go to the end of "AccountType"

Print:
```

  public double overdraftCharge() {
    if (type.isPremium()) {
      double result = 10;
      if (daysOverdrawn > 7) {
        result += (daysOverdrawn - 7) * 0.85;
      }
      return result;
    }
    else {
      return daysOverdrawn * 1.75;
    }
  }
```

Select name of "overdraftCharge" in "AccountType"


#|en| Now, we should edit the method so that it will work correctly in the new location.

Select "type." in "overdraftCharge" of "AccountType"


#|en| First remove the `type` field from the method, since the method is inside the class that implements the account type and, therefore, all methods could be called from it directly.

Remove selected

Select "daysOverdrawn" in "overdraftCharge" of "AccountType"



#|en| Now we should go through the fields and methods  that left in `Account` class but still needed inside the method we move. In our case, this is the `daysOverdrawn` field.


#|en| In theory, there are four options for saving a method or field of the original class: <ol><li>Move the field or method to the target class.</li><li>Create a reference from the target class to the original one or restore the previously existing one.</li><li>Pass an instance of the original class as a parameter of the target class method.</li><li>Pass the field value as a parameter.</li></ol>



#|en| In our case, let's pass the field value as a parameter…


Go to parameters of "overdraftCharge" of "AccountType"

Print "int daysOverdrawn"

Select "daysOverdrawn" in "overdraftCharge" of "AccountType"


#|en| …and use this parameter in the method body.


#C|en| Let's compile and test to check for errors in your code.
#S Everything is OK! We can keep going.

###### Set step 3

Select body of "overdraftCharge" in "Account"


#|en| Now we can replace the body of the original method in `Account` class with simple delegation.

Print:
```
    return type.overdraftCharge(daysOverdrawn);
```


#C|en| Let's compile again just to be safe.
#S Everything is OK! We can keep going.

###### Set step 4

Select name of "overdraftCharge" in "Account"


#|en| The code works fine, so we can continue and remove the `overdraftCharge()` method from the original class entirely.

Select "overdraftCharge()" in "bankCharge"


#|en| But first, find all places where it is called and readdress these to the method in the `AccountType` class.

Print:
```
type.overdraftCharge(daysOverdrawn)
```


#|en| In case, when the moved being method is not private, make sure that other classes are not using it. It's easy in strongly-typed programming languages (Java, C#) – compilation will uncover everything that may have been missed. In other languages, autotest is your friend.

Select whole "overdraftCharge" in "Account"


#|en| After redirecting all method calls to the new class, we can entirely remove the method declaration in the `Account` class.

Remove selected

Select name in "Account"


#|en| We can compile and test after each removal or perform everything in one fell swoop.


#|en| So can we say that we are done moving the method?<br/><br/>Not quite…



#|en| Let's look at one nuance. In this case, the method referred to a single field, which allowed us to pass its value to the parameter. If the method had called any other method of the `Account` class, we would not be able to do this. In such situations, you must pass the entire object in the parameters and retrieve everything you need from it. Let's see how it's done.

Select parameters in "overdraftCharge"


#|en| First, pass an instance of the original class to the method being moved.

Print:
```
Account account
```

Select "daysOverdrawn" in "overdraftCharge"


#|en| In addition, all fields and methods of interest should now be taken directly from the instance received.

Print:
```
account.getDaysOverdrawn()
```

Select "overdraftCharge(|||daysOverdrawn|||)"


#|en| And third, you must add passing of the current instance of the `Account` class to all method calls.

Print "this"


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
