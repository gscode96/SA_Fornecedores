package br.com.senai.core.domain;

import java.util.Objects;

public class Mercadoria {
	
	private int id;
	
	private String descricaoCurta;
	
	private Double valor;

	public Mercadoria(String descricaoCurta, Double valor) {
		this.descricaoCurta = descricaoCurta;
		this.valor = valor;
	}

	public Mercadoria(int id, String descricaoCurta, Double valor) {
		this(descricaoCurta, valor);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricaoCurta() {
		return descricaoCurta;
	}

	public void setDescricaoCurta(String descricaoCurta) {
		this.descricaoCurta = descricaoCurta;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mercadoria other = (Mercadoria) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return  descricaoCurta;
	}
	
	
	
	
	
	
	
	
	
	
	

}
