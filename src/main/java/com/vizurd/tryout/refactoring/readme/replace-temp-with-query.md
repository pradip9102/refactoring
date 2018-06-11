replace-temp-with-query:java

---

1. Make sure that a value is assigned to the variable once and only once within the method.

2. Use <b>extract method</b> to move the expression in question to the new method.

3. Replace the variable with a query to your new method.



---

```
class Product {
  // ...
  public double getPrice() {
    int basePrice = quantity * itemPrice;
    double discountFactor;
    if (basePrice > 1000) {
      discountFactor = 0.95;
    }
    else {
      discountFactor = 0.98;
    }
    return basePrice * discountFactor;
  }
}
```

---

```
class Product {
  // ...
  public double getPrice() {
    return basePrice() * discountFactor();
  }
  private int basePrice() {
    return quantity * itemPrice;
  }
  private double discountFactor() {
    if (basePrice() > 1000) {
      return 0.95;
    }
    else {
      return 0.98;
    }
  }
}
```

---

###### Set step 1


#|en| Let's look at *Replace Temp with Query*  using a simple method as an example.

Select "int |||basePrice|||"
+Select "double |||discountFactor|||"


#|en| Replace the variables `basePrice` and `discountFactor` one by one with calls to the respective methods.


#|en| First, make sure that there is just one value assignment to the variable within the method.


#|en| That is already apparent here, but to be safe, we can declare these variables with the keyword `final`. In this case, the compiler will flag all places where attempts are made to re-assign values to variables.

Go to "|||int basePrice"

Print "final "

Wait 500ms

Go to "|||double discountFactor"

Print "final "


#C|en| Compile and verify that nothing has gone astray.
#S <b>Everything's A-OK! We can keep going.</b><br/><br/>Keep in mind that this check is very important! If issues arise during this step, you should refrain from using this refactoring technique.

###### Set step 2

Select "basePrice = quantity * itemPrice"


#|en| For the second step, we create a `basePrice()` method and move the expression forming the `basePrice` variable to it.

Go to the end of "Product"

Print:
```

  private int basePrice() {
    return quantity * itemPrice;
  }
```

Select "basePrice = |||quantity * itemPrice|||"


#|en| Now we can use a method call instead of the initial expression. Thus, we now have a new method and all of the old code still works.

Print "basePrice()"

###### Set step 3

Select "(|||basePrice||| >"


#|en| Now is the perfect time to replace the variable with a direct method call.


#|en| Replace the first variable and then compile to make sure that nothing is broken.

Print "basePrice()"


#C|en| Let's run the compiler and auto-tests.
#S Everything is OK! We can keep going.

Select "return |||basePrice|||"


#|en| Perform the next replacement.

Print "basePrice()"


#C|en| Let's run the compiler and auto-tests.
#S Everything is OK! We can keep going.

Select:
```
    final int basePrice = basePrice();

```


#|en| The previous replacement was the last one, so we can remove the variable declaration.

Remove selected

Select "double |||discountFactor|||"


#|en| The first variable is done. We can repeat all this to extract `discountFactor`.

Go to the end of "Product"


#|en| Create a new method…


Print:
```

  private double discountFactor() {
    if (basePrice() > 1000) {
      return 0.95;
    }
    else {
      return 0.98;
    }
  }
```

Go to "double discountFactor|||;"


#|en| …use it to initialize the variable…


Print " = discountFactor()"

Select:
```
    if (basePrice() > 1000) {
      discountFactor = 0.95;
    }
    else {
      discountFactor = 0.98;
    }

```


#|en|^ …and remove the code that is no longer necessary.

Remove selected


#|en| Note how difficult it would have been to extract `discountFactor` if we had not first replaced `basePrice` with a method call.


#|en| Ultimately the `getPrice()` method comes to look as follows.

Select:
```
    final double discountFactor = discountFactor();

```

Remove selected

Select "discountFactor" in "getPrice"

Replace "discountFactor()"


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
