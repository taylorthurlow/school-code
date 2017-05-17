public class MaxHeap<T extends Comparable<? super T>> {

	private static final int HEAP_SIZE = 100;
	private T[] heap;
	private int lastIndex;
	private int numSwaps = 0;

	public MaxHeap() {
		T[] tempHeap = (T[]) new Comparable[HEAP_SIZE + 1];
		heap = tempHeap;
		lastIndex = 0;

		// Need to add nodes with add() for this one
	}

	public MaxHeap(T[] entries) {
		T[] tempHeap = (T[]) new Comparable[HEAP_SIZE + 1];
		heap = tempHeap;
		lastIndex = 0;

		// Fill heap in wrong order
		for (int i = 0; i < entries.length; i++) {
			heap[i + 1] = entries[i];
			lastIndex++;
		}

		// Start at backside and perform reheap on all non-leaf nodes
		for (int i = lastIndex / 2; i > 0; i--)
			reheap(i);
	}

	// Dumb add method, pass in entries in constructor to use smart add
	public void add(T newEntry) {
		int targetIndex = lastIndex + 1;
		int parentIndex = targetIndex / 2;

		// Compare new node with its parent
		while (parentIndex > 0 && newEntry.compareTo(heap[parentIndex]) > 0) {
			// Swap new node with parent because it's bigger
			heap[targetIndex] = heap[parentIndex];
			targetIndex = parentIndex;
			parentIndex = targetIndex / 2;
			numSwaps++;
		}

		// Found the proper place now, set the value
		heap[targetIndex] = newEntry;
		lastIndex++;
	}

	public T removeMax() {
		T root = null;
		if (!isEmpty()) {
			root = heap[1];
			heap[1] = heap[lastIndex];
			lastIndex--;
			reheap(1);
		}

		return root;
	}

	private void reheap(int rootIndex) {
		boolean finished = false;
		T swapped = heap[rootIndex];
		int leftChildIndex = 2 * rootIndex;

		while (!finished && leftChildIndex <= lastIndex) {
			int largerIndex = leftChildIndex; // Assume left is biggest
			int rightChildIndex = leftChildIndex + 1;
			// Check if right is actually larger
			if (rightChildIndex <= lastIndex && heap[rightChildIndex].compareTo(heap[largerIndex]) > 0)
				largerIndex = rightChildIndex;

			if (swapped.compareTo(heap[largerIndex]) < 0) {
				// Swapped node is smaller than it's largest child, needs to be swapped
				heap[rootIndex] = heap[largerIndex];
				rootIndex = largerIndex;
				leftChildIndex = 2 * rootIndex;
				numSwaps++;
			} else {
				finished = true;
			}
		}

		heap[rootIndex] = swapped;
	}

	public T getMax() {
		if (!isEmpty())
			return heap[1];
		else
			return null;
	}

	public boolean isEmpty() {
		return lastIndex < 1;
	}

	public int getNumSwaps() {
		return numSwaps;
	}

	public int getSize() {
		return lastIndex;
	}

	public T[] getHeap() {
		return heap;
	}

	public T getValueAt(int i) {
		return heap[i];
	}

	public void clear() {
		while (lastIndex > 0) {
			heap[lastIndex] = null;
			lastIndex--;
		}

		lastIndex = 0;
	}
}
