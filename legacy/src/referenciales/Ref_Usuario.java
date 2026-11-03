/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package referenciales;

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import programas.conectarDB;

/**
 *
 * @author Herbert
 */
public class Ref_Usuario extends javax.swing.JInternalFrame {

      private conectarDB DB;
    private Statement st;
    private ResultSet rs;
    int bandera,cbo;
   
    public Ref_Usuario() {
       initComponents();
          DB = new conectarDB();
            nullAndLoad();
            ocultar();
         cargarCbos();
    }

        private void cargarTabla(){
        DefaultTableModel modelo = (DefaultTableModel)this.jTable1.getModel();
        String datos[] = new String[8];
        Integer i = 1;
        try  {
         st= DB.con.createStatement();
         String sql = "select c.id_cond,c.nombres,c.apellidos,c.fecha_nac,c.nro_reg,c.direcc,c.telef,n.descrip,e.descrip,c.id_est\n" +
"from conductor c,estado e,nacionalidad n\n" +
"where n.id_nac=c.id_nac and e.id_est=c.id_est ";
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
          String sql = "select descrip from nacionalidad";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql);
             while (rs.next()){  
                 cboNac.addItem(rs.getString(1));
             }
             //tipo de doc no se le aplico a conductor
//         st = DB.con.createStatement();
//         rs = st.executeQuery("select descrip from tipo_doc order by descrip asc");
//             while (rs.next()){  
//                 cboTiD.addItem(rs.getString(1));
//             }
          rs.close();
          st.close();
        }
     catch ( SQLException excepcionSql ) {
         JOptionPane.showMessageDialog( null, excepcionSql.getMessage(),
            "Error al cargar listas desplegables", JOptionPane.ERROR_MESSAGE );
     }
  }

   public void save(){    
         try{
             Date date = jDateChooser1.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String xfp = String.valueOf(sdf.format(date));
        
             //String ctDOC=DB.buscarCod("SELECT id_tc FROM tipo_doc WHERE descrip='"+cboTiD.getSelectedItem()+"'");
             String ctnac=DB.buscarCod("SELECT id_nac FROM nacionalidad WHERE descrip='"+cboNac.getSelectedItem()+"'");
   String sql="select abm_cond('"+bandera+"',"
           + "'"+txtCNroC.getText()+"',"
           + "'"+txtCNom.getText()+"',"
           + "'"+txtCAp.getText()+"',"
           + "'"+xfp+"',"
           + "'"+txtCTel1.getText()+"',"
           + "'"+txtCDir.getText()+"',"
           + "'"+txtCTel.getText()+"',"
           + "'"+ctnac+"')";             
                  st = DB.con.createStatement();
                  st.executeQuery(sql);     
            } catch (Exception e)
                     {JOptionPane.showMessageDialog( null, e.getMessage(),
            "Error en la interaccón con la Base de Datos", JOptionPane.ERROR_MESSAGE );                                }
         this.btnAdd.requestFocus();
      }        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCNroC = new javax.swing.JTextField();
        txtCAp = new javax.swing.JTextField();
        txtCDir = new javax.swing.JTextField();
        txtCTel = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCNom = new javax.swing.JTextField();
        cboTiD = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cboNac = new javax.swing.JComboBox();
        txtCTel1 = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle("Conductor");
        setToolTipText("");

        jLabel1.setText("Nro. de Cedula: ");

        jLabel2.setText("Tipo de Doc.:");

        jLabel3.setText("Apellidos:");

        jLabel4.setText("Fecha Nac.:");

        jLabel5.setText("Dirección:");

        jLabel6.setText("Telefono:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro de Cedula", "Nombres", "Apellidos", "Fecha Nac", "Nro. de Reg.", "Direccion", "Telefono", "nac"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

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

        btnDelete.setText("Borrar");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        btnDelete.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDeleteKeyPressed(evt);
            }
        });

        btnEdit.setText("Editar");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        btnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEditKeyPressed(evt);
            }
        });

        jLabel8.setText("Nro. de Reg.:");

        jLabel9.setText("Nombres:");

        cboTiD.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir" }));
        cboTiD.setEnabled(false);

        jLabel7.setText("Nacionalidad");

        cboNac.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "elegir" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCNroC, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExit)
                        .addGap(83, 83, 83))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cboTiD, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(20, 20, 20))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtCNom, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCTel1))
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 2, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtCAp, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboNac, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCDir, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCTel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtCNroC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cboTiD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtCTel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtCNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtCAp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cboNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtCDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtCTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
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

    private void btnAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            al_agregar();
        }
    }//GEN-LAST:event_btnAddKeyPressed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnExitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnExitKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)dispose();
    }//GEN-LAST:event_btnExitKeyPressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        nullAndLoad();
        ocultar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){

            ocultar();
            nullAndLoad();
        }
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
      //  if(bandera==2){
          //  save();
      //  }else{
            validar_blancos();
      //  }
        nullAndLoad();
        ocultar();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSaveKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            validar_blancos();
            nullAndLoad();
            ocultar();
        }
    }//GEN-LAST:event_btnSaveKeyPressed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        al_borrar();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnDeleteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDeleteKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            al_borrar();
        }
    }//GEN-LAST:event_btnDeleteKeyPressed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        al_editar();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            al_editar();
        }
    }//GEN-LAST:event_btnEditKeyPressed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
