package ssy066.part2;

import junit.framework.TestCase;
import ssy066.part1.*;

import java.util.*;

/**
 * Finally, we have all the pieces ready! In this test you will create a class that
 * creates and represent a graph. It requires some more of you since you have to
 * implement your own Graph class. Also the graph making
 * algorithm is a little bit harder then in part 1.
 *
 * Read about graphs in:
 *
 * http://en.wikipedia.org/wiki/Directed_graph
 * http://en.wikipedia.org/wiki/Graph_(mathematics)
 *
 *
 * @author Kristofer
 */
public class Test3_Graphs extends TestCase {

    // these are used in the tests and are initialized in the setUp() method. You do no not
    // need to change it.
    private String v1 = "v1";
    private String v2 = "v2";
    private String v3 = "v3";
    private Map<String, Object> initial;
    private State testState;

    private Predicate v1Eq0;
    private Predicate v1Eq1;
    private Predicate v1Eq2;
    private Predicate v1Eq2AndFalse;
    private Predicate v1Eq2AndTrue;

    private Action v1Inc;
    private Action v1Dec;
    private Action v1To0;

    private Operation o1;
    private Operation o2;
    private Operation o3;
    private Operation o4;
    private Operation o5;

    private Set<Operation> operations;
    private Set<Operation> o1o2;

    private Graph g;


    /**
     * This is the setup method for the variables that are used. This is run before each test.
     */
    protected void setUp() {

        v1Eq0 = new EQ(v1, 0);
        v1Eq1 = new EQ(v1, 1);
        v1Eq2 = new EQ(v1, 2);
        v1Eq2AndFalse = new AND(new NOT(new EQ(v2, true)), v1Eq2);
        v1Eq2AndTrue = new AND(new EQ(v2, true), v1Eq2);


        v1Inc = new NextInc(v1, 1);
        v1Dec = new NextDec(v1, 1);
        v1To0 = new Next(v1, 0);

        o1 = new Operation("o1", v1Eq0, v1Inc);
        o2 = new Operation("o2", v1Eq1, v1Inc);
        o3 = new Operation("o3", new OR(v1Eq1, v1Eq2AndTrue), Arrays.asList(v1To0, new Next(v2, false)));
        o4 = new Operation("o4", v1Eq2, v1Inc);
        o5 = new Operation("o5", v1Eq2AndFalse, new Next(v2, true));

        initial = new HashMap<String, Object>();
        initial.put(v1, 0);
        initial.put(v2, false);
        initial.put(v3, "Initial");
        testState = new State(initial);

        operations = new HashSet<Operation>();
        operations.add(o1);
        operations.add(o2);
        operations.add(o3);
        operations.add(o4);
        operations.add(o5);

        o1o2 = new HashSet<Operation>();
        o1o2.add(o1);
        o1o2.add(o2);
    }


// *** TEST start below

    /**
     * Given a set of operations and a state, return the operations that are enabled, i.e. their precondition is true
     *
     * In this test you only need to implement the static method enabledOperations in GraphFactory.java. This
     * method should be used by your methods later. It is static since I wanted to test this function before you start
     * creating the graph creating algorithm.
     *
     * Since i'm testing for equality on a set, you need to return a HashSet from the method. I have also added a lot of
     * extra methods to our old classes to handle equality and hashcode. That is required when testing for equality. You
     * probably noticed all the code in part1.
     */
    public void test1_enabledOperations() {
        State init = testState;
        State instep2 = init.newState(v1, 1);
        Set<Operation> ops = GraphFactory.enabledOperations(operations, init);
        Set<Operation> ops2 = GraphFactory.enabledOperations(operations, instep2);

        Set<Operation> compareSet = new HashSet<Operation>();
        compareSet.add(o1);
        Set<Operation> compareSet2 = new HashSet<Operation>();
        compareSet2.add(o2);
        compareSet2.add(o3);

        assertTrue(ops.equals(compareSet));
        assertTrue(ops2.equals(compareSet2));
    }

    /**
     * Given a set of operation and a state, calculate all possible transitions from that state. The Transition
     * class is given and include the tail state (where the transition is going from) and the head state
     * (the target of the transition). The Transition.java defines how transition looks.
     *
     * Use the operation name as label.
     *
     * Use enabledOperations method in this method
     */
    public void test2_makeTransitions() {
        State init = testState;
        State instep2 = init.newState(v1, 1);

        Set<Transition> trans1 = GraphFactory.makeTransitions(operations, init);
        Set<Transition> trans2 = GraphFactory.makeTransitions(operations, instep2);

        Set<Transition> compareTrans1 = new HashSet<Transition>();
        compareTrans1.add(new Transition("o1", init, instep2, 1));

        Set<Transition> compareTrans2 = new HashSet<Transition>();
        compareTrans2.add(new Transition("o2", instep2, testState.newState(v1, 2), 1));
        compareTrans2.add(new Transition("o3", instep2, init, 1));

        assertTrue(trans1.equals(compareTrans1));
        assertTrue(trans2.equals(compareTrans2));
    }


