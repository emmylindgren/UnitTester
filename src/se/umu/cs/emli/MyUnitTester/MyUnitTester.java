package se.umu.cs.emli.MyUnitTester;

import se.umu.cs.emli.MyUnitTester.Controller.Controller;
import javax.swing.*;
/**
 * A unittester with GUI for running Testclasses.
 * Testclass should be in same path as jar-file for project, and user inputs
 * testclass-filename to start running tests.
 */
public class MyUnitTester {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::new);
    }
}
