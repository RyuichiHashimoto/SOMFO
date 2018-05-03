package somfo.problemSet.NTU;

import java.util.HashMap;

import somfo.core.Problem;
import somfo.core.ProblemSet;
import somfo.problems.SOP.FunctionFactory;
import somfo.util.JMException;

public class CIHS extends ProblemSet {

	public CIHS() throws JMException{
		HashMap d = new HashMap<String,Object>();


		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/CIHS/M1.dat");
		d.put("ShiftFile", "data/CIHS/O1.dat");

		Problem problem = FunctionFactory.getFunctionProblem("Griewank", d);

		add(problem);

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/CIHS/M2.dat");
		d.put("ShiftFile", "data/CIHS/O2.dat");

		problem = FunctionFactory.getFunctionProblem("Rastrigin", d);

		taskname_ = "CIHS";

		add(problem);
	}



}
