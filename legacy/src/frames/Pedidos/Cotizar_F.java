/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package frames.Pedidos;

import frames.Buscador;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import programas.conectarDB;
import programas.reporte;

/**
 *
 * @author Herbert
 */
public class Cotizar_F extends javax.swing.JInternalFrame {

    private conectarDB DB;
    private Statement st;
    private ResultSet rs;
    int bandera;
    String aop,cod;
    public Cotizar_F() {
        initComponents();
      DB = new conectarDB();
            limpiar_cargar();
            ocultar(); 
            cargarCbos();
    }

     
  public final void cargarCbos(){        
try  {
             
              String sql1 = "SELECT descrip FROM pais order by 1 asc";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql1);
             while (rs.next()){  
                cboDestino.addItem(rs.getString(1));
                cboOrigen.addItem(rs.getString(1));
             }
          rs.close();
          st.close();
        }
     catch ( SQLException excepcionSql ) {
         JOptionPane.showMessageDialog( null, excepcionSql.getMessage(),
            "Error al cargar listas", JOptionPane.ERROR_MESSAGE );
     }
  }

   public void save(int tipo_de_guardado){
      // if(tipo_de_guardado==1){//------------------------------------------------------------------------------------------------------------------------------
        try{
//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//String xfp = String.valueOf(sdf.format(jDateChooser1.getDate()));
String cOrig=DB.buscarCod("SELECt id_pais from pais where descrip='"+cboOrigen.getSelectedItem()+"'");
String cDest=DB.buscarCod("SELECt id_pais from pais where descrip='"+cboDestino.getSelectedItem()+"'");
              
                if( bandera==3){
                    cod=aop;
                    //DB.con.setAutoCommit(true);
                }else{
                    cod=txtCPed.getText();
                   // DB.con.setAutoCommit(false);
                }
                //ban integer, cod integer, fecha date, des character varying, desp integer, rped integer, est integer
  String sql="select abm_coti('"+bandera+"',"
          + "'"+cod+"',"
          + ""+txtCodCLi.getText()+",'"+txtPeso.getText()+"',"
          + "'"+txtVOL.getText()+"','"+txtCoti.getText()+"','"+cOrig+"','"+cDest+"');";
  System.out.print("\n"+sql);
  st = DB.con.createStatement();
  st.executeQuery(sql);     
  System.out.print("\n PROCEDMIENTO "+sql);
 // new reporte().reporteConParametro("coti",1,DB.con);
            } catch (SQLException e)
                     {JOptionPane.showMessageDialog( null, e.getMessage(),"Error en la interaccón con la Base de Datos CAB", JOptionPane.ERROR_MESSAGE );                                }   
    //   }
         this.btnAdd.requestFocus();
      }        
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtCPed = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtCodCLi = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        txtCli = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cboDestino = new javax.swing.JComboBox();
        cboOrigen = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        txtVOL = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtPeso = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCoti = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle(" Cotizar Flete");

        btnAdd.setText("Agregar");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setText("Anular");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la cotizacion de flete"));

        jLabel3.setText("Codigo");

        txtCPed.setEnabled(false);

        jLabel8.setText("Cliente");

        jLabel16.setText("Destino");

        txtCodCLi.setEnabled(false);

        btnSearch.setText("...");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        txtCli.setEnabled(false);
        txtCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliActionPerformed(evt);
            }
        });

        jLabel10.setText("Origen");

        cboDestino.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir" }));

        cboOrigen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir" }));

        jLabel1.setText("Volumen");

        txtVOL.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel2.setText("Mt 3");

        jLabel4.setText("Peso");

        txtPeso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPeso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesoKeyPressed(evt);
            }
        });

        jLabel5.setText("Kg");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Cotizacion");

        txtCoti.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCoti.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCoti.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCodCLi, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCPed, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtVOL, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(cboOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(46, 46, 46)
                                        .addComponent(jLabel16)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cboDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtPeso)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel5))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtCoti, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 143, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCPed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCodCLi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cboOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(cboDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtVOL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCoti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExit)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnDelete)
                    .addComponent(btnSave)
                    .addComponent(btnExit)
                    .addComponent(btnCancelar))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
 bandera=1;
