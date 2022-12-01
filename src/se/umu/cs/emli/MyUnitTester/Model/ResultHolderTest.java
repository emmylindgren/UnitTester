package se.umu.cs.emli.MyUnitTester.Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * JUnit test for resultHolder class.
 * @author Emmy Lindgren, id19eln.
 */
class ResultHolderTest {
    private ResultHolder resultHolder;
    private final String baseString = System.lineSeparator() + System.lineSeparator();

    @BeforeEach
    void setUp() {
        resultHolder = new ResultHolder();
    }

    @AfterEach
    void tearDown() {
        resultHolder = null;
    }

    @Test
    void addingFailedTest() {
        resultHolder.addFailedTest();
        resultHolder.addFailedTest();
        assertEquals(resultHolder.getResultText(),baseString +
                "2 tests failed." + System.lineSeparator());
    }

    @Test
    void addingPassedTest() {
        resultHolder.addSuccessTest();
        resultHolder.addSuccessTest();
        resultHolder.addSuccessTest();
        assertEquals(resultHolder.getResultText(),baseString +
                "3 tests succeeded." + System.lineSeparator());
    }

    @Test
    void addingExceptionTest() {
        resultHolder.addException();
        assertEquals(resultHolder.getResultText(),baseString +
                "1 tests failed because of exceptions.");
    }
    @Test
    void addingExceptionFailedAndPassedTest() {
        resultHolder.addException();
        resultHolder.addFailedTest();
        resultHolder.addSuccessTest();
        assertEquals(resultHolder.getResultText(),baseString +
                "1 tests succeeded." +System.lineSeparator() + "1 tests failed."+ System.lineSeparator() +
                "1 tests failed because of exceptions.");
    }

    @Test
    void getResultStringWhenNoResults() {
        resultHolder.addException();
        resultHolder.addFailedTest();
        resultHolder.addSuccessTest();
        assertNotEquals(resultHolder.getResultText(),"");
    }
}