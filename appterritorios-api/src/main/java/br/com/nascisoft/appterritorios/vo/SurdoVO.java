package br.com.nascisoft.appterritorios.vo;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import br.com.nascisoft.appterritorios.entities.Cidade;
import br.com.nascisoft.appterritorios.entities.Mapa;
import br.com.nascisoft.appterritorios.entities.MelhorDia.Dia;
import br.com.nascisoft.appterritorios.entities.MelhorPeriodo.Periodo;
import br.com.nascisoft.appterritorios.entities.Regiao;
import br.com.nascisoft.appterritorios.entities.Surdo;
import br.com.nascisoft.appterritorios.util.StringUtils;

public class SurdoVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private String regiao;
	private String mapa;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String observacao;
	private String telefone;
	private String libras;
	private String publicacoesPossui;
	private Integer anoNascimento;
	private String dvd;
	private String instrutor;
	private String sexo;
	private List<Periodo> melhoresPeriodos;
	private List<Dia> melhoresDias;
	private String onibus;
	private String msn;
	private Double latitude;	
	private Double longitude;
	private Long regiaoId;
	private String nomeCidade;
	private Byte qtdePessoasEndereco;
	
	public static final Comparator<SurdoVO> COMPARATOR_ENDERECO = new Comparator<SurdoVO>() {
		@Override
		public int compare(SurdoVO o1, SurdoVO o2) {
			return o1.getEndereco().compareTo(o2.getEndereco());
		}
	};
	
	public static final Comparator<SurdoVO> COMPARATOR_LATITUDE = new Comparator<SurdoVO>() {
		@Override
		public int compare(SurdoVO o1, SurdoVO o2) {
			return o1.getLatitude().compareTo(o2.getLatitude())*-1;
		}
	};
	
	public SurdoVO() {
		super();
	}
	
	public SurdoVO(Surdo surdo, Mapa mapa, Regiao regiao, Cidade cidade) { 
		super();
		this.setId(surdo.getId());
		this.setNome(surdo.getNome());
		this.setRegiao(regiao.getNomeRegiaoCompleta());
		this.setRegiaoId(regiao.getId());
		this.setLatitude(surdo.getLatitude());
		this.setLongitude(surdo.getLongitude());
		this.setMapa(mapa != null ? mapa.getNome() : "");
		this.setLogradouro(surdo.getLogradouro());
		this.setNumero(surdo.getNumero());
		this.setComplemento(surdo.getComplemento());
		this.setBairro(surdo.getBairro());
		this.setCep(surdo.getCep());
		this.setObservacao(surdo.getObservacao());
		this.setTelefone(surdo.getTelefone());
		this.setLibras(surdo.getLibras());
		this.setPublicacoesPossui(surdo.getPublicacoesPossui());
		this.setAnoNascimento(surdo.getAnoNascimento());
		this.setDvd(surdo.getDvd());
		this.setInstrutor(surdo.getInstrutor());
		this.setSexo(surdo.getSexo());
		this.setMelhoresPeriodos(surdo.getMelhoresPeriodos());
		this.setMelhoresDia(surdo.getMelhoresDias());
		this.setOnibus(surdo.getOnibus());
		this.setMsn(surdo.getMsn());
		this.setNomeCidade(cidade.getNome());
		this.setQtdePessoasEndereco(surdo.getQtdePessoasEndereco());
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

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public List<Dia> getMelhoresDias() {
		return melhoresDias;
	}

	public void setMelhoresDia(List<Dia> melhoresDias) {
		this.melhoresDias = melhoresDias;
	}

	public String getOnibus() {
		return onibus;
	}

	public void setOnibus(String onibus) {
		this.onibus = onibus;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public String getRegiao() {
		return regiao;
	}

	public String getMapa() {
		return mapa;
	}
	public void setMapa(String mapa) {
		this.mapa = mapa;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLongitude() {
		return longitude;
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
	
	
	public String getPublicacoesPossui() {
		return publicacoesPossui;
	}

	public void setPublicacoesPossui(String publicacoesPossui) {
		this.publicacoesPossui = publicacoesPossui;
	}

	public Integer getAnoNascimento() {
		return anoNascimento;
	}

	public void setAnoNascimento(Integer anoNascimento) {
		this.anoNascimento = anoNascimento;
	}

	public Long getRegiaoId() {
		return regiaoId;
	}

	public void setRegiaoId(Long regiaoId) {
		this.regiaoId = regiaoId;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	public Byte getQtdePessoasEndereco() {
		return qtdePessoasEndereco;
	}

	public void setQtdePessoasEndereco(Byte qtdePessoasEndereco) {
		this.qtdePessoasEndereco = qtdePessoasEndereco;
	}
	
	public List<Periodo> getMelhoresPeriodos() {
		return melhoresPeriodos;
	}

	public void setMelhoresPeriodos(List<Periodo> melhoresPeriodos) {
		this.melhoresPeriodos = melhoresPeriodos;
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
