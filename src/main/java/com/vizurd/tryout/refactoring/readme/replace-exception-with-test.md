replace-exception-with-test:java

###

1. Create a conditional for an edge case and move it before the try/catch block.



2. Move code from the `catch` section inside this conditional.



3. In the `catch` section, place the code for throwing a usual unnamed exception and run all the tests.



4. If no exceptions were thrown during the tests, get rid of the `try`/`catch` operator.





###

```
class ResourcePool {
  // ...
  private Stack available;
  private Stack allocated;

  public Resource getResource() {
    Resource result;
    try {
      result = (Resource) available.pop();
      allocated.push(result);
      return result;
    } catch (EmptyStackException e) {
      result = new Resource();
      allocated.push(result);
      return result;
    }
  }
}
```

###

```
class ResourcePool {
  // ...
  private Stack available;
  private Stack allocated;

  public Resource getResource() {
    Resource result;
    if (available.empty()) {
      result = new Resource();
    }
    else {
      result = (Resource) available.pop();
    }
    allocated.push(result);
    return result;
  }
}
```

###

Set step 1


#|en| For this example, we take an object that controls resources that are expensive to create but reusable. A good example of this situation is database connections.


Select "Stack |||available|||"

#|en|+ The administrator has two pools. One of them contains resources available for use…


Select "Stack |||allocated|||"


#|en|<= …and the other pool contains already allocated resources.


Select "(Resource) available.pop()"


#|en|< When a client needs a resource, the administrator provides it from the pool of available resources and moves it to the allocated pool. When the client frees up the resource, the administrator returns it back.


Select "result = new Resource();"


#|en|< If a client requests a resource and no free resources remain, the administrator creates a new resource.



#|en|< "Insufficient resources" is not an unexpected event, so using an exception is not truly justified.


Go to "Resource result;|||"


#|en| So let's try to get rid of the exception. First, at the beginning of the method, create a conditional whose condition coincides with the condition for throwing an exception. Place all the remaining code in `else`.


Print:
```

    if (available.empty()) {
    }
    else {
```

Go to:
```
    }|||
  }
```

Print:
```

    }
```

Select:
```
    try {
      result = (Resource) available.pop();
      allocated.push(result);
      return result;
    } catch (EmptyStackException e) {
      result = new Resource();
      allocated.push(result);
      return result;
    }

```

Indent

Set step 2

Select:
```
        result = new Resource();
        allocated.push(result);
        return result;

```


#|en| Then copy the code from the `catch` section to inside the guard clause.


Go to "empty()) {|||"

Print:
```

      result = new Resource();
      allocated.push(result);
      return result;
```

Set step 3

Go to "catch (EmptyStackException e) {|||"


#|en| This code should never reach the `catch` section. But to be 100% sure, insert a check inside the section and run all the tests.


Print:
```

        throw new RuntimeException("Should not reach here.");
```


#C|en| Let's compile and test.
#S Everything is OK! We can keep going.


Set step 4


#|en| Now we can remove the `try` / `catch` section without worrying about possible errors.


Select:
```
      try {

```

Remove selected

Select:
```
      } catch (EmptyStackException e) {
        throw new RuntimeException("Should not reach here.");
        result = new Resource();
        allocated.push(result);
        return result;
      }

```

Remove selected

Select:
```
        result = (Resource) available.pop();
        allocated.push(result);
        return result;
```

Deindent

Select:
```
      allocated.push(result);
      return result;

```


#|en| After this, it is usually possible to tidy up the conditional code. In this case, we can apply <a href="/consolidate-duplicate-conditional-fragments">Consolidate Duplicate Conditional Fragments</a>.


Go to:
```
    }|||
  }
```

Print:
```

    allocated.push(result);
    return result;
```

Wait 500ms

Select:
```
      allocated.push(result);
      return result;

```

Remove selected


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
