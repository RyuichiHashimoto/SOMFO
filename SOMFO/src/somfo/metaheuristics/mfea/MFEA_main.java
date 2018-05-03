package somfo.metaheuristics.mfea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import somfo.core.Algorithm;
import somfo.core.Operator;
import somfo.core.ProblemSet;
import somfo.operators.crossover.CrossoverFactory;
import somfo.operators.mutation.MutationFactory;
import somfo.problemSet.ProblemSetFactory;
import somfo.util.JMException;
import somfo.util.Random;

public class MFEA_main {

	public static Logger logger_; // Logger object
	public static FileHandler fileHandler_; // FileHandler object

	void subscriptLogToFile(){

	};

	static HashMap log = new HashMap<>();

	static HashMap experiment_setting_ = new HashMap<>();


	static void experiment_setting(String name){

		try(BufferedReader br = new BufferedReader(new FileReader(name))){
		String[] S;
		String line;
		int counter=0;
		while(( line = br.readLine())!= null){
			S  = line.split(":");
			System.out.print(S[0] + " " + S[1] + "\n");
			experiment_setting_.put(S[0],S[1]);
		}

		} catch (IOException e){
			e.printStackTrace();
		}
	}




	public static void main(String[] args) throws JMException, SecurityException, IOException, ClassNotFoundException {


		// this value has the name of weight vector data
		experiment_setting("setting/experiment.st");
		String knapsackfileName;
		String empty;

		int NumberOfRun;
		String Functionname;
		if(experiment_setting_.containsKey("NumberOfRun")){
				empty = (String) experiment_setting_.get("NumberOfRun");
				NumberOfRun = Integer.parseInt(empty);
			} else {
				NumberOfRun = 1;
		}



		int   eve= -1, amo = -1;



		if(experiment_setting_.containsKey("NumberOfRun")){
			Functionname = (String) experiment_setting_.get("NumberOfRun");
		} else {
			System.exit(1);
		}
		String taskname;

		if(experiment_setting_.containsKey("taskName")){
			taskname = (String)experiment_setting_.get("taskName");
		} else {
			taskname="empty";
		}


		String FuntionName;

		taskname = args[0];
		System.out.print(taskname + "dsafsdfadf \n");


		ProblemSet problem_one = ProblemSetFactory.getProblemSet(taskname);






        Algorithm algorithm; // The algorithm to use

        Operator crossover; // Crossover operator
		Operator mutation; // Mutation operator



		HashMap parameters; // Operator parameters
		// Logger object and file to store log messages
		//	problem = new DTLZ1("Real",7,2);

//		problem = new Knapsack("BinaryInt",knapsackfileName);


		//algorithm = new SOEA(problem);
		// algorithm = new MOEAD_DRA(problem);

		File newdir = new File("result");
		newdir.mkdir();
		newdir = new File("result/config");
		newdir.mkdir();
		newdir = new File("result/FUN");
		newdir.mkdir();
		newdir = new File("result/FUN/MFEA/");
		newdir.mkdir();


		newdir = new File("result/FUN/Transfer/"  + taskname);
		newdir.mkdir();

		algorithm = new MFEA(problem_one);


		// Algorithm parameters
		if(experiment_setting_.containsKey("populationSize")){
			empty = (String) experiment_setting_.get("populationSize");
			algorithm.setInputParameter("populationSize", Integer.parseInt(empty));
		} else {
			logger_.info("populationSize is not setted");
			System.exit(1);
		}
		if(experiment_setting_.containsKey("populationSize")){
			empty = (String) experiment_setting_.get("rmp");
			algorithm.setInputParameter("rmp", Double.parseDouble(empty));
		} else {
			logger_.info("rmp is not setted");
			System.exit(1);
		}






		if(experiment_setting_.containsKey("maxEvaluations")){
			empty = (String) experiment_setting_.get("maxEvaluations");
			algorithm.setInputParameter("maxEvaluations", Integer.parseInt(empty));
		} else {
			logger_.info("maxEvaluations is not setted");
			System.exit(1);
		}
		if(experiment_setting_.containsKey("MaxGeneration")){
			empty = (String) experiment_setting_.get("MaxGeneration");
			algorithm.setInputParameter("maxGeneration", Integer.parseInt(empty));
		} else {
			logger_.info("MaxGeneration is not setted");
		}

		if(experiment_setting_.containsKey("numberOfParents")){
			empty = (String) experiment_setting_.get("numberOfParents");
			algorithm.setInputParameter("numberOfParents", Integer.parseInt(empty));
		} else {
			logger_.info("MaxGeneration is not setted");
			System.exit(1);
		}

		parameters = new HashMap();
		mutation = null;

		// Mutation operator
		if(experiment_setting_.containsKey("MutationDistributionIndex")){
			empty = (String) experiment_setting_.get("MutationDistributionIndex");
			parameters.put("distributionIndex",Double.parseDouble(empty));
		} else {
			System.out.println("distributionIndex is not setted");
		}


		if(experiment_setting_.containsKey("MutationProbability")){
			empty = (String) experiment_setting_.get("MutationProbability");
			parameters.put("probability",Double.parseDouble(empty));
		} else {
			System.out.println("MutationProbability is not setted");
	//		System.exit(1);
		}
		parameters.put("probability", 1.0 / problem_one.get(0).getNumberOfVariables());

		if(experiment_setting_.containsKey("MutationName")){
			mutation = MutationFactory.getMutationOperator((String)experiment_setting_.get("MutationName"), parameters);
		} else {
			logger_.info("MutationName is not setted");
			System.exit(1);
		}
		algorithm.addOperator("mutation", mutation);



		if(experiment_setting_.containsKey("CrossOverProbability")){
			empty = (String) experiment_setting_.get("CrossOverProbability");
			parameters.put("probability",Double.parseDouble(empty));
		} else {
			logger_.info("CrossOverProbability is not setted");
			System.exit(1);
		}

		crossover = null;
		if(experiment_setting_.containsKey("CrossOverDistributionIndex")){
			empty = (String) experiment_setting_.get("CrossOverDistributionIndex");
			parameters.put("distributionIndex",Double.parseDouble(empty));
		} else {
			System.out.println("CrossOverDistribution is not setted");
			parameters.put("distributionIndex",20.0);

		}

		if(experiment_setting_.containsKey("CrossOverName")){
			crossover = CrossoverFactory.getCrossoverOperator((String)experiment_setting_.get("CrossOverName"), parameters);
		} else {
			System.out.println("CrossOverName is not setted");
			System.exit(1);
		}
		algorithm.addOperator("crossover", crossover);

		// Execute the Algorithm
		long initTime = System.currentTimeMillis();

		algorithm.setInputParameter("tasknumber", "Task1");

		int counter=0;
		Random.set_seed(123456);
		do {
			counter++;
			algorithm.setInputParameter("times", counter);

			System.out.println("MFEA  start	"  + counter + "	time");
			algorithm.execute();
			System.out.println( "MFEA  end	"  + counter + "	time");
		} while(counter<NumberOfRun);

		long estimatedTime = System.currentTimeMillis() - initTime;

		// Result messages
		System.out.println("Total execution time: " + estimatedTime + "ms");
		System.out.println("Objectives values have been writen to file FUN");
		System.out.println("Variables values have been writen to file VAR");

	}

}
