package somfo.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Setting {
	
	static void experiment_setting(HashMap a,String name){

		try(BufferedReader br = new BufferedReader(new FileReader(name))){
		String[] S;
		String[] na;
		String line;
		int counter=0;
		Object[] emp;
		while(( line = br.readLine())!= null){
			S  = line.split(":");
			na = S[1].split(" ");
			emp = new Object[S.length - 1];

			System.out.print(S[0] + " ");
			for(int i = 1;i < S.length;i++ ){
				emp[i] = na[i];
				System.out.print(S[i] + " ");
			}
			System.out.print("\n");
			a.put(S[0],emp);
		}

		} catch (IOException e){
			e.printStackTrace();
		}		
	}
	
}
