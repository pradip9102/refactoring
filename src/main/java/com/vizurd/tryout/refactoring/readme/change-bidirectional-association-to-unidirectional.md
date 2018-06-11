change-bidirectional-association-to-unidirectional:java

###

1. Make sure that one of the following is true for your classes:
    - Association is not used at all
    - Another way of getting the associated object is available (such as by querying a database)
    - The association object can be passed as an argument to the methods that use it.

2. Depending on your situation, instead of using a field containing an association with the relevant object, you may want to use a parameter or method call for obtaining the associated object in a different way.

3. Delete the code that assigns the associated object to the field.

4. Delete the now-unused field.

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

  double getDiscountedPrice() {
    return getGrossPrice() * (1 - getCustomer().getDiscount());
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

  double getPriceFor(Order order) {
     Assert.isTrue(orders.contains(order));
     return order.getDiscountedPrice();
  }
}
```

###

```
class Order {
  // ...
  public Customer getCustomer() {
    Iterator iter = Customer.getInstances().iterator();
    while (iter.hasNext()) {
      Customer each = (Customer)iter.next();
      if (each.containsOrder(this)) {
        return each;
      }
    }
    return null;
  }

  double getDiscountedPrice() {
    return getGrossPrice() * (1 - getCustomer().getDiscount());
  }
}

class Customer {
  // ...
  private Set orders = new HashSet();

  void addOrder(Order arg) {
    orders.add(arg);
  }

  double getPriceFor(Order order) {
     Assert.isTrue(orders.contains(order));
     return order.getDiscountedPrice();
  }
}
```

###

Set step 1

#|en| We will start *Change Bidirectional Association to Unidirectional*  from the place where we have stopped in the inverse refactoring.

Select name of "Order"
+ Select name of "Customer"

#|en| In other words, we have `Customer` and `Order` classes with a bidirectional association.

#|en| Two new methods have been added to the code since completion of the previous refactoring.

Select name of "getPriceFor"

#|en|V First, the method for getting order price in the customer object.

+ Select name of "getDiscountedPrice"

#|en| Then the method for getting price with discount in the order class.

Select "private |||Customer||| customer;"

#|en| Few days ago a new requirement was received, which says that orders must only be created only for existing customers. This lets us eliminate the bidirectional association between orders and customer, and only keeping customers aware of their orders.

Select "|||getCustomer()|||.getDiscount()"

#|en|+ The hardest part of this refactoring technique is making sure that it is possible. Refactoring itself is easy, but we must make sure that it is safe. The problem comes down to whether any of order class' code needs a customer field. If that is the case, removing the field requires you to provide an alternative method for getting the customer object.

Set step 2

#|en|^= First, we review all usages of the customer field and it's getter. Is there another way to provide the customer object or it's data? Often thу best solution means passing the customer as an argument to the methods, which use the field.

Go to parameters of "getDiscountedPrice"

Print "Customer customer"

Wait 500ms

Select "|||getCustomer()|||.getDiscount()"

Replace "customer"


Select:
```
     return order.getDiscountedPrice();
```

#|en| This works particularly well for methods that called by client code already containing a customer object. In this case, you just pass it as a method's argument.

Go to:
```
     return order.getDiscountedPrice(|||);
```

Print "this"

Wait 1000ms

Select body of "getCustomer"

#|en| Another alternative is to change the customer field getter so that it would get the right object by looking it in some object repository like this:

Print:
```
    Iterator iter = Customer.getInstances().iterator();
    while (iter.hasNext()) {
      Customer each = (Customer)iter.next();
      if (each.containsOrder(this)) {
        return each;
      }
    }
    return null;
```

Select parameters of "getDiscountedPrice"

#|en| The previous introduction of a parameter into the method can now be removed since the customer getter will return the correct object.

Remove selected

Select "|||customer|||.getDiscount()"

Replace "getCustomer()"


Select "getDiscountedPrice(|||this|||);"
Remove selected

Select name of "getCustomer"

#|en| Slow… But it works. In the context of a database, things may even become a little faster if a database query is used.

Set step 3

Select:
```
  // Should be used in Order class only.

```
+ Select whole "friendOrders"
+ Select body of "addOrder"

#|en| Now we can prepare for the `setCustomer` method removal. Instead calling it in customer class, we should add orders to the order collection directly.

Select:
```
  // Should be used in Order class only.

```
+ Select whole "friendOrders"

Remove selected

Select body of "addOrder"

Type:
```
    orders.add(arg);
```

Select whole "setCustomer"

#|en| Then we can freely remove the method in the order class.

Remove selected

Set step 4

Select:
```
  private Customer customer;


```

#|en| At last, we can remove the field itself, fully eliminating the bidirectional association between the classes.

Remove selected

#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!

Set final step

#|en|Q The refactoring is complete! You can compare the old and new code if you like.
