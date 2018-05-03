package somfo.problemSet.Binary;

import java.util.HashMap;

import somfo.core.Problem;
import somfo.core.ProblemSet;
import somfo.problems.SOP.FunctionFactory;
import somfo.problems.SOP.Decoder.Decoder;
import somfo.problems.SOP.Decoder.IntDecoder;
import somfo.util.JMException;

public class Binary_One extends ProblemSet {

	public Binary_One() throws JMException{
		HashMap param = new HashMap<String,Object>();

		Decoder decoder = new IntDecoder(param);

		Problem problem = FunctionFactory.getFunctionProblem("Griewank", null);
		add(problem);

//		problem = FunctionFactory.getFunctionProblem("Rastrigin", d);

		taskname_ = "CIHS";

		add(problem);
	}

}
