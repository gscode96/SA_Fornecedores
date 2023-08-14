package br.com.senai.core.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.core.dao.DaoItem;
import br.com.senai.core.dao.ManagerDb;
import br.com.senai.core.domain.Item;
import br.com.senai.core.domain.Mercadoria;
import br.com.senai.core.service.CompraService;

public class DaoPostgresqlItem implements DaoItem {
	
	private final String INSERT = "INSERT INTO itens_compras (id_mercadoria, id_compra, qtde, subtotal) VALUES (?, ?, ?, ?)";
	
	private final String GET_BY_COMPRA_ID = "SELECT ic.id_mercadoria, ic.qtde, ic.subtotal, m.descricao, m.valor FROM itens_compras ic "
			+ "INNER JOIN mercadorias m "
			+ "ON m.id = ic.id_mercadoria "
			+ "WHERE ic.id_compra = ? ";
	
	private Connection conexao;
	private CompraService serviceCompra;
	
	public DaoPostgresqlItem() {
		this.conexao = ManagerDb.getInstance().getConexao();
	}

	@Override
	public void inserir(Item item) {
		serviceCompra = new CompraService();
		PreparedStatement ps = null;
		try {
			ps = conexao.prepareStatement(INSERT);
			ps.setInt(1, item.getMercadoria().getId());
			ps.setInt(2, serviceCompra.pegarUltimoId());
			ps.setDouble(3, item.getQtde());
			ps.setDouble(4, item.getSubTotal());
			ps.execute();
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao inserir o item da compra. Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().fechar(ps);
		}
		
	}

	@Override
	public List<Item> listarPor(int idDaCompra) {
		List<Item> itensCompra = new ArrayList<Item>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conexao.prepareStatement(GET_BY_COMPRA_ID);
			ps.setInt(1, idDaCompra);
			rs = ps.executeQuery();
			while (rs.next()) {
				itensCompra.add(extrairDo(rs));
			}
		}catch (Exception e) {
			throw new RuntimeException("Ocorre um erro ao listar os itens da compra. Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
		return itensCompra;
	}
	
	private Item extrairDo(ResultSet rs) {
		try {
			int qtde = rs.getInt("qtde");
			double suStotal = rs.getDouble("subtotal");
			int idMercadoria = rs.getInt("id_mercadoria");
			String descricao = rs.getString("descricao");
			double valor = rs.getDouble("valor");
			
			Mercadoria item = new Mercadoria(idMercadoria, descricao, valor);
			
			return new Item(qtde, suStotal, item);
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao extrair o item da compra. Motivo: " + e.getMessage());
		}
	}

}
