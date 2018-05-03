package somfo.util;

public class Sort {

	static int[] random_sort(int[] Sort){
		int size = Sort.length;
		int[] list = new int[size];
		for(int i=0;i<size;i++){
			list[i] = Sort[i];
		}
		for(int i = size - 1;i > 0; i--){
			int j = Random.nextIntIE(i+1);
			if (i != j ){
				int t = list[i];
				list[i]  = list[j];
				list[j]  = t;
			}
		}
		return list;
	}
	static double[] random_sort(double[] Sort){
		int size = Sort.length;

		double[] list = new double[size];
		for(int i=0;i<size;i++){
			list[i] = Sort[i];
		}
		for(int i = size - 1;i > 0; i--){
			int j = Random.nextIntIE(i+1);
			if (i != j ){
				double t = list[i];
				list[i]  = list[j];
				list[j]  = t;
			}
		}
		return list;
	}
	public static void main(String[] args){
		int []  a  = new int[5];
		Random.set_seed(1243);
		for(int i=0;i<5;i++){
			a[i] = i*7;
		}
		int [] d = random_sort(a);
		for(int i=0;i<5;i++){
		System.out.print(a[i] + "  ");
		}
		System.out.print( "\n"+ "ソート後" + "\n");

		for(int i=0;i<5;i++){
			System.out.print(d[i] + "  ");
		}


	}


}
