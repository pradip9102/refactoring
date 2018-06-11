split-temporary-variable:java

###

1. Find the place in your code where the variable is first filled with a value. In this place, rename the variable and give it a name that corresponds to the value assigned.


2. Use the new name instead of the old one in places where this value of the variable is used.


3. Repeat as needed for places where the variable is assigned a different value.




###

```
public double getDistanceTravelled(int time) {
  double result;
  double acc = primaryForce / mass;
  int primaryTime = Math.min(time, delay);
  result = 0.5 * acc * primaryTime * primaryTime;

  int secondaryTime = time - delay;
  if (secondaryTime > 0) {
    double primaryVel = acc * delay;
    acc = (primaryForce + secondaryForce) / mass;
    result +=  primaryVel * secondaryTime + 0.5 * acc * secondaryTime * secondaryTime;
  }
  return result;
}
```

###

```
public double getDistanceTravelled(int time) {
  double result;
  final double primaryAcceleration = primaryForce / mass;
  int primaryTime = Math.min(time, delay);
  result = 0.5 * primaryAcceleration * primaryTime * primaryTime;

  int secondaryTime = time - delay;
  if (secondaryTime > 0) {
    double primaryVel = primaryAcceleration * delay;
    final double secondaryAcceleration = (primaryForce + secondaryForce) / mass;
    result +=  primaryVel * secondaryTime + 0.5 * secondaryAcceleration * secondaryTime * secondaryTime;
  }
  return result;
}
```

###

Set step 1


#|en| Let's look at *Split Temporary Variable*  using a small method, which calculates the movement of a ball in space as a function of time and the forces acting on it.


Select "|||acc||| ="


#|en|^ Notably for our example, the `acc` variable is set in it twice.



#|en|+ It performs two tasks: it contains the initial acceleration caused by the first force…



#|en|^= …and later acceleration caused by both forces.



#|en|^ So it is better to split up this variable, with each part responsible for only one task.


Select "double |||acc|||"


#|en| Start by changing the name of the variable. For this purpose, it is convenient to select a name that reflects the first use of the variable.


Print "primaryAcceleration"

Go to "|||double primaryAcceleration"


#|en| In addition, we declare it as `final` in order to guarantee that a value is assigned to it only once.


Print "final "

Set step 2

Select "result = 0.5 * |||acc|||"
+ Select "|||acc||| * delay"


#|en| Then we should rename the variable in all places where it is used, including the place where the new value is assigned.


Print "primaryAcceleration"

Go to "|||acc ="


#|en| After all replacements, you can declare the initial variable in the place of the second assignment of a value to it.


Print "double "


#C|en| After getting to the second case of variable use, compile and test.
#S Everything is OK! We can keep going.


Set step 3

Select 1st "|||acc||| "


#|en| Now we can repeat all these actions with the second assignment of a temporary variable. We remove the initial name of the variable once and for all, and then replace it with a new name that fits the second task.


Print "secondaryAcceleration"

Wait 500ms

Go to "|||double secondaryAcceleration"

Print "final "

Wait 500ms

Select " |||acc||| "

Replace "secondaryAcceleration"


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
