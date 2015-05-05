package br.com.nascisoft.apoioterritoriols.admin.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.googlecode.objectify.Key;

import br.com.nascisoft.apoioterritoriols.admin.server.dao.AdminDAO;
import br.com.nascisoft.apoioterritoriols.admin.xml.BackupType;
import br.com.nascisoft.apoioterritoriols.admin.xml.BairroType;
import br.com.nascisoft.apoioterritoriols.admin.xml.BairrosType;
import br.com.nascisoft.apoioterritoriols.admin.xml.CidadeType;
import br.com.nascisoft.apoioterritoriols.admin.xml.CidadesType;
import br.com.nascisoft.apoioterritoriols.admin.xml.MapaType;
import br.com.nascisoft.apoioterritoriols.admin.xml.MapasType;
import br.com.nascisoft.apoioterritoriols.admin.xml.ObjectFactory;
import br.com.nascisoft.apoioterritoriols.admin.xml.RegiaoType;
import br.com.nascisoft.apoioterritoriols.admin.xml.RegioesType;
import br.com.nascisoft.apoioterritoriols.admin.xml.SurdoType;
import br.com.nascisoft.apoioterritoriols.admin.xml.SurdosType;
import br.com.nascisoft.apoioterritoriols.admin.xml.UsuarioType;
import br.com.nascisoft.apoioterritoriols.admin.xml.UsuariosType;
import br.com.nascisoft.apoioterritoriols.cadastro.server.dao.CadastroDAO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Bairro;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;

