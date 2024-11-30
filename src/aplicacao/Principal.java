package aplicacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import bancoDados.BancoDados;
import entidades.Cliente;
import entidades.Servico;

public class Principal {

	public static void main(String[] args) {
		
		Scanner tc = new Scanner(System.in);

		Connection conexao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
				
		char loop = 's';
		while (loop == 's' || loop == 'S') {
						
			System.out.println("Cadastrando cliente:");
			System.out.print("Nome: ");
			String nome = tc.nextLine();
			System.out.print("Telefone: ");
			String telefone = tc.nextLine();
			System.out.print("E-mail: ");
			String email = tc.nextLine();
			System.err.println();

			Cliente cliente = new Cliente(nome, telefone, email, new Date());

			conexao = BancoDados.conectar();
			try {
				ps = conexao.prepareStatement("insert into clientes"
						+ "(nome, telefone, email)"
						+ "values"
						+ "(?, ?, ?)");
				
				ps.setString(1, nome);
				ps.setString(2, telefone);
				ps.setString(3, email);
				ps.executeUpdate();				
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			System.out.print("Digite o número de serviço(s): ");
			int n = tc.nextInt();
			for (int i = 1; i <= n; i++) {
				tc.nextLine();
				System.out.println();
				System.out.println("Digite os dados do " + i + "º serviço:");
				System.out.print("Nome do serviço: ");
				String produto = tc.nextLine();
				System.out.print("Preço do serviço: ");
				double preco = tc.nextDouble();
				System.out.print("Quantidade de serviço(s): ");
				int quantidade = tc.nextInt();

				Servico servico = new Servico(produto, preco, quantidade);
				cliente.addLista(servico);
				
				try {
					ps = conexao.prepareStatement("insert into produto_servico"
							+ "(nome, preco, quantidade)"
							+ "values"
							+ "(?, ?, ?)");
					
					ps.setString(1, produto);
					ps.setDouble(2, preco);
					ps.setInt(3, quantidade);
					ps.executeUpdate();
									
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}

			System.out.println();
			System.out.println(cliente.texto());
			System.out.println();
			System.out.println("VALOR DO PAGAMENTO R$: " 
					+ String.format("%.2f", cliente.pagamento()));
			double absorve = cliente.pagamento();
			
				try {
					ps = conexao.prepareStatement("insert into caixa"
							+ "(valor_total)"
							+ "value"
							+ "(?)");
					
					ps.setDouble(1, absorve);
					ps.executeUpdate();
					
				} 
				catch (SQLException e) {
					
					e.printStackTrace();
				}
									
			System.out.println();
			System.out.print("Deseja continuar usando o sistema? (S/N): ");
			loop = tc.next().charAt(0);
			tc.nextLine();
		}
		
		System.out.println();
		
		try {
			
			Map<String, Integer> map = new HashMap<>();
			
			rs = ps.executeQuery("select * from produto_servico");
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
			
			rs = ps.executeQuery("select * from caixa");
			double soma = 0;
			while (rs.next()) {
			soma += rs.getDouble("valor_total");
			}
			
			System.out.println();
			System.out.println("ARRECADAÇÃO DO DIA R$: " 
							+ String.format("%.2f", soma));
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BancoDados.fechaPS(ps);
			BancoDados.fecharRS(rs);
			BancoDados.fecharConectar();
		}
		System.out.println();
		System.out.println("*** SISTEMA FINALIZADO! ***");
		
		tc.close();		
	}
}
