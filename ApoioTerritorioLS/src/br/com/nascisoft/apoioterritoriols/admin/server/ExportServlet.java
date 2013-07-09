package br.com.nascisoft.apoioterritoriols.admin.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

import br.com.nascisoft.apoioterritoriols.admin.vo.RelatorioVO;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;

public class ExportServlet extends AbstractApoioTerritorioLSHttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger
		.getLogger(ExportServlet.class.getName());

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String destinatarios = req.getParameter("destinatario");
		
		String remetente = req.getParameter("remetente");
		
		if (!StringUtils.isEmpty(destinatarios)) {
			
			RelatorioVO relatorio = (new AdminServiceImpl()).obterDadosRelatorio();
			Set<String> chaves = relatorio.keySet();
			List<String> chavesOrdenadas = new ArrayList<String>();
			chavesOrdenadas.addAll(chaves);
			Collections.sort(chavesOrdenadas);
			
			StringBuffer csv = new StringBuffer("Cidade|Região|Bairro|Quantidade\n");
			String bairroNaoInformado = "Não informado";

			for (String regiao : chavesOrdenadas) {
				csv.append(relatorio.get(regiao).getCidade()).append("|").append(regiao).append("||\n");
				Set<String> chavesBairros = relatorio.get(regiao).getMapaBairros().keySet();
				List<String> chavesBairrosOrdenados = new ArrayList<String>();
				chavesBairrosOrdenados.addAll(chavesBairros);
				Collections.sort(chavesBairrosOrdenados);
				for (String bairro : chavesBairrosOrdenados) {
					if(!bairroNaoInformado.equals(bairro)) {
						csv.append(relatorio.get(regiao).getCidade()).append("|")
							.append(regiao).append("|")
							.append(bairro).append("|")
							.append(relatorio.get(regiao).getMapaBairros().get(bairro)).append("\n");
					}
				}
				if (relatorio.get(regiao).getMapaBairros().get(bairroNaoInformado) != null) {
					csv.append(relatorio.get(regiao).getCidade()).append("|")
						.append(regiao).append("|")
						.append(bairroNaoInformado).append("|")
						.append(relatorio.get(regiao).getMapaBairros().get(bairroNaoInformado)).append("\n");
				}
			}

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			out.write(csv.toString().getBytes());
			ByteArrayOutputStream zipOut = new ByteArrayOutputStream();
			ZipOutputStream zip = new ZipOutputStream(zipOut);
			ZipEntry entry = new ZipEntry("export.csv");
			zip.putNextEntry(entry);
			zip.write(out.toByteArray());
			zip.close();
			
			DataSource ds = new ByteArrayDataSource(zipOut.toByteArray(), "aplication/zip");
									
			Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);

	        try {
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(remetente));
				msg.addRecipient(Message.RecipientType.TO,
				                 new InternetAddress(destinatarios));
				msg.setSubject("Export de relatório de endereços/surdos do ApoioTerritorioLS");
				msg.setText("Segue em anexo o export. Note que por limitação de segurança do google o anexo é um arquivo .zipe. " +
						"Por favor renomeie o arquivo para .zip e depois abra normalmente.");
				Multipart mp = new MimeMultipart();						
				 
				MimeBodyPart htmlPart = new MimeBodyPart();
			    htmlPart.setContent("Segue em anexo o export. Note que por limitação de segurança do google o anexo é um arquivo .zipe. " +
						"Por favor renomeie o arquivo para .zip e depois abra normalmente.", "text/html");
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
}
