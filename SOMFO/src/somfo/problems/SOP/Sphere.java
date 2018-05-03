package somfo.problems.SOP;

import java.util.HashMap;
import java.util.Map;

import somfo.core.Solution;
public class Sphere extends Function{


	@Override
	//this problem have no constrain.
	public Object repair(Solution d, Map<String, Object> a) {
		return null;
	}

	public Sphere(HashMap a){

		numberOfVariables_ = (int) a.get("numberOfVariables");

		numberOfObjectives_ = 1;

		rotationFileReading((String) a.get("rotationFile"));
		ShiftFileReading((String) a.get("ShiftFile"));


		lowerLimit_ = new double[numberOfVariables_];
		upperLimit_ = new double[numberOfVariables_];

		for(int i = 0;i < numberOfVariables_;i++){
			lowerLimit_[i] = -100;
			upperLimit_[i] = 100;
		}


		problemName_  = "Sphere";
	}



	@Override
	public double evaluate(Solution solution) {
		double[] x = decode(solution.getValue());

		double sum = 0;

		for(int i = 0;i<numberOfVariables_;i++){
			sum += x[i]*x[i];
		}
		return sum;
	}




}
