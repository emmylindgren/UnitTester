package se.umu.cs.emli.MyUnitTester;

import se.umu.cs.emli.MyUnitTester.Controller.Controller;
import javax.swing.*;

public class MyUnitTester {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::new);
    }
}
