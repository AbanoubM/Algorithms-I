/*************************************************************************
 * Name: Abanoub Milad
 * Email: abanoubcs@gmail.com
 * Description: double ended queue java implementation.
 * 
 ************************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private int size;
	private DNode<Item> header, trailer;

	private class DNode<E> {
		private E item;
		private DNode<E> next;
		private DNode<E> previous;

		private DNode(E obj, DNode<E> previous, DNode<E> next) {
			item = obj;
			this.next = next;
			this.previous = previous;
		}
	}

	private class ListIterator implements Iterator<Item> {
		private DNode<Item> current = header.next;

		public boolean hasNext() {
			return current != trailer;
		}

		public void remove() { /* not supported */
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (current == trailer)
				throw new NoSuchElementException();

			Item item = current.item;
			current = current.next;
			return item;
		}
	}

	// construct an empty deque
	public Deque() {
		size = 0;
		header = new DNode<Item>(null, null, null);
		trailer = new DNode<Item>(null, header, null);
		header.next = trailer;
	}

	// is the deque empty?
	public boolean isEmpty() {
		return size == 0;
	}

	// return the number of items on the deque
	public int size() {
		return size;
	}

	// insert the item at the front
	public void addFirst(Item item) {
		if (item == null)
			throw new NullPointerException();
		DNode<Item> first = new DNode<Item>(item, header, header.next);
		header.next = first;
		first.next.previous = first;
		size++;

	}

	// insert the item at the end
	public void addLast(Item item) {
		if (item == null)
			throw new NullPointerException();

		DNode<Item> last = new DNode<Item>(item, trailer.previous, trailer);
		trailer.previous = last;
		last.previous.next = last;
		size++;

	}

	// delete and return the item at the front
	public Item removeFirst() {
		if (size == 0)
			throw new NoSuchElementException();

		DNode<Item> after = header.next.next;
		Item item = header.next.item;
		header.next = after;
		after.previous = header;
		size--;

		return item;
	}

	// delete and return the item at the end
	public Item removeLast() {
		if (size == 0)
			throw new NoSuchElementException();

		DNode<Item> before = trailer.previous.previous;
		Item item = trailer.previous.item;
		trailer.previous = before;
		before.next = trailer;
		size--;

		return item;
	}

	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new ListIterator();
	}

	// unit testing
	public static void main(String[] args) {
	}
}