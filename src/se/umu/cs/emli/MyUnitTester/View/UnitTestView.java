package se.umu.cs.emli.MyUnitTester.View;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UnitTestView {
    private JFrame frame;
    private JButton sendInputButton;
    private JButton clearButton;

    public UnitTestView(String title){
        this.frame = new JFrame(title);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel inputPanel = this.buildInputPanel();
        JPanel outputPanel = this.buildOutPutPanel();
        JPanel clearingPanel = this.buildClearingPanel();

        this.frame.setMinimumSize(new Dimension(400, 300));
        this.frame.add(inputPanel,BorderLayout.PAGE_START);
        this.frame.add(outputPanel,BorderLayout.LINE_START);
        this.frame.add(clearingPanel,BorderLayout.PAGE_END);

        this.frame.pack();

    }

    public void show(){this.frame.setVisible(true);}

    private JPanel buildInputPanel(){
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Input filename here")
                ,new EmptyBorder(5,5,5,5)));
        inputPanel.setLayout(new BorderLayout(5,5));

        JTextField inputText = new JTextField();
        sendInputButton = new JButton("Run tests");
        //sendInputButton.addActionListener(); ?? vad göra här?
        inputPanel.add(inputText, BorderLayout.CENTER);
        inputPanel.add(sendInputButton,BorderLayout.LINE_END);

        return inputPanel;
    }

    private JPanel buildOutPutPanel(){
        JPanel outputPanel = new JPanel();
        JTextArea outputText = new JTextArea();
        outputText.setEditable(false);

        outputPanel.add(outputText);

        return outputPanel;
    }

    private JPanel buildClearingPanel(){
        JPanel clearingPanel = new JPanel();
        clearButton = new JButton("Clear");
        //clearButton.addActionListener();
        clearingPanel.add(clearButton);

        return clearingPanel;
    }
}
