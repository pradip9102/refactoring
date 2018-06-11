replace-data-value-with-object:java

---

1. Create a new class and copy your field and a getter for accessing the field to the class.

2. Create a constructor that accepts the original value of the field.

3. In the original class, change the field type to the new class.

4. Change the access methods so that they delegate to the new class.



---

```
class Order {
  // ...
  private String customer;

  public Order(String customer) {
    this.customer = customer;
  }
  public String getCustomer() {
    return customer;
  }
  public void setCustomer(String customer) {
    this.customer = customer;
  }
}

// Client code, which uses Order class.
private static int numberOfOrdersFor(Collection orders, String customer) {
  int result = 0;
  Iterator iter = orders.iterator();
  while (iter.hasNext()) {
    Order each = (Order) iter.next();
    if (each.getCustomer().equals(customer)) {
      result++;
    }
  }
  return result;
}
```

---

```
class Order {
  // ...
  private Customer customer;

  public Order(String customerName) {
    this.customer = new Customer(customerName);
  }
  public String getCustomerName() {
    return customer.getName();
  }
  public void setCustomer(String customerName) {
    this.customer = new Customer(customerName);
  }
}

class Customer {
  private final String name;

  public Customer(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }
}

// Client code, which uses Order class.
private static int numberOfOrdersFor(Collection orders, String customer) {
  int result = 0;
  Iterator iter = orders.iterator();
  while (iter.hasNext()) {
    Order each = (Order) iter.next();
    if (each.getCustomerName().equals(customer)) {
      result++;
    }
  }
  return result;
}
```

---

###### Set step 1


#|en| Let's look at the *Replace Data Value with Object*  refactoring, using an order class as an example.

Select "private String |||customer|||"


#|en| In this example, the customer in the order class is stored as a string. Alternatively, we could create a `Customer` class and move the other customer data and behaviors to this class.

Go to after "Order"

Print:
```


class Customer {
}
```


#|en| The class is now ready. Let's move the customer name field to it, since the field is used in the rest of the order code.

Go to end of "Customer"

Print:
```

  private final String name;

  public String getName() {
    return name;
  }
```

###### Set step 2

Go to before of "getName"


#|en| We should also create a constructor that accepts the initial value of the name.

Print:
```

  public Customer(String name) {
    this.name = name;
  }
```

###### Set step 3

Select "private String customer"


#|en| Now we can change the type of the `Customer` field. We should also change the associated methods so that they work with instances of the `Customer` class.


#|en| Let's start with changing the type of the customer field.

Select "private |||String||| customer"

Replace "Customer"

###### Set step 4


#|en| Now we will make the getter for the user name return the value from the associated object.

Select "return |||customer|||" in "getCustomer"

Replace "customer.getName()"

Select name of "public Order"
+ Select name of "setCustomer"


#|en| Then change the constructor and access setter so that they fill the customer field with a new `Customer` object.

Select "= |||customer|||" in "public Order"
+ Select "= |||customer|||" in "setCustomer"

Replace "new Customer(customer)"

Select name of "setCustomer"


#|en| Note that the setter creates a new instance of the customer class each time. That makes the customer is value object. In other words, if there are two orders made by single customer, the orders will still have two separate customer objects.


#|en| Value objects should be made immutable to avoid certain unpleasant errors related to the fact that objects are always passed via references. By the way, later we will still need to convert `Customer` to a reference object, but that's out of current refactoring scope.


#C|en| Anyway, let's compile and test to make sure there are no errors.
#S Everything is OK! Code works correctly.

Go to name of "Order"


#|en| All we have left now is to look at `Order` methods which work with `Customer` and make a few small changes in them.

Select name of "getCustomer"


#|en| FIrst, we apply <a href="/rename-method">Rename Method</a> to the getter to make clear that it returns a name, not an object.

Replace "getCustomerName"

Wait 500ms

Select "each.|||getCustomer|||"

Replace "getCustomerName"

Select "String |||customer|||" in parameters of "public Order"
+ Select "(|||customer|||)" in "public Order"
+ Select "String |||customer|||" in parameters of "setCustomer"
+ Select "(|||customer|||)" in "setCustomer"


#|en| It also is a good idea to change the names of the parameters in the constructor and setter.

Replace "customerName"


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!



#|en| Before we finish, note that here and in many other cases, one more step is necessary. You may need to add a credit score, address, etc. to the `Customer`. You cannot do this yet, since `Customer` is used as a value object. That is, each order has its own instance of the `Customer` class.


#|en| To create the necessary attributes in the `Customer` class, use the <a href="/change-value-to-reference">Change Value to Reference</a> refactoring technique on it. After that refactoring, all orders for the same customer will refer to the same instance of the `Customer` class.

###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
