
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
import programas.HashMd5;
import programas.conectarDB;


/**
 *
 * @author schafler92
 */
public class Ref_personal extends javax.swing.JInternalFrame {
    
    private conectarDB DB;
    private Statement st;
    private ResultSet rs;
    int bandera;
    String aop,cod;

    public Ref_personal() {
        initComponents();
          DB = new conectarDB();
            nullAndLoad();
            ocultar(); 
            cargarCbos();
    }

        private void cargarTabla(){
        DefaultTableModel modelo = (DefaultTableModel)this.jTable1.getModel();
        String datos[] = new String[11];
        Integer i = 1;
        try  {
         st= DB.con.createStatement();
         String sql = "  select p.id_per,p.nombres,p.apellidos,p.telf,p.direcc,c.descrip,p.usuario,p.contrasena,p.fecha_nac,s.descrip,p.id_est from\n" +
"  personal p, sucursal s, cargo c\n" +
"  where c.id_car=p.id_car and s.id_suc=p.id_suc and p.id_est=1";
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
              datos[10]= rs.getString(11);
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
          String sql = "SELECT descrip FROM nacionalidad";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql);
             while (rs.next()){  
//               cboNac.addItem(rs.getString(1));
             }
          rs.close();
          st.close();
        }
     catch ( SQLException excepcionSql ) {
         JOptionPane.showMessageDialog( null, excepcionSql.getMessage(),
            "Error al cargar lista de nac", JOptionPane.ERROR_MESSAGE );
     }
try  {
          String sql = "SELECT descrip FROM cargo";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql);
             while (rs.next()){  
                 cboCargo.addItem(rs.getString(1));
             }
          rs.close();
          st.close();
        }
     catch ( SQLException excepcionSql ) {
         JOptionPane.showMessageDialog( null, excepcionSql.getMessage(),
            "Error al cargar lista de cargos", JOptionPane.ERROR_MESSAGE );
     }

try  {
          String sql = "SELECT descrip from sucursal";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql);
             while (rs.next()){  
                 cboSuc.addItem(rs.getString(1));
             }
          rs.close();
          st.close();
        }
     catch ( SQLException excepcionSql ) {
         JOptionPane.showMessageDialog( null, excepcionSql.getMessage(),
            "Error al cargar lista de sucursal", JOptionPane.ERROR_MESSAGE );
     }

  }

   public void save(){
         try{
            // String cNac=DB.buscarCod("SELECT id_nac FROM nacionalidad WHERE descrip='"+cboNac.getSelectedItem()+"'");
             String cSuc=DB.buscarCod("SELECT id_suc FROM sucursal WHERE descrip='"+cboSuc.getSelectedItem()+"'");
             String cCar=DB.buscarCod("SELECT id_car FROM cargo WHERE descrip='"+cboCargo.getSelectedItem()+"'");

             cod=txtCed.getText().trim();
                if(bandera==2 || bandera==3)cod=aop;
  String sql="SELECT abm_personal('"+bandera+"',"
          + "'"+cod+"',"
          + "'"+txtNme.getText().trim()+"',"
          + "'"+txtApe.getText().trim()+"',"
          + "'"+jDateChooser1.getDate()+"','"+txtUser.getText()+"',"
          + "'"+new HashMd5().hash(jPasswordField1.getText())+"','"+txtDir.getText().trim()+"','"+txtCel.getText().trim()+"','"+cCar+"','"+cSuc+"')";
  st = DB.con.createStatement();
  System.out.print(""+sql);
                  st.executeQuery(sql);     
            } catch (SQLException e)
                     {JOptionPane.showMessageDialog( null, e.getMessage(),
            "Error en la interaccón con la Base de Datos", JOptionPane.ERROR_MESSAGE );                                }
         this.btnAdd.requestFocus();
      }        
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtCed = new javax.swing.JTextField();
        txtNme = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtApe = new javax.swing.JTextField();
        txtCel = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtDir = new javax.swing.JTextField();
        cboCargo = new javax.swing.JComboBox();
        cboSuc = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setTitle("Personal - Prototipo");

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

        jLabel2.setText("Nro de Cedula");

        txtCed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedKeyTyped(evt);
            }
        });

        txtNme.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNmeKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNmeKeyReleased(evt);
            }
        });

        jLabel1.setText("Nombres");

        jLabel4.setText("Apellidos");

        txtApe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApeKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtApeKeyReleased(evt);
            }
        });

        txtCel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelKeyTyped(evt);
            }
        });

        jLabel7.setText("Celular");

        jLabel8.setText("Direccion");

        txtDir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDirKeyReleased(evt);
            }
        });

        cboCargo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir" }));

        cboSuc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir" }));

        jLabel6.setText("Sucursal");

        jLabel13.setText("Cargo");

        jLabel3.setText("Usuario");

        jLabel10.setText("Contraseña");

        jLabel5.setText("Fecha de nac.");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro de Cedula", "Nombres", "Apellidos", "Telefono", "Dirección", "Cargo", "Usuario", "Contraseña", "Fecha de Nac.", "suc"
            }
        ));
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel13)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtUser, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboSuc, javax.swing.GroupLayout.Alignment.LEADING, 0, 140, Short.MAX_VALUE)
                            .addComponent(cboCargo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDir, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCed, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNme, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtApe, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCel, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
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
                        .addContainerGap(314, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtCed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtApe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(cboCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cboSuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnSave)
                    .addComponent(btnCancelar)
                    .addComponent(btnExit))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
al_agregar();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
al_editar();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
al_borrar();
    }//GEN-LAST:event_btnDeleteActionPerformed

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

    private void btnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEditKeyPressed
