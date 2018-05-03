import subprocess
import numpy as np
import matplotlib.pyplot as plt
import matplotlib as mpl
import os
import glob
import shutil

def getAllFileName(str,target):
	return glob.iglob(str+'/**/' + target, recursive=True);


if __name__ == "__main__":
    Algorithm = "Island"
    outputdir = "Paper";
    FILES = getAllFileName(Algorithm + "\\every1amount5","*.result");
    if (not os.path.exists(outputdir)):
        os.mkdir(outputdir)
    for file in FILES:
            problems = file.split("\\")[3]
            tasks = file.split("\\")[4].split(".")[0];
            print(problems)
            print(tasks);
            
            
            tasks_filename = "file";
            
            if(tasks=="task1"):
            	tasks_filename = "Task1";
            if(tasks=="task2"):
            	tasks_filename = "Task2";
			
            if (problems == "30A30R_HS"):
            	problems = "30A30R_LS"
            elif (problems == "30A30R_LS"):
            	problems = "30A30R_SS"
            
            if (not os.path.exists(outputdir + "\\"+problems)):
                os.mkdir(outputdir + "\\"+problems)
            if (not os.path.exists(outputdir + "\\"+problems+"\\"+tasks_filename)):
                os.mkdir(outputdir + "\\"+problems+"\\"+tasks_filename)
            
            outputfile = Algorithm  + "_"+tasks +".dat";
            print(outputfile)
            shutil.copy2(file,outputdir + "\\"+problems+"\\"+tasks_filename + "\\"+outputfile)

            

    print("end")
    input()




