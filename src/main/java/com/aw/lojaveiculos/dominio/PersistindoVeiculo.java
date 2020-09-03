package com.aw.lojaveiculos.dominio;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.aw.lojaveiculos.main.JpaUtil;

public class PersistindoVeiculo {

	public static void main(String[] args) {
		
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		
		Veiculo veiculo = new Veiculo();
		veiculo.setFabricante("Honda");
		veiculo.setModelo("Civic");
		veiculo.setAnoFabricacao(2020);
		veiculo.setAnoModelo(2020);
		veiculo.setValor(new BigDecimal(90500));
		
		manager.persist(veiculo);
		
		tx.commit();
		manager.close();
		JpaUtil.close();

	}

}
