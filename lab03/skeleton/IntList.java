/**
 * A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 */
public class IntList {
    public int first;
    public IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /** Returns an IntList consisting of the given values. */
    public static IntList of(int... values) {
        if (values.length == 0) {
            return null;
        }
        IntList p = new IntList(values[0], null);
        IntList front = p;
        for (int i = 1; i < values.length; i++) {
            p.rest = new IntList(values[i], null);
            p = p.rest;
        }
        return front;
    }

    /** Returns the size of the list. */
    public int size() {
        if (rest == null) {
            return 1;
        }
        return 1 + rest.size();
    }

    /** Returns [position]th value in this list. */
    public int get(int position) {
        // TODO: YOUR CODE HERE
        if (position == 0) {
            return this.first;
        } else {
            return this.rest.get(position - 1);
        }
    }

    /** Returns the string representation of the list. */
    public String toString() {
        IntList temp = this;
        String ans = "";
        while (temp != null) {
            ans += temp.first;
            temp = temp.rest;
        }
        return ans;
    }

    /** Returns whether this and the given list or object are equal. */
    public boolean equals(Object o) {
        IntList other = (IntList) o;
        this.getClass().equals(other.getClass());
        if (this.size() != other.size()) {
            return false;
        }
        IntList temp = this;
        while (temp != null) {
            if (temp.first != other.first) {
                return false;
            } else {
                temp = temp.rest;
                other = other.rest;
            }
        }
    }
}
