/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.gui.analysis;

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
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

/**
 *
 * @author xizho3
 */
public class PlateDesignProcess 
{
    private File plateDesign; 
    private File[] plateDesigns; 
    private RConnection rcon ; 
    private String plateDesignPath; 
    private String[] colnamesStr, colnamesStrMulti ;
    private String[][] colnameValueStr; 
    private String csvFileSeled, plateDesignSeled; 
    private HashMap<String, String> csvPlateMap; 
    private JFrame linkframe; 

    public PlateDesignProcess(File plateDesign, File[] plateDesigns, RConnection rcon, String plateDesignPath, String[] colnamesStr) {
        this.plateDesign = plateDesign;
        this.plateDesigns = plateDesigns;
        this.rcon = rcon;
        this.plateDesignPath = plateDesignPath;
        this.colnamesStr = colnamesStr;
    }
    
    //initial 
    public PlateDesignProcess(){}
    
    //initial with a path
    public  PlateDesignProcess(String filPath) throws RserveException, REngineException, REXPMismatchException
    {
        
        checkPath(filPath);
    }
    
    //set path to plate design
    public void setDesignPath(String filepathStr) throws RserveException, REngineException, REXPMismatchException 
    {
       checkPath(filepathStr);
    }
    
    //validate the path 
    private void checkPath(String filPath) throws RserveException, REngineException, REXPMismatchException
    {
        File tmpFile = new File(filPath); 
        if (tmpFile.isDirectory())
        {
            System.out.println("Yes, this is a directory");
        }
        else if (tmpFile.isFile())
        {
            System.out.println("Yes, this is a file.");
            this.plateDesignPath = filPath; 
            parsePlateDesign(plateDesignPath); 
        }
        else 
        {
            System.out.println("Sorry, please check your inputed path.");
        }
    }
    
    //set plate design file
    public void setPlateDesign(File plateDesignFile) throws RserveException, REngineException, REXPMismatchException
    {
        this.plateDesign = plateDesignFile; 
        String tmpfilePath = plateDesign.getAbsolutePath(); 
        parsePlateDesign(tmpfilePath); 
    }
    
    //set multiple plate design files for morn than two bioscreen csv files
    public void setPlateDesignArray(File[] plateDesignFiles, String[] csvList) throws RserveException, REngineException, REXPMismatchException
    {
        this.plateDesigns = plateDesignFiles; 
        if (plateDesignFiles != null && plateDesignFiles.length > 1)
        {
            if (plateDesignFiles != null)
            {
                String[] csvfilesNameStr = getFileNames(plateDesignFiles);
                linkFrame(csvfilesNameStr, csvList);
            }
        }
        
    }
    
    public void setPlateDesignArray(String[] plateDesignFiles, String[] csvList) throws RserveException, REngineException, REXPMismatchException
    {
        if (plateDesignFiles != null && plateDesignFiles.length > 1)
        {
            if (plateDesignFiles != null)
            {
                linkFrame(plateDesignFiles, csvList);
            }
        }
        
    }

    //process the plate design file
    private void parsePlateDesign(String plateDesignPath) throws RserveException, REngineException, REXPMismatchException 
    {
        //todo
        this.rcon = new RConnection(); 
        String tmpCommand = "platedesign <- read.table(\"" + plateDesignPath+ "\",header=TRUE,sep=\";\")"; 
        
        rcon.eval(tmpCommand); 
        REXP rvalue = rcon.parseAndEval("colnames(platedesign)"); 
        
        
        String[] colnameStr = "Puits.Souche.Replicat.Groupe.Temperature".split("\\."); 
        this.colnamesStr = colnameStr; 
        this.colnameValueStr = new String[colnamesStr.length -1][]; 
        for (int i =0; i < colnameStr.length; i ++)
        {
            System.out.println(colnameStr[i]);
            
            if ( i != 0)
            {
                try 
                {
                    REXP rfd  = rcon.parseAndEval("unique(platedesign$"+colnameStr[i]+")");
                    colnameValueStr[i -1] = rfd.asStrings(); 
                    System.out.println(Arrays.toString(rfd.asStrings()));
                } 
                catch (Exception e) 
                {
                    
                }
//                REXP rfd  = rcon.parseAndEval("unique(platedesign$"+colnameStr[i]+")");
//                colnameValueStr[i -1] = rfd.asStrings(); 
//                System.out.println(Arrays.toString(rfd.asStrings()));
            }
        }
    }
    
    //get column names to build combo box
    public String[] getColNames()
    {
        return this.colnamesStr; 
    }
    
    //get column names and the unique values to build combo box 
    public String[][] getColVal()
    {
        return this.colnameValueStr; 
    }

