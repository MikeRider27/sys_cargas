

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
public class Ref_Cliente extends javax.swing.JInternalFrame {

      private conectarDB DB;
    private Statement st;
    private ResultSet rs;
    int bandera,cbo;
    String aop;
   
    public Ref_Cliente() {
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
         String sql = "select c.id_docc,c.nombres,c.apellidos,c.fecha_nac,c.direcc,c.telf,t.descrip,n.descrip\n" +
"from cliente c, tipo_cli t,nacionalidad n\n" +
"where t.id_cli=c.id_cli and n.id_nac=c.id_nac and c.id_est=1";
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
          String sql = "select descrip from tipo_cli";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql);
             while (rs.next()){  
                 cboTiCl.addItem(rs.getString(1));
             }
             
         st = DB.con.createStatement();
         rs = st.executeQuery("select descrip from tipo_doc order by descrip asc");
             while (rs.next()){  
                 cboTiD.addItem(rs.getString(1));
             }
             
             st = DB.con.createStatement();
         rs = st.executeQuery("select descrip from nacionalidad order by descrip asc");
             while (rs.next()){  
                 cboNac.addItem(rs.getString(1));
             }
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
        
             String ctDOC=DB.buscarCod("SELECT id_tc FROM tipo_doc WHERE descrip='"+cboTiD.getSelectedItem()+"'");
             String ctCLI=DB.buscarCod("SELECT id_cli FROM tipo_cli WHERE descrip='"+cboTiCl.getSelectedItem()+"'");
             String ctnac=DB.buscarCod("SELECT id_nac FROM nacionalidad WHERE descrip='"+cboNac.getSelectedItem()+"'");
   String sql="select abm_cli('"+bandera+"','"+txtCNroC.getText()+"','"+txtCNom.getText()+"','"+txtCAp.getText()+"','"+xfp+"','"+txtCDir.getText()+"',"
           + "'"+txtCTel.getText()+"','"+ctCLI+"','"+ctnac+"')";             
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
        cboTiCl = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cboNac = new javax.swing.JComboBox();

        setClosable(true);
        setIconifiable(true);
        setTitle("Cliente");
        setToolTipText("");

        jLabel1.setText("Nro. de Cedula: ");

        jLabel2.setText("Tipo de Doc.:");

        jLabel3.setText("Apellidos:");

        jLabel4.setText("Fecha Nac.:");

        jLabel5.setText("Dirección:");

        jLabel6.setText("Telefono:");

        txtCAp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCApKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCApKeyTyped(evt);
            }
        });

        txtCDir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCDirKeyReleased(evt);
            }
        });

        txtCTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCTelKeyPressed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro de Cedula", "Nombres", "Apellidos", "Fecha Nac", "Direccion", "Telefono", "Tipo de Cli.", "nac"
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

        btnDelete.setText("Anular");
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

        jLabel8.setText("Tipo de Cli.:");

        jLabel9.setText("Nombres:");

        txtCNom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCNomKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCNomKeyTyped(evt);
            }
        });

        cboTiD.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir" }));

        cboTiCl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir" }));

        jLabel7.setText("Nacionalidad");

        cboNac.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "elegir" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCTel, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(btnAdd)
                        .addGap(18, 18, 18)
                        .addComponent(btnEdit)
                        .addGap(35, 35, 35)
                        .addComponent(btnDelete)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave)
                        .addGap(26, 26, 26)
                        .addComponent(btnCancelar)
                        .addGap(18, 18, 18)
                        .addComponent(btnExit)
                        .addGap(0, 123, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCNroC, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(cboNac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtCAp))
                                        .addGap(22, 22, 22))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtCNom)
                                            .addComponent(cboTiCl, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cboTiD, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                            .addComponent(txtCDir))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
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
                            .addComponent(cboTiCl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtCDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnCancelar)
                    .addComponent(btnExit)
                    .addComponent(btnSave))
                .addContainerGap(15, Short.MAX_VALUE))
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
       // if(bandera==2){
            //save();
       // }else{
            validar_blancos();
        //}
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

    private void txtCNomKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCNomKeyReleased
//txtCNom.setText(txtCNom.getText().trim().toUpperCase());
    }//GEN-LAST:event_txtCNomKeyReleased

    private void txtCApKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCApKeyReleased
