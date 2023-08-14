package br.com.senai.core.service;

import java.util.List;

import br.com.senai.core.dao.DaoMercadoria;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Mercadoria;

public class MercadoriaService {
	
	private DaoMercadoria dao;
	
	public MercadoriaService() {
		this.dao = FactoryDao.getInstance().getDaoMercadoria();
	}	
	
	public List<Mercadoria> listarTodas(){
		return dao.listarTodas();
	}

}
