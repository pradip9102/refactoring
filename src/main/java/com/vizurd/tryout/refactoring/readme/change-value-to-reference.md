change-value-to-reference:java

###

1. Use <a href="/replace-constructor-with-factory-method">Replace Constructor with Factory Method</a> on the class from which the references are to be generated.

2. Determine which object will be responsible for providing access to references. Instead of creating a new object, when you need one you now need to get it from a storage object or static dictionary field.

3. Determine whether references will be created in advance or dynamically as necessary. If objects are created in advance, make sure to load them before use.

4. Change the factory method so that it returns a reference. If objects are created in advance, decide how to handle errors when a non-existent object is requested.



###

```
class Customer {
  private final String name;
  public Customer(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }
}

class Order {
  //…
  private Customer customer;
  public String getCustomerName() {
      return customer.getName();
  }
  public void setCustomer(String customerName) {
    customer = new Customer(customerName);
  }
  public Order(String customerName) {
    customer = new Customer(customerName);
  }
}

// Some client code, which uses Order class.
private static int numberOfOrdersFor(Collection orders, String customer) {
  int result = 0;
  Iterator iter = orders.iterator();
  while (iter.hasNext()) {
    Order each = (Order) iter.next();
    if (each.getCustomerName().equals(customer)) {
      result++;
    }
  }
  return result;
}
```

###

```
class Customer {
  private static Dictionary instances = new Hashtable();

  // This code should be executed at the program launch.
  static void loadCustomers() {
    new Customer("Lemon Car Hire").store();
    new Customer("Associated Coffee Machines").store();
    new Customer("Bilston Gasworks").store();
  }
  private void store() {
    instances.put(this.getName(), this);
  }

  private final String name;
  public static Customer getNamed(String name) {
    return (Customer) instances.get(name);
  }
  private Customer(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }
}

class Order {
  //…
  private Customer customer;
  public String getCustomerName() {
      return customer.getName();
  }
  public void setCustomer(String customerName) {
    customer = Customer.getNamed(customerName);
  }
  public Order(String customerName) {
    customer = Customer.getNamed(customerName);
  }
}

// Some client code, which uses Order class.
private static int numberOfOrdersFor(Collection orders, String customer) {
  int result = 0;
  Iterator iter = orders.iterator();
  while (iter.hasNext()) {
    Order each = (Order) iter.next();
    if (each.getCustomerName().equals(customer)) {
      result++;
    }
  }
  return result;
}
```

###

Set step 1

#|en| Let's look at *Replace Data Value with Object*  using the customer/order class example. We will pick up where we finished the *Replace Data Value with Object*  refactoring.

Select name of "Customer"

#|en|+ Here we have a customer class…

Select name of "Order"

#|en|+ …that is used in the order class…

Select name of "numberOfOrdersFor"

#|en|= …and client code that is used by both classes.

Select name of "Customer"

#|en| Currently, the customer in the order class is used as a data value. In other words, each order has own instance of `Customer` even if the actual customer is the same. We want to change the code so that multiple orders for the same customer use the same instance of the `Customer` class.

#|en| In our case, this means that for each customer name, there must exist one and only one instance of the customer class.

#|en| We start with <a href="/replace-constructor-with-factory-method">Replace Constructor with Factory Method</a>. This lets us keep an eye on the process of creation of customer objects, which is extremely important for what we are going to do. We create the factory method in the customer class.

Go to before "public Customer"
Print:
```

  public static Customer create(String name) {
    return new Customer(name);
  }
```

Select "new Customer(customerName)"

#|en| Then we replace the call to the `Customer` class constructor with a reference to the factory method.

Replace "Customer.create(customerName)"

Select visibility of "public Customer"

#|en| We can now make the customer constructor private.

Print "private"

Set step 2

Select name of "Customer"

#|en| A decision must be made: Which object will be responsible for providing access to instances of the customer class? It would be good to have a registry object for this purpose, containing a pool of all reference objects and retrieving the necessary instances from it. For example, if you need to put several products in an order, each product can be stored inside the order object.

#|en| But, in this case, no such object exists for customers. To not create a new class for storing a customer registry, you can set up storage by using a static field in the `Customer` class.

Go to the start of "Customer"

Print:
```

  private static Dictionary instances = new Hashtable();

```

Set step 3

#|en| Then decide how to create customers: in advance or dynamically (as needed). We will use the first way. When launching the application, we will load the clients that are currently "in use". We can take this information from a database or file, for example.

#|en| For simplicity, we will use explicit code for loading customers. This makes it possible to change it by using <a href="/substitute-algorithm">Substitute Algorithm</a>.

Print:
```

  // This code should be executed at the program launch.
  static void loadCustomers() {
    new Customer("Lemon Car Hire").store();
    new Customer("Associated Coffee Machines").store();
    new Customer("Bilston Gasworks").store();
  }
  private void store() {
    instances.put(this.getName(), this);
  }

```

Set step 4

Select name of "create"

#|en| Now we modify the factory method of the `Customer` class so that it returns the previously created customer.

Select "new Customer(name)" in "create"

Replace "(Customer) instances.get(name)"

Select name of "create"

#|en| And since the `create()` method now always returns an existing customer, this should be clarified with the help of <a href="/rename-method">Rename Method</a>.

Print "getNamed"

Wait 500ms

Select "Customer.|||create|||"

Replace "getNamed"

#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!

Set final step

#|en|Q The refactoring is complete! You can compare the old and new code if you like.