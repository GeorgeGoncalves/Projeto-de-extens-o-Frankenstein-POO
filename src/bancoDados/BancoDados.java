package bancoDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BancoDados {

private static Connection conexao = null;
	
	public static Connection conectar() {
		if (conexao == null) {
			try {
				conexao = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/barbearia", 
						"root", "root");
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conexao;
	}
		
	public static void fechaPS(PreparedStatement ps) {
		if (conexao != null) {
			try {
				ps.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void fecharRS(ResultSet rs) {
		if(conexao != null) {
			try {
				rs.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void fecharConectar() {
		try {
			conexao.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
