#coding: UTF-8

import sys
import os;
from toolbox.ErrorMassage import *
class CommandLineSetting:
	"""A Command Line Analizer"""
	
	HashMap = {};
	
	def __init__(self,CommandLine):
		list = [];
		list.clear(); 
		for index in range(0,len(CommandLine)):
			str = CommandLine[index];
			if((str.startswith("-")) and (not index == len(CommandLine)-1)):
				key = str[1:];
				self.add(key,CommandLine[index+1]);
			else:
				list.append(str);
	
	def clear(self):
		self.HashMap.clear();
	
	
	def get(self,str):
		if self.containKey(str):
			ret = self.HashMap[str];
		else:
			print ("the key " + str + " is not found, so I crash this program");
			print ("push any key to finish this program");
			input();
			sys.exit() 
		return ret;
		
	def containKey(self, key):
		return key in self.HashMap; 
	
	def add(self,str, obj):
		if (not self.containKey(str)):
			self.HashMap[str] = obj
	
	
	
	def getAsInt(self,str):
		ret = self.get(str);
		return int(ret);
	
	def getAsFloat(self,str):
		ret = self.get(str);
		return float(ret);
	
	def getAsStr(self,str):
		ret = self.get(str);
		return ret;
	def getAsBool(self,str):
		ret = self.get(str);
		if (isinstance(ret,bool)):
			return ret;
		elif(isinstance(ret,type("d"))):
			return ret.upper() == "TRUE" or ret.upper() == "MAX";
		else:
			print("type of ret is ",type(ret));
			ErrorMassage("getAsBool is wrong");
	#return not Command


	def getCommand(self,CommandLine):
		list = [];
		list.clear(); 
		for index in range(0,len(CommandLine)):
			str = CommandLine[index];
			if((str.startswith("-")) and (not index == len(CommandLine)-1)):
				key = str[1:];
				self.add(key,CommandLine[index+1]);
			else:
				list.append(str);
		
		return list;
	
if __name__ == "__main__":
	print("test");
	file = "test";
	print(type(file))
		
	input();