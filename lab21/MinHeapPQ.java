/* A PriorityQueue class that uses a min heap to maintain ordering. */
public class MinHeapPQ<T> implements PriorityQueue<T> {

    /* The heap backing our HeapPQ. */
    private MinHeap<PriorityItem> heap;

    /* Initializes an empty HeapPQ. */
    public MinHeapPQ() {
        heap = new MinHeap<PriorityItem>();
    }

    /* Returns the item with the smallest priority value, but does not remove it
       from the HeapPQ. */
    public T peek() {
        return heap.peek().item();
    }

    /* Inserts ITEM with the priority value PRIORITY into the HeapPQ. */
    public void insert(T item, double priority) {
        heap.insert(new PriorityItem(item, priority));
    }

    /* Returns the item with the highest priority (smallest priority value), and
       removes it from the HeapPQ. */
    public T poll() {
        return heap.removeMin().item();
    }

    /* Changes the PriorityItem with item ITEM to have priority value PRIORITY.
       Assume the items in the HeapPQ are all distinct. Check for item equality
       with .equals(), not ==. */
    public void changePriority(T item, double priority) {
        heap.update(new PriorityItem(item, priority));
    }

    /* A wrapper class that stores items and their associated priorities. */
    public class PriorityItem implements Comparable<PriorityItem> {
        private T item;
        private double priority;

        private PriorityItem(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        public T item(){
            return this.item;
        }

        public double priority() {
            return this.priority;
        }

        @Override
        public String toString() {
            return "(PriorityItem: " + this.item.toString() + ", "
                    + this.priority + ")";
        }

        @Override
        public int compareTo(PriorityItem o) {
            double diff = this.priority - o.priority;
            if (diff > 0) {
                return 1;
            } else if (diff < 0) {
                return -1;
            } else {
                return 0;
            }
        }

        @Override
		public boolean equals(Object o) {
			if (getClass() == o.getClass()) {
				PriorityItem p = (PriorityItem) o;
				return p.item.equals(item);
			}
			return false;
		}
    }
}
