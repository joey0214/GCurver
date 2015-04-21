/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.border.Border;

/**
 *
 * @author xizho3
 */
public class ProgressBar extends JPanel implements  ActionListener, PropertyChangeListener
{
    private JProgressBar progressBar;
    private JButton startButton;
    private JTextArea taskOutput;
//    private Task task;
    public ProgressBar() 
    {
        JFrame frame = new JFrame("Drawing plot"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JProgressBar proBar = new JProgressBar(); 
//        proBar.setValue(25);
        proBar.setStringPainted(true);
        
        Border border = BorderFactory.createTitledBorder("Drawing plot ......"); 
        proBar.setBorder(border);
        
        frame.getContentPane().add(proBar, BorderLayout.NORTH); 
        frame.setSize(300, 100);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) 
    {
        
    }

   
    
}
