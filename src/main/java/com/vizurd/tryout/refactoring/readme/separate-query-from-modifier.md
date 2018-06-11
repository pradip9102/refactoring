separate-query-from-modifier:java

---

1. Create a new *query method*  to return what the original method did.

2. Change the original method so that it returns only the result of calling the new *query method* .

3. Replace all references to the original method with a call to the *query method*  but immediately before this line, insert a call to the original method.

4. Get rid of the value-returning code in the original method, which now has become a proper *modifier method* .



---

```
class Guard {
  // ...
  public void checkSecurity(String[] people) {
    String found = findCriminalAndAlert(people);
    someLaterCode(found);
  }
  public String findCriminalAndAlert(String[] people) {
    for (int i = 0; i < people.length; i++) {
      if (people[i].equals("Don")) {
        sendAlert();
        return "Don";
      }
      if (people[i].equals("John")) {
        sendAlert();
        return "John";
      }
    }
    return "";
  }
}
```

---

```
class Guard {
  // ...
  public void checkSecurity(String[] people) {
    doSendAlert(people);
    String found = findCriminal(people);
    someLaterCode(found);
  }
  public void doSendAlert(String[] people) {
    if (findCriminal(people) != "") {
      sendAlert();
    }
  }
  public String findCriminal(String[] people) {
    for (int i = 0; i < people.length; i++) {
      if (people[i].equals ("Don")) {
        return "Don";
      }
      if (people[i].equals ("John")) {
        return "John";
      }
    }
    return "";
  }
}
```

---

###### Set step 1

Select name of "findCriminalAndAlert"


#|en| Let's look at *Separate Query from Modifier*  refactoring using a security system class as our example. The class has a method that tells us the name of a violator and sends a warning.


#|en| The main problem with this method is that it is used for two different purposes.

Select "return "Don""
+ Select "return "John""


#|en| First, it finds and returns a list of names that are then used for different purposes.

Select "found" in "checkSecurity"


#|en| An example of such use can be found in the `checkSecurity` method.

Select "sendAlert()" in "findCriminalAndAlert"


#|en| Secondly, it alerts about the people found.


#|en| With this approach, even if we only need to get a list of names we take the risk of accidentally sending alerts. This refactoring technique will save us from that risk. In our case, searching for people will be the "query" and sending alerts will be the "modifier".

Go to the end of "Guard"


#|en| To separate the query from the modifier, first create an appropriate query that returns the same value as the original method, but does not lead to side effects.

Print:
```

  public String findCriminal(String[] people) {
    for (int i = 0; i < people.length; i++) {
      if (people[i].equals ("Don")) {
        return "Don";
      }
      if (people[i].equals ("John")) {
        return "John";
      }
    }
    return "";
  }
```

###### Set step 2

Select "return" in "findCriminalAndAlert"


#|en| Then, one by one, replace all cases of `return` in the original method with calls for the new query.

Select "return |||"Don"|||" in "findCriminalAndAlert"

Replace "findCriminal(people)"

Select "return |||"John"|||" in "findCriminalAndAlert"

Replace "findCriminal(people)"

Select "return |||""|||" in "findCriminalAndAlert"

Replace "findCriminal(people)"

###### Set step 3

Select:
```
    String found = findCriminalAndAlert(people);
```


#|en| Now change all the methods from which reference is made so that two calls occur in them: first for the modifier, then for the query.

Select:
```
    |||String found = |||findCriminalAndAlert(people);
```

Remove selected

Go to "findCriminalAndAlert(people);|||"

Print:
```

    String found = findCriminal(people);
```

###### Set step 4

Select type of "findCriminalAndAlert"


#|en| Once this has been completed for all calls, we remove the return code from the modifier.

Print "void"

Select in "findCriminalAndAlert":
```
        return findCriminal(people);

```
+Select in "findCriminalAndAlert":
```
    return findCriminal(people);

```

Remove selected

Select name of "findCriminalAndAlert"


#|en| We should also now change the name of the original method for consistency.

Print "doSendAlert"

Select "findCriminalAndAlert"

Replace "doSendAlert"

Select body of "doSendAlert"


#|en| Of course, the result contains a great deal of duplicate code since the modifier still uses the body of the query. But now we can apply <a href="/substitute-algorithm">Substitute Algorithm</a> without the risk of breaking any other code.

Print:
```
    if (findCriminal(people) != "") {
      sendAlert();
    }
```


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
