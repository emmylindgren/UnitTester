package se.umu.cs.emli.MyUnitTester.View;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class UnitTestView {
    private final JFrame frame;
    private JButton sendInputButton;
    private JTextField inputText;
    private JButton clearButton;
    private JTextArea outputText;

    public UnitTestView(String title){
        this.frame = new JFrame(title);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel inputPanel = this.buildInputPanel();
        JPanel outputPanel = this.buildOutPutPanel();
        JPanel clearingPanel = this.buildClearingPanel();

        this.frame.setMinimumSize(new Dimension(400, 300));
        this.frame.add(inputPanel,BorderLayout.PAGE_START);
        this.frame.add(outputPanel,BorderLayout.CENTER);
        this.frame.add(clearingPanel,BorderLayout.PAGE_END);

        this.frame.pack();
    }

    public void show(){this.frame.setVisible(true);}

    private JPanel buildInputPanel(){
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Input filename here")
                ,new EmptyBorder(5,5,5,5)));
        inputPanel.setLayout(new BorderLayout(5,5));

        inputText = new JTextField();
        sendInputButton = new JButton("Run tests");

        inputPanel.add(inputText, BorderLayout.CENTER);
        inputPanel.add(sendInputButton,BorderLayout.LINE_END);

        return inputPanel;
    }

    private JPanel buildOutPutPanel(){
        JPanel outputPanel = new JPanel();
        outputText = new JTextArea();
        outputText.setEditable(false);
        outputText.setBorder(new EmptyBorder(5,5,5,5));
        outputPanel.setLayout(new BorderLayout());

        JScrollPane outputTextScroll = new JScrollPane(outputText);
        outputPanel.add(outputTextScroll,BorderLayout.CENTER);

        return outputPanel;
    }

    private JPanel buildClearingPanel(){
        JPanel clearingPanel = new JPanel();
        clearButton = new JButton("Clear");

        clearingPanel.add(clearButton);
        return clearingPanel;
    }

    /**
     * Attaching a listener on clearingbutton.
     * @param actionListener to call on when action is performed.
     */
    public void setClearOutPutListener(ActionListener actionListener){
        clearButton.addActionListener(actionListener);
    }

    /**
     * Attaching a listener on sendinputbutton.
     * @param actionListener to call on when action is performed.
     */
    public void setSendInputListener(ActionListener actionListener){
        sendInputButton.addActionListener(actionListener);
    }

    public String getInputValue(){
        return inputText.getText();
    }
    public void clearOutPut(){
        outputText.setText(null);
    }
    public void updateOutPut(String string){ outputText.append(string);}
}
