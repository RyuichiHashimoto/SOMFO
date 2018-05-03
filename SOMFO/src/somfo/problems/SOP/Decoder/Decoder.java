package somfo.problems.SOP.Decoder;

import java.util.HashMap;

public abstract class Decoder{

	protected HashMap<String,Object> parameter;
	
	public Decoder(HashMap<String, Object> argmenParameter){
		parameter = argmenParameter;
	}
		
	public void setParameters(String key, Object value){
		parameter.put(key, value);
	}
	
	public abstract Object decode(double[] variables);
}
