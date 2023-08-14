package br.com.senai.core.dao;

import br.com.senai.core.dao.postgresql.DaoPostgresqlColaborador;
import br.com.senai.core.dao.postgresql.DaoPostgresqlCompra;
import br.com.senai.core.dao.postgresql.DaoPostgresqlFornecedor;
import br.com.senai.core.dao.postgresql.DaoPostgresqlItem;
import br.com.senai.core.dao.postgresql.DaoPostgresqlMercadoria;


public class FactoryDao {
	
private static FactoryDao instance;
	
	private FactoryDao() {}
	
	public DaoCompra getDaoCompra() {
		return new DaoPostgresqlCompra();
	}
	
	public DaoItem getDaoItem() {
		return new DaoPostgresqlItem();
	}
	
	public DaoFornecedor getDaoFornecedor() {
		return new DaoPostgresqlFornecedor();
	}
	
	public DaoColaborador getDaoColaborador() {
		return new DaoPostgresqlColaborador();
	}
	
	public DaoMercadoria getDaoMercadoria() {
		return new DaoPostgresqlMercadoria();
	}
	
	public static FactoryDao getInstance() {
		if (instance == null) {
			instance = new FactoryDao();
		}
		return instance;
	}
}
