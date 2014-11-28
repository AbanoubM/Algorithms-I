/*************************************************************************
 * Name: Abanoub Milad
 * Email: abanoubcs@gmail.com
 * Description: randomized queue java implementation.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int front, rear;
	private Item[] que;

	private class ArrayIterator implements Iterator<Item> {
		private int[] indices;
		private int cursor;

		public ArrayIterator() {
			cursor = 0;
			indices = new int[size()];
			int itr = 0;

			// no circular case occuring
			if (rear > front) {
				for (int i = front; i < rear; i++, itr++)
					indices[itr] = i;
				// circular case occuring
			} else if (!isEmpty()) {
				for (int i = front; i < que.length; i++, itr++) {
					indices[itr] = i;
				}
				for (int i = 0; i < rear; i++, itr++)
					indices[itr] = i;
			}
			StdRandom.shuffle(indices);

		}

		public boolean hasNext() {
			return cursor < indices.length;
		}

		public void remove() { /* not supported */
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (cursor >= indices.length)
				throw new NoSuchElementException();

			Item item = que[indices[cursor]];
			cursor++;
			return item;
		}
	}

	// construct an empty randomized queue
	public RandomizedQueue() {
		front = 0;
		rear = 0;
		que = (Item[]) new Object[4];
	}

	// is the queue empty?
	public boolean isEmpty() {
		return front == rear;
	}

	// return the number of items on the queue
	public int size() {
		if (rear >= front)
			return rear - front;
		return que.length - front + rear;

	}

	// add the item
	public void enqueue(Item item) {
		if (item == null)
			throw new NullPointerException();
		que[rear++] = item;
		if (front == rear)
			resizeUp(que.length * 2);
		else if (rear == que.length) {
			if (front != 0)
				rear = 0;
			else
				resizeUp(que.length * 2);
		}
	}

	// delete and return a random item
	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		int index = (front + StdRandom.uniform(size())) % que.length;
		Item item = que[index];
//		que[index] = null;

		rear = (rear - 1 + que.length) % que.length;
//
//		if (isEmpty()) {
//			front = 0;
//			rear = 0;
//			que = (Item[]) new Object[4];
//		} else {
			que[index] = que[rear];
			que[rear] = null;
			if (size() == que.length / 4 && size()!=0)
				resizeDown(que.length / 2);
//		}

		return item;
	}

	// return (but do not delete) a random item
	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		int index = (front + StdRandom.uniform(size())) % que.length;
		return que[index];
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new ArrayIterator();
	}

	private void resizeUp(int capacity) {
		Item[] copy = (Item[]) new Object[capacity];
		// no circular case occuring
		int itr = 0;
		if (rear > front) {
			for (int i = front; i < rear; i++, itr++)
				copy[itr] = que[i];
		} else {
			for (int i = front; i < que.length; i++, itr++)
				copy[itr] = que[i];
			for (int i = 0; i < rear; i++, itr++)
				copy[itr] = que[i];
		}
		front = 0;
		rear = itr;
		que = copy;
	}

	private void resizeDown(int capacity) {
		Item[] copy = (Item[]) new Object[capacity];
		int itr = 0;
		// no circular case occuring
		if (rear > front) {
			for (int i = front; i < rear; i++, itr++)
				copy[itr] = que[i];
			// circular case occuring
		} else {
			for (int i = front; i < que.length; i++, itr++)
				copy[itr] = que[i];
			for (int i = 0; i < rear; i++, itr++)
				copy[itr] = que[i];
		}
		front = 0;
		rear = itr;
		que = copy;
	}

	// unit testing
	public static void main(String[] args) {

	}
}