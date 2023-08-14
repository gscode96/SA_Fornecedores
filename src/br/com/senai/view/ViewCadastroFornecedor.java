package br.com.senai.view;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.com.senai.core.domain.Fornecedor;
import br.com.senai.core.service.FornecedorService;

public class ViewCadastroFornecedor extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField edtNomeFantasia;
	private JTextField edtRazaoSocial;
	private JFormattedTextField ftfCnpj;
	
	private FornecedorService service;
	private Fornecedor fornecedor;

	/**
	 * Create the frame.
	 */
	public ViewCadastroFornecedor() {
		this.service = new FornecedorService();		
		setResizable(false);
		setTitle("Gerenciar Fornecedor - Cadastro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewConsultaFornecedor view = new ViewConsultaFornecedor();
				view.setVisible(true);
				dispose();
			}
		});
		btnConsultar.setForeground(Color.WHITE);
		btnConsultar.setBackground(SystemColor.textHighlight);
		btnConsultar.setBounds(335, 11, 89, 23);
		contentPane.add(btnConsultar);
		
		JLabel lblNomeFantasia = new JLabel("Nome Fantasia");
		lblNomeFantasia.setBounds(10, 45, 89, 16);
		contentPane.add(lblNomeFantasia);
		
		JLabel lblRazaoSocial = new JLabel("Raz\u00E3o Social");
		lblRazaoSocial.setBounds(234, 45, 89, 16);
		contentPane.add(lblRazaoSocial);
		
		edtNomeFantasia = new JTextField();
		edtNomeFantasia.setBounds(10, 72, 214, 20);
		contentPane.add(edtNomeFantasia);
		edtNomeFantasia.setColumns(10);
		
		edtRazaoSocial = new JTextField();
		edtRazaoSocial.setColumns(10);
		edtRazaoSocial.setBounds(234, 72, 190, 20);
		contentPane.add(edtRazaoSocial);
		
		JLabel lblCnpj = new JLabel("CNPJ");
		lblCnpj.setBounds(10, 103, 55, 16);
		contentPane.add(lblCnpj);
		
		ftfCnpj = new JFormattedTextField();
		ftfCnpj.setBounds(10, 131, 118, 20);
		contentPane.add(ftfCnpj);
		try {
			MaskFormatter mascara = new MaskFormatter("##.###.###/####-##");
			mascara.install(ftfCnpj);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String razaoSocial = edtRazaoSocial.getText();
					String nomeFantasia = edtNomeFantasia.getText();
					String cnpj = ftfCnpj.getText();							
					
					if (fornecedor == null) {
						fornecedor = new Fornecedor(razaoSocial, nomeFantasia, cnpj);
					}else {			
						fornecedor.setRazaoSocial(razaoSocial);
						fornecedor.setNomeFantasia(nomeFantasia);
						fornecedor.setCnpj(cnpj);
					}					
					service.salvar(fornecedor);
					JOptionPane.showMessageDialog(contentPane, "Fornecedor salvo com sucesso");
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}				
			}
		});
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setBackground(SystemColor.textHighlight);
		btnSalvar.setBounds(335, 167, 89, 23);
		contentPane.add(btnSalvar);
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
		this.edtNomeFantasia.setText(fornecedor.getNomeFantasia());
		this.edtRazaoSocial.setText(fornecedor.getRazaoSocial());
		this.ftfCnpj.setText(fornecedor.getCnpj());	
	}
}
