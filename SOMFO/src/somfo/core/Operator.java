package somfo.core;



import java.util.HashMap;
import java.util.Map;

import somfo.util.JMException;

public abstract class Operator {
	protected final Map<String, Object> parameters_;

	public Operator(HashMap<String, Object> parameters) {
		parameters_ = parameters;
	}

	abstract public Object execute(Object object) throws JMException;

	public void setParameter(String name, Object value) {
		parameters_.put(name, value);
	}

	public Object getParameter(String name) {
		return parameters_.get(name);
	}


}
