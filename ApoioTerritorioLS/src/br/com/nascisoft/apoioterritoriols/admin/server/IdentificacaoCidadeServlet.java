package br.com.nascisoft.apoioterritoriols.admin.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;

public class IdentificacaoCidadeServlet extends
		AbstractApoioTerritorioLSHttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(IdentificacaoCidadeServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		logger.info("Entrando em servlet de mapeamento de identificacação de cidade");
		
		String cidade = req.getParameter("nome");
		String uf = req.getParameter("uf");
		
		InputStream is = this.getServletContext().getResourceAsStream("/WEB-INF/apoioterritorio.properties");
		Properties props = new Properties();
		props.load(is);

		String key = props.getProperty("mapsKey");
		
		final Geocoder geocoder = new Geocoder();
		geocoder.setApiKey(key);
		GeocoderRequest geocoderRequest = 
				new GeocoderRequestBuilder().setAddress(cidade + ", " + uf + ", Brasil").setLanguage("pt-BR").setRegion("BR").getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
		
		resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println("<html><title>Resultado de busca geografica</title><body>Resultado de busca <br/>");
        writer.println("Status: " + geocoderResponse.getStatus() + "<br/>");
        
        for (GeocoderResult result : geocoderResponse.getResults()) {
            writer.println("Endereço: " + result.getFormattedAddress() + "<br/>");
        	writer.println("Latitude: " + result.getGeometry().getLocation().getLat() + "<br/>");
        	writer.println("Longitude: " + result.getGeometry().getLocation().getLng() + "<br/>");
        }
        
        writer.println("</body></html>");
		
	}

}
