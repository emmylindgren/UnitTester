package se.umu.cs.emli.MyUnitTester;

import se.umu.cs.unittest.TestClass;

/**
 * Testing
 * - private testmethods does not break system.
 * - Testmethods with parameters does not run.
 * - Testmethods without setup does not break system.
 *
 */
public class Test2 implements TestClass {
    private MyInt myInt;

    public Test2() {
    }


    public void tearDown() {
    }

    public boolean testwithfalse() {
        return false;
    }

    public boolean testwithParams(String hej){
        return true;
    }

    private boolean testThatShouldNotrun(){
        return true;
    }
}
