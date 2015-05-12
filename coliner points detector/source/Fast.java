/*************************************************************************
 * Name: Abanoub Milad
 * Email: abanoubcs@gmail.com
 * Description: optimized algorithm for detecting coliner points.
 */
import java.util.Arrays;

public class Fast {

	public static void main(String[] args) {

		// rescale coordinates and turn on animation mode
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);
		StdDraw.setPenRadius(0.01); // make the points a bit large

		String filename = args[0];
		In in = new In(filename);

		int N = in.readInt();
		Point[] points = new Point[N];

		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
			points[i].draw();
		}

		int counter = 0;
		boolean flag;
		double last=Double.NEGATIVE_INFINITY;
		Arrays.sort(points);
		Point[] pointsSLope = points.clone();

		for (int p = 0; p < N - 3; p++) {

			Arrays.sort(pointsSLope, points[p].SLOPE_ORDER);
			flag = false;

			for (int q = 1; q < pointsSLope.length - 2; q++) {

				// if (p == 2) {
				// System.out.println("the list is ");
				// for (int i = 0; i < pointsSLope.length; i++) {
				// System.out.print(pointsSLope[i].toString()+ " ");
				// }
				// System.out.println();
				//
				// System.out.println("p = " + points[p].toString());
				// System.out.println("q = " + pointsSLope[q].toString());
				// System.out.println("p.compare to q = "
				// + points[p].compareTo(pointsSLope[q]));
				//
				// }

				if (points[p].compareTo(pointsSLope[q]) < 0
						&& (!flag || last != points[p].slopeTo(pointsSLope[q]))) {
					flag = false;

					double temp = points[p].slopeTo(pointsSLope[q]);
					int count, itr;
					flag = false;
					for (itr = q + 1, count = 2; itr < pointsSLope.length; itr++) {
						// if (p == 2) {
						// System.out.println("p = " + points[p].toString());
						// System.out.println("qitr = "
						// + pointsSLope[itr].toString());
						// System.out.println("p.compare to q = "
						// + points[p].compareTo(pointsSLope[itr]));
						//
						// }
						if (temp == points[p].slopeTo(pointsSLope[itr])) {
							if (points[p].compareTo(pointsSLope[itr]) > 0) {
								flag = true;
								break;
							} else
								count++;
						} else
							break;
					}
					if (count > 3 && !flag) {
						Arrays.sort(pointsSLope, q, itr);
						points[p].drawTo(pointsSLope[itr - 1]);
						counter++;

						String result = points[p].toString() + " -> ";
						for (int i = 0; i < count - 2; i++) {
							result += pointsSLope[q + i].toString() + " -> ";
						}
						result += pointsSLope[itr - 1].toString();

						StdOut.println(result);
					}

					q = itr - 1;
				} else {
					flag = true;
					last = points[p].slopeTo(pointsSLope[q]);
				}
			}
		}
		StdOut.println(counter);

		// display to screen all at once
		StdDraw.show(0);

		// reset the pen radius
		StdDraw.setPenRadius();
	}

}
