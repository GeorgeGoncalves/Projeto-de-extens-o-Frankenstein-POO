package entidades;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cliente {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private String nome;
	private String telefone;
	private String email;
	private Date momento;
	
	List<Servico> servico = new ArrayList<>();
	
	public Cliente() {
	}
		
	public Cliente(String nome, String telefone, String email, Date momento) {
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.momento = momento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getMomento() {
		return momento;
	}

	public void setMomento(Date momento) {
		this.momento = momento;
	}
	
	public void addLista(Servico srvc) {
		servico.add(srvc);
	}
	
	public void removeLista(Servico srvc) {
		servico.remove(srvc);
	}
	
	public double pagamento() {
		double soma = 0;
		for(Servico a: servico) {
			soma += a.total();
		}
		return soma;
	}
	
	public String texto() {
	StringBuilder sb = new StringBuilder();
		sb.append(nome);
		sb.append(", TELEFONE: ");
		sb.append(telefone);
		sb.append(", E-MAIL: ");
		sb.append(email);
		sb.append(" ");
		sb.append(sdf.format(new Date()));
		sb.append("\n");
		for (Servico a: servico) {
			sb.append("\n");
			sb.append(a.texto());
		}
		return sb.toString();		
				
	}
	
	
}
