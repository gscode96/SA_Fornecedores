package br.com.senai.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.senai.core.domain.Compra;
import br.com.senai.core.service.CompraService;
import br.com.senai.view.componentes.table.CompraTableModel;

import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.awt.event.ActionEvent;

public class ViewConsultaCompra extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField edtFiltro;
	private JButton btnListar;
	private JButton btnRegistrar;
	private JButton btnVisualizar;
	private JTable tableCompra;
	private JScrollPane spCompras;
	
	private CompraService service;

	/**
	 * Create the frame.
	 */
	public ViewConsultaCompra() {
		this.service = new CompraService();
		CompraTableModel model = new CompraTableModel(new ArrayList<Compra>());
		this.tableCompra = new JTable(model);
		setResizable(false);
		setTitle("Gerenciar Compra - Listagem");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 411, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewCadastroCompra view = new ViewCadastroCompra();
				view.setVisible(true);
			}
		});
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setBackground(SystemColor.textHighlight);
		btnRegistrar.setBounds(294, 12, 89, 23);
		contentPane.add(btnRegistrar);
		
		JLabel lblFiltro = new JLabel("Filtro");
		lblFiltro.setBounds(12, 47, 55, 16);
		contentPane.add(lblFiltro);
		
		edtFiltro = new JTextField();
		edtFiltro.setColumns(10);
		edtFiltro.setBounds(12, 75, 216, 20);
		contentPane.add(edtFiltro);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String filtroInformado = edtFiltro.getText();
					List<Compra> comprasEncontradas = service.listarPor(filtroInformado);
					List<Compra> compraFiltrada = new ArrayList<Compra>();
					HashSet<Integer> setId = new HashSet<>();
					comprasEncontradas.forEach(v -> {
						if (!setId.contains(v.getId())) {
							setId.add(v.getId());
							compraFiltrada.add(v);
						}
					});
					CompraTableModel model = new CompraTableModel(compraFiltrada);
					tableCompra.setModel(model);
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}			
			}
		});
		btnListar.setForeground(Color.WHITE);
		btnListar.setBackground(SystemColor.textHighlight);
		btnListar.setBounds(240, 72, 89, 23);
		contentPane.add(btnListar);
		
		JLabel lblResultados = new JLabel("Resultados");
		lblResultados.setBounds(12, 107, 74, 16);
		contentPane.add(lblResultados);
		
		spCompras = new JScrollPane(tableCompra);
		spCompras.setBounds(12, 135, 375, 86);
		contentPane.add(spCompras);
		
		
		JButton btnVisualizar = new JButton("Visualizar");
		btnVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tableCompra.getSelectedRow();
				if (linhaSelecionada >= 0) {
					CompraTableModel model = (CompraTableModel)tableCompra.getModel();
					Compra compraSelecionada = model.getPor(linhaSelecionada);
					ViewCadastroCompra view = new ViewCadastroCompra();
					view.setCompra(compraSelecionada);
					view.setVisible(true);
					dispose();
					
					
				}else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para visualizar",
							"Aviso", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnVisualizar.setForeground(Color.WHITE);
		btnVisualizar.setBackground(SystemColor.textHighlight);
		btnVisualizar.setBounds(277, 233, 106, 23);
		contentPane.add(btnVisualizar);
	}
}
