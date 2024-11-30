package DAO;

import java.util.Map;

import entidades.Cliente;
import entidades.Servico;

public interface FrankensteinDao {

	void inserirClientes(Cliente obj);
	void inserirServicoProduto(Servico obj);
	void inserircaixa(Double obj);
	Map<String, Integer> resultadoVendas();	
	Double resultadoCaixa();
}
