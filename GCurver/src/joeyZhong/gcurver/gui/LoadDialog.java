/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.gui;

import java.io.File;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author xizho3
 */
class LoadDialog 
{ 
    private  JFileChooser fileChooser; 
    private  File selectedFile;
    private File[] selectedFiles; 
    
    public LoadDialog()
    {
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load csv file");
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setCurrentDirectory(new File("."));

        int c = fileChooser.showOpenDialog(this.fileChooser);
        if (c != JFileChooser.APPROVE_OPTION) 
        {
            return;
        }
        else if (c == JFileChooser.APPROVE_OPTION) 
        {
            selectedFiles = fileChooser.getSelectedFiles();
            checkFiles(selectedFiles);
           
        }
    }
    
    public LoadDialog(String dialogTitle, boolean multipChoose, String fileFilter)
    {
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(dialogTitle);
        fileChooser.setMultiSelectionEnabled(multipChoose);
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setCurrentDirectory(new File("."));

        int c = fileChooser.showOpenDialog(this.fileChooser);
        if (c != JFileChooser.APPROVE_OPTION) 
        {
            selectedFile =null; 
            selectedFiles = null; 
            return;
        }
        else if (c == JFileChooser.APPROVE_OPTION) 
        {
            
            selectedFiles = fileChooser.getSelectedFiles();
            if (fileChooser.getSelectedFiles() == null)
            {
                System.out.println("null~!!!");
            }
            System.out.println(selectedFiles.length);
            checkFiles(selectedFiles, fileFilter);
           
        }
    }
    
    public File getFile()
    {
        return selectedFiles[0]; 
    }
    
    public File[] getFiles()
    {
        return selectedFiles;
    }
    
    // to check if all selected files have the same suffix in file name
    private void checkFiles(File[] selectFiles) 
    {
        // get the splited array length, may more than two dot in file name
        int arrayLen = selectFiles[0].getName().split("\\.").length; 
        String tester = selectFiles[0].getName().split("\\.")[arrayLen -1]; 
        if (!"csv".equals(tester.toLowerCase()))
        {
            JOptionPane.showMessageDialog(new JFrame(), "Are you sure the selected file is in csv format?");
            this.selectedFile = null;
            this.selectedFiles = null;
        }
        
        for (int i = 1; i < selectFiles.length; i++)
        {
            String tested = selectFiles[i].getName().split("\\.")[1].toLowerCase();
            if (tested == null ? tester != null : !tested.equals(tester))
            {
                JOptionPane.showMessageDialog(new JFrame(), "Wrong format. Please choose filein csv again.");
                this.selectedFile = null;
                this.selectedFiles = null;
            }
        }
        
    }

    private void checkFiles(File[] selectedFiles, String fileFilter) 
    {
        System.out.println(selectedFiles.length);
        if ("none".equals(fileFilter.toLowerCase()))
        {
            //do nothing
        }
        else 
        {
            // get the splited array length, may more than two dot in file name
            int arrayLen = selectedFiles[0].getName().split("\\.").length;
            String tester = selectedFiles[0].getName().split("\\.")[arrayLen - 1];
            if (!fileFilter.toLowerCase().equals(tester.toLowerCase())) 
            {
                JOptionPane.showMessageDialog(new JFrame(), "Are you sure the selected file is in " + fileFilter.toUpperCase()+ " format?");
            }

            for (int i = 1; i < selectedFiles.length; i++) 
            {
                String tested = selectedFiles[i].getName().split("\\.")[1].toLowerCase();
                if (tested == null ? tester != null : !tested.equals(tester)) 
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Wrong format. Please choose file in " +fileFilter.toUpperCase()+ " again.");
                }
            }
        }
       
    }
    
    
}
