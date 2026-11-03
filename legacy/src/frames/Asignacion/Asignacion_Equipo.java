/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package frames.Asignacion;

import frames.Buscador;
import frames.Login;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import programas.conectarDB;

/**
 *
 * @author Herbert
 */
public class Asignacion_Equipo extends javax.swing.JInternalFrame {

    private conectarDB DB;
    private Statement st;
    private ResultSet rs;
    int bandera;
    String aop,cod;
    public Asignacion_Equipo() {
        initComponents();
      DB = new conectarDB();
            limpiar_cargar();
            ocultar(); 
            cargarCbos();
    }

        private void cargarTabla(){
        DefaultTableModel modelo = (DefaultTableModel)this.jTable1.getModel();
        String datos[] = new String[9];
        Integer i = 1;
        try  {
         st= DB.con.createStatement();
         String sql = "SELECT * FROM asignacion ";
         rs= st.executeQuery(sql);
             while (rs.next()){
              datos[0]= rs.getString(1);              
              datos[1]= rs.getString(2);
              datos[2]= rs.getString(3);
              datos[3]= rs.getString(4);
              datos[4]= rs.getString(5);              
              datos[5]= rs.getString(6);
              datos[6]= rs.getString(7);
              datos[7]= rs.getString(8);
              datos[8]= rs.getString(9);

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
     
  public final void cargarCbos(){        
try  {
          String sql = "SELECT descrip FROM estado order by 1 asc";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql);
             while (rs.next()){  
                cboFEst.addItem(rs.getString(1));
             }
             
              String sql1 = "SELECT chasis_nr FROM tracto order by 1 asc";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql1);
             while (rs.next()){  
                cbotrac.addItem(rs.getString(1));
             }
             
              String sql2 = "SELECT cap_carg FROM semirremolque order by 1 asc";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql2);
             while (rs.next()){  
                cbosemi.addItem(rs.getString(1));
             }
             
//              String sql3 = "SELECT id_rped FROM r_pedido order by 1 asc";
//         st = DB.con.createStatement();
//         rs = st.executeQuery(sql3);
//             while (rs.next()){  
//                cboPed.addItem(rs.getString(1));
//             }
          rs.close();
          st.close();
        }
     catch ( SQLException excepcionSql ) {
         JOptionPane.showMessageDialog( null, excepcionSql.getMessage(),
            "Error al cargar listas", JOptionPane.ERROR_MESSAGE );
     }
  }

   public void save(int tipo_de_guardado){
       if(tipo_de_guardado==1){
        try{
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

             String cTra=DB.buscarCod("SELECt id_tc from tracto where chasis_nr='"+cbotrac.getSelectedItem()+"'");
             String cEst=DB.buscarCod("SELECt id_est from estado where descrip='"+cboFEst.getSelectedItem()+"'");
             String cSemi=DB.buscarCod("SELECt id_semi from semirremolque where cap_carg='"+cbosemi.getSelectedItem()+"'");
              
                if(bandera==2 || bandera==3)cod=aop;
                //ban integer, cod integer, fecha date, des character varying, desp integer, rped integer, est integer
  String sql="select abm_asig('"+bandera+"','"+cod+"','"+jDateChooser1.getDate()+"','"+null+"','"+txtPed.getText()+"',"
          + "'"+cEst+"','"+txtConductor.getText()+"','"+cSemi+"','"+Login.cod_usuario+"','"+cTra+"');";System.out.print("\n"+sql);
  st = DB.con.createStatement();
  st.executeQuery(sql);     
            } catch (SQLException e)
                     {JOptionPane.showMessageDialog( null, e.getMessage(),"Error en la interaccón con la Base de Datos", JOptionPane.ERROR_MESSAGE );                                }   
       }else{//////////////////////////////inicio del detalle///////////////////////////////////////////////////////////
//           String cMerc=DB.buscarCod("SELECt id_merc from mercaderia where desc'");
//         try{
//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////             String cNac=DB.buscarCod("SELECt id_merc from mercaderia where descrip='"+cboPed.getSelectedItem()+"'");
//
//                
//  String sql="Begin;"
//          + "CALL abm_detalle_ped_flete('','');";
//  st = DB.con.createStatement();
//                  st.executeQuery(sql);     
//            } catch (SQLException e)
//                     {JOptionPane.showMessageDialog( null, e.getMessage(),
//            "Error en la interaccón con la Base de Datos", JOptionPane.ERROR_MESSAGE );                                }  
       }
         this.btnAdd.requestFocus();
      }        
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtCPed = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbosemi = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        cboFEst = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        cbotrac = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        txtConductor = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtPed = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Asignacion de Equipos");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. de Asig.", "Fecha de Carga", "Tracto", "Cod. de Pedido", "Semirremolque", "Estado", "Descripcion"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setResizable(false);
        jTable1.getColumnModel().getColumn(2).setResizable(false);
        jTable1.getColumnModel().getColumn(3).setResizable(false);
        jTable1.getColumnModel().getColumn(4).setResizable(false);

