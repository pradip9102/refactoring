duplicate-observed-data:java

###

1. Create a domain class.


2. Use the Observer pattern. Make the user interface class an observer of the domain class.


3. Hide direct access to the fields of the user interface.


4. Use setters to set the values of the fields in response to the user's activity in the interface.


5. Move the necessary fields from the GUI class to the domain class. Change the access methods in the interface class so that they refer to the fields of the domain class.




###

```
class IntervalWindow extends Frame {
  java.awt.TextField startField;
  java.awt.TextField endField;
  java.awt.TextField lengthField;

  public IntervalWindow() {
    startField = new java.awt.TextField();
    endField = new java.awt.TextField();
    lengthField = new java.awt.TextField();
    SymFocus focusListener = new SymFocus();
    startField.addFocusListener(focusListener);
    endField.addFocusListener(focusListener);
    lengthField.addFocusListener(focusListener);
  }

  class SymFocus extends java.awt.event.FocusAdapter {
    public void focusLost(java.awt.event.FocusEvent event) {
      Object object = event.getSource();
      if (object == startField) {
        StartField_FocusLost(event);
      }
      else if (object == endField) {
        EndField_FocusLost(event);
      }
      else if (object == lengthField) {
        LengthField_FocusLost(event);
      }
    }

    void StartField_FocusLost(java.awt.event.FocusEvent event) {
      if (isNotInteger(startField.getText())) {
        startField.setText("0");
      }
      calculateLength();
    }

    void EndField_FocusLost(java.awt.event.FocusEvent event) {
      if (isNotInteger(endField.getText())) {
        endField.setText("0");
      }
      calculateLength();
    }

    void LengthField_FocusLost(java.awt.event.FocusEvent event) {
      if (isNotInteger(lengthField.getText())) {
        lengthField.setText("0");
      }
      calculateEnd();
    }

    void calculateLength() {
      try {
        int start = Integer.parseInt(startField.getText());
        int end = Integer.parseInt(endField.getText());
        int length = end - start;
        lengthField.setText(String.valueOf(length));
      } catch (NumberFormatException e) {
        throw new RuntimeException ("Unexpected Number Format Error");
      }
    }
    void calculateEnd() {
      try {
        int start = Integer.parseInt(startField.getText());
        int length = Integer.parseInt(lengthField.getText());
        int end = start + length;
        endField.setText(String.valueOf(end));
      } catch (NumberFormatException e) {
        throw new RuntimeException ("Unexpected Number Format Error");
      }
    }
  }
}
```

###

