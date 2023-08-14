package br.com.senai.core.domain;

import java.util.Objects;

public class Fornecedor {
	
	private int id;
	
	private String razaoSocial;
	
	private String nomeFantasia;
	
	private String cnpj;

	public Fornecedor(String razaoSocial, String nomeFantasia, String cnpj) {
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.cnpj = cnpj;
	}	
	public Fornecedor(int id, String razaoSocial, String nomeFantasia, String cnpj) {
		this(razaoSocial, nomeFantasia, cnpj);
		this.id = id;
	}
	
	public Fornecedor (int id, String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
		this.id = id;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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
		Fornecedor other = (Fornecedor) obj;
		return id == other.id;
	}
	@Override
	public String toString() {
		return  nomeFantasia;
	}
	
	
	
	
	
	
	
	
	
	

}
