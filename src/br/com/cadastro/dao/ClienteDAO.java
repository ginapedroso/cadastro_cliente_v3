package br.com.cadastro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import br.com.cadastro.model.BaseCrud;
import br.com.cadastro.model.ConnectionManager;
import br.com.cadastro.pojo.Cliente;

public class ClienteDAO implements BaseCrud<Cliente> {

	
	private String sql_create = "INSERT INTO cliente(nome) values(?)";
	private String sql_delete = "DELETE FROM cliente WHERE id = ?";
	private String sql_update = "UPDATE cliente SET nome=? WHERE id=?";
	private String sql_byId = "SELECT * FROM cliente WHERE id = ?";
	private String sql_criteria = "SELECT * FROM cliente WHERE true";
	
	@Override
	public void create(Cliente pojo) {
		try {
			Connection con = new ConnectionManager().getInstance().getConnection();
			PreparedStatement st = con.prepareStatement(sql_create);
			int i = 0;
			st.setString(++i, pojo.getNome());
			st.execute();
			st.close();
			con.close();
			
			JOptionPane.showMessageDialog(null, "Registro gravado com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "erro ao gravar o registro");
		}
		
	}

	@Override
	public void update(Cliente pojo) {
		try {
			Connection con = new ConnectionManager().getInstance().getConnection();
			PreparedStatement st = con.prepareStatement(sql_update);
			int i = 0;
			st.setString(++i, pojo.getNome());
			st.setLong(++i, pojo.getId());
			st.execute();
			st.close();
			con.close();
			JOptionPane.showMessageDialog(null, "Registro alterado com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "erro ao alterar o registro");
		}
		
	}

	@Override
	public void delete(Long id) {
		try {
			Connection con = new ConnectionManager().getInstance().getConnection();
			PreparedStatement st = con.prepareStatement(sql_delete);
			int i = 0;
			st.setLong(++i, id);
			st.execute();
			st.close();
			con.close();
			JOptionPane.showMessageDialog(null, "Registro excluido com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "erro ao excluir o registro");
		}
		
	}

	@Override
	public Cliente readById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> readByCriteria(Map<String, Object> criteria) {
		List<Cliente> clientes = new ArrayList<Cliente>();
		String sql = sql_criteria;
		if (criteria != null){
		String criteriaNome = (String) criteria.get("nome");
		if (criteriaNome != null && criteriaNome.trim().length() > 0){
			sql += " and nome ilike '%"+criteriaNome+"%'";
		}
		}else{
			sql += " ORDER BY nome";
		}
		try {
			Connection con = new ConnectionManager().getInstance().getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs != null){
				while (rs.next()){
					Cliente cliente = extract(rs);
					clientes.add(cliente);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return clientes;
	}

	private Cliente extract(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setId(rs.getLong("id"));
		cliente.setNome(rs.getString("nome"));
		return cliente;
	}

}
