//  SBXCrossover.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package somfo.operators.crossover;

import java.util.HashMap;

import somfo.core.Solution;
import somfo.util.Configuration;
import somfo.util.JMException;
import somfo.util.Random;

/**
 * This class allows to apply a SBX crossover operator using two parent
 * solutions.
 */
public class AnotherSBCX extends Crossover {
	/**
	 * EPS defines the minimum difference allowed between real values
	 */
	private static final double EPS = 1.0e-14;

	private static final double ETA_C_DEFAULT_ = 20.0;
	private Double crossoverProbability_ = 0.9;
	private double distributionIndex_ = ETA_C_DEFAULT_;

	/**
	 * Valid solution types to apply this operator
	 */

	/**
	 * Constructor Create a new SBX crossover operator whit a default index
	 * given by <code>DEFAULT_INDEX_CROSSOVER</code>
	 */
	public AnotherSBCX(HashMap<String, Object> parameters) {
		super(parameters);

		crossoverProbability_ = (Double) parameters.get("CrossoverProbability");
		distributionIndex_ = (Double) parameters.get("CrossoverDistribution");
	} // SBXCrossover

	/**
	 * Perform the crossover operation.
	 *
	 * @param probability
	 *            Crossover probability
	 * @param parent1
	 *            The first parent
	 * @param parent2
	 *            The second parent
	 * @return An array containing the two offsprings
	 */
	public Solution[] doCrossover(double probability, Solution parent1, Solution parent2) throws JMException {

		Solution[] offSpring = new Solution[2];

		offSpring[0] = new Solution(parent1);
		offSpring[1] = new Solution(parent2);


		double a,b;
		int numberOfVariables = parent1.getNumberOfVariables();

        //交差するかどうか
		if (Random.nextDoubleIE() <= probability) {
			double [] u = new double[numberOfVariables];
			double[]  cf = new double[numberOfVariables];
			for(int i = 0;i <numberOfVariables;i++){
				u[i] = Random.nextDoubleII();
			}

			for(int i = 0;i <numberOfVariables;i++){
				if(u[i] < 0.5){
					cf[i] = Math.pow(2*u[i],1.0/(distributionIndex_ + 1.0));
				}else {
					cf[i] = Math.pow(2*(1-u[i]), -1.0/(distributionIndex_ + 1.0));
				}
			}

			for(int i = 0;i <numberOfVariables;i++){
				a = 0.5*((1 + cf[i]) *parent1.getValue(i) + (1-cf[i])*parent2.getValue(i));
				if(a > parent1.getUpperLimit(i))
					a = parent1.getUpperLimit(i);
				else if (a < parent1.getlowerLimit(i))
					a = parent1.getlowerLimit(i);

				offSpring[0].setValue(i,a);

				b = 0.5*((1 + cf[i]) *parent2.getValue(i) + (1-cf[i])*parent1.getValue(i));
				if(b > parent1.getUpperLimit(i))
					b = parent1.getUpperLimit(i);
				else if (b < parent1.getlowerLimit(i))
					b  = parent1.getlowerLimit(i);

				offSpring[1].setValue(i,b);
			}
		}

		return offSpring;
	} // doCrossover

	/**
	 * Executes the operation
	 *
	 * @param object
	 *            An object containing an array of two parents
	 * @return An object containing the offSprings
	 */
	public Object execute(Object object) throws JMException {
		Solution[] parents = (Solution[]) object;

		if (parents.length != 2) {
			Configuration.logger_.severe("SBXCrossover.execute: operator needs two " + "parents");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		} // if



		Solution[] offSpring;
		offSpring = doCrossover(crossoverProbability_, parents[0], parents[1]);

		// for (int i = 0; i < offSpring.length; i++)
		// {
		// offSpring[i].setCrowdingDistance(0.0);
		// offSpring[i].setRank(0);
		// }
		return offSpring;
	} // execute
} // SBXCrossover
