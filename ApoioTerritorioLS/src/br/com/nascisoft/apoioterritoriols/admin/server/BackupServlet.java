package br.com.nascisoft.apoioterritoriols.admin.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
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

import br.com.nascisoft.apoioterritoriols.admin.server.dao.AdminDAO;
import br.com.nascisoft.apoioterritoriols.admin.xml.BackupType;
import br.com.nascisoft.apoioterritoriols.admin.xml.MapaType;
import br.com.nascisoft.apoioterritoriols.admin.xml.MapasType;
import br.com.nascisoft.apoioterritoriols.admin.xml.SurdoType;
import br.com.nascisoft.apoioterritoriols.admin.xml.SurdosType;
import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.cadastro.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;

public class BackupServlet extends AbstractApoioTerritorioLSHttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger
		.getLogger(BackupServlet.class.getName());

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String destinatarios = req.getParameter("destinatarios");
		
		if (!StringUtils.isEmpty(destinatarios)) {
			AdminDAO dao = new AdminDAO();
			
			BackupType backup = new BackupType();
			backup.setMapas(new MapasType());
			backup.setSurdos(new SurdosType());
			
			List<Mapa> mapas  = dao.obterMapas();
			for (Mapa mapa : mapas) {
				backup.getMapas().getMapa().add(obterMapaXML(mapa));
			}
			
			List<Surdo> surdos = dao.obterSurdos();
			for (Surdo surdo : surdos) {
				backup.getSurdos().getSurdo().add(obterSurdoXML(surdo));
			}
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				JAXBContext context = JAXBContext.newInstance(BackupType.class);
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
				marshaller.marshal(backup, out);
			} catch (JAXBException e) {
				logger.log(Level.SEVERE, "Erro ao gerar arquivo de backup", e);
				throw new ServletException(e);
			}
			
			ByteArrayOutputStream zipOut = new ByteArrayOutputStream();
			ZipOutputStream zip = new ZipOutputStream(zipOut);
			ZipEntry entry = new ZipEntry("backup.xml");
			zip.putNextEntry(entry);
			zip.write(out.toByteArray());
			zip.close();
			
			DataSource ds = new ByteArrayDataSource(zipOut.toByteArray(), "aplication/zip");
									
			Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);

	        try {
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("tiagonn@gmail.com", "Tiago Nascimento"));
				msg.addRecipient(Message.RecipientType.TO,
				                 new InternetAddress(destinatarios));
				msg.setSubject("Backup de mapas e surdos do ApoioTerritorioLS");
				msg.setText("Segue em anexo o backup");
				Multipart mp = new MimeMultipart();						
				 
				MimeBodyPart htmlPart = new MimeBodyPart();
			    htmlPart.setContent("Segue em anexo o backup", "text/html");
			    mp.addBodyPart(htmlPart);
			    
				MimeBodyPart attachment = new MimeBodyPart();
				attachment.setFileName("backup.zipe");
				attachment.setDataHandler(new DataHandler(ds));
				mp.addBodyPart(attachment);
				
				msg.setContent(mp);
				
				Transport.send(msg);
	        } catch (Exception e) {
				logger.log(Level.SEVERE, "Erro ao enviar e-mail de backup", e);
				throw new ServletException(e);
			}

			
		}
	}
	
	private MapaType obterMapaXML(Mapa mapa) {
		MapaType xml = new MapaType();
		
		xml.setId(mapa.getId());
		xml.setLetra(mapa.getLetra());
		xml.setNumero(mapa.getNumero());
		xml.setRegiao(mapa.getRegiao());
		
		return xml;
	}
	
	private SurdoType obterSurdoXML(Surdo surdo) {
		SurdoType xml = new SurdoType();
		
		xml.setBairro(surdo.getBairro());
		xml.setCep(surdo.getCep());
		xml.setComplemento(surdo.getComplemento());
		xml.setCrianca(surdo.getCrianca());
		xml.setDvd(surdo.getDvd());
		xml.setEstaAssociadoMapa(surdo.getEstaAssociadoMapa());
		xml.setHorario(surdo.getHorario());
		xml.setId(surdo.getId());
		xml.setIdade(surdo.getIdade());
		xml.setInstrutor(surdo.getInstrutor());
		xml.setLatitude(surdo.getLatitude());
		xml.setLibras(surdo.getLibras());
		xml.setLogradouro(surdo.getLogradouro());
		xml.setLongitude(surdo.getLongitude());
		if (surdo.getMapa() != null) {
			xml.setMapa(surdo.getMapa().getId());
		}
		xml.setMelhorDia(surdo.getMelhorDia());
		xml.setMsn(surdo.getMsn());
		xml.setNome(surdo.getNome());
		xml.setNumero(surdo.getNumero());
		xml.setObservacao(surdo.getObservacao());
		xml.setOnibus(surdo.getOnibus());
		xml.setPossuiMSN(surdo.isPossuiMSN());
		xml.setRegiao(surdo.getRegiao());
		xml.setSexo(surdo.getSexo());
		xml.setTelefone(surdo.getTelefone());
		xml.setMudouSe(surdo.isMudouSe());
		xml.setVisitarSomentePorAnciaos(surdo.isVisitarSomentePorAnciaos());
		
		return xml;		
	}

}