btnEdit.setEnabled(true);

btnDelete.setEnabled(true);
btnCancelar.setEnabled(true);
txtCNroC.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
txtCNom.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
txtCAp.setText(jTable1.getValueAt(jTable1.getSelectedRow(),2).toString());
txtCDir.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 5).toString());
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	String dateInString = jTable1.getValueAt(jTable1.getSelectedRow(), 3).toString();
 Date date = null;
	try {
 
		 date = formatter.parse(dateInString);
	//	System.out.println(date);
		//System.out.println(formatter.format(date));
 
	} catch (ParseException e) {
	}
        jDateChooser1.setDate(date);
//datetxtCel.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 3).toString());
txtCTel.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 6).toString());
txtCTel1.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 4).toString());
jDateChooser1.setDate(date);
cboNac.setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(), 7).toString());
    }//GEN-LAST:event_jTable1MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cboNac;
    private javax.swing.JComboBox cboTiD;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtCAp;
    private javax.swing.JTextField txtCDir;
    private javax.swing.JTextField txtCNom;
    private javax.swing.JTextField txtCNroC;
    private javax.swing.JTextField txtCTel;
    private javax.swing.JTextField txtCTel1;
    // End of variables declaration//GEN-END:variables
private void al_agregar(){
        bandera=1;
nullAndLoad();
txtCNroC.requestFocus();
btnAdd.setEnabled(false);
btnCancelar.setEnabled(true);
btnSave.setEnabled(true);
}
private void al_editar(){
    btnCancelar.setEnabled(true);
    btnSave.setEnabled(true);
    btnAdd.setEnabled(false);
    txtCNroC.requestFocus();
    bandera=2;
    btnDelete.setEnabled(false);
    btnEdit.setEnabled(false);
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
    btnAdd.requestFocus();
    btnCancelar.setEnabled(false);
    btnDelete.setEnabled(false);
    btnEdit.setEnabled(false);
    btnSave.setEnabled(false); 

}


private void nullAndLoad(){
    limpiarTabla();
    cargarTabla();
    txtCTel.setText("");
    txtCNroC.setText("");
    txtCAp.setText("");
    txtCDir.setText("");
    jDateChooser1.setDate(null);
    txtCNom.setText("");
    cboNac.setSelectedIndex(0);
    txtCTel1.setText("");
}

  private void limpiarTabla(){
        DefaultTableModel modelo = (DefaultTableModel)this.jTable1.getModel();
        while(this.jTable1.getRowCount() != 0)
            modelo.removeRow(0);
}
 
  public void validarDuplicados(){ 
      try  {
          String sql = "SELECT * from conductor where id_cond='"+txtCNroC.getText().trim()+"' and id_cond!='"+txtCNroC.getText()+"'";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql);
boolean find = rs.next();
if(find==true){
    JOptionPane.showMessageDialog(null, "Ya existe un conductor con ese ci", "Alerta", JOptionPane.ERROR_MESSAGE);
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
         if(txtCNroC.getText().trim().isEmpty() || txtCAp.getText().trim().isEmpty() || txtCNom.getText().trim().isEmpty() || cboNac.getSelectedIndex()==0){
             JOptionPane.showMessageDialog(null, "Debe completar todos los campos para proceder", "Atención", JOptionPane.ERROR_MESSAGE);
 
             }else{
    validarDuplicados();
     } 
}
}
