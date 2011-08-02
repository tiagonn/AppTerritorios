package br.com.nascisoft.apoioterritoriols.cadastro;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;
import br.com.nascisoft.apoioterritoriols.cadastro.xml.Bairros;
import br.com.nascisoft.apoioterritoriols.cadastro.xml.Regioes;

public class CadastroTestCase extends TestCase {
	
	public void testXMLJaxB() throws Exception {
		Bairros bairros = new Bairros();
		List<String> lista = new ArrayList<String>();
		lista.add("Bairro 1");
		lista.add("Bairro 2");
		bairros.setBairro(lista);
		String result = convertToXml(bairros, Bairros.class);
		assertNotNull(result);
		
	}
	
	public void testXMLFile2Bean() throws Exception {
		JAXBContext context = JAXBContext.newInstance(Bairros.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Bairros bairros = (Bairros)unmarshaller.unmarshal(new File("META-INF/BairrosCampinas.xml"));
		assertNotNull(bairros);
	}
	
	public void testXMLRegioesBean() throws Exception {
		JAXBContext context = JAXBContext.newInstance(Regioes.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Regioes regioes = (Regioes)unmarshaller.unmarshal(new File("war/WEB-INF/classes/META-INF/RegioesCampinas.xml"));
		assertNotNull(regioes);
	}
	
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

}
