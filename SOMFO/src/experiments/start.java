package experiments;
import static org.kohsuke.args4j.ExampleMode.*;

import java.util.List;

import javax.naming.NameNotFoundException;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
/*
 * ここで指定することによりプログラムを実行できるようにする．
 * 増田さんが使用していたargs4j が便利そうなのでそれを利用できるか検討する．
 *
 * 現段階の案として，いちいち変更が必要なものに関して，settingに書くよりここに書いた方がいいものはここに書く．
 * 一回の試行で一つの問題を解く;
 *
 *
 */

import somfo.core.AlgorithmMain;
import somfo.main.AlgorithmMainFactory;
import somfo.util.JMException;

/*
 * きれいにかきましょう．
 *　SMSEMOAに使用されるrefについては，後でやる・
 */

public class start {

	@Option(name="-ga", aliases= "--GeneticAlgorithm", required=true, usage="Specify the populationSize")
	private String geneticAlgorithmName;
	@Option(name="-nowTrial", aliases= "---nowTrial", required=false, usage="Specify the nowTrial")
	private int nowTrial = 0;
	@Option(name="-p", aliases= "--Problem Name", required=true, usage="set the Problem Name")
	private String problenName;
	@Option(name="-ps", aliases= "--PopulationSize", required=false, metaVar="<populationSize>", usage="Specify the populationSize")
	private int populationSize = -1;
	@Option(name="-co", aliases= "--Crossover", required=false, metaVar="<Crossover>", usage="Specify the Crossover")
	private String crossoverName = null;
	@Option(name="-cop", aliases= "--CrossoverProbability", required=false, metaVar="<CrossovePror>", usage="Specify the CrossoverProbability")
	private double CrossoverProbablity = 2;
	@Option(name="-cod", aliases= "--CrossoverDistribution", required=false, metaVar="<CrossoverDistribution>", usage="Specify the CrossoverDistribution")
	private double CrossoverDistribution = -1;
	@Option(name="-mt", aliases= "--Mutation", required=false, metaVar="<Mutation>", usage="Specify the Mutation")
	private String MutationName = null;
	@Option(name="-mtp", aliases= "--MutationProbablity", required=false, metaVar="<mutationProbablity>", usage="Specify the Mutation Probablity")
	private double MutationProbablity =  2;
	@Option(name="-mtd", aliases= "--MutationDistribution", required=false, metaVar="<MutationDistribution>", usage="Specify the Mutation Probablity")
	private double MutationDistribution = -1;
	@Option(name="-rep", aliases= "--repeat time", required=false, metaVar="<Repeat time>", usage="Specify the the Iteration")
	private int reps = -1;
	@Option(name="-eva", aliases= "--MaxEvaluations", required=false, metaVar="<MaxEvaluations>", usage="Specify --MaxEvaluations")
	private int maxEvaluations_ = -1;
	@Option(name="-gen", aliases= "--MaxGeneration", required=false, metaVar="<MaxGeneration>", usage="Specify --MaxGeneration")
	private int maxGenerations_ = -1;
	@Option(name="-seed", aliases= "--seed", required=false, metaVar="<Seed >", usage="Specify the seed of random number")
	private int seed_ = -1;
	@Option(name="-ep", aliases= "--Epsilon", required=false, usage="Specify the Epsilon on NormalizeObjectives")
	private double epsiron = - 1;
	@Option(name="-e", aliases= "--Every", required=false, usage="Specify the Epsilon on NormalizeObjectives")
	private int e = - 1;
	@Option(name="-a", aliases= "--amount", required=false, usage="Specify the Epsilon on NormalizeObjectives")
	private int a = - 1;
	@Option(name="-rmp", aliases= "--RMP", required=false, usage="Specify the Epsilon on NormalizeObjectives")
	private double rmp = - 1;


	@Option(name="-dir", aliases= "--Directory", required=false, usage="Specify the Directory storing Directory")
	private String Directory = null;
	@Argument
	private List<String> arguments;

	public static void main(String[] args) throws NameNotFoundException, ClassNotFoundException, JMException {
		 new start().domain(args);
	}

	public void domain(String[] args) throws NameNotFoundException, ClassNotFoundException, JMException {
	      CmdLineParser parser = new CmdLineParser(this);
	        // if you have a wider console, you could increase the value;
	        // here 80 is also the default
	        parser.setUsageWidth(80);

	        try {

	        	parser.parseArgument(args);
	        	if (arguments != null) throw new IllegalArgumentException("No file is given. ");
	        } catch( CmdLineException e ) {
	            System.err.println(e.getMessage());
	            System.err.println("java test [options...] arguments...");
	            // print the list of available options
	            parser.printUsage(System.err);
	            System.err.println();
	            // print option sample. This is useful some time
	            System.err.println("  Example: java SampleMain"+parser.printExample(ALL));
	            return;
	        }
	        Setting hashmap  = new Setting();
	        add(hashmap);
	        hashmap.experiment_setting("setting/" + geneticAlgorithmName + ".st");
	        AlgorithmMain algorithm =  AlgorithmMainFactory.getAlgorithmMain(geneticAlgorithmName, hashmap);
	        algorithm.run(nowTrial);
	}

	public void add(Setting hashmap) throws JMException{
        hashmap.add("TaskName", problenName);

        if (seed_ != -1){
        	hashmap.add("Seed", seed_);
        }
        if (maxEvaluations_ != -1){
         	hashmap.add("maxEvaluations",maxEvaluations_);
        }
        if (maxGenerations_ != -1){
        	hashmap.add("maxGeneration",maxGenerations_);
        }
        System.out.println(maxEvaluations_ + "	");



//      hashmap.add("outputNormal", OutNorm);
        if (rmp != -1) hashmap.add("rmp", rmp);
        if(epsiron != -1) hashmap.add("epsilon", epsiron);
        if(populationSize != -1) hashmap.add("populationSize", populationSize);
        if(crossoverName != null) hashmap.add("CrossoverName", crossoverName);
        if(CrossoverProbablity != 2) hashmap.add("CrossoverProbability", CrossoverProbablity);
        if(CrossoverDistribution != -1) hashmap.add("CrossoverDistribution", CrossoverDistribution);
        if(MutationName != null) hashmap.add("MutationName", MutationName);
        if(MutationProbablity != 2) hashmap.add("MutationProbablity", MutationProbablity);
        if(MutationDistribution != -1) hashmap.add("MutationDistribution", MutationDistribution);
        if(reps != -1) hashmap.add("NumberOfTrial", reps);
        if(e != -1) hashmap.add("every", e);
        if(a != -1) hashmap.add("amount", a);
        if(Directory != null) hashmap.add("ResultDirectory", Directory);

	}



}
