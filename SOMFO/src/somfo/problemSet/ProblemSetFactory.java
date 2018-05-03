package somfo.problemSet;


import somfo.core.ProblemSet;
import somfo.problemSet.NTU.CIHS;
import somfo.problemSet.NTU.CILS;
import somfo.problemSet.NTU.CIMS;
import somfo.problemSet.NTU.NIHS;
import somfo.problemSet.NTU.NILS;
import somfo.problemSet.NTU.NIMS;
import somfo.problemSet.NTU.PIHS;
import somfo.problemSet.NTU.PILS;
import somfo.problemSet.NTU.PIMS;
import somfo.problemSet.simpleTask.DuplicatedFunction;
import somfo.util.Configuration;
import somfo.util.JMException;


public class ProblemSetFactory{
	/**
	 * Gets a crossover operator through its name.
	 *
	 * @param name
	 *            of the operator
	 * @return the operator
	 * @throws JMException
	 */
	public static ProblemSet getProblemSet(String name) throws JMException {

		if (name.equalsIgnoreCase("CIHS"))
			return new CIHS();
		else if (name.equalsIgnoreCase("CIMS")){
			return new CIMS();
		}else if (name.equalsIgnoreCase("CILS")){
			return new CILS();
		}else if (name.equalsIgnoreCase("PIHS")){
			return new PIHS();
		}else if (name.equalsIgnoreCase("PIMS")){
			return new PIMS();
		}else if (name.equalsIgnoreCase("PILS")){
			return new PILS();
		}else if (name.equalsIgnoreCase("NIHS")){
			return new NIHS();
		}else if (name.equalsIgnoreCase("NIMS")){
			return new NIMS();
		}else if (name.equalsIgnoreCase("NILS")){
			return new NILS();
		} else if(name.startsWith("Duplicated")){
			return new DuplicatedFunction(name.split("Duplicated")[1]);
		}

	/*	else if (name.equalsIgnoreCase("NonUniformMutation"))
			return new NonUniformMutation(parameters);
		else if (name.equalsIgnoreCase("SwapMutation"))
			return new SwapMutation(parameters);
*/  else {
			Configuration.logger_.severe("Function Problem '" + name + "' not found ");
			Class cls = java.lang.String.class;
			String name2 = cls.getName();
			throw new JMException("Exception in " + name2 + ".getMutationOperator()");
		}
	}
}
