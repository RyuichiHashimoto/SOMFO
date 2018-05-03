package somfo.metaheuristics.sameProblemSolver;

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
import somfo.problems.SOP.FunctionFactory;
import somfo.util.JMException;
import somfo.util.Random;

public class SameProblemSOEA_main {

	public static Logger logger_; // Logger object
	public static FileHandler fileHandler_; // FileHandler object

	void subscriptLogToFile(){

	};

	static HashMap log = new HashMap<>();

	static HashMap experiment_setting_one = new HashMap<>();
	static HashMap experiment_setting_two = new HashMap<>();


	static void experiment_setting_one(String name){

		try(BufferedReader br = new BufferedReader(new FileReader(name))){
		String[] S;
		String line;
		int counter=0;
		while(( line = br.readLine())!= null){
			S  = line.split(":");
			System.out.print(S[0] + " " + S[1] + "\n");
			experiment_setting_one.put(S[0],S[1]);
		}

		} catch (IOException e){
			e.printStackTrace();
		}
	}

	static void experiment_setting_two(String name){

		try(BufferedReader br = new BufferedReader(new FileReader(name))){
		String[] S;
		String line;
		int counter=0;
		while(( line = br.readLine())!= null){
			S  = line.split(":");
			System.out.print(S[0] + " " + S[1] + "\n");
			experiment_setting_two.put(S[0],S[1]);
		}

		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void assert_each(HashMap<Object,Object> d){

	}




	public static void main(String[] args) throws JMException, SecurityException, IOException, ClassNotFoundException {


		// this value has the name of weight vector data
		experiment_setting_one("setting/sameProblemSolver_one.ini");
		experiment_setting_two("setting/sameProblemSolver_two.ini");

		String empty;

		int NumberOfRun;
		String Functionname;
		assert experiment_setting_one.containsKey("NumberOfRun") : "setting/sameProblem.st:: don't have the information of Run Times";
		assert experiment_setting_one.containsKey("PopulationSize") : "setting/sameproblem.st :: don't have the information of PopulationSize";
		assert experiment_setting_one.containsKey("TerminationConditionStyle"): "setting/sameProblem.st:: don't have the information of TerminationConditionStyle";
		assert experiment_setting_one.containsKey("numberOfParents") : "setting/sameprobllem.st:: don't have the information of numberOfParents";
		assert experiment_setting_one.containsKey("MutationDistributionIndex") : "setting/sameprobllem.st:: don't have the information of  MutationDistributionIndex";
		assert experiment_setting_one.containsKey("MutationProbability"): "setting/sameprobllem.st:: don't have the information of  MutationProbability";
		assert experiment_setting_one.containsKey("maxEvaluations") : "setting/sameProblem.st :: don't  have the information of MaxEvaluation";
		assert experiment_setting_one.containsKey("maxGeneration") : "setting/sameProblem.st :: don't  have the information of MaxEvaluation";
		assert experiment_setting_one.containsKey("MutationName"): "setting/sameProblem.st :: don't  have the information of MutationName";
		assert experiment_setting_one.containsKey("CrossOverProbability") : "setting/sameProblem.st :: don't  have the information of CrossOverProbability";
		assert experiment_setting_one.containsKey("CrossOverDistributionIndex") : "setting/sameProblem.st :: don't  have the information of CrossOverDistributionIndex";
		assert experiment_setting_one.containsKey("CrossOveName") : "setting/sameProblem.st :: don't  have the information of CrossOverName";

		assert experiment_setting_two.containsKey("NumberOfRun") : "setting/sameProblem.st:: don't have the information of Run Times";
		assert experiment_setting_two.containsKey("PopulationSize") : "setting/sameproblem.st :: don't have the information of PopulationSize";
		assert experiment_setting_two.containsKey("TerminationConditionStyle"): "setting/sameProblem.st:: don't have the information of TerminationConditionStyle";
		assert experiment_setting_two.containsKey("numberOfParents") : "setting/sameprobllem.st:: don't have the information of numberOfParents";
		assert experiment_setting_two.containsKey("MutationDistributionIndex") : "setting/sameprobllem.st:: don't have the information of  MutationDistributionIndex";
		assert experiment_setting_two.containsKey("MutationProbability"): "setting/sameprobllem.st:: don't have the information of  MutationProbability";
		assert experiment_setting_two.containsKey("maxEvaluations") : "setting/sameProblem.st :: don't  have the information of MaxEvaluation";
		assert experiment_setting_two.containsKey("maxGeneration") : "setting/sameProblem.st :: don't  have the information of MaxEvaluation";
		assert experiment_setting_two.containsKey("MutationName"): "setting/sameProblem.st :: don't  have the information of MutationName";
		assert experiment_setting_two.containsKey("CrossOverProbability") : "setting/sameProblem.st :: don't  have the information of CrossOverProbability";
		assert experiment_setting_two.containsKey("CrossOverDistributionIndex") : "setting/sameProblem.st :: don't  have the information of CrossOverDistributionIndex";
		assert experiment_setting_two.containsKey("CrossOveName") : "setting/sameProblem.st :: don't  have the information of CrossOverName";

		NumberOfRun = Integer.parseInt((String) experiment_setting_one.get("NumberOfRun"));



		String every = args[1],amount = args[2];
		int   eve= -1, amo = -1;
		eve = Integer.parseInt(every);
		amo = Integer.parseInt(amount);

		String taskname;



		taskname = args[0];


		ProblemSet  problem = new ProblemSet();
		problem.add(FunctionFactory.getFunctionProblem(taskname, null));
		problem.setProblemSetname(problem.get(0).getName());


        Algorithm algorithm; // The algorithm to use
        Algorithm algorithmone; // The algorithm to use
        Algorithm algorithmtwo; // The algorithm to use

        Operator crossover; // Crossover operator
		Operator mutation; // Mutation operator
		HashMap parameters; // Operator parameters

		// Logger object and file to store log messages
		//	problem = new DTLZ1("Real",7,2);

//		problem = new Knapsack("BinaryInt",knapsackfileName);


		//algorithm = new SOEA(problem);
		// algorithm = new MOEAD_DRA(problem);
		String filenametwo =  "every" + every+ "amount" + amount;
		File newdir = new File("result"); newdir.mkdir();
		newdir = new File("result/config");		newdir.mkdir();
		newdir = new File("result/FUN");		newdir.mkdir();
		newdir = new File("result/FUN/SameSolver");		newdir.mkdir();
		newdir = new File("result/FUN/SameSolver/" + filenametwo);		newdir.mkdir();
		newdir = new File("result/FUN/SameSolver/"+filenametwo + "/"  + taskname);		newdir.mkdir();
		parameters = new HashMap();
		algorithm = new SameProblemSOEA(problem);

		algorithmone = new SameProblemSOEA(problem);
		algorithmtwo = new SameProblemSOEA(problem);

		algorithm.setInputParameter("everygeneration", eve);
		algorithm.setInputParameter("amountoftransfer", amo);


		algorithmone.setInputParameter("populationSize", Integer.parseInt((String) experiment_setting_one.get("populationSize")));
		algorithmone.setInputParameter("TerminationConditionStyle", (String) experiment_setting_one.get("TerminationConditionStyle"));
		algorithmone.setInputParameter("directoryname", filenametwo);
		algorithmone.setInputParameter("maxEvaluations", Integer.parseInt((String) experiment_setting_one.get("maxEvaluations")));
		algorithmone.setInputParameter("maxGeneration", Integer.parseInt((String) experiment_setting_two.get("MaxGeneration")));
		algorithmone.setInputParameter("numberOfParents", Integer.parseInt((String) experiment_setting_one.get("numberOfParents")));
		parameters.put("distributionIndex",Double.parseDouble((String) experiment_setting_one.get("MutationDistributionIndex")));
		parameters.put("probability",Double.parseDouble((String) experiment_setting_one.get("MutationProbability")));
		mutation = MutationFactory.getMutationOperator((String)experiment_setting_one.get("MutationName"), parameters);
		algorithmone.addOperator("mutation", mutation);
		parameters.put("probability",Double.parseDouble((String) experiment_setting_one.get("CrossOverProbability")));
		parameters.put("distributionIndex",Double.parseDouble((String) experiment_setting_one.get("CrossOverDistributionIndex")));
		crossover = CrossoverFactory.getCrossoverOperator((String)experiment_setting_one.get("CrossOverName"), parameters);
		algorithmone.addOperator("crossover", crossover);

		algorithmtwo.setInputParameter("populationSize", Integer.parseInt((String) experiment_setting_one.get("populationSize")));
		algorithmtwo.setInputParameter("TerminationConditionStyle", (String) experiment_setting_one.get("TerminationConditionStyle"));
		algorithmtwo.setInputParameter("directoryname", filenametwo);
		algorithmtwo.setInputParameter("maxEvaluations", Integer.parseInt((String) experiment_setting_one.get("maxEvaluations")));
		algorithmtwo.setInputParameter("maxGeneration", Integer.parseInt((String) experiment_setting_two.get("MaxGeneration")));
		algorithmtwo.setInputParameter("numberOfParents", Integer.parseInt((String) experiment_setting_one.get("numberOfParents")));
		parameters.put("distributionIndex",Double.parseDouble((String) experiment_setting_one.get("MutationDistributionIndex")));
		parameters.put("probability",Double.parseDouble((String) experiment_setting_one.get("MutationProbability")));
		mutation = MutationFactory.getMutationOperator((String)experiment_setting_one.get("MutationName"), parameters);
		algorithmtwo.addOperator("mutation", mutation);
		parameters.put("probability",Double.parseDouble((String) experiment_setting_one.get("CrossOverProbability")));
		parameters.put("distributionIndex",Double.parseDouble((String) experiment_setting_one.get("CrossOverDistributionIndex")));
		crossover = CrossoverFactory.getCrossoverOperator((String)experiment_setting_one.get("CrossOverName"), parameters);
		algorithmtwo.addOperator("crossover", crossover);

		algorithm.setInputParameter("algorithmtwo", algorithmtwo);
		algorithm.setInputParameter("algorithmone", algorithmone);

		long initTime = System.currentTimeMillis();

		algorithm.setInputParameter("tasknumber", "Task1");

		int counter=0;
		Random.set_seed(123456);
		do {
			counter++;
			algorithm.setInputParameter("times", counter);
			System.out.println("SOEA  start	"  + counter + "	time");
			algorithm.execute();
			System.out.println( "SOEA  end	"  + counter + "	time");
		} while(counter<NumberOfRun);

		long estimatedTime = System.currentTimeMillis() - initTime;

		// Result messages
		System.out.print("Total execution time: " + estimatedTime + "ms");
		System.out.print("Objectives values have been writen to file FUN");
		System.out.print("Variables values have been writen to file VAR");

	}

}
