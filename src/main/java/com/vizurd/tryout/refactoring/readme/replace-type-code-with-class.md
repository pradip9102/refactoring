replace-type-code-with-class:java

###

1. Create a new class and give it a new name that corresponds to the purpose of the coded type. Here we will call it *type class* .


2. Copy the field containing type code to the *type class*  and make it private. Then create a getter for the field. A value will be set for this field only from the constructor.


3. For each value of the coded type, create a static method in the *type class* .


4. In the original class, replace the type of the coded field with *type class* . Create a new object of this type in the constructor as well as in the field setter. Change the field getter so that it calls the *type class*  getter.


5. Replace any mentions of values of the coded type with calls of the relevant *type class*  static methods.


6. Remove the coded type constants from the original class and make the *type class*  constructor private.




###

```
class Person {
  public static final int O = 0;
  public static final int A = 1;
  public static final int B = 2;
  public static final int AB = 3;

  private int bloodGroup;

  public Person(int code) {
    bloodGroup = code;
  }
  public void setBloodGroup(int code) {
    bloodGroup = code;
  }
  public int getBloodGroup() {
    return bloodGroup;
  }
}

// Somewhere in client code.
Person parent = new Person(Person.O);
if (parent.getBloodGroup() == Person.AB) {
  // ...
}
child.setBloodGroup(parent.getBloodGroup());
```

###

```
class Person {
  private BloodGroup bloodGroup;

  public Person(BloodGroup bloodGroup) {
    bloodGroup = bloodGroup;
  }
  public void setBloodGroup(BloodGroup bloodGroup) {
    bloodGroup = bloodGroup;
  }
  public BloodGroup getBloodGroup() {
    return bloodGroup;
  }
}

class BloodGroup {
  private final int code;

  private BloodGroup(int code) {
    this.code = code;
  }
  public int getCode() {
    return this.code;
  }

  public static BloodGroup O() {
    return new BloodGroup(0);
  }
  public static BloodGroup A() {
    return new BloodGroup(1);
  }
  public static BloodGroup B() {
    return new BloodGroup(2);
  }
  public static BloodGroup AB() {
    return new BloodGroup(3);
  }
}

// Somewhere in client code.
Person parent = new Person(BloodGroup.O());
if (parent.getBloodGroup() == BloodGroup.AB()) {
  // ...
}
child.setBloodGroup(parent.getBloodGroup());
```

###

Set step 1


#|en| Let's look at *Replace Type Code With Class* , using the example of a person class that contains blood type fields.


Select:
```
  public static final int |||O = 0|||;
  public static final int |||A = 1|||;
  public static final int |||B = 2|||;
  public static final int |||AB = 3|||;
```


#|en| Blood types are coded as four constants of this class.


Go to after "Person"


#|en| We start refactoring by creating a new `BloodGroup` class, which will be responsible for blood types.


Type:
```


class BloodGroup {
}
```

Set step 2


#|en| We place the blood type field from the `Person` class, its getter and the constructor, which initialize the field value.


Go to the end of "BloodGroup"

Type:
```

  private final int code;

  public BloodGroup(int code) {
    this.code = code;
  }
  public int getCode() {
    return this.code;
  }
```

Set step 3


#|en| Now let's create static methods for each of the type code values from the original class. These methods should return instances of the `BloodGroup` class.


Go to the end of "class BloodGroup"

Print:
```


  public static BloodGroup O() {
    return new BloodGroup(0);
  }
  public static BloodGroup A() {
    return new BloodGroup(1);
  }
  public static BloodGroup B() {
    return new BloodGroup(2);
  }
  public static BloodGroup AB() {
    return new BloodGroup(3);
  }
```



#C|en| Let's compile and test our code.
#S All is well, so let's continue.


Set step 4

Select:
```
  private |||int||| bloodGroup;
```


#|en| In the original class, change the type of the coded field to `BloodGroup`.



