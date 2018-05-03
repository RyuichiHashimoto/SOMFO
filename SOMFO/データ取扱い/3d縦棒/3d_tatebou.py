import pandas as pd
import numpy as np
from mpl_toolkits.mplot3d import Axes3D
from matplotlib.font_manager import FontProperties
import matplotlib.pyplot as plt
from subprocess import call
import matplotlib.cm as cm
import os;
import sys;
import seaborn as sns;
def SVG_TO_emf(datafile):
    outputfile= datafile.split(".")[0]+".emf"
    call(["inkscape","-f",datafile,"-e",outputfile]);
    os.remove(datafile);

def makeGraph(DatFile):
    df_list = [];
    if (not os.path.exists(DatFile)):
        print("nothing such a file, " + DatFile);
    
    

    file = pd.ExcelFile(DatFile);
    for sheet in file.sheet_names:
        df_list.append(file.parse(sheet));
    
    df = df_list[0];
    fig = plt.figure()    
    ax=fig.add_subplot(111, projection='3d')
    fig.patch.set_alpha(0.0)
    ax.patch.set_alpha(1.0)
    fp=FontProperties(fname=r'C:\WINDOWS\Fonts\msgothic.ttc', size=5)
    


    xpos,ypos=np.meshgrid(np.arange(len(df.index)), np.arange(len(df.columns)))
    xpos=xpos.flatten()
    ypos=ypos.flatten()
    zpos=np.zeros(len(xpos))
    dx=np.full(len(xpos), 0.5)
    dy=np.full(len(ypos), 0.5)
    dz=df.as_matrix().flatten()
    ax.bar3d(xpos, ypos, zpos, dx, dy, dz)
    ax.set_xlabel(u"渡す個体数", fontproperties=fp)
    ax.set_ylabel(u"渡す間隔(世代)", fontproperties=fp)
    ax.set_zlabel(u"評価値", fontproperties=fp)
    plt.xticks(np.arange(len(df.index)),   df.index, fontproperties=fp)
    plt.yticks(np.arange(len(df.columns)), df.columns, fontproperties=fp)
    plt.axis([-0.5, len(df.index)-0.5, -0.5, len(df.columns)-0.5])
    outputGraph_FILE = "Graph";
    outputGraph_FILE = outputGraph_FILE + "/"+DatFile.split("/")[1].split(".")[0];
    outputGraph_FILE = outputGraph_FILE + ".svg"
    #outputGraph_FILE = DatFile.split("\\")[0];
#    for i in DatFile.split(".")[0].split("\\")[1:]:
 #       outputGraph_FILE = outputGraph_FILE+"\\"+i;
    print(outputGraph_FILE);
#    outputGraph_FILE = outputGraph_FILE+".svg";
    plt.savefig(outputGraph_FILE)
    SVG_TO_emf(outputGraph_FILE)


if __name__ == "__main__":
    if (not os.path.exists("Graph")):
        os.mkdir("Graph");
    Algorithm = "Re_Island";


    if len(sys.argv) == 2:
        Algorithm = sys.argv[1];
    PROBLEM = ["CIHS","CIMS","CILS","PIHS","PIMS","PILS","NIHS","NIMS","NILS"]
    TASK = ["TASK1","TASK2"];
    books = [];
    for p in PROBLEM:
        for t in TASK:
            books.append(Algorithm+"/"+p+"_"+t+".xlsx");


    for b in books:
        print(b);
        makeGraph(b)

    
#    print(df_list[0]);
    print("end");
    input();