/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames.Pedidos;

import frames.Buscador;
import frames.Buscador_antiTembo;
import frames.Login;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import programas.conectarDB;
import programas.reporte;

/**
 *
 * @author Kuroryuu
 */
public class Reg_crt extends javax.swing.JInternalFrame {

   private conectarDB DB;
    private Statement st;
    private ResultSet rs;
    int bandera;
    String aop,cod;
    public Reg_crt() {
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
        String datos[] = new String[10];
        Integer i = 1;
        try  {
         st= DB.con.createStatement();
         String sql = "SELECT * FROM emi_crt";
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
              datos[9]= rs.getString(10);
              
              

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
        String datos[] = new String[6];
        Integer i = 1;
        try  {
         st= DB.con.createStatement();
         String sql = "select d.item,d.valor,m.descrip,d.canti,d.vol,d.peso from d_m_emi d, moneda m where m.id_mon=d.id_mon and id_crt='"+parametro+"' ";
         rs= st.executeQuery(sql);
             while (rs.next()){
              datos[0]= rs.getString(1);              
              datos[1]= rs.getString(2);
              datos[2]= rs.getString(3);
              datos[3]= rs.getString(4);              
              datos[4]= rs.getString(5);
              datos[5]= rs.getString(6);
             // datos[6]= rs.getString(7);

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
             
                String sql1 = "SELECT descrip FROM pais order by 1 asc";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql1);
             while (rs.next()){  
                cboDestino.addItem(rs.getString(1));
                cboOrigen.addItem(rs.getString(1));
             }
             
              String sql2 = "SELECT descrip FROM aduana order by 1 asc";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql2);
             while (rs.next()){  
                cboAdu.addItem(rs.getString(1));
             }
                           String sql3 = "SELECT descrip FROM mercaderia order by 1 asc";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql3);
             while (rs.next()){  
                cboMerca.addItem(rs.getString(1));
             }
                           String sql4 = "SELECT descrip FROM moneda order by 1 asc";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql4);
             while (rs.next()){  
                cboMoneda.addItem(rs.getString(1));
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
String xfp = String.valueOf(sdf.format(jDateChooser2.getDate()));
String cAdu=DB.buscarCod("SELECt id_adu from aduana where descrip='"+cboAdu.getSelectedItem()+"'");
String cOrig=DB.buscarCod("SELECt id_pais from pais where descrip='"+cboOrigen.getSelectedItem()+"'");
String cDest=DB.buscarCod("SELECt id_pais from pais where descrip='"+cboOrigen.getSelectedItem()+"'");
//String cImp=DB.buscarCod("SELECt id_tic from tipo_carg where descrip='"+cboDesp1.getSelectedItem()+"'");
              
                if( bandera==3){
                    cod=aop;
                    DB.con.setAutoCommit(true);
                }else{
                    cod=txtCOd.getText();
                    DB.con.setAutoCommit(false);
                }
                //ban integer, cod integer, fecha date, des character varying, desp integer, rped integer, est integer
  String sql="select abm_crt('"+bandera+"',"
          + "'"+cod+"','2016',"
          + "'"+sdf.format(jDateChooser2.getDate())+"','"+ Login.cod_usuario+"',"
          + "'"+txtCodCLiexp.getText()+"','"+txtCodCLiimp.getText()+"','"+cAdu+"','"+cOrig+"','"+cDest+"');";
  st = DB.con.createStatement();
  st.executeQuery(sql);     
 // cboD_mer_ped.requestFocus();
  cargarTabla();
  System.out.print("\n PROCEDMIENTO "+sql);
  
            } catch (SQLException e)
                     {JOptionPane.showMessageDialog( null, e.getMessage(),"Error en la interaccón con la Base de Datos CAB", JOptionPane.ERROR_MESSAGE );                                }   
       }else{//-------------------------------------------------------------------inicio del detalle--------------------------------------------------------
         String cMon=DB.buscarCod("SELECt id_mon from moneda where descrip='"+cboMoneda.getSelectedItem()+"'");
         String cMer=DB.buscarCod("SELECt id_merc from mercaderia where descrip='"+cboMerca.getSelectedItem()+"'");
           try{
  String sql="select abm_dcrt('"+bandera+"','"+cod+"','2016','"+cMer+"','"+txtItem.getText()+"'"
          + ",'"+txtValor.getText()+"','"+cMon+"','"+txtCant.getText()+"','"+txtVol.getText()+"','"+txtPeso.getText()+"');";
  st = DB.con.createStatement();
                  st.executeQuery(sql);  
                 // cboD_mer_ped.requestFocus();
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

        btnSave = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCOd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cboOrigen = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        cboDestino = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cboAdu = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cboMerca = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        cboMoneda = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        txtCant = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtVol = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtPeso = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtCodCLiimp = new javax.swing.JTextField();
        txtCodCLiexp = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtped = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txtItem = new javax.swing.JTextField();

        setTitle("Registrar CRT");

        btnSave.setText("Guardar");
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

        btnDelete.setText("Anular");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAdd.setText("Agregar");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "P. Origen", "Exportador", "P. Destino", "Impotador", "Aduana"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Codigo:");

        jLabel2.setText("Fecha");

        txtCOd.setEditable(false);

        jLabel3.setText("Importador:");

        jLabel4.setText("Exportador:");

        jLabel5.setText("P. Origen:");

        cboOrigen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir" }));

        jLabel6.setText("P. Destino:");

        cboDestino.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir" }));

        jLabel7.setText("Aduana:");

        cboAdu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir" }));

        jLabel8.setText("Item:");

        jLabel9.setText("Mercaderia:");

        cboMerca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir" }));

        jLabel10.setText("Valor:");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Mercaderia", "Moneda", "Valor", "Cantidad", "Volumen", "Peso"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        cboMoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir" }));
        cboMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMonedaActionPerformed(evt);
            }
        });

        jLabel11.setText("Cantidad:");

        jLabel12.setText("Pallets");

        jLabel13.setText("Volumen:");

        jLabel14.setText("m3");

        jLabel15.setText("Peso:");

        txtPeso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesoKeyPressed(evt);
            }
        });

        jLabel16.setText("Kg");

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

        jLabel17.setText("Moneda");

        jLabel18.setText("segun ped");

        jButton3.setText("...");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("cargar detalle");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(28, 28, 28)
                                .addComponent(txtCant))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cboMerca, 0, 111, Short.MAX_VALUE)
                                    .addComponent(txtValor)
                                    .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addGap(0, 106, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel15))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPeso)
                            .addComponent(txtVol))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16))
                        .addGap(118, 118, 118))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(txtCOd, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(cboOrigen, 0, 186, Short.MAX_VALUE)
                                                        .addComponent(cboDestino, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(txtCodCLiexp, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jButton1))
                                                    .addComponent(cboAdu, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(txtCodCLiimp, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jButton2))
                                                    .addComponent(cboMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(txtped, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jButton3)))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jButton4))))))
                            .addComponent(jLabel7)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel17)
                                .addComponent(jLabel18)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExit)
                        .addGap(18, 18, 18))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1)
                                .addComponent(txtCOd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cboOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtCodCLiexp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cboDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtCodCLiimp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(cboAdu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtped, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(cboMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jButton4)
                            .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboMerca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtVol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnExit)
                            .addComponent(btnSave)
                            .addComponent(btnCancelar)
                            .addComponent(btnDelete)
                            .addComponent(btnAdd))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
 bandera=1;
