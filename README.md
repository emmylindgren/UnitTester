# UnitTester

A framework for unit-testing built using Java's Swing and Reflection API libraries.

# How to run 

1. Place testclass in the same directory as the testers jar-file
    - Testclasses should implement TestClass-interface found in folder unittest
    - Testclasses should take no parameters 
    - Testclasses can be with or without both setUp and tearDown methods
    - Testclasses must have constructor 
    - All testmethods must be public, start with the name test and take no parameters
2. Start the unittester by running the command `java -jar MyUnitTester.jar ` or doubleclick the jar-file itself. 
3. Input your testclass filename and press run tests. 
4. You will now get printouts from the runs on every testmethod in your testclass. 
