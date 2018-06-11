replace-parameter-with-method-call:java

###

1.en. Make sure that the value-getting code does not use parameters from the current method, since they will be unavailable from inside another method. If so, moving the code is not possible.


2.en. If the relevant code is more complicated than a single method or function call, use <a href="/extract-method">Extract Method</a> to isolate this code in a new method and make the call simple.


3.en. In the code of the main method, replace all references to the parameter being replaced with calls to the method that gets the value.


4.en. Use <a href="/remove-parameter">Remove Parameter</a> to eliminate the now-unused parameter.




###

```
class Order {
  // ...
  public double getPrice() {
    int basePrice = quantity * itemPrice;
    int discountLevel;
    if (quantity > 100) {
      discountLevel = 2;
    }
    else {
      discountLevel = 1;
    }
    double finalPrice = discountedPrice(basePrice, discountLevel);
    return finalPrice;
  }
  private double discountedPrice(int basePrice, int discountLevel) {
    if (discountLevel == 2) {
      return basePrice * 0.1;
    }
    else {
      return basePrice * 0.05;
    }
  }
}
```

###

```
class Order {
  // ...
  public double getPrice() {
    return discountedPrice();
  }
  private double discountedPrice() {
    if (getDiscountLevel() == 2) {
      return getBasePrice() * 0.1;
    }
    else {
      return getBasePrice() * 0.05;
    }
  }
  private int getDiscountLevel() {
    if (quantity > 100) {
      return 2;
    }
    else {
      return 1;
    }
  }
  private double getBasePrice() {
    return quantity * itemPrice;
  }
}
```

###

Set step 1


#|en| Let's look at this refactoring using yet another order price example.


Select name of "getPrice"
+Select name of "discountedPrice"


#|en|^ The method for getting the discount (<code>discountedPrice</code>) is currently nearly impossible to use separately from the method for getting the price (<code>getPrice</code>), since you must get the values of all parameters prior to it.


Select parameters of "discountedPrice"


#|en| But what if we eliminate all parameters in <code>discountedPrice</code>? Let's try.


Select:
```
    int discountLevel;
    if (quantity > 100) {
      discountLevel = 2;
    }
    else {
      discountLevel = 1;
    }

```

Set step 2


#|en| To start, we extract <code>discountLevel</code> to its own method.


Go to after "discountedPrice"

Print:
```

  private int getDiscountLevel() {
    if (quantity > 100) {
      return 2;
    }
    else {
      return 1;
    }
  }
```

Set step 3

Select "discountLevel" in body of "discountedPrice"


#|en| Now we can use this method instead of this parameter in the discount calculation method.


Print "getDiscountLevel()"

Set step 4

Select ", int discountLevel" in parameters of "discountedPrice"


#|en| One of the parameters is no longer needed so we can use <a href="/remove-parameter">Remove Parameter</a>


Remove selected

Select ", discountLevel"

Wait 500ms

Remove selected

Wait 500ms

Select:
```
    int discountLevel;
    if (quantity > 100) {
      discountLevel = 2;
    }
    else {
      discountLevel = 1;
    }

```


#|en| We can then remove parameter calculation, which is no longer used.


Remove selected


#C|en| Let's run the compiler and auto-tests.
#S Everything is good! Let's continue.


Select parameters of "discountedPrice"


#|en| One parameter, one more to go…


Select "quantity * itemPrice"


#|en| Let's extract the base price calculation to its own method.


Go to after "getDiscountLevel"

Print:
```

  private double getBasePrice() {
    return quantity * itemPrice;
  }
```

Select "basePrice" in body of "discountedPrice"


#|en| Now use this method in <code>discountedPrice</code>.


Print "getBasePrice()"

Wait 250ms

Select "int basePrice" in parameters of "discountedPrice"


#|en| As before, we can now get rid of this parameter as well.


Remove selected

Select "discountedPrice(|||basePrice|||)"

Wait 500ms

Remove selected

Wait 500ms

Select:
```
    int basePrice = quantity * itemPrice;

```


#|en| Then clean up the code of the method for getting the price…


Remove selected

Select body of "getPrice"


#|en| …or if we make it a bit more pretty:


Print "    return discountedPrice();"



#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
