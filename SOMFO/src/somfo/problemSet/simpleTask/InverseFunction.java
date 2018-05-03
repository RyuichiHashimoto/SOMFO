package somfo.problemSet.simpleTask;

import java.util.HashMap;

import somfo.core.Problem;
import somfo.core.ProblemSet;
import somfo.problems.SOP.FunctionFactory;
import somfo.util.JMException;

public class InverseFunction extends ProblemSet {

	public InverseFunction(String functionName) throws JMException{
		HashMap d = new HashMap<String,Object>();

		d.put("numberOfVariables", 50);

		Problem problem = FunctionFactory.getFunctionProblem(functionName, d);

		add(problem);

		d.put("numberOfVariables", 50);

		problem = FunctionFactory.getFunctionProblem("Inverse"+functionName, d);

		taskname_ = "Inverse" + functionName;
		
		add(problem);
	}



}
