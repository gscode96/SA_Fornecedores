package br.com.senai.core.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;

public class Compra {
	
	private int id;
	
	private LocalDate data;
	
	private Double total;
	
	private String observacoes;
	
	private Colaborador colaborador;
	
	private Fornecedor fornecedor;
	
	private List<Item> itens;

	public Compra(LocalDate data, Double total, String observacoes, Colaborador colaborador, Fornecedor fornecedor,
			List<Item> itens) {
		this.data = data;
		this.total = total;
		this.observacoes = observacoes;
		this.colaborador = colaborador;
		this.fornecedor = fornecedor;
		this.itens = itens;
	}

	public Compra(int id, LocalDate data, Double total, String observacoes, Colaborador colaborador,
			Fornecedor fornecedor, List<Item> itens) {
		this(data, total, observacoes, colaborador, fornecedor, itens);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItem(List<Item> itens) {
		this.itens = itens;
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
		Compra other = (Compra) obj;
		return id == other.id;
	}
	
	
	
	
	
	
	
	
	

}
