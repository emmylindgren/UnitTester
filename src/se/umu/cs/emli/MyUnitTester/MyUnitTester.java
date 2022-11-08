package se.umu.cs.emli.MyUnitTester;


import se.umu.cs.emli.MyUnitTester.Model.ClassHolder;
import se.umu.cs.emli.MyUnitTester.View.UnitTestView;

import javax.swing.*;

public class MyUnitTester {
    public static void main(String[] args) {

        ClassHolder holder = new ClassHolder("se.umu.cs.emli.MyUnitTester.Test1");
        boolean isValid = holder.isClassValid();
        System.out.println(isValid);
        holder.loadMethods();

        SwingUtilities.invokeLater(() -> {
            UnitTestView view = new UnitTestView("Unit tester");
            view.show();

        });

    }
}
