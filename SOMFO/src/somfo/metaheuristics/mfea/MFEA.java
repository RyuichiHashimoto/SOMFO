package somfo.metaheuristics.mfea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import somfo.core.Algorithm;
import somfo.core.Operator;
import somfo.core.Population;
import somfo.core.ProblemSet;
import somfo.core.Solution;
import somfo.util.JMException;
import somfo.util.Permutation;
import somfo.util.Random;
import somfo.util.fileSubscription;

public class MFEA extends Algorithm{
	private int populationSize_;

	private Population population_;
	private Population offSpring_;

	int numberOfParents_;


	double rmp_;

	int evaluations_;

	int generation_;

	int numberOfmakeChild_;

	Operator crossover_;

	Operator mutation_;

	double bestObj;

	public MFEA(ProblemSet problem) {
		super(problem);
	}


	@Override
	public Population execute() throws JMException, ClassNotFoundException {
//		int maxEvaluations = ((Integer) this.getInputParameter("maxEvaluations")).intValue();
		int maxGenerations = ((Integer) this.getInputParameter("maxGeneration")).intValue();
		rmp_ 			= (double)this.getInputParameter("rmp");
		populationSize_ = ((Integer) this.getInputParameter("populationSize")).intValue();
		evaluations_ = 0;
		numberOfmakeChild_ = 2;
		numberOfParents_= 2;// ((Integer) this.getInputParameter("numberOfParents")).intValue();

		double[][]	best = new double[problemSet_.size()][maxGenerations];
		String name = problemSet_.getProblemSetname();

		int generation=0;

		HashMap parameters = new HashMap(); // Operator parameters

		int time = ((Integer) this.getInputParameter("times")).intValue();
		String directoryname = (String) this.getInputParameter("directoryname");


		population_ = new Population(populationSize_);
		offSpring_ = new Population(populationSize_);

		crossover_ = operators_.get("crossover"); // default: DE crossover
		mutation_ = operators_.get("mutation"); // default: polynomial mutation

		int[] permutation = new int[populationSize_];
		double[][] number = new double[problemSet_.size()][maxGenerations];
		double[][] worst = new double[problemSet_.size()][maxGenerations];
		initPopulation();

		for(int j = 0;j < problemSet_.size();j++){
			best[j][generation] = population_.getMINFactorialCost(j);
			worst[j][generation] = population_.getMAXFactorialCost(j);
			number[j][generation] = 100;
		}

		Permutation.randomPermutation(permutation,populationSize_);
		do {
			generation++;
			makeOffspring();
			enviromnentalSelection();
			for(int j = 0;j < problemSet_.size();j++){
				best[j][generation-1] = population_.getMINFactorialCost(j);
				worst[j][generation-1] = population_.getMAXFactorialCost(j);
				number[j][generation-1] = 100;
			}

		} while((generation < maxGenerations) ) ;

		for(int i = 0; i< problemSet_.size();i++){
			String taskName = problemSet_.get(i).getName();
			fileSubscription.printToFile("result/" + "MFEA" + "/" + name + "/Task"+ (i+1) +"/Different/"+ taskName + (time)+ ".dat", number[i], populationSize_/problemSet_.size());
			fileSubscription.printToFile("result/" + "MFEA" + "/" + name + "/Task"+ (i+1)+"/BestFUN/" + taskName + (time )+ ".dat", best[i], populationSize_/problemSet_.size());
			fileSubscription.printToFile("result/" + "MFEA" + "/" + name + "/Task"+ (i+1)+"/WorstFUN/"+ taskName + (time )+ ".dat", worst[i], populationSize_/problemSet_.size());
			population_.printVariablesToFile("result/" + "MFEA" + "/" + name + "/Task"+ (i+1)+"/BESTVAR/"+ taskName + (time )+ ".dat");
		}
		System.out.println(best[0][maxGenerations-1]+"	"+ best[1][maxGenerations-1]);
		return population_;
	}


