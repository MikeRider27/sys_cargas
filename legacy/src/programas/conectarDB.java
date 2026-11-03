package programas;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
 
public class conectarDB {
 
   public Connection con = null;
   public ResultSet rs;
   public Statement st;
    
   public conectarDB() {
       String contraseña_bd=null;//:V
       if(System.getProperty("user.name").equals("mama") || System.getProperty("user.name").equals("Kuroryuu") || System.getProperty("user.name").equals("sch")){
           contraseña_bd="wasd";
       }else{
           contraseña_bd="falcon";
       }
         try{
             Class.forName("org.postgresql.Driver");
             con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cargas","postgres",contraseña_bd);
            } 
           catch (ClassNotFoundException | SQLException e){
               JOptionPane.showMessageDialog(null,"Fallo en la Conexion con la Base de Datos\nError: "+e.getMessage(),"Error Fatal",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            }
    }
        
 public String gencod(String sql){
             int cod = 0;
        try {
            st= con.createStatement();
            rs = st.executeQuery(sql);
            rs.next() ;
            cod = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(conectarDB.class.getName()).log(Level.SEVERE, null, ex);
        }
            return String.valueOf(cod);
   }
     
     public String buscarCod(String sql){
                String aux="";
        try {
            st = con.createStatement();
            System.out.print("buscarCod= "+sql+"\n\n");
            rs = st.executeQuery(sql);
            if (rs.next()) {
                aux = rs.getString(1);
            }
        } catch (SQLException ex) {
               Logger.getLogger(conectarDB.class.getName()).log(Level.SEVERE, null, ex);
        }
                 return aux;
     }
      
    public int obtenerRol(String usuario){
        int aux = 0;
        try {
            String sql = "SELECT rol FROM usuario where username like '%"+usuario+"%'";
            st = con.createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            aux = rs.getInt("rol");
        } catch (SQLException ex) {
            Logger.getLogger(conectarDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print(aux);
        return aux;    
    }   
}