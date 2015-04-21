/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.conf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author xizho3
 */
public class InstallDependency 
{
    //check depency
    public void checkdependency(){}
    
    //install rjava for R
    public void installRJava(){}
    
    //install JRI for R --no-save
    public void installJRI(){}
    
    public void installRserve() throws IOException, InterruptedException
    {
        
        String command = "R CMD INSTALL "+System.getProperty("user.dir") +"/src/joeyZhong/gcurver/script/Rserve_1.8-1.tar.gz";
        runShell(command);
        //install.packages('Rserve')
        
    }

    private void runShell(String command) throws IOException, InterruptedException
    {
        Process myprocess = Runtime.getRuntime().exec(command);
        myprocess.waitFor();
        System.out.print(command);

        BufferedReader reader
                = new BufferedReader(new InputStreamReader(myprocess.getInputStream()));

        String line = "";
        while ((line = reader.readLine()) != null) 
        {
            System.out.print(line + "\n");
            System.out.print(command); 
    
        }
    }
    
    
}
