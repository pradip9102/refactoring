encapsulate-collection:java

###

1. Create methods for adding and deleting collection elements.

2. Assign an empty collection to the field as the initial value.

3. Find the setter calls for the collection field. Change the setter so that it uses element add/remove operations.

4. Find all calls of the collection getter after which the collection is changed. Change the code so that it uses your new methods for adding and deleting elements from the collection.

5. Change the getter so that it returns a read-only representation of the collection.

6. Inspect the client code that uses the collection for code that would look better inside of the collection class itself.



###

```
class Course {
  public Course(String name, boolean isAdvanced) {
    // ...
  }
  public boolean isAdvanced() {
    // ...
  }
}

class Person {
  private Set courses;

  public Set getCourses() {
    return courses;
  }
  public void setCourses(Set arg) {
    courses = arg;
  }
}

// Client code
Person kent = new Person();
Set s = new HashSet();
s.add(new Course("Smalltalk Programming", false));
s.add(new Course("Appreciating Single Malts", true));
kent.setCourses(s);
Assert.equals(2, kent.getCourses().size());
Course refact = new Course("Refactoring", true);
kent.getCourses().add(refact);
kent.getCourses().add(new Course("Brutal Sarcasm", false));
Assert.equals(4, kent.getCourses().size());
kent.getCourses().remove(refact);
Assert.equals(3, kent.getCourses().size());

Iterator iter = kent.getCourses().iterator();
int count = 0;
while (iter.hasNext()) {
  Course each = (Course) iter.next();
  if (each.isAdvanced()) {
    count++;
  }
}
System.out.print("Advanced courses: " + count);
```

###

```
class Course {
  public Course(String name, boolean isAdvanced) {
    // ...
  }
  public boolean isAdvanced() {
    // ...
  }
}

class Person {
  private Set courses = new HashSet();

  public Set getCourses() {
    return Collections.unmodifiableSet(courses);
  }
  public void initializeCourses(Set arg) {
    Assert.isTrue(courses.isEmpty());
    courses.addAll(arg);
  }
  public void addCourse(Course arg) {
    courses.add(arg);
  }
  public void removeCourse(Course arg) {
    courses.remove(arg);
  }
  public int numberOfAdvancedCourses() {
    Iterator iter = getCourses().iterator();
    int count = 0;
    while (iter.hasNext()) {
      Course each = (Course) iter.next();
      if (each.isAdvanced()) {
        count++;
      }
    }
    return count;
  }
  public int numberOfCourses() {
    return courses.size();
  }
}

// Client code
Person kent = new Person();
kent.addCourse(new Course("Smalltalk Programming", false));
kent.addCourse(new Course("Appreciating Single Malts", true));
Assert.equals(2, kent.numberOfCourses());
Course refact = new Course("Refactoring", true);
kent.addCourse(refact);
kent.addCourse(new Course("Brutal Sarcasm", false));
Assert.equals(4, kent.numberOfCourses());
kent.removeCourse(refact);
Assert.equals(3, kent.numberOfCourses());

System.out.print("Advanced courses: " + kent.numberOfAdvancedCourses());
```

###

###### Set step 1


#|en| Let's look at *Encapsulate Collection* , using a catalog of training courses as our example.

Select name of "Course"


#|en| The class of courses is rather simple.

Select name of "Person"


#|en| Plus there is also a class of course students.

Go to "Person kent = |||new Person();"


#|en| With this interface, clients add courses via the following code.

Go to the end of "class Person"


#|en| Thus, first we need proper modification methods for this collection.

Print:
```

  public void addCourse(Course arg) {
    courses.add(arg);
  }
  public void removeCourse(Course arg) {
    courses.remove(arg);
  }
```

###### Set step 2

Go to "private Set courses|||"


#|en| We can also make life easier for ourselves by initializing the field:


Print " = new HashSet()"

###### Set step 3

Select name of "setCourses"


#|en| Now look at the uses of the `setCourses` setter. If there are many clients and the setter is used intensively, replace the method body so that it uses add/remove operations.

Select "kent.setCourses(s)"


