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
public class Pedido_Flete extends javax.swing.JInternalFrame {

    private conectarDB DB;
    private Statement st;
    private ResultSet rs;
    int bandera;
    String aop,cod;
    public Pedido_Flete() {
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
        String datos[] = new String[3];
        Integer i = 1;
        try  {
         st= DB.con.createStatement();
         String sql = "SELECT d.item,m.descrip,d.cantidad from mercaderia m,d_merc d where m.id_merc=d.id_merc and id_ped='"+parametro+"' ";
         rs= st.executeQuery(sql);
             while (rs.next()){
              datos[0]= rs.getString(1);              
              datos[1]= rs.getString(2);
              datos[2]= rs.getString(3);

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
                           String sql3 = "SELECT descrip FROM tipo_carg order by 1 asc";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql3);
             while (rs.next()){  
                cboDesp1.addItem(rs.getString(1));
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
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String xfp = String.valueOf(sdf.format(jDateChooser1.getDate()));
String cDesp=DB.buscarCod("SELECt id_desp from despachante where concat(id_desp,' - ',nombres,', ',apellidos)='"+cboDesp.getSelectedItem()+"'");
String cTic=DB.buscarCod("SELECt id_tic from tipo_carg where descrip='"+cboDesp1.getSelectedItem()+"'");
              
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
          + "'"+sdf.format(jDateChooser1.getDate())+"',"
          + "'"+txtFDesc.getText().trim()+"','"+cDesp+"','"+txtCodRegPed.getText()+"','"+cTic+"');";
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
          + "'"+cod+"','"+txtItem.getText()+"','"+txtCantidad.getText()+"');";
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
        btnEdit = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtCPed = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        cboDesp = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtFDesc = new javax.swing.JTextField();
        txtCodRegPed = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        txtCli = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cboDesp1 = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboD_mer_ped = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtItem = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Pedido de Flete ");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Ped.", "Fecha", "Descripcion", "Despachante", "Nro. Ped.", "Estado"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
        }

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

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Mercaderia", "Cantidad"
            }
        ));
        jScrollPane1.setViewportView(jTable2);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Pedido de Flete"));

        jLabel3.setText("Cod. Pedido:");

        txtCPed.setEnabled(false);

        jLabel10.setText("Fecha:");

        jDateChooser1.setEnabled(false);

        jLabel11.setText("Despachante:");

        cboDesp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- ELEGIR --" }));
        cboDesp.setEnabled(false);

        jLabel8.setText("Nro. Pedido:");

        jLabel16.setText("Descripción:");

        txtFDesc.setEnabled(false);
        txtFDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFDescKeyPressed(evt);
            }
        });

        txtCodRegPed.setEnabled(false);

        btnSearch.setText("...");
        btnSearch.setEnabled(false);
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

        jLabel12.setText("Tipo de Carga:");

        cboDesp1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- ELEGIR --" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel3)))
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCPed, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboDesp, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtCodRegPed, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSearch))
                            .addComponent(cboDesp1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFDesc)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtCli, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cboDesp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCodRegPed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addGap(13, 13, 13)
                .addComponent(txtCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cboDesp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtFDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle del Pedido de Flete"));

        jLabel1.setText("Mercaderia:");

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

        txtItem.setText("0");
        txtItem.setEnabled(false);
        txtItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtItemKeyPressed(evt);
            }
        });

        jLabel5.setText("Pallets");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboD_mer_ped, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)))))
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboD_mer_ped, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap())
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
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                .addComponent(btnEdit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnExit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar)))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnDelete)
                            .addComponent(btnEdit)
                            .addComponent(btnSave)
                            .addComponent(btnExit)
                            .addComponent(btnCancelar))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
 bandera=1;
