package somfo.util;

public class Distance {

	public static double CalcDistance(double[] a, double[] b) {
		return CalcEucideanDistance(a, b);
	}

	public static double CalcEucideanDistance(double[] a, double[] b) {
		double sum = 0;
		for (int d = 0; d < a.length; d++) {
			sum += ((a[d] - b[d]) * (a[d] - b[d]));
		}
		return Math.sqrt(sum);
	}

	public static double CalcTchebycheffDistance(double[] a, double[] b) {
		return Double.MIN_VALUE;
	}

	public static double CalcDistance(int[] x, double[] y) {
		double sum = 0;
		for (int d = 0; d < x.length; d++) {
			sum += ((x[d] - y[d]) * (x[d] - y[d]));
		}
		return Math.sqrt(sum);
	}

	public static double CalcDistance(double[] x, int[] y) {
		return CalcDistance(y, x);
	}


	public static void main(String[] args) {

		final int SIZE = 2;
		double[] FirstArgment = new double[SIZE];
		double[] SecondArgment = new double[SIZE];

		for(int i = 0; i < SIZE; i++){
			FirstArgment[i] = 0;
			SecondArgment[i] = 1;
		}
		System.out.println(CalcDistance(FirstArgment,SecondArgment));
	}

	// public static double HammingDistance(double[] a, double[] b, int[]
	// division){

	// return 0;
	// }

}
