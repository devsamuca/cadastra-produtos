package conexao; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static Connection conn;

    public static Connection getConexao() {
        try {
            if (conn == null) {
                // Cria uma nova conexão
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/produtos", "root", "Root");
                return conn; 
            } else {
                return conn;
            }
        } catch(SQLException e){
            e.printStackTrace();
            return null;  
        }
    }
}