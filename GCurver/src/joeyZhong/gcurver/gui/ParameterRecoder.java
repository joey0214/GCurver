/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.gui;

import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author xizho3
 */
public class ParameterRecoder 
{

   
    private String currentDir;
    private String xmlPathStr; 
    private String workPathStr, imageFormatStr; 
    private DocumentBuilderFactory docFactory; 
    private DocumentBuilder docuBuilder; 
    
    public ParameterRecoder()
    {
        //first, at current directory , search the parameter xml file
        //then load it or creat a new one.
        
        //
        
        currentDir = System.getProperty("user.dir");
        workPathStr = currentDir;
        findParameterXML(workPathStr, "GCurver_Parments.XML"); 
        readXML(); 
        
    }
    
    public void getParameter(){}
    
    public String getImageFormat()
    {
        return imageFormatStr; 
    }
    
    public void setImageFormat(String imageFormatStr)
    {
        this.imageFormatStr = imageFormatStr; 
    }
    
    public void writeParameter(){}

    
    public void setWorkDir(String workDirSetUp) 
    {
        if ((new File(workDirSetUp)).isDirectory())
        {
            this.workPathStr = workDirSetUp;
        }
        else
        {
            String message = "\"The directory you chose is not not valid.\"\n"
                    + "PLease choose again and make sure.\n";
            JFrame warnningFrame = new JFrame(); 
            warnningFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JOptionPane.showMessageDialog(warnningFrame, message, "Dialog",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String getWorkDir()
    {
        return workPathStr; 
    }
     
    private void findParameterXML(String searchPath, String fileNameStr) 
    {
        //if found.
        //dialog to ask if load or not
        //if agree to load , read parameter
        //if no xml detect. create a new one
        File[] filelist = (new File(searchPath)).listFiles(new FilenameFilter() 
        {
            @Override
            public boolean accept(File dir, String name) 
            {
                return name.toUpperCase().endsWith("XML");  
            }
        }); 
        
        if (filelist != null)
        {
            for (File testFile : filelist)
            {
                if (testFile.isDirectory())
                {
                    findParameterXML(testFile.getPath(), fileNameStr);
                }
                else if (fileNameStr.equalsIgnoreCase(testFile.getName()))
                {
                    xmlPathStr = testFile.getName();
                }
                else
                {
                    xmlPathStr = null; 
                }
            }
        }
    }

    public void done() 
    {
        //write output xml file
        if (xmlPathStr == null)
        {
            //write new xml file
        }
        else
        {
            //modify existed xml file
        }
        
    }

    private void readXML() 
    {
        if (xmlPathStr != null)
        {
            
        }
    }
    
    public void writeXML()
    {       
        try
        {
            docFactory =  DocumentBuilderFactory.newInstance(); 
            docuBuilder = docFactory.newDocumentBuilder();
            Document xmldoc = docuBuilder.newDocument();
            Element rootElement = xmldoc.createElement("GCurver");
            xmldoc.appendChild(rootElement);

            // parment elements
            Element parmentEle = xmldoc.createElement("Parments");
            rootElement.appendChild(parmentEle);

            // ImageFormat elements
            Element imageFormatEle = xmldoc.createElement("ImageFormat");
            imageFormatEle.appendChild(xmldoc.createTextNode(imageFormatStr));
            parmentEle.appendChild(imageFormatEle);

            // lastname elements
            Element dirEle = xmldoc.createElement("WorkDirectory");
            dirEle.appendChild(xmldoc.createTextNode(workPathStr));
            parmentEle.appendChild(dirEle); 
            
            // output DOM XML to console 
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            System.out.println(xmldoc); 
            DOMSource source = new DOMSource(xmldoc);
            StreamResult console = new StreamResult(new File(workPathStr +"/GCurver_Parments.xml"));
            transformer.transform(source, console);
        }
        catch(ParserConfigurationException | DOMException | IllegalArgumentException | TransformerException e)
        {
            e.printStackTrace();
        }
    }

//    //create element and node
//    private Node getpar(Document xmldoc, String string, String valueStr) 
//    {
//        Element parmenElement = xmldoc.createElement("Parments"); 
//        parmenElement.setAttribute("id", string);
//        parmenElement.appendChild(getparElement(xmldoc,parmenElement, "Value", valueStr)); 
//        return parmenElement;
//    }
//    //create text node
//    private Node getparElement(Document xmldoc, Element parmenElement, String keyName, String valueStr) 
//    {
//        Element nodeElement = xmldoc.createElement(keyName); 
//        nodeElement.appendChild(xmldoc.createTextNode(valueStr)); 
//        return nodeElement; 
//    }
}