```
class IntervalWindow extends Frame implements Observer {
  java.awt.TextField startField;
  java.awt.TextField endField;
  java.awt.TextField lengthField;
  private Interval subject;

  public IntervalWindow() {
    startField = new java.awt.TextField();
    endField = new java.awt.TextField();
    lengthField = new java.awt.TextField();
    SymFocus focusListener = new SymFocus();
    startField.addFocusListener(focusListener);
    endField.addFocusListener(focusListener);
    lengthField.addFocusListener(focusListener);

    subject = new Interval();
    subject.addObserver(this);
    update(subject, null);
  }

  class SymFocus extends java.awt.event.FocusAdapter {
    public void focusLost(java.awt.event.FocusEvent event) {
      Object object = event.getSource();
      if (object == startField) {
        StartField_FocusLost(event);
      }
      else if (object == endField) {
        EndField_FocusLost(event);
      }
      else if (object == lengthField) {
        LengthField_FocusLost(event);
      }
    }

    void StartField_FocusLost(java.awt.event.FocusEvent event) {
      setStart(startField.getText());
      if (isNotInteger(getStart())) {
        setStart("0");
      }
      subject.calculateLength();
    }

    void EndField_FocusLost(java.awt.event.FocusEvent event) {
      setEnd(endField.getText());
      if (isNotInteger(getEnd())) {
        setEnd("0");
      }
      subject.calculateLength();
    }

    void LengthField_FocusLost(java.awt.event.FocusEvent event) {
      setLength(lengthField.getText());
      if (isNotInteger(getLength())) {
        setLength("0");
      }
      subject.calculateEnd();
    }
  }

  public void update(Observable observed, Object arg) {
    endField.setText(subject.getEnd());
    startField.setText(subject.getStart());
    lengthField.setText(subject.getLength());
  }

  String getEnd() {
    return subject.getEnd(arg);
  }
  void setEnd(String arg) {
    subject.setEnd(arg);
  }
  String getStart() {
    return subject.getStart(arg);
  }
  void setStart(String arg) {
    subject.setStart(arg);
  }
  String getLength() {
    return subject.getLength(arg);
  }
  void setLength(String arg) {
    subject.setLength(arg);
  }
}

class Interval extends Observable {
  private String end = "0";
  private String start = "0";
  private String length = "0";

  String getEnd() {
    return end;
  }
  void setEnd(String arg) {
    end = arg;
    setChanged();
    notifyObservers();
  }
  String getStart() {
    return start;
  }
  void setStart(String arg) {
    start = arg;
    setChanged();
    notifyObservers();
  }
  String getLength() {
    return length;
  }
  void setLength(String arg) {
    length = arg;
    setChanged();
    notifyObservers();
  }

  void calculateLength() {
    try {
      int start = Integer.parseInt(getStart());
      int end = Integer.parseInt(getEnd());
      int length = end - start;
      setLength(String.valueOf(length));
    } catch (NumberFormatException e) {
      throw new RuntimeException ("Unexpected Number Format Error");
    }
  }
  void calculateEnd() {
    try {
      int start = Integer.parseInt(getStart());
      int length = Integer.parseInt(getLength());
      int end = start + length;
      setEnd(String.valueOf(end));
    } catch (NumberFormatException e) {
      throw new RuntimeException ("Unexpected Number Format Error");
    }
  }
}
```

###

Set step 1

Select name of "class IntervalWindow"


#|en| Let's look at *Duplicate Observed Data*  using the class that creates a window for editing numeric intervals.


Select 1st "lengthField"
+ Select 1st "startField"
+ Select 1st "endField"


#|en|< The window consists of three parts: Start value, End value, and Length. <br/><img src="/images/refactoring/diagrams/gui-window.png">


Select name of "focusLost"


#|en|V+ Recalculations of new values occur when the element loses focus. When a change occurs in `Start` or `End` text fields, `length` is calculated. When `length` changes, `End` is calculated.


Select name of "StartField_FocusLost"
+ Select name of "EndField_FocusLost"
+ Select name of "LengthField_FocusLost"


#|en|V= The specific calculations take place in utility methods for each of the fields.


Select name of "calculateLength"
+ Select name of "calculateEnd"


#|en| These methods call calculation of the new length (`calculateLength`) or new end value (`calculateEnd`) depending on what has changed in the window.


Go to the end of file


#|en| Our task is to separate all recalculations of length and end value into a separate domain class. Let's start by creating such class.


Print:
```


class Interval extends Observable {
}
```


#|en| After creation of a domain class, let's place a reference to it from the window class.


Go to:
```
  java.awt.TextField startField;
  java.awt.TextField endField;
  java.awt.TextField lengthField;
|||
```

Print:
```
  private Interval subject;

```

Set step 2

Select name of "public IntervalWindow"


#|en| Then we create the code for initializing this reference field and make the window class an observer of the domain class. We should place all this code in the `IntervalWindow` constructor.


Go to the end of "public IntervalWindow"

Print:
```


    subject = new Interval();
    subject.addObserver(this);
    update(subject, null);
```

Select "|||update|||(subject, null);"


#|en|^ Here, the call to the `update` function guarantees that the window object (GUI) will be filled with data from the domain object. But we need some other things in order for this to work.


Go to:
```
class IntervalWindow extends Frame|||
```


