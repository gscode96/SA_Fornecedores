package br.com.senai.core.dao.postgresql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import br.com.senai.core.dao.DaoCompra;
import br.com.senai.core.dao.ManagerDb;
import br.com.senai.core.domain.Colaborador;
import br.com.senai.core.domain.Compra;
import br.com.senai.core.domain.Fornecedor;
import br.com.senai.core.domain.Item;


public class DaoPostgresqlCompra implements DaoCompra {

	private final String INSERT = "INSERT INTO compras (data, total, observacoes, id_fornecedor,"
			+ " id_colaborador) VALUES (?, ?, ?, ?, ?)";

	private final String SELECT_BY_ID = "SELECT compras.id, compras.data, compras.total, "
			+ "compras.observacoes, compras.id_fornecedor, colaboradores.nome_completo, fornecedores.nome_fantasia, colaboradores.id, fornecedores.id "
			+ "compras.id_colaborador FROM compras, fornecedores, colaboradores WHERE compras.id = ? ";

	private final String SELECT_BY_NAME = "SELECT compras.id, data, total, observacoes, "
			+ "colaboradores.nome_completo, fornecedores.nome_fantasia, "
			+ "colaboradores.id as id_colaborador, "
			+ "fornecedores.id as id_fornecedor "
			+ "FROM compras, fornecedores, colaboradores "
			+ "WHERE compras.id_fornecedor = fornecedores.id "
			+ "AND colaboradores.id = compras.id_colaborador "
			+ "AND Upper(nome_fantasia) LIKE Upper(?)";
	
	private final String GET_LAST_ID = "SELECT MAX(id) FROM compras";
	

	private Connection conexao;

	public DaoPostgresqlCompra() {
		this.conexao = ManagerDb.getInstance().getConexao();
	}

	@Override

	public void inserir(Compra compra) {
		PreparedStatement ps = null;
		try {
			ManagerDb.getInstance().configurarAutocommitDa(conexao, false);
			ps = conexao.prepareStatement(INSERT);
				ps.setDate(1, Date.valueOf(compra.getData()));
				ps.setDouble(2, compra.getTotal());
				ps.setString(3, compra.getObservacoes());
				ps.setInt(4, compra.getFornecedor().getId());
				ps.setInt(5, compra.getColaborador().getId());
				ps.execute();
			this.conexao.commit();
			ManagerDb.getInstance().configurarAutocommitDa(conexao, true);
		} catch (Exception e) {
			ManagerDb.getInstance().realizarRollbackNa(conexao);
			throw new RuntimeException("Ocorreu um erro ao inserir as compras. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
		}
	}
	
	@Override
	public List<Compra> listarPor(String nomeFantasia) {
		List<Compra> compras = new ArrayList<Compra>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conexao.prepareStatement(SELECT_BY_NAME);
			ps.setString(1, nomeFantasia);
			rs = ps.executeQuery();
			while (rs.next()) {
				compras.add(extrairDo(rs));
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorre um erro ao listar as compras. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
		return compras;
	}

	private Compra extrairDo(ResultSet rs) {
		try {
			
			int idDaCompra = rs.getInt("id");
			
			LocalDate data = rs.getDate("data").toLocalDate();
			
			double total = rs.getDouble("total");
			
			String observacoes = rs.getString("observacoes");

			int idDoColaborador = rs.getInt("id_colaborador");
			String nomeCompleto = rs.getString("nome_completo");
			Colaborador colaborador = new Colaborador(idDoColaborador, nomeCompleto);
			
			int idDoFornecedor = rs.getInt("id_fornecedor");
			String nomeFantasia = rs.getString("nome_fantasia");
			Fornecedor fornecedor = new Fornecedor(idDoFornecedor, nomeFantasia);
			
			return new Compra(idDaCompra, data, total, observacoes, colaborador, fornecedor, null);

		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao extrair os colaboradores. Motivo: " + e.getMessage());
		}
	}
	
	@Override
	public int pegarUltimoId() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conexao.prepareStatement(GET_LAST_ID);
			rs = ps.executeQuery();
			if (rs.next()) {
				int ultimoId = rs.getInt("max");
				return ultimoId;
			}	
			return -1;
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao "
					+ "buscar o projeto. Motivo: " + e.getMessage());
		}
	}
	
}
