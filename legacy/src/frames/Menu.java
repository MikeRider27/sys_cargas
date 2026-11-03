package frames;


import frames.Asignacion.Asig_Fletero;
import frames.Asignacion.Asignacion_Equipo;
import frames.Asignacion.Reg_Llegada;
import frames.Asignacion.Reg_Salida;
import frames.Asignacion.Reg_Viaje;
import frames.Asignacion.Registrar_Eventos;
import frames.Pedidos.*;
import frames.Viaticos.Emi_Rendic;
import frames.Viaticos.Reg_Comp;
import referenciales.*;
import java.awt.Desktop;
import programas.conectarDB;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;


/**
 *
 * @author schafler92
 */
public class Menu extends javax.swing.JFrame {
    public conectarDB DB;    
    public static int SearchBan=0;
    Statement st;
    ResultSet rs;
    

    public Menu() {
        initComponents();
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
         //DB = new conectarDB();
        controlar_permisos();
        //DB.cargarDatos();
    }
    
    private void controlar_permisos(){
        if(Login.per==0){//falcon
            
        }
        if(Login.per==1){//admin
            jMenu10.setVisible(false);
        }
        if(Login.per==2){//
            
        }
        if(Login.per==3){
           jMenuBar1.setVisible(false);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem17 = new javax.swing.JMenuItem();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem37 = new javax.swing.JMenuItem();
        jMenuItem38 = new javax.swing.JMenuItem();
        jMenuItem39 = new javax.swing.JMenuItem();
        jMenuItem40 = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem41 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem42 = new javax.swing.JMenuItem();
        jMenuItem43 = new javax.swing.JMenuItem();
        jMenuItem44 = new javax.swing.JMenuItem();
        jMenuItem45 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem32 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem49 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem46 = new javax.swing.JMenuItem();
        jMenuItem47 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
        jMenuItem48 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();

        jMenuItem17.setText("jMenuItem17");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu Principal del Sistema");
        setResizable(false);

        jMenu1.setText("Mod. Pedidos");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenuItem18.setText("Regitrar Pedido");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem18);

        jMenuItem25.setText("Cotizar Flete");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem25);

        jMenuItem16.setText("Pedido de Flete");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem16);

        jMenuItem26.setText("Emitir CRT");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem26);

        jMenu11.setText("Reclamos");

        jMenuItem21.setText("Listar Reclamos");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem21);

        jMenuItem22.setText("Registrar Reclamos");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem22);

        jMenu1.add(jMenu11);

        jMenuBar1.add(jMenu1);

        jMenu5.setText("Mod. Logistica");
        jMenu5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenuItem4.setText("Asignar  Equipos");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem4);

        jMenuItem5.setText("Registrar de Salida");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem5);

        jMenuItem37.setText("Asignar Fletero");
        jMenuItem37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem37ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem37);

        jMenuItem38.setText("Registrar Viaje");
        jMenuItem38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem38ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem38);

        jMenuItem39.setText("Registrar Llegada ");
        jMenuItem39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem39ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem39);

        jMenuItem40.setText("R. Eventos de Cargas");
        jMenuItem40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem40ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem40);

        jMenuBar1.add(jMenu5);

        jMenu12.setText("Mod. Rendiciones");
        jMenu12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenuItem10.setText("R. Apert. y Cierre de Caja");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem10);

        jMenuItem23.setText("Asignar Viaticos");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem23);

        jMenuItem24.setText("Registrar Comprobantes");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem24);

        jMenuItem41.setText("Emitir Rendiciones");
        jMenuItem41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem41ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem41);

        jMenuBar1.add(jMenu12);

        jMenu2.setText("Mantenimiento");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenu7.setText("Mant. Referenciales de Empresa");

        jMenuItem2.setText("Personal");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem2);

        jMenuItem1.setText("Cargo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem1);

        jMenuItem20.setText("Sucursal");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem20);

        jMenu2.add(jMenu7);

        jMenu8.setText("Mant. Referenciales de Pedido");

        jMenuItem7.setText("Mercaderia");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem7);

        jMenuItem8.setText("Despachante");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem8);

        jMenuItem6.setText("Tipo de Carga");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem6);

        jMenuItem42.setText("Tipo de Mercaderia");
        jMenu8.add(jMenuItem42);

        jMenuItem43.setText("Rutas");
        jMenu8.add(jMenuItem43);

        jMenuItem44.setText("Peajes");
        jMenu8.add(jMenuItem44);

        jMenuItem45.setText("Aduanas");
        jMenu8.add(jMenuItem45);

        jMenu2.add(jMenu8);

        jMenu9.setText("Mant. Referenciales de Logistica");

        jMenuItem9.setText("Tracto");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem9);

        jMenuItem32.setText("Semirremolque");
        jMenu9.add(jMenuItem32);

        jMenuItem27.setText("Accesorios");
        jMenu9.add(jMenuItem27);

        jMenuItem28.setText("Itinerario");
        jMenu9.add(jMenuItem28);

        jMenuItem29.setText("Tipo de Evento");
        jMenu9.add(jMenuItem29);

        jMenuItem30.setText("Marca - Tracto");
        jMenu9.add(jMenuItem30);

        jMenuItem35.setText("Marca - Semi");
        jMenu9.add(jMenuItem35);

        jMenuItem34.setText("Fletero");
        jMenu9.add(jMenuItem34);

        jMenu2.add(jMenu9);

        jMenu10.setText("Mant. Referenciales Varios");

        jMenuItem14.setText("Tipo de Cliente");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem14);

        jMenuItem12.setText("Tipo de Documentos");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem12);

        jMenuItem13.setText("Modelo Tracto");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem13);

        jMenuItem49.setText("Tipo Semi");
        jMenu10.add(jMenuItem49);

        jMenuItem15.setText("Estado - Provincia");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem15);

        jMenuItem19.setText("Pais");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem19);

        jMenuItem46.setText("Ciudad");
        jMenu10.add(jMenuItem46);

        jMenuItem47.setText("Caja");
        jMenu10.add(jMenuItem47);

        jMenuItem31.setText("Nacionalidad");
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem31);

        jMenuItem48.setText("Rol");
        jMenu10.add(jMenuItem48);

        jMenu2.add(jMenu10);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Informes");
        jMenu3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuBar1.add(jMenu3);

        jMenu6.setText("Ayuda");
        jMenu6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenuItem3.setText("Manual");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem3);

        jMenuBar1.add(jMenu6);

        jMenu4.setText("Sistema");
        jMenu4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenuItem11.setText("SALIR");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1148, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
