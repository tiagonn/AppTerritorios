package br.com.nascisoft.appterritorios.vo;

import java.io.Serializable;

public class LoginVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private String identificador;
	private String email;
	private String nickname;
	private String loginURL;
	private String logoutURL;
	private Boolean logado = false;
	private Boolean autorizado = false;
	private Boolean admin = false;
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLoginURL() {
		return loginURL;
	}
	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}
	public String getLogoutURL() {
		return logoutURL;
	}
	public void setLogoutURL(String logoutURL) {
		this.logoutURL = logoutURL;
	}
	public Boolean isLogado() {
		return logado;
	}
	public void setLogado(Boolean logado) {
		this.logado = logado;
	}
	public void setAutorizado(Boolean autorizado) {
		this.autorizado = autorizado;
	}
	public Boolean isAutorizado() {
		return autorizado;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	public Boolean isAdmin() {
		return admin;
	}

}