//txtCAp.setText(txtCAp.getText().trim().toUpperCase());
    }//GEN-LAST:event_txtCApKeyReleased

    private void txtCDirKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCDirKeyReleased
//txtCDir.setText(txtCDir.getText().trim().toUpperCase());
    }//GEN-LAST:event_txtCDirKeyReleased

    private void txtCNomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCNomKeyTyped
if(evt.getKeyCode() != KeyEvent.VK_SPACE){
char c;
 c=evt.getKeyChar();
 if(!(c<'0'||c>'9'))
 evt.consume();
} 
    }//GEN-LAST:event_txtCNomKeyTyped

    private void txtCApKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCApKeyTyped
if(evt.getKeyCode() != KeyEvent.VK_SPACE){
char c;
 c=evt.getKeyChar();
 if(!(c<'0'||c>'9'))
 evt.consume();
} 
    }//GEN-LAST:event_txtCApKeyTyped

    private void txtCTelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTelKeyPressed
if(evt.getKeyCode()==10){
    btnSave.doClick();
}
    }//GEN-LAST:event_txtCTelKeyPressed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
aop=(jTable1.getValueAt(jTable1.getSelectedRow(),0).toString());
txtCNroC.setText(aop);
txtCNom.setText(jTable1.getValueAt(jTable1.getSelectedRow(),1).toString());
txtCAp.setText(jTable1.getValueAt(jTable1.getSelectedRow(),2).toString());
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	String dateInString = jTable1.getValueAt(jTable1.getSelectedRow(),3).toString();
 Date date = null;
	try {
 
		 date = formatter.parse(dateInString);
	//	System.out.println(date);
		//System.out.println(formatter.format(date));
 
	} catch (ParseException e) {
		e.printStackTrace();
	}
jDateChooser1.setDate(date);
txtCDir.setText(jTable1.getValueAt(jTable1.getSelectedRow(),4).toString());
txtCTel.setText(jTable1.getValueAt(jTable1.getSelectedRow(),5).toString());
cboNac.setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(),7).toString());
cboTiCl.setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(),6).toString());
//cboTiD.setSelectedItem(jTable1.getValueAt(jTable1.getSelectedRow(),1).toString());
//--------
btnEdit.setEnabled(true);
btnDelete.setEnabled(true);
btnCancelar.setEnabled(true);
btnAdd.setEnabled(false);
    }//GEN-LAST:event_jTable1MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cboNac;
    private javax.swing.JComboBox cboTiCl;
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
    cboNac.setSelectedIndex(0);
    cboTiCl.setSelectedIndex(0);
    cboTiD.setSelectedIndex(0);
    jDateChooser1.setDate(null);
    txtCNom.setText("");
}

  private void limpiarTabla(){
        DefaultTableModel modelo = (DefaultTableModel)this.jTable1.getModel();
        while(this.jTable1.getRowCount() != 0)
            modelo.removeRow(0);
}
 
  public void validarDuplicados(){ 
      try  {
          String sql = "SELECT * from cliente where id_docc='"+txtCNroC.getText().trim()+"' and id_docc!='"+txtCNroC.getText()+"'";
         st = DB.con.createStatement();
         rs = st.executeQuery(sql);
boolean find = rs.next();
if(find==true){
    JOptionPane.showMessageDialog(null, "Ya existe un cliente con ese "+cboTiD.getSelectedItem(), "Alerta", JOptionPane.ERROR_MESSAGE);
}else{
save();
}
          rs.close();
          st.close();
        }
     catch ( SQLException excepcionSql ){
         JOptionPane.showMessageDialog( null, excepcionSql.getMessage(),
            "Error al validar duplicados", JOptionPane.ERROR_MESSAGE );
  }  
  }
  
     private void validar_blancos(){      
         if(txtCNroC.getText().trim().isEmpty() || txtCAp.getText().trim().isEmpty() || txtCNom.getText().trim().isEmpty() || cboTiCl.getSelectedIndex()==0){
             JOptionPane.showMessageDialog(null, "Debe completar todos los campos para proceder", "Atención", JOptionPane.ERROR_MESSAGE);
 
             }else{
    validarDuplicados();
     } 
}
}
