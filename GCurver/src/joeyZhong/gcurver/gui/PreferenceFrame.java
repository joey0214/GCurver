/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
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
public class PreferenceFrame 
{
    //this frame is initial to collect preference information
    
    private JFrame preferFrame; 
    private JComboBox imageFormatBox; 
    private String[] imageFormats ; 
    private JButton okButton, cancelButton, dirBrowseButton; 
    private String imageSetUpFormat, workDirSetUp; 
    private JTextField workDirField; 
    private JLabel imageFormatLabel, workDirLabel; 
    private ImageIcon dirIcon; 
    private JLabel iconLabel; 
    private JFileChooser pathChooser ; 
    
    public PreferenceFrame()
    {
        preferFrame = new JFrame("Preference Setup");
        preferFrame.setVisible(true);
        preferFrame.setSize(350, 150);
        preferFrame.setVisible(true);
        preferFrame.setLocation(400, 200);
        preferFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        preferFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        imageFormatLabel = new JLabel("Image Format: "); 
        imageFormatLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        imageFormatLabel.setHorizontalAlignment(JLabel.LEFT);
        
        workDirLabel = new JLabel("Work Dir: ");
        
        workDirField = new JTextField(); 
        int defaultHeight = workDirField.getSize().height; 
        int defaultWidth = workDirField.getSize().width;
        workDirField.setColumns(10);
        System.out.println(defaultHeight+", "+ defaultWidth);
        workDirField.setText(System.getProperty("user.dir"));
        workDirField.setEditable(false);
        workDirField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        
        imageFormats = new String[]{"PNG", "JPEG","TIFF","BMP","SVG","EPS"};
        
        imageFormatBox = new JComboBox(); 
        imageFormatBox.addItem("Default");
        loadAllItem2Box(imageFormatBox, imageFormats);
        
        imageFormatBox.setAlignmentX(Component.LEFT_ALIGNMENT); 
        
        imageFormatBox.addItemListener(new ItemListener() 
        {
            @Override
            public void itemStateChanged(ItemEvent e) 
            {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    int selection = imageFormatBox.getSelectedIndex();
                    if (selection == 0)
                    {
                        imageSetUpFormat = "Default"; 
                    }
                    else 
                    {
                        imageSetUpFormat = imageFormats[selection - 1]; 
                    }
                    System.out.println(imageSetUpFormat);
                }
            }
        });
        
        okButton = new JButton("OK"); 
        okButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        okButton.setHorizontalAlignment(JButton.LEFT);
        okButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                //todo, save prefrence setup
                recoderPreference(); 
                preferFrame.dispose();
            }
        });
        
        cancelButton = new JButton("Cancel"); 
        cancelButton.setAlignmentX(Component.LEFT_ALIGNMENT); 
        cancelButton.setHorizontalAlignment(JButton.LEFT); 
        cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                imageSetUpFormat = "Default"; 
                System.out.println("cancel, do no thing");

                preferFrame.dispose();
            }
        });
        
//        dirIcon = new ImageIcon("/GCurver/src/joeyzhong/gcurver/icons/dir.jpg"); 
//        dirIcon = new ImageIcon("/joeyZhong/gcurver/gui/script/dir.jpg"); 
//        if (dirIcon != null){System.out.println("null icon!!");}
//        iconLabel = new JLabel(dirIcon); 
//        JButton test = new JButton(dirIcon);
        dirBrowseButton = new JButton("Browse"); 
        dirBrowseButton.setAlignmentX(Component.LEFT_ALIGNMENT); 
        dirBrowseButton.setHorizontalAlignment(JButton.LEFT);
        dirBrowseButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                pathChooser = new JFileChooser(); 
                pathChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                pathChooser.setDialogTitle("Set your wrok directory");
                pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                pathChooser.setAcceptAllFileFilterUsed(false);
                
                if (pathChooser.showDialog(new JFrame(), "Choose") == JFileChooser.APPROVE_OPTION)
                {
                    //get current path
                    System.out.println("current dir: " + pathChooser.getCurrentDirectory());
                    //get selected path
                    System.out.println("select dir: " + pathChooser.getSelectedFile());
                    workDirSetUp = pathChooser.getSelectedFile().getPath(); 
                    workDirField.setText(workDirSetUp);
                }
                else
                {
                    System.out.println("noselection");
                    workDirSetUp = pathChooser.getCurrentDirectory().getPath();
                    workDirField.setText(workDirSetUp);
                }
            }
        });

        GridBagConstraints frameGbc = new GridBagConstraints();
        preferFrame.setLayout(new GridBagLayout());
        frameGbc.insets = new Insets(4, 4, 4, 4);

        addComponent(preferFrame, imageFormatLabel, frameGbc, 0, 0, 1, 1);
        addComponent(preferFrame, imageFormatBox, frameGbc, 1, 0, 1, 1);
        
        addComponent(preferFrame, workDirLabel, frameGbc, 0, 1, 1, 1);
        addComponent(preferFrame, workDirField, frameGbc, 1, 1, 1, 1);
        addComponent(preferFrame, dirBrowseButton, frameGbc, 2, 1, 1, 1);
        
        addComponent(preferFrame, cancelButton, frameGbc, 0, 2, 1, 1);
        addComponent(preferFrame, okButton, frameGbc, 1, 2, 1, 1);
        
        preferFrame.setVisible(true);
    }
    
    private static void addComponent(Container container, Component component, GridBagConstraints gbc,int gridx, int gridy,
      int gridwidth, int gridheight )
    {
        gbc.gridheight = gridheight; 
        gbc.gridwidth = gridwidth; 
        gbc.gridx = gridx; 
        gbc.gridy = gridy; 
        
        container.add(component, gbc);
    }
     
    private void loadAllItem2Box(JComboBox chooseBox, String[] choicesOffered) 
    {
        chooseBox.removeAllItems(); 
        chooseBox.addItem(" ");
        for (int i=0; i < choicesOffered.length; i++)
        {
            chooseBox.addItem(choicesOffered[i]);
        }
    }
    
    private void recoderPreference()
    {
        ParameterRecoder paraRecoder = new ParameterRecoder(); 
        paraRecoder.setImageFormat(imageSetUpFormat);
        paraRecoder.setWorkDir(workDirSetUp); 
        paraRecoder.done(); 
    }
    
}
