package somfo.metaheuristics.ReEvaluate_reverse_Transfer;

import java.util.HashMap;

import somfo.core.Algorithm;
import somfo.core.Operator;
import somfo.core.Population;
import somfo.core.ProblemSet;
import somfo.core.Solution;
import somfo.util.JMException;
import somfo.util.Permutation;
import somfo.util.fileSubscription;

public class ReEvaluate_Reverse_TransferSOEA extends Algorithm{

	public ReEvaluate_Reverse_TransferSOEA(ProblemSet problem) {
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

	int[] evaluation;
	int[] MaxEvaluation;
	double[][] best;
	double[] BestObj;


	@Override
	public Population execute() throws JMException, ClassNotFoundException {

		population_  = new Population[problemSet_.size()];
		offSpring_  = new Population[problemSet_.size()];
		String name = problemSet_.getProblemSetname();

//		int maxEvaluations = ((Integer) this.getInputParameter("maxEvaluations")).intValue();


		int maxGenerations = ((Integer) this.getInputParameter("maxGeneration")).intValue();

		everygeneration_   = ((Integer) this.getInputParameter("everygeneration")).intValue();

		amoutOftransfer_  = ((Integer) this.getInputParameter("amountoftransfer")).intValue();

		String directoryname = (String) this.getInputParameter("DirectoryName");

		populationSize_ = ((Integer) this.getInputParameter("populationSize")).intValue()/2;
		evaluations_ = 0;
		numberOfmakeChild_ = 2;
		numberOfParents_= 2;

		int maxEvaluation = populationSize_ * maxGenerations;

		System.out.print(name  + " ");

		int generation=0;

		HashMap parameters = new HashMap(); // Operator parameters

		int time = ((Integer) this.getInputParameter("times")).intValue();

		crossover_ = operators_.get("crossover"); // default: DE crossover
		mutation_ = operators_.get("mutation"); // default: polynomial mutation

		int evaluations = 0;
		MaxEvaluation = new int [problemSet_.size()];



		best =new double[problemSet_.size()][];
		BestObj = new double[problemSet_.size()];
		evaluation = new int[problemSet_.size()];
		for(int i = 0;i<problemSet_.size();i++){
			MaxEvaluation[i] = populationSize_*maxGenerations;
			best[i] = new double[MaxEvaluation[i]];
			evaluation[i] = 0;
			BestObj[i] = Double.MAX_VALUE;
			initPopulation(i);
		}

		int counter = 0;
		boolean first = true;
		do {


			for(int t = problemSet_.size()-1; t >= 0;t--){
		//		System.out.println("Task" + (t+1) +  "	"+evaluation[t]);
				if (first){
					first = false;
				} else {
						transfer_pop(t);
				}
				for(int e = 0; e < everygeneration_;e++){
					if ((MaxEvaluation[t] == evaluation[t])){
						break;
					}
					makeOffspring(t);
					enviromnentalSelection(t);
					int[] permutation = new int[populationSize_];
					Permutation.randomPermutation(permutation,populationSize_);
				}
			}
			counter = 0;
			for(int t = 0; t < problemSet_.size();t++){
				if ((MaxEvaluation[t] == evaluation[t])){
					counter++;
				}
			}

		} while(counter != problemSet_.size());

		for(int i = 0; i< problemSet_.size();i++){
//			fileSubscription.printToFile(directoryname   +  "/Task"+(i+1)+ "/differentInd/" + problemSet_.get(i).getName() + (time -1)+ ".dat", number[i], populationSize_);
			fileSubscription.printToFile(directoryname   +  "/Task"+(i+1)+"/BestFUN/" + problemSet_.get(i).getName() + (time)+ ".dat", best[i], 1);
			population_[i].printVariablesToFile(directoryname+ "/Task"+ (i+1)+"/BestVAR/"+ problemSet_.get(i).getName() + (time)+ ".dat");

		}
		System.out.println(BestObj[0] + "	" + BestObj[1]);

		return null;
	}

	public void transfer_pop( int key){
		population_[key].factorial_sort(0);
		Population d = new Population(populationSize_);
		int [] randomPerm =  new int[populationSize_];
		Permutation.randomPermutation(randomPerm);
		for(int i = 0;i<populationSize_ - amoutOftransfer_;i++){
			d.add(new Solution(population_[key].get(i)));
		}
		for(int j = 0; j < problemSet_.size();j++ ){
			if(j == key){
				continue;
			}

			for(int i = 0;i< amoutOftransfer_ / (problemSet_.size() - 1);i++){
				Permutation.randomPermutation(randomPerm);
				Solution a = new Solution (population_[j].get(randomPerm[i]));
				double sum = problemSet_.get(key).evaluate(a);
				a.setFactorialCost(0,sum);
				evaluations_++;
				d.add(a);
			}
		}
		population_[key] = d;
	}

	public void initPopulation(int k) throws JMException, ClassNotFoundException {
		population_[k] = new Population(populationSize_);
 		offSpring_[k]  = new Population(populationSize_);
		for (int i = 0; i < populationSize_; i++) {
 			Solution newSolution = new Solution(problemSet_);
			double sum = problemSet_.get(k).evaluate(newSolution);
			if (BestObj[k] > sum){
				BestObj[k] = sum;
			}
			evaluation[k]++;
			best[k][evaluation[k]-1] = BestObj[k];

			newSolution.setFactorialCost(0, sum );
			evaluations_++;
			population_[k].add(new Solution( newSolution));
		} // for
		for(int p =0;p<populationSize_;p++){
			best[k][p] = BestObj[k];
		}
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
				evaluation[k]++;
				if (ret < BestObj[k]){
					BestObj[k] = ret;
				}
				best[k][evaluation[k]-1] = BestObj[k];

				offSpring[j].setFactorialCost(0, ret );
				offSpring_[k].add(new Solution (offSpring[j])) ;
			}
		}
	}



	public void enviromnentalSelection(int k){
		Population empty = new Population(populationSize_*2);
		int size = population_[k].size();

		empty.merge(population_[k]);
		empty.merge(offSpring_[k]);

		empty.factorial_sort(0);

		population_[k] = new Population(empty.cut(0,size -1 ));
	}




}
