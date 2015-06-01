package br.com.cadastro.model;

import br.com.cadastro.service.ClienteService;

public class ServiceLocator {

	public static ClienteService getClienteService(){
		return new ClienteService();
	}
}
