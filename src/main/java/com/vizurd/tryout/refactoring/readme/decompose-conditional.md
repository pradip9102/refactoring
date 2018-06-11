decompose-conditional:java

---

1. Extract the conditional to a separate method via <a href="/extract-method">Extract Method</a>.

2. Repeat the process for the `then` and `else` blocks.



---

```
class Stadium {
  // ...
  public double summerRate;
  public double winterRate;
  public double winterServiceCharge;

  public double getTicketPrice(Date date, int quantity) {
    double charge;
    if (date.before(SUMMER_START) || date.after(SUMMER_END)) {
      charge = quantity * winterRate + winterServiceCharge;
    }
    else {
      charge = quantity * summerRate;
    }
    return charge;
  }
}
```

---

```
class Stadium {
  // ...
  public double summerRate;
  public double winterRate;
  public double winterServiceCharge;

  public double getTicketPrice(Date date, int quantity) {
    double charge;
    if (notSummer(date)) {
      charge = winterCharge(quantity);
    }
    else {
      charge = summerCharge(quantity);
    }
    return charge;
  }

  private boolean notSummer(Date date) {
    return date.before(SUMMER_START) || date.after(SUMMER_END);
  }
  private double winterCharge(int quantity) {
    return quantity * winterRate + winterServiceCharge;
  }
  private double summerCharge(int quantity) {
    return quantity * summerRate;
  }
}
```

---

###### Set step 1


#|en| Let's look at *Decompose Conditional*  using a simple class, that calculates the cost of a stadium ticket.

Select name of "getTicketPrice"
+ Select "SUMMER_START"
+ Select "SUMMER_END"


#|en| The cost depends on the season (winter or summer).


#|en| Our task is to make this conditional easier to understand. We can start by extracting the condition to a new method with a more obvious name.

Go to the end of "Stadium"

Print:
```


  private boolean notSummer(Date date) {
  }
```

Select "date.before(SUMMER_START) || date.after(SUMMER_END)"

Wait 500ms

Go to the end of "notSummer"

Print:
```

    return date.before(SUMMER_START) || date.after(SUMMER_END);
```

Wait 500ms

Select "date.before(SUMMER_START) || date.after(SUMMER_END)" in "getTicketPrice"

Remove selected

Print "notSummer(date)"



#C|en| Let's compile and test to check for errors in the code.
#S Wonderful, it's all working!


Select "notSummer(date)" in "getTicketPrice"


#|en| The condition became much clearer now. However, many programmers in such situations do not extract the components of the conditional, thinking about conditions as short and not worth the effort.


#|en| But no matter how short the condition is, there is often a big difference between the purpose of the code and its body. Figuring this out requires looking at the code in detail. In this case, doing so is easy but even here the extracted method looks more like a comment.

###### Set step 2

Select "charge = quantity * winterRate + winterServiceCharge;"


#|en| Now we turn to the body of the conditional. First we extract everything inside `then` to a new method.

Go to the end of "Stadium"

Print:
```

  private double winterCharge(int quantity) {
    return quantity * winterRate + winterServiceCharge;
  }
```

Select "charge = quantity * winterRate + winterServiceCharge;"

Replace "charge = winterCharge(quantity);"


Select "charge = quantity * summerRate;"


#|en| Then we turn our attention to `else`.


Go to the end of "Stadium"

Print:
```

  private double summerCharge(int quantity) {
    return quantity * summerRate;
  }
```

Select "charge = quantity * summerRate;"

Replace "charge = summerCharge(quantity);"


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
