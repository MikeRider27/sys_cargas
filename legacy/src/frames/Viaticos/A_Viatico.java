/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package frames;

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
public class A_Viatico extends javax.swing.JInternalFrame {

    private conectarDB DB;
    private Statement st;
    private ResultSet rs;
    int bandera;
    String aop,cod;
    public A_Viatico() {
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
        String datos[] = new String[6];
        Integer i = 1;
        try  {
         st= DB.con.createStatement();
         String sql = "SELECT * FROM ped_flete where id_est=1";
         rs= st.executeQuery(sql);
             while (rs.next()){
              datos[0]= rs.getString(1);              
              datos[1]= rs.getString(2);
              datos[2]= rs.getString(3);
              datos[3]= rs.getString(4);
              datos[4]= rs.getString(5);              
              datos[5]= rs.getString(6);

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
             
              String sql1 = "SELECT descrip FROM mercaderia order by 1 asc";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql1);
             while (rs.next()){  
                cboD_mer_ped.addItem(rs.getString(1));
             }
             
              String sql2 = "SELECT concat(id_desp,' - ',nombres,', ',apellidos) FROM despachante order by 1 asc";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql2);
             while (rs.next()){  
                cboDesp.addItem(rs.getString(1));
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
       if(tipo_de_guardado==1){//------------------------------------------------------------------------------------------------------------------------------
        try{
String cDesp=DB.buscarCod("SELECt id_desp from despachante where concat(id_desp,' - ',nombres,', ',apellidos)='"+cboDesp.getSelectedItem()+"'");
              
                if( bandera==3){
                    cod=aop;
                    DB.con.setAutoCommit(true);
                }else{
                    cod=txtCPed.getText();
                    DB.con.setAutoCommit(false);
                }
                //ban integer, cod integer, fecha date, des character varying, desp integer, rped integer, est integer
  String sql="select abm_ped('"+bandera+"',"
          + "'"+cod+"',"
          + "'"+cDesp+"','"+txtCodRegPed.getText()+"');";
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
          + "'"+cod+"','"+txtCantidad.getText()+"');";
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
        txtCPed = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cboDesp = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        txtCodRegPed = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboD_mer_ped = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        setClosable(true);
        setIconifiable(true);
        setTitle("Asig. Viaticos ");

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
                "Title 1Item", "Moneda", "Monto"
            }
        ));
        jScrollPane1.setViewportView(jTable2);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Asignacion de viaticos"));

        jLabel3.setText("Cod. Asig.:");

        txtCPed.setEnabled(false);
        txtCPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCPedActionPerformed(evt);
            }
        });

        jLabel10.setText("Fecha:");

        jLabel11.setText("Conductor:");

        cboDesp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- ELEGIR --" }));
        cboDesp.setEnabled(false);

        jLabel8.setText("Cod. Viaje:");

        txtCodRegPed.setEnabled(false);

        btnSearch.setText("...");
        btnSearch.setEnabled(false);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCodRegPed, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearch))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cboDesp, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCPed, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCPed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cboDesp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCodRegPed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle de la Asignacion de Viaticos"));

        jLabel1.setText("Moneda:");

        cboD_mer_ped.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- ELEGIR --" }));
        cboD_mer_ped.setEnabled(false);
        cboD_mer_ped.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboD_mer_pedActionPerformed(evt);
            }
        });

        jLabel2.setText("Cantidad:");

        txtCantidad.setEnabled(false);
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
        });

        jLabel4.setText("Item:");

        jTextField1.setEditable(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel5.setText("total");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboD_mer_ped, 0, 150, Short.MAX_VALUE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jTextField2))
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboD_mer_ped, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Fecha", "Cod. Viaje", "Conductor"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addGap(10, 10, 10)
                        .addComponent(btnExit))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnDelete)
                            .addComponent(btnSave)
                            .addComponent(btnExit)
                            .addComponent(btnCancelar)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        jPanel2.getAccessibleContext().setAccessibleName("Detalle de Asuçignacion de Viaticos");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
 bandera=1;
