extract-variable:java

###

1. Create a local variable and give it the necessary value.


2. Replace the original expression with your new variable.




###

```
double price() {
  // Price consists of: base price - discount + shipping cost
  return quantity * itemPrice -
    Math.max(0, quantity - 500) * itemPrice * 0.05 +
    Math.min(quantity * itemPrice * 0.1, 100.0);
}
```

###

```
double price() {
  final double basePrice = quantity * itemPrice;
  final double quantityDiscount = Math.max(0, quantity - 500) * itemPrice * 0.05;
  final double shipping = Math.min(basePrice * 0.1, 100.0);
  return basePrice - quantityDiscount + shipping;
}
```

###

Set step 1


#|en| Let's look at *Extract Variable*  using this simple method as an example.



#|en| As you see, the method contains a single enormous expression capable of throwing off both police dogs and the most determined programmers.



#|en| Let's split this expression into separate parts, placing each part in a separate variable.


Select "quantity * itemPrice"


#|en| First define the `basePrice` as the number of products in the order, multiplied by the unit cost…


Set step 2

Go to "shipping cost|||"

Print:
```

  final double basePrice = quantity * itemPrice;
```

Select 2nd "quantity * itemPrice"
+ Select 3rd "quantity * itemPrice"


#|en| …and use the new variable in the formula. The expression is repeated several times so we will replace all identical calculations with the variable.


Print "basePrice"


#C|en| Compile and verify that nothing has gone astray.
#S Everything is OK! We can keep going.


Select:
```
    Math.max(0, quantity - 500) * itemPrice * 0.05 +
    Math.min(basePrice * 0.1, 100.0);
```


#|en| Now replace the remaining parts of the complex expression with variables.


Select:
```
Math.max(0, quantity - 500) * itemPrice * 0.05
```


#|en| Define the `quantityDiscount` and move calculation to a new variable.


Go to "quantity * itemPrice;|||"

Print:
```

  final double quantityDiscount = Math.max(0, quantity - 500) * itemPrice * 0.05;
```

Select:
```

    Math.max(0, quantity - 500) * itemPrice * 0.05
```

Replace " quantityDiscount"


#C|en| All done. Let's compile and check for errors.
#S Everything is OK! We can keep going.


Select:
```
Math.min(basePrice * 0.1, 100.0)
```


#|en| The final part of calculation is `shipping`. We use a separate variable here as well.


Go to "itemPrice * 0.05;|||"

Print:
```

  final double shipping = Math.min(basePrice * 0.1, 100.0);
```

Select:
```

    Math.min(basePrice * 0.1, 100.0)
```

Replace " shipping"

Select:
```
  // Price consists of: base price - discount + shipping cost

```


#|en| Since the expression is now obvious and intuitive, we can remove the comment.


Remove selected


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
