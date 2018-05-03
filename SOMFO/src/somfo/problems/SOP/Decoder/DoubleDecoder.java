package somfo.problems.SOP.Decoder;

import java.util.HashMap;

public class DoubleDecoder extends Decoder{

	public DoubleDecoder(HashMap<String, Object> argmentParameter) {
		super(argmentParameter);
	}

	@Override
	public Object decode(double[] variables) {
		double[] upperLimit =  (double[]) parameter.get("upperLimit");
		double[] lowerLimit =  (double[]) parameter.get("lowerLimit");

		double[] ret = new double[variables.length];

		for(int i = 0; i < ret.length;i++){
			ret[i] = (upperLimit[i]-lowerLimit[i])*variables[i] + lowerLimit[i];
		}
		return ret;
	}
}
