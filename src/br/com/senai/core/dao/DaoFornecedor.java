package br.com.senai.core.dao;

import java.util.List;

import br.com.senai.core.domain.Fornecedor;

public interface DaoFornecedor {

	public void inserir(Fornecedor fornecedor);

	public void alterar(Fornecedor fornecedor);
	
	public void removerPor(int id);
	
	public Fornecedor buscarPor(int id);
	
	public List<Fornecedor> listarPor(String nomeFantasia);
	
	public List<Fornecedor>  listarFornecedor();
	
}