#|en| The complexity of this procedure depends on the way in which the collection setter is used. In the most simple case, the client initializes values by using the setter, meaning that courses do not exist prior to use of the method.

Select body of "setCourses"


#|en| If this is the case, change the setter body so that it uses collection's `add` method.

Print:
```
    Assert.isTrue(courses.isEmpty());
    Iterator iter = arg.iterator();
    while (iter.hasNext()) {
      addCourse((Course) iter.next());
    }
```

Select name of "setCourses"


#|en| After this modification, use <a href="/rename-method">Rename Method</a> to make your intentions obvious.

Select "setCourses"

Replace "initializeCourses"


#|en| But in a general case, this method should first remove all existing collection items, and then add new ones.


#|en| If there are no additional behaviors or checks during initialization, we can make the code even simpler by getting rid of the loop and using collection's `addAll` method.


Select:
```
    Iterator iter = arg.iterator();
    while (iter.hasNext()) {
      addCourse((Course) iter.next());
    }
```

Replace:
```
    courses.addAll(arg);
```


#|en| Remember that we cannot simply assign a value to a set even if the previous set was empty. If the client plans to modify the set after passing it, this will violate encapsulation. So we always have to create copies.

Select:
```
Set s = new HashSet();

```


#|en| If client code simply creates a set and uses its setter, we should force it to use add/remove methods instead…


Remove selected

Select:
```
|||s.add|||(new Course("Smalltalk Programming", false));
|||s.add|||(new Course("Appreciating Single Malts", true));
```

Replace "kent.addCourse"

Wait 500ms

Select:
```
kent.initializeCourses(s);

```


#|en| …and get rid of the call to the initialization method completely.

Remove selected

###### Set step 4


Select "getCourses().add"
+ Select "getCourses().remove"


#|en| Now let's see who is using the collection's getter. Our foremost interest should go to cases when it is used to modify the collection.


#|en| We need to replace such calls with add/remove calls.

Select "getCourses().add"

Replace "addCourse"

Wait 500ms

Select "getCourses().remove"

Replace "removeCourse"

###### Set step 5

Select:
```
return |||courses|||;
```


#|en| As the finishing touch, let's change the getter's body so that it returns a read-only value (an immutable representation of the collection).


Print:
```
Collections.unmodifiableSet(courses)
```


#C|en| Let's compile to make sure there are no errors.
#S Wonderful, it's all working!


Select:
```
private Set |||courses|||
```


#|en| At this point, we can consider the collection fully encapsulated. Nobody can change its elements other than by using the `Person` class' method.

###### Set step 6

Select:
```
Iterator iter = kent.getCourses().iterator();
int count = 0;
while (iter.hasNext()) {
  Course each = (Course) iter.next();
  if (each.isAdvanced()) {
    count++;
  }
}

```


#|en| Now that we have a proper interface for the `Person` class, we can start moving the relevant code to this class. Here is an example of the code.


#|en| Apply <a href="/extract-method">Extract Method</a> to the code to move it to `Person`.

Go to the end of "class Person"

Print:
```

  public int numberOfAdvancedCourses() {
    Iterator iter = getCourses().iterator();
    int count = 0;
    while (iter.hasNext()) {
      Course each = (Course) iter.next();
      if (each.isAdvanced()) {
        count++;
      }
    }
    return count;
  }
```

Select:
```
Iterator iter = kent.getCourses().iterator();
int count = 0;
while (iter.hasNext()) {
  Course each = (Course) iter.next();
  if (each.isAdvanced()) {
    count++;
  }
}

```

Remove selected

Select:
```
System.out.print("Advanced courses: " + |||count|||);
```

Replace "kent.numberOfAdvancedCourses()"

Select "kent.getCourses().size()"


#|en| Take a loot at this code. It's quite frequently encountered.

Go to the end of "Person"


#|en| It can be replaced with a more readable version.

Print:
```

  public int numberOfCourses() {
    return courses.size();
  }
```

Select "kent.getCourses().size()"

Replace "kent.numberOfCourses()"


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
