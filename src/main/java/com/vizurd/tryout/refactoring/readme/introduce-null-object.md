introduce-null-object:java

###

1.en. From the class in question, create a subclass that will perform the role of null object.


2.en. In both classes, create the method <code>isNull()</code>, which will return <code>true</code> for a null object and <code>false</code> for a real class.


3.en. Find all places where the code may return <code>null</code> instead of a real object. Change the code so that it returns a null object.


4.en. Find all places where the variables of the real class are compared with <code>null</code>. Replace these checks with a call for <code>isNull()</code>.


5.en. <ul><li>If methods of the original class are performed in these conditionals for values not equal to <code>null</code>, redefine these methods in the null class and put the code from the <code>else</code> part of the conditional code there. Then you can delete the conditional entirely, since differing behavior will be controlled through polymorphism.</li><li>If things are more complicated and redefining the methods is "not in the cards", see whether you can simply move the operations that should be performed for values equal to <code>null</code> to new methods of the Null object. Call these methods instead of the old code in <code>else</code> as default operations.</li></ul>




###

```
class Company {
  //…
  private Customer customer;
  public Customer getCustomer() {
    return customer;
  }
}

class Customer {
  //…
  public String getName() {
    //…
  }
  public BillingPlan getPlan() {
    //…
  }
  public PaymentHistory getHistory() {
    //…
  }
}

class PaymentHistory {
  public int getWeeksDelinquentInLastYear() {
    //…
  }
}

// Somewhere in client code
Customer customer = site.getCustomer();
String customerName;
if (customer == null) {
  customerName = "N/A";
}
else {
  customerName = customer.getName();
}

//…
BillingPlan plan;
if (customer == null) {
  plan = BillingPlan.basic();
}
else {
  plan = customer.getPlan();
}

//…
int weeksDelinquent;
if (customer == null) {
  weeksDelinquent = 0;
}
else {
  weeksDelinquent = customer.getHistory().getWeeksDelinquentInLastYear();
}
```

###

```
class Company {
  //…
  private Customer customer;
  public Customer getCustomer() {
    return (customer == null) ? Customer.newNull() : customer;
  }
}

class Customer {
  //…
  public boolean isNull() {
    return false;
  }
  public static Customer newNull() {
    return new NullCustomer();
  }

  public String getName() {
    //…
  }
  public BillingPlan getPlan() {
    //…
  }
  public PaymentHistory getHistory() {
    //…
  }
}
class NullCustomer extends Customer {
  @Override public boolean isNull() {
    return true;
  }
  @Override public String getName() {
    return "N/A";
  }
  @Override public BillingPlan getPlan() {
    return BillingPlan.basic();
  }
  @Override public PaymentHistory getHistory() {
    return PaymentHistory.newNull();
  }
}

class PaymentHistory {
  public boolean isNull() {
    return false;
  }
  public static PaymentHistory newNull() {
    return new NullPaymentHistory();
  }

  public int getWeeksDelinquentInLastYear() {
    //…
  }
}
class NullPaymentHistory extends PaymentHistory {
  @Override public boolean isNull() {
    return true;
  }
  @Override public int getWeeksDelinquentInLastYear() {
    return 0;
  }
}

// Somewhere in client code
Customer customer = site.getCustomer();
String customerName = customer.getName();

//…
BillingPlan plan = customer.getPlan();

//…
int weeksDelinquent = customer.getHistory().getWeeksDelinquentInLastYear();
```

###

Set step 1

Select name of "Company"


#|en| Let's look at this refactoring, using a commercial company class as an example.


Select name of "Customer"


#|en| Every business has customers (<code>Customer</code>).


Select "getName" in "Customer"
+Select "getPlan" in "Customer"
+Select "getHistory" in "Customer"


#|en| Customers in turn have their own properties and behaviors.


Go to "// Somewhere in client code"


#|en| The client code operates on these access methods in order to do its work. For example, this is code for getting the name of a current customer:


Select "if (customer == null)"


#|en| Note the conditional that verifies whether the business has the customer in question. This situation may occur if the business is new or an old customer has decided to change vendors.



#|en| The code may contain many such repetitive <code>null</code> verifications, which indicates the need to introduce a null-object.



