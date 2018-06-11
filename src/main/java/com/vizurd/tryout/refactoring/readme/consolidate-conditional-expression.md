consolidate-conditional-expression:java

###

1. Consolidate the conditionals in a single expression by using `AND` and `OR`.


2. Perform <a href="/extract-method">Extract Method</a> on the operator conditions and give the method a name that reflects the expression's purpose.




###

```
class Payout {
  // ...

  public int seniority;
  public int monthsDisabled;
  public boolean isPartTime;

  public double disabilityAmount() {
    if (seniority < 2) {
      return 0;
    }
    if (monthsDisabled > 12) {
      return 0;
    }
    if (isPartTime) {
      return 0;
    }
    // compute the disability amount
    // ...
  }

  public double vacationAmount() {
    if (onVacation()) {
      if (lengthOfService() > 10) {
        return 1;
      }
    }
    return 0.5;
  }
}
```

###

```
class Payout {
  // ...

  public int seniority;
  public int monthsDisabled;
  public boolean isPartTime;

  public double disabilityAmount() {
    if (isNotEligibleForDisability()) {
      return 0;
    }
    // compute the disability amount
    // ...
  }
  private boolean isNotEligibleForDisability() {
    return seniority < 2 || monthsDisabled > 12 || isPartTime;
  }

  public double vacationAmount() {
    return (onVacation() && lengthOfService() > 10) ? 1 : 0.5;
  }
}
```

###

Set step 1


#|en| Let's look at *Consolidate Conditional Expression* , using the method for calculating workman's injury compensation.


Select "if" in "disabilityAmount"
+ Select "return 0" in "disabilityAmount"


#|en| As you see, there are a number of conditions that return an identical result.



#|en| We can merge these checks into a single expression using the `OR` operator.


Go to:
```
}|||
    // compute the disability amount
```

Print:
```

    if (seniority < 2 || monthsDisabled > 12 || isPartTime) {
      return 0;
    }
```

Select:
```
    if (seniority < 2) {
      return 0;
    }
    if (monthsDisabled > 12) {
      return 0;
    }
    if (isPartTime) {
      return 0;
    }

```

Remove selected

Set step 2

Select "seniority < 2 || monthsDisabled > 12 || isPartTime"


#|en| This condition looks too long and hard to comprehend. So we can <a href="/extract-method">Extract Method</a> and make more clear what the conditional is looking for (no compensation to be paid).


Go to after "disabilityAmount"

Print:
```

  private boolean isNotEligibleForDisability() {
    return seniority < 2 || monthsDisabled > 12 || isPartTime;
  }
```

Select "seniority < 2 || monthsDisabled > 12 || isPartTime" in "disabilityAmount"

Replace "isNotEligibleForDisability()"


#C|en| Let's run the compiler and auto-tests.
#S Wonderful, it's all working!


Select "if" in "vacationAmount"


#|en| The previous example demonstrated the `OR` operation but the same thing can be done using `AND`.



#|en| These conditions can be replaced as follows:


Go to the end of "vacationAmount"

Print:
```


    if (onVacation() && lengthOfService() > 10) {
      return 1;
    }
    else {
      return 0.5;
    }
```

Select:
```
    if (onVacation()) {
      if (lengthOfService() > 10) {
        return 1;
      }
    }
    return 0.5;


```

Remove selected

Select body of "vacationAmount"


#|en| If the code only checks a condition and returns a value, we can simplify it to a greater degree by using a ternary operator.


Replace:
```
    return (onVacation() && lengthOfService() > 10) ? 1 : 0.5;
```


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
