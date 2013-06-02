package br.com.nascisoft.apoioterritoriols.cadastro;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import junit.framework.TestCase;

public class CadastroTestCase extends TestCase {
	
	 @SuppressWarnings("rawtypes")
	public String convertToXml(Object source, Class type) {
	        String result;
	        StringWriter sw = new StringWriter();
	        try {
	            JAXBContext context = JAXBContext.newInstance(type);
	            Marshaller marshaller = context.createMarshaller();
	            marshaller.marshal(source, sw);
	            result = sw.toString();
	        } catch (JAXBException e) {
	            throw new RuntimeException(e);
	        }

	        return result;
	    }
	 
//	 public void testBairrosImport() throws Exception {
//		 JAXBContext context = JAXBContext.newInstance(Bairros.class);
//		 Unmarshaller unmarshaller = context.createUnmarshaller();
//		 Bairros bairros = (Bairros)unmarshaller.unmarshal(new File("war/WEB-INF/classes/META-INF/BairrosCampinas.xml"));
//		 assertNotNull(bairros);
//	 }
//	 
//	 public void testRegioesImport() throws Exception {
//		JAXBContext context = JAXBContext.newInstance(Regioes.class);
//		Unmarshaller unmarshaller = context.createUnmarshaller();
//		Regioes regioes = (Regioes)unmarshaller.unmarshal(new File("war/WEB-INF/classes/META-INF/RegioesCampinas.xml"));
//		assertNotNull(regioes);
//	}

}
