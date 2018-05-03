from GetFileName import*
import numpy as np;
import glob
import os
def getAverage(targetdir:str):
    FILES = glob.glob(targetdir+"/*.dat");
    average = [];
    counter = 0;
    if (len(FILES)!=0):    
	    for file in FILES:
	            counter=counter+1;
	            Data = np.loadtxt(file);
	            if counter == 1:
	                average = Data;
	            else:
	                average += Data;
	    average = average/counter;
    else:
    	average = np.array([0,1]);
	
    return average;

#not implement
def Tolog(targetfile:str,demiliter:str):
    data = np.loadtxt(targetfile);
    data[:,1] = np.log(data[:,1]);
    d = targetfile.split("\\");
 #   print(d[-2])
    outputfilename="";
    for i in range(0,len(d)-1):
        outputfilename += d[i]+"\\";
        
    outputfilename +="\\log_" +d[-1];
    np.savetxt(outputfilename,data);
#extract data of the last row of the file an
def getMinimumValue(file:str,index:int,demiliter:str):
    with open(file,"r") as fin:
        data = fin.readlines();
        row = data[len(data)-1];
        d = row.split(demiliter);
        if (index-1 >= len(d)):
            return 999999999999999;
        else:
            return d[index-1];
    return -999999999999999
# arrange the final value of the search
def arrangeLastValue(algorithmname:str,index:int,demiliter:str):
    print("start") 
    FILES = getAllFileName(algorithmname,"BestFUN")
    #FILES = getALLFileName(algorithmname);
    for file in FILES:
        print(file)   
#   glob.iglob(dir+'/'+algorithmname+'/**/' + target, recursive=True)

def SubscriptAverage(dir,targetdir):
    FILES = getAllFileName(dir,"average.dat");
    for file in FILES:
        os.remove(file);
    FILES_ = getAllFileName(dir,"log_average.dat");
    for file in FILES_:
        os.remove(file);

    DIRECTORY = getAllFileName(dir,targetdir);
    for dir in DIRECTORY:
        print("FUN");
        ret = getAverage(dir);
        np.savetxt(dir + "\\average.dat",ret);
    

if __name__ =="__main__":
    print("test");
    arrangeLastValue("Island",1,"\t");
    input()
    