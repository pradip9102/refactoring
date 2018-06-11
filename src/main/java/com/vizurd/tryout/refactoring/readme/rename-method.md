rename-method:java

###

1. See whether the method is defined in a superclass or subclass. If so, you must repeat all steps in these classes too.

2. The next method is important for maintaining the functionality of the program during the refactoring process. Create a new method with a new name. Copy the code of the old method to it. Delete all the code in the old method and, instead of it, insert a call for the new method.

3. Find all references to the old method and replace them with references to the new one.

4. Delete the old method. This step is not possible if the old method is part of the public interface. In that case, mark the old method as `deprecated`.



###

```
class Person {
  //…
  public String getTelephoneNumber() {
    return ("(" + officeAreaCode + ") " + officeNumber);
  }
}

// Client code
phone = employee.getTelephoneNumber();
```

###

```
class Person {
  //…
  public String getOfficeTelephoneNumber() {
    return ("(" + officeAreaCode + ") " + officeNumber);
  }
}

// Client code
phone = employee.getOfficeTelephoneNumber();
```

###

###### Set step 1

Select name of "getTelephoneNumber"


#|en| There is a method for getting the phone number of a certain person. The method is not overridden anywhere so we do not need to track changes in subclasses.

###### Set step 2


#|en| Let's change it's name to `getOfficeTelephoneNumber`, a more descriptive name.

Go to the end of "Person"


#|en| Start by creating a new method and copying the body to the new method.

Print:
```

  public String getOfficeTelephoneNumber() {
    return ("(" + officeAreaCode + ") " + officeNumber);
  }
```


#|en| Then we change the old method so that it call the new one. That might look to you as a useless step, but it will help to keep the code working while you search for all calls of the old method and replace them with the new method calls.

Select body of "getTelephoneNumber"

Replace "    getOfficeTelephoneNumber();"

###### Set step 3


#|en| So, we find the places where the old method is called, modifying them to call the new method instead.

Select "employee.|||getTelephoneNumber|||()"

Replace "getOfficeTelephoneNumber"

###### Set step 4

Select whole "getTelephoneNumber"


#|en| After all changes have been made, we can go ahead and delete the old method.

Remove selected


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
