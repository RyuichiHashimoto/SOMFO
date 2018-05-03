package somfo.operators.mutation;

import java.util.HashMap;

import somfo.core.Solution;
import somfo.util.JMException;
import somfo.util.Random;


public class PolynomialMutation extends Mutation {
	private static final double ETA_M_DEFAULT_ = 100000000;
	private final double eta_m_ = ETA_M_DEFAULT_;

	private Double mutationProbability_ = null;
	private Double distributionIndex_ = eta_m_;

	/**
	 * Valid solution types to apply this operator
	 */

	/**
	 * Constructor Creates a new instance of the polynomial mutation operator
	 */
	public PolynomialMutation(HashMap<String, Object> parameters) {
		super(parameters);
		mutationProbability_ = (Double) parameters.get("MutationProbability");
		distributionIndex_ = (Double) parameters.get("MutationDistribution");
	} // PolynomialMutation

	/**
	 * Perform the mutation operation
	 *
	 * @param probability
	 *            Mutation probability
	 * @param solution
	 *            The solution to mutate
	 * @throws JMException
	 *
	 */

	/*
    rnvec_temp=p.rnvec;
    for i=1:dim
        if rand(1)<1/dim
            u=rand(1);
            if u <= 0.5
                del=(2*u)^(1/(1+mum)) - 1;
                rnvec_temp(i)=p.rnvec(i) + del*(p.rnvec(i));

            else
                del= 1 - (2*(1-u))^(1/(1+mum));
                rnvec_temp(i)=p.rnvec(i) + del*(1-p.rnvec(i));
            end
        end
    end
    object.rnvec = rnvec_temp;
            */
	public void doMutation(double probability, Solution solution) throws JMException {
			for (int var=0; var < solution.getNumberOfVariables(); var++) {
				if (Random.nextDoubleII() <= probability){
					double u =Random.nextDoubleIE();
					double del = -100000;
					double y = 0;

			        if (u <= 0.5){
                        del=Math.pow((2.0*u),(1.0/(1.0+distributionIndex_))) - 1.0;
                        y = solution.getValue(var) + del*(solution.getValue(var));
			        }   else{
			        	del= 1.0 - Math.pow((2.0*(1.0-u)),(1.0/(1.0+distributionIndex_)));
			        	y = solution.getValue(var) + del*(1.0 - solution.getValue(var));
			        }
			        if(del == -100000){
			        	assert false : "test";
			        }

					solution.setValue(var, y);
			}
		} // for
	} // doMutation

	/**
	 * Executes the operation
	 *
	 * @param object
	 *            An object containing a solution
	 * @return An object containing the mutated solution
	 * @throws JMException
	 */
	public Object execute(Object object) throws JMException {
		Solution solution = (Solution) object;
		doMutation(mutationProbability_, solution);

		return solution;
	} // execute

} // PolynomialMutation
