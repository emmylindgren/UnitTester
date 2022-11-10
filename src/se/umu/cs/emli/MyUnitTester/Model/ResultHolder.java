package se.umu.cs.emli.MyUnitTester.Model;

/**
 * Class to hold the results of a test-run.
 */
public class ResultHolder {
    private int nrOfSuccess;
    private int nrOfFail;
    private int nrOfExceptions;

    public ResultHolder(){
        nrOfSuccess = 0;
        nrOfFail = 0;
        nrOfExceptions = 0;
    }

    public void addFailedTest(){nrOfFail ++;}

    public void addException(){nrOfExceptions ++;}

    public void addSuccessTest(){nrOfSuccess ++;}

    /**
     * Builds a string with the final results of the test-run.
     * Contains information about number of successfull tests, failed tests and
     * number of failed tests because of exceptions (if there are any).
     * @return string containing final results.
     */
    public String getResultText(){
        StringBuilder builder = new StringBuilder();

        if(nrOfExceptions == 0 && nrOfFail == 0 && nrOfSuccess == 0){
            return "Class had no test-methods to run.";
        }

        builder.append(System.lineSeparator()).append(System.lineSeparator());

        if(nrOfSuccess != 0){
            builder.append(nrOfSuccess).append(" tests succeeded.").append(System.lineSeparator());
        }
        if(nrOfFail != 0){builder.append(nrOfFail).append(" tests failed.").append(System.lineSeparator());}
        if(nrOfExceptions != 0){builder.append(nrOfExceptions).append(" tests failed because of exceptions");}

        return builder.toString();
    }
}