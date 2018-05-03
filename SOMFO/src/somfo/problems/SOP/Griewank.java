package somfo.problems.SOP;

import java.util.HashMap;
import java.util.Map;

import somfo.core.Solution;
public class Griewank extends Function{


	@Override
	//this problem have no constrain.
	public Object repair(Solution d, Map<String, Object> a) {
		return null;
	}

	public Griewank(HashMap a){
		numberOfVariables_ = (int) a.get("numberOfVariables");

		numberOfObjectives_ = 1;

		String rotation_filename = (String) a.get("rotationFile");
		rotationFileReading(rotation_filename);

		String Shift_filename = (String) a.get("ShiftFile");
		ShiftFileReading(Shift_filename);


		lowerLimit_ = new double[numberOfVariables_];
		upperLimit_ = new double[numberOfVariables_];

		for(int i = 0;i < numberOfVariables_;i++){
			lowerLimit_[i] = -100;
			upperLimit_[i] = 100;
		}


		problemName_  = "Griewank";
	}



	@Override
	public double evaluate(Solution solution) {
		double[] x = decode(solution.getValue());
		double sum = 0;

		double sum1=0,sum2=1;

		for(int i = 0 ; i < numberOfVariables_;i++){
			sum1 += x[i]*x[i];
			sum2 = sum2 * Math.cos(x[i]/Math.sqrt(i+1));
		}

		sum = 1 + sum1/4000 - sum2;

		return sum;
	}




}
