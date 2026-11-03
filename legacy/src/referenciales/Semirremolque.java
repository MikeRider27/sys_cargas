
package referenciales;

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import programas.HashMd5;
import programas.conectarDB;


/**
 *
 * @author schafler92
 */
public class Semirremolque extends javax.swing.JInternalFrame {
    
    private final conectarDB DB;
    private Statement st;
    private ResultSet rs;
    int bandera,cbo;
    String aop,cod,orden="id_suc";

    public Semirremolque() {
        initComponents();
          DB = new conectarDB();
            nullAndLoad();
            ocultar();
    }

        private void cargarTabla(){
        DefaultTableModel modelo = (DefaultTableModel)this.jTable1.getModel();
        String datos[] = new String[4];
        Integer i = 1;
        try  {
         st= DB.con.createStatement();
         String sql = "select * from sucursal ORDER BY "+orden+"";
         rs= st.executeQuery(sql);

         while (rs.next()){
              datos[0]= rs.getString(1);              
              datos[1]= rs.getString(2);
              datos[2]= rs.getString(3);
              datos[3]= rs.getString(4);

              modelo.addRow(datos);
              i++;
             }
          rs.close();
          st.close();
        }
     catch ( SQLException excepcionSql ) {
         JOptionPane.showMessageDialog( null, excepcionSql.getMessage(),
            "Error al cargar la tabla", JOptionPane.ERROR_MESSAGE );
          System.exit( 1 );
      }
 }
     

   public void save(){    
         try{
                if(bandera==2 || bandera==3)cod=aop;
   String sql="select abm_suc('"+bandera+"','"+cod+"','"+txtmail.getText()+"')";             
                  st = DB.con.createStatement();
                  st.executeQuery(sql);     
            } catch (SQLException e)
                     {JOptionPane.showMessageDialog( null, e.getMessage(),
            "Error en la interaccón con la Base de Datos", JOptionPane.ERROR_MESSAGE );                                }
         this.btnAdd.requestFocus();
      }        
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox3 = new javax.swing.JCheckBox();
        jTextField1 = new javax.swing.JTextField();
        btnExit = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtmail = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtCod = new javax.swing.JTextField();
        cboCargo = new javax.swing.JComboBox();
        cboCargo1 = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        txtmail1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtmail2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtmail3 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtmail4 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtmail5 = new javax.swing.JTextField();

        jCheckBox3.setText("jCheckBox3");

        jTextField1.setText("jTextField1");

        setClosable(true);
        setIconifiable(true);
        setTitle("Semirremolque");

        btnExit.setText("Salir");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        btnExit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnExitKeyPressed(evt);
            }
        });

        btnAdd.setText("Agregar");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        btnAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAddKeyPressed(evt);
            }
        });

        btnSave.setText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        btnSave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSaveKeyPressed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        btnCancelar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCancelarKeyPressed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Tracto", "Modelo", "Marca", "Nro. de Chasis", "Nro de Chapa", "Tara"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(10);
        }

        jLabel2.setText("Cod. Semi:");

        jLabel1.setText("Tipo de Semi:");

        jLabel9.setText("Marca:");

        jLabel10.setText("Nro. de Chasis:");

        jButton1.setText("Editar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodKeyTyped(evt);
            }
        });

        cboCargo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir" }));

        cboCargo1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir" }));

        jLabel11.setText("Nro. de Chapa :");

        jLabel12.setText("Cap. Carga:");

        jLabel14.setText("Largo:");

        jLabel15.setText("Ancho:");

        jLabel16.setText("Alto:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboCargo, 0, 189, Short.MAX_VALUE)
                            .addComponent(cboCargo1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(24, 24, 24)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel12))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                        .addComponent(txtCod, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtmail2)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel16)
                                    .addGap(54, 54, 54)
                                    .addComponent(txtmail5))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel14)
                                        .addComponent(jLabel15))
                                    .addGap(43, 43, 43)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtmail3, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                                        .addComponent(txtmail4)))))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtmail1))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExit)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtmail2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtmail3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtmail4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtmail5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtmail1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cboCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cboCargo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd)
                    .addComponent(jButton1)
                    .addComponent(btnSave)
                    .addComponent(btnCancelar)
                    .addComponent(btnExit))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
al_agregar();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
if(bandera==2){
   save();
}else{
  validar_blancos();   
}
    nullAndLoad();
    ocultar();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

