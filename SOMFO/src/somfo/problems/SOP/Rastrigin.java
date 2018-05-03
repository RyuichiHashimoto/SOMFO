package somfo.problems.SOP;

import java.util.HashMap;
import java.util.Map;

import somfo.core.Solution;
public class Rastrigin extends Function{


	@Override
	//this problem have no constrain.
	public Object repair(Solution d, Map<String, Object> a) {
		return null;
	}

	public Rastrigin(HashMap a){
		numberOfVariables_ = (int) a.get("numberOfVariables");
		numberOfObjectives_ = 1;

		String rotation_filename = (String) a.get("rotationFile");
		rotationFileReading(rotation_filename);

		String Shift_filename = (String) a.get("ShiftFile");
		ShiftFileReading(Shift_filename);

		lowerLimit_ = new double[numberOfVariables_];
		upperLimit_ = new double[numberOfVariables_];

		for(int i = 0;i < numberOfVariables_;i++){
			lowerLimit_[i] = -50;
			upperLimit_[i] = 50;

		}


		problemName_  = "Rastrigin";
	}



	@Override
	public double evaluate(Solution solution) {
		double[] x = decode(solution.getValue());

		double sum = 0;

		for(int i = 0;i<numberOfVariables_;i++){
			sum += (x[i]*x[i] - 10*Math.cos(2*Math.PI*x[i]) + 10);
		}
		return sum;
	}




}
