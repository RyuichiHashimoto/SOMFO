package somfo.core;

import somfo.util.Random;

public class Solution {
	ProblemSet problemSet_;


//移住した個体かの確認
	boolean transferredSolution;


//移住した個体で生成された子個体か確認
	boolean transferredOffspring;


	int numberOftasks_;

	int numberOfVariables_;

	int[] numberOfObjectives_;

	double[][] objective_;

	int skillFactor_;

	int[] factorialRank_;

	double[] factorialCost_;


	double[] upperLimit_;

	double[] lowerLimit_;

	double scalarFitness_;

	double[] value_;

	public int getValueSize(){
		return value_.length;
	}

	public ProblemSet getProblemSet(){
			return problemSet_;
	}

	//Each task has the its limits;
	public Solution(int n){
			upperLimit_ = new double[n];
			lowerLimit_ = new double[n];
			value_ = new double[n];
		for(int i = 0;i< n;i++){
			upperLimit_[i] = 1.0;
			lowerLimit_[i] = 0.0;
			value_[i] = 0.0;
		}
	}
	public int getNumberOftask(){return numberOftasks_;}

	//必須
	public Solution(Solution a ){



		transferredSolution = a.getTransferedSolution();


		transferredOffspring = a.getTransferredOffspring();



		numberOftasks_ = a.getNumberOftask();
		numberOfVariables_ = a.getNumberOfVariables();
		skillFactor_ = a.getSkillFactor();

		factorialRank_ = new int[numberOftasks_];
		factorialCost_ = new double[numberOftasks_];
		scalarFitness_ = a.getScalarFitness();

		problemSet_ = a.getProblemSet();


		numberOfObjectives_ = new int[numberOftasks_];
		objective_ = new double[numberOftasks_][];

		setObjective(a.getObjective());
		for(int i = 0;i < numberOftasks_;i ++){
			factorialRank_[i] = a.getfactorialRank(i);
			factorialCost_[i] = a.getFactorialCost(i);
		}

		upperLimit_ = new double[numberOfVariables_];
		lowerLimit_ = new double[numberOfVariables_];
		value_ = new double[numberOfVariables_];
		objective_ = new double[numberOftasks_][];
		for(int i=0;i<numberOftasks_;i++){
			numberOfObjectives_[i] = problemSet_.get(i).getNumberOfObjectives();
			objective_[i] = new double[numberOfObjectives_[i]];
		}

		for(int i  = 0 ; i < numberOfVariables_;i++){
			upperLimit_[i] = a.getUpperLimit(i);
			lowerLimit_[i] = a.getlowerLimit(i);
			value_[i]	  = a.getValue(i);
		}
		for(int i  = 0 ; i < numberOftasks_;i++){
			objective_[i] = new double[numberOfObjectives_[i]];
			for(int j = 0;j < numberOfObjectives_[i];j++){
				objective_[i][j] = a.getObjective(i, j);
			}
		}

	}

	public boolean getTransferedSolution(){
		return transferredSolution;
	}
	public void setTransferedSolution(boolean d){
		transferredSolution = d;
	}

	public boolean getTransferredOffspring(){
		return transferredOffspring;
	}
	public void seTransferredOffspring(boolean d){
		transferredOffspring = d;
	}

	public void remake(){
		for(int i=0;i<numberOftasks_;i++){
			for(int j =0 ;j < numberOfObjectives_[i];j++){
				objective_[i][j] = 0.0;
			}
		}
		for(int i =0 ;i < numberOfVariables_;i++){
			value_[i] = Random.nextDoubleII();
		}
	}

	public double[] getValue(){
		return value_;
	}

	public double getObjective(int TaskNumber,int ObjectiveNumber){
		return objective_[TaskNumber][ObjectiveNumber];
	}

	public double[][] getObjective(){
		return objective_;
	}

	public double[] getUpperLimits(){
		return upperLimit_;
	}
	public double[] getLowerLimits(){
		return lowerLimit_;
	}

	public double getValue(int a){
		return value_[a];
	}
	public int getNumberOfObjectives(int key){
		return numberOfObjectives_[key];
	}

	public int getNumberOfVariables(){
		return	numberOfVariables_;
	}

	public double[] getObjectives(int taskNumber){
		return objective_[taskNumber];
	}


	private void limitreset(){
		for(int i=0;i<numberOfVariables_;i++){
			upperLimit_[i] = 1.0;
			lowerLimit_[i] = 0.0;
		}
	}

	// judge two Solutino have same point
	public static boolean  IsEqual(Solution one,Solution two){
		double sum = 0;
		double emp;
		for(int i = 0; i < one.getNumberOfVariables();i++){
			emp = (one.getValue(i) - two.getValue(i))*(one.getValue(i) - two.getValue(i));
			emp = Math.sqrt(emp);
			if(emp > 1.0E-4){
				return false;
			}
		}
			return true;
	}


	public Solution(Problem a) throws ClassNotFoundException {
		transferredSolution = false;

		transferredOffspring = false;

		numberOftasks_ = 1;
		skillFactor_ = 0;

		problemSet_ = new ProblemSet();
		problemSet_.add(a);

		factorialCost_ = new double[1];
		factorialRank_ = new int [1];
		numberOfVariables_ = a.getNumberOfVariables();
		upperLimit_ = new double[numberOfVariables_];
		lowerLimit_ = new double[numberOfVariables_];
		value_ = new double[numberOfVariables_];
		objective_ = new double[1][a.getNumberOfObjectives()];
		numberOfObjectives_ = new int[1];
		numberOfObjectives_[0] = a.getNumberOfObjectives();

		for(int i  = 0 ; i < numberOfVariables_;i++){
			upperLimit_[i] = 1.0;
			lowerLimit_[i] = 0.0;
		}
		remake();

	}

