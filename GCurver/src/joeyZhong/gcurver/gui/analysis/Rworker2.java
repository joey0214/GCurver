/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.gui.analysis;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import joeyZhong.gcurver.script.PlotDemo1;
//import org.rosuda.JRI.RFactor;
import org.rosuda.JRI.RVector;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.RFactor;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RFileInputStream;
import org.rosuda.REngine.Rserve.RserveException;


/**
 *
 * @author xizho3
 */
public class Rworker2 extends Canvas
{
    private RConnection rConnection; 
    private org.rosuda.REngine.REXP rvalue; 
    private int dataColLen, dataRowLen;
    private File csvFile, plateDesignFile; 
    private File[] csvFiles; 
    private String mainTitleString, subtitleString, legendString, xlabStr , ylabStr; 
    private int plotNumber; 
    private String defaultPLotFormat="png", exportPlotFormat , imagePathStr; 
    private Image rplotImage; 
    private boolean refresh=false; 
    
    // this class is use Rserve to call R
    public Rworker2() throws RserveException, IOException, InterruptedException
    {
//        runShell("R CMD Rserve --no-save");
        rConnection = new RConnection(); 
        plotNumber = 0; 
        
        // install depended packages, from local
//        rConnection.eval("install.packages(\"grofit\")");
        

    }
    
    //run s shell command if need
    public String runCommand(String commandStr) throws RserveException, REXPMismatchException
    {
        rvalue = rConnection.eval(commandStr); 
        return rvalue.asString(); 
    }
    
    //read file into R, with header, separator is ","
    public void readTable(String tablePath) throws RserveException, REXPMismatchException
    {
        String tmpCommand = "mydata <- read.table(\"" + tablePath+ "\",header=TRUE,sep=\",\")"; 
        System.out.println(tmpCommand);
        REXP rvalue = rConnection.eval(tmpCommand); 
    }
    
    //show header of table
    public String[] showHeader()throws RserveException, REXPMismatchException, REngineException
    {
        rvalue = rConnection.parseAndEval("colnames(mydata)"); 
        return rvalue.asStrings(); 
    }
    
    //get column names of data
    public String[] getColNames() throws RserveException, REXPMismatchException, REngineException
    {
        String[] colNameList = rConnection.parseAndEval("colnames(mydata)").asStrings(); 
        return colNameList; 
    }
    
    //return column count number of table
    public int getNCol()
    {
        return this.dataColLen; 
    }
    
    //return row count number of table 
    public int getNRow()
    {
        return this.dataRowLen;
    }
    
    //return data in table to Java
    public double[][] getData() throws REngineException, REXPMismatchException
    {
        REXP rowLen = rConnection.eval("nrow(mydata)"); 
        REXP colLen = rConnection.eval("ncol(mydata)"); 
        
        this.dataColLen = colLen.asInteger(); 
        this.dataRowLen = rowLen.asInteger(); 
        
        double[][] dataMatrix = new double[rowLen.asInteger()][colLen.asInteger()]; 
        
        for (int i =0; i < rowLen.asInteger(); i ++)
        {
            for (int j =0; j < colLen.asInteger(); j++)
            { 
                REXP tmpRexp = rConnection.eval("mydata[" +(i+1) +","+(j +1)+"]");
                if ( j == 0)
                {
                    dataMatrix[i][j] = convertTime(tmpRexp); 
                }
                else
                {
                    dataMatrix[i][j] = Double.parseDouble(tmpRexp.asString());
                } 
//                System.out.println(">>> " + tmpRexp.asString());
            }
        }
        
        return dataMatrix; 
        
        
//        rConnection.eval("dataMatrix <- matrix(mydata, ncol=11)"); 
//        org.rosuda.REngine.RFactor dataFactor = rConnection.eval("dataMatrix").asFactor();
//        return dataFactor; 
    }
    
    public void buildTableObj() throws REXPMismatchException, REngineException
    {
        String[] tablecol = getColNames(); 
        Object tableObjects = getData();
        JTable dataTable = new JTable((Object[][]) tableObjects, tablecol); 
        
        JScrollPane scrollPane = new JScrollPane(dataTable); 
        dataTable.setFillsViewportHeight(true);
        
        
    } 
    
    public void selectData(){}
    
