package somfo.metaheuristics.IslandModelSOEA;

import java.util.HashMap;
import java.util.Vector;

import somfo.core.Algorithm;
import somfo.core.Operator;
import somfo.core.Population;
import somfo.core.Problem;
import somfo.core.ProblemSet;
import somfo.core.Solution;
import somfo.util.JMException;
import somfo.util.Permutation;
import somfo.util.Random;
import somfo.util.fileSubscription;

public class Different_IslandModelSOEA extends Algorithm{
	public Different_IslandModelSOEA(ProblemSet problem) {
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

		int maxEvaluations = ((Integer) this.getInputParameter("maxEvaluations")).intValue();


		int maxGenerations = ((Integer) this.getInputParameter("maxGeneration")).intValue();

		everygeneration_   = ((Integer) this.getInputParameter("everygeneration")).intValue();

		amoutOftransfer_  = ((Integer) this.getInputParameter("amountoftransfer")).intValue();

		String directoryname = (String) this.getInputParameter("directoryname");


		populationSize_ = ((Integer) this.getInputParameter("populationSize")).intValue();
		evaluations_ = 0;
		numberOfmakeChild_ = 2;
		numberOfParents_= ((Integer) this.getInputParameter("numberOfParents")).intValue();

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

		for(int i = 0;i<problemSet_.size();i++){
			initPopulation(i);
		}
		generation=0;
		int numberOftransfer = maxGenerations/ everygeneration_;


		for(int i = 0;i < numberOftransfer; i++){
			for(int k = 0 ; k< problemSet_.size(); k ++){
				for(int j = 0 ; j< everygeneration_ ; j ++){
					makeOffspring(k);
					enviromnentalSelection(k);
					int a = everygeneration_ * i + j;
					best[k][a] = Math.log(population_[k].getMINFactorialCost(0));
					number[k][a] = population_[k].getNumberOfDifferentIndividuals();
				}
			}
			transfer_pop();


		}

		for(int i = 0; i< problemSet_.size();i++){
			fileSubscription.printToFile("result/FUN/"  + "DifferentIslandModelSOEA" + "/"+ directoryname   + "/" + name + "/" +"differentInd" +problemSet_.get(i).getName() + (time -1)+ ".dat", number[i], populationSize_);
			fileSubscription.printToFile("result/FUN/"  + "DifferentIslandModelSOEA" + "/"+ directoryname   + "/" + name + "/" + problemSet_.get(i).getName() + (time -1)+ ".dat", best[i], populationSize_);
		}

		return null;
	}

	public void transfer_pop( ){
		Population[] empty = new Population[problemSet_.size()];
		for(int key = 0; key<problemSet_.size();key++){
			population_[key].factorial_sort(0);
			empty[key] = new Population(population_[key]);
		}

		boolean dd;
		int [] randomPerm =  new int[populationSize_];
		for(int key = 0; key<problemSet_.size();key++){
			Population d = new Population();
			d.add(new Solution(empty[key].get(0)));

			for(int i = 1;(d.size() <  populationSize_ - amoutOftransfer_ )|| (i < population_[key].size());i++){
				Solution a;
				dd = true;
				for(int k = 0;k<d.size();k++){
					a = d.get(k);
					if(Solution.IsEqual(a, empty[key].get(i))){
						dd = false;
						break;
					}
				}
				if(dd){
					d.add(new Solution(empty[key].get(i)));
				}

			}
			for(int i = 0;i<populationSize_ - amoutOftransfer_ - d.size();i++){
				int kuruma =Random.nextIntIE(0,problemSet_.size());
				int keke = Random.nextIntIE(0, empty[kuruma].size());
				d.add(new Solution(empty[kuruma].get(keke)));
			}

			for(int j = 0; j < problemSet_.size();j++ ){
				if(j == key){
					continue;
				}

				for(int i = 0;i< amoutOftransfer_ / (problemSet_.size() - 1);i++){
					Permutation.randomPermutation(randomPerm);
					Solution a = new Solution (empty[j].get(randomPerm[i]));
					double sum = problemSet_.get(key).evaluate(a);
					a.setFactorialCost(0,sum);
					evaluations_++;
					d.add(a);
				}
			}
			population_[key] = new Population(d);
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
