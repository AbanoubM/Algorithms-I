/*----------------------------------------------------------------
 *  Author:        Abanoub Milad 
 * Email: abanoubcs@gmail.com
 *  Written:       1/10/2014
 *  Last updated:  3/10/2014
 *
 *  Compilation:   javac Percolation.java
 *  Execution:     Percolation Simulator
 *----------------------------------------------------------------*/
public class Percolation {
	private byte[][] grid;
	private final int SIZE; // dimension of grid array
	private final int TOTAL_SIZE; // number of grid elements + start & end nodes
	private WeightedQuickUnionUF conn; // WeightedQuickUnionUF object

	// create N-by-N grid, with all sites blocked
	public Percolation(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException();
		} else {
			SIZE = N;
			TOTAL_SIZE = N * N + 2;
			grid = new byte[N][N];

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
		} else if (grid[i - 1][j - 1] == 0) {

			grid[i - 1][j - 1] = 1;

			int temp = (i - 1) * SIZE + j;

			// checking open neighbours to connect to

			// not in the first row
			if (i != 1 && grid[i - 2][j - 1] != 0)
				conn.union(temp, temp - SIZE);
			// not in last row
			if (i != SIZE && grid[i][j - 1] != 0)
				conn.union(temp, temp + SIZE);
			// not in the left column
			if (j != 1 && grid[i - 1][j - 2] != 0)
				conn.union(temp, temp - 1);
			// not in the right column
			if (j != SIZE && grid[i - 1][j] != 0)
				conn.union(temp, temp + 1);

			// checking if site full by its neighbours
			// if yes calls check pipe to tansfer full to neighbours
			if (i == 1 || grid[i - 2][j - 1] == 2
					|| (j != 1 && grid[i - 1][j - 2] == 2)
					|| (j != SIZE && grid[i - 1][j] == 2)
					|| (i != SIZE && grid[i][j-1] == 2)) {
				// grid[i - 1][j - 1] = 2;
				extendPipe(i - 1, j - 1);
			}
		}
	}

	// pipe a site to other full sites
	// this method is zero indexed!
	private void extendPipe(int i, int j) {
		// stoping case site is closed or its already piped
		// if (grid[i][j] != 1 )
		// return;

		// site is open then make it full "pipe it"
		grid[i][j] = 2;

		// check if any of neighbours is open to connect pipe to

		// not in last row
		if (i != 0 && grid[i - 1][j] == 1)
			extendPipe(i - 1, j);
		// not in last row
		if (i != SIZE - 1 && grid[i + 1][j] == 1)
			extendPipe(i + 1, j);
		// not in the left column
		if (j != 0 && grid[i][j - 1] == 1)
			extendPipe(i, j - 1);
		// not in the right column
		if (j != SIZE - 1 && grid[i][j + 1] == 1)
			extendPipe(i, j + 1);
	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		if (i <= 0 || i > SIZE || j <= 0 || j > SIZE) {
			throw new IndexOutOfBoundsException();
		} else {
			return grid[i - 1][j - 1] != 0;
		}
	}

	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		if (i <= 0 || i > SIZE || j <= 0 || j > SIZE) {
			throw new IndexOutOfBoundsException();
		} else {
			return grid[i - 1][j - 1] == 2;
		}
	}

	// does the system percolate?
	public boolean percolates() {
		if (SIZE == 1)
			return grid[0][0] != 0;
		return conn.connected(0, TOTAL_SIZE - 1);
	}

	// test client, optional
	public static void main(String[] args) {
	}

}