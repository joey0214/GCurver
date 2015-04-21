/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.test;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import joeyZhong.gcurver.conf.SetEnvironment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import joeyZhong.gcurver.conf.SetEnvironment2;
import joeyZhong.gcurver.gui.ParameterRecoder;
import joeyZhong.gcurver.gui.PreferenceFrame;
import joeyZhong.gcurver.gui.ProgressBar;
import joeyZhong.gcurver.gui.TableBuilder;
import joeyZhong.gcurver.gui.analysis.PlateDesignProcess;
import joeyZhong.gcurver.gui.analysis.Rworker2;
import joeyZhong.gcurver.script.PlotDemo;
//import joeyZhong.gcurver.gui.SetEnvironment1;
import org.rosuda.JRI.Rengine;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RserveException;

/**
 *
 * @author xizho3
 */
public  class guiTest 
{
    private String csvFileSeled, plateDesignSeled; 
    
    public static void main(String[] args) throws IOException, InterruptedException, RserveException, REXPMismatchException, REngineException
    {
        // test on GCurver GUI
//        GCurver curvergui = new GCurver();
        
//        //test on FileProcess
//        FileProcess fileProcess = new FileProcess(); 
//        String[] testStrings = {"hello", "hello", "world", "boy", "boy"};
//        fileProcess.reduce2Uniq(testStrings);
        
//        // test read output from command line after executed a command
//        readOutput(); 
        
//        //test on how to call JRI
//        System.out.println(System.getProperty("java.library.path"));
//        runDependency(); 
//        callJRI(); 
//        runCommand("sh ~/myGithub/GCurver/GCurver/src/joeyZhong/gcurver/script/check_rjava.sh");
        
//        // test on serve
//        Rworker2 rworker2 = new Rworker2(); 
////        System.out.println(rworker2.runCommand("R.version.string"));
//        rworker2.readTable("/home/joey/myGithub/GCurver/GCurver/testData/BioscreenExperiment_10_02_15.small.csv");
////         System.out.println(rworker2.showHeader());
//        
//        
//        //test: show data in table
////        testTable(rworker2);
//        rworker2.drawPlot();
//        displayPlot(rworker2.getPlotImage()); 
//        rworker2.refresh();
//        displayPlot(rworker2.getPlotImage()); 
        
        // test on setEnvironment
//        SetEnvironment settest = new SetEnvironment();
//        SetEnvironment2 settest2 = new SetEnvironment2(); 
        
        
        //test on process  bar
//        demoPreocessBar();
        
        // test on plateDesign
//        PlateDesignProcess proplate = new PlateDesignProcess("/home/joey/research/PlateDesign10_02_15.csv"); 
//        PlateDesignProcess proplate = new PlateDesignProcess(); 
//        proplate.setDesignPath("/home/joey/research/PlateDesign10_02_15.csv"); 
        
//        //test on link frame
//        String[] csvFile = {"test1.csv", "test2.csv", "test3.csv"};
//        String[] plateDesign = {"plate1.csv", "plate2.csv", "plate3.csv"}; 
//        PlateDesignProcess platedesi = new PlateDesignProcess(); 
//        
//        platedesi.setPlateDesignArray(csvFile, plateDesign);
        
        //test on preference frame
//        PreferenceFrame preferFrame = new PreferenceFrame(); 
        
        ParameterRecoder paraRecoder = new ParameterRecoder(); 
        paraRecoder.setImageFormat("JPG");
//        paraRecoder.setWorkDir(workDirSetUp); 
        paraRecoder.setWorkDir("/test/home"); 
        paraRecoder.writeXML();
    }

    private static void readOutput() throws IOException 
    {
        Path currentRelativePath = Paths.get("");
        String pathString = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + pathString);

        Runtime rt = Runtime.getRuntime();
        String commands = "Rscript " + pathString+"/Rscript/test.R";
        Process proc = rt.exec(commands);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String outputString = null;
        while ((outputString = stdInput.readLine()) != null) 
        {
            if (outputString != null)
            {
                System.out.println(outputString);
            }
        }