    /**
     * Here you will implement the algorithm that creates a Graph. Start by creating a new class that implements the
     * Graph interface and all its methods. You can usually generate empty methods in your IDE. Search for
     * "generate java code from interface eclipse (or intellij)" in google.
     *
     * Your graph is now created in the method GraphFactory.makeMeAGraph.
     *
     * The Graph includes a set of states and a set of transitions. So the method makeMeAGraph should create all
     * transitions and states in the graph and then call the Graph constructor. You have to decide for your self
     * what to send to the graph via the constructor. You can even create extra classes to be used by your graph
     * It is possible to make it with just State and Transition, but if you want to represent the graph in another
     * way, you are free to do so.
     *
     * To make this test pass you have to implement getStates in the Graph class and the constructor.
     *
     * This algorithm is more demanding then what you did in part 1. So divide your code into multiple methods
     * to make it easier. Also write your own tests for those methods. Observe that you method in GraphFactory needs
     * to be public static.
     *
     * The graph in this test are created based on the operation o1 and o2. These are
     * defined at the top of this testfile. We also use testState as initial state. The graph should include,
     * after construction, three states (v1 = 0, v1 = 1 and v1 = 2, the rest of variables have initial value) and
     * two transitions with label "o1" and "o2"
     *
     * Another possibility that I use a lot, is System.out.println(). Use it everytime you need troubleshooting.
     *
     * Also try debugging. Add a line break and run the test in debug-mode and step through
     * your code, line by line.
     */
    public void test3_implementAGraph() {
        g = GraphFactory.makeMeAGraph(o1o2, testState);
        Set<State> result = g.getStates();


        // below is created to test the result.
        Set<State> compare = new HashSet<State>();
        compare.add(testState);
        compare.add(testState.newState(v1, 1));
        compare.add(testState.newState(v1, 2));
        assertTrue(result.equals(compare));
    }

    /**
     * Implement the getTransition in Graph. Uses a graph based on o1 and o2 and testState
     *
     */
    public void test4_graphTransitions() {
        g = GraphFactory.makeMeAGraph(o1o2, testState);
        Set<Transition> result = g.getTransitions();

        // below is created to test the result.
        Set<Transition> compare = new HashSet<Transition>();
        compare.add(new Transition("o1", (testState), (testState.newState(v1, 1)), 1));
        compare.add(new Transition("o2", (testState.newState(v1, 1)), (testState.newState(v1, 2)), 1));

        assertTrue(result.equals(compare));
    }

    /**
     * Continue from above. Just return the initial state.
     */
    public void test5_getInitalNode() {
        g = GraphFactory.makeMeAGraph(o1o2, testState);

        assertTrue(g.getInitalState().equals((testState)));
    }

    /**
     * Continue from above. Create a graph based on o1 and o2 and testState and
     * implement getOutGoingTransitions. Here we will send
     * in a state and the result will be all outgoing transitions from that state.
     */
    public void test6_graphOutGoingTransitions() {
        g = GraphFactory.makeMeAGraph(o1o2, testState);

        Set<Transition> result = g.getOutGoingTransitions((testState));

        Set<Transition> compare = new HashSet<Transition>();
        compare.add(new Transition("o1", (testState), (testState.newState(v1, 1)), 1));

        assertTrue(result.equals(compare));
    }

    /**
     * Continue from above. Create a graph based on o1 and o2 and testState and
     * implement getIncomingTransition. Here we will send
     * in a state and the result will be all incoming transitions from that state.
     */
    public void test7_graphIncomingTransitions() {
        g = GraphFactory.makeMeAGraph(o1o2, testState);

        Set<Transition> result = g.getIncomingTransitions((testState.newState(v1, 1)));

        Set<Transition> compare = new HashSet<Transition>();
        compare.add(new Transition("o1", (testState), (testState.newState(v1, 1)), 1));

        assertTrue(result.equals(compare));
    }

    /**
     * Continue from above. Create a graph based on o1 and o2 and testState and
     * implement getSuccStates. Should return all states that can reach the given state
     */
    public void test8_graphGetSuccStates() {
        g = GraphFactory.makeMeAGraph(o1o2, testState);


        Set<State> result = g.getSuccStates((testState));

        Set<State> compare = new HashSet<State>();
        compare.add((testState.newState(v1, 1)));
        assertTrue(result.equals(compare));
    }

