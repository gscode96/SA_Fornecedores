
package br.com.senai.core.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.core.dao.DaoMercadoria;
import br.com.senai.core.dao.ManagerDb;
import br.com.senai.core.domain.Mercadoria;

public class DaoPostgresqlMercadoria implements DaoMercadoria {
	
	private final String SELECT_TODAS = "SELECT c.id, c.descricao, c.valor "
			+ "FROM mercadorias c "
			+ "ORDER BY c.descricao ";
	
	private Connection conexao;
	
	public DaoPostgresqlMercadoria() {
		this.conexao = ManagerDb.getInstance().getConexao();
	}
	
	@Override
	public List<Mercadoria> listarTodas() {
		List<Mercadoria> mercadorias = new ArrayList<Mercadoria>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conexao.prepareStatement(SELECT_TODAS);
			rs = ps.executeQuery();
			while (rs.next()) {
				mercadorias.add(extrairDo(rs));
			}
		}catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na listagem de mercadorias. Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
		return mercadorias;
	}
	
	private Mercadoria extrairDo(ResultSet rs) {
		try {
			int id = rs.getInt("id");
			String descricao = rs.getString("descricao");
			Double valor = rs.getDouble("valor");
			return new Mercadoria(id, descricao, valor);
		}catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na extração da mercadoria. Motivo: " + e.getMessage());
		}
	}

}