limpiar_cargar();
cod=DB.gencod("SELECT coalesce(MAX(id_cot),0)+1 FROM coti_flete");
txtCPed.setText(cod);
btnAdd.setEnabled(false);
btnCancelar.setEnabled(true);
btnSave.setEnabled(true);
//---------
cboOrigen.setEnabled(true);
txtCodCLi.setEnabled(true);
btnSearch.setEnabled(true);
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

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
validar_blancos();
limpiar_cargar();
ocultar();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed

        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

    ocultar(); 
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
Buscador b= new Buscador(null,true, "select id_docc,concat (id_docc,' - ',nombres,', ',apellidos)as cli from cliente where concat(nombres,' ',apellidos) LIKE", new String[]{"Documento","Cliente"}, new Object[]{txtCodCLi,txtCli}, DB.con);
b.setTitle("Buscador de Clientes");
b.setVisible(true);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtPesoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesoKeyPressed

        if(evt.getKeyCode()==10){
          String cOrig=DB.buscarCod("SELECt precio from pais where descrip='"+cboOrigen.getSelectedItem()+"'");
String cDest=DB.buscarCod("SELECt precio from pais where descrip='"+cboDestino.getSelectedItem()+"'");
String total=String.valueOf(((Integer.parseInt(cOrig)+Integer.parseInt(cDest))*Integer.parseInt(txtVOL.getText()))*Integer.parseInt(txtPeso.getText()));
        txtCoti.setText(total);  
        JOptionPane.showMessageDialog(this, "El costo estimado del flete es de "+total+"Gs.");
        }
    }//GEN-LAST:event_txtPesoKeyPressed

    private void txtCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox cboDestino;
    private javax.swing.JComboBox cboOrigen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtCPed;
    private javax.swing.JTextField txtCli;
    private javax.swing.JTextField txtCodCLi;
    private javax.swing.JTextField txtCoti;
    private javax.swing.JTextField txtPeso;
    private javax.swing.JTextField txtVOL;
    // End of variables declaration//GEN-END:variables

private void ocultar(){  
    btnAdd.setEnabled(true);
    btnAdd.requestFocus();
    btnCancelar.setEnabled(false);
    btnDelete.setEnabled(false);
    btnSave.setEnabled(false); 
}


private void limpiar_cargar(){
    
    txtCPed.setText("");
    txtPeso.setText("");
    cboDestino.setSelectedIndex(0);
    cboOrigen.setSelectedIndex(0);
    txtVOL.setText("");
    txtCoti.setText("");
    txtCodCLi.setText("");
    txtCli.setText("");
}
 
  public void validarDuplicados(){   
      try  {
          String sql = "select * from coti_flete where id_cot='"+txtCPed.getText()+"'";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql);
boolean find = rs.next();
if(find==true){
    JOptionPane.showMessageDialog(null, "Ya existe una Cotizacion con ese Codigo", "Alerta", JOptionPane.ERROR_MESSAGE);
}else{
    save(1);
//    jDateChooser1.setEnabled(false);
//cboDesp.setEnabled(false);
//txtCodCLi.setEnabled(false);
//btnSearch.setEnabled(false);
//txtFDesc.setEnabled(false);
////----------
//cboD_mer_ped.setEnabled(true);
//txtCantidad.setEnabled(true);
//cboD_mer_ped.requestFocus();
    
}
          rs.close();
          st.close();
        }
     catch ( SQLException excepcionSql ){
         JOptionPane.showMessageDialog( null, excepcionSql.getMessage(),
            "Error al validar duplicados", JOptionPane.ERROR_MESSAGE );
  }}
  
     private void validar_blancos(){      
if( cboOrigen.getSelectedIndex()==0 || txtCodCLi.getText().trim().isEmpty() || txtCPed.getText().trim().isEmpty()
        || cboDestino.getSelectedIndex()==0) {
    JOptionPane.showMessageDialog(null, "complete todos los campos", "Alerta", JOptionPane.ERROR_MESSAGE);
}else{
    validarDuplicados();
    
}       
     }
}
