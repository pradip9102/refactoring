consolidate-duplicate-conditional-fragments:java

---

1. If the duplicated code is at the beginning of the conditional branches, move the code to a place before the conditional.

2. If the code is executed at the end of the branches, place it after the conditional.

3. If the duplicate code is randomly situated inside the branches, first try to move the code to the beginning or end of the branch, depending on whether it changes the result of the subsequent code.

4. If appropriate and the duplicate code is longer than one line, try using <a href="/extract-method">Extract Method</a>.



---

```
double sendEmailPromotion() {
  // ...
  if (isSpecialDeal()) {
    total = price * 0.95;
    validateEmailAddress();
    sendEmail();
  }
  else {
    validateEmailAddress();
    total = price * 0.98;
    sendEmail();
  }
}
```

---

```
double sendEmailPromotion() {
  // ...
  validateEmailAddress();
  if (isSpecialDeal()) {
    total = price * 0.95;
  }
  else {
    total = price * 0.98;
  }
  sendEmail();
}
```

---

###### Set step 1

Select "sendEmail();"
+ Select "validateEmailAddress();"


#|en| As you see, our method has a conditional with repeating code in different branches.


#|en| Let's move this code outside the conditional. We start with `sendEmail()`.

Select:
```
    sendEmail();

```


#|en| The call to this function is at the end of both branches of the conditional operator. That means that we can move the call after the condition.

Remove selected

Go to the end of "sendEmailPromotion"

Print:
```

  sendEmail();
```


#C|en| Let's run the compiler and auto-tests.
#S So far so good!


Select:
```
    validateEmailAddress();

```


#|en| Now let's try to move `validateEmailAddress()`. These calls are in different places, so it is worth thinking about whether to move them at all. In our case, validation can be performed anywhere but preferably closer to the beginning of the method. So let's move it there.

Remove selected

Go to:
```
  // ...|||
  if
```

Print:
```

  validateEmailAddress();
```


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
