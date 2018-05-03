import subprocess;
import multiprocessing as mp;
import sys;
from getFileName import *
import numpy as np;


def getFinalAverage(dir,targetdir:str):
    FILES = getAllFileName(dir,targetdir);

    for file in FILES:
        Data = np.loadtxt(file);
        print(Data);



if __name__ =="__main__":
    getFinalAverage("result","BestFUN")


    input();