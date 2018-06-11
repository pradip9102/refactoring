preserve-whole-object:java

###

1.en. Create a parameter in the method for the object from which you can get the necessary values.


2.en. Now start removing the old parameters from the method one by one, replacing them with calls to the relevant methods of the parameter object. Test the program after each replacement of a parameter.


3.en. Delete the getter code from the parameter object that had preceded the method call.




###

```
class Room {
  // ...
  public boolean withinPlan(HeatingPlan plan) {
    int low = getLowestTemp();
    int high = getHighestTemp();
    return plan.withinRange(low, high);
  }
}

class HeatingPlan {
  private TempRange range;
  public boolean withinRange(int low, int high) {
    return (low >= range.getLow() && high <= range.getHigh());
  }
}
```

###

```
class Room {
  // ...
  public boolean withinPlan(HeatingPlan plan) {
    return plan.withinRange(this);
  }
}

class HeatingPlan {
  private TempRange range;
  public boolean withinRange(Room room) {
    return (room.getLowestTemp() >= range.getLow() && room.getHighestTemp() <= range.getHigh());
  }
}
```

###

Set step 1


#|en| Let's look at this refactoring using the class that describes hotel rooms and logs their daily temperature.


Select "plan.withinRange"


#|en| The class should analyze room's micro climate and react with certain actions. For now, only temperature is compared with a predefined temperature plan. Then, depending on the results of the comparison, class could issue a heat or cool command, or even send an email to the house owner if the temperature is dangerously high.


Select "low, high" in "withinPlan"


#|en| Currently, we are passing only the temperature for analysis but at any time we may need to check another room parameter, such as humidity.



#|en| With current implementation, we would have to add more and more parameters to the method. To avoid this, we can pass the entire room object instead of specific values. That will allow to take any room data straight from the room object, without changing signature of the method.


Go to parameters of "withinRange"


#|en| So for the first step, we add a parameter to the <code>withinRange</code> method.


Print "Room room, "

Go to "plan.withinRange(|||"

Print "this, "

Set step 2


#|en| One by one, we should remove parameters with data, that could be retrieved from the object we pass into the method.


Select ", int high" in parameters of "withinRange"

Wait 250ms

Remove selected

Select "&& |||high|||"

Replace "room.getHighestTemp()"

Wait 500ms

Select ", high"

Remove selected


#C|en| Compile and test, and then repeat the actions for the remaining parameter.
#S Everything is good! Let's continue.


Select ", int low" in parameters of "withinRange"

Wait 500ms

Remove selected

Select "|||low||| >="

Replace "room.getLowestTemp()"

Wait 500ms

Select ", low"

Remove selected



#C|en| Compile and test one more time, to be sure that the code still works.
#S The tests are completed successfully!


Select:
```
    int low = getLowestTemp();
    int high = getHighestTemp();

```

Set step 3


#|en| And finally, let's remove the unused variables from <code>withinPlan</code>.


Remove selected


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
