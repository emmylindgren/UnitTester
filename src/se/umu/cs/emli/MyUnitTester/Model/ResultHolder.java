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

    public int getNrOfSuccess(){return nrOfSuccess;}

    public int getNrOfFail(){return nrOfFail;}
    public int getNrOfExceptions(){return nrOfExceptions;}
    public int getTotalNrOfFails(){return nrOfExceptions + nrOfFail;}
}
