replace-nested-conditional-with-guard-clauses:java

###

1. Isolate all guard clauses that lead to calling an exception or immediate return of a value from the method. Place these conditions at the beginning of the method.

2. After rearrangement is complete and all tests are successfully completed, see whether you can use <a href="/consolidate-conditional-expression">Consolidate Conditional Expression</a> for guard clauses that lead to the same exceptions or returned values.



###

```
class Payout {
  // ...
  double getPayAmount() {
    double result = 0;
    if (isDead) {
      result = deadAmount();
    }
    else {
      if (isSeparated) {
        result = separatedAmount();
      }
      else {
        if (isRetired) {
          result = retiredAmount();
        }
        else {
          result = normalPayAmount();
        }
      }
    }
    return result;
  }
}
```

###

```
class Payout {
  // ...
  double getPayAmount() {
    if (isDead) {
      return deadAmount();
    }
    if (isSeparated) {
      return separatedAmount();
    }
    if (isRetired) {
      return retiredAmount();
    }
    return normalPayAmount();
  }
}
```

###

###### Set step 1


#|en| Imagine a payroll system with special rules for employees who have passed away, live apart, or have retired. These cases are unusual but do occur.

Select "isDead"
+ Select "isSeparated"
+ Select "isRetired"


#|en|+ Once some of the special conditions are triggered…


Select "result = normalPayAmount()"


#|en|<= …a corresponding method is called. Then it's is taken to the end of the method and returned as is. Such could be pretty difficult to understand, especially if there are lot of conditional branches. To fix it, we could place guard clauses, e.g. return the value right away if some condition is met.

Select "|||result =||| deadAmount();"

Replace "return"

Wait 500ms

Select:
```
    }
|||    else {
|||
```

+Select:
```
|||    }
|||    return result;
```

Wait 500ms

Remove selected

Wait 500ms

Select:
```
      if (isSeparated) {
        result = separatedAmount();
      }
      else {
        if (isRetired) {
          result = retiredAmount();
        }
        else {
          result = normalPayAmount();
        }
      }
```

Deindent

Wait 500ms

Select "isSeparated"


#|en| Continue performing replacements, one at a time.

Select "|||result =||| separatedAmount();"

Replace "return"

Wait 500ms

Select:
```
    }
|||    else {
|||
```

+Select:
```
|||    }
|||    return result;
```

Wait 500ms

Remove selected

Wait 500ms

Select:
```
      if (isRetired) {
        result = retiredAmount();
      }
      else {
        result = normalPayAmount();
      }
```

Deindent

Wait 500ms

Select "isRetired"


#|en| And the last one.

Select "|||result =||| retiredAmount();"

Replace "return"

Wait 500ms

Select:
```
    }
|||    else {
|||
```

+Select:
```
|||    }
|||    return result;
```

Wait 500ms

Remove selected

Wait 500ms

Select:
```
      result = normalPayAmount();
```

Deindent

Wait 500ms

Select:
```
    double result = 0;

```


#|en| After these changes, you can get rid of the `result` variable entirely.

Remove selected

Wait 500ms

Select "result ="

Replace "return"

Wait 500ms

Select:
```
    return result;

```

Wait 500ms

Remove selected


#|en| Multi-level sub-conditionals are often written by programmers taught that a method should contain only one exit point. But in modern programming, this rule have become obsolete.


#|en| If, during execution, method did everything, it could, it's better to exit as soon as possible. Otherwise, going over an empty `else` block only throws up roadblocks to performance and readability.


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
