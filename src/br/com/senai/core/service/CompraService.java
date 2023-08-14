package br.com.senai.core.service;

import java.util.List;

import br.com.senai.core.dao.DaoCompra;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Compra;

public class CompraService {
	
	private DaoCompra dao;
	
	public CompraService() {
		this.dao = FactoryDao.getInstance().getDaoCompra();
	}
	
	public int pegarUltimoId() {
		try {
			int result = this.dao.pegarUltimoId();
			return result;
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public void salvar(Compra compra) {		
		this.validar(compra);		
		boolean isJaInserido = compra.getId() > 0;		
		if (isJaInserido) {
			throw new IllegalArgumentException("Essa");
		}else {
			this.dao.inserir(compra);
		}
	}
	
	
	public void validar(Compra compra) {
		boolean isComprainvalida = compra.getItens() == null;
		if (isComprainvalida) {
			throw new IllegalArgumentException("A compra deve possuir ao menos 1 item");
		}
	}
	
	public List<Compra> listarPor(String fornecedor){
		if (fornecedor != null && !fornecedor.isBlank()) {
			String filtro = "%" + fornecedor + "%";
			return dao.listarPor(filtro);
		}
		throw new IllegalArgumentException("O filtro para listagem é obrigatório");
	}

}
