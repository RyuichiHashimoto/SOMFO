package somfo.main;

import java.util.HashMap;

import javax.naming.NameNotFoundException;

import experiments.Setting;
import somfo.core.Algorithm;
import somfo.core.AlgorithmMain;
import somfo.core.Operator;
import somfo.core.ProblemSet;
import somfo.metaheuristics.ReEvaluate_IslandModelSOEA.Reevaluate_IslandModelSOEA;
import somfo.operators.crossover.CrossoverFactory;
import somfo.operators.mutation.MutationFactory;
import somfo.problemSet.ProblemSetFactory;
import somfo.util.JMException;

public class ReEvaluate_IslandMain extends AlgorithmMain{

	public ReEvaluate_IslandMain(Setting test) {
		super(test);
	}



	@Override
	public void setParameter() throws NameNotFoundException, ClassNotFoundException, JMException {

		int NumberOfRun = setting_.getAsInt("NumberOfTrial");
		String taskname   = setting_.getAsStr("TaskName");


		ProblemSet problem_ = ProblemSetFactory.getProblemSet(taskname);
		Operator Mutation;
		Operator Crossover;
		DirectoryName = "result/ReEvaluate_Island/" + taskname;
		algorithm = new Algorithm[1];
		algorithm[0] = new Reevaluate_IslandModelSOEA(problem_);

		algorithm[0].setInputParameter("populationSize", setting_.getAsInt("populationSize"));
		algorithm[0].setInputParameter("maxGeneration", setting_.getAsInt("maxGeneration"));
		algorithm[0].setInputParameter("rmp", setting_.getAsDouble("rmp"));

		HashMap parameters = new HashMap(); // Operator parameters
		parameters.put("MutationProbability", setting_.getAsDouble("MutationProbability"));
		parameters.put("MutationDistribution", setting_.getAsDouble("MutationDistribution"));
		parameters.put("CrossoverProbability", setting_.getAsDouble("CrossoverProbability"));
		parameters.put("CrossoverDistribution", setting_.getAsDouble("CrossoverDistribution"));
		Mutation = MutationFactory.getMutationOperator((String)setting_.getAsStr("MutationName"), parameters);
		Crossover = CrossoverFactory.getCrossoverOperator((String)setting_.getAsStr("CrossoverName"), parameters);
		algorithm[0].addOperator("crossover", Crossover);
		algorithm[0].addOperator("mutation", Mutation);

		algorithm[0].setInputParameter("everygeneration", setting_.getAsInt("every"));
		algorithm[0].setInputParameter("amountoftransfer", setting_.getAsInt("amount"));
		DirectoryName += "/every" + setting_.getAsStr("every") +"amount" + setting_.getAsStr("amount");

//		algorithm[0].setInputParameter("tasknumber", "Task" + String.valueOf(i+1) );
		DirectoryName = "result/ReEvaluate_Island" +"/every" + setting_.getAsStr("every") +"amount" + setting_.getAsStr("amount")+"/"+ taskname;
		algorithm[0].setInputParameter("DirectoryName", DirectoryName);


	}

}