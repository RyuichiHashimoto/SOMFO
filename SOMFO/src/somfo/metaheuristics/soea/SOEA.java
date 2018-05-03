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

public class SOEA extends Algorithm{
	private int populationSize_;

	private Population population_;
	private Population offSpring_;

	int numberOfParents_;

	Problem problem_;

	int evaluations_;

	int generation_;

	int maxEvaluations_;

	int numberOfmakeChild_;
	double bestObj;
	Operator crossover_;

	Operator mutation_;
	double[] best;


	public SOEA(ProblemSet problem) {
		super(problem);
	}


	@Override
	public Population execute() throws JMException, ClassNotFoundException {
		problem_ = problemSet_.get(0);

		//int maxEvaluation = ((Integer) this.getInputParameter("maxEvaluations")).intValue();
		int maxGeneration = ((Integer) this.getInputParameter("maxGeneration")).intValue();;

		populationSize_ = ((Integer) this.getInputParameter("populationSize")).intValue();
		maxEvaluations_ = populationSize_ *maxGeneration;
		evaluations_ = 0;
		numberOfmakeChild_ = 2;
		numberOfParents_= 2;
		directoryName = ((String)this.getInputParameter("DirectoryName"));

		System.out.print(directoryName  + " ");
		int generation=0;
		HashMap parameters = new HashMap(); // Operator parameters

		int time = ((Integer) this.getInputParameter("times")).intValue();

		String taskName = problem_.getName();

		population_ = new Population(populationSize_);
		offSpring_ = new Population(populationSize_);


		crossover_ = operators_.get("crossover"); // default: SBX crossover
		mutation_ = operators_.get("mutation"); // default: polynomial mutation

		int[] permutation = new int[populationSize_];
		Permutation.randomPermutation(permutation,populationSize_);
		best  = new double[maxGeneration];
		//double[] number = new double[maxGeneration];
		//double[] worst = new double[maxGeneration];
		bestObj = Double.MAX_VALUE;
		initPopulation();
		generation=0;
		best[generation] = bestObj;
		do {
			generation++;
			makeOffspring();
			enviromnentalSelection();
			best[generation] = bestObj;
	//		worst[generation-1] =population_.getMAXFactorialCost();
	//		number[generation -1 ] = population_.getNumberOfDifferentIndividuals();
		} while((evaluations_ < maxEvaluations_)) ;
		population_.printVariablesToFile(directoryName+"/" +this.getInputParameter("tasknumber")+"/" +"BestVAR"+"/"+ taskName + (time )+ ".dat");
	 	fileSubscription.printToFile(directoryName+"/" +this.getInputParameter("tasknumber")+"/" +"BestFUN"+"/"+ taskName + (time )+ ".dat", best, 1);
	//	fileSubscription.printToFile(directoryName+"/"+this.getInputParameter("tasknumber")+"/"   +"BestFUN/"+ taskName + (time )+ ".dat", best,1);
	//	fileSubscription.printToFile(directoryName+"/" +this.getInputParameter("tasknumber")+"/" +  "WorstFUN/"+ taskName + (time )+ ".dat", worst,1);

	 	System.out.println();
	 	System.out.println(bestObj);
		return population_;
	}


	public void initPopulation() throws JMException, ClassNotFoundException {
 		for (int i = 0; i < populationSize_; i++) {
 			Solution newSolution = new Solution(problemSet_);
			double sum = problem_.evaluate(newSolution);
			newSolution.setFactorialCost(0, sum );
			evaluations_++;
			population_.add(new Solution( newSolution));
			if(bestObj > sum){
				bestObj = sum;
			}
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

			offSpring[0] = (Solution) mutation_.execute(offSpring[0]);
			double ret = problem_.evaluate(offSpring[0]);
			if(ret < bestObj){
				bestObj = ret;
			}

			evaluations_++;
			offSpring[0].setFactorialCost(0, ret );

			offSpring_.add(new Solution (offSpring[0])) ;
			if(ret < bestObj){
				bestObj = ret;
			}
			evaluations_++;

			offSpring[1] = (Solution) mutation_.execute(offSpring[1]);
			ret = problem_.evaluate(offSpring[1]);
			offSpring[1].setFactorialCost(0, ret );
			offSpring_.add(new Solution (offSpring[1])) ;

		}
	}



	public void enviromnentalSelection(){
		Population empty = new Population(populationSize_*2);
		int size = population_.size();

		empty.merge(new Population(population_));
		empty.merge(new Population(offSpring_));

		empty.factorial_sort(0);

		population_ = new Population(empty.cut(0,size -1 ));

	}




}
