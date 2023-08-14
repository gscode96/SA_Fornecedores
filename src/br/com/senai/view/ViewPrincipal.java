package br.com.senai.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public ViewPrincipal() {
		setTitle("ERP SENAI - M\u00F3dulo de Gest\u00E3o de Compras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JMenuBar barraPrincipal = new JMenuBar();
		barraPrincipal.setBounds(0, 0, 630, 22);
		contentPane.add(barraPrincipal);
		
		JMenu menuCadastros = new JMenu("Cadastros");
		barraPrincipal.add(menuCadastros);
		
		JMenuItem opcaoFornecedores = new JMenuItem("Fornecedores");
		opcaoFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewConsultaFornecedor view = new ViewConsultaFornecedor();
				view.setVisible(true);
			}
		});
		menuCadastros.add(opcaoFornecedores);
		
		JMenu menuComercial = new JMenu("Comercial");
		barraPrincipal.add(menuComercial);
		
		JMenuItem opcaoCompras = new JMenuItem("Compras");
		opcaoCompras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewConsultaCompra view = new ViewConsultaCompra();
				view.setVisible(true);
				
				
				
			}
		});
		menuComercial.add(opcaoCompras);
		
		JMenuItem menuSair = new JMenuItem("Sair");
		menuSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		barraPrincipal.add(menuSair);
		


	}
}
