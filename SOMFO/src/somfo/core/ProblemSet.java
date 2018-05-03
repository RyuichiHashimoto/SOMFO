package somfo.core;

import java.util.Vector;

public class ProblemSet {

	protected Vector<Problem> ProblemSet_ = new Vector<Problem>();

	double [][] upperLimits_;

	double [][] lowerLimits_;

	int[] numberOfObjectives_;

	int   numberOfVariables_;

	protected String taskname_;

	public ProblemSet(){
		numberOfVariables_ = 0;
	}

	public String getProblemSetname(){
		return taskname_;
	}
	public void setProblemSetname(String s){
		taskname_ = s;
	}



	public void add(Problem p){
		ProblemSet_.add(p);
		if(p.getNumberOfVariables() > numberOfVariables_){
			numberOfVariables_ = p.getNumberOfVariables();
		}
	}

	public int size(){
		return  ProblemSet_.size();
	}
	public Problem get(int key){
		return  ProblemSet_.get(key);
	}

	public int[] getNumberOfObjectives(){
		return numberOfObjectives_;
	}


	public void set(){
		int size = ProblemSet_.size();
		numberOfObjectives_ = new int[size];
		lowerLimits_ = new double[size][];
		upperLimits_ = new double[size][];

		for(int i =0;i<ProblemSet_.size();i++){
			Problem prob = ProblemSet_.get(i);




		}
	}

	public int getNumberOfVariables() {
		return numberOfVariables_;
	}

	public void evaluate(Solution newSolution) {
		assert newSolution.getNumberOftask() > 0: "There are no tasks newSOlution solve";
		double[] f = new double[newSolution.getNumberOftask()];

		for(int i=0;i<newSolution.getNumberOftask() ;i++){
			f[i] = this.get(i).evaluate(newSolution);
		}
		for(int i=0;i<newSolution.getNumberOftask() ;i++){
			newSolution.setFactorialCost(i,f[i]);
		}
	}

	public Solution repair(Solution newSolution, Object object) {
		return newSolution;
		// TODO 自動生成されたメソッド・スタブ

	}

}
