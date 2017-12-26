package br.com.nascisoft.appterritorios.dao;


import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Query;

public class BaseDAO {
	
	protected <T> List<T> aplicarFiltro(Map<String, Object> mapa, Class<T> type, String order) {
		Set<String> filtros = mapa.keySet();
		LoadType<T> loader = ofy().load().type(type);
		Query<T> query = null;
		for (String filtro : filtros) {
			if (query != null) {
				query = query.filter(filtro, mapa.get(filtro));
			} else {
				query = loader.filter(filtro, mapa.get(filtro));
			}
		}
		
		
		if (order != null) {
			if (query != null) {
				query = query.order(order);
			} else {
				query = loader.order(order);
			}
		}
		
		List<T> retorno = null;
		if (query != null) {
			retorno = query.list();
		} else {
			retorno = loader.list();
		}
		
		return retorno;
		
	}

}
