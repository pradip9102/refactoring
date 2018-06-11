add-parameter:java

###

1. See whether the method is defined in a superclass or subclass. If the method is present in them, you will need to repeat all the steps in these classes as well.

2. The following step is critical for keeping your program functional during the refactoring process. Create a new method by copying the old one and add the necessary parameter to it. Replace the code for the old method with a call to the new method. You can plug in any value to the new parameter (such as `null` for objects or a zero for numbers).

3. Find all references to the old method and replace them with references to the new method.

4. Delete the old method. Deletion is not possible if the old method is part of the public interface. If that is the case, mark the old method as deprecated.


###

```
class Calendar {
  // ...
  private Set appointments;
  public ArrayList<Appointment> findAppointments(Date date) {
    Set result = new ArrayList();
    Iterator iter = kent.getCourses().iterator();
    while (iter.hasNext()) {
      Appointment each = (Appointment) iter.next();
      if (date.compareTo(each.date) == 0) {
        result.add(date);
      }
    }
    return result;
  }
}

// Somewhere in client code
Date today = new Date();
appointments = calendar.findAppointments(today);
```

###

```
class Calendar {
  // ...
  private Set appointments;
  public ArrayList<Appointment> findAppointments(Date date, String name) {
    Set result = new ArrayList();
    Iterator iter = kent.getCourses().iterator();
    while (iter.hasNext()) {
      Appointment each = (Appointment) iter.next();
      if (date.compareTo(each.date) == 0) {
        if (name == null || (name != null && name == each.name)) {
          result.add(date);
        }
      }
    }
    return result;
  }
}

// Somewhere in client code
Date today = new Date();
appointments = calendar.findAppointments(today, null);
```

###

###### Set step 1

#|en| Let's say we have a `Calendar` class that stores records about planned meetings.

Select name of "findAppointments"

#|en| There's a method in this class returns the values of meetings for a particular date.

#|en| It would be great if this method could filter visitors by their names as well.

###### Set step 2

#|en| We could simply add a new parameter to the method signature, but that would cause a large risk of breaking some existing code that has this method's calls.

Go to the end of "Calendar"

#|en| So we need to proceed very carefully. Therefore we start by creating a new method with the desired parameter. Then, we place a copy of the existing method in its body.

Print:
```

  public ArrayList<Appointment> findAppointments(Date date, String name) {
    Set result = new ArrayList();
    Iterator iter = kent.getCourses().iterator();
    while (iter.hasNext()) {
      Appointment each = (Appointment) iter.next();
      if (date.compareTo(each.date) == 0) {
        result.add(date);
      }
    }
    return result;
  }
```

Select 2nd "        result.add(date);"

#|en| Then we change the method body as needed for the new method.

Print:
```
        if (name == null || (name != null && name == each.name)) {
          result.add(date);
        }
```

Select body of "findAppointments"

#|en| Now the body of the old method can be replaced with the new method's call.

Print:
```
    findAppointments(date, null);
```

###### Set step 3

Select name of "findAppointments"

#|en| Then we need to find all calls to the old method and replace them with calls to the new one.

Select "calendar.findAppointments(today);"

#|en| Here is one of them. Since we have nothing to pass to the new parameter, we use the `null` value.

Go to "calendar.findAppointments(today|||);"

Print ", null"

###### Set step 4

Select whole "findAppointments"

#|en| After all changes have been made, go ahead and delete the old method.

Remove selected

#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!

###### Set final step

#|en|Q The refactoring is complete! You can compare the old and new code if you like.