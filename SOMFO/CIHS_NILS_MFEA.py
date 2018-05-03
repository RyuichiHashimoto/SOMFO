import subprocess;
import multiprocessing as mp;
import sys;

def function(name):
	subprocess.call(name);

def multi(list):
	p = mp.Pool(mp.cpu_count());
	p.map(function,list);
	p.close();


def getGen(problem, obj):
	gen = 0;
	if (problem.startswith("WFG")):
		if(obj == str(3)):
			gen = 400;
		elif (obj == str(5)):
			gen = 750;
		elif (obj == str(8)):
			gen = 1500;
		elif (obj == str(10)):
			gen = 2000;
	elif(problem == "DTLZ1"):
		if(obj == str(3)):
			gen = 400;
		elif (obj == str(5)):
			gen = 600;
		elif (obj == str(8)):
			gen = 750;
		elif (obj == str(10)):
			gen = 1000;
	elif(problem == "DTLZ2"):
		if(obj == str(3)):
			gen = 250;
		elif (obj == str(5)):
			gen = 350;
		elif (obj == str(8)):
			gen = 500;
		elif (obj == str(10)):
			gen = 750;
	elif(problem == "DTLZ3"):
		if(obj == str(3)):
			gen = 1000;
		elif (obj == str(5)):
			gen = 1000;
		elif (obj == str(8)):
			gen = 1000;
		elif (obj == str(10)):
			gen = 1500;
	elif(problem == "DTLZ4"):
		if(obj == str(3)):
			gen = 600;
		elif (obj == str(5)):
			gen = 1000;
		elif (obj == str(8)):
			gen = 1250;
		elif (obj == str(10)):
			gen = 2000;



	return str(gen)

if __name__ == "__main__":
	
	PROBLEMS = ["CIHS","CIMS","CILS","PIHS","PIMS","PILS","NIHS","NIMS","NILS"];
	ALGORITHM = ["MFEA"];
	list = [];
	for algo in ALGORITHM:
		for p in PROBLEMS:
			list.append(["SOMFO.bat","-ga", algo,"-p" ,p]);



	multi(list);
	
