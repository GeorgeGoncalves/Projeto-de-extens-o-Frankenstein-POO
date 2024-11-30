package DAO;

import DaoImpl.Implementacao;
import bancoDados.BancoDados;

public class FabricaDao {

	public static FrankensteinDao criarDao() {
		return new Implementacao(BancoDados.conectar());
	}
	
}
