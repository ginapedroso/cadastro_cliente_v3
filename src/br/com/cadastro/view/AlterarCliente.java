package br.com.cadastro.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import br.com.cadastro.model.ServiceLocator;
import br.com.cadastro.pojo.Cliente;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

public class AlterarCliente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNome;
	private Long id = null;
	private String nome = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AlterarCliente dialog = new AlterarCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AlterarCliente(Cliente pojo) {
		this.id = pojo.getId();
		this.nome = pojo.getNome();
		inicializar();
	}

	public AlterarCliente() {
		inicializar();
	}

	/**
	 * Create the dialog.
	 */
	// public AlterarCliente() {

	public void inicializar() {
		setTitle("Alterar Cliente");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel dataPanel = new JPanel();
			contentPanel.add(dataPanel);
			dataPanel.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblNome = new JLabel("Nome:");
				dataPanel.add(lblNome, BorderLayout.WEST);
			}
			{
				txtNome = new JTextField();
				txtNome.setText(nome);
				dataPanel.add(txtNome, BorderLayout.CENTER);
				txtNome.setColumns(10);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.LIGHT_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnWrite = new JButton("Gravar");
				btnWrite.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						if (txtNome.getText() != null
								&& txtNome.getText().trim().length() > 0 && id > 0) {

							Cliente pojo = new Cliente();
							pojo.setNome(txtNome.getText().toString());
							pojo.setId(id);
							ServiceLocator.getClienteService().update(pojo);

						} else {
							JOptionPane.showMessageDialog(null,
									"Registro incompleto");
						}
					}
				});
				btnWrite.setActionCommand("OK");
				buttonPane.add(btnWrite);
				getRootPane().setDefaultButton(btnWrite);
			}
		}
	}

}
