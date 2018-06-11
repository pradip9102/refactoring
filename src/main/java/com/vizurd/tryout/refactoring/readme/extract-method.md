extract-method:java

###

1. Create a new method and name it in a way that makes its purpose self-evident.

2. Copy the relevant code fragment to your new method. Delete the fragment from its old location and put a call for the new method there instead.

3. In the new method, create parameters for passing values from the original method.

4. Pass the results and other changed data back to the original method.



###

```
void printOwing() {
  Enumeration elements = orders.elements();
  double outstanding = 0.0;

  // print banner
  System.out.println ("*****************************");
  System.out.println ("****** Customer totals ******");
  System.out.println ("*****************************");

  // print owings
  while (elements.hasMoreElements()) {
    Order each = (Order) elements.nextElement();
    outstanding += each.getAmount();
  }

  // print details
  System.out.println("name: " + name);
  System.out.println("amount: " + outstanding);
}
```

###

```
void printOwing() {
  printBanner();
  double outstanding = getOutstanding();
  printDetails(outstanding);
}

void printBanner() {
  System.out.println("*****************************");
  System.out.println("****** Customer totals ******");
  System.out.println("*****************************");
}

void printDetails(double outstanding) {
  System.out.println("name: " + name);
  System.out.println("amount: " + outstanding);
}

double getOutstanding() {
  Enumeration elements = orders.elements();
  double outstanding = 0.0;
  while (elements.hasMoreElements()) {
    Order each = (Order) elements.nextElement();
    outstanding += each.getAmount();
  }
  return outstanding;
}
```

###

###### Set step 1


#|en| Let's take a look at *Extract Method*  using this function as an example.

Select in "printOwing":
```
  // print banner
  System.out.println ("*****************************");
  System.out.println ("****** Customer totals ******");
  System.out.println ("*****************************");
```


#|en| We begin with a very, very simple case. The code for displaying a banner can be easily extracted via copy and paste.

Wait 500ms

Go to the end of file

Print:
```


void printBanner() {
  System.out.println("*****************************");
  System.out.println("****** Customer totals ******");
  System.out.println("*****************************");
}
```

###### Set step 2

Select in "printOwing":
```
  // print banner
  System.out.println ("*****************************");
  System.out.println ("****** Customer totals ******");
  System.out.println ("*****************************");
```


#|en| After that, we replace the code in the original method with a call to the new method.

Remove selected

Print:
```
  printBanner();
```


#C|en| At last, we should compile the code to check for possible errors.
#S Cause for celebration – we have successfully extracted the first method!


###### Set step 3

Select:
```
  // print details
  System.out.println("name: " + name);
  System.out.println("amount: " + |||outstanding|||);
```


#|en| Now things get trickier. The problem with extracting complex methods is buried in local variables.

Select in "printOwing":
```
  // print details
  System.out.println("name: " + name);
  System.out.println("amount: " + outstanding);
```


#|en| Let's try to extract the method for displaying the details.

Wait 500ms

Go to the end of file

Print:
```


void printDetails() {
  System.out.println("name: " + name);
  System.out.println("amount: " + outstanding);
}
```

Select in "printOwing":
```
  // print details
  System.out.println("name: " + name);
  System.out.println("amount: " + outstanding);
```

Remove selected

Print "  printDetails();"


#F Ошибка! Переменная `outstanding` в методе `printDetails()` не определена.

#C|en| Let's launch the compiler.
#F Error! The variable `outstanding` in method `printDetails()` is not defined.

#F Помилка! Змінна `outstanding` в методі `printDetails()` не визначена.

Select in "printDetails" text "outstanding"


#|en| Ah… Yes, we really did move the `outstanding` variable out of the original method but no value is assigned to it in the new method.


#|en| The better solution is to convert that variable to a method parameter and pass its value from the original method.

Go to text "void printDetails(|||)"

Print "double outstanding"

Go to text "printDetails(|||)" in "printOwing"

Print "outstanding"


#C|en| Let's launch the compiler.
#S A-OK! Let's keep moving.

###### Set step 4

Select in "printOwing":
```
  Enumeration elements = orders.elements();
  double outstanding = 0.0;
```
+ Select in "printOwing":
```
  // print owings
  while (elements.hasMoreElements()) {
    Order each = (Order) elements.nextElement();
    outstanding += each.getAmount();
  }
```


#|en| Now on to the extraction of the code for calculating amounts outstanding.


Wait 500ms

Go to the end of file

Print:
```


void getOutstanding() {
  Enumeration elements = orders.elements();
  double outstanding = 0.0;
  while (elements.hasMoreElements()) {
    Order each = (Order) elements.nextElement();
    outstanding += each.getAmount();
  }
}
```

Select in "printOwing":
```
  Enumeration elements = orders.elements();
  double outstanding = 0.0;


```

Remove selected

Select in "printOwing":
```

  // print owings
  while (elements.hasMoreElements()) {
    Order each = (Order) elements.nextElement();
    outstanding += each.getAmount();
  }

```

Remove selected

Print "  getOutstanding();"

Select in "getOutstanding":
```
  Enumeration |||elements||| = orders.elements();
  double |||outstanding||| = 0.0;
```


#|en| In this case, additional difficulties are caused by local variables to which new values are assigned. It is quite possible that these values are used in the remaining code of the main method.


#|en| If a value is assigned to the parameter, you can get rid of this by using *Remove Assignments to Parameters*  refactoring.

Select in "getOutstanding":
```
  double |||outstanding||| = 0.0;
```


#|en|<+ Let's check each variable.

+ Select in "printOwing":
```
  printDetails(|||outstanding|||);
```


#|en|<= Here, the problem is caused by the `outstanding` variable, which is then used in the `printDetails()` call.


#|en|< Pass it back to the original method.

Select type of "GetOutstanding"

Replace:
```
double
```

Go to the end of "getOutstanding"

Print:
```

  return outstanding;
```

Go to text "|||getOutstanding()" in "printOwing"

Print "double outstanding = "


#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
