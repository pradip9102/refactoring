replace-parameter-with-explicit-methods:java

###

1.en. For each variant of the method, create a separate method. Run these methods based on the value of a parameter in the main method.


2.en. Find all places where the original method is called. In these places, place a call for one of the new parameter-dependent variants.


3.en. When no calls to the original method remain, delete it.




###

```
class Order {
  // ...
  public static final int FIXED_DISCOUNT = 0;
  public static final int PERCENT_DISCOUNT = 1;

  public void applyDiscount(int type, double discount) {
    switch (type) {
      case FIXED_DISCOUNT:
        price -= discount;
        break;
      case PERCENT_DISCOUNT:
        price *= discount;
        break;
      default:
        throw new IllegalArgumentException("Invalid discount type");
    }
  }
}

// Somewhere in client code
if (weekend) {
  order.applyDiscount(Order.FIXED_DISCOUNT, 10);
}
if (order.items.size() > 5) {
  order.applyDiscount(Order.PERCENT_DISCOUNT, 0.2);
}
```

###

```
class Order {
  // ...
  public void applyFixedDiscount(double discount) {
    price -= discount;
  }
  public void applyPercentDiscount(double discount) {
    price *= discount;
  }
}

// Somewhere in client code
if (weekend) {
  order.applyFixedDiscount(10);
}
if (order.items.size() > 5) {
  order.applyPercentDiscount(0.2);
}
```

###

Set step 1

Select name of "Order"


#|en| Let's look at this technique using an order class as an example.


Select name of "applyDiscount"


#|en| This class has a method for applying discounts that handle both fixed discounts and percentage-based ones.



#|en| Let's start refactoring by extracting each version to a separate method.


Select "price -= discount;"

Wait 1000ms

Go to after "applyDiscount"

Print:
```

  public void applyFixedDiscount(double discount) {
    price -= discount;
  }
```

Select "price *= discount;"

Wait 1000ms

Go to after "applyFixedDiscount"

Print:
```

  public void applyPercentDiscount(double discount) {
    price *= discount;
  }
```

Set step 2


#|en| Now find all places where the original method is called, replacing them with calls to our new methods.


Select "applyDiscount(Order.FIXED_DISCOUNT, "

Replace "applyFixedDiscount("

Wait 1000ms

Select "applyDiscount(Order.PERCENT_DISCOUNT, "

Replace "applyPercentDiscount("


#C|en| Let's compile and test to check for errors in code.
#S Wonderful, it's all working!


Set step 3


#|en| Once changes are complete, remove the original method and now-useless constants.


Select whole "applyDiscount"

Remove selected

Wait 1000ms

Select:
```
  public static final int FIXED_DISCOUNT = 0;
  public static final int PERCENT_DISCOUNT = 1;


```
Remove selected


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
