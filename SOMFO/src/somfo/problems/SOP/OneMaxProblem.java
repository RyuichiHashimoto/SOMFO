package somfo.problems.SOP;

import java.util.HashMap;
import java.util.Map;

import somfo.core.Problem;
import somfo.core.Solution;
import somfo.problems.SOP.Decoder.Decoder;
import somfo.problems.SOP.Decoder.IntDecoder;
import somfo.util.Distance;

public class OneMaxProblem extends Problem {

	private int SolutionIndex = 0;

	private int DecodeIndex = 0;

	private double[] OptimumPosition;

	Decoder Decoder;

	public void setOptimPosition(){
		
	};
	
	
	public OneMaxProblem(HashMap<String, Object> _param) {
		numberOfVariables_ = (int) _param.get("VariableSize");
		numberOfObjectives_ = 1;

		lowerLimit_ = new double[numberOfVariables_];
		upperLimit_ = new double[numberOfVariables_];
		OptimumPosition = (double[])_param.get("OptimumSolution");

		for (int i = 0; i < numberOfVariables_; i++) {
			lowerLimit_[i] = 0;
			upperLimit_[i] = 1;
		}
		problemName_ = "OneMaxProblem";
	}

	public void setDecoder(Decoder ArgDecoder) {
		Decoder = ArgDecoder;
	}

	@Override
	public Object repair(Solution _d, Map<String, Object> a) {
		return _d;
	}

	@Override
	public double evaluate(Solution _Solution) {
		double[] variables = _Solution.getValue().clone();

		int[] decodedVariables = (int[]) Decoder.decode(variables);

		return Distance.CalcDistance(decodedVariables, OptimumPosition);
	}

	public static void main(String[] args) {
		final int VariableSize = 10;
		final int[] division = new int[VariableSize];
		final double[] optimSolution = new double[VariableSize];
		Solution sol = new Solution(VariableSize);
		
		for (int var = 0; var < VariableSize; var++) {
			sol.setValue(var, 0.2);
			division[var] = 10;
			optimSolution[var] = 5;
		}
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("division",division);
		param.put("VariableSize", VariableSize);
		param.put("OptimumSolution", optimSolution);
		OneMaxProblem problem = new OneMaxProblem(param);
		problem.setDecoder(new IntDecoder(param));
		double fitness = problem.evaluate(sol);
		System.out.println("fitness	" + fitness);
	}

}