        // read any errors from the attempted command
        String errorString = null;
//        errorString = stdError.readLine(); 
//        System.out.println(errorString);
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((errorString = stdError.readLine()) != null) 
        {
            if (errorString.indexOf("package") >= 0)
            {
                System.out.println("need depentency"); 
            }
            
            System.out.println(errorString);
        }
    }

    private static void callJRI() 
    {
//        System.out.println(System.getProperty("java.library.path"));
        
//        System.loadLibrary("JRI");
//          -Djava.library.path="/home/joey/myGithub/GCurver/GCurver/library/JRI.jar;/home/joey/myGithub/GCurver/GCurver/library/JRIEngine.jar;/home/joey/myGithub/GCurver/GCurver/library/REngine.jar"
//        System.load("/home/joey/myGithub/GCurver/GCurver/library/JRI.jar");
//        System.loadLibrary("/home/joey/myGithub/GCurver/GCurver/library/"); 
//        System.loadLibrary("/home/joey/myGithub/GCurver/GCurver/library/REngine.jarJRI.jar");
        //-Djava.library.path="/usr/lib/R/site-library/rJava/jri"
        //-Djava.library.path="${/usr/lib/R/site-library/rJava/jri}\lib;${env_var:PATH}"
   
        ///-Djava.library.path=.:"/usr/lib/R/site-library/rJava/jri/"
        
        
        Rengine rengine = new Rengine(new String [] {"--vanilla"},false, null); 
        
        if (!rengine.isAlive())
        {
            System.out.println("R is not alive!");
            return;
        }
        
        if (!rengine.waitForR()) 
        {
            System.out.println("Cannot load R");
            return;
        }
        
        double[] arr = rengine.eval("rnorm(10)").asDoubleArray();
        for (double a : arr) 
        {
            System.out.print(a + ",");
        }
        rengine.end();
    }

    private static  void runDependency() throws IOException, InterruptedException 
    {
//        runCommand("export R_HOME=/usr/local/bin/R");
        runCommand("echo hello");
        final String dir = System.getProperty("user.dir");
//        System.out.println("current dir = " + dir);
        ///home/joey/myGithub/GCurver/GCurver
        runCommand("/bin/sh " +dir+"/src/joeyZhong/gcurver/script/check_rjava.sh");
//        System.out.println("/bin/sh " +dir+"/src/joeyZhong/gcurver/script/check_rjava.sh");
//        runCommand(" export ");
//       
//        runCommand(" /bin/sh /home/joey/myGithub/GCurver/GCurver/src/joeyZhong/gcurver/script/check_rjava.sh");
        
//        runCommand("R --version");
    }
    
    private static void runCommand(String command) throws IOException, InterruptedException
    {
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();

        BufferedReader reader
                = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line = "";
        while ((line = reader.readLine()) != null) 
        {
            System.out.print(line + "\n");
    
        }
    }

