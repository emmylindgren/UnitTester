package se.umu.cs.emli.MyUnitTester.Tests;

import se.umu.cs.emli.MyUnitTester.Model.ClassHolder;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for classHolder class.
 * @author Emmy Lindgren, id19eln.
 */

class ClassHolderTest {
    @org.junit.jupiter.api.Test
    void correctClassTest() {
        try {
            ClassHolder holder = new ClassHolder("Test1");
            assertTrue(holder.isValid());

        } catch (NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @org.junit.jupiter.api.Test
    void incorrectClassTest() {
        try {
            ClassHolder holder = new ClassHolder("TestWithoutImplements");
            assertFalse(holder.isValid());

        } catch (NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @org.junit.jupiter.api.Test
    void noClassFoundTest() {
        assertThrows(ClassNotFoundException.class, () -> new ClassHolder("testsomintefinns"));
    }

    @org.junit.jupiter.api.Test
    void correctNumberOfTestMethods() {
        int i = 0;
        try {
            ClassHolder holder = new ClassHolder("Test1");
            if(holder.isValid()){
                while(holder.hasTestMethodsToRun()){
                    holder.runNextTestMethod();
                    i++;
                }
            }

        } catch (NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(i,5);
    }

    @org.junit.jupiter.api.Test
    void zeroTestMethods() {
        try {
            ClassHolder holder = new ClassHolder("TestWithoutMethods");
            if(holder.isValid()){
                assertFalse(holder.hasTestMethodsToRun());
            }
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @org.junit.jupiter.api.Test
    void returnsResultTest() {
        try {
            ClassHolder holder = new ClassHolder("Test1");
            if(holder.isValid()){
                while(holder.hasTestMethodsToRun()){
                    holder.runNextTestMethod();
                }
                assertNotEquals(holder.getResults(),"");
            }
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @org.junit.jupiter.api.Test
    void returnsInvalidReason() {
        try {
            ClassHolder holder = new ClassHolder("TestWithoutImplements");
            holder.isValid();
            assertNotEquals(holder.getInvalidReason(),"");

        } catch (NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}