remove-control-flag:java

###

1. Find the value assignment to the control flag that causes the exit from the loop or current iteration.

2. Replace it with `break` if exiting a loop, `continue` if exiting an iteration, or `return` if needing to return a value from the function.

3. Remove the remaining code and checks associated with the control flag.



###

```
void checkSecurity(String[] people) {
  boolean found = false;
  for (int i = 0; i < people.length; i++) {
    if (!found) {
      if (people[i].equals ("Don")) {
        sendAlert();
        found = true;
      }
      if (people[i].equals ("John")) {
        sendAlert();
        found = true;
      }
    }
  }
}
```

###

```
void checkSecurity(String[] people) {
  for (int i = 0; i < people.length; i++) {
    if (people[i].equals ("Don")) {
      sendAlert();
      break;
    }
    if (people[i].equals ("John")) {
      sendAlert();
      break;
    }
  }
}
```

###

###### Set step 1


#|en| The following function checks whether the list of people contains anybody suspicious; these suspicious names (Don and John) are hard-coded.

Select "|||found||| = false"


#|en| In this function, the variable `found` is a control flag. It is initialized by one value…


Select "|||found||| = true"


#|en| …which changes as the function is run…


Select "(!found)"


#|en| …after which the code does not do anything more until the loop is finished.

Select "found = true"


#|en| This refactoring starts with us looking for any assignments to the control variable that affect the execution flow of the program. In our case, this is assignments of the `true` value.

###### Set step 2


#|en| According to the logic of this method, we can simply replace assignments to control flags with `break` operator.

Print "break"

###### Set step 3


#|en| Then we can remove all other mentions of the control flag.


Select:
```
    if (!found) {

```

+ Select:
```
      }
|||    }
|||  }
```

Remove selected

Select "      "

Replace "    "

Wait 500ms

Select:
```
  boolean found = false;

```

Remove selected


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
