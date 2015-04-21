/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import joeyZhong.gcurver.gui.analysis.PlateDesignProcess;
import joeyZhong.gcurver.gui.analysis.Rworker2;
import joeyZhong.gcurver.script.PlotDemo1;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RserveException;

/**
 *
 * @author joey zhong   
 */
class CurverGUI extends JFrame implements  ActionListener
{
    private JMenuBar menuBar;
    private Dimension screenDimension; 
    private JMenuItem loadJMenuItem, editJMenuItem,aboutJMenuItem, setEnvirItem, installDepItem, importDesignItem, exitJMenuItem, exportJMenuItem;
    private JMenuItem showTableItem,preferenceItem; 
    private JMenu fileJMenu, anylsisJMenu, helpJMenu;
    private GridBagConstraints gbc; 
    private JPanel rplotPanel,toolsPanel, multipFilesJPanel;
//    private JFrame rplotPanel; 
    private int frameWidth, frameHeight; 
    private JLabel titJLabel,xnameJLabel,ynameJLabel,testJLabel;
    private JTextField mainTitleJTextField, xNameJTextField,yNameJTextField; 
    private JButton renameJButton1, renameJButton2, renameJButton3, testJButton,refreshButton;
    private String yourTitleString, yourXnameString, yourYnameString;
    private LoadDialog loadDialog;
    private Rworker2 rworker2; 
    private JComboBox stratBox,colBox; 
    private String[][] colValuStr ;
    private ParameterRecoder parameterRecoder;
    
    public CurverGUI(ParameterRecoder parameterRecoder) throws RserveException, IOException, InterruptedException 
    {
        super();
        this.parameterRecoder = parameterRecoder; 
        setTitle("GCcurver Beta");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        show();
    }

    

