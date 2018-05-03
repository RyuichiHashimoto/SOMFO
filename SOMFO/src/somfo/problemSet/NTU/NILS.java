package somfo.problemSet.NTU;

import java.util.HashMap;

import somfo.core.Problem;
import somfo.core.ProblemSet;
import somfo.problems.SOP.FunctionFactory;
import somfo.util.JMException;

public class NILS extends ProblemSet {




	public NILS() throws JMException{
		HashMap d = new HashMap();

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/NILS/M1.dat");
		d.put("ShiftFile", "data/NILS/O1.dat");

		Problem problem = FunctionFactory.getFunctionProblem("Rastrigin", d);
		add(problem);

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/NILS/M2.dat");
		d.put("ShiftFile", "data/NILS/O2.dat");
		problem = FunctionFactory.getFunctionProblem("Schwefel", d);
		add(problem); 
		taskname_ = "NILS";

	}
}