#|en| First, declare the `IntervalWindow` class as the one, which implements `Observer` interface.


Print:
```
 implements Observer
```


#|en| Second, create the `update()` method to provide the actual implementation.


Go to the end of "class IntervalWindow"

Print:
```


  public void update(Observable observed, Object arg) {
  }
```


#C|en| Compile and test. While we have not yet made any "real" changes, mistakes can be often made in the simplest things, and it is best to keep all code checked at all times.
#S All is well, so let's continue.


Set step 3

Select 1st "lengthField"
+ Select 1st "startField"
+ Select 1st "endField"


#|en| Now let's take care of the fields. Perform <a href="/self-encapsulate-field">Self-Encapsulate Field</a> on each of the fields of the interval window. For each field, create a getter and setter that return and accept a string value. Start with the field of the end of the interval:


Go to the end of "class IntervalWindow"

Print:
```


  String getEnd() {
    return endField.getText();
  }
  void setEnd(String arg) {
    endField.setText(arg);
  }
```


#|en| Then we can replace all references to `endField` with calls to the relevant methods.


Select "endField.getText" in "class SymFocus"

Wait 1000ms

Type "getEnd"

Select "endField.setText" in "class SymFocus"

Wait 1000ms

Type "setEnd"

Set step 4

Select name of "EndField_FocusLost"


#|en| In our case, unlike ordinary self-encapsulation, the user can independently change the value of the `End` field in the window. So we make sure that this change is saved if it is made.


Go to start of "EndField_FocusLost"

Print:
```

      setEnd(endField.getText());
```

Select "setEnd(|||endField.getText()|||);"


#|en| Note that in this call we are accessing the field directly. This is because after continuing the refactoring, `getEnd()` will be getting its value from the domain object, not the field. And in this particular case, we need the value of the field in the window (GUI).



#|en| Otherwise, when the user changes the value of the field, this code would always return the old value. That's why we need a direct access.


Set step 5

Select name of "Interval"


#|en| Excellent! Once the `End` field is fully encapsulated, we can add the relevant field to the domain class.


Go to start of "class Interval"

Print:
```

  private String end = "0";
```

Select "private String end = |||"0"|||;"


#|en| We should initialize the field with the same value as the field in the GUI.


Go to the end of "class Interval"


#|en| Then add methods for accessing the field.


Print:
```


  String getEnd() {
    return end;
  }
  void setEnd(String arg) {
    end = arg;
    setChanged();
    notifyObservers();
  }
```

Select:
```
  void setEnd(String arg) {
    end = arg;
    |||setChanged()|||;
    |||notifyObservers()|||;
  }
```


#|en| As you see, when a value gets changed, we notify the window observer that it is time to update its value from the domain object…


+ Select "void |||update|||"


#|en| …which leads us to calling the `update()` method in the window class. It does not have anything in it yet, so let's add the necessary code to make everything work.


Go to the start of "update"

Print:
```

    endField.setText(subject.getEnd());
```

Select:
```
|||endField.setText|||(subject.getEnd());
```

#|en| That is another place where direct access is needed, since calling a setter would lead to infinite recursion.



#C|en| After finishing all the field rearrangements, we'd better compile and test.
#S Everything is OK! We can keep going.


Select 1st "lengthField"
+ Select 1st "startField"


#|en| Now we do the same thing with the remaining fields, `Start` and `Length`


Go to the end of "class IntervalWindow"

Print:
```

  String getStart() {
    return startField.getText();
  }
  void setStart(String arg) {
    startField.setText(arg);
  }
```

Wait 500ms

Select "startField.getText" in "class SymFocus"

Replace "getStart"

Wait 1000ms

Select "startField.setText" in "class SymFocus"

Replace "setStart"

Wait 1000ms

Go to start of "StartField_FocusLost"

Print:
```

      setStart(startField.getText());
```

Wait 500ms

Go to:
```
  private String end = "0";|||
```


#|en| Add the field to the interval class.


Print:
```

  private String start = "0";
```

