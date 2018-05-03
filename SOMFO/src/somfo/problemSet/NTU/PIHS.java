package somfo.problemSet.NTU;

import java.util.HashMap;

import somfo.core.Problem;
import somfo.core.ProblemSet;
import somfo.problems.SOP.FunctionFactory;
import somfo.util.JMException;

public class PIHS extends ProblemSet {




	public PIHS() throws JMException{
		HashMap d = new HashMap();

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/PIHS/M1.dat");
		d.put("ShiftFile", "data/PIHS/O1.dat");

		Problem problem = FunctionFactory.getFunctionProblem("Rastrigin", d);
		add(problem);

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/PIHS/M2.dat");
		d.put("ShiftFile", "data/PIHS/O2.dat");
		problem = FunctionFactory.getFunctionProblem("Sphere", d);
		add(problem);
		taskname_ = "PIHS";

	}
}
