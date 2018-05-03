package somfo.problemSet.NTU;

import java.util.HashMap;

import somfo.core.Problem;
import somfo.core.ProblemSet;
import somfo.problems.SOP.FunctionFactory;
import somfo.util.JMException;

public class CIMS extends ProblemSet {




	public CIMS() throws JMException{
		HashMap d = new HashMap();

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/CIMS/M1.dat");
		d.put("ShiftFile", "data/CIMS/O1.dat");

		Problem problem = FunctionFactory.getFunctionProblem("Ackley", d);
		add(problem);

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/CIMS/M2.dat");
		d.put("ShiftFile", "data/CIMS/O2.dat");

		problem = FunctionFactory.getFunctionProblem("Rastrigin", d);

		taskname_ = "CIMS";

		add(problem);
	}
}
