
package frames;

import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import programas.HashMd5;
import programas.conectarDB;
import programas.reporte;

public class Login extends javax.swing.JFrame {
    static public String  usuario ="";
    static public int cod_usuario =0;
    conectarDB DB;
    int error=0;
    public static int per;
    
    public Login(){
     initComponents();
     DB = new conectarDB();
   }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtClave = new javax.swing.JPasswordField();
        lblClave = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblUsuari = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inicio de Sesion - SysCargas3 - Prototipo");
        setResizable(false);

        txtClave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClaveKeyPressed(evt);
            }
        });

        lblClave.setText("Contraseña:");

        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyReleased(evt);
            }
        });

        lblUsuari.setText("Usuario: ");
        lblUsuari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblUsuariMousePressed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/scania-stax-2_.jpg"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUsuari)
                    .addComponent(lblClave))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUsuari)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblClave))
                        .addGap(34, 34, 34))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtClaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClaveKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){ingrsar();}
    }//GEN-LAST:event_txtClaveKeyPressed

    private void txtUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyReleased
txtUsuario.setText(txtUsuario.getText().toUpperCase());
    }//GEN-LAST:event_txtUsuarioKeyReleased

    private void lblUsuariMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariMousePressed
Menu p = new Menu();
                    p.setLocationRelativeTo(null);
                    p.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_lblUsuariMousePressed

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        this.dispose();
        new Menu().setVisible(true);
    }//GEN-LAST:event_jLabel2MousePressed

    private void ingrsar(){//por ahora solo consulta el usuario y contraseña, mas adelante podes agregar mas cosas como el permiso o cargo para hacer mas purete
          try {
             String sql = "SELECT u.descrip,u.contrasena,u.id_rol,p.id_per FROM usuario u,personal p WHERE u.descrip='"+txtUsuario.getText().trim()+"'"
                     + "AND u.id_usu=p.id_usu";
            DB.st = DB.con.createStatement();
            DB.rs = DB.st.executeQuery(sql);
            int bandera = 0;
            while(DB.rs.next()){
                System.out.print(DB.rs.getString("contrasena")+"  dfsdfs "+new HashMd5().hash(txtClave.getText().trim().toLowerCase()));
                if(txtUsuario.getText().trim().toUpperCase().equals(DB.rs.getString("descrip"))
                        && new HashMd5().hash(txtClave.getText().trim().toLowerCase()).equals(DB.rs.getString("contrasena"))){
                    usuario = DB.rs.getString("descrip");
                    cod_usuario=DB.rs.getInt(4);
                    per = DB.rs.getInt(3);
                 //   if(DB.rs.getInt("user_status")==0){
                  //      JOptionPane.showMessageDialog(null, "El usuario que intenta Ingresar esta Inactivo, contacte con el administrador","Alerta",JOptionPane.ERROR_MESSAGE); 
                  //      System.exit(0);
                  //  }
                    //---------todo bien, iniciando sistema-------------------------
                    Menu p = new Menu();
                    p.setLocationRelativeTo(null);
                    p.setVisible(true);
                    this.dispose();
                    bandera = 1;                       
                }   
            } 
            if(bandera==0){   
                 JOptionPane.showMessageDialog(null, "El usuario y/o la contraseña son incorrectos","Error",JOptionPane.ERROR_MESSAGE); 
                 error++;
                 txtClave.setText("");
                 txtUsuario.setText("");
                 txtUsuario.requestFocus();
            } 
                        if(error>2){
                JOptionPane.showMessageDialog(null, "Cerrando el Sistema","Alerta",JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
        public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Login acceso = new Login();
                acceso.setLocationRelativeTo(null);
                acceso.setVisible(true);
                acceso.toFront();
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblClave;
    private javax.swing.JLabel lblUsuari;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