    public void drawPlot() throws RserveException, REXPMismatchException, REngineException, IOException
    {
        
        
        rConnection.eval("pure_data <- mydata[,2:ncol(mydata)]"); 
        rConnection.eval("interval <- 0.25"); 

        rConnection.eval("time_series <- seq(0, (nrow(mydata)-1)*interval, interval)"); 
//        rConnection.eval("plot.new()");
        String tmpfilename = "Rplot" + plotNumber + "."+ defaultPLotFormat; 
        System.out.println(tmpfilename);
//        rConnection.eval("png(hello.png)");
        System.out.println(System.getProperty("user.dir"));
        String rplotFilename = System.getProperty("user.dir")+"/"+tmpfilename; 
        this.imagePathStr = rplotFilename; 
        REXP deviceMsg = rConnection.eval("try(png(\""+System.getProperty("user.dir")+"/"+tmpfilename+"\"))");
//        if (deviceMsg.asString() != null)
//        {
//            System.out.println("can not open png device: \n" + deviceMsg.asString());
//            REXP warningMsg = rConnection.eval("if (exists(\\\"last.warning\\\") && length(last.warning)>0) names(last.warning)[1] else 0");
//            if (warningMsg.asString() != null)
//            {
//                System.out.println(warningMsg.asString());
//                return;
//            }
//            
//        }
        
        this.imagePathStr = rplotFilename; 



//        rConnection.eval("plot(time_series, pure_data[,2]);dev.off()"); 
        
//        if (refresh)
//        {
////            rConnection.parseAndEval("plot(time_series, pure_data[,2])");
////            rConnection.parseAndEval("main="+ mainTitleString);
//            rConnection.parseAndEval("plot(time_series, pure_data[,2], main=\""+ mainTitleString + "\",xlab=\""+xlabStr+"\",ylab=\""+ylabStr+"\")");
//        
//        }
//        else
//        {
//            rConnection.parseAndEval("plot(time_series, pure_data[,2])");
//        
//        }
//        rConnection.parseAndEval("plot(time_series, pure_data[,2], main="+ mainTitleString + ",xlab="+xlabStr+",ylab="+ylabStr+")");
        rConnection.parseAndEval("plot(time_series, pure_data[,2])");
//        rConnection.eval("library(ggplot2)"); 
//        rConnection.eval("fit <- lm(pure_data[,2] ~ time_series)"); 
//        REXP summaryMsg = rConnection.parseAndEval("summary(fit)"); 
////        System.out.println(summaryMsg.asStrings());
//        rConnection.parseAndEval("plot(fit)");
//        
        rConnection.eval("dev.off()");
//        rConnection.eval("plot(time_series, log2(pure_data[,2]))"); 
        
//        deviceMsg = rConnection.parseAndEval("r=readBin('" +rplotFilename+"','raw',1024*1024); unlink('"+rplotFilename+"'); r");
//        readImage(deviceMsg.asBytes());
        
        
        
         // we read in chunks of bufSize (64k by default) and store the resulting byte arrays in a vector
        // ... just in case the file gets really big ...
        // we don't know the size in advance, because it's just a stream.
        // also we can't rewind it, so we have to store it piece-by-piece
	RFileInputStream imgInputStream=rConnection.openFile(rplotFilename);
        processInputStream(imgInputStream); 
        rConnection.removeFile(rplotFilename);

        
        plotNumber ++; 
    }
    
    //to get image generated by R plot
    public Image getPlotImage()
    {
        return rplotImage; 
    }
    
    Image img;
    public Rworker2(Image img) {
        this.img=img;
        MediaTracker mediaTracker = new MediaTracker(new Canvas());
        mediaTracker.addImage(img, 0);
        try {
            mediaTracker.waitForID(0);
        } catch (InterruptedException ie) {
            System.err.println(ie);
            System.exit(1);
        }
        setSize(img.getWidth(null), img.getHeight(null));
    }
//
    @Override
    public void paint(Graphics g) 
    {
        g.drawImage(img, 0, 0, null);
    }
    
    public String getImagePath()
    {
        return imagePathStr; 
    }
    
    public void savePlot(){}
    
    //set main title string for plot drawing
    public void setMainTitle(String titleString)
    {
        this.mainTitleString = titleString; 
    }
    
    // set legend title for plot drawing
    public void setLegend(String myLegend)
    {
        this.legendString = myLegend; 
    }
    
    //set xlab for R plot
    public void setXLab(String xlabStr)
    {
        this.xlabStr = xlabStr; 
    }
    
    //setylab for R plot
    public void setYLab(String ylabStr)
    {
        this.ylabStr = ylabStr; 
    }
    
    // set subtitle string for plot drawing
    public void setSubTitle(String subtitleString)
    {
        this.subtitleString = subtitleString; 
    }
    
    //set single csv file for plot drawing
    public void setCSVfile(File csvFile) throws RserveException, REXPMismatchException
    {
        this.csvFile = csvFile; 
        String filaPath = csvFile.getAbsolutePath(); 
        readTable(filaPath);
    }
    
    //set multiple csv file for plot drawing
    public void setCSVfiles(File[] csvfilFiles)
    {
        this.csvFiles = csvfilFiles; 
    }
    
