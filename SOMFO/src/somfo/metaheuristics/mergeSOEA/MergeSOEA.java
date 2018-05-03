package somfo.metaheuristics.mergeSOEA;

import java.util.HashMap;

import somfo.core.Algorithm;
import somfo.core.Operator;
import somfo.core.Population;
import somfo.core.ProblemSet;
import somfo.core.Solution;
import somfo.util.JMException;
import somfo.util.Permutation;
import somfo.util.fileSubscription;

public class MergeSOEA extends Algorithm{
	public MergeSOEA(ProblemSet problem) {
		super(problem);
		// TODO 自動生成されたコンストラクター・スタブ
	}



	private int populationSize_;

	private Population[] population_;

	private Population[] offSpring_;

	int numberOfParents_;


	int evaluations_;

	int generation_;

	int everygeneration_;

	int amoutOftransfer_;

	int numberOfmakeChild_;

	Operator crossover_;

	Operator mutation_;


	@Override
	public Population execute() throws JMException, ClassNotFoundException {

		population_  = new Population[problemSet_.size()];
		offSpring_  = new Population[problemSet_.size()];
		String name = problemSet_.getProblemSetname();

//		int maxEvaluations = ((Integer) this.getInputParameter("maxEvaluations")).intValue();

		int maxGenerations = ((Integer) this.getInputParameter("maxGeneration")).intValue();

		everygeneration_   = ((Integer) this.getInputParameter("everygeneration")).intValue();

		//amoutOftransfer_  = ((Integer) this.getInputParameter("amountoftransfer")).intValue();

		String directoryname = (String) this.getInputParameter("DirectoryName");


		populationSize_ = ((Integer) this.getInputParameter("populationSize")).intValue()/2;
		evaluations_ = 0;
		numberOfmakeChild_ = 2;
		numberOfParents_= 2;

		System.out.print(name  + " ");

		int generation=0;

		HashMap parameters = new HashMap(); // Operator parameters

		int time = ((Integer) this.getInputParameter("times")).intValue();



		crossover_ = operators_.get("crossover"); // default: DE crossover
		mutation_ = operators_.get("mutation"); // default: polynomial mutation

		int[] permutation = new int[populationSize_];
		Permutation.randomPermutation(permutation,populationSize_);
		double[][] best = new double[problemSet_.size()][maxGenerations];
		double[][] number = new double[problemSet_.size()][maxGenerations];
		double[][] worst = new double[problemSet_.size()][maxGenerations];

		for(int i = 0;i<problemSet_.size();i++){
			initPopulation(i);
		}
		generation=0;
		int numberOftransfer = maxGenerations/ everygeneration_;


		for(int i = 0;i < numberOftransfer; i++){
			if(i!= 0){
				transfer_pop();
			}
			for(int k = 0 ; k< problemSet_.size(); k ++){

				for(int j = 0 ; j< everygeneration_ ; j ++){
					makeOffspring(k);
					enviromnentalSelection(k);
					int a = everygeneration_ * i + j;

					best[k][a] = population_[k].getMINFactorialCost();
					worst[k][a] = population_[k].getMAXFactorialCost();
					number[k][a] = 100;
				}
			}
		}

		for(int i = 0; i< problemSet_.size();i++){
//			fileSubscription.printToFile(directoryname   +  "/Task"+(i+1)+ "/differentInd/" + problemSet_.get(i).getName() + (time -1)+ ".dat", number[i], populationSize_);
			fileSubscription.printToFile(directoryname   +  "/Task"+(i+1)+"/BestFUN/" + problemSet_.get(i).getName() + (time)+ ".dat", best[i], 1);
			fileSubscription.printToFile(directoryname   +  "/Task"+(i+1)+"/WorstFUN/" + problemSet_.get(i).getName() + (time)+ ".dat", worst[i], 1);
		}
		return null;
	}


	public void transfer_pop( ){
		Population[] ret = new Population[problemSet_.size()];

		for(int t = 0;t<problemSet_.size();t++){
			ret[t] = new Population();
			ret[t].merge(new Population(population_[0]));
			ret[t].merge(new Population(population_[1]));
			for(int p = 0; p <ret[t].size();p++){
				double val = problemSet_.get(t).evaluate(ret[t].get(p));
				ret[t].get(p).setFactorialCost(0, val);
			}
			ret[t].factorial_sort(0);
		}
		for(int t = 0;t<problemSet_.size();t++){
			population_[t].clear();
			for(int p = 0;p<populationSize_;p++){
				population_[t].add(ret[t].get(p));
			}
		}
	}

	public void initPopulation(int k) throws JMException, ClassNotFoundException {
		population_[k] = new Population();
 		offSpring_[k]  = new Population();
		for (int i = 0; i < populationSize_; i++) {
 			Solution newSolution = new Solution(problemSet_);
			double sum = problemSet_.get(k).evaluate(newSolution);
			newSolution.setFactorialCost(0, sum );
			evaluations_++;
			population_[k].add(new Solution( newSolution));
		} // for
	}

	public void makeOffspring(int k) throws JMException, ClassNotFoundException {
		int size = population_[k].size();
		int[] randomPermutation = new int[size];
		Permutation.randomPermutation(randomPermutation);
		Solution[] parents = new Solution[numberOfParents_];

		offSpring_[k].clear();

		for(int i = 0;i < size/numberOfmakeChild_ ;i ++){
			for(int j = 0 ;j < numberOfParents_; j ++){
				parents[j] = population_[k].get(randomPermutation[i*numberOfParents_+j]);
			}
			Solution[] offSpring;
			offSpring = (Solution[]) crossover_.execute(parents);

			for(int j = 0;j<numberOfmakeChild_;j++){
				offSpring[j] = (Solution) mutation_.execute(offSpring[j]);
				double ret = problemSet_.get(k).evaluate(offSpring[j]);
				offSpring[j].setFactorialCost(0, ret );
				evaluations_++;
				offSpring_[k].add(new Solution (offSpring[j])) ;
			}
		}
	}



	public void enviromnentalSelection(int k){
		Population empty = new Population(population_[k]);
		int size = population_[k].size();


		empty.merge(offSpring_[k]);

		empty.factorial_sort(0);

		population_[k] = new Population(empty.cut(0,size -1 ));
	}




}
