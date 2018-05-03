package somfo.problems.SOP;


import java.util.HashMap;

import somfo.core.Problem;
import somfo.util.Configuration;
import somfo.util.JMException;


public class FunctionFactory {
	/**
	 * Gets a crossover operator through its name.
	 *
	 * @param name
	 *            of the operator
	 * @return the operator
	 * @throws JMException
	 */
	public static Problem getFunctionProblem(String name, HashMap parameters) throws JMException {

		if (name.equalsIgnoreCase("Sphere"))
			return new Sphere(parameters);
		else if (name.equalsIgnoreCase("Rastrigin")){
			return new Rastrigin(parameters);
		}else if (name.equalsIgnoreCase("Ackley")){
			return new Ackley(parameters);
		}  else if (name.equalsIgnoreCase("Rosenbrock")){
			return new Rosenbrock(parameters);
		}  else if (name.equalsIgnoreCase("Schwefel")){
			return new Schwefel(parameters);
		}  else if (name.equalsIgnoreCase("Griewank")){
			return new Griewank(parameters);
		}  else if (name.equalsIgnoreCase("Weierstrass")){
			return new Weierstrass(parameters);
		}  
		
		/*
		 * else if (name.equalsIgnoreCase("NonUniformMutation")) return new
		 * NonUniformMutation(parameters); else if
		 * (name.equalsIgnoreCase("SwapMutation")) return new
		 * SwapMutation(parameters);
		 */ else {
			Configuration.logger_.severe("Function Problem '" + name + "' not found ");
			Class cls = java.lang.String.class;
			String name2 = cls.getName();
			throw new JMException("Exception in " + name2 + ".getMutationOperator()");
		}
	}
}