	public Solution(ProblemSet problemSet) throws ClassNotFoundException {

		numberOftasks_ = problemSet.size();

		skillFactor_ = -1;
		transferredOffspring = false;
		factorialRank_ = new int[numberOftasks_];
		factorialCost_ = new double[numberOftasks_];
		scalarFitness_ = 1.0E20;

		transferredSolution = false;

		problemSet_ = problemSet;

		numberOfVariables_ = problemSet.getNumberOfVariables();

		numberOfObjectives_ = new int[numberOftasks_];
		objective_ = new double[numberOftasks_][];
		upperLimit_ = new double[numberOfVariables_];
		lowerLimit_ = new double[numberOfVariables_];
		value_ = new double[numberOfVariables_];
		objective_ = new double[numberOftasks_][];
		for(int i=0;i<numberOftasks_;i++){
			numberOfObjectives_[i] = problemSet_.get(i).getNumberOfObjectives();
			objective_[i] = new double[numberOfObjectives_[i]];
		}

		for(int i  = 0 ; i < numberOfVariables_;i++){
			upperLimit_[i] = 1.0;
			lowerLimit_[i] = 0.0;
		}
		for(int i  = 0 ; i < numberOftasks_;i++){
			objective_[i] = new double[numberOfObjectives_[i]];
			for(int j = 0;j < numberOfObjectives_[i];j++){
				objective_[i][j] = -1;
			}
		}

		remake();
	}


	public void setObjective(int TaskNumber,int ObjectiveNumber,double a){
		objective_[TaskNumber][ObjectiveNumber] = a;
	}
	public void setObjectives(int taskNumber,double[] a){
		for(int key = 0; key < numberOfObjectives_[taskNumber];key++)
			objective_[taskNumber][key] = a[key];
	}
	public void setObjectives(double[][] a){
		for(int t = 0;t<numberOftasks_;t++){
			for(int key = 0; key < numberOfObjectives_[t];key++)
				objective_[t][key] = a[t][key];
			}
	}

	public void setFactorialCost(int key,double a){
		factorialCost_[key] = a;
	}
	public void setFactorialCost(double[] a){
		for(int i=0;i<problemSet_.size();i++){
				factorialCost_[i] = a[i];
		}
	}
	public double[] getFactorialCost(){
		return factorialCost_;
	}
	public double getFactorialCost(int key){
		return factorialCost_[key];
	}

	public void  setSkillFactor(int a){
		skillFactor_ = a;
	}

	public int getSkillFactor(){
		return skillFactor_;
	}

	public void setScalarFitness(double a){
		scalarFitness_ = a;
	}

	public double getScalarFitness(){
		return scalarFitness_;
	}


	public int[] getfactorialRank(){
		return factorialRank_;
	}
	public int getfactorialRank(int key){
		return factorialRank_[key];
	}

	public void setFactorialRank(int[] rank){
		for(int i=0;i<problemSet_.size();i++){
			factorialRank_[i] = rank[i];
		}
	}
	public void setFactorialRank(int key,  int rank){
			factorialRank_[key] = rank;
	}




	public void setUpperLimit(double[] a){
		for(int i=0 ;i < a.length;i++){
			upperLimit_[i] = a[i];
		}
	}
	public void setLowerLimit(double[] a){
		for(int i=0 ;i < a.length;i++){
			lowerLimit_[i] = a[i];
		}
	}
	public double getUpperLimit(int key){
			return  upperLimit_[key];
	}
	public double getlowerLimit(int key){
		return  lowerLimit_[key];
}
	public void setValues(double[] value){
		for(int key = 0; key < value.length;key++)
			value_[key] = value[key];
	}

	public void printVale(){
		for(int i= 0;i < numberOfVariables_;i++)
		System.out.print(value_[i] +" " );
		System.out.print("\n");

	}

	public void printObjectives(){
		for(int j =0; j<numberOftasks_;j++){
			for(int i= 0;i < numberOfObjectives_[j];i++)
				System.out.print(objective_[i] +" " );
			System.out.print("\n");

		}
	}
	public void SetNumberOfObjectives(int[] a){
		for(int i = 0 ;  i < a.length;i++){
			numberOfObjectives_[i] = a[i];
		}
	}

	public void setNumberOfObjectives(int tasknumber, int NumberOfObjectives){
			numberOfObjectives_[tasknumber] = NumberOfObjectives;
	}

	public void setObjective(double[][] Objectives){
		for(int i=0;i<numberOftasks_;i++){
			for(int k = 0;k<problemSet_.get(i).getNumberOfObjectives();k++){
				objective_[k] = Objectives[k];
			}
		}
	}

	public void setObjective( int taskNumber,double[] Objectives){
		for(int i = 0;i<problemSet_.get(taskNumber).getNumberOfObjectives();i++){
				objective_[taskNumber][i] = Objectives[i];
		}
	}

	public void SetObjective(int tasknumber,int ObjectiveNumber,double ob ){
		objective_[tasknumber][ObjectiveNumber] = ob;
	}
	public void SetUpperLimit(double [] a){
		for(int i =0;i<numberOfVariables_;i++){
			upperLimit_[i] = a[i];
		}
	}
	public void SetLowerLimit(double [] a){
		for(int i =0;i<numberOfVariables_;i++){
			lowerLimit_[i] = a[i];
		}
	}

	public void setValue(int key,double a){
		value_[key] = a;
	}
	public void setValue(double[] a){
		for(int i=0;i < numberOfVariables_;i++){
			value_[i] = a[i];
		}
	}
	public void setNumberOfVariables(int a){
		numberOfVariables_ = a;
	}

	public void setID(int i) {
		// TODO 自動生成されたメソッド・スタブ

	}



}