Wait 500ms

Go to the end of "class Interval"

Print:
```

  String getStart() {
    return start;
  }
  void setStart(String arg) {
    start = arg;
    setChanged();
    notifyObservers();
  }
```

Wait 500ms

Go to the end of "update"

Print:
```

    startField.setText(subject.getStart());
```


Go to the end of "class IntervalWindow"


#|en| Now we deal with the length field.


Print:
```

  String getLength() {
    return lengthField.getText();
  }
  void setLength(String arg) {
    lengthField.setText(arg);
  }
```

Wait 500ms

Select "lengthField.getText" in "class SymFocus"

Type "getLength"

Wait 500ms

Select "lengthField.setText" in "class SymFocus"

Type "setLength"

Wait 500ms

Go to start of "LengthField_FocusLost"

Print:
```

      setLength(lengthField.getText());
```

Wait 500ms

Go to:
```
  private String start = "0";|||
```


#|en| Add the field to the interval class.


Print:
```

  private String length = "0";
```

Wait 500ms

Go to the end of "class Interval"

Print:
```

  String getLength() {
    return length;
  }
  void setLength(String arg) {
    length = arg;
    setChanged();
    notifyObservers();
  }
```

Wait 500ms

Go to the end of "update"

Print:
```

    lengthField.setText(subject.getLength());
```


Select name of "calculateEnd"
+ Select name of "calculateLength"


#|en| At this point, it would be a good time to move the `calculateEnd()` and `calculateLength()` methods to the interval class.


Select body of "setEnd"
+ Select body of "setStart"
+ Select body of "setLength"


#|en| But to do this, you must first configure the setters of the fields of the `IntervalWindow` class to fill values in the `Interval` class.


Select body of "setEnd"

Replace:
```
    subject.setEnd(arg);
```

Wait 500ms

Select body of "setStart"

Replace:
```
    subject.setStart(arg);
```

Wait 500ms

Select body of "setLength"

Replace:
```
    subject.setLength(arg);
```


#|en| We removed the value assignment in the GUI interface field because the value will still be set when the setter of the interval class is called (remember about implementation of Observer in the `update` method).


Select body of "getEnd"
+ Select body of "getStart"
+ Select body of "getLength"


#|en| We should do the same with getters.


Select body of "getEnd"

Replace:
```
    return subject.getEnd(arg);
```

Wait 500ms

Select body of "getStart"

Replace:
```
    return subject.getStart(arg);
```

Wait 500ms

Select body of "getLength"

Replace:
```
    return subject.getLength(arg);
```

Select name of "calculateEnd"
+ Select name of "calculateLength"


#|en| Now we can start moving `calculateEnd()` and `calculateLength()` to the interval class.


Select:
```

    void calculateLength() {
```
+ Select whole "calculateLength"


#|en| Let's start by moving `calculateLength`.


Remove selected

Go to the end of "class Interval"

Print:
```


  void calculateLength() {
    try {
      int start = Integer.parseInt(getStart());
      int end = Integer.parseInt(getEnd());
      int length = end - start;
      setLength(String.valueOf(length));
    } catch (NumberFormatException e) {
      throw new RuntimeException ("Unexpected Number Format Error");
    }
  }
```

Select "calculateLength()" in "SymFocus"

Replace "subject.calculateLength()"


Select whole "calculateEnd"

Remove selected

Go to the end of "class Interval"

Print:
```

  void calculateEnd() {
    try {
      int start = Integer.parseInt(getStart());
      int length = Integer.parseInt(getLength());
      int end = start + length;
      setEnd(String.valueOf(end));
    } catch (NumberFormatException e) {
      throw new RuntimeException ("Unexpected Number Format Error");
    }
  }
```

Select "calculateEnd()" in "SymFocus"

Replace "subject.calculateEnd()"


Select name of "Interval"


#|en| Ultimately, we get the domain class, which contains all behaviors and calculations on the source data separate from the GUI code.



#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.

