package br.com.senai.view.componentes.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.core.domain.Item;

public class ItemTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	
	private List<Item> itens;
	
	public ItemTableModel(List<Item> itens) {
		this.itens = itens;
	}
	
	public Item getPor(int rowIndex) {
		return itens.get(rowIndex);
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0) {
			return "Código";
		}else if (columnIndex == 1) {
			return "Mercadoria";
		}else if (columnIndex == 2) {
			return "Qtde";
		}else if (columnIndex == 3) {
			return "Valor(R$)";
		}else if (columnIndex == 4) {
			return "Subtotal(R$)";
		}
		throw new IllegalArgumentException("Indice inválido");
	}
	
	@Override
	public int getRowCount() {
		return itens.size();
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Item itemDaLinha = itens.get(rowIndex);
		if (columnIndex == 0) {
			return itemDaLinha.getMercadoria().getId();
		}else if (columnIndex == 1) {
			return itemDaLinha.getMercadoria().getDescricaoCurta();
		}else if (columnIndex == 2) {
			return itemDaLinha.getQtde();
		}else if (columnIndex == 3) {
			return itemDaLinha.getMercadoria().getValor();
		}else if (columnIndex == 4) {
			return itemDaLinha.getSubTotal();
		}
		throw new IllegalArgumentException("Indice inválido");
	}
	
	public void removerPor(int rowIndex) {
		this.itens.remove(rowIndex);
	}


}
