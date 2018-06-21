***Please check the course website for the most recent version of the spec if available.***

---
layout: page
title: "Tracing, IntLists, & Recursion"
tags: [Lab, Java]
---

## Before You Begin ##

Pull the code for Lab 3 from GitHub.

### Learning Goals for Today

This lab builds on our knowledge of Java from [Lab 2][] and the [Java
introduction][].

[Lab 2]: {{ site.baseurl }}/labs/lab02
[Java introduction]: {{ site.baseurl }}/java

We'll start by quickly introducing a few new syntax features which will be
helpful to round out our understanding of loops and conditionals, followed by a
deeper dive into arrays.

We assume no prior experience with any of these topics in Java, but we assume
some prior knowledge of these concepts, either from reading the textbook or
(preferably) from an earlier course like Python control flow and lists from
CS 61A.

This course strives to teach you how to "program", and this includes not just
teaching you how to write code, but how to do a variety of other activities.
Today's lab includes some discussion questions that test your ability to not
only write code, but also analyze code (figure out what code does), test code
(see if given code is doing what it should do), and evaluate multiple versions
of code.

While it is easy to skip over these sections, we highly recommend that you
spend due time on all these activities since they are extremely important skills
for programmers to learn and later labs and projects will be much harder if you
have not mastered these skills.

### Find A Partner

As usual, your section leader will encourage you to find a different partner
today. After this week, and as part of project 1, you'll have to choice to work
with one partner more consistently. For now, we just want you to meet a few
other classmates and learn more about which learning styles work well with you.

Be alert to differences between your approaches to today's activities and your
partner's. Which ones work best for you? Which ones don't work as well?

## Exercise: Fill-in-the-Blanks

The program `DateConverter.java` in the `lab03` skeleton folder is missing two
assignment statements. The missing statements can either be at the beginning,
the end, or at both the beginning and the end of the loop.

In a bit, you'll determine what the statements are and where they go. But
first, you'll come up with a small but comprehensive set of tests for the code
before writing the code itself. This technique is called *test-driven
development*, and we'll be doing it more in subsequent labs.

Testing the code involves supplying a value for `dayOfYear` on the command
line. A few new things about the code:

- The value for `dayOfYear` is read from `args[0]`, which is a command line
  argument. Review the previous labs if you don't remember how to run a program
  with different command line arguments.
- The statement `import java.io.*;` makes Java library methods involving input
  and output accessible inside the program. You don't have to worry about this.
- The five lines starting with `try {` catches an _exception_ that would occur
  if the command line argument isn't an integer. We'll learn about exceptions
  in a few weeks.

Along with your partner, list out five different test cases (arguments you
might give to `DataConverter`) that should be able to tell you whether or not
the program is running correctly.

Once you've finished that, complete `DateConverter.java` by putting in two
assignment statements as specified above. Once you're done with that, compile
your program and try each one of your test cases.

Did they all work as expected? Can you think of a way that your tests might not
have been comprehensive (meaning that all your tests pass but there is still
some type of input which will not work the way you want it to)?

## Exercise: Program Translation

Either do these with your partner, or compare notes with them after you
complete them on your own. Translate the following `while` loops to `for` loops.
The body of the `for` loops should contain only the call to `println`.

```java
int k = 0;
while (k < 10) {
    System.out.println(k);
    k = k + 1;
}
```

```java
int k = 0;
while (k < 10) {
    k = k + 1;
    System.out.println(k);
}
```

## Java Syntax Shortcuts

### Incrementing and Decrementing

Let `k` be an integer variable. Then the three following statements are
equivalent in that they all increment `k`.

```java
k = k + 1;
k += 1;
k++;
```

Similarly, these three statements all decrement `k` by 1.

```java
k = k - 1;
k -= 1;
k--;
```

Note: The motivation for this shorthand notation is that the operations of
incrementing and decrementing by 1 are very common. While the following example
shows that it is legal to increment or decrement variables within larger
expressions this is a risky practice very susceptible to off-by-one errors.
Therefore, we ask that you only use the `++` or `--` operations on lines by
themselves. For example,

```java
System.out.println(args[k++]);
```

Don't do this!

### Breaking from a Loop

The `break` statement "breaks out of" a single loop (both `for` and `while`
loops). In other words, it stops the execution of the loop body, and continues
with the statement immediately following the loop. An example of its use would
be a program segment that searches an array named `values` for a given `value`,
setting the variable `found` to true if the value is found and to false if the
value cannot be found in the array.

```java
boolean found = false;
for (int k = 0; k < values.length; k++) {
    if (values[k] == value) {
        found = true;
        break;
    }
}
```

