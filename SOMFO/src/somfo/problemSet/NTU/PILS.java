package somfo.problemSet.NTU;

import java.util.HashMap;

import somfo.core.Problem;
import somfo.core.ProblemSet;
import somfo.problems.SOP.FunctionFactory;
import somfo.util.JMException;

public class PILS extends ProblemSet {




	public PILS() throws JMException{
		HashMap d = new HashMap();

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/PILS/M1.dat");
		d.put("ShiftFile", "data/PILS/O1.dat");

		Problem problem = FunctionFactory.getFunctionProblem("Ackley", d);
		add(problem);

		d.put("numberOfVariables", 25);
		d.put("rotationFile", "data/PILS/M2.dat");
		d.put("ShiftFile", "data/PILS/O2.dat");
		problem = FunctionFactory.getFunctionProblem("Weierstrass", d);
		add(problem);
		taskname_ = "PILS";

	}
}
