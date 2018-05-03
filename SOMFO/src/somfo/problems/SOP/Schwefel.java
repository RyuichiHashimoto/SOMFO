package somfo.problems.SOP;

import java.util.HashMap;
import java.util.Map;

import somfo.core.Problem;
import somfo.core.Solution;
import somfo.util.MATRIX;
public class Schwefel extends Function{


	@Override
	//this problem have no constrain.
	public Object repair(Solution d, Map<String, Object> a) {
		return null;
	}

	public Schwefel(HashMap a){
		numberOfVariables_ = (int) a.get("numberOfVariables");

		numberOfObjectives_ = 1;

		String rotation_filename = (String) a.get("rotationFile");
		rotationFileReading(rotation_filename);

		String Shift_filename = (String) a.get("ShiftFile");
		ShiftFileReading(Shift_filename);


		lowerLimit_ = new double[numberOfVariables_];
		upperLimit_ = new double[numberOfVariables_];

		for(int i = 0;i < numberOfVariables_;i++){
			lowerLimit_[i] = -500;
			upperLimit_[i] = 500;
		}


		problemName_  = "Schwefel";
	}



	@Override
	public double evaluate(Solution solution) {
		double[] x = decode(solution.getValue());

		double sum = 418.9829*numberOfVariables_;

		double sum2 = 0;

		for(int i = 0;i<numberOfVariables_;i++){
			sum2 += x[i]*Math.sin(Math.sqrt(Math.abs(x[i])));
		}

		sum = sum - sum2;
		return sum;
	}




}
