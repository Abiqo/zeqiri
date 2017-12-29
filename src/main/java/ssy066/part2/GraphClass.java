package ssy066.part2;

import ssy066.part1.State;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class GraphClass implements Graph{

    private HashSet<State> GraphStates;
    private HashSet<Transition> GraphTransitions;
    private State init;


    public GraphClass(State init, HashSet<State> states, HashSet<Transition> transitions) {
        this.init = init;
        this.GraphStates = states;
        this.GraphTransitions = transitions;
    }



    @Override
    public HashSet<State> getStates() {
        return GraphStates;
    }

    /**
     * @return all transitions of this graph
     */
    @Override
    public HashSet<Transition> getTransitions() {
        return GraphTransitions;
    }

    /**
     * @return the initial state of this graph
     */
    @Override
    public State getInitalState() {
        return init;
    }

    /**
     * Given a state, returns all outgoing transitions from this state, i.e. all transition where
     * the state is a tail.
     *
     * @param s The state
     * @return All outgoing transitions
     */
    @Override
    public HashSet<Transition> getOutGoingTransitions(State s) {
        HashSet<Transition> transitions = new HashSet<>();
        for(Transition t: GraphTransitions){
            if(t.tail.equals(s)){
                transitions.add(t);
            }
        }
        return transitions;
    }

    /**
     * Given a state, return all transitions coming into that state, i.e. all transitions where
     * the state is head
     *
     * @param s the state
     * @return a set including the incoming transitions
     */
    @Override
    public HashSet<Transition> getIncomingTransitions(State s) {
        HashSet<Transition> transitions = new HashSet<>();
        for(Transition t: GraphTransitions){
            if(t.head.equals(s)){
                transitions.add(t);
            }
        }
        return transitions;
    }

    /**
     * Returns all successor states that is reachable from state state using one transition. All states that are heads where
     * state state is tail
     *
     * @param s the state
     * @return all successor state
     */
    @Override
    public HashSet<State> getSuccStates(State s) {
        HashSet<State> succStates = new HashSet<>();
        HashSet<Transition> transitions = getOutGoingTransitions(s);
        for(Transition t: transitions){
            succStates.add(t.head);
        }
        return succStates;

    }

    /**
     * Returns all predecessor states that can reach state state using one transition. All states that are tails where
     * state state is head
     *
     * @param s the state
     * @return all predecessor state
     */
    @Override
    public HashSet<State> getPredStates(State s) {
        HashSet<State> succStates = new HashSet<>();
        HashSet<Transition> transitions = getIncomingTransitions(s);
        for(Transition t: transitions){
            succStates.add(t.tail);
        }
        return succStates;
    }

    /**
     * Returns all states that does not have any incoming transitions
     *
     * @return A set of states
     */
    @Override
    public HashSet<State> getSourceStates() {
        HashSet<State> sourceStates = new HashSet<>();
        HashSet<Transition> transitions = new HashSet<>();
        for(State s: GraphStates){
            if(getIncomingTransitions(s).isEmpty()){
                sourceStates.add(s);
            }
        }
        return sourceStates;
    }

    /**
     * Returns all states that does not have any outgoing transitions
     *
     * @return A set of states
     */
    @Override
    public HashSet<State> getSinkStates() {
        HashSet<State> sinkStates = new HashSet<>();
        HashSet<Transition> transitions = new HashSet<>();
        for(State s: GraphStates){
            if(getOutGoingTransitions(s).isEmpty()){
                sinkStates.add(s);
            }
        }
        return sinkStates;
    }

    /**
     * Given a sequence of transition labels, return the state you reach when following these states. If the sequence
     * can not be followed, return null.
     *
     * @param sequence The given sequence
     * @return the reached state, or null if nothing is reached.
     */
    @Override
    public State getStateFromSequence(List<String> sequence) {
        State currentState = init;
        HashSet<Transition> transitions;

        for(int i = 0; i<sequence.size(); i++){
            transitions = getOutGoingTransitions(currentState);
            for(Transition t: transitions){
                if(t.label.equals(sequence.get(i))){
                    currentState = t.head;
                }
            }
        }
        if(!currentState.equals(init)) return currentState;

        else return null;

    }
}
