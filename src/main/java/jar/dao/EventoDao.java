package jar.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import jar.model.Evento;

public class EventoDao extends DaoImpl<Evento> {

	public EventoDao(Class<Evento> clazz) {
		super(clazz);
	}

	public List<Evento> findEventByPersonAndDate(Long id, Date date){
		Query query = entitymanager.createNamedQuery("Evento.findEventByPersonAndDate")
				.setParameter("personId",id)
				.setParameter("date", date);

		return (List<Evento>) query.getResultList();
	}
}
