package br.com.senai.core.domain;

public class Item {
	
	private int qtde;
	
	private Double subTotal;
	
	private Mercadoria mercadoria;

	public Item(int qtde, Double subTotal, Mercadoria mercadoria) {
		this.qtde = qtde;
		this.subTotal = subTotal;
		this.mercadoria = mercadoria;
	}

	public int getQtde() {
		return qtde;
	}

	public void setQtde(int qtde) {
		this.qtde = qtde;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Mercadoria getMercadoria() {
		return mercadoria;
	}

	public void setMercadoria(Mercadoria mercadoria) {
		this.mercadoria = mercadoria;
	}
}