    //set plate design file for plot drawing
    public void setPlateDesign(File plateDesignFile)
    {
        this.plateDesignFile = plateDesignFile; 
        readPlateDesign(); 
    }
    
    
    
    public void runShell(String shellString) throws IOException, InterruptedException
    {
        Process myprocess = Runtime.getRuntime().exec(shellString);
        myprocess.waitFor();
        System.out.print(shellString);

        BufferedReader reader
                = new BufferedReader(new InputStreamReader(myprocess.getInputStream()));

        String line = "";
        while ((line = reader.readLine()) != null) 
        {
            System.out.print(line + "\n");
            System.out.print(shellString); 
    
        }
    }

    //convert time in the format of hh:mm:ss into int, like 2.5
    private double convertTime(REXP tmpRexp) throws REXPMismatchException 
    {
        String tmpStr = tmpRexp.asString(); 
        double hour = Double.parseDouble(tmpStr.split(":")[0]) + (Double.parseDouble(tmpStr.split(":")[1]) / 60); 
        System.out.println(hour);
        return hour; 
    }

    //read plate design
    private void readPlateDesign() 
    {
        //TODO
    }
    
    // close Rconnection
    public void closeConnection()
    {
	rConnection.close();
    }
    
    private void readImage(byte[] asBytes)
    {
        
        Image img = Toolkit.getDefaultToolkit().createImage(asBytes);

//        Frame f = new Frame("Test image");
//        f.add(new PlotDemo1(img));
//        f.addWindowListener(new WindowAdapter() 
//        { // just so we can close the window
//            public void windowClosing(WindowEvent e) 
//            {
//                System.exit(0);
//            }
//        });
//        f.pack();
//        f.setVisible(true);
    }

    private void processInputStream(RFileInputStream imgInputStream) throws IOException 
    {
        Vector buffers = new Vector();
        int bufSize = 65536;
        byte[] buf = new byte[bufSize];
        int imgLength = 0;
        int n = 0;
        while (true) 
        {
            n = imgInputStream.read(buf);
            if (n == bufSize) 
            {
                buffers.addElement(buf);
                buf = new byte[bufSize];
            }
            if (n > 0) 
            {
                imgLength += n;
            }
            if (n < bufSize) 
            {
                break;
            }
        }
        if (imgLength < 10) 
        { 
            // this shouldn't be the case actually, beause we did some error checking, but for those paranoid ...
            System.out.println("Cannot load image, check R output, probably R didn't produce anything.");
            return;
        }
        System.out.println("The image file is " + imgLength + " bytes big.");

        // now let's join all the chunks into one, big array ...
        byte[] imgCode = new byte[imgLength];
        int imgPos = 0;
        for (Enumeration e = buffers.elements(); e.hasMoreElements();) 
        {
            byte[] b = (byte[]) e.nextElement();
            System.arraycopy(b, 0, imgCode, imgPos, bufSize);
            imgPos += bufSize;
        }
        if (n > 0) 
        {
            System.arraycopy(buf, 0, imgCode, imgPos, n);
        }

        // ... and close the file ... and remove it - we have what we need :)
        imgInputStream.close();

        // now this is pretty boring AWT stuff, nothing to do with R ...
        this.rplotImage = Toolkit.getDefaultToolkit().createImage(imgCode);
//        this.rplotImage = img; 


    }

    public void refresh() throws REXPMismatchException, REngineException, RserveException, IOException 
    {
        refresh = true; 
        redrawPlot();

    }
    
    //return plot image
    public Image getRePlotImage()
    {
        return this.rplotImage; 
    }
    
    //draw plot again after setup
    private void redrawPlot() throws REngineException, REXPMismatchException, IOException 
    {
        
        String tmpfilename = "Rplot" + plotNumber + "."+ defaultPLotFormat; 
        System.out.println(tmpfilename);
//        rConnection.eval("png(hello.png)");
        System.out.println(System.getProperty("user.dir"));
        String rplotFilename = System.getProperty("user.dir")+"/"+tmpfilename; 
//        this.imagePathStr = rplotFilename; 
        REXP deviceMsg = rConnection.eval("try(png(\""+System.getProperty("user.dir")+"/"+tmpfilename+"\"))");
        if (refresh) 
        {
            rConnection.parseAndEval("plot(time_series, pure_data[,2], main=\"" + mainTitleString + "\",xlab=\"" + xlabStr + "\",ylab=\"" + ylabStr + "\")");

        } else {
            rConnection.parseAndEval("plot(time_series, pure_data[,2])");

        }

//        
        rConnection.eval("dev.off()");

        RFileInputStream imgInputStream = rConnection.openFile(rplotFilename);
        processInputStream(imgInputStream);
        rConnection.removeFile(rplotFilename);

        plotNumber++;
    }

   
}
