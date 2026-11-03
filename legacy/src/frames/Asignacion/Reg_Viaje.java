/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package frames.Asignacion;

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
public class Reg_Viaje extends javax.swing.JInternalFrame {

    private conectarDB DB;
    private Statement st;
    private ResultSet rs;
    int bandera;
    String aop,cod;
    public Reg_Viaje() {
        initComponents();
      DB = new conectarDB();
            limpiar_cargar();
            ocultar(); 
            cargarCbos();
            cargarTabla();
    }

        private void cargarTabla(){
        DefaultTableModel modelo = (DefaultTableModel)this.jTable1.getModel();
            limpiarTabla(jTable1);
        String datos[] = new String[7];
        Integer i = 1;
        try  {
         st= DB.con.createStatement();
         String sql = "SELECT * FROM r_viaje where id_est=1";
         rs= st.executeQuery(sql);
             while (rs.next()){
              datos[0]= rs.getString(1);              
              datos[1]= rs.getString(2);
              datos[2]= rs.getString(3);
              datos[3]= rs.getString(4);
              datos[4]= rs.getString(5);              
              datos[5]= rs.getString(6);
              datos[6]= rs.getString(7);

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
        
                private void cargarTabladETALLE(String parametro){
        DefaultTableModel modelo = (DefaultTableModel)this.jTable2.getModel();
                    limpiarTabla(jTable2);
        String datos[] = new String[2];
        Integer i = 1;
        try  {
         st= DB.con.createStatement();
         String sql = "SELECT m.descrip,d.descrip from mercaderia m,d_merc d where m.id_merc=d.id_merc and id_ped='"+parametro+"' ";
         rs= st.executeQuery(sql);
             while (rs.next()){
              datos[0]= rs.getString(1);              
              datos[1]= rs.getString(2);

              modelo.addRow(datos);
              i++;
             }
          rs.close();
          st.close();
        }
     catch ( SQLException excepcionSql ) {
         JOptionPane.showMessageDialog( null, excepcionSql.getMessage(),
            "Error al cargar la tabla detalle", JOptionPane.ERROR_MESSAGE );
          System.exit( 1 );
      }
 }
     
  public final void cargarCbos(){        
try  {
             
              String sql1 = "SELECT descrip FROM itinerario order by 1 asc";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql1);
             while (rs.next()){  
                cboIti.addItem(rs.getString(1));//itinerario
             }
             
              String sql3 = "SELECT descrip FROM pais order by 1 asc";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql3);
             while (rs.next()){  
                cboOrigen.addItem(rs.getString(1));//origen
                cboDest.addItem(rs.getString(1));//destino
             }
             
              String sql4 = "SELECT descrip FROM ruta order by 1 asc";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql4);
             while (rs.next()){  
                cboRuta.addItem(rs.getString(1));//ruta
             }
             
//              String sql5 = "SELECT concat(id_desp,' - ',nombres,', ',apellidos) FROM despachante order by 1 asc";
//         st = DB.con.createStatement();
//         rs = st.executeQuery(sql5);//wtf
//             while (rs.next()){  
//              //  cboDesp.addItem(rs.getString(1));
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
       if(tipo_de_guardado==1){//------------------------------------------------------------------------------------------------------------------------------
        try{
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String xfp = String.valueOf(sdf.format(jDateChooser1.getDate()));
String cDest=DB.buscarCod("SELECt id_pais from pais where descrip='"+cboDest.getSelectedItem()+"'");
String cOrig=DB.buscarCod("SELECt id_pais from pais where descrip='"+cboDest.getSelectedItem()+"'");
String cRuta=DB.buscarCod("SELECt id_pais from pais where descrip='"+cboDest.getSelectedItem()+"'");
String cIti=DB.buscarCod("SELECt id_pais from pais where descrip='"+cboDest.getSelectedItem()+"'");
              
                if( bandera==3){
                    cod=aop;
                    DB.con.setAutoCommit(true);
                }else{
                    cod=txtCod.getText();
                    DB.con.setAutoCommit(false);
                }
                //ban integer, cod integer, fecha date, des character varying, desp integer, rped integer, est integer
  String sql="select abm_ped('"+bandera+"',"
          + "'"+cod+"',"
          + "'"+xfp+"',"
          + "'"+cDest+"');";
  st = DB.con.createStatement();
  st.executeQuery(sql);     
  cboD_mer_ped.requestFocus();
  cargarTabla();
  System.out.print("\n PROCEDMIENTO "+sql);
            } catch (SQLException e)
                     {JOptionPane.showMessageDialog( null, e.getMessage(),"Error en la interaccón con la Base de Datos CAB", JOptionPane.ERROR_MESSAGE );                                }   
       }else{//-------------------------------------------------------------------inicio del detalle--------------------------------------------------------
         try{
  String sql="select abm_dmerc('"+bandera+"','"+DB.buscarCod("SELECT id_merc from mercaderia where descrip='"+cboD_mer_ped.getSelectedItem()+"'")+"',"
          + "'"+cod+"');";
  st = DB.con.createStatement();
                  st.executeQuery(sql);  
                  cboD_mer_ped.requestFocus();
                  cargarTabladETALLE(cod);
                  System.out.print("\n PROCEDMIENTO DETALLE"+sql);
            } catch (SQLException e)
                     {JOptionPane.showMessageDialog( null, e.getMessage(),
            "Error en la interaccón con la Base de Datos DET", JOptionPane.ERROR_MESSAGE );
                     }  
       }//----------------------------------------------------------------------------------------------------------------------------------------------------
         this.btnAdd.requestFocus();
      }        
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtCod = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cboIti = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cboOrigen = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        cboDest = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cboRuta = new javax.swing.JComboBox();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboD_mer_ped = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtItem = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle("Reg. Viaje");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Viaje", "F. Salida", "Estado", "Itinerario", "Origen", "Destino", "Ruta"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

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

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Asignacion"
            }
        ));
        jScrollPane1.setViewportView(jTable2);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Pedido de Flete"));

        jLabel3.setText("Cod. Viaje:");

        txtCod.setEnabled(false);
        txtCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodActionPerformed(evt);
            }
        });

        jLabel10.setText("Fecha Sal:");

        cboIti.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- ELEGIR --" }));

        jLabel12.setText("Itinerario:");

        jLabel5.setText("Origen:");

        cboOrigen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- ELEGIR --" }));

        jLabel6.setText("Destino:");

        cboDest.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- ELEGIR --" }));

        jLabel7.setText("Ruta:");

        cboRuta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- ELEGIR --" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 5, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cboOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(cboIti, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(cboDest, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cboIti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cboOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboDest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle del Viaje"));

        jLabel1.setText("Asignacion:");

        cboD_mer_ped.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- ELEGIR --" }));
        cboD_mer_ped.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboD_mer_pedActionPerformed(evt);
            }
        });

        jLabel4.setText("Item:");

        txtItem.setEditable(false);
        txtItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboD_mer_ped, 0, 150, Short.MAX_VALUE)
                    .addComponent(txtItem))
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboD_mer_ped, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(11, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(91, 91, 91)
                                .addComponent(btnAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCancelar)))
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnDelete)
                            .addComponent(btnSave)
                            .addComponent(btnExit)
                            .addComponent(btnCancelar))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jPanel1.getAccessibleContext().setAccessibleName("Datos del Viaje...");
        jPanel2.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
 bandera=1;
