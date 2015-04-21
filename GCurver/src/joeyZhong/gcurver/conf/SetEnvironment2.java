/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.conf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Arrays;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 *
 * @author xizho3
 */
public class SetEnvironment2  implements ActionListener
{
    //compare to SetEnvironment. this SetEnvironment2 is using JComboBox to select path.
    
//    JLabel label01, label02, label03, label04, label05;
    JTextField text01; 
    JButton choose01 ;
    JButton setButton, finishButton;
    JComboBox choiceBox; 
    JFileChooser fileChooser; 
    String selectedPath ; 
    private int selectedIndex; 
    private String[] pathStrings = {"Default", "Default", "Default", "Default", "Default", "Default", "Default", "Default"};; 
    
    public SetEnvironment2()
    {
        JFrame frame = new JFrame("Selecting JComboBox"); 
        frame.setVisible(true);
        frame.setSize(300, 125);
        frame.setVisible(true);
        frame.setLocation(400, 200);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        text01 = new JTextField("No folder chose");
        
        choose01 = new JButton("Choose"); 
        choose01.addActionListener(this);
        
        setButton = new JButton("Set Environment"); 
        setButton.addActionListener(this);
        finishButton = new JButton("Finish"); 
        finishButton.addActionListener(this);
        
        choiceBox = new JComboBox(); 
        choiceBox.setSize(50, 15);
        choiceBox.addItemListener(new ItemListener() 
        {
            
            @Override
            public void itemStateChanged(ItemEvent e) 
            {
                selectedIndex = choiceBox.getSelectedIndex();
                if (selectedIndex ==0 ){}
            }
        });
        choiceBox.addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                selectedIndex = choiceBox.getSelectedIndex();
                if (selectedIndex == 0)
                {}
                
            }
        });
        // add items to JComboBox
        choiceBox.addItem("No select");
        choiceBox.addItem("R_HOME");
        choiceBox.addItem("PATH");
        choiceBox.addItem("JAVA_HOME");
        choiceBox.addItem("LD_LIBRARY_PATH");
        choiceBox.addItem("java.library.path");
        choiceBox.addItem("R_SHARE_DIR");
        choiceBox.addItem("R_INCLUDE_DIR");
        choiceBox.addItem("R_DOC_DIR");
       
        //set layout strategy    
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane()); 
        frame.getContentPane().setLayout(groupLayout);
        
        GroupLayout.SequentialGroup horGroup = groupLayout.createSequentialGroup(); 
        
        horGroup.addGap(5); 
        
        horGroup.addGroup(groupLayout.createParallelGroup().addComponent(choiceBox).addComponent(text01).addComponent(setButton)); 
        horGroup.addGap(5); 
        horGroup.addGroup(groupLayout.createParallelGroup().addComponent(choose01).addComponent(finishButton)); 
        horGroup.addGap(5); 
        
        groupLayout.setHorizontalGroup(horGroup);

        GroupLayout.SequentialGroup verGroup = groupLayout.createSequentialGroup(); 
        verGroup.addGap(5); 
        verGroup.addGroup(groupLayout.createParallelGroup().addComponent(choiceBox).addComponent(choose01)); 
        verGroup.addGap(5); 
        verGroup.addGroup(groupLayout.createParallelGroup().addComponent(text01)); 
        verGroup.addGap(5); 
        verGroup.addGroup(groupLayout.createParallelGroup().addComponent(setButton).addComponent(finishButton)); 
        verGroup.addGap(5); 
        groupLayout.setVerticalGroup(verGroup);
        
  
        frame.setVisible(true); 
    }
    
    public void actionPerformed(ActionEvent ace) 
    {
        if (ace.getSource() == choose01) 
        {
            fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose your path");
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setCurrentDirectory(new File("."));

            int c = fileChooser.showOpenDialog(this.fileChooser);
            if (c != JFileChooser.APPROVE_OPTION) 
            {
                return;
            } 
            else if (c == JFileChooser.APPROVE_OPTION) 
            {
                selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
                System.out.println(selectedPath);
                text01.setText(selectedPath);

            }
        }
        
        if (ace.getSource() == setButton)
        {
            pathStrings[selectedIndex-1] = selectedPath; 
        }
        
        if (ace.getSource() == finishButton)
        {
            System.out.println(Arrays.toString(pathStrings));
            //TODO
            // to set the environment
        }
        
        
    }
}
