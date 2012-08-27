package br.com.nascisoft.apoioterritoriols.admin.server;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.nascisoft.apoioterritoriols.admin.server.dao.AdminDAO;
import br.com.nascisoft.apoioterritoriols.cadastro.entities.Surdo;

public class MapeamentoNovosAtributosServlet extends AbstractApoioTerritorioLSHttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger
		.getLogger(MapeamentoNovosAtributosServlet.class.getName());

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		logger.log(Level.INFO, "Task de mapeamento de novos atributos iniciada");
		AdminDAO dao = new AdminDAO();
		
		List<Surdo> surdos = dao.obterSurdos();
		
		for (Surdo surdo : surdos) {
			logger.log(Level.INFO, "Mapeamento novos atributos de surdo id " + surdo.getId());
			Surdo surdoMapeado = new Surdo();
			
			surdoMapeado.setId(surdo.getId());
			surdoMapeado.setNome(surdo.getNome());
			surdoMapeado.setRegiao(surdo.getRegiao());
			surdoMapeado.setLatitude(surdo.getLatitude());
			surdoMapeado.setLongitude(surdo.getLongitude());
			surdoMapeado.setMapa(surdo.getMapa());
			surdoMapeado.setLogradouro(surdo.getLogradouro());
			surdoMapeado.setNumero(surdo.getNumero());
			surdoMapeado.setComplemento(surdo.getComplemento());
			surdoMapeado.setBairro(surdo.getBairro());
			surdoMapeado.setCep(surdo.getCep());
			surdoMapeado.setObservacao(surdo.getObservacao());
			surdoMapeado.setTelefone(surdo.getTelefone());
			surdoMapeado.setLibras(surdo.getLibras());
			surdoMapeado.setPublicacoesPossui(surdo.getPublicacoesPossui());
			surdoMapeado.setDvd(surdo.getDvd());
			surdoMapeado.setInstrutor(surdo.getInstrutor());
			surdoMapeado.setIdade(surdo.getIdade());
			surdoMapeado.setAnoNascimento(surdo.getAnoNascimento());
			surdoMapeado.setSexo(surdo.getSexo());
			surdoMapeado.setHorario(surdo.getHorario());
			surdoMapeado.setMelhorDia(surdo.getMelhorDia());
			surdoMapeado.setOnibus(surdo.getOnibus());
			surdoMapeado.setMsn(surdo.getMsn());
			surdoMapeado.setMudouSe(surdo.isMudouSe());
			surdoMapeado.setVisitarSomentePorAnciaos(surdo.isVisitarSomentePorAnciaos());
			surdoMapeado.setPossuiMSN(surdo.isPossuiMSN());
			surdoMapeado.setEstaAssociadoMapa(surdo.getEstaAssociadoMapa());
			
			dao.adicionarSurdo(surdoMapeado);
		}
		
	}
}