    private void initComponents() throws RserveException, IOException, InterruptedException 
    {
        addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                rworker2.closeConnection();
                System.exit(0);
            }
        });
         
        makeMenu();
        
        screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenDimension.width*8/10 , screenDimension.height*8/10 );
        frameHeight = screenDimension.width*8/10; //this.getHeight();
        frameWidth = screenDimension.height*8/10; //this.getWidth();
        
        makePane(); 
        fillToolsPanel(); 
        
        rworker2 = new Rworker2();
    }

    private void makeMenu() 
    {
        loadJMenuItem = new JMenuItem("Load");
        loadJMenuItem.addActionListener(this);
        
        importDesignItem = new JMenuItem("Import Plate Design"); 
        importDesignItem.addActionListener(this);
        importDesignItem.enable(false);
        
        exitJMenuItem = new JMenuItem("Exit");
        exitJMenuItem.addActionListener(this);
        
        exportJMenuItem = new JMenuItem("Export graph"); 
        exitJMenuItem.addActionListener(this);
        
        preferenceItem = new JMenuItem("Preference"); 
        preferenceItem.addActionListener(this);
        
        fileJMenu = new JMenu("File");
        fileJMenu.add(loadJMenuItem); 
        fileJMenu.add(importDesignItem); 
        fileJMenu.add(exportJMenuItem);
        fileJMenu.addSeparator();
        fileJMenu.add(preferenceItem); 
        fileJMenu.addSeparator();
        fileJMenu.add(exitJMenuItem); 
        
        editJMenuItem = new JMenuItem("Edit");
        editJMenuItem.addActionListener(this);
        
        showTableItem = new JMenuItem("Show data in Table"); 
        showTableItem.addActionListener(this); 
        showTableItem.enable(false);
        
        anylsisJMenu = new JMenu("Analysis"); 
        anylsisJMenu.add(editJMenuItem); 
        anylsisJMenu.add(showTableItem); 
        
        aboutJMenuItem = new JMenuItem("About"); 
        aboutJMenuItem.addActionListener(this); 
        
        setEnvirItem = new JMenuItem("Set Envirmonent"); 
        setEnvirItem.addActionListener(this);
        setEnvirItem.enable(false);
        
        installDepItem = new JMenuItem("Install indepency"); 
        installDepItem.addActionListener(this);
        installDepItem.enable(false);
        
        helpJMenu = new JMenu("Help"); 
        helpJMenu.add(aboutJMenuItem); 
        helpJMenu.addSeparator();
        helpJMenu.add(setEnvirItem); 
        helpJMenu.add(installDepItem);
        
        menuBar = new JMenuBar();
        menuBar.add(fileJMenu); 
        menuBar.add(anylsisJMenu); 
        menuBar.add(helpJMenu);
        
        setJMenuBar(menuBar);

    }
    
    
    @Override
    public void actionPerformed(ActionEvent ace) 
    {
        if (ace.getSource() == loadJMenuItem )
        {
            loadDialog = new LoadDialog();
            File[] selectedFiles = loadDialog.getFiles(); 
            //when open a dialog, but does not choose a file, it will return null.
            if (selectedFiles != null)
            {
                showTableItem.enable(true);
                importDesignItem.enable(true);
                int numFiles = selectedFiles.length; 
                //only choose one file
                if (numFiles == 1)
                {
                    try 
                    {
                        rworker2.setCSVfile(selectedFiles[0]);
//                        rworker2.setCSVfile(null);
//                        rworker2.readTable("/home/joey/myGithub/GCurver/GCurver/testData/BioscreenExperiment_10_02_15.small.csv");
                        rworker2.drawPlot();
                        refreshPlotPanel();

                    } 
                    catch (RserveException ex) 
                    {
                        Logger.getLogger(CurverGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }  
                    catch (REXPMismatchException | IOException | REngineException ex) 
                    {
                        Logger.getLogger(CurverGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
                //choose two file, want to compare them.
                if (numFiles == 2)
                {
                    //TODO
                }
            }
            
        }
        
        if (ace.getSource() == showTableItem)
        {
            try 
            { 
                TableBuilder tabBuilder = new TableBuilder(rworker2.getData(), rworker2.getColNames(), rworker2.getNCol(), rworker2.getNRow());
            } 
            catch (REngineException | REXPMismatchException ex) 
            {
                Logger.getLogger(CurverGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (ace.getSource() == importDesignItem)
        {
//            loadDialog = new LoadDialog("Choose your Plate Design file:", false, "csv");
            loadDialog = new LoadDialog();
            
            //no file has been chose
            if (loadDialog.getFiles() == null ||loadDialog.getFiles().length == 0)
            {
                //file choser dialog choose nothing, 
                //so, do nothing here
                String message = "\"No input file detected.\"\n"
                            + "PLease choose again and make sure.\n";

                    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
                            JOptionPane.ERROR_MESSAGE);
            }
            
            //only one file chosed
            else if (loadDialog.getFiles().length == 1)
            {
                File plateDesignFile = loadDialog.getFile();
                if (plateDesignFile != null) 
                {
//                rworker2.setPlateDesign(plateDesignFile);
                    PlateDesignProcess pro = new PlateDesignProcess();
                    try {
                        pro.setPlateDesign(plateDesignFile);
                        String[] colnames = pro.getColNames();
                        final String[][] colValues = pro.getColVal();
                        stratBox.addItem(" ");
                        stratBox.addItemListener(new ItemListener() 
                        {

                            @Override
                            public void itemStateChanged(ItemEvent e) 
                            {
                                if ((e.getStateChange() == ItemEvent.SELECTED)) 
                                {
                                    int selection = stratBox.getSelectedIndex();
                                    switch (selection) 
                                    {
                                        case 0:
                                            break;
                                        case 1:
                                            colBox.removeAllItems();
                                            colBox.enable(true);
                                            colBox.addItem(" ");
                                            String[] tmpstr1 = colValues[0];
                                            for (int i = 0; i < tmpstr1.length; i++) 
                                            {
                                                colBox.addItem(tmpstr1[i]);
                                            }
                                            System.out.println("hello1");
                                            break;
                                        case 2:
                                            colBox.removeAllItems();
                                            colBox.enable(true);
                                            colBox.addItem(" ");
                                            String[] tmpstr2 = colValues[1];
                                            for (int i = 0; i < tmpstr2.length; i++) 
                                            {
                                                colBox.addItem(tmpstr2[i]);
                                            }
                                            System.out.println("hello1");
                                            break;
                                        case 3:
                                            colBox.removeAllItems();
                                            colBox.enable(true);
                                            colBox.addItem(" ");
                                            String[] tmpstr3 = colValues[2];
                                            for (int i = 0; i < tmpstr3.length; i++) 
                                            {
                                                colBox.addItem(tmpstr3[i]);
                                            }
                                            System.out.println("hello1");
                                            break;
                                        case 4:
                                            colBox.removeAllItems();
                                            colBox.enable(true);
                                            colBox.addItem(" ");
                                            String[] tmpstr4 = colValues[3];
                                            for (int i = 0; i < tmpstr4.length; i++) 
                                            {
                                                colBox.addItem(tmpstr4[i]);
                                            }
                                            System.out.println("hello1");
                                            break;
                                    }
                                }
                                //To change body of generated methods, choose Tools | Templates.
                            }
                        });
                        for (int i = 1; i < colnames.length; i++) 
                        {
                            stratBox.enable(true);
                            stratBox.addItem(colnames[i]);
                        }

                        colBox.enable(true);
                        colBox.removeAllItems();
                        colBox.addItem(" ");
                        for (int l = 0; l < colValues.length; l++) 
                        {
                            colBox.addItem(colnames[l]);
                            for (int m = 0; m < colValues[l].length; m++) 
                            {
                                colBox.addItem("-" + colValues[l][m]);
                            }

                        }
                    } 
                    catch (REngineException | REXPMismatchException ex) 
                    {
                        Logger.getLogger(CurverGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            //chosed more than two plate design files
            else if (loadDialog.getFiles().length >1)
            {
                
            } 
        }

        if (ace.getSource() == exitJMenuItem )
        {
            rworker2.closeConnection();
            System.exit(0);
        }
        
        if (ace.getSource() == exportJMenuItem)
        {
            //TODO
        }
        
        if (ace.getSource() == editJMenuItem )
        {
            //TODO
        }
        
        if (ace.getSource() == aboutJMenuItem )
        {
            try 
            {
                //TODO
                (new AboutDialog(this)).show();
            } 
            catch (MalformedURLException ex) 
            {
                Logger.getLogger(CurverGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (ace.getSource() == renameJButton1)
        {
            yourTitleString = mainTitleJTextField.getText();
            yourTitleString = yourTitleString.trim(); 
        }
        
        if (ace.getSource() == renameJButton2)
        {
            yourXnameString = xNameJTextField.getText();
            yourXnameString = yourXnameString.trim(); 
        }
        
        if (ace.getSource() == renameJButton3)
        {
            yourYnameString = yNameJTextField.getText();
            yourYnameString = yourYnameString.trim();
        }
        
//        if (ace.getSource() == testJButton)
//        {
//            loadDialog.getFile();
//        }
        
        if (ace.getSource() == refreshButton)
        {
            //todo
            String maintitleStr = mainTitleJTextField.getText().toString(); 
            rworker2.setMainTitle(maintitleStr);
            String xlab = xNameJTextField.getText().toString(); 
            String ylab = yNameJTextField.getText().toString(); 
            rworker2.setXLab(xlab);
            rworker2.setYLab(ylab);
            try { 
                rworker2.refresh();
                refreshPlotPanel();
            } 
            catch (REXPMismatchException | REngineException | IOException ex) 
            {
                Logger.getLogger(CurverGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (ace.getSource() == preferenceItem)
        {
            PreferenceFrame preferFrame = new PreferenceFrame(); 
            
        }
    }
    
    private void makePane() 
    {
        JComponent pane;
        pane = (JComponent) getContentPane();
        pane.setLayout(new GridBagLayout());
        
        gbc = new GridBagConstraints();
        //set distance between containers
        gbc.insets = new Insets(4, 4, 4, 4);
        
        rplotPanel = new JPanel();
        rplotPanel.setLayout(new GridBagLayout());
        rplotPanel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED), "R Plot"));
//        rplotPanel.setPreferredSize(new Dimension((frameWidth *7/10), (frameHeight)));
//        rplotPanel.setSize((frameWidth *7/10), (frameHeight));

        toolsPanel = new JPanel();
        toolsPanel.setLayout(new GridBagLayout());
        toolsPanel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED), "Tools"));
//        toolsPanel.setPreferredSize(new Dimension((frameWidth *3/10), (frameHeight / 2)));
//        toolsPanel.setSize((frameWidth *3/10), (frameHeight / 2));

        multipFilesJPanel = new JPanel();
        multipFilesJPanel.setLayout(new GridBagLayout());
        multipFilesJPanel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED), "Multi Controller"));
//        multipFilesJPanel.setPreferredSize(new Dimension((frameWidth *3/10), (frameHeight / 2)));
//        multipFilesJPanel.setSize((frameWidth *3/10), (frameHeight / 2));
    
        //when the size of frame changed, containes do nothing
        gbc.weightx = 1;
        gbc.weighty = 1;
//        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
//        gbc.anchor = GridBagConstraints.NORTH;
        add(pane, rplotPanel, gbc, 0, 0, 3, 4);
        
        gbc.weightx = 0;
        gbc.weighty = 0;
//        gbc.gridwidth = 1;
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.anchor = GridBagConstraints.NORTH;
        add(pane, toolsPanel, gbc, 3, 0, 1, 2);

        gbc.weightx = 0;
        gbc.weighty = 0;
//        gbc.gridwidth = 1;
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.anchor = GridBagConstraints.NORTH;
        add(pane, multipFilesJPanel, gbc, 3, 2,1, 2);
        
        
        
    }
    
    private void add(Container cn, Component c, GridBagConstraints gbc, 
            int x, int y, int w, int h) 
    {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        cn.add(c, gbc);
    }  

    private void fillToolsPanel() 
    {
        testJLabel = new JLabel("Test"); 
        titJLabel = new JLabel("Main title");
        xnameJLabel = new JLabel("X Axis");
        ynameJLabel = new JLabel("Y Axis"); 
        
        mainTitleJTextField = new JTextField();
        mainTitleJTextField.setVisible(true);
        mainTitleJTextField.setEnabled(true);
        mainTitleJTextField.setEditable(true);
        mainTitleJTextField.setColumns(10);
        
        yNameJTextField = new JTextField(); 
        yNameJTextField.setVisible(true);
        yNameJTextField.setEnabled(true);
        yNameJTextField.setEditable(true);
        yNameJTextField.setColumns(10);
        
        xNameJTextField = new JTextField();
        xNameJTextField.setVisible(true);
        xNameJTextField.setEnabled(true);
        xNameJTextField.setEditable(true);
        xNameJTextField.setColumns(10);
        
        renameJButton1 = new JButton("Rename"); 
        renameJButton1.addActionListener(this);
        
        renameJButton2 = new JButton("Rename"); 
        renameJButton2.addActionListener(this);
        
        renameJButton3 = new JButton("Rename"); 
        renameJButton3.addActionListener(this);
        
        testJButton = new JButton("Test");
        testJButton.addActionListener(this);
        
        refreshButton = new JButton("Renew"); 
        refreshButton.addActionListener(this);
        
        stratBox = new JComboBox(); 
        stratBox.enable(false);
        
        colBox = new JComboBox(); 
        colBox.enable(false);
         
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        
        
        add(toolsPanel, titJLabel, gbc,          0, 0, 1, 1);
        add(toolsPanel, mainTitleJTextField, gbc, 1, 0, 1, 1);
        add(toolsPanel, renameJButton1, gbc,     2, 0, 1, 1);
        
        add(toolsPanel, xnameJLabel, gbc,    0, 1, 1, 1);
        add(toolsPanel, xNameJTextField, gbc, 1, 1, 1, 1);
        add(toolsPanel, renameJButton2, gbc, 2, 1, 1, 1);
        
        add(toolsPanel, ynameJLabel, gbc,    0, 2, 1, 1);
        add(toolsPanel, yNameJTextField, gbc, 1, 2, 1, 1);
        add(toolsPanel, renameJButton3, gbc, 2, 2, 1, 1);
        
        add(toolsPanel, refreshButton, gbc, 0, 3, 1, 1);
        
        add(toolsPanel, stratBox, gbc, 1, 3, 1, 1);
        add(toolsPanel, colBox, gbc, 2, 3, 1, 1);
//        gbc.weightx = 0;
//        gbc.weighty = 0;
//        gbc.anchor = GridBagConstraints.NORTH;
//        JSeparator sep = new JSeparator();
//        sep.setPreferredSize(new Dimension(5, 1));
//        gbc.fill = GridBagConstraints.HORIZONTAL; 
//        gbc.weighty = 1;
//        toolsPanel.add(sep);
////        add(toolsPanel, sep);
////        toolsPanel.add(new JSeparator(GridBagConstraints.HORIZONTAL));
        
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        add(toolsPanel, testJButton, gbc, 0, 3, 1, 1);
    }
    
//    public void loadImage(String imagePath)
//    {
//        
//    }

    private void refreshPlotPanel() 
    {
        rplotPanel.removeAll();
        rplotPanel.add(new Rworker2(rworker2.getRePlotImage())); 
//        System.out.println(rworker2.getPlotImage();
        this.pack();
        rplotPanel.setVisible(true);
        rplotPanel.prepareImage(rworker2.getPlotImage(), this);
        this.setVisible(true); 
//        this.repaint();
        
    }
}
