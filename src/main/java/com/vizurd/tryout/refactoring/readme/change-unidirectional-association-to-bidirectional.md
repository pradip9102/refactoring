change-unidirectional-association-to-bidirectional:java

###

1.en. Add a field for holding the reverse association.

2.en. Decide which class will be the "dominant" one.

3.en. Create a utility method for setting up an association in the non-dominant class.

4.en. If old methods for controlling the unidirectional association were in the "dominant" class, complement them with calls to utility methods from the associated object.

5.en. If the old methods for controlling the association were in the non-dominant class, implement a control algorithm in the dominant class and delegate execution to them from the non-dominant class.



###

```
class Order {
  // ...
  private Customer customer;

  public Customer getCustomer() {
    return customer;
  }
  public void setCustomer(Customer arg) {
    customer = arg;
  }
}

class Customer {
  // ...
}
```

###

```
class Order {
  // ...
  private Customer customer;

  public Customer getCustomer() {
    return customer;
  }
  public void setCustomer(Customer arg) {
    // Remove order from old customer.
    if (customer != null) {
      customer.friendOrders().remove(this);
    }
    customer = arg;
    // Add order to new customer.
    if (customer != null) {
      customer.friendOrders().add(this);
    }
  }
}

class Customer {
  // ...
  private Set orders = new HashSet();

  // Should be used in Order class only.
  Set friendOrders() {
    return orders;
  }
  void addOrder(Order arg) {
    arg.setCustomer(this);
  }
}
```

###

Set step 1

#|en| Let's consider <i>Replace Unidirectional Association with Bidirectional</i> using the example of two classes: <code>Customer</code> and <code>Order</code>.

Select:
```
private Customer customer;
```

#|en| The order class contains a reference to the customer object.

Select name of "Customer"

#|en| However, the customer class does not have references to order objects. Thus, if you need to get an order object from the method of a customer object, you will have to usу some roundabout ways that are slow and inconvenient.

#|en| Let's start refactoring by adding order fields to the <code>Customer</code> class. Since a customer can have multiple orders, we should make the field a collection.

Go to the end of "Customer"

Print:
```

  private Set orders = new HashSet();
```

Set step 2

Select name of "Order"
+ Select name of "Customer"

#|en| Now we need to decide which class will be responsible for managing the association. It is better to place responsibility with a single class because this allows keeping the entire logic in a single place.

#|en| By the way, here's how to decide this:<ul><li>If both objects are reference objects and the association is one-to-many, the “control” object will be the one that contains one reference. So if one client has many orders, the association is controlled by the order.</li><li>If one object is a component of the other (whole–part association), the “whole” object should control the association.</li><li>If both objects are reference objects, and the association is many-to-many: you can select either the order class or client class as the control class.</li></ul>

Set step 3

Go to the end of "Customer"

#|en| In our case, the order class will be responsible for the association. For this reason, we add a helper method to the customer class, which will provide access to the order collection.

Print:
```


  // Should be used in Order class only.
  Set friendOrders() {
    return orders;
  }
```

Set step 4

Select name of "setCustomer"

#|en| Now we can change the field's setter in the <code>Order</code> class so that it would add the current order object to the list of customer orders.

Go to the start of "setCustomer"

Print:
```

    // Remove order from old customer.
    if (customer != null) {
      customer.friendOrders().remove(this);
    }
```

Wait 1000ms

Go to the end of "setCustomer"

Print:
```

    // Add order to new customer.
    if (customer != null) {
      customer.friendOrders().add(this);
    }
```

Go to:
```
    // Remove order from old customer.|||
```

#|en|< The exact code in the controlling modifier varies with the multiplicity of the association. If <code>Customer</code> cannot be <code>null</code>, you can get by without checking it for <code>null</code> but in that case you should check the argument for <code>null</code>.

Go to:
```
    // Add order to new customer.|||
```
#|en|< The basic pattern is always the same, however: first tell the other object to remove its pointer to you, set your pointer to the new object, and then tell the new object to add a pointer to you.

Set step 5

Go to the end of "Customer"

#|en| If you want to modify a reference through the customer class, the class should call the control method in the associated order object:

Print:
```

  void addOrder(Order arg) {
    arg.setCustomer(this);
  }
```

#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!

Set final step

#|en|Q The refactoring is complete! You can compare the old and new code if you like.
