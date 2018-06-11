move-field:java

---

1. If the field is public, encapsulate it.

2. Create a field copy and methods for accessing the field in the target class.

3. Decide how you will refer to the target class. It is quite possible that you already have a field or method that returns an appropriate class instance, but if not, you will need to create one of these.

4. Replace all references to the old class with the relevant calls to methods in the target class.

5. Delete the field in the original class.



---

```
class Account {
  // ...
  private AccountType type;
  private double interestRate;

  public double interestForAmount_days(double amount, int days) {
    return interestRate * amount * days / 365.0;
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

  public double interestForAmount_days(double amount, int days) {
    return getInterestRate() * amount * days / 365.0;
  }
  public double interestForBigFamily(double familySize) {
    return getInterestRate() / familySize;
  }

  // other 10 methods, which use getInterestRate()

  private double getInterestRate() {
    return type.getInterestRate();
  }
  private void setInterestRate(double arg) {
    type.setInterestRate(arg);
  }
}

class AccountType {
  // ...
  private double interestRate;

  public double getInterestRate() {
    return interestRate;
  }
  public void setInterestRate(double arg) {
    interestRate = arg;
  }
}
```

---

###### Set step 1


#|en| Let's look at *Move Field*  using a bank account class as our example.

Select "double |||interestRate|||"


#|en| We want to move the `interestRate` field to the `AccountType` class.

Select name of "interestForAmount_days"


#|en| Several methods refer to this field. One of them is the `interestForAmount_days()` method.

###### Set step 2

Go to the end of "AccountType"


#|en| Let's start by creating the same field and same access methods in the target class.

Print:
```

  private double interestRate;

  public double getInterestRate() {
    return interestRate;
  }
  public void setInterestRate(double arg) {
    interestRate = arg;
  }
```


#C|en| To stay on the safe side, compile and test after each change.
#S All is well, so let's continue.

###### Set step 3

Select "AccountType |||type|||" in "Account"


#|en| In our example, the `Account` class contains a field for accessing the account type object. For this reason, we can access the moved field through it.

###### Set step 4


#|en| We replace all references to the old field with appropriate calls to methods in the target class.

Select "interestRate" in "interestForAmount_days"

Replace "type.getInterestRate()"

###### Set step 5

Select in "Account":
```
  private double interestRate;

```


#|en| Once changes are complete, we can remove the original field.

Remove selected


#C|en| Let's compile and test for potential errors.
#S Outstanding. The code is doing what we intended.

#|en| Remember that if a class has many methods that use the moved field, you may want to self-encapsulate it to simplify later refactoring. Let's look at a quick example.

Select whole "Account"

Replace instantly:
```
class Account {
  // ...
  private AccountType type;
  private double interestRate;

  public double interestForAmount_days(double amount, int days) {
    return getInterestRate() * amount * days / 365.0;
  }
  public double interestForBigFamily(double familySize) {
    return getInterestRate() / familySize;
  }

  // other 10 methods, which use getInterestRate()

  private double getInterestRate() {
    return interestRate;
  }
  private void setInterestRate(double arg) {
    interestRate = arg;
  }
}

```

Select name of "interestForAmount_days"
+ Select name of "interestForBigFamily"
+ Select "other 10 methods"


#|en| In this case, you will not need to make changes in all methods right away…


Select "interestRate = arg" in "setInterestRate"
+Select "return interestRate" in "getInterestRate"


#|en| …only in the access methods (getter and setter).

Select "return interestRate" in "getInterestRate"

Replace "return type.getInterestRate()"

Select "interestRate = arg" in "setInterestRate"

Replace "type.setInterestRate(arg)"

Select in "Account":
```
  private double interestRate;

```


#|en| Then the original field can be removed.

Remove selected

Select name of "interestForAmount_days"
+ Select name of "interestForBigFamily"


#|en| Later, if desired, you can redirect access methods for clients so that they use the new object.

Select name of "Account"


#|en| Self-encapsulating allows refactoring via baby steps. And when a class is undergoing major changes, that is a good thing.


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
