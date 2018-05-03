package somfo.problems.SOP;

import java.util.HashMap;
import java.util.Map;

import somfo.core.Solution;
public class Ackley extends Function{


	@Override
	//this problem have no constrain.
	public Object repair(Solution d, Map<String, Object> a) {
		return null;
	}

	public Ackley(HashMap a){
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


		problemName_  = "Ackley";
	}



	@Override
	public double evaluate(Solution solution) {
		double[] x = decode(solution.getValue());

		double sum = 0;

		double sum1=0,sum2=0;

		for(int i= 0; i < numberOfVariables_;i++){
			sum1 += x[i]*x[i];
			sum2 += Math.cos(Math.PI*2*x[i]);
		}
		sum1 = sum1/numberOfVariables_;
		sum2 = sum2/numberOfVariables_;
		sum1 = Math.sqrt(sum1);

		sum = -20*Math.exp(-0.2*sum1) -Math.exp(sum2) + 20 + Math.exp(1);


		return sum;
	}




}
