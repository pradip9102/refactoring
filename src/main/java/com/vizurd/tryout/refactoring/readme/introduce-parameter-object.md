introduce-parameter-object:java

---

1. Create a new class that will represent your group of parameters. Make the class immutable.

2. In the method that you want to refactor, use <a href="/add-parameter">Add Parameter</a>, which is where your parameter object will be passed. In all method calls, pass the object created from old method parameters to this parameter.

3. Now start deleting old parameters from the method one by one, replacing them in the code with fields of the parameter object. Test the program after each parameter replacement.

4. When done, see whether there is any point in moving a part of the method (or sometimes even the whole method) to a parameter object class. If so, use <a href="/move-method">Move Method</a> or <a href="/extract-method">Extract Method</a>.



---

```
class Account {
  // ...
  private Vector transactions = new Vector();

  public double getFlowBetween(Date start, Date end) {
    double result = 0;
    Enumeration e = transactions.elements();
    while (e.hasMoreElements()) {
      Transaction each = (Transaction) e.nextElement();
      if (each.getDate().compareTo(start) >= 0 && each.getDate().compareTo(end) <= 0) {
        result += each.getValue();
      }
    }
    return result;
  }
}

class Transaction {
  private Date chargeDate;
  private double value;

  public Transaction(double value, Date chargeDate) {
    this.value = value;
    this.chargeDate = chargeDate;
  }
  public Date getDate() {
    return chargeDate;
  }
  public double getValue() {
    return value;
  }
}

// Somewhere in client code…
double flow = account.getFlowBetween(startDate, endDate);
```

---

```
class Account {
  // ...
  private Vector transactions = new Vector();

  public double getFlowBetween(DateRange range) {
    double result = 0;
    Enumeration e = transactions.elements();
    while (e.hasMoreElements()) {
      Transaction each = (Transaction) e.nextElement();
      if (range.includes(each.getDate())) {
        result += each.getValue();
      }
    }
    return result;
  }
}

class Transaction {
  private Date chargeDate;
  private double value;

  public Transaction(double value, Date chargeDate) {
    this.value = value;
    this.chargeDate = chargeDate;
  }
  public Date getDate() {
    return chargeDate;
  }
  public double getValue() {
    return value;
  }
}

class DateRange {
  private final Date start;
  private final Date end;

  public DateRange(Date start, Date end) {
    this.start = start;
    this.end = end;
  }
  public Date getStart() {
    return start;
  }
  public Date getEnd() {
    return end;
  }
  public boolean includes(Date arg) {
    return arg.compareTo(start) >= 0 && arg.compareTo(end) <= 0;
  }
}

// Somewhere in client code…
double flow = account.getFlowBetween(new DateRange(startDate, endDate));
```

---

###### Set step 1


#|en| Let's look at this refactoring, using the bank account and transactions classes.

Select name of "getFlowBetween"


#|en| We are interested in the method for getting the total for all transactions during an indicated period of time.

Select parameters in "getFlowBetween"


#|en| As you can see, the method takes a range of two dates as its parameters. Pretty common situation? But instead of passing two dates, it would be better to pass a single date range object.


#|en| In future, that will allow us to move date range behaviors into their own class.

Go to after "Transaction"


#|en| Let's begin with creating a simple range class.

Print:
```


class DateRange {
  private final Date start;
  private final Date end;

  public DateRange(Date start, Date end) {
    this.start = start;
    this.end = end;
  }
  public Date getStart() {
    return start;
  }
  public Date getEnd() {
    return end;
  }
}
```

Select "private" in "DateRange"


#|en| The class will be immutable: the dates of the range cannot be changed after it is created, since the date fields are declared as private and we did not create setters for them.


#|en| This way you could avoid many errors related to passing objects in method parameters via references.

###### Set step 2

Go to the parameters end of "getFlowBetween"


#|en| Now we can add a range parameter to the method for getting the transaction total.

Print ", DateRange range"

Wait 500ms

Select "account.|||getFlowBetween|||"


#|en| Let's find all places where the method is called. In these calls, add a new parameter – specifically, an object created from the dates already given to the method.

Go to ", endDate|||"

Print ", new DateRange(startDate, endDate)"

###### Set step 3

Select "|||startDate, endDate|||,"


#|en| Then we can get rid of the old date parameters.

Select "Date start" in parameters of "getFlowBetween"


#|en| First take care of the `start` parameter.

Select "start" in body of "getFlowBetween"

Replace "range.getStart()"

Select "Date start, " in parameters of "getFlowBetween"


#|en| After replacements in the method body, the parameter can be removed from the signature and calls of the method.

Remove selected

Wait 500ms

Select "getFlowBetween(|||startDate, |||"

Remove selected

Wait 500ms

Select "Date end" in parameters of "getFlowBetween"


#|en| Now for the remaining parameter.

Select "end" in body of "getFlowBetween"

Replace "range.getEnd()"

Wait 500ms

Select "Date end, " in parameters of "getFlowBetween"

Remove selected

Wait 500ms

Select "getFlowBetween(|||endDate, |||"

Remove selected

Wait 500ms


#C|en| Compile, and test after performing all these moves.
#S Everything is good! Let's continue.

###### Set step 4


#|en| After all the necessary parameters were removed, we can start thinking about moving appropriate behaviors to the parameter object.

Select "each.getDate().compareTo(range.getStart()) >= 0 && each.getDate().compareTo(range.getEnd()) <= 0"


#|en| In our case, we can move a check to see if a date is within a range. This gets rid of this code inside `getFlowBetween`.

Go to the end of "DateRange"

Print:
```

  public boolean includes(Date arg) {
    return arg.compareTo(start) >= 0 && arg.compareTo(end) <= 0;
  }
```

Wait 500ms

Select "each.getDate().compareTo(range.getStart()) >= 0 && each.getDate().compareTo(range.getEnd()) <= 0"

Replace "range.includes(each.getDate())"


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
