/* A doubly-linked list supporting various sorting algorithms. */
public class DLList<T extends Comparable<T>> {

    private class Node {

        T item;
        Node prev;
        Node next;

        Node(T item) {
            this.item = item;
            this.prev = this.next = null;
        }

        Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    /* The sentinel of this DLList. */
    Node sentinel;
    /* The number of items in this DLList. */
    int size;

    /* Creates an empty DLList. */
    public DLList() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        this.size = 0;
    }

    /* Creates a copy of DLList represented by LST. */
    public DLList(DLList<T> lst) {
        Node ptr = lst.sentinel.next;
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        this.size = 0;
        while (ptr != lst.sentinel) {
            addLast(ptr.item);
            ptr = ptr.next;
        }
    }

    /* Returns true if this DLList is empty. Otherwise, returns false. */
    public boolean isEmpty() {
        return size == 0;
    }

    /* Adds a new Node with item ITEM to the front of this DLList. */
    public void addFirst(T item) {
        Node newNode = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size += 1;
    }

    /* Adds a new Node with item ITEM to the end of this DLList. */
    public void addLast(T item) {
        Node newNode = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size += 1;
    }

    /* Removes the Node referenced by N from this DLList. */
    private void remove(Node n) {
        n.prev.next = n.next;
        n.next.prev = n.prev;
        n.next = null;
        n.prev = null;
        size -= 1;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Node ptr = sentinel.next; ptr != sentinel; ptr = ptr.next) {
            s.append(ptr.item.toString());
            s.append(" ");
        }
        return s.toString();
    }

    /* Returns a copy of this DLList sorted using insertion sort. Does not
       modify the original DLList. */
    public DLList<T> insertionSort() {
        DLList<T> toReturn = new DLList<>();
        for (Node ptr = sentinel.next; ptr != sentinel; ptr = ptr.next) {
            toReturn.insertionSortHelper(ptr.item);
        }
        return toReturn;
    }

    /* Inserts ITEM into this DLList such that the values of this DLList are in
       increasing order. */
    private void insertionSortHelper(T item) {
        this.addLast(item);
        if (this.size <= 1) {
            return;
        }
        Node n = sentinel.prev;
        while (n.prev != sentinel && n.item.compareTo(n.prev.item) < 0) {
            Node l = n;
            swap(n.prev, n);
            n = l;
        }
    }

    private void swap(Node a, Node b) {
            a.next = b.next;
            b.prev = a.prev;
            a.next.prev = a;
            b.prev.next = b;
            b.next = a;
            a.prev = b;
    }

    /* Returns a copy of this DLList sorted using selection sort. Does not
       modify the original DLList. */
    public DLList<T> selectionSort() {
        DLList<T> copy = new DLList<>(this);
        DLList<T> toReturn = new DLList<>();
        while (copy.size != 0) {
            Node min = copy.sentinel.next;
            for (Node p = copy.sentinel.next; p != copy.sentinel; p = p.next) {
                if (p.item.compareTo(min.item) < 0) {
                    min = p;
                }
            }
            toReturn.addLast(min.item);
            copy.remove(min);
        }
        return toReturn;
    }

    /* Returns a copy of this DLList sorted using merge sort. Does not modify
       the original DLList. */
    public DLList<T> mergeSort() {
        if (size <= 1) {
            return this;
        }
        DLList<T> oneHalf = new DLList<>();
        DLList<T> otherHalf = new DLList<>();
        int h = size / 2;
        Node n = sentinel;
        for (int i = 0; i < h; i += 1) {
            n = n.next;
            oneHalf.addLast(n.item);
        }
        for (int i = h; i < size; i += 1) {
            n = n.next;
            otherHalf.addLast(n.item);
        }
        return oneHalf.mergeSort().merge(otherHalf.mergeSort());
    }

    /* Returns the result of merging this DLList with LST. Does not modify the
       two DLLists. Assumes that this DLList and LST are in sorted order. */
    private DLList<T> merge(DLList<T> lst) {
        DLList<T> toReturn = new DLList<T>();
        Node thisPtr = sentinel.next;
        Node lstPtr = lst.sentinel.next;
        while (thisPtr != sentinel && lstPtr != lst.sentinel) {
            if (thisPtr.item.compareTo(lstPtr.item) < 0) {
                toReturn.addLast(thisPtr.item);
                thisPtr = thisPtr.next;
            } else {
                toReturn.addLast(lstPtr.item);
                lstPtr = lstPtr.next;
            }
        }
        while (thisPtr != sentinel) {
            toReturn.addLast(thisPtr.item);
            thisPtr = thisPtr.next;
        }
        while (lstPtr != lst.sentinel) {
            toReturn.addLast(lstPtr.item);
            lstPtr = lstPtr.next;
        }
        return toReturn;
    }

    /* Returns a copy of this DLList sorted using quicksort. The first element
       is used as the pivot. Does not modify the original DLList. */
    public DLList<T> quicksort() {
        if (size <= 1) {
            return this;
        }
        // Assume first element is the divider.
        Boolean increasing = true;
        for (Node n = sentinel.next; n != sentinel.prev; n = n.next) {
            if (n.item.compareTo(n.next.item) > 0) {
                increasing = false;
                break;
            }
        }
        if (increasing) {
            return this;
        }
        Boolean decreasing = true;
        for (Node n = sentinel.next; n != sentinel.prev; n = n.next) {
            if (n.item.compareTo(n.next.item) < 0) {
                decreasing = false;
                break;
            }
        }
        if (decreasing) {
            DLList<T> result = new DLList<>();
            for (Node n = sentinel.prev; n != sentinel; n = n.prev) {
                result.addLast(n.item);
            }
            return result;
        }

        DLList<T> smallElements = new DLList<>();
        DLList<T> equalElements = new DLList<>();
        DLList<T> largeElements = new DLList<>();
        T pivot = sentinel.next.item;
        for (Node n = sentinel.next; n != sentinel; n = n.next) {
            if (n.item.compareTo(pivot) < 0) {
                smallElements.addLast(n.item);
            }
            if (n.item.compareTo(pivot) == 0) {
                equalElements.addLast(n.item);
            }
            if (n.item.compareTo(pivot) > 0) {
                largeElements.addLast(n.item);
            }
        }
        DLList<T> sortSmall = smallElements.quicksort();
        DLList<T> sortLarge = largeElements.quicksort();
        sortSmall.append(equalElements);
        sortSmall.append(sortLarge);
        return sortSmall;
    }

    /* Appends LST to the end of this DLList. */
    public void append(DLList<T> lst) {
        if (lst.isEmpty()) {
            return;
        }
        if (isEmpty()) {
            sentinel = lst.sentinel;
            size = lst.size;
            return;
        }
        sentinel.prev.next = lst.sentinel.next;
        lst.sentinel.next.prev = sentinel.prev;
        sentinel.prev = lst.sentinel.prev;
        lst.sentinel.prev.next = sentinel;
        size += lst.size;
    }

    /* Returns a random integer between 0 and 99. */
    private static int randomInt() {
        return (int) (100 * Math.random());
    }

    private static DLList<Integer> generateRandomIntegerDLList(int N) {
        DLList<Integer> toReturn = new DLList<>();
        for (int k = 0; k < N; k++) {
            toReturn.addLast((int) (100 * Math.random()));
        }
        return toReturn;
    }

    public static void main(String[] args) {
        DLList values;
        DLList sortedValues;

        System.out.print("Before insertion sort: ");
        values = generateRandomIntegerDLList(10);
        System.out.println(values);
        sortedValues = values.insertionSort();
        System.out.print("After insertion sort: ");
        System.out.println(sortedValues);

        System.out.print("Before selection sort: ");
        values = generateRandomIntegerDLList(10);
        System.out.println(values);
        sortedValues = values.selectionSort();
        System.out.print("After selection sort: ");
        System.out.println(sortedValues);

        System.out.print("Before merge sort: ");
        values = generateRandomIntegerDLList(10);
        System.out.println(values);
        sortedValues = values.mergeSort();
        System.out.print("After merge sort: ");
        System.out.println(sortedValues);

        System.out.print("Before quicksort: ");
        values = generateRandomIntegerDLList(10);
        System.out.println(values);
        sortedValues = values.quicksort();
        System.out.print("After quicksort: ");
        System.out.println(sortedValues);

        System.out.print("Own test for Qsort: ");
        values = new DLList();
        values.addLast(4);
        values.addLast(8);
        values.addLast(7);
        values.addLast(9);
        values.addLast(9);

        System.out.println(values);
        sortedValues = values.quicksort();
        System.out.print("After Qsort: ");
        System.out.print(sortedValues);
    }
}
