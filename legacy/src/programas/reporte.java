
package programas;


import java.sql.Connection;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author schafler92
 */
public class reporte {
  
    public void reporteSinParametro(String archivo,Connection DB){
        try {
                JasperReport report=JasperCompileManager.compileReport("src/informes/"+archivo+".jrxml"); 
                
                HashMap parameters = new HashMap();
                  
                JasperPrint print = JasperFillManager.fillReport(report, parameters,DB);
                JasperViewer view = new JasperViewer(print, false);
                
                view.setTitle("Vista Previa - Informe");
                //view.setExtendedState(Frame.MAXIMIZED_BOTH);
                view.setLocationRelativeTo(null);
                view.setVisible(true);   
                view.setAlwaysOnTop(true);
                view.toFront();
            
        } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
        }     
    } 
    public void reporteConParametro(String archivo,Integer cod,Connection DB){
        try {
                JasperReport report=JasperCompileManager.compileReport("src/informes/"+archivo+".jrxml"); 
                
                HashMap parameters = new HashMap();
                parameters.put("p",cod);
                  
                JasperPrint print = JasperFillManager.fillReport(report, parameters,DB);
                JasperViewer view = new JasperViewer(print, false);
                
                view.setTitle("Vista Previa - Informe");
                //view.setExtendedState(Frame.MAXIMIZED_BOTH);
                view.setLocationRelativeTo(null);
                view.setVisible(true);              
                view.toFront();
        } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
        }     
    } 
    
}
