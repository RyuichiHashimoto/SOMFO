package somfo.core;
import java.io.Serializable;
import java.util.Map;

import somfo.core.Solution;


public abstract class Problem implements Serializable{

	abstract public Object repair(Solution  d, Map<String, Object> a);

	protected int numberOfVariables_;

	protected int numberOfObjectives_;


	protected String problemName_;

	protected double[] lowerLimit_;

	protected double[] upperLimit_;

	public int getNumberOfVariables() {
		return numberOfVariables_;
	}

	public void setNumberOfVariables(int numberOfVariables) {
		numberOfVariables_ = numberOfVariables;
	} // getNumberOfVariables

	public int getNumberOfObjectives() {
		return numberOfObjectives_;
	}

	public double getLowerLimit(int i) {
		return lowerLimit_[i];
	} // getLowerLimit

	public void  setLowerLimit(double[] a){
		lowerLimit_ = new double[numberOfVariables_];
		for(int i =0;i<numberOfVariables_;i++){
			lowerLimit_[i] = a[i];
		}
	}
	public void  setUpperLimit(double[] a){
		upperLimit_ = new double[numberOfVariables_];
		for(int i =0;i<numberOfVariables_;i++){
			upperLimit_[i] = a[i];
		}
	}

	
	public double getUpperLimit(int i) {
		return upperLimit_[i];
	} // getUpperLimit

	public double[] getUpperLimit(){
		return upperLimit_;
	}
	public double[] getLowerLimit(){
		return lowerLimit_;
	}
	
	

	public abstract double evaluate(Solution solution);

	public String getName() {
		return problemName_;
	}

}
