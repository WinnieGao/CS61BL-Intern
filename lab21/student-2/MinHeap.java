import java.util.ArrayList;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /* Returns the element at index INDEX. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        this.contents.set(index2, element1);
        this.contents.set(index1, element2);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        return 2 * index;
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        return 2 * index + 1;
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
       return index / 2;
    }

    /* Sets ELEMENT as the left child of the element at the index INDEX. */
    private void setLeft(int index, E element) {
        setElement(getLeftOf(index), element);
    }

    /* Sets ELEMENT as the right child of the element at the index INDEX. */
    private void setRight(int index, E element) {
        setElement(getRightOf(index), element);
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. */
    private int min(int index1, int index2) {
        E val1 = contents.get(index1);
        E val2 = contents.get(index2);
        if (val1.compareTo(val2) < 0) {
            return index1;
        } else {
            return index2;
        }
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E peek() {
        return contents.get(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    private void bubbleUp(int index) {
        while (index != 1 && min(index, getParentOf(index)) == index) {
            swap(index, getParentOf(index));
            index = getParentOf(index);
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        while (min(getLeftOf(index), getRightOf(index)) <= contents.size()
                && min(index, min(getLeftOf(index), getRightOf(index))) != index) {
            int m = min(getLeftOf(index), getRightOf(index));
            swap(index, m);
            index = m;
        }
    }

    /* Inserts element into the MinHeap. */
    public void insert(E element) {
        contents.add(element);
        bubbleUp(contents.size() - 1);
    }

    /* Returns the smallest element. */
    public E removeMin() {
        E min = this.peek();
        swap(1, contents.size());
        if (contents.size() >= 2) {
            bubbleDown(1);
        }
        return min;
    }

    /* Updates the position of ELEMENT inside the MinHeap. If the element does
       not exist in the MinHeap, do nothing. */
    public void update(E element) {
        int index = -1;
        for (E e : contents) {
            index += 1;
            if (e.equals(element)) {
                if (min(index, getParentOf(index)) == index) {
                    bubbleUp(index);
                }
                if (min(index, min(getLeftOf(index), getRightOf(index))) != index) {
                    bubbleDown(index);
                }
            }
        }
    }
}
