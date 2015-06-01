package br.com.cadastro.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectionManager {
	
	private String user = "";
	private  String password = "";
	private  String url = "";
	private String driver = "";
	
	private Connection con = null;
	private static ConnectionManager instance;
	
	
	public static ConnectionManager getInstance(){
		if (instance == null){
			instance = new ConnectionManager();
		}
		return instance;
	}
	
	public ConnectionManager() {
		this.user = "postgres";
		this.password = "gina";
		this.url = "jdbc:postgresql://localhost:5432/BD_CLIENTE";
		this.driver = "org.postgresql.Driver";
	}
	
	public Connection getConnection() {
		try {
			if (con == null || con.isClosed()){
				this.createConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "erro na conexao do banco");
		}
		return con;
	}

	private void createConnection() throws Exception {
		Class.forName(driver);
		con = DriverManager.getConnection(url, user, password);
	}
}
