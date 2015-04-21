/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeyZhong.gcurver.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RFactor;
import org.rosuda.REngine.RList;

/**
 *
 * @author xizho3
 */
public class TableBuilder 
{
    private JTable jTable; 
    
    //todo list
    //1. set table editable(false)
    // 2. set table prefered width
    // 3. export to csv, dialog
    // 4. horizional scrollplane
    
    
    public TableBuilder()
    {
        
    }

    public TableBuilder(double[][] data, String[] colNames, int ncol, int nrow) throws REXPMismatchException 
    {
//        transl(data, colNames); 
//        String[] tablecol = getColNames(); 
//        Object tableObjects = getData();
        System.out.println((transl(data, ncol, nrow)).getClass());
        JTable dataTable = new JTable(transl(data, ncol, nrow), colNames); 
        
        this.jTable = dataTable; 
        tabBuilder();
        
    }
    
    public TableBuilder(Object[][] tabObjec, String[] headerNmaes)
    {
        JTable obJTable = new JTable(tabObjec, headerNmaes);
        this.jTable = obJTable;
        tabBuilder(); 
    }
    

    
    public void saveToCsv()
    {
        int dialogtype = 1; 
        DialogMaker dialogMaker = new DialogMaker("Save to CSV",  dialogtype); 
         
    }

    private Object[][] transl(double[][] data, int ncol, int nrow) 
    {
        
        System.out.println(">>> " + (ncol) + "--- "+ (nrow));
        Object[][] dataObj = new Object[nrow][ncol]; 
        for (int i = 0; i < nrow; i ++)
        {
            for (int j =0; j < ncol; j++)
            {
                dataObj[i][j] = (Object)data[i][j]; 
//                System.out.println(">>> " + data[i][j]);
            }
        }
//        for (int l =0; l < )
        System.out.println(">>> " + Arrays.toString(data));
        return dataObj; 
    }

    private void tabBuilder() 
    {
//        jTable.
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(jTable); 
        scrollPane.setAutoscrolls(true);
        scrollPane.createHorizontalScrollBar(); 
        scrollPane.createVerticalScrollBar(); 
        
//        scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL) );
//        scrollPane.add(new JScrollBar(JScrollBar.HORIZONTAL)); 
        jTable.setFillsViewportHeight(true);
        
        final JFrame frame = new JFrame("Table"); 
        frame.setVisible(true);
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setLocation(400, 200);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        frame.set
//        frame.addWindowListener(new WindowAdapter() 
//        {
//            public void windowClosing(WindowEvent e) 
//            {
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////                System.exit(0);
//            }
//        });
        
        JPanel panel = new JPanel(); 
        panel.setLayout(new BorderLayout());
//        panel.add(jTable.getTableHeader(), BorderLayout.PAGE_START); 
//        panel.add(jTable, BorderLayout.CENTER);
        panel.add(scrollPane); 
        panel.setVisible(true);
        frame.add(panel); 
//        frame.setLayout(new BorderLayout()); 
//        frame.add(dataTable.getTableHeader(), BorderLayout.PAGE_START); 
//        frame.add(dataTable, BorderLayout.CENTER); 
//        frame.add(scrollPane); 
        frame.setVisible(true);
        
    }
    
}
