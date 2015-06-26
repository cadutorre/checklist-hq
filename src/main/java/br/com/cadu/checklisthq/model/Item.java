package br.com.cadu.checklisthq.model;

public class Item {

	private String nome;
	private String data;
	private String imageUrl;

	public Item(String nome, String data, String imageUrl) {
		super();
		this.nome = nome;
		this.data = data;
		this.imageUrl = imageUrl;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return new StringBuilder("Item: ").append("[nome = " + nome + "]").append("[data = " + data + "]").append("[imageUrl = " + imageUrl + "]").toString();
	}
}