        btnAdd.setText("Agregar");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setText("Borrar");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnEdit.setText("Modificar");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnSave.setText("Grabar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnExit.setText("Salir");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Asignacion"));

        jLabel3.setText("Cod. de Asig:");

        jLabel10.setText("Fecha de carga:");

        jLabel11.setText("Cod de Pedido de F.:");

        jLabel8.setText("Semirremilque:");

        cbosemi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- ELEGIR --" }));

        jLabel9.setText("Estado:");

        cboFEst.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- ELEGIR --" }));

        jLabel12.setText("Tracto:");

        cbotrac.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- ELEGIR --" }));

        jLabel13.setText("Conductor:");

        jButton1.setText("...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Capcidad de carga");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCPed, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtConductor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cbotrac, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbosemi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboFEst, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtPed)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCPed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtConductor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbotrac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2))
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbosemi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(cboFEst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnDelete)
                            .addComponent(btnEdit)
                            .addComponent(btnSave)
                            .addComponent(btnExit)
                            .addComponent(btnCancelar)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
 bandera=1;
limpiar_cargar();
cod=DB.gencod("SELECT coalesce(MAX(id_asig),0)+1 FROM asignacion");
txtCPed.setText(cod);
jDateChooser1.requestFocus();
btnAdd.setEnabled(false);
btnCancelar.setEnabled(true);
btnSave.setEnabled(true);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

    bandera=3;
        btnCancelar.setEnabled(true);
            int msj=JOptionPane.showConfirmDialog(null,"Desea anular este Registro?", "Confirmar",JOptionPane.YES_NO_OPTION);
        if (msj==JOptionPane.YES_OPTION) {  
   save(1);
        } 
       btnCancelar.doClick();

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
          
    btnCancelar.setEnabled(true);
    btnSave.setEnabled(true);
    btnAdd.setEnabled(false);
    btnEdit.setEnabled(false);
    jDateChooser1.requestFocus();
    btnCancelar.setEnabled(true);
    bandera=2;
    btnDelete.setEnabled(false);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
//         try {
//            DB.con.commit();
//            System.out.print("\nCOMMIT");
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog( this, e.getMessage(),"Error al Guardar", JOptionPane.ERROR_MESSAGE ); 
//        }  
        validar_blancos();
    limpiar_cargar();
    ocultar(); 
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
//         try {
//            DB.con.rollback();
//            System.out.print("\nROLLBACK");
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog( this, e.getMessage(),"Error al Guardar", JOptionPane.ERROR_MESSAGE ); 
//        }  
    limpiar_cargar();
    ocultar(); 
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
aop=(jTable1.getValueAt(jTable1.getSelectedRow(),0).toString());
txtCPed.setText(aop);
jTable1.getValueAt(jTable1.getSelectedRow(),1).toString();//saca datos del jtable
txtCPed.setText(jTable1.getValueAt(jTable1.getSelectedRow(),0).toString());
//cboD_mer_ped.setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(),3).toString());
btnEdit.setEnabled(true);
btnDelete.setEnabled(true);
btnCancelar.setEnabled(true);
btnAdd.setEnabled(false);
    }//GEN-LAST:event_jTable1MousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
Buscador b= new Buscador(null,true, "select id_per,concat (nombres,', ',apellidos)as cli from personal where concat(nombres,' ',apellidos) LIKE", new String[]{"Documento","Personal"}, new Object[]{txtConductor,null}, DB.con);
b.setTitle("Buscador de Clientes");
b.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cboFEst;
    private javax.swing.JComboBox cbosemi;
    private javax.swing.JComboBox cbotrac;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtCPed;
    private javax.swing.JTextField txtConductor;
    private javax.swing.JTextField txtPed;
    // End of variables declaration//GEN-END:variables

private void ocultar(){  
    btnAdd.setEnabled(true);
    btnAdd.requestFocus();
    btnCancelar.setEnabled(false);
    btnDelete.setEnabled(false);
    btnEdit.setEnabled(false);
    btnSave.setEnabled(false); 
}


private void limpiar_cargar(){
    limpiarTabla();
    cargarTabla();
    txtCPed.setText("");
    txtConductor.setText("");
    cbotrac.setSelectedIndex(0);
    cboFEst.setSelectedIndex(0);
    cbosemi.setSelectedIndex(0);
    jDateChooser1.setDate(null);
}

  private void limpiarTabla(){
        DefaultTableModel modelo = (DefaultTableModel)this.jTable1.getModel();
        while(this.jTable1.getRowCount() != 0)
            modelo.removeRow(0);
  }
 
  public void validarDuplicados(){   
//     try  {
////          String sql = "select count(1) from asignacion where id_asig='"+txtCPed.getText()+"'";
////         st = DB.con.createStatement();
////         rs = st.executeQuery(sql);
////boolean find = rs.next();
////if(find==true){
////    JOptionPane.showMessageDialog(null, "Ya existe un Asignacion con ese Codigo", "Alerta", JOptionPane.ERROR_MESSAGE);
////}else{
   save(1);
////}
////          rs.close();
////          st.close();
//        }
//     catch ( SQLException excepcionSql ){
//         JOptionPane.showMessageDialog( null, excepcionSql.getMessage(),
//            "Error al validar duplicados", JOptionPane.ERROR_MESSAGE );
//  }
  }
  
     private void validar_blancos(){      
if( cbotrac.getSelectedIndex()==0 || txtConductor.getText().trim().equals("")) {
    JOptionPane.showMessageDialog(null, "coomplete todo", "Alerta", JOptionPane.ERROR_MESSAGE);
}else{
    validarDuplicados();
}       
     }
}