limpiar_cargar();
cod=DB.gencod("SELECT coalesce(MAX(id_ped),0)+1 FROM ped_flete");
txtCPed.setText(cod);
btnAdd.setEnabled(false);
btnCancelar.setEnabled(true);
btnSave.setEnabled(true);
//---------
cboDesp.setEnabled(true);
txtCodRegPed.setEnabled(true);
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
         try {
            if(!DB.con.getAutoCommit()){
                DB.con.commit();
                        new reporte().reporteConParametro("ped_flete",Integer.parseInt(txtCPed.getText()), DB.con);
            }
            if(!DB.con.getAutoCommit())DB.con.setAutoCommit(true);
           // System.out.print("\nCOMMIT");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog( this, e.getMessage(),"Error al Guardar", JOptionPane.ERROR_MESSAGE ); 
        }  
    limpiar_cargar();
        limpiarTabla(jTable2);
    ocultar(); 
    txtCantidad.setEnabled(false);
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

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
 if(evt.getKeyCode()==10){
    if(cboD_mer_ped.getSelectedIndex()!=0 && !txtCantidad.getText().trim().isEmpty()){
        save(2);
        txtCantidad.setText("");
        cboD_mer_ped.requestFocus();
        cboD_mer_ped.setSelectedIndex(0);
    }
 }
    }//GEN-LAST:event_txtCantidadKeyPressed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
aop=(jTable1.getValueAt(jTable1.getSelectedRow(),0).toString());
txtCPed.setText(aop);
//txtFDesc.setText(jTable1.getValueAt(jTable1.getSelectedRow(),2).toString());//saca datos del jtable
txtCPed.setText(jTable1.getValueAt(jTable1.getSelectedRow(),0).toString());
cboDesp.setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(),3).toString());
txtCodRegPed.setText(jTable1.getValueAt(jTable1.getSelectedRow(),4).toString());
btnDelete.setEnabled(true);
btnCancelar.setEnabled(true);
btnAdd.setEnabled(false);
cargarTabladETALLE(aop);
    }//GEN-LAST:event_jTable1MousePressed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
Buscador b= new Buscador(null,true, "select p.id_rped,concat(c.nombres,' -',c.apellidos)as cli"
        + " FROM r_pedido p, cliente c "
        + "WHERE p.id_est=1 and c.id_est=1 and c.id_docc=p.id_docc and concat(c.nombres,' -',c.apellidos) LIKE", new String[]{"Codigo","Cliente"}, new Object[]{txtCodRegPed}, DB.con);
b.setTitle("Buscador de Registro de Pedido");
b.setVisible(true);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void cboD_mer_pedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboD_mer_pedActionPerformed
//if(cboD_mer_ped.getSelectedIndex())//
    }//GEN-LAST:event_cboD_mer_pedActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void txtCPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPedActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox cboD_mer_ped;
    private javax.swing.JComboBox cboDesp;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField txtCPed;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodRegPed;
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
    
    txtCPed.setText("");
//    txtFDesc.setText("");
    cboDesp.setSelectedIndex(0);
    txtCantidad.setText("");
    cboD_mer_ped.setSelectedIndex(0);
    txtCodRegPed.setText("");
//    txtCli.setText("");
    
//    cboNrP.setSelectedIndex(0);
}

  private void limpiarTabla(JTable tabla){
        DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
        while(tabla.getRowCount() != 0)
            modelo.removeRow(0);
  }
 
  public void validarDuplicados(){   
      try  {
          String sql = "select * from ped_flete where id_ped='"+txtCPed.getText()+"'";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql);
boolean find = rs.next();
if(find==true){
    JOptionPane.showMessageDialog(null, "Ya existe un Pedido de flete con ese Codigo", "Alerta", JOptionPane.ERROR_MESSAGE);
}else{
    save(1);
cboDesp.setEnabled(false);
txtCodRegPed.setEnabled(false);
btnSearch.setEnabled(false);
//txtFDesc.setEnabled(false);
//----------
cboD_mer_ped.setEnabled(true);
txtCantidad.setEnabled(true);
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
if( cboDesp.getSelectedIndex()==0 || txtCodRegPed.getText().trim().isEmpty() || txtCPed.getText().trim().isEmpty()
        ) {
    JOptionPane.showMessageDialog(null, "complete todos los campos", "Alerta", JOptionPane.ERROR_MESSAGE);
}else{
    validarDuplicados();
    
}       
     }
}
