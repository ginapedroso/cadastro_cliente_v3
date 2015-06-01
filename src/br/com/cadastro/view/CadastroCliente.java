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

public class CadastroCliente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNome;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CadastroCliente dialog = new CadastroCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CadastroCliente() {
		setTitle("Cadastrar Cliente");
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
						
						if (txtNome.getText() != null && txtNome.getText().trim().length() > 0){
							
							Cliente pojo = new Cliente();
							pojo.setNome(txtNome.getText().toString());
							
							Map<String,Object> criteria = new HashMap<String, Object>();
							criteria.put("nome", txtNome.getText().toString());
							List<Cliente> clientes = ServiceLocator.getClienteService().readByCriteria(criteria );
							if (clientes.size() == 0){
								ServiceLocator.getClienteService().create(pojo);
							}else{
								pojo.setId(clientes.get(0).getId());
								ServiceLocator.getClienteService().update(pojo);
							}
							refresh();
							
						}else{
							JOptionPane.showMessageDialog(null, "Registro incompleto");
						}
					}
				});
				btnWrite.setActionCommand("OK");
				buttonPane.add(btnWrite);
				getRootPane().setDefaultButton(btnWrite);
			}
		}
	}

	public void refresh(){
		txtNome.setText(null);
	}
}
