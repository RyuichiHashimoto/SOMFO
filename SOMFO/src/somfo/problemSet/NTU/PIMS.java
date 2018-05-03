package somfo.problemSet.NTU;

import java.util.HashMap;

import somfo.core.Problem;
import somfo.core.ProblemSet;
import somfo.problems.SOP.FunctionFactory;
import somfo.util.JMException;

public class PIMS extends ProblemSet {




	public PIMS() throws JMException{
		HashMap d = new HashMap();

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/PIMS/M1.dat");
		d.put("ShiftFile", "data/PIMS/O1.dat");

		Problem problem = FunctionFactory.getFunctionProblem("Ackley", d);
		add(problem);

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/PIMS/M2.dat");
		d.put("ShiftFile", "data/PIMS/O2.dat");
		problem = FunctionFactory.getFunctionProblem("Rosenbrock", d);
		add(problem);

		taskname_ = "PIMS";

	}
}
