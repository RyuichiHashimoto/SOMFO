package somfo.problemSet.NTU;

import java.util.HashMap;

import somfo.core.Problem;
import somfo.core.ProblemSet;
import somfo.problems.SOP.FunctionFactory;
import somfo.util.JMException;

public class NIMS extends ProblemSet {




	public NIMS() throws JMException{
		HashMap d = new HashMap();

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/NIMS/M1.dat");
		d.put("ShiftFile", "data/NIMS/O1.dat");

		Problem problem = FunctionFactory.getFunctionProblem("Griewank", d);
		add(problem);

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/NIMS/M2.dat");
		d.put("ShiftFile", "data/NIMS/O2.dat");
		problem = FunctionFactory.getFunctionProblem("Weierstrass", d);
		add(problem);
		taskname_ = "NIMS";

	}
}
