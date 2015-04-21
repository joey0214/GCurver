/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.gui;

/**
 *
 * @author xizho3
 */
public class DialogMaker 
{
    private int dialogType = 0 ;
    public DialogMaker(){}
    
    //to build a dailod for export trable to csv table
    // ask for file name and directory
    public DialogMaker(String dialogTitle, int dialType )
    {
        this.dialogType = dialType; 
        makeDialog(); 
    }
    
    private void makeDialog()
    {
        if (dialogType == 0){}
        
        if (dialogType == 1)
        {
            makeDialog1();
        }
        
        if (dialogType == 2)
        {
            makeDialog2();
        }
    }

    private void makeDialog1() 
    {
        //TODO
    }

    private void makeDialog2() 
    {
        //TODO
    }
}
