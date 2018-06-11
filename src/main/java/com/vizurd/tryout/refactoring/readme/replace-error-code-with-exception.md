replace-error-code-with-exception:java

###

1.en. Find all calls to a method that returns error codes and, instead of checking for an error code, wrap it in <code>try</code>/<code>catch</code> blocks.


2.en. Inside the method, instead of returning an error code, throw an exception.


3.en. Change the method signature so that it contains information about the exception being thrown (<code>@throws</code> section).




###

```
class Account {
  // ...
  private int balance;

  public int withdraw(int amount) {
    if (amount > balance) {
      return -1;
    }
    else {
      balance -= amount;
      return 0;
    }
  }
}

// Somewhere in client code.
if (account.withdraw(amount) == -1) {
  handleOverdrawn();
}
else {
  doTheUsualThing();
}
```

###

```
class Account {
  // ...
  private int balance;

  public void withdraw(int amount) throws BalanceException {
    if (amount > balance) {
      throw new BalanceException();
    }
    balance -= amount;
  }
}
class BalanceException extends Exception {}

// Somewhere in client code.
try {
  account.withdraw(amount);
  doTheUsualThing();
} catch (BalanceException e) {
  handleOverdrawn();
}
```

###

Set step 1


#|en| Let's look at this refactoring in the context of withdrawals from a bank account.


Go to "if (amount > balance) {|||"


#|en|<+ If a customer attempts to withdraw more money than his or her current balance allows, an error code will be returned (<code>-1</code>)…


Select "account.withdraw(amount) == -1"


#|en|= …which is then checked in the client code.



#|en| We can replace all this by throwing an exception and then "catching" it in the client code.


Go to after "Account"


#|en| First, we create a new exception class.


Print:
```

class BalanceException extends Exception {}
```


#|en| Then, wrap our method body with the <code>try</code>/<code>catch</code> block.


Select:
```
if (account.withdraw(amount) == -1) {
  handleOverdrawn();
}
else {
  doTheUsualThing();
}
```

Replace:
```
try {
  account.withdraw(amount);
  doTheUsualThing();
} catch (BalanceException e) {
  handleOverdrawn();
}
```

Set step 2


#|en| After that, change the method so that it throws an exception instead of returning an error code.


Select:
```
      return -1;
```

Replace:
```
      throw new BalanceException();
```

Wait 500ms

Select:
```
      return 0;

```

Remove selected

Select type of "withdraw"

Replace "void"

Select:
```
|||    else {
|||      balance -= amount;
|||    }
|||
```


#|en| This code can be simplified a bit if we remove <code>else</code>.


Remove selected

Select:
```
      balance -= amount;
```

Deindent

Select name of "Account"


#|en| This step is not very safe because we are forced to change all references to the method, as well as the method itself, in a single step. Otherwise, the compiler will shake its head at us in disapproval. If there are many calls, we will have to make a mammoth modification without any intermediate compilation or testing.



#|en| In such cases, it is better to create a new method and place the code of the old one inside it, including exceptions. Replace the code of the old method with <code>try</code>/<code>catch</code> blocks that return error codes. After this, the code will remain functional and we could replace error code handlers, one by one, with calls to the new method and <code>try</code>/<code>catch</code> blocks.


Set step 3

Go to "withdraw(int amount)|||"


#|en| After all of the changes, we must update the method's signature, indicating that the method now throws exceptions.


Print " throws BalanceException"


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
