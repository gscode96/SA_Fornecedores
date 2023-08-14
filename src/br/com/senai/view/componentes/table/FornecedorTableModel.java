package br.com.senai.view.componentes.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.core.domain.Fornecedor;

public class FornecedorTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	
	private List<Fornecedor> fornecedores;
	
	public FornecedorTableModel(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}
	
	public Fornecedor getPor(int rowIndex) {
		return fornecedores.get(rowIndex);
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0) {
			return "Código";
		}else if (columnIndex == 1) {
			return "Nome Fantasia";
		}else if (columnIndex == 2) {
			return "CNPJ";
		}
		throw new IllegalArgumentException("Indice inválido");
	}
	
	@Override
	public int getRowCount() {
		return fornecedores.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Fornecedor fornecedorDaLinha = fornecedores.get(rowIndex);
		if (columnIndex == 0) {
			return fornecedorDaLinha.getId();
		}else if (columnIndex == 1) {
			return fornecedorDaLinha.getNomeFantasia();
		}else if (columnIndex == 2) {
			return fornecedorDaLinha.getCnpj();
		}
		throw new IllegalArgumentException("Indice inválido");
	}
	
	public void removerPor(int rowIndex) {
		this.fornecedores.remove(rowIndex);
	}


}
