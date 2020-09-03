package com.aw.lojaveiculos.main;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.aw.lojaveiculos.dominio.JpaUtil;
import com.aw.lojaveiculos.dominio.Veiculo;

public class ListandoVeiculos {

	public static void main(String[] args) {
		
		EntityManager manager = JpaUtil.getEntityManager();
		
		Query query = manager.createQuery("select v from Veiculo v");
		List<Veiculo> veiculos = query.getResultList();
		for (Veiculo veiculo : veiculos) {
			System.out.println(veiculo.getCodigo() + " - "
					+ veiculo.getFabricante() + " "
					+ veiculo.getModelo() + ", ano "
					+ veiculo.getAnoFabricacao() + "/"
					+ veiculo.getAnoModelo() + " por "
					+ "R$" + veiculo.getFabricante());
		}
		manager.close();
		JpaUtil.close();
	}

}
