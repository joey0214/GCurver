/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.gui;

import java.io.IOException;
import org.rosuda.REngine.Rserve.RserveException;

/**
 *
 * @author xizho3
 */
public class GCurver 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RserveException, IOException, InterruptedException 
    {
        // TODO code application logic here
        ParameterRecoder parameterRecoder = new ParameterRecoder(); 
        CurverGUI curvergui = new CurverGUI(parameterRecoder);
    }
    
}
