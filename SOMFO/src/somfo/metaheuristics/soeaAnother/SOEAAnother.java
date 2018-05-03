package somfo.metaheuristics.soeaAnother;

import java.util.HashMap;

import somfo.core.Algorithm;
import somfo.core.Operator;
import somfo.core.Population;
import somfo.core.ProblemSet;
import somfo.core.Solution;
import somfo.util.JMException;
import somfo.util.Permutation;
import somfo.util.Random;
import somfo.util.fileSubscription;

public class SOEAAnother extends Algorithm{
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


	public SOEAAnother(ProblemSet problem) {
		super(problem);
	}


	@Override
	public Population execute() throws JMException, ClassNotFoundException {
		int maxEvaluations = ((Integer) this.getInputParameter("maxEvaluations")).intValue();
		int maxGenerations = ((Integer) this.getInputParameter("maxGeneration")).intValue();
		rmp_ 			= (double)this.getInputParameter("rmp");
		populationSize_ = ((Integer) this.getInputParameter("populationSize")).intValue();
		evaluations_ = 0;
		numberOfmakeChild_ = 2;
		numberOfParents_= ((Integer) this.getInputParameter("numberOfParents")).intValue();

		double[][]	best = new double[problemSet_.size()][maxGenerations];
		String name = problemSet_.getProblemSetname();


		int generation=0;

		HashMap parameters = new HashMap(); // Operator parameters

		int time = ((Integer) this.getInputParameter("times")).intValue();
		String directoryname = (String) this.getInputParameter("directoryname");


		population_ = new Population();
		offSpring_ = new Population();

		crossover_ = operators_.get("crossover"); // default: DE crossover
		mutation_ = operators_.get("mutation"); // default: polynomial mutation

		int[] permutation = new int[populationSize_];
		Permutation.randomPermutation(permutation,populationSize_);
		double[][] number = new double[problemSet_.size()][maxGenerations];
		double[][] worst = new double[problemSet_.size()][maxGenerations];

		initPopulation();
		generation=0;


		do {
			generation++;

			makeOffspring();
			enviromnentalSelection();
			for(int j = 0;j < problemSet_.size();j++){
				offSpring_.printFactorialCostToFile("result/config/data" + j + "off.dat" , j);

				population_.printFactorialCostToFile("result/config/data" + j + ".dat", j);

				best[j][generation-1] = population_.getMINFactorialCost(j);
				worst[j][generation-1] = population_.getMAXFactorialCost(j);
				number[j][generation-1] = 100;
			}
		} while((generation < maxGenerations) && (evaluations_ < maxEvaluations)) ;
		for(int i = 0; i< problemSet_.size();i++){
			String taskName = problemSet_.get(i).getName();
			fileSubscription.printToFile("result/FUN/" + "SOEAAnother" + "/" + name + "/" +"differentInd"+ taskName + (time -1)+ ".dat", number[i], populationSize_/problemSet_.size());
			fileSubscription.printToFile("result/FUN/" + "SOEAAnother" + "/" + name + "/"+"bset" + taskName + (time -1)+ ".dat", best[i], populationSize_/problemSet_.size());
			fileSubscription.printToFile("result/FUN/" + "SOEAAnother" + "/" + name + "/" +"worst"+ taskName + (time -1)+ ".dat", worst[i], populationSize_/problemSet_.size());
		}

		return population_;
	}


	public void initPopulation() throws JMException, ClassNotFoundException {
 		for (int i = 0; i < populationSize_; i++) {
 			Solution newSolution = new Solution(problemSet_);
 			newSolution.setSkillFactor(i%problemSet_.size());
 			evaluateWithSkillFactor(newSolution);
			evaluations_++;
			population_.add(new Solution( newSolution));
		}
	}

	public void evaluateWithSkillFactor(Solution solution) throws JMException, ClassNotFoundException {
		int tasknumber = solution.getSkillFactor();
		for(int i = 0 ; i < problemSet_.size();i++){
			if(i == tasknumber){
				double sum = problemSet_.get(i).evaluate(solution);
				solution.setFactorialCost(i, sum);
			} else {
				solution.setFactorialCost(i, 10E30);
			}
		}
	}

	public Solution[] makechildWithSkillFactor(Solution parent1, Solution parent2)throws JMException, ClassNotFoundException {

		Solution[] offspring = new  Solution[numberOfParents_];

		if(parent1.getSkillFactor() == parent2.getSkillFactor() || rmp_ < Random.nextDoubleII()){
			Solution[] Parents    = new Solution[numberOfParents_];
			Parents[0]	= parent1;
			Parents[1]	= parent2;

			offspring = (Solution[])crossover_.execute(Parents);

			if(Random.nextDoubleII() < 0.5){
				offspring[0].setSkillFactor(Parents[0].getSkillFactor());
			} else {
				offspring[0].setSkillFactor(Parents[1].getSkillFactor());
			}

			if(Random.nextDoubleII() < 0.5){
				offspring[1].setSkillFactor(Parents[0].getSkillFactor());
			} else {
				offspring[1].setSkillFactor(Parents[1].getSkillFactor());
			}
			for(int j  = 0 ; j < offspring.length;j++){
				offspring[j] = (Solution) mutation_.execute(offspring[j]);
			}

		} else {

			offspring[0]	= new Solution (parent1);
			offspring[1]	= new Solution ( parent2);


			for(int j  = 0 ; j < offspring.length;j++){
				offspring[j] = (Solution) mutation_.execute(offspring[j]);
			}
		}
		return offspring;

	}



	public void makeOffspring() throws JMException, ClassNotFoundException {
		int size = population_.size();
		int[] randomPermutation = new int[size];
		Permutation.randomPermutation(randomPermutation);
		Solution[] parents = new Solution[numberOfParents_];

		offSpring_.clear();

		for(int i = 0;i < size/numberOfmakeChild_ ;i ++){
			for(int j = 0 ;j < numberOfParents_; j ++){
					parents[j] = population_.get(randomPermutation[i*numberOfParents_+j]);
			}
			Solution[] offSpring;
			offSpring = makechildWithSkillFactor(parents[0],parents[1]);
			evaluateWithSkillFactor(offSpring[0]);
			evaluateWithSkillFactor(offSpring[1]);

			offSpring_.add(new Solution (offSpring[0]));
			offSpring_.add(new Solution (offSpring[1]));

		}
	}


	public void enviromnentalSelection(){
		Population empty = new Population(population_);
		int size = population_.size();


		empty.merge(offSpring_);

		empty.setFactorialRank();
		empty.setScalarFitness();
		empty.sortScalarFitness();
		population_ = new Population(empty.cut(0,size -1 ));


	}



}
