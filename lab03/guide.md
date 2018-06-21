# Lab 3

## Exercises

### Fill-in-the-Blanks

Students have to add code to a Java file in up to two possible designated
locations. This exercise is mainly to ensure students can handle if and while
statements; if they get an `ArrayIndexOutOfBounds` exception, they probably ran
the program without passing in a command line argument. An infinite loop
probably means they didn't decrease the value of `dayOfYear`

### IntList

People who are trying to solve problems iteratively may try to modify the
object by using `this` which doesn't work. Tell them to use a IntList pointer
that points to `this`.

In addition, encourage students to draw box and pointer diagrams!

#### `get`

The students must implement the `get` method, which will require the students
to use either recursive or iterative strategy to solve the problem. The
students will have to check for edge-cases like negative index or index <=
number of elements. The index starts at 0.

#### `toString`

Implementing `toString` method can be a little harder than usual, so
encouraging them to write their own helper method to accomplish such task would
be nice. Or, they could take an iterative approach. Encourage them to look up
how to concatenate strings, it may help. May need to remind the students that
they need to return the string, not just print it.

#### `equals`

Implementing `equals` method is relatively straightforward, but there are some
edge-cases to consider. Especially null checks and type checks should be done
along with proper casting. The method does not ask if the two lists are the
same object, but if their representations are the same.

It is okay just to copy the cast from the `Point` example in the lab: we don't
expect them to know what that means.
