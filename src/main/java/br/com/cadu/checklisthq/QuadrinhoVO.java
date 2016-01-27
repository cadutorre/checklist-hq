package br.com.cadu.checklisthq;

public class QuadrinhoVO {

	private final String nome;
	private final String detalheUrl;
	private final String imagemUrl;
	private final Double preco;

	public QuadrinhoVO(String nome,String detalheUrl,String imagemUrl,Double preco) {
		super();
		this.nome = nome;
		this.detalheUrl = detalheUrl;
		this.imagemUrl = imagemUrl;
		this.preco = preco;
	}

	public String getNome() {
		return nome;
	}

	public String getDetalheUrl() {
		return detalheUrl;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public Double getPreco() {
		return preco;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(nome).append(",").append(detalheUrl).append(",").append(imagemUrl).append(",").append(preco).toString();
	}

}
