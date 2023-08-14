package br.com.senai.core.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.core.dao.DaoColaborador;
import br.com.senai.core.dao.ManagerDb;
import br.com.senai.core.domain.Colaborador;

public class DaoPostgresqlColaborador implements DaoColaborador {
	
	private final String SELECT_TODOS = "SELECT c.id, c.nome_completo "
			+ "FROM colaboradores c "
			+ "ORDER BY c.nome_completo ";
	
	private Connection conexao;
	
	public DaoPostgresqlColaborador() {
		this.conexao = ManagerDb.getInstance().getConexao();
	}
	
	@Override
	public List<Colaborador> listarTodos() {
		List<Colaborador> colaboradores = new ArrayList<Colaborador>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conexao.prepareStatement(SELECT_TODOS);
			rs = ps.executeQuery();
			while (rs.next()) {
				colaboradores.add(extrairDo(rs));
			}
		}catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na listagem de colaboradores. Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
		return colaboradores;
	}
	
	private Colaborador extrairDo(ResultSet rs) {
		try {
			int id = rs.getInt("id");
			String nomeCompleto = rs.getString("nome_completo");
			return new Colaborador(id, nomeCompleto);
		}catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na extração do colaborador. Motivo: " + e.getMessage());
		}
	}

}
