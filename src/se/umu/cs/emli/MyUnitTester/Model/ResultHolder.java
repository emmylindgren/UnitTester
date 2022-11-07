package se.umu.cs.emli.MyUnitTester.Model;

import java.util.HashMap;

public class ResultHolder {
    private int nrOfSuccess;
    private int nrOfFail;
    private HashMap<String, Integer> failedTests;

    public ResultHolder(){
        nrOfSuccess = 0;
        nrOfFail = 0;
        failedTests = new HashMap<>();
    }

    //TODO: Check if this is even correct?
    public void addFailedTest(String exceptionName){
        failedTests.merge(exceptionName,1,Integer::sum);
        nrOfFail ++;
    }

    public void addSuccessTest(){nrOfSuccess ++;}

    public int getNrOfSuccess(){return nrOfSuccess;}

    public int getNrOfFail(){return nrOfFail;}

    public HashMap<String,Integer> getFailedTests(){return failedTests;}

}
