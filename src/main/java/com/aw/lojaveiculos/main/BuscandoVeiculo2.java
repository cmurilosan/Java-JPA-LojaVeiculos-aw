package com.aw.lojaveiculos.main;

import javax.persistence.EntityManager;

import com.aw.lojaveiculos.dominio.JpaUtil;
import com.aw.lojaveiculos.dominio.Veiculo;

public class BuscandoVeiculo2 {

	public static void main(String[] args) {
		
		EntityManager manager = JpaUtil.getEntityManager();
		
		Veiculo veiculo = manager.getReference(Veiculo.class, 1L);
		
		System.out.println("Veículo de código " + veiculo.getCodigo() + " é um " + veiculo.getModelo());
		
		manager.close();
		JpaUtil.close();
	}

}
