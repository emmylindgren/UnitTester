package se.umu.cs.emli.MyUnitTester.Model;

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

    public String getResultText(){
        StringBuilder builder = new StringBuilder();

        builder.append(System.lineSeparator()).append(System.lineSeparator());

        if(nrOfSuccess != 0){
            builder.append(nrOfSuccess).append(" tests succeeded.").append(System.lineSeparator());
        }
        if(nrOfFail != 0){builder.append(nrOfFail).append(" tests failed.").append(System.lineSeparator());}
        if(nrOfExceptions != 0){builder.append(nrOfExceptions).append(" tests failed because of exceptions");}

        return builder.toString();
    }
}
