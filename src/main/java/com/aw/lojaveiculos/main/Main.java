package com.aw.lojaveiculos.main;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("loja_veiculos");
		
		entityManagerFactory.close();
	}

}
