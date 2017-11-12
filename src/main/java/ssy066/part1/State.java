package ssy066.part1;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The State represent a map between the variables and their current value.
 * Variables are represented as Strings and the values can be of any type. Usually
 * the values is of type String, Integer or Boolean.
 *
 * The variables are simplified since they do not have any
 * domain, which mean that they can be updated to anything.
 *
 * A State object should be immutable, i.e. when created it should never change.
 *
 */
public class State {
	private final Map<String, Object> state;

    /**
     * The constructor takes a map defining the values of the variables. The
     * variables are defined as a String and the values can be of any type (mostly
     * String, Integer or Boolean).
     *
     * @param init Each variable
     */


	public State(Map<String, Object> init) {
	    state= new HashMap<>(init);
	}

    /**
     * If the state include the variable var, the method should return its value.
     * If the variable is not part of the state, a java.util.NoSuchElementException
     * should be fired. The method inState can be useful.
     *
     * @param var The variable name
     * @return the value of the variable named var
     */
	public Object get(String var){
        inState(var);
        return state.get(var);
	}

    /**
     * Returns a new State where value of variable var is updated to value. If var is
     * not part of the state, a NoSuchElementException should be fired
     * @param var the name of the variable
     * @param value the newState value
     * @return a new State
     */
	public State newState(String var, Object value) {
        inState(var);
	    Map<String, Object> map = new HashMap<>(state);
        map.put(var, value);
        State newState = new State(map);

	    return newState;

	    /*Map<String, Object> stateMap1 = new HashMap<String, Object>();
        stateMap1.put(var, value);
        State state1 = new State(stateMap1);
        state1.inState(var);
        return state1;
        */
	}

    /**
     * Returns a new State where values of variables in updVars is updated. If any of the
     * variables is not part of the state, a NoSuchElementException should be fired
     * @param updVars The map including only the variable and values to be updated
     * @return a new State
     */
	public State newState(Map<String, Object> updVars) {
        Map<String, Object> newmap = new HashMap<>(state);
        for(String key : updVars.keySet())
        {
            inState(key);

            newmap.put(key, updVars.get(key));

        }
        return new State(newmap);
	}


	

    /**
     * Use this helper method to check if a variable is included in the state
     * If not, The method will throw an exception that should be propagated
     * to the user of the State object
     * @param var The variable name to check
     */
	private void inState(String var) {
		if (!state.containsKey(var))
			throw new java.util.NoSuchElementException("The variable "+var+" is not part of the state");
	}


    @Override
    public String toString() {
        StringBuffer name = new StringBuffer();
        for (Entry<String, Object> kv : state.entrySet()){
            String line = kv.getKey() +":"+ kv.getValue().toString();
            name.append(line + ", ");
        }
        return name.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof State))
            return false;
        State other = (State) obj;
        if (state == null) {
          return false;
        } else if (!state.equals(other.state))
            return false;
        return true;
    }

}
