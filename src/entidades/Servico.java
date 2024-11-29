package entidades;

public class Servico {

	private String nome;
	private Double preco;
	private Integer quantidade;
	
	public Servico() {
	}

	public Servico(String nome, double preco, int quantidade) {
		this.nome = nome;
		this.preco = preco;
		this.quantidade = quantidade;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public double total() {
		return preco * quantidade;
	}
	
	public String texto() {
		return "SERVIÇO: "
				+ nome
				+ ", VALOR R$: "
				+ String.format("%.2f", preco)
				+ ", QUANTIDADE: "
				+ quantidade
				+ ", R$: "
				+ String.format("%.2f", total());
	}
	
	
}
