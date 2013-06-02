package br.com.nascisoft.apoioterritoriols.login.entities;

import java.io.Serializable;

import javax.persistence.Id;

import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.NotSaved;
import com.googlecode.objectify.annotation.Unindexed;

@Unindexed
@Cached
public class Surdo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id private Long id;
	@Indexed private Key<Regiao> regiao;
	@Indexed private Key<Cidade> cidade;
	@Indexed private String nome;
	private String logradouro;
	private String numero;
	private String complemento;
	@Indexed private String bairro;
	private String cep;
	private String observacao;
	private String telefone;
	private String libras;
	private String publicacoesPossui;
	private String dvd;
	private String instrutor;
	private Integer anoNascimento;
	private String sexo;
	private String horario;
	private String melhorDia;
	private String onibus;
	@Indexed private Key<Mapa> mapa;
	@Indexed private boolean estaAssociadoMapa = Boolean.FALSE;
	private String msn;
	@Indexed private boolean possuiMSN = Boolean.FALSE;
	@Indexed private Double latitude;
	@Indexed private Double longitude;
	@Indexed private boolean mudouSe = Boolean.FALSE;
	@Indexed private boolean visitarSomentePorAnciaos = Boolean.FALSE;
	@NotSaved private Long mapaAnterior;
	@NotSaved private Long regiaoId;
	@NotSaved private Long cidadeId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Key<Regiao> getRegiao() {
		return regiao;
	}
	public void setRegiao(Key<Regiao> regiao) {
		this.regiao = regiao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getLibras() {
		return libras;
	}
	public void setLibras(String libras) {
		this.libras = libras;
	}
	public String getPublicacoesPossui() {
		return publicacoesPossui;
	}
	public void setPublicacoesPossui(String publicacoesPossui) {
		this.publicacoesPossui = publicacoesPossui;
	}
	public String getDvd() {
		return dvd;
	}
	public void setDvd(String dvd) {
		this.dvd = dvd;
	}
	public String getInstrutor() {
		return instrutor;
	}
	public void setInstrutor(String instrutor) {
		this.instrutor = instrutor;
	}
	public Integer getAnoNascimento() {
		return anoNascimento;
	}
	public void setAnoNascimento(Integer anoNascimento) {
		this.anoNascimento = anoNascimento;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public String getMelhorDia() {
		return melhorDia;
	}
	public void setMelhorDia(String melhorDia) {
		this.melhorDia = melhorDia;
	}
	public String getOnibus() {
		return onibus;
	}
	public void setOnibus(String onibus) {
		this.onibus = onibus;
	}
	public Key<Mapa> getMapa() {
		return mapa;
	}
	public void setMapa(Key<Mapa> mapa) {
		this.mapa = mapa;
		this.setEstaAssociadoMapa(mapa != null);
	}
	public void setEstaAssociadoMapa(boolean estaAssociadoMapa) {
		this.estaAssociadoMapa = estaAssociadoMapa;
	}
	public boolean getEstaAssociadoMapa() {
		return estaAssociadoMapa;
	}
	public void setMsn(String msn) {
		this.msn = msn;
		this.setPossuiMSN(msn != null && msn.length() > 0);
	}
	public String getMsn() {
		return msn;
	}
	public void setPossuiMSN(boolean possuiMSN) {
		this.possuiMSN = possuiMSN;
	}
	public boolean isPossuiMSN() {
		return possuiMSN;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public boolean isMudouSe() {
		return mudouSe;
	}
	public void setMudouSe(boolean mudouSe) {
		this.mudouSe = mudouSe;
	}
	public boolean isVisitarSomentePorAnciaos() {
		return visitarSomentePorAnciaos;
	}
	public void setVisitarSomentePorAnciaos(boolean visitarSomenteAnciaos) {
		this.visitarSomentePorAnciaos = visitarSomenteAnciaos;
	}
	public Long getMapaAnterior() {
		return mapaAnterior;
	}
	public void setMapaAnterior(Long mapaAnterior) {
		this.mapaAnterior = mapaAnterior;
	}
	
	public String getEndereco() {
		StringBuilder retorno = new StringBuilder();
		retorno.append(this.getLogradouro()).append(", ").append(this.getNumero());
		if (!StringUtils.isEmpty(this.getComplemento())) {
			retorno.append(", ").append(this.getComplemento());
		} 
		if (!StringUtils.isEmpty(this.getBairro())) {
			retorno.append(", ").append(this.getBairro());
		}
		if (!StringUtils.isEmpty(this.getCep())) {
			retorno.append(", ").append(this.getCep());
		}
		return retorno.toString();
	}
	public Long getRegiaoId() {
		return regiaoId;
	}
	public void setRegiaoId(Long regiaoId) {
		this.regiaoId = regiaoId;
	}
	public Key<Cidade> getCidade() {
		return cidade;
	}
	public void setCidade(Key<Cidade> cidade) {
		this.cidade = cidade;
	}
	public Long getCidadeId() {
		return cidadeId;
	}
	public void setCidadeId(Long cidadeId) {
		this.cidadeId = cidadeId;
	}
	
}
