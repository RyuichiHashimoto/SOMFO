package somfo.problemSet.simpleTask;

import java.util.HashMap;

import somfo.core.Problem;
import somfo.core.ProblemSet;
import somfo.problems.SOP.FunctionFactory;
import somfo.util.JMException;

public class DuplicatedFunction extends ProblemSet {

	public DuplicatedFunction(String functionName) throws JMException{
		HashMap d = new HashMap<String,Object>();

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/DuplicatedFunction/M1.dat");
		d.put("ShiftFile", "data/DuplicatedFunction/O1.dat");

		Problem problem = FunctionFactory.getFunctionProblem(functionName, d);

		add(problem);

		d.put("numberOfVariables", 50);
		d.put("rotationFile", "data/DuplicatedFunction/M2.dat");
		d.put("ShiftFile", "data/DuplicatedFunction/O2.dat");
		problem = FunctionFactory.getFunctionProblem(functionName, d);

		taskname_ = "Duplicated" + functionName;

		add(problem);
	}



}