public class BackupServlet extends AbstractApoioTerritorioLSHttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger
		.getLogger(BackupServlet.class.getName());

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
        try {
			String destinatarios = req.getParameter("destinatarios");
			
			String remetente = req.getParameter("remetente");
			
			String tipo = req.getParameter("tipo");
			
			ByteArrayOutputStream zipOut = null;
			
			if (!StringUtils.isEmpty(destinatarios)) {
			
				if ("enderecos".equals(tipo)) {
					zipOut = prepararBackupEnderecos();
				} else {
					zipOut = prepararBackupCompleto();
				}
	
				DataSource ds = new ByteArrayDataSource(zipOut.toByteArray(), "aplication/zip");
										
				Properties props = new Properties();
		        Session session = Session.getDefaultInstance(props, null);
	
	
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(remetente));
				msg.addRecipient(Message.RecipientType.TO,
				                 new InternetAddress(destinatarios));
				msg.setSubject("enderecos".equals(tipo) 
						? "Export de enderecos cadastrados no ApoioTerritorioLS"
								: "Backup de mapas e enderecos do ApoioTerritorioLS");
				msg.setText("Segue em anexo o backup/export.\n\n\nNote que por limitação de segurança do google o anexo é um arquivo .zipe. " +
						"Por favor renomeie o arquivo para .zip e depois o abra normalmente.");
				Multipart mp = new MimeMultipart();						
				 
				MimeBodyPart htmlPart = new MimeBodyPart();
			    htmlPart.setContent("Segue em anexo o backup/export.<br/><br/><br/>Note que por limitação de segurança do google o anexo é um arquivo .zipe. " +
						"Por favor renomeie o arquivo para .zip e depois o abra normalmente.", "text/html");
			    mp.addBodyPart(htmlPart);
			    
				MimeBodyPart attachment = new MimeBodyPart();
				attachment.setFileName("enderecos".equals(tipo)?"export.zipe":"backup.zipe");
				attachment.setDataHandler(new DataHandler(ds));
				mp.addBodyPart(attachment);
				
				msg.setContent(mp);
				
				Transport.send(msg);
			}
        } catch (Exception e) {
			logger.log(Level.SEVERE, "Erro ao preparar backup ou ao enviar e-mail de backup/export. Não relançando a exception para a mensagem não voltar para a fila.", e);
		}
	}
	
	private ByteArrayOutputStream prepararBackupEnderecos() throws IOException {
		AdminDAO dao = new AdminDAO();
		CadastroDAO cadastroDAO = new CadastroDAO();
		
		List<Surdo> surdos = dao.obterSurdos();
		
		Set<Key<Mapa>> chavesMapa = new HashSet<Key<Mapa>>();
		Set<Key<Regiao>> chavesRegiao = new HashSet<Key<Regiao>>();
		Set<Key<Cidade>> chavesCidade = new HashSet<Key<Cidade>>();

		for (Surdo surdo : surdos) {
			if (surdo.getMapa() != null) {
				chavesMapa.add(surdo.getMapa());
			}
				chavesRegiao.add(surdo.getRegiao());
				chavesCidade.add(surdo.getCidade());
		}
		
		Map<Key<Mapa>, Mapa> mapas = cadastroDAO.obterMapas(chavesMapa);
		Map<Key<Regiao>, Regiao> mapasRegiao = dao.obterRegioes(chavesRegiao);
		Map<Key<Cidade>, Cidade> mapasCidade = dao.obterCidades(chavesCidade);
		
		List<SurdoVO> surdosVO = new ArrayList<SurdoVO>();
		for (Surdo surdo : surdos) {
			surdosVO.add(new SurdoVO(
					surdo, 
					mapas.get(surdo.getMapa()), 
					mapasRegiao.get(surdo.getRegiao()),
					mapasCidade.get(surdo.getCidade())));
		}
		
		StringBuilder csv = new StringBuilder();
		csv.append("cidade;regiao;mapa;nome;logradouro;numero;complemento;bairro;cep;observacao;telefone;libras;")
			.append("publicacoesPossui;anoNascimento;dvd;instrutor;tipo;horario;melhorDia;onibus;msn;latitude;longitude;qtdePessoasEndereco\n"); 
		for (SurdoVO surdo : surdosVO) {
			csv.append("\"").append(surdo.getNomeCidade()).append("\";")
				.append("\"").append(surdo.getRegiao()).append("\";")
				.append("\"").append(surdo.getMapa()).append("\";")
				.append("\"").append(surdo.getNome()).append("\";")
				.append("\"").append(surdo.getLogradouro()).append("\";")
				.append("\"").append(surdo.getNumero()).append("\";")
				.append("\"").append(surdo.getComplemento()).append("\";")
				.append("\"").append(surdo.getBairro()).append("\";")
				.append("\"").append(surdo.getCep()).append("\";")
				.append("\"").append(surdo.getObservacao()).append("\";")
				.append("\"").append(surdo.getTelefone()).append("\";")
				.append("\"").append(surdo.getLibras()).append("\";")
				.append("\"").append(surdo.getPublicacoesPossui()).append("\";")
				.append("\"").append(surdo.getAnoNascimento()).append("\";")
				.append("\"").append(surdo.getDvd()).append("\";")
				.append("\"").append(surdo.getInstrutor()).append("\";")
				.append("\"").append(surdo.getSexo()).append("\";")
				.append("\"").append(surdo.getHorario()).append("\";")
				.append("\"").append(surdo.getMelhorDia()).append("\";")
				.append("\"").append(surdo.getOnibus()).append("\";")
				.append("\"").append(surdo.getMsn()).append("\";")
				.append("\"").append(surdo.getLatitude()).append("\";")
				.append("\"").append(surdo.getLongitude()).append("\";")
				.append("\"").append(surdo.getQtdePessoasEndereco()).append("\n");
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(csv.toString().getBytes("ISO-8859-1"));
		
		ByteArrayOutputStream zipOut = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(zipOut);
		ZipEntry entry = new ZipEntry("enderecos.csv");
		zip.putNextEntry(entry);
		zip.write(out.toByteArray());
		zip.close();
		
		return zipOut;		
	}

	private ByteArrayOutputStream prepararBackupCompleto() throws JAXBException, IOException {
		AdminDAO dao = new AdminDAO();
		
		BackupType backup = new BackupType();
		
		List<Usuario> usuarios = dao.obterUsuarios();
		if (usuarios.size() > 0) {
			backup.setUsuarios(new UsuariosType());
			for (Usuario user : usuarios) {
				backup.getUsuarios().getUsuario().add(obterUsuarioXML(user));
			}
		}
		
		backup.setCidades(new CidadesType());
		List<Cidade> cidades = dao.obterCidades();
		for (Cidade cidade : cidades) {
			backup.getCidades().getCidade().add(obterCidadeXML(cidade));
		}
		
		backup.setRegioes(new RegioesType());
		List<Regiao> regioes = dao.obterRegioes();
		for (Regiao regiao : regioes) {
			backup.getRegioes().getRegiao().add(obterRegiaoXML(regiao));
		}
		
		List<Bairro> bairros = dao.obterBairros();
		if (bairros.size() > 0) {
			backup.setBairros(new BairrosType());
			for (Bairro bairro : bairros) {
				backup.getBairros().getBairro().add(obterBairroXML(bairro));
			}
		}
		
		List<Mapa> mapas  = dao.obterMapas();
		if (mapas.size() > 0) {
			backup.setMapas(new MapasType());
			for (Mapa mapa : mapas) {
				backup.getMapas().getMapa().add(obterMapaXML(mapa));
			}
		}
		
		List<Surdo> surdos = dao.obterSurdos();
		if (surdos.size() > 0) {
			backup.setSurdos(new SurdosType());
			for (Surdo surdo : surdos) {
				backup.getSurdos().getSurdo().add(obterSurdoXML(surdo));
			}
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JAXBContext context = JAXBContext.newInstance(BackupType.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.marshal((new ObjectFactory()).createBackup(backup), out);
		
		ByteArrayOutputStream zipOut = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(zipOut);
		ZipEntry entry = new ZipEntry("backup.xml");
		zip.putNextEntry(entry);
		zip.write(out.toByteArray());
		zip.close();
		
		return zipOut;
	}
	
	private UsuarioType obterUsuarioXML(Usuario user) {
		UsuarioType xml = new UsuarioType();
		
		xml.setEmail(user.getEmail());
		xml.setAdmin(user.getAdmin());
		
		return xml;
	}
	
	private CidadeType obterCidadeXML(Cidade cidade) {
		CidadeType xml = new CidadeType();
		
		xml.setIdentificador(cidade.getId());
		xml.setNome(cidade.getNome());
		xml.setLatitudeCentro(cidade.getLatitudeCentro());
		xml.setLongitudeCentro(cidade.getLongitudeCentro());
		xml.setLatitudeCentroTerritorio(cidade.getLatitudeCentroTerritorio());
		xml.setLongitudeCentroTerritorio(cidade.getLongitudeCentroTerritorio());
		xml.setQuantidadeSurdosMapa(cidade.getQuantidadeSurdosMapa());
		xml.setUF(cidade.getUF());
		xml.setPais(cidade.getPais());
		xml.setUtilizarBairroBuscaEndereco(cidade.getUtilizarBairroBuscaEndereco());
		
		return xml;
	}
	
	private RegiaoType obterRegiaoXML(Regiao regiao) {
		RegiaoType xml = new RegiaoType();

		xml.setIdentificador(regiao.getId());
		xml.setIdentificadorCidade(regiao.getCidade().getId());
		xml.setNome(regiao.getNome());
		xml.setLetra(regiao.getLetra());
		xml.setLatitudeCentro(regiao.getLatitudeCentro());
		xml.setLongitudeCentro(regiao.getLongitudeCentro());
		xml.setNivelZoom(regiao.getZoom());
		xml.setCorLetra(regiao.getCorLetra());
		xml.setCorFundo(regiao.getCorFundo());
		
		return xml;
	}
	
	private BairroType obterBairroXML(Bairro bairro) {
		BairroType xml = new BairroType();
		
		xml.setIdentificador(bairro.getId());
		xml.setIdentificadorCidade(bairro.getCidade().getId());
		xml.setNome(bairro.getNome());
		
		return xml;
	}
	
	private MapaType obterMapaXML(Mapa mapa) {
		MapaType xml = new MapaType();
		
		xml.setIdentificador(mapa.getId());
		xml.setLetra(mapa.getLetra());
		xml.setNumero(mapa.getNumero());
		xml.setIdentificadorRegiao(mapa.getRegiao().getId());
		
		return xml;
	}
	
	private SurdoType obterSurdoXML(Surdo surdo) {
		SurdoType xml = new SurdoType();
		
		xml.setBairro(surdo.getBairro());
		xml.setCep(surdo.getCep());
		xml.setComplemento(surdo.getComplemento());
		xml.setPublicacoesPossui(surdo.getPublicacoesPossui());
		xml.setDvd(surdo.getDvd());
		xml.setEstaAssociadoMapa(surdo.getEstaAssociadoMapa());
		xml.setHorario(surdo.getHorario());
		xml.setId(surdo.getId());
		xml.setAnoNascimento(surdo.getAnoNascimento());
		xml.setInstrutor(surdo.getInstrutor());
		xml.setLatitude(surdo.getLatitude());
		xml.setLibras(surdo.getLibras());
		xml.setLogradouro(surdo.getLogradouro());
		xml.setLongitude(surdo.getLongitude());
		if (surdo.getMapa() != null) {
			xml.setIdentificadorMapa(surdo.getMapa().getId());
		}
		xml.setMelhorDia(surdo.getMelhorDia());
		xml.setMsn(surdo.getMsn());
		xml.setNome(surdo.getNome());
		xml.setNumero(surdo.getNumero());
		xml.setObservacao(surdo.getObservacao());
		xml.setOnibus(surdo.getOnibus());
		xml.setPossuiMSN(surdo.isPossuiMSN());
		xml.setIdentificadorRegiao(surdo.getRegiao().getId());
		xml.setIdentificadorCidade(surdo.getCidade().getId());
		xml.setSexo(surdo.getSexo());
		xml.setTelefone(surdo.getTelefone());
		xml.setMudouSe(surdo.isMudouSe());
		xml.setVisitarSomentePorAnciaos(surdo.isVisitarSomentePorAnciaos());
		xml.setQtdePessoasEndereco(surdo.getQtdePessoasEndereco());
		
		return xml;		
	}

}
