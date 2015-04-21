/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.gui.analysis;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author xizho3
 */
public class FileProcess 
{
    private File csvFile; 
    private File[] csvFiles; 
    
    //input single csv file
    public void setSingleFile(File singleFile)
    {
        this.csvFile = singleFile;        
    }
    
    //input more than two files, for comparison
    public void setMultiFiles(File[] multiFiles)
    {
        this.csvFiles = multiFiles; 
        
    }
    
    
    //given a list of name, contains replicate strings, return uniq string
    public String[] reduce2Uniq(String[] nameStrings)
    {
        Set<String>  uniqStrings = new HashSet<>(Arrays.asList(nameStrings));
        String[] reduecedStrings = uniqStrings.toArray(new String[0]);
        return reduecedStrings;
    }
    
    //select part of input data
    public void selectData(){}
    
    //display data in table
    public void dataInTable(){}
    
    //
    public void runRscript(){}

   
    
    
    
    
}