limpiar_cargar();
cod=DB.gencod("SELECT coalesce(MAX(id_crt),0)+1 FROM emi_crt");
txtCOd.setText(cod);  
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

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
         try {
            if(!DB.con.getAutoCommit()){
                DB.con.commit();
                        new reporte().reporteConParametro("emicrt",Integer.parseInt(txtCOd.getText()), DB.con);
            }
            if(!DB.con.getAutoCommit())DB.con.setAutoCommit(true);
           // System.out.print("\nCOMMIT");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog( this, e.getMessage(),"Error al Guardar", JOptionPane.ERROR_MESSAGE ); 
        }  
    limpiar_cargar();
        limpiarTabla(jTable2);
    ocultar(); 
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Buscador b= new Buscador(null,true, "select id_docc,concat (id_docc,' - ',nombres,', ',apellidos)as cli from cliente where concat(nombres,' ',apellidos) LIKE", new String[]{"Documento","Cliente"}, new Object[]{txtCodCLiexp,null}, DB.con);
b.setTitle("Buscador de Clientes");
b.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
Buscador b= new Buscador(null,true, "select id_docc,concat (id_docc,' - ',nombres,', ',apellidos)as cli from cliente where concat(nombres,' ',apellidos) LIKE", new String[]{"Documento","Cliente"}, new Object[]{txtCodCLiimp,null}, DB.con);
b.setTitle("Buscador de Clientes");
b.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtPesoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesoKeyPressed
 if(evt.getKeyCode()==10){
    if(cboMerca.getSelectedIndex()!=0 && !txtCant.getText().trim().isEmpty()){
       // txtItem.setText(String.valueOf(Integer.parseInt(txtItem.getText())+1));
        save(2);
        txtCant.setText("");
        cboMerca.requestFocus();
        cboMerca.setSelectedIndex(0);
    }
 }
    }//GEN-LAST:event_txtPesoKeyPressed

    private void cboMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMonedaActionPerformed
