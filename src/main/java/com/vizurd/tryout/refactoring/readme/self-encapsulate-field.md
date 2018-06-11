self-encapsulate-field:java

###

1. Create a getter (and optional setter) for the field.


2. Find all direct invocations of the field and replace them with getter and setter calls.




###

```
class IntRange {
  private int low, high;

  public boolean includes(int arg) {
    return arg >= low && arg <= high;
  }
  public void grow(int factor) {
    high = high * factor;
  }
  public IntRange(int low, int high) {
    this.low = low;
    this.high = high;
  }
}
```

###

```
class IntRange {
  private int low, high;

  int getLow() {
    return low;
  }
  int getHigh() {
    return high;
  }
  void setLow(int arg) {
    low = arg;
  }
  void setHigh(int arg) {
    high = arg;
  }
  public boolean includes(int arg) {
    return arg >= getLow() && arg <= getHigh();
  }
  public void grow(int factor) {
    setHigh(getHigh() * factor);
  }
  public IntRange(int low, int high) {
    this.low = low;
    this.high = high;
  }
}
```

###

Set step 1


#|en| Let's look at *Self-Encapsulation*  using the example of a range class.<br/><br/>Self-encapsulation differs from regular encapsulation by requiring even its own class to access fields through getters and setters.


Go to before "includes"


#|en| These access methods must be created if they do not yet exist. So go ahead and create getters and setters in our class.


Print:
```

  int getLow() {
    return low;
  }
  int getHigh() {
    return high;
  }
  void setLow(int arg) {
    low = arg;
  }
  void setHigh(int arg) {
    high = arg;
  }
```

Set step 2

Select "low" in "includes"
+ Select "high" in "includes"
+ Select "high" in "grow"


#|en| Our example has several methods that use direct access to fields.



#|en| To finish self-encapsulation, let's replace all references to fields in these methods with getter and setter calls.


Select "low" in "includes"

Replace "getLow()"

Wait 500ms

Select "high" in "includes"

Replace "getHigh()"

Wait 500ms

Select "high = " in "grow"

Replace "setHigh("

Wait 500ms

Go to "|||;" in "grow"

Print ")"

Wait 500ms

Select "(|||high|||" in "grow"

Replace "getHigh()"

Select "this.low"
+Select "this.high"


#|en| As you may have noticed, we did not touch the assignment in the constructor. It is often assumed that a setter is used after an object has already been created, so its behavior may be different than during initialization.



#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
