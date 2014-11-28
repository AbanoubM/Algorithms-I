/*************************************************************************
 * Name: Abanoub Milad
 * Email: abanoubcs@gmail.com
 * Description: brute force algorithm for detecting coliner points.
 */
import java.util.Arrays;

public class Brute {

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
		// Point[] toSort = new Point[4];
		double temp;
		Arrays.sort(points);
		for (int p1 = 0; p1 < points.length; p1++) {
			for (int p2 = p1 + 1; p2 < points.length; p2++) {
				for (int p3 = p2 + 1; p3 < points.length; p3++) {
					temp = points[p2].slopeTo(points[p3]);
					if (points[p1].slopeTo(points[p2]) == temp) {
						for (int p4 = p3 + 1; p4 < points.length; p4++) {
							if (temp == points[p3].slopeTo(points[p4])) {
								counter++;
								points[p1].drawTo(points[p4]);

								StdOut.println(points[p1].toString() + " -> "
										+ points[p2].toString() + " -> "
										+ points[p3].toString() + " -> "
										+ points[p4].toString());
							}
						}
					}
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
