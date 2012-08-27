package br.com.nascisoft.apoioterritoriols.cadastro.vo;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.cadastro.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;

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
	private String horario;
	private String melhorDia;
	private String onibus;
	private String msn;
	private Double latitude;	
	private Double longitude;
	
	public static final Comparator<SurdoVO> COMPARATOR_ENDERECO = new Comparator<SurdoVO>() {
		@Override
		public int compare(SurdoVO o1, SurdoVO o2) {
			return o1.getEndereco().compareTo(o2.getEndereco());
		}
	};
	
	public SurdoVO() {
		super();
	}
	
	public SurdoVO(Surdo surdo, Mapa mapa) { 
		super();
		this.setId(surdo.getId());
		this.setNome(surdo.getNome());
		this.setRegiao(surdo.getRegiao());
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
		this.setHorario(surdo.getHorario());
		this.setMelhorDia(surdo.getMelhorDia());
		this.setOnibus(surdo.getOnibus());
		this.setMsn(surdo.getMsn());
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

	public String getIdade() {
		String retorno = null;
		if (anoNascimento != null) {			
			retorno = String.valueOf(Integer.valueOf(
					DateTimeFormat.getFormat("yyyy").format(new Date())) - this.anoNascimento);
		}
		return retorno;
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
	
	public String getObservacaoConsolidada() {
		StringBuilder retorno = new StringBuilder();
		
		if (!StringUtils.isEmpty(this.getSexo())) {
			retorno.append("<u>").append(this.getSexo()).append("</u>").append("; ");
		}
		if (!StringUtils.isEmpty(this.getIdade())) {
			retorno.append("Possui aproximadamente ").append("<u>").append(this.getIdade()).append(" anos;</u> ");
		}
		if (!StringUtils.isEmpty(this.getLibras())) {
			if ("Sim".equals(this.getLibras())) {
				retorno.append("<u>").append("Sabe</u> LIBRAS; ");
			} else {
				retorno.append("<u>").append("Não sabe</u> LIBRAS; ");
			}
		}
		if (!StringUtils.isEmpty(this.getDvd())) {
			if ("Sim".equals(this.getDvd())) {
				retorno.append("<u>").append("Possui</u> DVD; ");
			} else {
				retorno.append("<u>").append("Não possui</u> DVD; ");
			}
		}
		if (!StringUtils.isEmpty(this.getPublicacoesPossui())) {
			retorno.append("Possui a(s) seguinte(s) publicações: ").append("<u>").append(this.getPublicacoesPossui()).append("</u>").append("; ");
		}
		if (!StringUtils.isEmpty(this.getMelhorDia())) {
			retorno.append("Melhor(es) dia(s) para ser contactado(a): ").append("<u>").append(this.getMelhorDia()).append("</u>").append("; ");
		}
		if (!StringUtils.isEmpty(this.getHorario())) {
			retorno.append("Melhor horário para ser contactado(a): ").append("<u>").append(this.getHorario()).append("</u>").append("; ");
		}
		if (!StringUtils.isEmpty(this.getOnibus())) {
			retorno.append("Ônibus que pode(m) ser usado(s): ").append("<u>").append(this.getOnibus()).append("</u>").append("; ");
		}
		if (!StringUtils.isEmpty(this.getMsn())) {
			retorno.append("E-mail/MSN: ").append("<u>").append(this.getMsn()).append("</u>").append("; ");
		}
		if (!StringUtils.isEmpty(this.getObservacao())) {
			retorno.append("</br><strong>Demais observações: </strong>").append(this.getObservacao());
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

}
