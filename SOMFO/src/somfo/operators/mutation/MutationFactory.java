package somfo.operators.mutation;


import java.util.HashMap;

import somfo.util.Configuration;
import somfo.util.JMException;


public class MutationFactory {
	/**
	 * Gets a crossover operator through its name.
	 *
	 * @param name
	 *            of the operator
	 * @return the operator
	 * @throws JMException
	 */
	public static Mutation getMutationOperator(String name, HashMap parameters) throws JMException {

		if (name.equalsIgnoreCase("PolynomialMutation"))
			return new PolynomialMutation(parameters);
		else if (name.equalsIgnoreCase("BitFlipMutation")){
			return new BitFlipMutation(parameters);
		}
	/*	else if (name.equalsIgnoreCase("NonUniformMutation"))
			return new NonUniformMutation(parameters);
		else if (name.equalsIgnoreCase("SwapMutation"))
			return new SwapMutation(parameters);
*/  else {
			Configuration.logger_.severe("Operator '" + name + "' not found ");
			Class cls = java.lang.String.class;
			String name2 = cls.getName();
			throw new JMException("Exception in " + name2 + ".getMutationOperator()");
		}
	}
}