nullAndLoad();
ocultar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAddKeyPressed
if(evt.getKeyCode() == KeyEvent.VK_ENTER){
al_agregar();
}
    }//GEN-LAST:event_btnAddKeyPressed

    private void btnSaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSaveKeyPressed
if(evt.getKeyCode() == KeyEvent.VK_ENTER){
    validar_blancos();
    nullAndLoad();
    ocultar();
}
    }//GEN-LAST:event_btnSaveKeyPressed

    private void btnExitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnExitKeyPressed
if(evt.getKeyCode() == KeyEvent.VK_ENTER)dispose();
    }//GEN-LAST:event_btnExitKeyPressed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
if(evt.getKeyCode() == KeyEvent.VK_ENTER){

ocultar();
nullAndLoad();
}
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
aop=(jTable1.getValueAt(jTable1.getSelectedRow(),0).toString());
//txtCod.setText(aop);
//txtTel.setText(jTable1.getValueAt(jTable1.getSelectedRow(),1).toString());
//txtDir.setText(jTable1.getValueAt(jTable1.getSelectedRow(),2).toString());
//txtmail.setText(jTable1.getValueAt(jTable1.getSelectedRow(),3).toString());
btnCancelar.setEnabled(true);
btnAdd.setEnabled(false);
    }//GEN-LAST:event_jTable1MousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
al_editar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtCodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodKeyReleased

    private void txtCodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cboCargo;
    private javax.swing.JComboBox cboCargo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtmail;
    private javax.swing.JTextField txtmail1;
    private javax.swing.JTextField txtmail2;
    private javax.swing.JTextField txtmail3;
    private javax.swing.JTextField txtmail4;
    private javax.swing.JTextField txtmail5;
    // End of variables declaration//GEN-END:variables
private void al_agregar(){
        bandera=1;
nullAndLoad();
cod=DB.gencod("SELECT coalesce (MAX(id_suc),0)+1 FROM sucursal");
txtCod.setText(cod);
//txtTel.requestFocus();
btnAdd.setEnabled(false);
btnCancelar.setEnabled(true);
btnSave.setEnabled(true);
}
private void al_editar(){
    btnCancelar.setEnabled(true);
    btnSave.setEnabled(true);
    btnAdd.setEnabled(false);
  //  txtTel.requestFocus();
    bandera=2;
}

private void al_borrar(){
        btnCancelar.setEnabled(true);
        bandera=3;
            int msj=JOptionPane.showConfirmDialog(null,"Desea borrar este Registro?", "Confirmar",JOptionPane.YES_NO_OPTION);
        if (msj==JOptionPane.YES_OPTION) {  
           save();
        } 
       btnCancelar.doClick();
}

private void ocultar(){  
    btnAdd.setEnabled(true);
    jButton1.setEnabled(false);
    btnAdd.requestFocus();
    btnCancelar.setEnabled(false);
    btnSave.setEnabled(false); 

}


private void nullAndLoad(){
    limpiarTabla();
    cargarTabla();
//    txtTel.setText("");
//    txtmail.setText("");
//    txtDir.setText("");
    txtCod.setText("");
}

  private void limpiarTabla(){
        DefaultTableModel modelo = (DefaultTableModel)this.jTable1.getModel();
        while(this.jTable1.getRowCount() != 0)
            modelo.removeRow(0);
}
 
  public void validarDuplicados(){ 
      try  {
          String sql = "SELECT * from sucursal where direcc='"+txtCod.getText().trim()+"'";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql);
boolean find = rs.next();
if(find==true){
    JOptionPane.showMessageDialog(null, "Ya existe una sucursal en esa dir", "Alerta", JOptionPane.ERROR_MESSAGE);
}else{
    save();
}
          rs.close();
          st.close();
        }
     catch ( SQLException excepcionSql ){
         JOptionPane.showMessageDialog( null, excepcionSql.getMessage(),
            "Error al validar ALIAS duplicados", JOptionPane.ERROR_MESSAGE );
  } 
  }
  
     private void validar_blancos(){      
         if(txtCod.getText().trim() .equals("") || txtmail.getText().trim() .equals("")){
             JOptionPane.showMessageDialog(null, "Debe completar todos los campos para proceder", "Atención", JOptionPane.ERROR_MESSAGE);
 
             }else{
    validarDuplicados();
     } 
}}
