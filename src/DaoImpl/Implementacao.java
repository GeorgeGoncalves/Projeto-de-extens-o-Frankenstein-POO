package DaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import DAO.FrankensteinDao;
import entidades.Cliente;
import entidades.Servico;

public class Implementacao implements FrankensteinDao {

	private Connection conexao;
	
	public Implementacao(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void inserirClientes(Cliente obj) {
		
PreparedStatement ps = null;
		
		try {
			ps = conexao.prepareStatement("insert into clientes"
					+ "(nome, telefone, email)"
					+ "values"
					+ "(?, ?, ?)");
			
			ps.setString(1, obj.getNome());
			ps.setString(2, obj.getTelefone());
			ps.setString(3, obj.getEmail());
			ps.executeUpdate();				
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}		
	}	

	@Override
	public void inserirServicoProduto(Servico obj) {
		
		PreparedStatement ps = null;
		
		try {
			ps = conexao.prepareStatement("insert into produto_servico"
					+ "(nome, preco, quantidade)"
					+ "values"
					+ "(?, ?, ?)");
			
			ps.setString(1, obj.getNome());
			ps.setDouble(2, obj.getPreco());
			ps.setInt(3, obj.getQuantidade());
			ps.executeUpdate();
							
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void inserircaixa(Double obj) {
		
		PreparedStatement ps = null;
		
		try {
			ps = conexao.prepareStatement("insert into caixa"
					+ "(valor_total)"
					+ "value"
					+ "(?)");
			
			ps.setDouble(1, obj);
			ps.executeUpdate();					
		} 
		catch (SQLException e) {					
			e.printStackTrace();
		}
		
	}

	@Override
	public Map<String, Integer> resultadoVendas() {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Map<String, Integer> map = new HashMap<>();
		
		try {
		
		
		ps = conexao.prepareStatement("select * from produto_servico");
		rs = ps.executeQuery();
		while (rs.next()){
			if (map.containsKey(rs.getString("nome"))) {
				int soma = map.get(rs.getString("nome"));
				map.put(rs.getString("nome"), rs.getInt("quantidade") + soma);
			} else {
				map.put(rs.getString("nome"), rs.getInt("quantidade"));
			}
		}
		
		for (String a: map.keySet())
			System.out.println(a + "- " + map.get(a));
	} catch (SQLException e) {
		e.printStackTrace();
	}
		return map;
	}
	
	@Override
	public Double resultadoCaixa() {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		double soma = 0;
		
		try {			
			
			ps = conexao.prepareStatement("select * from caixa");
			rs = ps.executeQuery();
		
			while (rs.next()) 
			soma += rs.getDouble("valor_total");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return soma; 
	}
}
