package somfo.problemSet.NTU;

import java.util.HashMap;

import somfo.core.Problem;
import somfo.core.ProblemSet;
import somfo.problems.SOP.FunctionFactory;
import somfo.util.JMException;

public class NIHS extends ProblemSet {




	public NIHS() throws JMException{
		HashMap d = new HashMap();

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/NIHS/M1.dat");
		d.put("ShiftFile", "data/NIHS/O1.dat");

		Problem problem = FunctionFactory.getFunctionProblem("Rosenbrock", d);
		add(problem);

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/NIHS/M2.dat");
		d.put("ShiftFile", "data/NIHS/O2.dat");
		problem = FunctionFactory.getFunctionProblem("Rastrigin", d);
		add(problem);
		taskname_ = "NIHS";
	}
}
