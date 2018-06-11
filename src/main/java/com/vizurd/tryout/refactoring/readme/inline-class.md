inline-class:java

---

1. In the recipient class, create the public fields and methods present in the donor class. Methods should refer to the equivalent methods of the donor class.

2. Replace all references to the donor class with references to the fields and methods of the recipient class.

3. Use <a href="/move-field">move field</a> and <a href="/move-method">move field</a> for moving functionality from the original class to the recipient class. Continue doing so until nothing remains in the original class.

4. Delete the original class.



---

```
class Person {
  private String name;
  private TelephoneNumber officeTelephone = new TelephoneNumber();

  public String getName() {
    return name;
  }
  public String getTelephoneNumber() {
    return officeTelephone.getTelephoneNumber();
  }
  public TelephoneNumber getOfficeTelephone() {
    return officeTelephone;
  }
}

class TelephoneNumber {
  private String number;
  private String areaCode;

  public String getAreaCode() {
    return areaCode;
  }
  public void setAreaCode(String arg) {
    areaCode = arg;
  }
  public String getNumber() {
    return number;
  }
  public void setNumber(String arg) {
    number = arg;
  }
  public String getTelephoneNumber() {
    return ("(" + areaCode + ") " + number);
  }
}

// Somewhere in client code
Person martin = new Person();
martin.getOfficeTelephone().setAreaCode("781");
```

---

```
class Person {
  private String name;
  private String number;
  private String areaCode;

  public String getName() {
    return name;
  }
  public String getTelephoneNumber() {
    return ("(" + areaCode + ") " + number);
  }
  public String getAreaCode() {
    return areaCode;
  }
  public void setAreaCode(String arg) {
    areaCode = arg;
  }
  public String getNumber() {
    return number;
  }
  public void setNumber(String arg) {
    number = arg;
  }
}


// Somewhere in client code
Person martin = new Person();
martin.setAreaCode("781");
```

---

###### Set step 1


#|en| Let's look at *Inline Class*  using the person class and its phone number as an example.

Select name of "TelephoneNumber"


#|en| We want to include the `TelephoneNumber` class back in the `Person` class, since it become unnecessary complex for our needs.

Go to the end of "Person"


#|en| We start by declaring all visible methods of the phone number class in the `Person` class.

Print:
```

  public String getAreaCode() {
    return officeTelephone.getAreaCode();
  }
  public void setAreaCode(String arg) {
    officeTelephone.setAreaCode(arg);
  }
  public String getNumber() {
    return officeTelephone.getNumber();
  }
  public void setNumber(String arg) {
    officeTelephone.setNumber(arg);
  }
```

Select "officeTelephone" in "getAreaCode"
+Select "officeTelephone" in "setAreaCode"
+Select "officeTelephone" in "getNumber"
+Select "officeTelephone" in "setNumber"


#|en| For the first step, all these methods will delegate to the phone number object.

###### Set step 2

Select "martin.getOfficeTelephone().setAreaCode("781")"


#|en| Now find all cases where the phone number class is used in client code and replace it with calls to the delegate methods in `Person`.

Print "martin.setAreaCode("781")"

###### Set step 3


#|en| We can then proceed to <a href="/move-method">Move Method</a> and <a href="/move-field">Move Field</a> for moving all fields and methods to the `Person` class. These changes can be done one by one or, if there are not too many, all at once.

Select:
```
  private String number;
  private String areaCode;


```


#|en| First move the fields.

Remove selected

Go to " new TelephoneNumber();|||"

Print:
```

  private String number;
  private String areaCode;
```

Select body of "getAreaCode" in "TelephoneNumber"


#|en| Then move each method…


Wait 500ms

Select body of "getAreaCode" in "Person"

Replace:
```
    return areaCode;
```

Select whole of "getAreaCode" in "TelephoneNumber"

Remove selected


Select body of "setAreaCode" in "TelephoneNumber"


#|en| …one by one…


Wait 500ms

Select body of "setAreaCode" in "Person"

Replace:
```
    areaCode = arg;
```

Select whole of "setAreaCode" in "TelephoneNumber"

Remove selected


Select body of "getNumber" in "TelephoneNumber"


#|en| …move all the methods…


Wait 500ms

Select body of "getNumber" in "Person"

Replace:
```
    return number;
```

Select whole of "getNumber" in "TelephoneNumber"

Remove selected

Select body of "setNumber" in "TelephoneNumber"


#|en| …each and every one…



Wait 500ms

Select body of "setNumber" in "Person"

Replace:
```
    number = arg;
```

Select whole of "setNumber" in "TelephoneNumber"

Remove selected

Select body of "getTelephoneNumber" in "TelephoneNumber"


#|en| …and finally the last getter of the phone number itself.

Wait 500ms

Select body of "getTelephoneNumber" in "Person"

Replace:
```
    return ("(" + areaCode + ") " + number);
```

Select whole of "getTelephoneNumber" in "TelephoneNumber"

Remove selected


#C|en| Now is a good time to compile and test, to make sure the code is still working correctly.
#S All is well, so let's continue.

###### Set step 4

Select whole "TelephoneNumber"


#|en| At this point, we need only to remove the `TelephoneNumber` class from the program.

Select:
```
  private TelephoneNumber officeTelephone = new TelephoneNumber();

```
+ Select whole "getOfficeTelephone"


#|en| Start by removing its field and getter in the `Person` class.

Remove selected

Select whole "TelephoneNumber"


#|en| Voila! Nothing is holding us back now from removing the class itself. Thank you for the good times, `TelephoneNumber`, they were good indeed!


Remove selected


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
