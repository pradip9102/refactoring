replace-array-with-object:java

---

1. Create the new class that will contain the data from the array. Place the array itself in the class as a public field.

2. Create a field for storing the object of this class in the original class.

3. In the new class, create access methods one by one for all elements of the array.

4. When access methods have been created for all elements, make the array private.

5. For each element of the array, create a private field in the class and then change the access methods so that they use this field instead of the array.

6. When this is done, delete the array.



---

```
class Tournament {
  String[] row = new String[2];

  public Tournament() {
    row[0] = "Liverpool";
    row[1] = "15";
  }
  public void displayScore() {
    String name = row[0];
    int score = Integer.parseInt(row[1]);
    // ...
  }
}
```

---

```
class Tournament {
  Performance row = new Performance();

  public Tournament() {
    row.setName("Liverpool");
    row.setScore("15");
  }
  public void displayScore() {
    String name = row.getName();
    int score = row.getScore();
    // ...
  }
}

class Performance {
  private String name;
  private int score;

  public String getName() {
    return name;
  }
  public void setName(String arg) {
    name = arg;
  }
  public int getScore() {
    return score;
  }
  public void setScore(String arg) {
    score = Integer.parseInt(arg);
  }
}
```

---

###### Set step 1


#|en| Let's look at *Replace Array with Object* , using a class that stores the name of an athletic team, number of wins and losses as our example.

Go to the end of file


#|en| Converting an array to an object starts with creating a class.

Print:
```


class Performance {
}
```

Go to the start of "Performance"


#|en| Then, we add the same array field to the new class as in the original. Don't worry, this is a temporary measure that we will take care of later.

Print:
```

  public String[] data = new String[2];
```

###### Set step 2

Select name of "Tournament"


#|en| Now we should find all code, which works with the array and replace it with calls to your new class.

Select "String[] row = new String[2]"


#|en| Create the instance of our data class in the place where the array had been initialized.

Print:
```
Performance row = new Performance()
```

Select "row" in "public Tournament"
+Select "row" in "displayScore"


#|en| Now replace the code that uses the array.

Replace "row.data"

###### Set step 3

Go to the end of "Performance"


#|en| Add getters and setters with more self-explanatory names to the data class. Start with the field containing the team name.

Print:
```


  public String getName() {
    return data[0];
  }
  public void setName(String arg) {
    data[0] = arg;
  }
```

Select "row.data[0] = "Liverpool""
+ Select "String name = row.data[0]"


#|en| Now we need to replace the code of assignment values to array elements with appropriate setters of the `Performance` class.

Select "row.data[0] = "Liverpool""

Replace "row.setName("Liverpool")"

Wait 500ms

Select "row.data[0]"

Replace "row.getName()"

Go to the end of "Performance"


#|en| Do the same with the second element.

Print:
```

  public int getScore() {
    return Integer.parseInt(data[1]);
  }
  public void setScore(String arg) {
    data[1] = arg;
  }
```


Select "row.data[1] = "15""

Replace "row.setScore("15")"

Wait 500ms

Select "Integer.parseInt(row.data[1])"

Replace "row.getScore()"

###### Set step 4

Select "|||public||| String[] data"


#|en| Having done so for all elements, we can declare the array private.

Replace "private"

###### Set step 5


#|en| The main part of this refactoring – replacing the interface – is now complete. But it will also be useful to replace the array inside the data class.

Select name of "getName"
+ Select name of "setName"


#|en| To do this, we add fields for all array elements and reorient the access methods to use them. First convert the team name field.

Go to "new String[2];|||"

Print:
```

  private String name;
```

Select "data[0]"

Replace "name"

Select name of "getScore"
+ Select name of "setScore"


#|en| Then convert the field that stores the team win/loss history.

Go to "String name;|||"

Print:
```

  private int score;
```

Select "Integer.parseInt(data[1])"

Replace "score"

Select "data[1] = arg"

Replace "score = Integer.parseInt(arg)"

###### Set step 6

Select:
```
  private String[] data = new String[2];

```

#|en| After finishing replacements for all the elements of the array, we can remove the array declaration from the class.

Remove selected


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