This `break` statement allows us to save computation time. If we find the value
within the array before the end, we don't waste more time looping through the
rest of the array.

However, the `break` statement is not always necessary, and code with a lot of
breaks can be confusing. Abusing the break statement is often considered poor
style. When using `break`, first consider if it would be more appropriate to put
another condition in the loop's test conditions instead of introducing a more
complicated `break` statement.

### Continuing to the Next Loop

The `continue` statement skips the current iteration of the loop body,
increments the variables in the loop information, then evaluates the loop
condition. This example checks how many 0's there are in array `values`:

```java
int count = 0;
for (int i = 0; i < values.length; i++) {
    if (values[i] != 0) {
        continue;
    }
    count += 1;
}
System.out.println("Number of 0s in values array: " + count);
```

Similar to the `break` statement, the `continue` allows us to save time by
skipping sections of the loop. In this case, the `continue` allows us to add to
the `count` only when there is a 0 in the array. Removing continue will give an
incorrect output.

The difference between `break` and `continue` is that `break` immediately stops
the loop and moves on to the code directly following it. In comparison,
`continue` stops going through the current iteration of the loop body and
immediately continues on to the next iteration as given by the loop
information.

Like with `break`, abusing `continue` is often considered poor style. Try not
to go crazy with nested breaks and continues.

Both `break` and `continue` apply to only the closest loop it is enclosed in.
For instance, in the case of the following nested loop, the `break` will only
exit out of the inner for loop, not the outer one.

```java
for (int i = 0; i < values.length; i++) {
    for (int j = i + 1; j < values.length; j++) {
        if (values[i] == value[j]) {
            break;
        }
    }
}
```

### Looping through Arrays

`for` statements work well with arrays. Consider, for example, an array named
`values`. It is very common to see code like the following:

```java
for (int k = 0; k < values.length; k += 1) {
    // do something with values[k]
}
```

In fact, this pattern of looping through arrays is so common that the Java
developers added the [enhanced for loop][] so programmers wouldn't need to type
as much.

[enhanced for loops]: {{ site.baseurl }}/java/#the-enhanced-for-loop

## Discussion: Recognizing Purpose

In the real world, often you will have to deal with code you did not write.
Sometimes you will be provided documentation, sometimes you will not. Being
able to recognize what purpose code serves is an important skill. Provide a
good (read: descriptive) name for each of the following methods. Assume that
`values` contains at least one element.

Don't be worried if your name was slightly different, but make sure that you can
figure out what each method is doing before you check. If you misunderstood
what the code was for, see if you can make sense of it once you know its name.

As you work through each problem, spend some time reflecting on your problem
solving process. How did you go about solving each problem? Did you come up
with a few representative examples? Did you trace through the code? How did you
and your partner keep track of the different variable? Which variables were
easy to identify, and what made them easy to identify?

```java
private static int _______ (int[] values) {
    int rtn = values[0];
    int k = 1;
    while (k < values.length) {
        if (rtn < values[k]) {
            rtn = values[k];
        }
        k++;
    }
    return rtn;
}
```

```java
private static void _______ (int[] values) {
    int k = 0;
    while (k < values.length / 2) {
        int temp = values[k];
        values[k] = values[values.length - 1 - k];
        values[values.length - 1 - k] = temp;
        k = k + 1;
    }
}
```

```java
private static boolean _______ (int[] values) {
    int k = 0;
    while (k < values.length - 1) {
        if (values[k] > values[k + 1]) {
            return false;
        }
        k = k + 1;
    }
    return true;
}
```

```java
private static int _______ (int[] values, int a) {
    int k = 0;
    int n = 0;
    while (k < values.length) {
        if (values[k] == a) {
            n++;
        }
        k++;
    }
    return n;
}
```

## Exercise: Insert and Delete

Look at the files `ArrayOperations.java` and `ArrayOperationsTest.java`.

Fill in the blanks in the `ArrayOperations` class. Your methods should then
pass the tests in `ArrayOperationsTest`.

> Compile and run `ArrayOperationsTest` using the `javac` and `java` commands.
>
> ```sh
> javac ArrayOperationsTest.java
> java ArrayOperationsTest
> ```
>
> When compiling `ArrayOperationsTest`, Java will also make sure to compile the
> other classes it depends on, like `ArrayOperations`.

Note: Before trying to program an algorithm, you should usually try solving a
small case by hand. For each of the exercises today, work with a partner to
work through each algorithm by hand on a small example before writing any code.

