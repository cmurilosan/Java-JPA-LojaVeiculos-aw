package com.aw.lojaveiculos.dominio;

import java.math.BigDecimal;

public class Veiculo {
	
	private Long codigo;
	private String fabricante;	
	private String modelo;
	private Integer anoFabricante;
	private Integer anoModelo;
	private BigDecimal valor;
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public Integer getAnoFabricante() {
		return anoFabricante;
	}
	public void setAnoFabricante(Integer anoFabricante) {
		this.anoFabricante = anoFabricante;
	}
	public Integer getAnoModelo() {
		return anoModelo;
	}
	public void setAnoModelo(Integer anoModelo) {
		this.anoModelo = anoModelo;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}	

}
