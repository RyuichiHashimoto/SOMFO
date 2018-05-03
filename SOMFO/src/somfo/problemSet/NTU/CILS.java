package somfo.problemSet.NTU;

import java.util.HashMap;

import somfo.core.Problem;
import somfo.core.ProblemSet;
import somfo.problems.SOP.FunctionFactory;
import somfo.util.JMException;

public class CILS extends ProblemSet {




	public CILS() throws JMException{
		HashMap d = new HashMap();

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/CILS/M1.dat");
		d.put("ShiftFile", "data/CILS/O1.dat");

		Problem problem = FunctionFactory.getFunctionProblem("Ackley", d);
		add(problem);

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/CILS/M2.dat");
		d.put("ShiftFile", "data/CILS/O2.dat");
		problem = FunctionFactory.getFunctionProblem("Schwefel", d);
		add(problem);
		taskname_ = "CILS";

	}
}
