package aplicacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import DAO.FabricaDao;
import DAO.FrankensteinDao;
import DaoImpl.Implementacao;
import bancoDados.BancoDados;
import entidades.Cliente;
import entidades.Servico;

public class Principal {

	public static void main(String[] args) {

		Scanner tc = new Scanner(System.in);

		Connection conexao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		FrankensteinDao frankDao = FabricaDao.criarDao();

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

			frankDao.inserirClientes(cliente);

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

				frankDao.inserirServicoProduto(servico);
			}

			System.out.println();
			System.out.println(cliente.texto());
			System.out.println();
			System.out.println("VALOR DO PAGAMENTO R$: " 
						+ String.format("%.2f", cliente.pagamento()));
			
			double absorve = cliente.pagamento();

			frankDao.inserircaixa(absorve);

			System.out.println();
			System.out.print("Deseja continuar usando o sistema? (S/N): ");
			loop = tc.next().charAt(0);
			tc.nextLine();
		}
		frankDao.resultadoVendas();
		
		System.out.println();
		System.out.println("ARRECADAÇÃO DO DIA R$: " 
						+ String.format("%.2f", frankDao.resultadoCaixa()));

		System.out.println();
		System.out.println("*** SISTEMA FINALIZADO! ***");

		tc.close();
	}
}
