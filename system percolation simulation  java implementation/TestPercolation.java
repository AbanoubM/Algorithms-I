/*----------------------------------------------------------------
 *  Author:        Abanoub Milad 
 * Email: abanoubcs@gmail.com
 *  Written:       1/10/2014
 *  Last updated:  3/10/2014
 *
 *  Compilation:   javac Percolation.java
 *  Execution:     Percolation Simulator
 *----------------------------------------------------------------*/
public class TestPercolation {
	private boolean[][] grid;
	private final int SIZE; // dimension of grid array
	private final int TOTAL_SIZE; // number of grid elements + start & end nodes
	private WeightedQuickUnionUF conn; // WeightedQuickUnionUF object

	// create N-by-N grid, with all sites blocked
	public TestPercolation(int N) {
		if (N <= 0) {
			throw new IndexOutOfBoundsException();
		} else {
			SIZE = N;
			TOTAL_SIZE = N * N + 2;
			grid = new boolean[N][N];

			conn = new WeightedQuickUnionUF(TOTAL_SIZE);
			for (int i = 0; i < SIZE; i++) {
				conn.union(0, i + 1);
				conn.union(TOTAL_SIZE - 1, TOTAL_SIZE - 2 - i);
			}

		}
	}

	// open site (row i, column j) if it is not already
	public void open(int i, int j) {
		if (i <= 0 || i > SIZE || j <= 0 || j > SIZE) {
			throw new IndexOutOfBoundsException();
		} else if (!grid[i - 1][j - 1]) {
			grid[i - 1][j - 1] = true;
			int temp = (i - 1) * SIZE + j;

			// checking neighbours

			// not in the first row
			if (i != 1 && grid[i - 2][j - 1])
				conn.union(temp, temp - SIZE);
			// not in last row
			if (i != SIZE && grid[i][j - 1])
				conn.union(temp, temp + SIZE);
			// not in the left column
			if (j != 1 && grid[i - 1][j - 2])
				conn.union(temp, temp - 1);
			// not in the right column
			if (j != SIZE && grid[i - 1][j])
				conn.union(temp, temp + 1);

		}
	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		if (i <= 0 || i > SIZE || j <= 0 || j > SIZE) {
			throw new IndexOutOfBoundsException();
		} else {
			return grid[i - 1][j - 1];
		}
	}

	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		if (i <= 0 || i > SIZE || j <= 0 || j > SIZE) {
			throw new IndexOutOfBoundsException();
		} else {
			return grid[i - 1][j - 1] && conn.connected(0, (i - 1) * SIZE + j);
		}
	}

	// does the system percolate?
	public boolean percolates() {
		if (SIZE == 1)
			return grid[0][0];
		return conn.connected(0, TOTAL_SIZE - 1);
	}

	// test client, optional
	public static void main(String[] args) {
	}

}