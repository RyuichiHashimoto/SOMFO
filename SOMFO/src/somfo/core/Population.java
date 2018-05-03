package somfo.core;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Vector;

import somfo.util.Permutation;


public class Population {

	private Solution[] population_;

	int populationSize_ = 0;;
	int capacity = 0;

	public int getCapacity(){return capacity;}

	public Population(Population a){

		capacity = a.getCapacity();
		population_ = new Solution[a.getCapacity()];

		for(int i =0 ;i < a.size();i++){
			add((a.get(i)));
		}
	}

	public Population() {
		capacity = 10;
		population_ = new Solution[10];
		populationSize_ = 0;
	}
	public Population(int d) {
		capacity = d;
		population_ = new Solution[d];
		populationSize_ = 0;
	}



	public Solution get(int key){
		return population_[key];
	}


	public double getMAXFactorialCost(int a){
		double max = 1.0E-30;

		for(int i = 0;i < populationSize_;i++){
			if(max < population_[i].getFactorialCost(a) &&  population_[i].getSkillFactor() == a ){
				max = population_[i].getFactorialCost(a);
			}
		}
		return max;
	}
	public double getMAXFactorialCost(){
		double max = 1.0E-30;

		for(int i = 0;i < populationSize_;i++){
			if(max < population_[i].getFactorialCost(0)){
				max = population_[i].getFactorialCost(0);
			}
		}
		return max;
	}
	public double getMINFactorialCost(){
		double min = Double.MAX_VALUE;

		for(int i = 0;i < populationSize_;i++){
			if(min > population_[i].getFactorialCost(0) ){
				min = population_[i].getFactorialCost(0);
			}
		}
		return min;
	}


	public double getMINFactorialCost(int a){
		double min = Double.MAX_VALUE;

		for(int i = 0;i < populationSize_;i++){
			if(min > population_[i].getFactorialCost(a) && population_[i].getSkillFactor() == a ){
				min = population_[i].getFactorialCost(a);
			}
		}
		return min;
	}

	public double getMINFactorialCost_without_skillfactor(int a){
		double min = Double.MAX_VALUE;

		for(int i = 0;i < populationSize_;i++){
			if(min > population_[i].getFactorialCost(a)  ){
				min = population_[i].getFactorialCost(a);
			}
		}
		return min;
	}


	public void add(Solution a){
		population_[populationSize_] = new Solution(a);
		populationSize_++;
	}
	public void clear(){
		populationSize_ = 0;
	}

	public void factorial_sort(int key){
		for(int i = 0 ;i < populationSize_ - 1;i++){
			for(int j = i + 1;j < populationSize_;j++){
				if(population_[i].getFactorialCost(key) > population_[j].getFactorialCost(key)){
					Solution a = (population_[i]);
					population_[i] =  population_[j];
					population_[j] = a;
				}
			}
		}
	}

	public void setFactorialRank(){
		int size = population_[0].getNumberOftask();
		for(int j = 0; j< size; j++){
			factorial_sort(j);
			for(int i = 0;i < populationSize_; i++){
				population_[i].setFactorialRank(j, i+1);
			}
		}
	}

	public void setScalarFitness(){
		int size = population_[0].getNumberOftask();
			for(int i  = 0; i < populationSize_;i++){
				int min = population_[i].getfactorialRank(0);
				for(int j = 1 ;j <size;j++){
					if(min > 	population_[i].getfactorialRank(j)){
						min = 	population_[i].getfactorialRank(j);
					}
			}
			population_[i].setScalarFitness(1.0/min);

		}
	}




	public void merge(Population a){
		for(int d = 0; d< a.size();d++){
			add(a.get(d));
		}
	}

	public Population cut_random(int size_of){
		int[] left = new int[populationSize_];
		Population ret = new Population();
		Permutation.randomPermutation(left);

		for(int i = 0;i<size_of;i++){
			ret.add(new Solution (get(left[i])));
		}
		return ret;
	}


	//含む
	public Population cut(int lower,int upper){
		Population ret = new Population(populationSize_);

		for(int i = lower;i<upper+1;i++){
			ret.add(get(i));
		}
		return ret;
	}

	public int getNumberOfDifferentIndividuals( ){
		int a = 1;
		Vector<Solution> d = new Vector<Solution>();

		d.add(population_[0]);
		Solution left,right;

		boolean test = true;
		for(int p =0;p<populationSize_;p++){
			test = true;
			Solution checkInd = population_[p];

			for(int q = 0; q < d.size();q++){
				Solution configind = d.get(q);
				if (Solution.IsEqual(checkInd, configind)){
					test = false;
					break;
				}
			}

			if (test){
				d.add(population_[p]);
			}
		}
		return d.size();
	}


	public int getSurvivalCountOfTransferedSolution(){
		int count = 0;
		for(int p =0 ; p < populationSize_;p++){
			if (population_[p].transferredOffspring){
				count++;
			}
		}
		return count;
	}

	public int size(){
		return populationSize_;
	}

	public void remake(){
		for(int i=0 ;i<populationSize_;i++){
			population_[i].remake();
		}
	}

	public void replace(int key,Solution a){
		population_[key] = a;
	}



	public void printVariablesToFile(String path) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);

			if (size() > 0) {
				int numberOfVariables = population_[0].getNumberOfVariables();

				for(int i = 0 ;i < populationSize_;i++){
					for (int j = 0; j < numberOfVariables; j++){
						bw.write(population_[i].getValue(j) + "	");
					}
					bw.newLine();
				}
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printObjectivesToFile(String path , int key) {

		try {
			FileOutputStream fos = new FileOutputStream(path);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);

			if (size() > 0) {
				int NumberOfObjectives = population_[0].getNumberOfObjectives(key);
				for(int i = 0 ;i < populationSize_;i++){
					for (int j = 0; j < NumberOfObjectives; j++){
						bw.write(population_[i].getObjective(key,j) + " ");
					}
					bw.newLine();

				}
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sortScalarFitness(){
		for(int i = 0 ;i < populationSize_ - 1;i++){
			for(int j = i + 1;j < populationSize_;j++){
				if(population_[i].getScalarFitness() < population_[j].getScalarFitness()){
					Solution a = population_[i];
					population_[i] =  population_[j];
					population_[j] =  a;
				}
			}
		}


	}

	public void printFactorialCostToFile(String path , int key) {

		try {
			FileOutputStream fos = new FileOutputStream(path);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);

			if (size() > 0) {
				int NumberOfObjectives = population_[0].getNumberOfObjectives(key);
				for(int i = 0 ;i < populationSize_;i++){
					for (int j = 0; j < NumberOfObjectives; j++){
						bw.write(population_[i].getFactorialCost(key) + " ");
					}
					bw.newLine();

				}
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}


