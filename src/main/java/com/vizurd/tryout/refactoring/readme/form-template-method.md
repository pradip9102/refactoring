form-template-method:java

---

1. Split algorithms in the subclasses into their constituent parts described in separate methods. <a href="/extract-method">Extract Method</a> can help with this.

2. The resulting methods that are identical for all subclasses can be moved to a superclass via <a href="/pull-up-method">Pull Up Method</a>.

3. The non-similar methods can be given consistent names via <a href="/rename-method">Rename Method</a>.

4. Move the signatures of non-similar methods to a superclass as abstract ones by using <a href="/pull-up-method">Pull Up Method</a>. Leave their implementations in the subclasses.

5. And finally, pull up the main method of the algorithm to the superclass. Now it should work with the method steps described in the superclass, both real and abstract.



---

```
class Article {
  // ...
  public String getTitle() { /* … */ }
  public String getIntro() { /* … */ }
  public String getBody() { /* … */ }
  public String getAuthor() { /* … */ }
  public String getDate() { /* … */ }

  public String markdownView() {
    String output = "# " + getTitle() + "\n\n";
    output += "> " + getIntro() + "\n\n";
    output += getBody() + "\n\n";
    output += "_Written by " + getAuthor() + " on " + getDate() + "_";
    return output;
  }
  public String htmlView() {
    String output = "<h2>" + getTitle() + "</h2>" + "\n";
    output += "<blockquote>" + getIntro() + "</blockquote>" + "\n";
    output += "<p>" + getBody() + "</p>" + "\n";
    output += "<em>Written by " + getAuthor() + " on " + getDate() + "</em>";
    return output;
  }
}
```

---

```
class Article {
  // ...
  public String getTitle() { /* … */ }
  public String getIntro() { /* … */ }
  public String getBody() { /* … */ }
  public String getAuthor() { /* … */ }
  public String getDate() { /* … */ }

  public String markdownView() {
    return new ArticleMarkdown(this).view();
  }
  public String htmlView() {
    return new ArticleHtml(this).view();
  }
}

abstract class ArticleView {
  protected Article article;
  protected ArticleView(Article article) {
    this.article = article;
  }
  protected abstract String title();
  protected abstract String intro();
  protected abstract String body();
  protected abstract String footer();
  public String view() {
    return title() + intro() + body() + footer();
  }
}

class ArticleMarkdown extends ArticleView {
  public ArticleMarkdown(Article article) {
    super(article);
  }
  @Override protected String title() {
    return "# " + article.getTitle() + "\n\n";
  }
  @Override protected String intro() {
    return "> " + article.getIntro() + "\n\n";
  }
  @Override protected String body() {
    return article.getBody() + "\n\n";
  }
  @Override protected String footer() {
    return "_Written by " + article.getAuthor() + " on " + article.getDate() + "_";
  }
}

class ArticleHtml extends ArticleView {
  public ArticleHtml(Article article) {
    super(article);
  }
  @Override protected String title() {
    return "<h2>" + article.getTitle() + "</h2>" + "\n";
  }
  @Override protected String intro() {
    return "<blockquote>" + article.getIntro() + "</blockquote>" + "\n";
  }
  @Override protected String body() {
    return "<p>" + article.getBody() + "</p>" + "\n";
  }
  @Override protected String footer() {
    return "<em>Written by " + article.getAuthor() + " on " + article.getDate() + "</em>";
  }
}
```

---

###### Set step 1

Select name of "Article"


#|en|+ Let's look at this refactoring using the example of an article that can be displayed in two formats…


Select name of "markdownView"


#|en|<+ …in Markdown plain text…


Select name of "htmlView"


#|en|<= …and in HTML.


#|en| Before starting the refactoring per se, we should arrange things so that these two methods appear in the subclasses of some shared parent class.

Select whole "markdownView"
+Select whole "htmlView"


#|en| To do this, we create a <a href="/replace-method-with-method-object">simple method object</a> by moving both methods to it.

Go to after "Article"

Print:
```


class ArticleView {
  protected Article article;
  public ArticleView(Article article) {
    this.article = article;
  }
  public String markdownView() {
    String output = "# " + article.getTitle() + "\n\n";
    output += "> " + article.getIntro() + "\n\n";
    output += article.getBody() + "\n\n";
    output += "_Written by " + article.getAuthor() + " on " + article.getDate() + "_";
    return output;
  }
  public String htmlView() {
    String output = "<h2>" + article.getTitle() + "</h2>" + "\n";
    output += "<blockquote>" + article.getIntro() + "</blockquote>" + "\n";
    output += "<p>" + article.getBody() + "</p>" + "\n";
    output += "<em>Written by " + article.getAuthor() + " on " + article.getDate() + "</em>";
    return output;
  }
}
```

Wait 500ms

Select body of "markdownView"
+ Select body of "htmlView"


#|en| Now the bodies of the original methods can be replaced with calls to the `ArticleView` methods.

Select body of "markdownView"

Replace:
```
    return new ArticleView(this).markdownView();
```

Wait 500ms

Select body of "htmlView"

Replace:
```
    return new ArticleView(this).htmlView();
```

Wait 500ms

