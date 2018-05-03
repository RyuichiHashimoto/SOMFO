package somfo.main;

import experiments.Setting;
import somfo.core.AlgorithmMain;
import somfo.util.JMException;

public class AlgorithmMainFactory {
	public static AlgorithmMain getAlgorithmMain(String name,Setting parameters) throws JMException {
		if (name.equalsIgnoreCase("SOEA")){
			return new SOEAMain(parameters);
		}else if (name.equalsIgnoreCase("MFEA")){
			return new MFEAMain(parameters);
		}else if (name.equalsIgnoreCase("Transfer")){
			return new TransferMain(parameters);
		}else if (name.equalsIgnoreCase("Random")){
			return new RandomMain(parameters);
		}else if (name.equalsIgnoreCase("Island")){
			return new IslandMain(parameters);
		}else if (name.equalsIgnoreCase("Island_binary")){
			return new IslandMain_Binary(parameters);
		}else if (name.equalsIgnoreCase("Re_Island")){
			return new Re_IslandMain(parameters);
		}else if (name.equalsIgnoreCase("Re_Transfer")){
			return new ReEvaluate_TransferMain(parameters);
		}else if (name.equalsIgnoreCase("Re_Reverse_Transfer")){
			return new ReEvaluate_Reverse_TransferMain(parameters);
		}else if (name.equalsIgnoreCase("Merge")){
			return new MergeMain(parameters);
		}else if (name.equalsIgnoreCase("Reverse_Transfer")){
			return new Reverse_TransferMain(parameters);
		} else if (name.equalsIgnoreCase("Island_with_rate")){
			return new IslandMain_with_rate(parameters);
		} else if (name.equalsIgnoreCase("Island_best")){
			return new IslandMain_Best(parameters);
		}else if (name.equalsIgnoreCase("Island_with_rate_different")){
			return new IslandMain_with_rate_different(parameters);
		}/* else if (name.equalsIgnoreCase("SMSEMOA")){
		}
			return new SMSEMOAMain(parameters);
		} else if (name.equalsIgnoreCase("MOEAD")){
			return new MOEADMain(parameters);
		} else if (name.equalsIgnoreCase("NSGAIII")){
			return new NSGAIIIMain(parameters);
		} else if (name.equalsIgnoreCase("SMSEMOAIGD")){
			return new SMSEMOAIGDMain(parameters);
		} else if (name.equalsIgnoreCase("NormalizeMOEAD")){
			return new NormalizeMOEADMain(parameters);
		} else if (name.equalsIgnoreCase("ParallelSMSEMOAIGD")){
			return new ParallelSMSEMOAIGDMain(parameters);
		} else if (name.equalsIgnoreCase("ParallelSMSEMOA")){
			return new ParallelSMSEMOAMain(parameters);
		}*//*
		  else if (name.equalsIgnoreCase("SBXCrossover2")){
			return new SBXCrossover2(parameters);
		} else if (name.equalsIgnoreCase("UniformCrossover")){
			return new UniformCrossover(parameters);
		}
		else if (name.equalsIgnoreCase("SinglePointCrossover"))
			return new SinglePointCrossover(parameters);
		else if (name.equalsIgnoreCase("PMXCrossover"))
			return new PMXCrossover(parameters);
		else if (name.equalsIgnoreCase("TwoPointsCrossover"))
			return new TwoPointsCrossover(parameters);
		else if (name.equalsIgnoreCase("HUXCrossover"))
			return new HUXCrossover(parameters);
		else if (name.equalsIgnoreCase("DifferentialEvolutionCrossover"))
			return new DifferentialEvolutionCrossover(parameters);
		else if (name.equalsIgnoreCase("BLXAlphaCrossover"))
			return new BLXAlphaCrossover(parameters);
		}*/ else{
			System.err.print("AlgorithmMainFactory.getAlgorithmMain. " + " AlgorithmMain '" + name + "' not found ");
			throw new JMException("Exception in " + name + ".getCrossoverOperator()");
		} // else
	} // getCrossoverOperator

}
