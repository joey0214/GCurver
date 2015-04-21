/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.gui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JTextArea;

/**
 *
 * @author xizho3
 */
class AboutDialog extends JDialog
{
    public AboutDialog(JFrame  parent) throws MalformedURLException
    {
        super(parent, "About Dialog", true);
        
        Box box = Box.createVerticalBox();
        URL myurl = new URL("https://github.com/joey0214/GCurver");
        String message = "This prigram is drawing growth curve using data exported from Bioscreen."
                + "\n" + "You can find latest version at here:\n\t" + myurl +"\n"+"Any problem, contact with joey0576@163.com";
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        box.add(textArea);
        box.add(Box.createGlue());
        getContentPane().add(box, "Center");
        
        JPanel p1 = new JPanel();
        JButton ok = new JButton("Ok");
        p1.add(ok);
        getContentPane().add(p1, "South");
        
        ok.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        
        setSize(400, 400);  
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screen.width/2,screen.height/2);
    }

//    AboutDialog() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
}