	public void initPopulation() throws JMException, ClassNotFoundException {
/* 		for (int i = 0; i < populationSize_; i++) {
 			Solution newSolution = new Solution(problemSet_);
 			newSolution.setSkillFactor(i %problemSet_.size());

 			evaluateWithSkillFactor(newSolution);
 			population_.add(new Solution( newSolution));
		}
*/
		 		for (int i = 0; i < populationSize_; i++) {
 			Solution newSolution = new Solution(problemSet_);
 			for (int j=0;j<problemSet_.size();j++ ){
 				double value = problemSet_.get(j).evaluate(newSolution);
 				newSolution.setFactorialCost(j, value);
 			}
			population_.add(newSolution);
		}
 		population_.setFactorialRank();
 		for(int p = 0; p < population_.size(); p++){
 			List<Integer> index = new ArrayList<Integer>();
 			Solution nowSol = population_.get(p);

 			index.add(0);
 			int rank = nowSol.getfactorialRank(0);
 			for (int t=1;t<problemSet_.size();t++ ){
 				if (rank > nowSol.getfactorialRank(t)){
 					index.clear();
 					index.add(t);
 					rank = nowSol.getfactorialRank(t);
 				} else if (rank == nowSol.getfactorialRank(t)){
 					index.add(t);
 				}
 			}


 			int BestTaskNumber = index.get(Random.nextIntIE(index.size()));
 			nowSol.setSkillFactor(BestTaskNumber);
 			for (int t=0;t<problemSet_.size();t++ ){
 				if(t == BestTaskNumber){

 					continue;
 				}
 				nowSol.setFactorialCost(t,Double.MAX_VALUE);

 			}
 		}


	}


	public void evaluateWithSkillFactor(Solution solution) throws JMException, ClassNotFoundException {
		int tasknumber = solution.getSkillFactor();
		for(int i = 0 ; i < problemSet_.size();i++){
			if(i == tasknumber){
				double sum = problemSet_.get(i).evaluate(solution);
				solution.setFactorialCost(i, sum);
			} else {
				solution.setFactorialCost(i, Double.MAX_VALUE);
			}
		}
	}

	public Solution[] makechildWithSkillFactor(Solution parent1, Solution parent2)throws JMException, ClassNotFoundException {

		Solution[] offspring = new  Solution[numberOfParents_];
		if(parent1.getSkillFactor() == parent2.getSkillFactor() || Random.nextDoubleIE() < rmp_){
			Solution[] Parents    = new Solution[numberOfParents_];
			Parents[0]	= parent1;
			Parents[1]	= parent2;

			offspring = (Solution[])crossover_.execute(Parents);

			if(Random.nextDoubleIE() < 0.5){
				offspring[0].setSkillFactor(Parents[0].getSkillFactor());
			} else {
				offspring[0].setSkillFactor(Parents[1].getSkillFactor());
			}

			if(Random.nextDoubleIE() < 0.5){
				offspring[1].setSkillFactor(Parents[0].getSkillFactor());
			} else {
				offspring[1].setSkillFactor(Parents[1].getSkillFactor());
			}
		} else {
			offspring[0]	=	new Solution (parent1);
			offspring[1]	=	new Solution (parent2);
			offspring[0].setSkillFactor(parent1.getSkillFactor());
			offspring[1].setSkillFactor(parent2.getSkillFactor());
		}
		mutation_.execute(offspring[0]);
		mutation_.execute(offspring[1]);

		return offspring;

	}

	public void makeOffspring() throws JMException, ClassNotFoundException {
		int size = population_.size();
		int[] randomPermutation = new int[size];
		Permutation.randomPermutation(randomPermutation);
		Solution[] parents = new Solution[numberOfParents_];
		offSpring_.clear();
		for(int i = 0;i < size/2 ;i ++){
			parents[0] = population_.get(randomPermutation[i]);
			parents[1] = population_.get(randomPermutation[size/2+i]);
			Solution[] offSpring;
			offSpring = makechildWithSkillFactor(parents[0],parents[1]);
			evaluateWithSkillFactor(offSpring[0]);
			evaluateWithSkillFactor(offSpring[1]);
			offSpring_.add((offSpring[0]));
			offSpring_.add((offSpring[1]));
		}
//		size = population_.size();
	}


	public void enviromnentalSelection(){
		Population empty = new Population(populationSize_*2);
		int size = population_.size();
		empty.merge(population_);
		empty.merge(offSpring_);
		empty.setFactorialRank();
		empty.setScalarFitness();
		empty.sortScalarFitness();
		//System.out.println();

		population_ = new Population(populationSize_);
		for(int p=0;p<size;p++){
			population_.add(empty.get(p));
		}

	}



}
