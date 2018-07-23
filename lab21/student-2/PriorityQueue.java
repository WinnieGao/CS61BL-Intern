/* PriorityQueue interface. Unlike Java's priority queue, items are not limited
   to only Comparable objects and can store any type of object (represented by
   type T) and an associated priority value. */
public interface PriorityQueue<T> {

    /* Returns but does not remove the highest priority element. */
    public T peek();

    /* Inserts ITEM into the PriorityQueue with priority value PRIORITY. */
    public void insert(T item, double priority);

    /* Removes and returns the highest priority element. */
    public T poll();

    /* Changes the priority value of ITEM to PRIORITY. Assume the items in the
       PriorityQueue are all distinct. */
    public void changePriority(T item, double priority);

}
