package br.com.senai.view;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.senai.core.domain.Colaborador;
import br.com.senai.core.domain.Compra;
import br.com.senai.core.domain.Fornecedor;
import br.com.senai.core.domain.Item;
import br.com.senai.core.domain.Mercadoria;
import br.com.senai.core.service.ColaboradorService;
import br.com.senai.core.service.CompraService;
import br.com.senai.core.service.FornecedorService;
import br.com.senai.core.service.ItemService;
import br.com.senai.core.service.MercadoriaService;
import br.com.senai.view.componentes.table.CompraTableModel;
import br.com.senai.view.componentes.table.ItemTableModel;

public class ViewCadastroCompra extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTable tableItens;

	private JTextField edtDataDaCompra;
	private JTextField edtQtde;
	private JTextField edtTotal;
	private JButton btnAdicionar;
	private JButton btnSalvar;
	private JTextArea taObservacoes;

	private JComboBox<Colaborador> cbColaborador;
	private JComboBox<Fornecedor> cbFornecedor;
	private JComboBox<Mercadoria> cbMercadoria;
	private JButton btnRemover;

	private CompraService compraService = new CompraService();
	private ColaboradorService colaboradorService = new ColaboradorService();
	private FornecedorService fornecedorService = new FornecedorService();
	private MercadoriaService mercadoriaService = new MercadoriaService();
	private ItemService itemService = new ItemService();

	private List<Item> itens = new ArrayList<>();

	public void carregarComboColaborador() {
		List<Colaborador> colaboradores = colaboradorService.listarTodos();
		for (Colaborador u : colaboradores) {
			cbColaborador.addItem(u);
		}
	}

	public void carregarComboFornecedor() {
		List<Fornecedor> colaboradores = fornecedorService.listarTodos();
		for (Fornecedor u : colaboradores) {
			cbFornecedor.addItem(u);
		}
	}

	public void carregarComboMercadoria() {
		List<Mercadoria> mercadorias = mercadoriaService.listarTodas();
		for (Mercadoria u : mercadorias) {
			cbMercadoria.addItem(u);
		}
	}
	
	private double gerarTotal() {
		double totalVenda = 0.0;
		for (int i = 0; i < itens.size(); i++) {
			totalVenda += itens.get(i).getMercadoria().getValor() * itens.get(i).getQtde();
		}
		return totalVenda;
	}
	
	public void setCompra(Compra compra) {
		this.cbFornecedor.setSelectedItem(compra.getFornecedor());
		this.cbColaborador.setSelectedItem(compra.getColaborador());
		this.edtTotal.setText(String.valueOf(compra.getTotal()));	
		
		this.cbColaborador.setEnabled(false);
		this.cbFornecedor.setEnabled(false);
		this.edtTotal.setEnabled(false);
		this.cbMercadoria.setEnabled(false);
		this.edtQtde.setEnabled(false);
		this.btnAdicionar.setEnabled(false);
		this.taObservacoes.setEnabled(false);
		this.btnSalvar.setEnabled(false);
		
		itens = itemService.listarPor(compra.getId());
		
		ItemTableModel model = new ItemTableModel(itens);
		this.tableItens.setModel(model);
		
	}

	/**
	 * Create the frame.
	 */
	public ViewCadastroCompra() {
		setTitle("Gerenciar Compra - Cadastro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 491, 633);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setForeground(Color.WHITE);
		btnConsultar.setBackground(SystemColor.textHighlight);
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConsultar.setBounds(374, 12, 89, 23);
		contentPane.add(btnConsultar);

		JLabel lblColaborador = new JLabel("Colaborador*");
		lblColaborador.setBounds(10, 48, 82, 16);
		contentPane.add(lblColaborador);

		cbColaborador = new JComboBox();
		cbColaborador.setBounds(10, 75, 332, 25);
		contentPane.add(cbColaborador);

		JLabel lblDataDaCompra = new JLabel("Data da Compra");
		lblDataDaCompra.setBounds(354, 48, 108, 16);
		contentPane.add(lblDataDaCompra);

		edtDataDaCompra = new JTextField();
		edtDataDaCompra.setEnabled(false);
		edtDataDaCompra.setBounds(354, 75, 109, 25);
		contentPane.add(edtDataDaCompra);
		edtDataDaCompra.setColumns(10);

		JLabel lblFornecedor = new JLabel("Fornecedor*");
		lblFornecedor.setBounds(10, 112, 82, 16);
		contentPane.add(lblFornecedor);

		cbFornecedor = new JComboBox();
		cbFornecedor.setBounds(10, 140, 453, 25);
		contentPane.add(cbFornecedor);

		JLabel lblMercadoria = new JLabel("Mercadoria*");
		lblMercadoria.setBounds(10, 177, 82, 16);
		contentPane.add(lblMercadoria);

		cbMercadoria = new JComboBox();
		cbMercadoria.setBounds(10, 205, 211, 25);
		contentPane.add(cbMercadoria);

		btnRemover = new JButton("Remover");
		btnRemover.setEnabled(false);
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tableItens.getSelectedRow();
				String[] options = { "Remover", "Cancelar" };
				if (linhaSelecionada >= 0) {

					int opcao = JOptionPane.showOptionDialog(null, "Deseja remover a mercadoria selecionada?",
							"Remoção", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
							options[0]);

					if (opcao == 0) {
						ItemTableModel model = (ItemTableModel) tableItens.getModel();
						model.removerPor(linhaSelecionada);
						tableItens.updateUI();
						JOptionPane.showMessageDialog(contentPane, "Item removido da venda com sucesso");
						edtTotal.setText(Double.toString(gerarTotal()));
						btnRemover.setEnabled(itens.size() > 0);
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "A seleção da mercadoria para remoção é obrigatória",
							"Aviso", JOptionPane.WARNING_MESSAGE);
				}
			
			}
		});
	
		
	
		
		btnRemover.setForeground(Color.blue);
		btnRemover.setBackground(SystemColor.textHighlight);
		btnRemover.setBounds(374, 204, 89, 23);
		contentPane.add(btnRemover);

		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mercadoria mercadoria = (Mercadoria) cbMercadoria.getSelectedItem();
				if (mercadoria == null) {
					JOptionPane.showMessageDialog(contentPane, "A seleção da mercadoria para inclusão é obrigatória",
							"Aviso", JOptionPane.WARNING_MESSAGE);
				} else if (edtQtde.getText().isBlank()) {
					JOptionPane.showMessageDialog(contentPane, "A quantidade do item é obrigatória", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				} else if (Integer.parseInt(edtQtde.getText()) == 0) {
					JOptionPane.showMessageDialog(contentPane, "A quantidade do item não pode ser zero", "Aviso",
							JOptionPane.WARNING_MESSAGE);
					edtQtde.setText("");
				} else {
					double valor = mercadoria.getValor();
					int qtde = Integer.parseInt(edtQtde.getText());
					double subTotal = valor * qtde;
					Item item = new Item(qtde, subTotal, mercadoria);
					
					if (itens.size() > 0) {
						if (itens.contains(item)) {
							JOptionPane.showMessageDialog(contentPane, "Mercadoria já existente na venda.", "Aviso",
									JOptionPane.WARNING_MESSAGE);
						} else {
							itens.add(item);
							edtTotal.setText(Double.toString(gerarTotal()));
							cbMercadoria.setSelectedIndex(0);
							edtQtde.setText(null);
						}
					} else {
						itens.add(item);
					}
					ItemTableModel model = new ItemTableModel(itens);
					btnRemover.setEnabled(itens.size() > 0);
					tableItens.setModel(model);
					edtTotal.setText(Double.toString(gerarTotal()));
					cbMercadoria.setSelectedIndex(0);
					edtQtde.setText(null);
					JOptionPane.showMessageDialog(contentPane, "Item inserido na venda com sucesso", "Sucesso",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});

		btnAdicionar.setForeground(Color.WHITE);
		btnAdicionar.setBackground(SystemColor.textHighlight);
		btnAdicionar.setBounds(273, 204, 89, 23);
		contentPane.add(btnAdicionar);

		edtQtde = new JTextField();
		edtQtde.setBounds(233, 205, 28, 25);
		contentPane.add(edtQtde);
		edtQtde.setColumns(10);

		JLabel lblQtde = new JLabel("Qtde");
		lblQtde.setBounds(233, 177, 55, 16);
		contentPane.add(lblQtde);

		JLabel lblItens = new JLabel("Itens");
		lblItens.setBounds(12, 242, 57, 16);
		contentPane.add(lblItens);

		JScrollPane spItens = new JScrollPane();
		spItens.setBounds(10, 270, 453, 87);
		contentPane.add(spItens);

		tableItens = new JTable();
		spItens.setViewportView(tableItens);
		tableItens.setModel(new ItemTableModel(itens));

		JLabel lblTotal = new JLabel("Total (R$)");
		lblTotal.setBounds(349, 369, 55, 16);
		contentPane.add(lblTotal);

		edtTotal = new JTextField();
		edtTotal.setEditable(false);
		edtTotal.setBounds(349, 397, 114, 20);
		contentPane.add(edtTotal);
		edtTotal.setColumns(10);

		JLabel lblObservacoes = new JLabel("Observa\u00E7\u00F5es");
		lblObservacoes.setBounds(10, 429, 89, 16);
		contentPane.add(lblObservacoes);

		taObservacoes = new JTextArea();
		taObservacoes.setBounds(10, 458, 453, 87);
		contentPane.add(taObservacoes);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		edtDataDaCompra.setText(LocalDate.now().format(dtf));

		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Fornecedor fornecedor = (Fornecedor) cbFornecedor.getSelectedItem();
				Colaborador colaborador = (Colaborador) cbColaborador.getSelectedItem();

				String obs = taObservacoes.getText();
				LocalDate data = LocalDate.from(dtf.parse(edtDataDaCompra.getText()));
				Double total = Double.parseDouble(edtTotal.getText()) ;
			
				
			 compraService.salvar(new Compra(data, total, obs, colaborador, fornecedor, itens));
			 JOptionPane.showMessageDialog(contentPane, "Compra salva com sucesso!");
			 
			 
			}
		});
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setBackground(SystemColor.textHighlight);
		btnSalvar.setBounds(374, 559, 89, 23);
		contentPane.add(btnSalvar);

		

		carregarComboFornecedor();

		carregarComboColaborador();

		carregarComboMercadoria();

	}
}