`insert`
:   The `insert` method takes three arguments: an `int` array, a position in
the array, and an `int` to put into that position. All the subsequent elements
in the array are moved over by one position to make room for the new element.
The last value in the array is lost.

    For example, let `values` be the array `{1, 2, 3, 4, 5}`. Calling

    ```java
    insert(values, 2, 7)
    ```

    would result in `values` becoming `{1, 2, 7, 3, 4}`.

`delete`
:   The `delete` method takes two arguments: an `int` array and a position in
the array. The subsequent elements are moved down one position, and the value 0
is assigned to the last array element.

    For example, let `values` be the array `{1, 2, 3, 4, 5}`. Calling

    ```java
    delete(values, 2)
    ```

    would result in `values` becoming `{1, 2, 4, 5, 0}`.

For now, don't worry about the methods being called with incorrect input.

For help debugging, try either drawing out a diagram on a few test inputs, or
copy and paste your `ArrayOperations` class into the [online Java visualizer][]
and add a `main` method with the code you'd like to test.

[online Java visualizer]: https://cscircles.cemc.uwaterloo.ca/java_visualize/

## Recursive Data Structures

> Read the **[IntLists section of Chapter 2.1][]**, up until **size and
> iterativeSize**.
>
> If you'd like a narration of the next part, watch Josh Hug's video
> *Introducing IntLists* at the beginning of the section.

[IntLists section of Chapter 2.1]: https://joshhug.gitbooks.io/hug61b/content/chap2/chap21.html#intlists

**Linked lists** are the first recursive data structure we'll be learning in
this class, and the algorithms we write for linked lists will help us master
the skills necessary to work with larger and more complex data structures later
in the course. However, linked lists are also interesting and practical in their
own right, and we'll make an argument for when and how they are useful in the
next week of the course.

`IntList` is the very first linked list we will be implementing in this class.

{%- capture intListExample -%}
public class IntList {
    public int first;
    public IntList rest;

    public static void main(String[] args) {
        IntList L = new IntList();
        L.first = 5;
        L.rest = null;

        L.rest = new IntList();
        L.rest.first = 10;
        // IntList p1 = L.rest;

        L.rest.rest = new IntList();
        L.rest.rest.first = 15;
        // IntList p2 = p1.rest;
        // p1.rest = null;
    }
}
{%- endcapture -%}
{% include java_visualizer.html caption="Introducing IntLists"
   code=intListExample %}

Step through the example above and make sure you understand what each line is
doing. Then, predict what would happen if we uncommented the `p1` and `p2`
lines. Once you've made your prediction with your partner, uncomment the lines
and see what happens in the Java Visualizer.

Did your prediction match with what actually happened? Make sure both partners
understand what each line is doing.

> If it's hard to see what's going on in the Java Visualizer, enable the
> following two options from the code editor.
>
> - **Prefer non-nesting and vertical layouts**
> - **Force linked lists to display vertically**

## Discussion: Iteration vs. Recursion

Consider the two different implementations of the `size` method below. The
method is first implemented using iteration (with a `while` loop) and the other
method is implemented using recursion.

{%- capture iterationVsRecursion -%}
public class IntList {
    public int first;
    public IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /** Return the size of the list using no recursion! */
    public int iterativeSize() {
        IntList p = this;
        int totalSize = 0;
        while (p != null) {
            totalSize += 1;
            p = p.rest;
        }
        return totalSize;
    }

    /** Return the size of the list using recursion! */
    public int size() {
        if (rest == null) {
            return 1;
        }
        return 1 + this.rest.size();
    }

    public static void main(String[] args) {
        IntList L = new IntList(5,
                        new IntList(10,
                            new IntList(15, null)));
        System.out.println(L.iterativeSize());
        System.out.println(L.size());
    }
}
{%- endcapture -%}
{% include java_visualizer.html caption="iterativeSize vs. size"
   code=iterationVsRecursion %}

Discuss with your partner how each implementation differs. Make sure to answer
the following questions in your discussion.

- How does the recursive `size` keep track of the pointer, `p`, that we
  initialize in `iterativeSize`?
- How does the recursive `size` keep track of the `totalSize` which we had to
  create a variable for in `iterativeSize`?
- Why does the base case for `size` terminate when `rest == null`, rather than
  when `this == null`?

## Exercise: `get`

Implement the `get` method in the `IntList` class, which takes an `int`
position, and returns the list element at the given (zero-indexed) position in
the list.

For example, if `get(1)` is called, you should return the second item in the
list. If the position is out of range, the behavior is undefined: `get` can
error out, loop around back to the start, or do anything else you'd like it to
do. Assume `get` is always called on the first node in the list.

## Exercise: `toString` and `equals`