limpiar_cargar();
cod=DB.gencod("SELECT coalesce(MAX(id_viaj),0)+1 FROM r_viaje");
txtCod.setText(cod);
jDateChooser1.requestFocus();
btnAdd.setEnabled(false);
btnCancelar.setEnabled(true);
btnSave.setEnabled(true);
//---------
jDateChooser1.setEnabled(true);
//cboDesp.setEnabled(true);
//txtCodRegPed.setEnabled(true);
//btnSearch.setEnabled(true);
//txtFDesc.setEnabled(true);
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
         try {
            if(!DB.con.getAutoCommit()){
                DB.con.commit();
                        new reporte().reporteConParametro("ped_flete",Integer.parseInt(txtCod.getText()), DB.con);
            }
            if(!DB.con.getAutoCommit())DB.con.setAutoCommit(true);
           // System.out.print("\nCOMMIT");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog( this, e.getMessage(),"Error al Guardar", JOptionPane.ERROR_MESSAGE ); 
        }  
    limpiar_cargar();
        limpiarTabla(jTable2);
    ocultar(); 
   // txtCantidad.setEnabled(false);
    cboD_mer_ped.setEnabled(false);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        try {
            if(!DB.con.getAutoCommit())DB.con.setAutoCommit(true);
        } catch (Exception e) {
        }
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarTabla(jTable2);
        try {
           if(!DB.con.getAutoCommit())DB.con.rollback();
           if(!DB.con.getAutoCommit())DB.con.setAutoCommit(true);
          //  System.out.print("\nROLLBACK");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog( this, e.getMessage(),"Error al Guardar", JOptionPane.ERROR_MESSAGE ); 
        }  
    limpiar_cargar();
    ocultar(); 
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
aop=(jTable1.getValueAt(jTable1.getSelectedRow(),0).toString());
txtCod.setText(aop);
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	String dateInString = jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString();
 Date date = null;
	try {
 
		 date = formatter.parse(dateInString);
                 jDateChooser1.setDate(date);
	//	System.out.println(date);
		//System.out.println(formatter.format(date));
 
	} catch (ParseException e) {
	}