Type:
```
BloodGroup
```


Select:
```
  public Person(int code) {
    bloodGroup = |||code|||;
  }
  public void setBloodGroup(int code) {
    bloodGroup = |||code|||;
  }
```


#|en| Change the code of the setter and constructor accordingly.


Type:
```
new BloodGroup(code)
```

Go to:
```
return bloodGroup|||;
```


#|en| Then change the field getter so that it calls the getter of the `BloodGroup` class.


Print ".getCode()"

Set step 5

Select:
```
  public static final int |||O = 0|||;
  public static final int |||A = 1|||;
  public static final int |||B = 2|||;
  public static final int |||AB = 3|||;
```


#|en| It is now time to replace all type code values with calls to the corresponding static methods of the *type class* .



#|en| First replace the values of all constants with calls to the corresponding methods of the `BloodGroup` class.


Select "public static final int O = |||0|||;"

Replace "BloodGroup.O().getCode()"

Select "public static final int A = |||1|||;"

Replace "BloodGroup.A().getCode()"

Select "public static final int B = |||2|||;"

Replace "BloodGroup.B().getCode()"

Select "public static final int AB = |||3|||;"

Replace "BloodGroup.AB().getCode()"


#|en|^ In effect, all uses of constants are now delegated to the methods of `BloodGroup`.


Select "new Person(|||Person.O|||);"


#|en| We will go one step further and get rid of direct references to constants of the `Person` class in the remaining code. We can use calls to the methods of the `BloodGroup` class instead.


Type "BloodGroup.O().getCode()"

Wait 500ms

Select "parent.getBloodGroup() == |||Person.AB|||"

Wait 500ms

Type "BloodGroup.AB().getCode()"


#C|en| After all the changes, test everything carefully.
#S All working. Excellent!


Select:
```
  public Person(|||int code|||) {
    bloodGroup = new BloodGroup(code);
  }
  public void setBloodGroup(|||int code|||) {
    bloodGroup = new BloodGroup(code);
  }
  public |||int||| getBloodGroup() {
    return bloodGroup|||.getCode()|||;
  }
```


#|en| In the end, it is better to avoid using any numeric codes for `BloodGroup` and use objects instead. Let's try to do so in the `Person` class.


Select:
```
  public Person(|||int code|||) {
    bloodGroup = new BloodGroup(code);
  }
  public void setBloodGroup(|||int code|||) {
    bloodGroup = new BloodGroup(code);
  }
```

Replace "BloodGroup bloodGroup"

Wait 500ms

Select:
```
  public Person(BloodGroup bloodGroup) {
    bloodGroup = |||new BloodGroup(code)|||;
  }
  public void setBloodGroup(BloodGroup bloodGroup) {
    bloodGroup = |||new BloodGroup(code)|||;
  }
```

Replace "bloodGroup"

Wait 500ms

Select:
```
  public |||int||| getBloodGroup() {
    return bloodGroup.getCode();
  }
```
Replace "BloodGroup"


Wait 500ms

Select:
```
  public BloodGroup getBloodGroup() {
    return bloodGroup|||.getCode()|||;
  }
```

Remove selected

Select:
```
Person parent = new Person(BloodGroup.O()|||.getCode()|||);
if (parent.getBloodGroup() == BloodGroup.AB()|||.getCode()|||) {
  // ...
}
```


#|en| These changes will probably cause the client code to break, but this can be fixed by simply getting rid of the codes there as well.


Remove selected


Set step 6
Select:
```
  public static final int O = BloodGroup.O().getCode();
  public static final int A = BloodGroup.A().getCode();
  public static final int B = BloodGroup.B().getCode();
  public static final int AB = BloodGroup.AB().getCode();


```


#|en| You can remove unused constants from the `Person` class.


Remove selected

Select "|||public||| BloodGroup" in "BloodGroup"


#|en| And finally, you should make the `BloodGroup` constructor private.


Replace "private"


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
