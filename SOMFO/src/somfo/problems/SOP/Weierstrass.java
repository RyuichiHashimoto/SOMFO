package somfo.problems.SOP;

import java.util.HashMap;
import java.util.Map;

import somfo.core.Solution;
public class Weierstrass extends Function{


	@Override
	//this problem have no constrain.
	public Object repair(Solution d, Map<String, Object> a) {
		return null;
	}
	static int k_max_ = 20;
	static double a = 0.5;
	int    b = 3;

	public Weierstrass(HashMap a){
		numberOfVariables_ = (int) a.get("numberOfVariables");
		numberOfObjectives_ = 1;

		String rotation_filename = (String) a.get("rotationFile");
		rotationFileReading(rotation_filename);

		String Shift_filename = (String) a.get("ShiftFile");
		ShiftFileReading(Shift_filename);
	lowerLimit_ = new double[numberOfVariables_];
		upperLimit_ = new double[numberOfVariables_];

		for(int i = 0;i < numberOfVariables_;i++){
			lowerLimit_[i] = -0.5;
			upperLimit_[i] = 0.5;
		}


		problemName_  = "Weierstrass";
	}



	public double pow(double d,  int a){
		double s = 1;
		for(int i=0;i < a;i++){
			s = s*d;
		}
		return s;
	}


	@Override
	public double evaluate(Solution solution) {
		double[] x = decode(solution.getValue());

		double config = 0;
		double sum=0;
		double sum1 = 0;

//		numberOfVariables_ = ;

		for(int i = 0;i<numberOfVariables_;i++){
			for(int k = 0;k<=k_max_;k++){
				sum = sum + Math.pow(a, k)*Math.cos(2*Math.PI*Math.pow(b, k)*(x[i] + 0.5));
			}
		}

		for(int k = 0;k<=k_max_;k++){
			config = config + x.length* Math.pow(a, k)*Math.cos((0.5*2*Math.PI*Math.pow(b, k)));
			sum1 = sum1 + Math.pow(a, k)*Math.cos(0.5*2*Math.PI*Math.pow(b, k));
		}



		return sum - sum1*numberOfVariables_;
	}

}
