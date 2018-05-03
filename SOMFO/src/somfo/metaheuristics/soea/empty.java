package somfo.metaheuristics.soea;

import java.util.HashMap;

import somfo.core.Algorithm;
import somfo.core.Operator;
import somfo.core.Population;
import somfo.core.Problem;
import somfo.core.ProblemSet;
import somfo.core.Solution;
import somfo.util.JMException;
import somfo.util.Permutation;
import somfo.util.fileSubscription;

public class empty extends Algorithm{
	private int populationSize_;

	private Population population_;
	private Population offSpring_;

	int numberOfParents_;

	Problem problem_;

	int evaluations_;

	int generation_;

	int numberOfmakeChild_;

	Operator crossover_;

	Operator mutation_;


	public empty(ProblemSet problem) {
		super(problem);
	}


	@Override
	public Population execute() throws JMException, ClassNotFoundException {
		problem_ = problemSet_.get(0);
		String name = problemSet_.getProblemSetname();
		int maxEvaluations = ((Integer) this.getInputParameter("maxEvaluations")).intValue();
		int maxGenerations = ((Integer) this.getInputParameter("maxGeneration")).intValue();;

		populationSize_ = ((Integer) this.getInputParameter("populationSize")).intValue();
		evaluations_ = 0;
		numberOfmakeChild_ = 2;
		numberOfParents_= ((Integer) this.getInputParameter("numberOfParents")).intValue();

		System.out.print(name  + " ");

		int generation=0;

		HashMap parameters = new HashMap(); // Operator parameters

		int time = ((Integer) this.getInputParameter("times")).intValue();

		String taskName = problem_.getName();

		population_ = new Population();
		offSpring_ = new Population();

		crossover_ = operators_.get("crossover"); // default: DE crossover
		mutation_ = operators_.get("mutation"); // default: polynomial mutation

		int[] permutation = new int[populationSize_];
		Permutation.randomPermutation(permutation,populationSize_);
		double[] best = new double[maxGenerations];
		double[] number = new double[maxGenerations];
		double[] worst = new double[maxGenerations];

		initPopulation();
		generation=0;


		do {


			generation++;
			makeOffspring();
			enviromnentalSelection();
			best[generation-1] = Math.log(population_.getMINFactorialCost());
			worst[generation-1] = Math.log(population_.getMAXFactorialCost());

			number[generation -1 ] = population_.getNumberOfDifferentIndividuals();
		} while((generation < maxGenerations) && (evaluations_ < maxEvaluations)) ;

		fileSubscription.printToFile("result/FUN/" + "SOEA" + "/" + name + "/" +"differentind"+ taskName + (time -1)+ ".dat", number, populationSize_);

		fileSubscription.printToFile("result/FUN/" + "SOEA" + "/" + name + "/" +"best"+ taskName + (time -1)+ ".dat", best, populationSize_);
		fileSubscription.printToFile("result/FUN/" + "SOEA" + "/" + name + "/" +"worst"+ taskName + (time -1)+ ".dat", worst, populationSize_);

		System.out.print(population_.get(0).getNumberOfVariables());

		return population_;
	}


	public void initPopulation() throws JMException, ClassNotFoundException {
 		for (int i = 0; i < populationSize_; i++) {
 			Solution newSolution = new Solution(problemSet_);
			double sum = problem_.evaluate(newSolution);
			newSolution.setFactorialCost(0, sum );
			evaluations_++;
			population_.add(new Solution( newSolution));
		} // for
	}

	public void makeOffspring() throws JMException, ClassNotFoundException {
		int size = population_.size();
		int[] randomPermutation = new int[size];
		Permutation.randomPermutation(randomPermutation);
		Solution[] parents = new Solution[numberOfParents_];

		offSpring_.clear();

		for(int i = 0;i < size/numberOfmakeChild_ ;i ++){
			parents[0] = population_.get(randomPermutation[size/numberOfmakeChild_ +i]);
			parents[1] = population_.get(randomPermutation[i]);

			Solution[] offSpring;
			offSpring = (Solution[]) crossover_.execute(parents);

			for(int j = 0;j<numberOfmakeChild_;j++){
				offSpring[j] = (Solution) mutation_.execute(offSpring[j]);
				double ret = problem_.evaluate(offSpring[j]);
				offSpring[j].setFactorialCost(0, ret );
				evaluations_++;
				offSpring_.add(new Solution (offSpring[j])) ;
			}
		}
	}



	public void enviromnentalSelection(){
		Population empty = new Population(population_);
		int size = population_.size();


		empty.merge(offSpring_);

		empty.factorial_sort(0);

		population_ = new Population(empty.cut(0,size -1 ));
	}




}
