package somfo.main;

import java.util.HashMap;

import javax.naming.NameNotFoundException;

import experiments.Setting;
import somfo.core.Algorithm;
import somfo.core.AlgorithmMain;
import somfo.core.Operator;
import somfo.core.ProblemSet;
import somfo.metaheuristics.soea.SOEA;
import somfo.operators.crossover.CrossoverFactory;
import somfo.operators.mutation.MutationFactory;
import somfo.problemSet.ProblemSetFactory;
import somfo.util.JMException;

public class SOEAMain extends AlgorithmMain{

	public SOEAMain(Setting test) {
		super(test);
	}



	@Override
	public void setParameter() throws NameNotFoundException, ClassNotFoundException, JMException {

		int NumberOfRun = setting_.getAsInt("NumberOfTrial");
		String taskname   = setting_.getAsStr("TaskName");

		ProblemSet problem_ = ProblemSetFactory.getProblemSet(taskname);

		ProblemSet problem_one = new ProblemSet();
		ProblemSet problem_two = new ProblemSet();
		Operator Mutation;
		Operator Crossover;
		DirectoryName = "result/SOEA/" + taskname;
		algorithm = new Algorithm[problem_.size()];

		for(int i=0;i<problem_.size();i++){
			ProblemSet temp = new ProblemSet();
			temp.add(problem_.get(i));
			algorithm[i] = new SOEA(temp);
		}
		for(int i=0;i<problem_.size();i++){
			algorithm[i].setInputParameter("populationSize", setting_.getAsInt("populationSize")/algorithm.length);
		}
		for(int i=0;i<problem_.size();i++){
			algorithm[i].setInputParameter("maxGeneration", setting_.getAsInt("maxGeneration"));
		}

		for(int i=0;i<problem_.size();i++){
			algorithm[i].setInputParameter("DirectoryName", DirectoryName);
		//	algorithm[i].setInputParameter("maxEvaluations", setting_.getAsInt("maxEvaluations"));
		}

		HashMap parameters = new HashMap(); // Operator parameters
		parameters.put("MutationProbability", setting_.getAsDouble("MutationProbability"));
		parameters.put("MutationDistribution", setting_.getAsDouble("MutationDistribution"));
		parameters.put("CrossoverProbability", setting_.getAsDouble("CrossoverProbability"));
		parameters.put("CrossoverDistribution", setting_.getAsDouble("CrossoverDistribution"));
		Mutation = MutationFactory.getMutationOperator((String)setting_.getAsStr("MutationName"), parameters);
		Crossover = CrossoverFactory.getCrossoverOperator((String)setting_.getAsStr("CrossoverName"), parameters);
		for(int i=0;i < problem_.size();i++){
			algorithm[i].addOperator("crossover", Crossover);
			algorithm[i].addOperator("mutation", Mutation);
		}


		for(int i=0;i < problem_.size();i++){
			algorithm[i].setInputParameter("tasknumber", "Task" + String.valueOf(i+1) );
		}



	}

}