if(cboMoneda.getSelectedIndex()>0){
    validar_blancos();
}
    }//GEN-LAST:event_cboMonedaActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
Buscador_antiTembo b= new Buscador_antiTembo(null,true, "select id_ped,fecha  from ped_flete where "
        + "extract(month from (fecha))=extract(month from current_date)", new String[]{"Codigo","Fecha"}, new Object[]{txtped,null}, DB.con);
b.setTitle("Buscador de pedidos");
b.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
          Statement  st=DB.con.createStatement();
          ResultSet  rs=st.executeQuery("SELECT d.item,m.descrip,d.cantidad from mercaderia m,d_merc d where m.id_merc=d.id_merc and id_ped='"+txtped.getText()+"' and d.item='"+txtItem.getText()+"'");
            if(rs.next()){
                txtCant.setText(rs.getInt(3)+"");
                cboMerca.setSelectedItem(rs.getString(2));
            }else{
                JOptionPane.showMessageDialog(this,"no hay mas detalles");
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cboAdu;
    private javax.swing.JComboBox cboDestino;
    private javax.swing.JComboBox cboMerca;
    private javax.swing.JComboBox cboMoneda;
    private javax.swing.JComboBox cboOrigen;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txtCOd;
    private javax.swing.JTextField txtCant;
    private javax.swing.JTextField txtCodCLiexp;
    private javax.swing.JTextField txtCodCLiimp;
    private javax.swing.JTextField txtItem;
    private javax.swing.JTextField txtPeso;
    private javax.swing.JTextField txtValor;
    private javax.swing.JTextField txtVol;
    private javax.swing.JTextField txtped;
    // End of variables declaration//GEN-END:variables
private void ocultar(){  
    btnAdd.setEnabled(true);
    btnAdd.requestFocus();
    btnCancelar.setEnabled(false);
    btnDelete.setEnabled(false);
    
    btnSave.setEnabled(false); 
}


private void limpiar_cargar(){
    limpiarTabla(jTable1);
    cargarTabla();
    
}

  private void limpiarTabla(JTable tabla){
        DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
        while(tabla.getRowCount() != 0)
            modelo.removeRow(0);
  }
 
  public void validarDuplicados(){   
      try  {
          String sql = "select * from emi_crt where id_crt='"+txtCOd.getText()+"'";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql);
boolean find = rs.next();
if(find==true){
    JOptionPane.showMessageDialog(null, "Ya existe un CRT con ese Codigo", "Alerta", JOptionPane.ERROR_MESSAGE);
}else{
    save(1);
}
          rs.close();
          st.close();
        }
     catch ( SQLException excepcionSql ){
         JOptionPane.showMessageDialog( null, excepcionSql.getMessage(),
            "Error al validar duplicados", JOptionPane.ERROR_MESSAGE );
  }}
  
     private void validar_blancos(){      
if( cboDestino.getSelectedIndex()==0 || cboOrigen.getSelectedIndex()==0) {
    JOptionPane.showMessageDialog(null, "complete todos los campos", "Alerta", JOptionPane.ERROR_MESSAGE);
}else{
    validarDuplicados();
    
}       
     }
}