//    private static void buildTable(Object data, String[] colNames) 
//    {
////        String[] tablecol = getColNames(); 
////        Object tableObjects = getData();
//        JTable dataTable = new JTable((Object[][]) data, colNames); 
//        
//        JScrollPane scrollPane = new JScrollPane(dataTable); 
//        dataTable.setFillsViewportHeight(true);
//    }

    private static void testTable(Rworker2 rworker2) throws REXPMismatchException, REngineException 
    {
        System.out.println(Arrays.toString(rworker2.getColNames()) );
//         buildTable(rworker2.getData(), rworker2.getColNames()); 
        
        String[] test1 = {"A", "B","C"}; 
        Object test2[][] = {{"1","2","3"}, {"2","3","4"}, {"3","4","5"}}; 
        Object rowData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
        { "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
        
//        TableBuilder tabBuilder = new TableBuilder(test2, test1); 
        TableBuilder tabBuilder = new TableBuilder(rworker2.getData(), rworker2.getColNames(), rworker2.getNCol(), rworker2.getNRow()); 
         
        
    }

    private static void demoPreocessBar() 
    {
        ProgressBar progressBar = new ProgressBar(); 
    }

    private static void displayPlot(Image plotImage) 
    {
         Frame f = new Frame("Test image");
        f.add(new PlotDemo(plotImage));
        f.addWindowListener(new WindowAdapter() 
        { // just do we can close the window
            public void windowClosing(WindowEvent e) 
            {
                System.exit(0);
            }
        });
        f.pack();
        f.setVisible(true); 
    }

//    private static void linkFrame(String[] csvFile, String[] plateDesign)
//    {
//        JFrame frame = new JFrame("Link plate deaign with csv file");
//        frame.setVisible(true);
//        frame.setSize(300, 150);
//        frame.setVisible(true);
//        frame.setLocation(400, 200);
//        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        JLabel csvFileJLabel = new JLabel("Plate CSV");
//        JLabel plateDesignJLabel = new JLabel("Plate Design");
//
//        
//
//        final JComboBox csvFileBox = new JComboBox();
//        csvFileBox.addItem(" ");
//        for (int i = 0; i < csvFile.length; i++) 
//        {
//            csvFileBox.addItem(csvFile[i]);
//        }
//        String csvFileSeled = ""; 
//        csvFileBox.addItemListener(new ItemListener() 
//        {
//
//            @Override
//            public void itemStateChanged(ItemEvent e) 
//            {
//                if ((e.getStateChange() == ItemEvent.SELECTED))
//                {
//                    int selectionCsv = csvFileBox.getSelectedIndex(); 
//                    switch(selectionCsv)
//                    {
//                        case 0:
//                            csvFileSeled = null; 
//                            break; 
//                        case 1: 
//                            csvFileSeled = csvFile[0]; 
//                            break; 
//                        case 2:
//                            csvFileSeled = csvFile[1]; 
//                            break ; 
//                        case 3:
//                            csvFileSeled = csvFile[2]; 
//                            break;
//                        case 4:
//                            csvFileSeled = csvFile[3];
//                            break; 
//                            
//                            
//                    }
//                } 
//
//                 
//            }
//        });
//
//        final JComboBox plateDesignFileBox = new JComboBox();
//        plateDesignFileBox.addItem(" ");
//        for (int j = 0; j < plateDesign.length; j++) 
//        {
//            plateDesignFileBox.addItem(plateDesign[j]);
//        }
//        String plateDesignSeled = ""; 
//        plateDesignFileBox.addItemListener(new ItemListener() 
//        {
//
//            @Override
//            public void itemStateChanged(ItemEvent e) 
//            {
//                if (e.getStateChange() == ItemEvent.SELECTED)
//                {
//                    int selectionDesign = plateDesignFileBox.getSelectedIndex(); 
//                    switch(selectionDesign)
//                    {
//                        case 0:
//                            plateDesignSeled = null; 
//                            break;
//                        case 1:
//                            plateDesignSeled = plateDesign[0]; 
//                            break; 
//                        case 2:
//                            plateDesignSeled = plateDesign[1]; 
//                            break; 
//                        case 3:
//                            plateDesignSeled = plateDesign[2]; 
//                            break; 
//                        case 4:
//                            plateDesignSeled = plateDesign[3]; 
//                            break; 
//                        
//                    }
//                }
//            }
//        });
//
//        HashMap<String, String> csvPlateMap = new HashMap<String, String>();
//        
//        JButton linkFileButton = new JButton("Link");
//        linkFileButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                            //todo
//
//            }
//        });
//        
//        JButton cancelButton = new JButton("Cancel"); 
//        cancelButton.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) 
//            {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        });
//        
//        JButton addlinkButton = new JButton("Add Link"); 
//        addlinkButton.addActionListener(new ActionListener() 
//        {
//
//            @Override
//            public void actionPerformed(ActionEvent e) 
//            {
//                if (csvFileSeled != null && plateDesignSeled != null)
//                {
//                    csvPlateMap.put(csvFileSeled,plateDesignSeled);
//                }
//                
//            }
//        });
//        
//        
//        GridBagConstraints frameGbc = new GridBagConstraints();
//        frame.setLayout(new GridBagLayout());
//        frameGbc.insets = new Insets(4, 4, 4, 4);
////        frameGbc.gridheight = 1;
////        frameGbc.gridwidth = 1;
////        frameGbc.weightx = 1;
////        frameGbc.weighty = 0;
//
//        addComponent(frame, csvFileJLabel, frameGbc, 0, 0, 1, 1);
//        addComponent(frame, plateDesignJLabel, frameGbc, 1, 0, 1, 1);
//        addComponent(frame, csvFileBox, frameGbc, 0, 1, 1, 1);
//        addComponent(frame, plateDesignFileBox, frameGbc, 1, 1, 1, 1);
//        addComponent(frame, linkFileButton, frameGbc, 0, 2, 2, 1);
////        frame.add(csvFileJLabel);
////        frame.add(plateDesignJLabel);
////        frame.add(csvFileBox); 
////        frame.add(plateDesignFileBox); 
////        frame.add(linkFileButton); 
//        frame.setVisible(true);
//        //To change body of generated methods, choose Tools | Templates.
//    }


    
    private static void addComponent(Container container, Component component, GridBagConstraints gbc,int gridx, int gridy,
      int gridwidth, int gridheight )
    {
        gbc.gridheight = gridheight; 
        gbc.gridwidth = gridwidth; 
        gbc.gridx = gridx; 
        gbc.gridy = gridy; 
        
        container.add(component, gbc);
    }
    
}