#|en| First create a <code>null</code>-object class for <code>customer</code> and modify the <code>Customer</code> class so that it supports a query for <code>null</code> verification.


Set step 2

Go to before "getName"

Print:
```

  public boolean isNull() {
    return false;
  }

```
Go to after "Customer"

Print:
```

class NullCustomer extends Customer {
  @Override public boolean isNull() {
    return true;
  }
}
```


#|en| To create "null" clients, let's introduce a factory method. Thanks to it, client code will not need to know about the existence of the null-object


Go to after "isNull"

Print:
```

  public static Customer newNull() {
    return new NullCustomer();
  }
```

Set step 3

Select "return customer"


#|en| Now we should handle all code that returns <code>Customer</code> objects. We should add the checks, which will return our <code>null</code> object instead of <code>null</code> value.


Print "return (customer == null) ? Customer.newNull() : customer"


Set step 4

Select "if (|||customer == null|||)"


#|en| Then, in the remaining code, replace all checks of the type <code>Customer == null</code> with calls of <code>Customer.isNull()</code>.


Print "customer.isNull()"


#|en| This is the most complex part of the refactoring. For each source of <code>null</code> that you are replacing, you must find all <code>null</code> checks and change them. If an object is passed back and forth between methods, doing so consistently can be difficult.



#C|en| After all replacements are done, compile and test carefully.
#S Great, it all works! We can continue then.


Set step 5


#|en| We do not yet gain any benefit from using <code>isNull</code> instead of plain <code>== null</code>  checks. The benefit will be visible when the code, which used to work in null cases will be moved straight to the null-object class.


Select "customerName = "N/A""


#|en| Let's start moving behaviors. The first thing to do is move the default customer name to the null-object class.


Go to the end of "NullCustomer"

Print:
```

  @Override public String getName() {
    return "N/A";
  }
```

Wait 500ms

Select:
```
String customerName;
if (customer.isNull()) {
  customerName = "N/A";
}
else {
  customerName = customer.getName();
}
```


#|en| Then remove the check for <code>null</code> from the corresponding part of the client code.


Print:
```
String customerName = customer.getName();
```


#|en| Do the same with the remaining methods for which you can think of a default behavior.


Go to the end of "NullCustomer"

Print:
```

  @Override public BillingPlan getPlan() {
    return BillingPlan.basic();
  }
```

Wait 500ms

Select:
```
BillingPlan plan;
if (customer.isNull()) {
  plan = BillingPlan.basic();
}
else {
  plan = customer.getPlan();
}
```

Replace:
```
BillingPlan plan = customer.getPlan();
```

Select "customer.getHistory()"


#|en| Careful review of the last bit of code could show a potential access error. It will occur when somebody will access a payment object while user object won't have any payment history.



#|en| To solve the problem, you can create a null-object class for the payment history class as well.


Go to the start of "PaymentHistory"

Print:
```

  public boolean isNull() {
    return false;
  }
```
Go to after "PaymentHistory"

Print:
```

class NullPaymentHistory extends PaymentHistory {
  @Override public boolean isNull() {
    return true;
  }
}
```

Wait 500ms

Go to before "getWeeksDelinquentInLastYear"

Print:
```

  public static PaymentHistory newNull() {
    return new NullPaymentHistory();
  }

```

Go to the end of "NullPaymentHistory"


#|en| Once the null-object has been defined, you can move default behavior to it.


Print:
```

  @Override public int getWeeksDelinquentInLastYear() {
    return 0;
  }
```

Select "customer.getHistory()"


#|en| Now we can rest easy about any potential problem accessing the null-object of the payment history. But there are still other things to take care of.



#|en| We can return the null-object of the payment history from the null-object of customers, fully ridding the client code of checks for <code>null</code>.


Go to the end of "NullCustomer"

Print:
```

  @Override public PaymentHistory getHistory() {
    return PaymentHistory.newNull();
  }
```

Select:
```
int weeksDelinquent;
if (customer.isNull()) {
  weeksDelinquent = 0;
}
else {
  weeksDelinquent = customer.getHistory().getWeeksDelinquentInLastYear();
}
```

Replace:
```
int weeksDelinquent = customer.getHistory().getWeeksDelinquentInLastYear();
```


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
