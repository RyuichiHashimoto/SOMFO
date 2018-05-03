package somfo.core;

import javax.naming.NameNotFoundException;

import experiments.Setting;
import experiments.SettingWriter;
import somfo.util.DirectoryMaker;
import somfo.util.JMException;
import somfo.util.Random;

public abstract class AlgorithmMain implements Runnable{

	protected Algorithm[] algorithm;

	protected Setting setting_;

	protected String DirectoryName = "result";

	public AlgorithmMain(Setting test){
		setting_ = test;
	}

	public final void MakeDirectory(String name){


	if(algorithm.length != 1){
		for(int i = 0; i< algorithm.length;i++){
			DirectoryMaker.Make(name +"/"+algorithm[i].getInputParameter("tasknumber") +"/" + "BestFUN");
			DirectoryMaker.Make(name +"/"+algorithm[i].getInputParameter("tasknumber") + "/" + "BestVAR");
			DirectoryMaker.Make(name +"/"+algorithm[i].getInputParameter("tasknumber") + "/" + "WorstFUN");
			DirectoryMaker.Make(name +"/"+algorithm[i].getInputParameter("tasknumber") + "/" + "WorstVAR");
			DirectoryMaker.Make(name +"/"+algorithm[i].getInputParameter("tasknumber") + "/" + "Different");
		}
	} else {
		int task = algorithm[0].getProblem().size();
		for(int i = 0; i< task;i++){
			DirectoryMaker.Make(name +"/Task"+(i+1) +"/" + "BestFUN");
			DirectoryMaker.Make(name +"/Task"+(i+1) + "/" + "BestVAR");
			DirectoryMaker.Make(name +"/Task"+(i+1) + "/" + "WorstFUN");
			DirectoryMaker.Make(name +"/Task"+(i+1) + "/" + "WorstVAR");
			DirectoryMaker.Make(name +"/Task"+(i+1) + "/" + "Different");
		}


	}
		DirectoryMaker.Make(name + "/" + "Setting");
	}

	public final void write(){
		SettingWriter.clear();
		SettingWriter.merge(setting_.get());
		SettingWriter.write(DirectoryName + "/Setting");
	}

	public final void MakeDirectory() {
		System.out.println(DirectoryName);
		MakeDirectory(DirectoryName);
	}

	public final void setSeed(int seed){
		Random.set_seed(seed);
	}

	//実際のアルゴリズムを回す場所
	public final void execute(int nowTrial) throws NameNotFoundException, ClassNotFoundException, JMException{
		int counter=0;
		long initTime = System.currentTimeMillis();
		int  NumberOfRun = setting_.getAsInt("NumberOfTrial");
		do {
			counter++;
			System.out.println();
			for (int task = 0; task< algorithm.length;task++ ){
				setSeed(nowTrial + algorithm.length*counter + task+ setting_.getAsInt("Seed"));
				algorithm[task].setInputParameter("times",nowTrial+counter);
				System.out.println("Task" + String.valueOf(task+1)+"	"+  +counter +"th start");
				long innerinitTime = System.currentTimeMillis();
				algorithm[task].execute();
				long estimatedTime = System.currentTimeMillis() - innerinitTime;
				System.out.println(counter + "th	start  trial time is"  + estimatedTime + "ms" );
			}
		} while(counter<NumberOfRun);
		long estimatedTime = System.currentTimeMillis() - initTime;
		setting_.add("exuecutionTime",  estimatedTime +"ms" );
	};

	public void run(){

	}

	public final void run(int nowTrial) throws ClassNotFoundException, JMException, NameNotFoundException{
		setParameter();
		MakeDirectory();
		write();
		execute(nowTrial);
		write();
	};

	public abstract void setParameter() throws NameNotFoundException, ClassNotFoundException, JMException;

}
