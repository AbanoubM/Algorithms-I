/*----------------------------------------------------------------
 *  Author:        Abanoub Milad 
 * Email: abanoubcs@gmail.com
 *  Written:       1/10/2014
 *  Last updated:  3/10/2014
 *
 *  Compilation:   javac PercolationStats.java
 *  Execution:    Percolation Statistics 
 *----------------------------------------------------------------*/
public class PercolationStats {
	private double[] results; // holds percolation ratios
	private final int SIZE; // one dimension of percolation array
	private final int NUM_SITES; // total number of sites

	// perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		if (T <= 0) {
			throw new IllegalArgumentException();
		} else {
			Percolation per; // percolation simulator object
			results = new double[T];
			SIZE = N;
			NUM_SITES = N * N;
			int temp1, temp2, counter;
			for (int i = 0; i < T; i++) {
				per = new Percolation(N);

				counter = 0;
				while (counter < N) {
					temp1 = StdRandom.uniform(N) + 1;
					temp2 = StdRandom.uniform(N) + 1;
					if (!per.isOpen(temp1, temp2)) {
						per.open(temp1, temp2);
						counter++;
					}
				}
				while (!per.percolates()) {
					temp1 = StdRandom.uniform(N) + 1;
					temp2 = StdRandom.uniform(N) + 1;
					while (per.isOpen(temp1, temp2)) {
						temp1 = StdRandom.uniform(N) + 1;
						temp2 = StdRandom.uniform(N) + 1;
					}
					per.open(temp1, temp2);
				}

				results[i] = getOpenCount(per) / (double) NUM_SITES;
			}
		}
	}

	// count number of open sites in percolation object
	private int getOpenCount(Percolation per) {
		int counter = 0;
		for (int i = 1; i <= SIZE; i++) {
			for (int j = 1; j <= SIZE; j++) {
				if (per.isOpen(i, j))
					counter++;
			}
		}
		return counter;
	}
	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(results);
	};

	// sample standard deviation of percolation
	// threshold
	public double stddev() {
		return StdStats.stddev(results);
	};

	// returns lower bound of the 95% confidence interval
	public double confidenceLo() {
		return mean() - 1.96 * stddev() / Math.sqrt(results.length);
	};

	// returns upper bound of the 95% confidence interval
	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(results.length);
	};

	// test client, described below
	public static void main(String[] args) {
		PercolationStats obj=new PercolationStats(Integer.parseInt(args[0]),
				Integer.parseInt(args[1]));

		StdOut.printf("mean = " + obj.mean());
		StdOut.println();
		StdOut.printf("stddev = " + obj.stddev());
		StdOut.println();
		StdOut.printf("95%% confidence interval = " + obj.confidenceLo() + ", "
				+ obj.confidenceHi());
	}
}