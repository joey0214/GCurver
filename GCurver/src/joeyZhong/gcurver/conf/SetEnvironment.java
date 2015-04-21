/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.conf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
//import joeyZhong.gcurver.gui.SetEnvironment1;

/**
 *
 * @author xizho3
 */
public class SetEnvironment extends JFrame implements ActionListener
{
//    public static void main(String[] args) {  
//        SetEnvironment f = new SetEnvironment();  
//    }
    
    JLabel label01, label02, label03, label04, label05;
    JTextField text01, text02, text03, text04, text05; 
    JButton choose01 , choose02, choose03, choose04, choose05;
    JButton setButton;

    
    
    public SetEnvironment()
    {
        this.setVisible(true);
        this.setSize(400, 400);
        this.setVisible(true);
        this.setLocation(400, 200);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        label01 = new JLabel("R_HOME"); 
        label02 = new JLabel("PATH "); 
        label03 = new JLabel("JAVA_HOME "); 
        label04 = new JLabel("LD_LIBRARY_PATH "); 
        label05 = new JLabel("java.library.path "); 
        
        text01 = new JTextField("No folder chose");
        text02 = new JTextField("No folder chose");
        text03 = new JTextField("No folder chose");
        text04 = new JTextField("No folder chose");
        text05 = new JTextField("No folder chose");
        
        choose01 = new JButton("Choose"); 
        choose01.addActionListener(this);
        choose02 = new JButton("Choose");
        choose02.addActionListener(this);
        choose03 = new JButton("Choose");
        choose03.addActionListener(this);
        choose04 = new JButton("Choose");
        choose04.addActionListener(this);
        choose05 = new JButton("Choose");
        choose05.addActionListener(this);
        
        setButton = new JButton("Set Environment"); 
        setButton.addActionListener(this);
        
        //set layout strategy
        GroupLayout groupLayout = new GroupLayout(this.getContentPane()); 
        this.getContentPane().setLayout(groupLayout);
        
        GroupLayout.SequentialGroup horGroup = groupLayout.createSequentialGroup(); 
        
        horGroup.addGap(5); 
        
        horGroup.addGroup(groupLayout.createParallelGroup().addComponent(label01)
        .addComponent(text01).addComponent(label02).addComponent(text02)
        .addComponent(label03).addComponent(text03)
        .addComponent(label04).addComponent(text04)
        .addComponent(label05).addComponent(text05)
        .addComponent(setButton)); 
        
        horGroup.addGap(5);
        
        horGroup.addGroup(groupLayout.createParallelGroup().addComponent(choose01)
        .addComponent(choose02).addComponent(choose03).addComponent(choose04)
        .addComponent(choose05)); 
        
        horGroup.addGap(5);
        
        groupLayout.setHorizontalGroup(horGroup);

        GroupLayout.SequentialGroup verGroup = groupLayout.createSequentialGroup(); 
        
        verGroup.addGap(10); 
        verGroup.addGroup(groupLayout.createParallelGroup().addComponent(label01)); 
        verGroup.addGap(10); 
        verGroup.addGroup(groupLayout.createParallelGroup().addComponent(text01).addComponent(choose01));
        verGroup.addGap(10); 
        verGroup.addGroup(groupLayout.createParallelGroup().addComponent(label02));
        verGroup.addGap(10); 
        verGroup.addGroup(groupLayout.createParallelGroup().addComponent(text02).addComponent(choose02));
        verGroup.addGap(10); 
        verGroup.addGroup(groupLayout.createParallelGroup().addComponent(label03));
        verGroup.addGap(10); 
        verGroup.addGroup(groupLayout.createParallelGroup().addComponent(text03).addComponent(choose03));
        verGroup.addGap(10); 
        verGroup.addGroup(groupLayout.createParallelGroup().addComponent(label04));
        verGroup.addGap(10); 
        verGroup.addGroup(groupLayout.createParallelGroup().addComponent(text04).addComponent(choose04));
        verGroup.addGap(10); 
        verGroup.addGroup(groupLayout.createParallelGroup().addComponent(label05));
        verGroup.addGap(10); 
        verGroup.addGroup(groupLayout.createParallelGroup().addComponent(text05).addComponent(choose05));
        verGroup.addGap(10); 
        verGroup.addGroup(groupLayout.createParallelGroup().addComponent(setButton));
        horGroup.addGap(10);
        groupLayout.setVerticalGroup(verGroup);
//        
    }
    
    public void actionPerformed(ActionEvent ace) 
    {
        if (ace.getSource() == setButton)
        {
            
        }
        
        
    }
}
