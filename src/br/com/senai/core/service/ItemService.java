package br.com.senai.core.service;

import java.util.List;

import br.com.senai.core.dao.DaoItem;
import br.com.senai.core.domain.Item;


public class ItemService {

	private DaoItem dao;
	
	public void gerarSubtotal(Item item) {
		double subTotal = item.getQtde() * item.getMercadoria().getValor();
		item.setSubTotal(subTotal);
	}
	
	public List<Item> listarPor(int idCompra){
		if (idCompra > 0) {
			int filtro =  idCompra ;
			return dao.listarPor(filtro);
		}
		throw new IllegalArgumentException("O id para listagem é obrigatório e deve ser maior que zero");
	}
	
	public void salvar(Item item) {
		
		
		this.validar(item);
		
		this.dao.inserir(item);
		
	}
	
	private void validar(Item item) {
		if (item != null) {
			boolean isIdMercadoriaInvalido = item.getMercadoria().getId() <= 0;
			if (isIdMercadoriaInvalido) {
				throw new IllegalArgumentException("O id da mercadoria deve ser positivo");				
			}
			
			boolean isQtdeInvalido = item.getQtde() <= 0;
			if (isQtdeInvalido) {
				throw new IllegalArgumentException("A quantidade deve ser positiva");				
			}
			
			boolean isSubTotalInvalido = item.getSubTotal() < 0;
			if (isSubTotalInvalido) {
				throw new IllegalArgumentException("O subtotal deve ser positivo");				
			}
			
		} else {
			throw new NullPointerException("O item da compra não pode ser nulo");
		}
	}
	
}