limpiar_cargar();
cod=DB.gencod("SELECT coalesce(MAX(id_ped),0)+1 FROM ped_flete");
txtCPed.setText(cod);
jDateChooser1.requestFocus();
btnAdd.setEnabled(false);
btnCancelar.setEnabled(true);
btnSave.setEnabled(true);
//---------
jDateChooser1.setEnabled(true);
cboDesp.setEnabled(true);
txtCodRegPed.setEnabled(true);
btnSearch.setEnabled(true);
txtFDesc.setEnabled(true);
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
         try {
            if(!DB.con.getAutoCommit()){
                DB.con.commit();
                        
            }
            if(!DB.con.getAutoCommit())DB.con.setAutoCommit(true);
           // System.out.print("\nCOMMIT");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog( this, e.getMessage(),"Error al Guardar", JOptionPane.ERROR_MESSAGE ); 
        }  
         new reporte().reporteConParametro("ped_flete",Integer.parseInt(txtCPed.getText()), DB.con);
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

    private void txtFDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFDescKeyPressed
 if(evt.getKeyCode()==10){
     validar_blancos();
 }
     // TODO add your handling code here:
    }//GEN-LAST:event_txtFDescKeyPressed

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
 if(evt.getKeyCode()==10){
    if(cboD_mer_ped.getSelectedIndex()!=0 && !txtCantidad.getText().trim().isEmpty()){
        txtItem.setText(String.valueOf(Integer.parseInt(txtItem.getText())+1));
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
txtFDesc.setText(jTable1.getValueAt(jTable1.getSelectedRow(),2).toString());//saca datos del jtable
txtCPed.setText(jTable1.getValueAt(jTable1.getSelectedRow(),0).toString());
cboDesp.setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(),3).toString());
txtCodRegPed.setText(jTable1.getValueAt(jTable1.getSelectedRow(),4).toString());
btnEdit.setEnabled(true);
btnDelete.setEnabled(true);
btnCancelar.setEnabled(true);
btnAdd.setEnabled(false);
cargarTabladETALLE(aop);
    }//GEN-LAST:event_jTable1MousePressed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
Buscador b= new Buscador(null,true, "select p.id_rped,concat(c.nombres,' -',c.apellidos)as cli"
        + " FROM r_pedido p, cliente c "
        + "WHERE p.id_est=1 and c.id_est=1 and c.id_docc=p.id_docc and concat(c.nombres,' -',c.apellidos) LIKE", new String[]{"Codigo","Cliente"}, new Object[]{txtCodRegPed,txtCli}, DB.con);
b.setTitle("Buscador de Registro de Pedido");
b.setVisible(true);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void cboD_mer_pedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboD_mer_pedActionPerformed
//if(cboD_mer_ped.getSelectedIndex())//
    }//GEN-LAST:event_cboD_mer_pedActionPerformed

    private void txtItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItemKeyPressed

    private void txtCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox cboD_mer_ped;
    private javax.swing.JComboBox cboDesp;
    private javax.swing.JComboBox cboDesp1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txtCPed;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCli;
    private javax.swing.JTextField txtCodRegPed;
    private javax.swing.JTextField txtFDesc;
    private javax.swing.JTextField txtItem;
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
    limpiarTabla(jTable1);
    cargarTabla();
    
    txtCPed.setText("");
    txtFDesc.setText("");
    cboDesp.setSelectedIndex(0);
    txtCantidad.setText("");
    cboD_mer_ped.setSelectedIndex(0);
    txtCodRegPed.setText("");
    txtCli.setText("");
    
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
          String sql = "select * from ped_flete where id_ped='"+txtCPed.getText()+"'";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql);
boolean find = rs.next();
if(find==true){
    JOptionPane.showMessageDialog(null, "Ya existe un Pedido de flete con ese Codigo", "Alerta", JOptionPane.ERROR_MESSAGE);
}else{
    save(1);
    jDateChooser1.setEnabled(false);
cboDesp.setEnabled(false);
txtCodRegPed.setEnabled(false);
btnSearch.setEnabled(false);
txtFDesc.setEnabled(false);
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
        || txtFDesc.getText().trim().isEmpty()) {
    JOptionPane.showMessageDialog(null, "complete todos los campos", "Alerta", JOptionPane.ERROR_MESSAGE);
}else{
    validarDuplicados();
    
}       
     }
}
