change-reference-to-value:java

---

1. Make the object unchangeable. The object should not have any setters or other methods that change its state and data (<a href="/remove-setting-method">Remove Setting Method</a> may help here). The only place where data should be assigned to the fields of a value object is a constructor.

2. Create a comparison method for comparing two value objects.

3. Check whether you can delete the factory method and make the object constructor public.


---

```
class Customer {
  private final String name;
  private Date birthDate;

  public String getName() {
    return name;
  }
  public Date getBirthDate() {
    return birthDate;
  }
  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }
  private Customer(String name) {
    this.name = name;
  }

  private static Dictionary instances = new Hashtable();

  public static Customer get(String name) {
    Customer value = (Customer)instances.get(name);
    if (value == null) {
      value = new Customer(name);
      instances.put(name, value);
    }
    return value;
  }
}

// Somewhere in client code
Customer john = Customer.get("John Smith");
john.setBirthDate(new Date(1985, 1, 1));
```

---

```
class Customer {
  private final String name;
  private Date birthDate;

  @Override public boolean equals(Object arg) {
    if (!(arg instanceof Customer)) {
      return false;
    }
    Customer other = (Customer) arg;
    return (name.equals(other.name));
  }
  @Override public int hashCode() {
    return name.hashCode();
  }
  public String getName() {
    return name;
  }
  public Date getBirthDate() {
    return birthDate;
  }
  public Customer(String name, Date birthDate) {
    this.name = name;
    this.birthDate = birthDate;
  }
}

// Somewhere in client code
Customer john = new Customer("John Smith", new Date(1985, 1, 1));
```

---

###### Set step 1

#|en| Let's look at *Change Reference to Value*  using a customer class as an example.

Select "private final String |||name|||"
+ Select "private Date |||birthDate|||"

#|en| This class contains a customer's name and date of birth. This class gives rise to reference objects, meaning that only one instance of the `Customer` class is created for one real customer.

Select "Customer.get("John Smith")"

#|en| The code for getting the instance may look as follows:

Select visibility of "private Customer"

#|en| The `Customer` class keeps a registry of its instances. We cannot simply access the constructor (because it is private).

Select name of "get"

#|en| Instead, we call a static factory method that looks for the customer among the objects already created. And only if such an object has not been created does the factory method run the real constructor and then add the newly created object to the registry.

#|en| Now let's say that we have multiple orders referring to the same client. Suddenly, the code of one of the orders changes the value of the client's date of birth. Since both orders refer to the same client object, the new date of birth will be available from the other order as well.

#|en| Would this be made impossible if each order had own instance of the `Customer` class? Probably not. That is why the main requirement of this refactoring is making the class immutable. In some cases, this is simply not possible, and the refactoring should not be executed.

Select whole "setBirthDate"

#|en| Following this logic, we should remove the setter of the date of birth field. Then, initialize the value of the field in the constructor. This could be done with <a href="/remove-setting-method">Remove Setting Method</a>.

Remove selected

Go to the end of parameters of "private Customer"

Print ", Date birthDate"

Go to the end of "private Customer"

Print:
```

    this.birthDate = birthDate;
```

Select:
```

john.setBirthDate(new Date(1985, 1, 1));
```

#|en| Since the class no longer contains a setter, we need to remove its use in the client code. Note that we don't have anything to compensate that setter yet. But don't worry, we will get to this a bit later.

Remove selected


###### Set step 2

Go to before "getname"

#|en| There's one more problem. Values with identical data should be equal when compared. To do this in Java, define special `equals` and `hash` methods in the classes being compared.

#|en| This is how it will look in our case.

Print:
```

  @Override public boolean equals(Object arg) {
    if (!(arg instanceof Customer)) {
      return false;
    }
    Customer other = (Customer) arg;
    return (name.equals(other.name));
  }
  @Override public int hashCode() {
    return name.hashCode();
  }
```

#|en| Now the comparison `new Customer("John").equals(new Customer("John"))` will return `TRUE`.

###### Set step 3

Select:
```

  private static Dictionary instances = new Hashtable();


```
+ Select whole "get"

#|en| And one last thing. Since we no longer need to keep a registry of created objects, we can remove the factory method and make the constructor public.

Remove selected

Select "|||private||| Customer"

Replace "public"

Select "Customer.get("John Smith")"

#|en| The client code will also change as the result of these changes.

Print "new Customer("John Smith", new Date(1985, 1, 1))"

#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!

###### Set final step

#|en|Q The refactoring is complete! You can compare the old and new code if you like.