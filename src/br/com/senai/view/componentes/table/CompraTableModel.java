package br.com.senai.view.componentes.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.core.domain.Compra;

public class CompraTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	
	private List<Compra> compras;
	
	public CompraTableModel(List<Compra> compras) {
		this.compras = compras;
	}
	
	public Compra getPor(int rowIndex) {
		return compras.get(rowIndex);
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0) {
			return "Código";
		}else if (columnIndex == 1) {
			return "Fornecedor";
		}else if (columnIndex == 2) {
			return "Valor(R$)";
		}
		throw new IllegalArgumentException("Indice inválido");
	}
	
	@Override
	public int getRowCount() {
		return compras.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Compra compraDaLinha = compras.get(rowIndex);
		if (columnIndex == 0) {
			return compraDaLinha.getId();
		}else if (columnIndex == 1) {
			return compraDaLinha.getFornecedor();
		}else if (columnIndex == 2) {
			return compraDaLinha.getTotal();
		}
		throw new IllegalArgumentException("Indice inválido");
	}
}