//int msj=JOptionPane.showConfirmDialog(null,"Desea Salir del Sistema e ir al Windows?", "Confirmar",JOptionPane.YES_NO_OPTION);
//        if (msj==JOptionPane.YES_OPTION) {
        this.dispose();  
//        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
       Pedido_Flete r = new  Pedido_Flete();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);

    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
Desktop d = Desktop.getDesktop();
        if(Desktop.isDesktopSupported()){
            try {
                d.open(new File("src/informes/manual.pdf"));
            } catch (IOException ex) {
                 JOptionPane.showMessageDialog(this, ex.getMessage(),"Error al Abrir Archivo", JOptionPane.ERROR_MESSAGE);
            }
        } else{
 JOptionPane.showMessageDialog(this, "El sistema No soporta el Manual de Ayuda", "Error WTF", JOptionPane.ERROR_MESSAGE);
        }
//        try {
//                Desktop.getDesktop().open(new File("src/reportes/Manual.pdf"));
//            } catch (IOException ex) {
//                 JOptionPane.showMessageDialog(this, ex.getMessage(),"Error al Abrir Archivo", JOptionPane.ERROR_MESSAGE);
//            }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
   referenciales.Ref_personal r= new  referenciales.Ref_personal();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
   Ref_nac r= new  Ref_nac();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        referenciales.Ref_estado r= new  referenciales.Ref_estado();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    referenciales.Ref_cargo r= new  referenciales.Ref_cargo();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        referenciales.Ref_tipo_cli r= new  referenciales.Ref_tipo_cli();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        referenciales.Ref_tipo_doc r= new  referenciales.Ref_tipo_doc();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:
         referenciales.Ref_tipo_merc r= new  referenciales.Ref_tipo_merc();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        // TODO add your handling code here:
           Ref_sucursal r = new  Ref_sucursal();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
           Ref_Mercaderia r = new  Ref_Mercaderia();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        Reg_Pedido r = new  Reg_Pedido();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        Ref_Cliente r = new Ref_Cliente();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        Reg_Reclamos r = new  Reg_Reclamos (1,0);
        Reg_Reclamos.chkA.setSelected(true);
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
Listar_Reclamos a=new Listar_Reclamos(this, false);
a.setLocationRelativeTo(null);
a.setVisible(true);
//this.setAlwaysOnTop(false);
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        Ref_despachante r = new  Ref_despachante();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        Ref_Usuario r = new  Ref_Usuario();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem31ActionPerformed
        Ref_nac r = new  Ref_nac();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem31ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        Cotizar_F r = new  Cotizar_F();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        Reg_crt r = new  Reg_crt();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem37ActionPerformed
       Asig_Fletero r = new  Asig_Fletero();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem37ActionPerformed

    private void jMenuItem38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem38ActionPerformed
       Reg_Viaje r = new  Reg_Viaje();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem38ActionPerformed

    private void jMenuItem39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem39ActionPerformed
       Reg_Llegada r = new  Reg_Llegada();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem39ActionPerformed

    private void jMenuItem40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem40ActionPerformed
        Registrar_Eventos r = new  Registrar_Eventos();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem40ActionPerformed

    private void jMenuItem41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem41ActionPerformed
        Emi_Rendic r = new  Emi_Rendic();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem41ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        Asignacion_Equipo r = new  Asignacion_Equipo();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        Reg_Salida r = new  Reg_Salida();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
       A_Viatico r = new  A_Viatico();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
       Reg_Comp r = new  Reg_Comp();
        jDesktopPane1.add(r);
        r.setVisible(true);        
Dimension desktopSize = jDesktopPane1.getSize();
Dimension jInternalFrameSize = r.getSize();
r.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
(desktopSize.height- jInternalFrameSize.height)/2);
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    /**
     * @param args the command line arguments
     */
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
                new Menu().setVisible(true); 
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem32;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem37;
    private javax.swing.JMenuItem jMenuItem38;
    private javax.swing.JMenuItem jMenuItem39;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem40;
    private javax.swing.JMenuItem jMenuItem41;
    private javax.swing.JMenuItem jMenuItem42;
    private javax.swing.JMenuItem jMenuItem43;
    private javax.swing.JMenuItem jMenuItem44;
    private javax.swing.JMenuItem jMenuItem45;
    private javax.swing.JMenuItem jMenuItem46;
    private javax.swing.JMenuItem jMenuItem47;
    private javax.swing.JMenuItem jMenuItem48;
    private javax.swing.JMenuItem jMenuItem49;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    // End of variables declaration//GEN-END:variables
    
}
