package somfo.problems.SOP.Decoder;

import java.util.HashMap;


/*
 * 各遺伝子を均等に分割して各ビットを割り当てる，
 *
 *
 *
 */

public class IntDecoder extends Decoder {

	public IntDecoder(HashMap<String, Object> argmenParameter) {
		super(argmenParameter);
	}

	@Override
	public Object decode(double[] variables) {
		int[] division = (int[] )parameter.get("division"); //各遺伝子の分割数．
		int[] ret = new int[division.length];

		for(int val  = 0; val < division.length;val++){
			double eachLength = 1.0/(division[val]);
			ret[val] = (int) (variables[val]/eachLength);

			if(ret[val]== division[val]){
				ret[val]--;
			}
		}
		return ret;
	}

	public static void main(String args[]){

		final int SIZE = 11;
		double[] variables = new double[SIZE];
		int[] division = new int[SIZE];

		for(int val = 0; val < SIZE; val++){
			variables[val] = (double)val/(SIZE-1);
//			variables[val] = 0.33;

			division[val] = 3;
		}

		HashMap<String,Object> param = new HashMap<String,Object>();

		param.put("division", division);

		IntDecoder intdecoder = new IntDecoder(param);

		int[] decodedVariables = (int[]) intdecoder.decode(variables);

		for(int val = 0; val < SIZE; val++){
			System.out.println(String.valueOf(decodedVariables[val])+"	"+String.valueOf(variables[val]));
		}
	}

}