    //private void linkFrame(File[] plateDesignFiles, String[] csvList) 
    private void linkFrame(final String[] csvFile, final String[] plateDesign) 
    {
        //initial a frame to link csv file with plate design file
        // but before this, should check account number of csv files and count number of plate design files
        // if they are equal, then do next
        //

        //check if they are null or not
        if (csvFile != null && plateDesign != null) 
        {
            //if user really chosed more than 1 files
            if (csvFile.length > 1 && plateDesign.length > 1) 
            {
                //both chosed the same number of files
                if (csvFile.length == plateDesign.length) 
                {
                    linkframe = new JFrame("Link plate deaign with csv file");
                    linkframe.setVisible(true);
                    linkframe.setSize(310, 150);
                    linkframe.setVisible(true);
                    linkframe.setLocation(400, 200);
                    linkframe.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                    linkframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JLabel csvFileJLabel = new JLabel("Plate CSV");
                    JLabel plateDesignJLabel = new JLabel("Plate Design");

                    final JComboBox csvFileBox = new JComboBox();
                    csvFileBox.addItem(" ");
                    for (int i = 0; i < csvFile.length; i++) 
                    {
                        csvFileBox.addItem(csvFile[i]);
                    }
                    csvFileSeled = "";
                    csvFileBox.addItemListener(new ItemListener() 
                    {
                        @Override
                        public void itemStateChanged(ItemEvent e) 
                        {
                            if ((e.getStateChange() == ItemEvent.SELECTED)) 
                            {
                                int selectionCsv = csvFileBox.getSelectedIndex();
                                switch (selectionCsv) 
                                {
                                    case 0:
                                        csvFileSeled = null;
                                        break;
                                    case 1:
                                        csvFileSeled = csvFile[0];
                                        break;
                                    case 2:
                                        csvFileSeled = csvFile[1];
                                        break;
                                    case 3:
                                        csvFileSeled = csvFile[2];
                                        break;
                                    case 4:
                                        csvFileSeled = csvFile[3];
                                        break;
                                }
                            }
                        }
                    });

                    final JComboBox plateDesignFileBox = new JComboBox();
                    plateDesignFileBox.addItem(" ");
                    for (int j = 0; j < plateDesign.length; j++) 
                    {
                        plateDesignFileBox.addItem(plateDesign[j]);
                    }
                    plateDesignSeled = "";
                    plateDesignFileBox.addItemListener(new ItemListener() 
                    {
                        @Override
                        public void itemStateChanged(ItemEvent e) 
                        {
                            if (e.getStateChange() == ItemEvent.SELECTED) 
                            {
                                int selectionDesign = plateDesignFileBox.getSelectedIndex();
                                switch (selectionDesign) {
                                    case 0:
                                        plateDesignSeled = null;
                                        break;
                                    case 1:
                                        plateDesignSeled = plateDesign[0];
                                        break;
                                    case 2:
                                        plateDesignSeled = plateDesign[1];
                                        break;
                                    case 3:
                                        plateDesignSeled = plateDesign[2];
                                        break;
                                    case 4:
                                        plateDesignSeled = plateDesign[3];
                                        break;

                                }
                            }
                        }
                    });

                    csvPlateMap = new HashMap<String, String>();

                    JButton linkFileButton = new JButton("Link");
                    linkFileButton.addActionListener(new ActionListener() 
                    {
                        @Override
                        public void actionPerformed(ActionEvent e) 
                        {
                            //todo
                            System.out.println("print hashmap");
                            
                            linkframe.dispose();
                        }
                    });

                    JButton cancelButton = new JButton("Cancel");
                    cancelButton.addActionListener(new ActionListener() 
                    {
                        @Override
                        public void actionPerformed(ActionEvent e) 
                        {
                            csvPlateMap.clear(); 
                            csvFileBox.removeAllItems();
                            loadAllItem(csvFileBox, csvFile);
                            plateDesignFileBox.removeAllItems();
                            loadAllItem(plateDesignFileBox, plateDesign);
                        }

                        
                    });

                    JButton addlinkButton = new JButton("Add Link");
                    addlinkButton.addActionListener(new ActionListener() 
                    {
                        @Override
                        public void actionPerformed(ActionEvent e) 
                        {
                            if (csvFileSeled != null && plateDesignSeled != null) 
                            {
                                csvPlateMap.put(csvFileSeled, plateDesignSeled);
                                csvFileBox.removeItem(csvFileSeled);
                                plateDesignFileBox.removeItem(plateDesignSeled);
                            }

                        }
                    });

                    GridBagConstraints frameGbc = new GridBagConstraints();
                    linkframe.setLayout(new GridBagLayout());
                    frameGbc.insets = new Insets(4, 4, 4, 4);

                    addComponent(linkframe, csvFileJLabel, frameGbc, 0, 0, 1, 1);
                    addComponent(linkframe, plateDesignJLabel, frameGbc, 1, 0, 1, 1);
                    addComponent(linkframe, csvFileBox, frameGbc, 0, 1, 1, 1);
                    addComponent(linkframe, plateDesignFileBox, frameGbc, 1, 1, 1, 1);
                    addComponent(linkframe, addlinkButton, frameGbc, 0, 2, 2, 1);
                    addComponent(linkframe, cancelButton, frameGbc, 0, 3, 1, 1);
                    addComponent(linkframe, linkFileButton, frameGbc, 1, 3, 1, 1);

                    linkframe.setVisible(true);

                } //count number of csv files and count number of design files are different
                else 
                {
                    String message = "\"The count number of csv files and count number of design files are different.\"\n"
                            + "PLease choose again and make sure.\n";

                    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }
            } //chosed files are less than 2
            else 
            {
                String message = "\"The count number of csv files and count number of design files are less than 2.\"\n"
                        + "PLease choose again and make sure.\n";

                JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
                        JOptionPane.ERROR_MESSAGE);
            }
        } //warning dialog for both of them are null
        else 
        {
            String message = "\"Both of csv files and plate design files are null.\"\n"
                    + "Please choose files again.\n";
            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
                    JOptionPane.ERROR_MESSAGE);
        }
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

    private String[] getFileNames(File[] plateDesignFiles) 
    {
        String[] csvnamesStrs = new String[plateDesignFiles.length]; 
        for (int i =0; i < plateDesignFiles.length; i++)
        {
            csvnamesStrs[i] = plateDesignFiles[i].getName(); 
            
        }
        
        return csvnamesStrs; 

    }

    //given a comboBox, first remove all items in sides, whether is empty or not
    // then add every item in the String array
    private void loadAllItem(JComboBox chooseBox, String[] choicesOffered) 
    {
        chooseBox.removeAllItems(); 
        chooseBox.addItem(" ");
        for (int i=0; i < choicesOffered.length; i++)
        {
            chooseBox.addItem(choicesOffered[i]);
        }
    }
  
}