Select name of "ArticleView"


#|en| Then from `ArticleView` we can extract two subclasses, `ArticleMarkdown` and `ArticleHtml`, by moving the corresponding methods to them.

Go to after "ArticleView"


#|en| Let's create a `ArticleMarkdown` class.

Print:
```


class ArticleMarkdown extends ArticleView {
  public ArticleMarkdown(Article article) {
    super(article);
  }
}
```

Wait 500ms

Select whole of "markdownView" in "ArticleView"


#|en| Move corresponding method to it.

Remove selected

Wait 500ms

Go to end of "ArticleMarkdown"

Print:
```

  public String markdownView() {
    String output = "# " + article.getTitle() + "\n\n";
    output += "> " + article.getIntro() + "\n\n";
    output += article.getBody() + "\n\n";
    output += "_Written by " + article.getAuthor() + " on " + article.getDate() + "_";
    return output;
  }
```

Wait 500ms

Select "ArticleView" in "markdownView" of "Article"

Replace "ArticleMarkdown"

Wait 500ms

Go to after "ArticleMarkdown"


#|en| Now let's create a `ArticleHtml` class.

Print:
```


class ArticleHtml extends ArticleView {
  public ArticleHtml(Article article) {
    super(article);
  }
}
```

Wait 500ms

Select whole of "htmlView" in "ArticleView"


#|en| And similarly move the remaining method to it.

Remove selected

Wait 500ms

Go to end of "ArticleHtml"

Print:
```

  public String htmlView() {
    String output = "<h2>" + article.getTitle() + "</h2>" + "\n";
    output += "<blockquote>" + article.getIntro() + "</blockquote>" + "\n";
    output += "<p>" + article.getBody() + "</p>" + "\n";
    output += "<em>Written by " + article.getAuthor() + " on " + article.getDate() + "</em>";
    return output;
  }
```

Wait 500ms

Select "ArticleView" in "htmlView" of "Article"

Replace "ArticleHtml"

Wait 500ms

Select name of "markdownView" in "ArticleMarkdown"
+ Select name of "htmlView" in "ArticleHtml"


#|en| Since the methods are now located in different classes, we can give them identical names.

Replace "view"

Wait 500ms

Select "markdownView" in body of "markdownView" of "Article"
+ Select "htmlView" in body of "htmlView" of "Article"

Replace "view"

Select name of "ArticleHtml"
+ Select name of "ArticleMarkdown"


#C|en| Let's launch autotests to check for errors in the code.
#S Everything is OK! We can keep going.

#|en| Finally, everything is ready to start the refactoring itself.


#|en| First split the `view` methods in both steps to their constituent steps. Defining the steps is rather easy in our case – these are parts of the printed article.

Select name of "ArticleMarkdown"


#|en| Start with the `ArticleMarkdown` class.

Go to end of "ArticleMarkdown"

Print:
```

  private String title() {
    return "# " + article.getTitle() + "\n\n";
  }
  private String intro() {
    return "> " + article.getIntro() + "\n\n";
  }
  private String body() {
    return article.getBody() + "\n\n";
  }
  private String footer() {
    return "_Written by " + article.getAuthor() + " on " + article.getDate() + "_";
  }
```

Select body of "view" in "ArticleMarkdown"


#|en| Now we can replace parts of the `view` method with calls to the new methods.

Print:
```
    return title() + intro() + body() + footer();
```

Select name of "ArticleHtml"


#|en| Do all of this for the `ArticleHtml` class.

Go to end of "ArticleHtml"

Print:
```

  private String title() {
    return "<h2>" + article.getTitle() + "</h2>" + "\n";
  }
  private String intro() {
    return "<blockquote>" + article.getIntro() + "</blockquote>" + "\n";
  }
  private String body() {
    return "<p>" + article.getBody() + "</p>" + "\n";
  }
  private String footer() {
    return "<em>Written by " + article.getAuthor() + " on " + article.getDate() + "</em>";
  }
```

Select body of "view" in "ArticleHtml"


#|en| Now we can replace parts of the `view` method with calls to the new methods.

Print:
```
    return title() + intro() + body() + footer();
```

###### Set step 4

Go to type "ArticleView"


#|en| As you can see, the two classes have many identical steps. We should move the identical steps as abstract methods to the superclass.

Print "abstract "

Wait 500ms

Select visibility of "public ArticleView"

Replace "protected"

Wait 500ms

Go to end of "ArticleView"

Wait 500ms

Print:
```

  protected abstract String title();
  protected abstract String intro();
  protected abstract String body();
  protected abstract String footer();
```

Wait 500ms

Select "|||private||| String"

Replace "@Override protected"

Wait 500ms

Select whole of "view" in "ArticleMarkdown"
+ Select whole of "view" in "ArticleHtml"

###### Set step 5


#|en| Now we can freely extract the identical `view` methods to the superclass.

Remove selected

Go to end of "ArticleView"

Print:
```

  public String view() {
    return title() + intro() + body() + footer();
  }
```


#C|en| Let's start the final testing.
#S Wonderful, it's all working!


###### Set final step


#|en|Q The refactoring is complete! You can compare the old and new code if you like.