//txtFDesc.setText(jTable1.getValueAt(jTable1.getSelectedRow(),2).toString());//saca datos del jtable
//txtCPed.setText(jTable1.getValueAt(jTable1.getSelectedRow(),0).toString());
//cboDesp.setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(),3).toString());
//txtCodRegPed.setText(jTable1.getValueAt(jTable1.getSelectedRow(),4).toString());
//btnEdit.setEnabled(true);
btnDelete.setEnabled(true);
btnCancelar.setEnabled(true);
btnAdd.setEnabled(false);
cargarTabladETALLE(aop);
    }//GEN-LAST:event_jTable1MousePressed

    private void cboD_mer_pedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboD_mer_pedActionPerformed
//if(cboD_mer_ped.getSelectedIndex())//
    }//GEN-LAST:event_cboD_mer_pedActionPerformed

    private void txtItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItemActionPerformed

    private void txtCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cboD_mer_ped;
    private javax.swing.JComboBox cboDest;
    private javax.swing.JComboBox cboIti;
    private javax.swing.JComboBox cboOrigen;
    private javax.swing.JComboBox cboRuta;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtItem;
    // End of variables declaration//GEN-END:variables

private void ocultar(){  
    btnAdd.setEnabled(true);
    btnAdd.requestFocus();
    btnCancelar.setEnabled(false);
    btnDelete.setEnabled(false);
//    btnEdit.setEnabled(false);
    btnSave.setEnabled(false); 
}


private void limpiar_cargar(){
    limpiarTabla(jTable1);
    cargarTabla();
    
    txtItem.setText("");
    cboIti.setSelectedIndex(0);
    cboOrigen.setSelectedIndex(0);
    cboDest.setSelectedIndex(0);
    cboRuta.setSelectedIndex(0);
    cboD_mer_ped.setSelectedIndex(0);
    txtCod.setText("");
    
//    cboNrP.setSelectedIndex(0);
    jDateChooser1.setDate(null);
}

  private void limpiarTabla(JTable tabla){
        DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
        while(tabla.getRowCount() != 0)
            modelo.removeRow(0);
  }
 
  public void validarDuplicados(){   
      try  {
          String sql = "select * from reg_viaje where id_ped='"+txtCod.getText()+"'";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql);
boolean find = rs.next();
if(find==true){
    JOptionPane.showMessageDialog(null, "Ya existe un Registro de viaje con ese Codigo", "Alerta", JOptionPane.ERROR_MESSAGE);
}else{
    save(1);
    jDateChooser1.setEnabled(false);
cboIti.setEnabled(false);
txtCod.setEnabled(false);
//----------
cboD_mer_ped.setEnabled(true);
txtItem.setEnabled(true);
cboD_mer_ped.requestFocus();
}
          rs.close();
          st.close();
        }
     catch ( SQLException excepcionSql ){
         JOptionPane.showMessageDialog( null, excepcionSql.getMessage(),
            "Error al validar duplicados", JOptionPane.ERROR_MESSAGE );
  }}
  
     private void validar_blancos(){      
if( cboIti.getSelectedIndex()==0 || txtCod.getText().trim().isEmpty()) {
    JOptionPane.showMessageDialog(null, "complete todos los campos", "Alerta", JOptionPane.ERROR_MESSAGE);
}else{
    validarDuplicados();
    
}       
     }
}
