
#!/usr/bin/env python
# -*- coding: utf-8 -*-
from GetFileName import*
import numpy as np;
import glob
import os
import linecache
def getAVE_and_STD(dir):
    DATAFILES = glob.glob(dir + "\\*.dat");
    test = [];

    for datfile in DATAFILES:
            if("average" in datfile):
                continue;
            a = sum(1 for line in open(datfile))
            data = linecache.getline(datfile,a);
#            data = np.loadtxt(datfile,delimiter="\t");
            #print(data[len(data)-1,1])
            data = data.split("\n")[0].split("\t")[1]
#            print(data);
            test.append(np.float64(data));
    
    return (np.average(test),np.std(test))

if __name__ =="__main__":
    Algorithm = "result\\SOEA";
    line_index = 50000-1 # 取り出したい行番号
    DIRECTORYS = getAllDirectoryName(Algorithm,"BestFUN");

    datalist = [];
    for dir in DIRECTORYS:
#        print(dir);
        ave,std = getAVE_and_STD(dir);    
        print(dir+str(ave) + "   " + str(std))
        if("Island" in  dir ):
            problem = dir.split("\\")[2] + "_" +dir.split("\\")[3] ;
        else:
            problem = dir.split("\\")[1]+ "_" +dir.split("\\")[2];
        eachlist = [problem,ave,std];
        datalist.append(eachlist);
#            print(datfile +"   " + data[len(data)-1,1]);
#        test = np.array(test);
#            print(target_line)
    with open(Algorithm + "\\data.csv","w") as fout:
        for d in datalist:
            for z in d:
                fout.write(str(z));
                fout.write(",");
            fout.write("\n")
    print("end")
    input()