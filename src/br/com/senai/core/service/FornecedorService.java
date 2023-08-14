package br.com.senai.core.service;

import java.util.List;

import br.com.senai.core.dao.DaoFornecedor;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Fornecedor;

public class FornecedorService {
	
	private DaoFornecedor dao;
	
	public FornecedorService() {
		this.dao = FactoryDao.getInstance().getDaoFornecedor();
	}
	
	public void salvar(Fornecedor fornecedor) {		
		this.validar(fornecedor);		
		boolean isJaInserido = fornecedor.getId() > 0;		
		if (isJaInserido) {
			this.dao.alterar(fornecedor);
		}else {
			this.dao.inserir(fornecedor);
		}
	}
	
	private void validar(Fornecedor fornecedor) {
		if (fornecedor != null) {
			
			boolean isRazaoSocialInvalida = fornecedor.getRazaoSocial() == null 
					|| fornecedor.getRazaoSocial().isBlank() 
					|| fornecedor.getRazaoSocial().length() < 2 
					|| fornecedor.getRazaoSocial().length() > 100;

			if (isRazaoSocialInvalida) {
				throw new IllegalArgumentException("A razão social é obrigatória "
						+ "e deve possuir entre 2 e 100 caracteres");
			}
			
			boolean isNomeFantasiaInvalido = fornecedor.getNomeFantasia() == null 
					|| fornecedor.getNomeFantasia().isBlank() 
					|| fornecedor.getNomeFantasia().length() < 2
					|| fornecedor.getNomeFantasia().length() > 150;
					
			if (isNomeFantasiaInvalido) {
				throw new IllegalArgumentException("O nome fantasia é obrigatório "
						+ "e deve possuir entre 2 e 150 caracteres");
			}
			
			boolean isCnpjInvalido = fornecedor.getCnpj() == null 
					|| fornecedor.getCnpj().isBlank() 
					|| fornecedor.getCnpj().length() != 18
					|| fornecedor.getCnpj().equals("  .   .   /    -  ");
					
			if (isCnpjInvalido) {
				throw new IllegalArgumentException("O cnpj é obrigatório "
						+ "e deve possuir 14 caracteres");
			}
			
		}else {
			throw new NullPointerException("O Fornecedor não pode ser nulo");
		}
	}
	
	public void removerPor(int idFornecedor) {
		if (idFornecedor > 0) {
			this.dao.removerPor(idFornecedor);
		}else {
			throw new IllegalArgumentException("O id para remoção do Fornecedor deve ser maior que zero");
		}
	}
	
	public List<Fornecedor> listarPor(String nomeFantasia){
		if (nomeFantasia != null && !nomeFantasia.isBlank()) {
			String filtro = nomeFantasia + "%";
			return dao.listarPor(filtro);		
		}else {
			throw new IllegalArgumentException("O filtro para listagem é obrigatório");
		}
	}
	
	public List<Fornecedor> listarTodos() {
	
		
		return dao.listarFornecedor();
		
	}

}