    /**
     * Continue from above. Create a graph based on o1 and o2 and testState and
     * implement getPredStates. Should return all states that you can reach from the given state
     */
    public void test9_graphGetPredStates() {
        g = GraphFactory.makeMeAGraph(o1o2, testState);


        Set<State> result = g.getPredStates((testState.newState(v1, 1)));

        Set<State> compare = new HashSet<State>();
        compare.add((testState));
        assertTrue(result.equals(compare));
    }


    /**
     * Continue from above. Create a graph based on o1 and o2 and testState and
     * implement getSourceStates
     * . A source state is a state with only outgoing transitions
     */
    public void test10_graphGetSourceStates() {
        g = GraphFactory.makeMeAGraph(o1o2, testState);

        Set<State> result = g.getSourceStates();

        Set<State> compare = new HashSet<State>();
        compare.add((testState));
        assertTrue(result.equals(compare));
    }

    /**
     * Continue from above. Create a graph based on o1 and o2 and testState and
     * implement getSinkStates
     * . A sink state is a state with only incoming transitions.
     */
    public void test11_getSinkStates() {
        g = GraphFactory.makeMeAGraph(o1o2, testState);

        Set<State> result = g.getSinkStates();

        Set<State> compare = new HashSet<State>();
        compare.add((testState.newState(v1, 2)));
        assertTrue(result.equals(compare));
    }



    /**
     * Continue from above. Create a graph based on o1 and o2 and testState and
     * implement test11_getStateFromSequence
     *
     * Given a sequence of labels, like {"o1", "o2"}, should return the state you reach
     * by following these transitions. In this case state (v1=2, v2=false v3=Initial)
     *
     * If the sequence can not be followed, return null
     */
    public void test11_getStateFromSequence() {
        g = GraphFactory.makeMeAGraph(o1o2, testState);

        State result1 = g.getStateFromSequence(Arrays.asList("o1"));
        State result2 = g.getStateFromSequence(Arrays.asList("o1", "o2"));
        State result3 = g.getStateFromSequence(Arrays.asList("o2", "o2"));

        State one = testState;
        State two = o1.execute(one);
        State three = o2.execute(two);

        assertTrue(result1.equals(two));
        assertTrue(result2.equals(three));
        assertTrue(result3 == null);
    }


    /**
     * The final test! You need to create a graph based on all five operations,
     * o1, o2, o3, o4, and o5. The testState is the initial state. I'm just testing
     * some of the functions here, but you can add more assertions if you want to
     * cover all your methods. This should just pass if everything is correct!!
     *
     * If it fails, click on the test in the lestlog, and it will show you what line that failed. You
     * can also add printouts between the asserts below.
     *
     */
    public void test12_finalGraphTest() {
        // This should not be the same graph as the other tests! So create it here
        Graph result = GraphFactory.makeMeAGraph(operations, testState);

        Set<State> sourceStates = result.getSourceStates();
        Set<State> sinkstates = result.getSinkStates();
        State temp = (testState.newState(v2, true).newState(v1, 2));
        Set<Transition> intrans = result.getIncomingTransitions(temp);
        Set<Transition> outtrans = result.getOutGoingTransitions(temp);
        int noOfStates = result.getStates().size();
        int noOfTrans = result.getTransitions().size();
        State result2 = result.getStateFromSequence(Arrays.asList("o1", "o3", "o1", "o2"));


        // comparators
        Set<State> cSourceN = new HashSet<State>();
        Set<State> cSinkN = new HashSet<State>();
        cSinkN.add((testState.newState(v1, 3)));
        cSinkN.add((temp.newState(v1, 3)));
        Set<Transition> cInT = new HashSet<Transition>();
        cInT.add(new Transition("o5", (testState.newState(v1, 2)), temp, 1));
        Set<Transition> cOutT = new HashSet<Transition>();
        cOutT.add(new Transition("o3", temp, result.getInitalState(), 1));
        cOutT.add(new Transition("o4", temp, (temp.newState(v1, 3)), 1));
        State someState = o2.execute(o1.execute(testState));

        System.out.println(outtrans);
        System.out.println(cOutT);

        assertTrue(sourceStates.equals(cSourceN));
        assertTrue(sinkstates.equals(cSinkN));
        assertTrue(intrans.equals(cInT));
        assertTrue(outtrans.equals(cOutT));
        assertTrue(noOfStates == 6);
        assertTrue(noOfTrans == 7);
        assertTrue(result2.equals(someState));


    }


}
