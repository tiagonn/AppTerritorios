package br.com.nascisoft.apoioterritoriols.login.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.MelhorDia.Dia;
import br.com.nascisoft.apoioterritoriols.login.entities.MelhorPeriodo.Periodo;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
public class Surdo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id private Long id;
	@Index private Key<Regiao> regiao;
	@Index private Key<Cidade> cidade;
	@Index private String nome;
	private String logradouro;
	private String numero;
	private String complemento;
	@Index private String bairro;
	private String cep;
	private String observacao;
	private String telefone;
	private String libras;
	private String publicacoesPossui;
	private String dvd;
	private String instrutor;
	private Integer anoNascimento;
	private String sexo;
	private MelhorPeriodo melhorPeriodo;
	private MelhorDia melhorDiaSemana;
	private String onibus;
	@Index private Key<Mapa> mapa;
	@Index private boolean estaAssociadoMapa = Boolean.FALSE;
	private String msn;
	@Index private boolean possuiMSN = Boolean.FALSE;
	@Index private Double latitude;
	@Index private Double longitude;
	@Index private boolean mudouSe = Boolean.FALSE;
	@Index private boolean visitarSomentePorAnciaos = Boolean.FALSE;
	@IgnoreSave private Long mapaAnterior;
	@IgnoreSave private Long regiaoId;
	@IgnoreSave private Long cidadeId;
	private Byte qtdePessoasEndereco;
	
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
	public MelhorDia getMelhorDia() {
		return melhorDiaSemana;
	}
	public void setMelhorDia(MelhorDia melhorDia) {
		this.melhorDiaSemana = melhorDia;
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
	public Byte getQtdePessoasEndereco() {
		if (this.qtdePessoasEndereco==null||this.qtdePessoasEndereco.equals(0)) {
			this.setQtdePessoasEndereco(null);
		}
		return qtdePessoasEndereco;
	}
	public void setQtdePessoasEndereco(Byte qtdePessoasEndereco) {
		this.qtdePessoasEndereco = qtdePessoasEndereco;		
		if (this.qtdePessoasEndereco == null) {
			switch (this.sexo) {
			case "Homem":
				this.qtdePessoasEndereco = 1;
				break;
			case "Mulher":
				this.qtdePessoasEndereco = 1;
				break;
			case "Casal":
				this.qtdePessoasEndereco = 2;
				break;
			default:
				this.qtdePessoasEndereco = 1;
				break;	
			}
		}
	}
	public MelhorPeriodo getMelhorPeriodo() {
		return melhorPeriodo;
	}
	public void setMelhorPeriodo(MelhorPeriodo melhorPeriodo) {
		this.melhorPeriodo = melhorPeriodo;
	}
	
	public List<Periodo> getMelhoresPeriodos() {
		List<Periodo> retorno = new ArrayList<MelhorPeriodo.Periodo>();
		if (this.melhorPeriodo == null) {
			this.melhorPeriodo = new MelhorPeriodo();
		}
		if (this.melhorPeriodo.getManha()) {
			retorno.add(Periodo.manha);
		}
		if (this.melhorPeriodo.getTarde()) {
			retorno.add(Periodo.tarde);
		}
		if (this.melhorPeriodo.getNoite()) {
			retorno.add(Periodo.noite);
		}
		return retorno;
	}
	
	public List<Dia> getMelhoresDias() {
		List<Dia> retorno = new ArrayList<MelhorDia.Dia>();
		if (this.melhorDiaSemana == null) {
			this.melhorDiaSemana = new MelhorDia();
		}
		if (this.melhorDiaSemana.getSegunda()) {
			retorno.add(Dia.segunda);
		}
		if (this.melhorDiaSemana.getTerca()) {
			retorno.add(Dia.terca);
		}
		if (this.melhorDiaSemana.getQuarta()) {
			retorno.add(Dia.quarta);
		}
		if (this.melhorDiaSemana.getQuinta()) {
			retorno.add(Dia.quinta);
		}
		if (this.melhorDiaSemana.getSexta()) {
			retorno.add(Dia.sexta);
		}
		if (this.melhorDiaSemana.getSabado()) {
			retorno.add(Dia.sabado);
		}
		if (this.melhorDiaSemana.getDomingo()) {
			retorno.add(Dia.domingo);
		}
		return retorno;
	}
	
	public String getMelhoresPeriodosCsv() {
		StringBuilder retorno = new StringBuilder();
		Iterator<Periodo> iter = this.getMelhoresPeriodos().iterator();
		while (iter.hasNext()) {
			retorno.append(iter.next().getNome());
			if (iter.hasNext()) {
				retorno.append(",");
			}
		}
		return retorno.toString();
	}
	
	public String getMelhoresDiasCsv(Boolean isAbreviado) {
		StringBuilder retorno = new StringBuilder();
		Iterator<Dia> iter = this.getMelhoresDias().iterator();
		while (iter.hasNext()) {
			retorno.append(isAbreviado ? iter.next().getNomeAbreviado() : iter.next().getNome());
			if (iter.hasNext()) {
				retorno.append(",");
			}
		}
		return retorno.toString();
	}
	
}
