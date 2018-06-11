extract-class:java

---

1. Create a new class to contain the relevant functionality.

2. Create a relationship between the old class and the new one.

3. Use <a href="/move-field">move field</a> and <a href="/move-method">move method</a> for each field that method that you have decided to move to a new class.

4. Decide whether to make the new class accessible from outside the object of the old class.



---

```
class Person {
  private String name;
  private String officeAreaCode;
  private String officeNumber;

  public String getName() {
    return name;
  }
  public String getTelephoneNumber() {
    return ("(" + officeAreaCode + ") " + officeNumber);
  }
  public String getOfficeAreaCode() {
    return officeAreaCode;
  }
  public void setOfficeAreaCode(String arg) {
    officeAreaCode = arg;
  }
  public String getOfficeNumber() {
    return officeNumber;
  }
  public void setOfficeNumber(String arg) {
    officeNumber = arg;
  }
}
```

---

```
class Person {
  private String name;
  private TelephoneNumber officeTelephone = new TelephoneNumber();

  public String getName() {
    return name;
  }
  public TelephoneNumber getOfficeTelephone() {
    return officeTelephone;
  }
  public String getTelephoneNumber() {
    return officeTelephone.getTelephoneNumber();
  }
}

class TelephoneNumber {
  private String areaCode;
  private String number;

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
```

---

###### Set step 1


#|en| Let's look at *Extract Class*  using the example of a simple class that describes a person.

Select:
```
  private String |||officeAreaCode|||;
  private String |||officeNumber|||;
```
+ Select name of "getTelephoneNumber"
+ Select name of "getOfficeAreaCode"
+ Select name of "setOfficeAreaCode"
+ Select name of "getOfficeNumber"
+ Select name of "setOfficeNumber"


#|en| In this example, we can isolate methods related to phone numbers to a separate class.

Go to the end of file


#|en| Let's start by defining the phone number class.

Print:
```


class TelephoneNumber {
}
```

###### Set step 2

Select name of "Person"


#|en| Easy as pie! Now we create a reference from the `Person` class to the phone number class.

Go to "private String officeNumber;|||"

Print:
```

  private TelephoneNumber officeTelephone = new TelephoneNumber();
```

###### Set step 3

Select "private String |||officeAreaCode|||"
+ Select name of "getOfficeAreaCode"
+ Select name of "setOfficeAreaCode"


#|en| Everything is ready to start moving fields and methods. We use <a href="/move-field">Move Field</a> to move the `officeAreaCode` field to the `TelephoneNumber` class.

Go to the start of "TelephoneNumber"

Print:
```

  private String areaCode;

  public String getAreaCode() {
    return areaCode;
  }
  public void setAreaCode(String arg) {
    areaCode = arg;
  }
```

Select "areaCode" in "TelephoneNumber"


#|en| Did you notice? We immediately renamed the field to be more neutral. That improves our chances of reusing the class.

Select name of "getOfficeAreaCode"
+ Select name of "setOfficeAreaCode"


#|en| Now we should change the methods, which used the moved field so that they access it through a `TelephoneNumber` object.

Select body of "getOfficeAreaCode"

Replace:
```
    return officeTelephone.getAreaCode();
```

Wait 500ms

Select body of "setOfficeAreaCode"

Replace:
```
    officeTelephone.setAreaCode(arg);
```


#|en| We can also turn all cases of direct field access to the proper getter/setter calls.

Select "officeAreaCode" in "getTelephoneNumber"

Replace "getOfficeAreaCode()"

Select:
```
  private String officeAreaCode;

```


#|en| At last, we can remove the field from the original class.

Remove selected

Select "private String |||officeNumber|||"


#|en| The `areaCode` is all done. Similarly, we move the `officeNumber` field…


Go to "private String areaCode;|||"

Print:
```

  private String number;
```

Go to the end of "TelephoneNumber"

Print:
```

  public String getNumber() {
    return number;
  }
  public void setNumber(String arg) {
    number = arg;
  }
```

Select name of "getTelephoneNumber"


#|en| …and the method for getting the formatted phone number `getTelephoneNumber()`.

Go to the end of "TelephoneNumber"

Print:
```

  public String getTelephoneNumber() {
    return ("(" + areaCode + ") " + number);
  }
```

Select "private String |||officeNumber|||"


#|en| After that, we can delegate all phone functionality to the `TelephoneNumber` class.

Select body of "getTelephoneNumber"

Replace:
```
    return officeTelephone.getTelephoneNumber();
```

Select body of "getOfficeNumber"

Replace:
```
    return officeTelephone.getNumber();
```

Select body of "setOfficeNumber"

Replace:
```
    officeTelephone.setNumber(arg);
```

Select:
```
  private String officeNumber;

```

Remove selected


#C|en| Let's run the compiler to verify that the code is not broken anywhere.
#S Everything is OK! Code works correctly.

###### Set step 4

Select "private TelephoneNumber officeTelephone"


#|en|+ Here we should decide how available we want this new field to be for a client code. We can hide it entirely using delegation methods for accessing all the fields (as is currently done)…


Select whole "getOfficeAreaCode"
+ Select whole "setOfficeAreaCode"
+ Select whole "getOfficeNumber"
+ Select whole "setOfficeNumber"


#|en|= …or remove all these methods and make the field public.

Remove selected



#|en| To do this, we will need to create a public getter for the associated object so that clients can access it.

Go to before "getTelephoneNumber"

Print:
```

  public TelephoneNumber getOfficeTelephone() {
    return officeTelephone;
  }
```

Select name of "getOfficeTelephone"


#|en| But if we want to make the field public, let's consider some of the dangers related to object references. What about the fact that the client can change the area code when opening a phone number? Any code that has access to a class instance via the public getter could perform such change.


#|en| The following options are possible: <ul><li>Any object can change any part of the phone number. In this case the phone number becomes a reference and you should look at <a href="/change-value-to-reference">Change Value to Reference</a>. Access to the phone number is implemented through an instance of `Person`.</li><li>We do not want anyone to be able to change a phone number except through the methods of an instance of the `Person` class. The phone number can be made read-only or access to it can be limited to an appropriate method.</li><li>We can also clone an instance of the `TelephoneNumber` class before providing access to it. But this can cause confusion because people will think that they can change this value. In addition, clients may have problems with references if the phone number is frequently passed .</li></ul>



#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