if(evt.getKeyCode() == KeyEvent.VK_ENTER){
     al_editar();
}
    }//GEN-LAST:event_btnEditKeyPressed

    private void btnDeleteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDeleteKeyPressed
if(evt.getKeyCode() == KeyEvent.VK_ENTER){
al_borrar();
}
    }//GEN-LAST:event_btnDeleteKeyPressed

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
btnAdd.requestFocus();
}
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void txtNmeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNmeKeyReleased
txtNme.setText(txtNme.getText().toUpperCase());
    }//GEN-LAST:event_txtNmeKeyReleased

    private void txtNmeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNmeKeyTyped
if(evt.getKeyCode() != KeyEvent.VK_SPACE){
char c;
 c=evt.getKeyChar();
 if(!(c<'0'||c>'9'))
 evt.consume();
} 
    }//GEN-LAST:event_txtNmeKeyTyped

    private void txtApeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApeKeyTyped
if(evt.getKeyCode() != KeyEvent.VK_SPACE){
char c;
 c=evt.getKeyChar();
 if(!(c<'0'||c>'9'))
 evt.consume();
} 
    }//GEN-LAST:event_txtApeKeyTyped

    private void txtCedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedKeyTyped
       char caracter = evt.getKeyChar();
        if(((caracter < '0') || (caracter > '9')) && (caracter != KeyEvent.VK_BACK_SPACE))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtCedKeyTyped

    private void txtCelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelKeyTyped
       char caracter = evt.getKeyChar();
        if(((caracter < '0') || (caracter > '9')) && (caracter != KeyEvent.VK_BACK_SPACE))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtCelKeyTyped

    private void txtApeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApeKeyReleased
txtApe.setText(txtApe.getText().toUpperCase());
    }//GEN-LAST:event_txtApeKeyReleased

    private void txtDirKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDirKeyReleased
txtDir.setText(txtDir.getText().toUpperCase());
    }//GEN-LAST:event_txtDirKeyReleased

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
btnEdit.setEnabled(true);

btnDelete.setEnabled(true);
btnCancelar.setEnabled(true);
aop=jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
txtCed.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
txtNme.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
txtApe.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString());
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	String dateInString = jTable1.getValueAt(jTable1.getSelectedRow(), 8).toString();
 Date date = null;
	try {
 
		 date = formatter.parse(dateInString);
	//	System.out.println(date);
		//System.out.println(formatter.format(date));
 
	} catch (ParseException e) {
		e.printStackTrace();
	}
        jDateChooser1.setDate(date);
//datetxtCel.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 3).toString());
txtUser.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 6).toString());
jPasswordField1.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 7).toString());
txtDir.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 4).toString());
txtCel.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 3).toString());
cboCargo.setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(), 5).toString());
cboSuc.setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(), 9).toString());
    }//GEN-LAST:event_jTable1MousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cboCargo;
    private javax.swing.JComboBox cboSuc;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtApe;
    private javax.swing.JTextField txtCed;
    private javax.swing.JTextField txtCel;
    private javax.swing.JTextField txtDir;
    private javax.swing.JTextField txtNme;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
private void al_agregar(){
        bandera=1;
    nullAndLoad();
txtCed.requestFocus();
btnAdd.setEnabled(false);
btnCancelar.setEnabled(true);
btnSave.setEnabled(true);
}
private void al_editar(){
    btnCancelar.setEnabled(true);
    btnSave.setEnabled(true);
    btnAdd.setEnabled(false);
    btnEdit.setEnabled(false);
    txtCed.requestFocus();
    btnCancelar.setEnabled(true);
    bandera=2;
    btnDelete.setEnabled(false);
}

private void al_borrar(){
    bandera=3;
        btnCancelar.setEnabled(true);
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
    txtNme.setText("");
    txtCed.setText("");
    txtCel.setText("");
    txtApe.setText("");
    txtDir.setText("");
    txtUser.setText("");
    jPasswordField1.setText("");
    jDateChooser1.setDate(null);
    cboCargo.setSelectedIndex(0);
    cboSuc.setSelectedIndex(0);
//    cboNac.setSelectedIndex(0);
}

  private void limpiarTabla(){
        DefaultTableModel modelo = (DefaultTableModel)this.jTable1.getModel();
        while(this.jTable1.getRowCount() != 0)
            modelo.removeRow(0);
  }
 
  public void validarDuplicados(){   
      try  {
          String sql = "SELECT * from personal where id_per='"+txtCed.getText().trim()+"' and '"+txtCed.getText().trim()+"' !='"+aop+"'";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql);
boolean find = rs.next();
if(find==true){
    JOptionPane.showMessageDialog(null, "Ya existe un PERSONAL con ese Nro. de Cedula", "Alerta", JOptionPane.ERROR_MESSAGE);
}else{
    save();
}
          rs.close();
          st.close();
        }
     catch ( SQLException excepcionSql ){
         JOptionPane.showMessageDialog( null, excepcionSql.getMessage(),
            "Error al validar duplicados", JOptionPane.ERROR_MESSAGE );
  }}
  
     private void validar_blancos(){      
         if(txtCed.getText() .equals("") || txtNme.getText() .equals("") || txtApe.getText() .equals("") || txtCel.getText() .equals("") || txtDir.getText() .equals("")
     || cboCargo.getSelectedIndex() ==0){
             JOptionPane.showMessageDialog(null, "Debe completar todos los campos para proceder", "Atención", JOptionPane.ERROR_MESSAGE);
         }else{
           validarDuplicados();
         }         
     } 

}