In [Lab 2][], we saw that the `Point` class contained an unexplained method
called `toString`.

```java
public class Point {
    public double x;
    public double y;

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
```

The `toString` method is used by Java to determine how to represent an object
as a string, like when printing objects to display to the user. In the example
below, we create a new point at the origin, $$(0, 0)$$. When calling
`System.out.println`, Java needs to figure out what exactly to print, so it
invokes the `toString` method which returns `(0.0, 0.0)`. Then, that string is
displayed to the screen.

```java
Point p = new Point();
System.out.println(p);
```

Likewise, the `equals` method is used whenever a user calls `equals`. We might
define equality between two points as follows.

```java
public class Point {
    public double x;
    public double y;

    public boolean equals(Object o) {
        Point other = (Point) o;
        return (this.x == other.x) && (this.y == other.y);
    }
}
```

Implement the standard Java methods, `toString` and `equals`, in the `IntList`
class.

`toString`
: The `toString` method for `IntList` returns the `String` representation of
this list, namely:

    1. The `String` representation of the first element, followed by a space,
    2. The `String` representation of the second element, followed by a space,
    3. ...
    4. The `String` representation of the last element.

    The list containing the integers 1, 3, and 5 is represented by the string
`1 3 5`.

> How would you convert an integer to a string in Java? Try searching for the
> answer online!

`equals`
: Given an `Object` as argument, this method returns `true` if this list and
the argument list are the same length and store equal items in corresponding
positions (determined by using the elements' `equals` method).

## Discussion: `StringList`

Draw the box-and-pointer diagram that would result from running the following
code. Make sure to represent `String` objects by pointing to them.

What are some of the techniques you and your partner found helpful for solving
this problem? How did you keep track of the state of the program and pay
attention to your current place in the program as you traced through the code?

{%- capture stringList -%}
public class StringList {
    public String first;
    public StringList rest;

    public StringList(String f, StringList r) {
        first = f;
        rest = r;
    }

    public static void main(String[] args) {
        StringList L = new StringList("eat", null);
        L = new StringList("shouldn't", L);
        L = new StringList("you", L);
        L = new StringList("sometimes", L);
        StringList M = L.rest;
        StringList R = new StringList("many", null);
        R = new StringList("potatoes", R);
        R.rest.rest = R;
        M.rest.rest.rest = R.rest;
        L.rest.rest = L.rest.rest.rest;
        L = M.rest;
    }
}
{%- endcapture -%}
{% include java_visualizer.html caption="Draw StringList.main"
   code=stringList %}

Now is also a good time to work through the Gradescope assessment for this lab
if you have not already done so!

## Conclusion

Tracing
: Practice working through box-and-pointer diagrams and come up with a
representation that helps you keep track of the state of the program. Make sure
you know how to draw box-and-pointer diagrams representing long sequences of
operations. We'll be building more on these skills in the next lab, and relying
on them in the rest of the course. Diagramming structures helps us communicate
complex ideas.

IntLists
: Using references, we recursively defined the `IntList` class. IntLists are
lists of integers that can change size (unlike arrays), and store an
arbitrarily large number of integers. Writing a `size` helper method can be
done with either recursion or iteration.

Recursion
: We'll be diving deeper into recursion in the next few weeks. If you'd like a
bit more review on recursion, we recommend some materials prepared from CS 61A.
Knowing how to convert from recursion to iteration, and from iteration to
recursion will be very useful as we apply it to solve linked list problems.

  - [John DeNero's Recursion Lecture][] and [Video](https://www.youtube.com/watch?v=1zF75dYpCHo&list=PL6BsET-8jgYXF2TzAQRQeaZwhp-9DfE1r&vq=hd1080)
  - [Kevin's Recursion Lecture][] and [Video](https://www.youtube.com/watch?v=UlVylEBR72U&vq=hd1080)

[John DeNero's Recursion Lecture]: https://inst.eecs.berkeley.edu/~cs61a/sp18/assets/slides/07-Recursion_full.pdf
[Kevin's Recursion Lecture]: https://docs.google.com/presentation/d/1IfXHf_LkgmHK5X9z5EVMovmY6b371uNlG3IzSnc7zbg/edit?usp=sharing

### Deliverables

Here's a short recap of what you need to do to finish this lab.

- Review the basic Java syntax (`if`, `else`, `while`, `for`, `break`,
  `continue`) as needed.
- Complete `DateConverter.java`.
- Complete `ArrayOperations.java` so that it passes `ArrayOperationsTest.java`
- Complete the methods in `IntList.java`.
- Submit all files to Gradescope and complete the online assessment.
