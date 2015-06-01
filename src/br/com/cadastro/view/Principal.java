package br.com.cadastro.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JScrollPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import br.com.cadastro.model.ConnectionManager;
import br.com.cadastro.model.ServiceLocator;
import br.com.cadastro.pojo.Cliente;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JPanel dataPanel;

	private JTable tabela;
	private DefaultTableModel modelo = new DefaultTableModel();
	private JScrollPane scrollPane;

	private final int COLUNA_ID = 0;
	private final int COLUNA_NOME = 1;

	private final String fileName = "report/lista.jasper";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setTitle("CLIENTES");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 524, 357);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		dataPanel = new JPanel();
		contentPane.add(dataPanel, BorderLayout.CENTER);

		criarTabela();
		scrollPane = new JScrollPane(tabela);
		dataPanel.add(scrollPane);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		JButton btnNew = new JButton("Novo");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CadastroCliente tela = new CadastroCliente();
				tela.setModal(true);
				tela.setLocationRelativeTo(null);
				tela.setVisible(true);
				criarTabela();
			}
		});
		buttonPanel.add(btnNew);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int linha = tabela.getSelectedRow();
				if (linha >= 0) {
					Long id = (Long) tabela.getValueAt(linha, COLUNA_ID);
					ServiceLocator.getClienteService().delete(id);
					criarTabela();
				} else {
					JOptionPane
							.showMessageDialog(null, "linha nao selecionada");
				}
			}
		});
		buttonPanel.add(btnDelete);

		JButton btnUpdate = new JButton("Alterar");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int linha = tabela.getSelectedRow();
				if (linha >= 0) {

					Cliente cliente = new Cliente();
					cliente.setId((Long) tabela.getValueAt(linha, COLUNA_ID));
					cliente.setNome((String) tabela.getValueAt(linha,
							COLUNA_NOME));

					AlterarCliente tela = new AlterarCliente(cliente);
					tela.setModal(true);
					tela.setLocationRelativeTo(null);
					tela.setVisible(true);
					// atualizaTabela();
					criarTabela();

				} else {
					JOptionPane
							.showMessageDialog(null, "linha nao selecionada");
				}
			}
		});
		buttonPanel.add(btnUpdate);

		JButton btnPrint = new JButton("Imprimir");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JasperPrint jp;
				try {
					jp = JasperFillManager.fillReport(fileName, null,
							ConnectionManager.getInstance().getConnection());
					JasperViewer view = new JasperViewer(jp, false);
					view.show();
				} catch (JRException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		buttonPanel.add(btnPrint);
		this.setLocationRelativeTo(null);
	}

	// ------------------ metodos p/ tabela

	private void criarTabela() {
		if (tabela == null) {
			tabela = new JTable(modelo);
			modelo.addColumn("id");
			modelo.addColumn("nome");
		}
		getLista();
	}

	private void getLista() {
		List<Cliente> clientes = ServiceLocator.getClienteService()
				.readByCriteria(null);
		modelo.setNumRows(0);
		for (Cliente c : clientes) {
			modelo.addRow(new Object[] { c.getId(), c.getNome() });
		}
	}

}
