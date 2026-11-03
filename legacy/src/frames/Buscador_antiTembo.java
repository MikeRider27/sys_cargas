
package frames;

import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Buscador_antiTembo extends javax.swing.JDialog {

    String[] col,datos;
    String sql;
    Connection con;
    Object[] campos;
    ResultSet rs;
    PreparedStatement pst;
    DefaultTableModel modelo ;
    
    public Buscador_antiTembo(java.awt.Frame parent, boolean modal,String sql,String[] nombre_columnas,Object campos[],Connection con) {
        super(parent, modal);
        initComponents();
        this.sql = sql;//el comando sql a consultar
        this.col = nombre_columnas;//un array[] de string con los nombres de las columnas
        this.campos = campos;//un array[] de object conteniendo los campos(del frame padre) a ser completados con lo seleccionado desde aqui
        this.con=con;//la conexion que es heredada del frame padre para no hacer una conexion nueva desde aqui
        this.setLocationRelativeTo(parent);//na'ape *insertar risa malefica aqui*
        modelo= (DefaultTableModel)jTable1.getModel();//obtiene el modelo de la tabla
        datos= new String[col.length];//datos es un array[] de string que obtiene todo lo que da como resultado la consulta, aqui se le da la longitud de col
        columnConfig();
        cargarTabla();
    }
    
    private void columnConfig(){//agrega las columnas
        int nro_columnas=0;        
        while(nro_columnas!=col.length){
         modelo.addColumn(col[nro_columnas]);
         nro_columnas++;
        }
    }
    
    private void limpiarTabla(JTable tabla){//no hace falta describir
        while(tabla.getRowCount()!=0)modelo.removeRow(0);
}
    
 private void cargarTabla(){//no hace falta describir
     limpiarTabla(jTable1);
   //  if(!txtBuscar.getText().trim().isEmpty()){
              System.out.print("\n -- CONSULTA BUSCADOR antitembo-- \n#SQL="+sql);
        try  {
         pst= con.prepareStatement(sql);
         rs= pst.executeQuery();
         if(rs.isBeforeFirst()){
          while (rs.next()){
              for (int i = 0; i < datos.length; i++) {
                 datos[i] = rs.getString(i+1);                        
                  }
              modelo.addRow(datos);
             }   
         }else{
           // new JoptionPaneContimeOut().visualizaDialogo(this,"La consulta no produjo resultados","Atención",1500,2);  
         }
        }
     catch ( SQLException e ) {
         JOptionPane.showMessageDialog( this, e.getMessage(),"Error al Cargar Listado (Buscador)", JOptionPane.ERROR_MESSAGE );
         System.exit( 1 );
      }finally{
            try {
              if(rs!=null)rs.close();
              if(pst!=null)pst.close();  
            } catch (SQLException e) {
                JOptionPane.showMessageDialog( this, e.getMessage(),"Error al Cerrar objetos (Buscador)", JOptionPane.ERROR_MESSAGE );
       }
     } 
//   }
 }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Buscador");
        setAlwaysOnTop(true);
        setResizable(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
    //THIS
        //IS
            //SPART.. NOPE, MAGIC!
                //WHATEVER
        if (JOptionPane.showConfirmDialog(this,"¿Desea seleccionar este Registro?", "Confirmar",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
for (int i = 0; i < campos.length; i++) {
           if (campos[i] instanceof JTextField ){ //pregunta si dicho Object es la instancia de un txt
                if(campos[i]!=null)((JTextField)campos[i]).setText(jTable1.getValueAt(jTable1.getSelectedRow(), i).toString());
           }else{
               if(campos[i] instanceof JDateChooser){//pregunta si es un jdatechooser
                    if(campos[i]!=null)((JDateChooser)campos[i]).setDate(String_to_Date(jTable1.getValueAt(jTable1.getSelectedRow(), i).toString()));
               }else{//si no es jdatechooser ya solo puede ser un combo
                if(campos[i]!=null)((JComboBox)campos[i]).setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(), i).toString());   
               }
           }
}       
dispose();
        }
    }//GEN-LAST:event_jTable1MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

private void toUpperCaseState(javax.swing.JTextField txt,java.awt.event.KeyEvent evt){//metodo interesante 1
if(evt.getKeyCode()!=32 && evt.getKeyCode()!=8 && evt.getKeyCode()!=16 && evt.getKeyCode()!=127 && evt.getKeyCode()!=39 && evt.getKeyCode()!=37 && evt.getKeyCode()!=36 && evt.getKeyCode()!=35){
    txt.setText(txt.getText().toUpperCase());
}
}   

private Date String_to_Date(String sDate){//metodo interesante 3
        try {
        return new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(Menu.jDesktopPane1.getSelectedFrame(),e.getMessage(),"Error al parsear fecha", JOptionPane.ERROR_MESSAGE);
        return null;
    }     
}
}
