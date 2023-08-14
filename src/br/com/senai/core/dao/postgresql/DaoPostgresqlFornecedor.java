package br.com.senai.core.dao.postgresql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.core.dao.DaoFornecedor;
import br.com.senai.core.dao.ManagerDb;
import br.com.senai.core.domain.Colaborador;
import br.com.senai.core.domain.Fornecedor;

public class DaoPostgresqlFornecedor implements DaoFornecedor {
	
	private final String INSERT = "INSERT INTO fornecedores (razao_social, "
			+ "nome_fantasia, cnpj) VALUES (?, ?, ?) ";
	
	private final String UPDATE = "UPDATE fornecedores SET razao_social = ?, "
			+ "nome_fantasia = ?, cnpj = ? WHERE id = ? ";
	
	private final String DELETE = "DELETE FROM fornecedores WHERE id = ? ";
	
	private final String SELECT_BY_ID = "SELECT id, razao_social, nome_fantasia, "
			+ "cnpj FROM fornecedores WHERE id = ? ";
	
	private final String SELECT_BY_NAME = "SELECT id, razao_social, nome_fantasia, "
			+ "cnpj FROM fornecedores WHERE Upper(nome_fantasia) LIKE Upper(?) ";
	
	private final String SELECT_TODOS = "SELECT id, nome_fantasia FROM fornecedores ORDER BY id";
	
	private Connection conexao;

	public DaoPostgresqlFornecedor() {

		this.conexao = ManagerDb.getInstance().getConexao();
	}

	@Override
	public void inserir(Fornecedor fornecedor) {
		PreparedStatement ps = null;
		try {
			ps = conexao.prepareStatement(INSERT);
			ps.setString(1, fornecedor.getRazaoSocial());
			ps.setString(2, fornecedor.getNomeFantasia());
			ps.setString(3, fornecedor.getCnpj());
			ps.execute();
		}catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao "
					+ "inserir o fornecedor. Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().fechar(ps);
		}
	}

	@Override
	public void alterar(Fornecedor fornecedor) {
		PreparedStatement ps = null;
		try {
			ManagerDb.getInstance().configurarAutocommitDa(conexao, false);			
			ps = conexao.prepareStatement(UPDATE);
			ps.setString(1, fornecedor.getRazaoSocial());
			ps.setString(2, fornecedor.getNomeFantasia());
			ps.setString(3, fornecedor.getCnpj());
			ps.setInt(4, fornecedor.getId());
			boolean isAlteracaoOK = ps.executeUpdate() == 1;
			if (isAlteracaoOK) {
				this.conexao.commit();
			}else {
				this.conexao.rollback();
			}
			ManagerDb.getInstance().configurarAutocommitDa(conexao, true);
		}catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao "
					+ "alterar o fornecedor. Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().fechar(ps);
		}
		
	}

	@Override
	public void removerPor(int id) {
		PreparedStatement ps = null;
		try {
			ManagerDb.getInstance().configurarAutocommitDa(conexao, false);
			ps = conexao.prepareStatement(DELETE);
			ps.setInt(1, id);
			boolean isExclusaoOK = ps.executeUpdate() == 1;
			if (isExclusaoOK) {
				this.conexao.commit();				
			}else {
				this.conexao.rollback();
			}
			ManagerDb.getInstance().configurarAutocommitDa(conexao, true);
		}catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao "
					+ "excluir o fornecedor. Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().fechar(ps);
		}		
	}
	
	@Override
	public Fornecedor buscarPor(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conexao.prepareStatement(SELECT_BY_ID);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				return extrairDo(rs);
			}
			return null;
		}catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao "
					+ "buscar o fornecedor. Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
	}

	@Override
	public List<Fornecedor> listarPor(String nomeFantasia) {
		List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conexao.prepareStatement(SELECT_BY_NAME);
			ps.setString(1, nomeFantasia);
			rs = ps.executeQuery();
			while (rs.next()) {
				fornecedores.add(extrairDo(rs));
			}
			return fornecedores;
		}catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao "
					+ "listar os fornecedores. Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
	}
	
	private Fornecedor extrairDo(ResultSet rs) {
		try {
			int id = rs.getInt("id");
		
			String nomeFantasia = rs.getString("nome_fantasia");;
					
			return new Fornecedor(id, nomeFantasia);
		}catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao "
					+ "extrair o fornecedor. Motivo: " + e.getMessage());
		}
	}
		
		public List<Fornecedor> listarFornecedor () {
			PreparedStatement ps = null;
			List<Fornecedor> fornecedor = new ArrayList<Fornecedor>();
			ResultSet rs = null;
			try {
				ps = conexao.prepareStatement(SELECT_TODOS);
				rs = ps.executeQuery();
				 while (rs.next()) {
					 fornecedor.add(extrairDo(rs));
				 }
				return fornecedor;
				
			} catch (Exception e) {
				throw new RuntimeException("Ocorreu um erro ao listar os colaboradores. Motivo: " + e.getMessage());
			} finally {
				ManagerDb.getInstance().fechar(ps);
				ManagerDb.getInstance().fechar(rs);
				
				
				
			}
		}
}
