package br.com.senai.view;

import java.awt.Color;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.senai.core.domain.Fornecedor;
import br.com.senai.core.service.FornecedorService;
import br.com.senai.view.componentes.table.FornecedorTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewConsultaFornecedor extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private FornecedorService service;
	
	private JTextField edtFiltro;
	
	private JTable tableFornecedor;

	/**
	 * Create the frame.
	 */
	public ViewConsultaFornecedor() {
		this.service = new FornecedorService();
		FornecedorTableModel model = new FornecedorTableModel(new ArrayList<Fornecedor>());
		this.tableFornecedor = new JTable(model);
		setResizable(false);
		setTitle("Gerenciar Fornecedor - Listagem");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 411, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblFiltro = new JLabel("Filtro");
		lblFiltro.setBounds(10, 45, 55, 16);
		contentPane.add(lblFiltro);
		
		edtFiltro = new JTextField();
		edtFiltro.setBounds(10, 72, 216, 20);
		contentPane.add(edtFiltro);
		edtFiltro.setColumns(10);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String filtroInformado = edtFiltro.getText();
					List<Fornecedor> fornecedoresEncontrados = service.listarPor(filtroInformado);
					FornecedorTableModel model = new FornecedorTableModel(fornecedoresEncontrados);
					tableFornecedor.setModel(model);
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}		
			}
		});
		btnListar.setForeground(Color.WHITE);
		btnListar.setBackground(SystemColor.textHighlight);
		btnListar.setBounds(236, 71, 89, 23);
		contentPane.add(btnListar);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewCadastroFornecedor view = new ViewCadastroFornecedor();
				view.setVisible(true);
				dispose();
			}
		});
		btnAdicionar.setForeground(Color.WHITE);
		btnAdicionar.setBackground(SystemColor.textHighlight);
		btnAdicionar.setBounds(296, 11, 89, 23);
		contentPane.add(btnAdicionar);
		
		JScrollPane spFornecedores = new JScrollPane(tableFornecedor);
		spFornecedores.setBounds(10, 130, 375, 86);
		contentPane.add(spFornecedores);
		
		JLabel lblResultados = new JLabel("Resultados");
		lblResultados.setBounds(10, 103, 74, 16);
		contentPane.add(lblResultados);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tableFornecedor.getSelectedRow();
				if (linhaSelecionada >= 0) {
					
					int opcao = JOptionPane.showConfirmDialog(contentPane, 
							"Deseja remover o registro selecionado?", 
							"Remoção", JOptionPane.YES_NO_OPTION);
					
					if (opcao == 0) {
						FornecedorTableModel model = (FornecedorTableModel)tableFornecedor.getModel();
						Fornecedor fornecedorSelecionado = model.getPor(linhaSelecionada);
						try {
							model.removerPor(linhaSelecionada);
							service.removerPor(fornecedorSelecionado.getId());
							tableFornecedor.updateUI();
							JOptionPane.showMessageDialog(contentPane, 
									"Fornecedor removido com sucesso");
						}catch (Exception ex) {
							JOptionPane.showMessageDialog(contentPane, ex.getMessage());
						}
						
					}
					
				}else {
					JOptionPane.showMessageDialog(contentPane, "Selecione um registro na tabela para remoção");
				}
			}
		});
		btnRemover.setForeground(Color.WHITE);
		btnRemover.setBackground(SystemColor.textHighlight);
		btnRemover.setBounds(296, 227, 89, 23);
		contentPane.add(btnRemover);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tableFornecedor.getSelectedRow();
				if(linhaSelecionada >= 0) {
					FornecedorTableModel model = (FornecedorTableModel)tableFornecedor.getModel();
					Fornecedor fornecedorSelecionado = model.getPor(linhaSelecionada);
					ViewCadastroFornecedor view = new ViewCadastroFornecedor();
					view.setFornecedor(fornecedorSelecionado);
					view.setVisible(true);
					dispose();
				}else {
					JOptionPane.showMessageDialog(contentPane, "Selecione um registro na tabela para edição");
				}
				
			}
		});
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setBackground(SystemColor.textHighlight);
		btnEditar.setBounds(197, 227, 89, 23);
		contentPane.add(btnEditar);
	}
}
