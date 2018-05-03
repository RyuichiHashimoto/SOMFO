package somfo.operators.selection;

import java.util.HashMap;

import somfo.core.Population;
import somfo.core.Solution;
import somfo.util.Random;


public class RandomSelection extends Selection {
	public RandomSelection(HashMap<String, Object> parameters) {
		super(parameters);
    }

	/**
	 * Performs the operation
	 *
	 * @param object
	 *            Object representing a SolutionSet.
	 * @return an object representing an array with the selected parents
	 */
	public Object execute(Object object) {
		Population population = (Population) object;
		int pos1, pos2;
		pos1 = Random.nextIntII(0, population.size() - 1);
		pos2 = Random.nextIntII(0, population.size() - 1);
		while ((pos1 == pos2) && (population.size() > 1)) {
			pos2 = Random.nextIntII(0, population.size() - 1);
		}

		Solution[] parents = new Solution[2];
		parents[0] = population.get(pos1);
		parents[1] = population.get(pos2);

		return parents;
	} // Execute


}
