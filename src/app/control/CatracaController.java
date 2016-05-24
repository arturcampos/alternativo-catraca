package app.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.dao.AlunoDao;
import app.dao.EventoDao;
import app.dao.PessoaDao;
import app.dao.PlasticoDao;
import app.model.Aluno;
import app.model.Evento;
import app.model.Pessoa;
import app.model.Plastico;
import app.util.Status;
import app.util.TipoPessoa;

public class CatracaController {

	private static final String ATIVO = Status.ATIVO.toString();
	private static final String ALUNO = TipoPessoa.ALUNO.toString();
	private static final String horaEntrada = "19:00";
	private static final String horaSaida = "22:00";
	private PlasticoDao plasticoDao;
	private PessoaDao pessoaDao;
	private AlunoDao alunoDao;
	private EventoDao eventoDao;

	public CatracaController() {
		synchronized (CatracaController.class) {
			if (plasticoDao == null) {
				plasticoDao = new PlasticoDao(Plastico.class);
			}
			if (pessoaDao == null) {
				pessoaDao = new PessoaDao(Pessoa.class);
			}
			if (alunoDao == null) {
				alunoDao = new AlunoDao(Aluno.class);
			}
			if (eventoDao == null) {
				eventoDao = new EventoDao(Evento.class);
			}
		}
	}

	public HashMap<String, Object> novoEvento(String linhaDigitavel) {
		HashMap<String, Object> retorno = new HashMap<String, Object>();

		// Caso a linha digitável esteja vazia, ERRO
		if ((linhaDigitavel.equals("")) || (linhaDigitavel == null)) {
			retorno.put("mensagem",
					"Leitura falhou, tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");
		} else {
			try {
				// Busca plástico através da linha digitável
				Plastico plastico = consultarPlastico(linhaDigitavel);

				// Se o plástico existe e está ativo
				if ((plastico != null) && (plastico.getStatus() == Status.ATIVO.toString())) {

					// Se a pessoa existe E ela é aluno E ela está ativa
					Pessoa pessoa = plastico.getPessoa();
					if ((pessoa != null) && (pessoa.getTipopessoa().equals(ALUNO)) && alunoAtivo(pessoa.getId())) {
						List<Evento> eventos = consultarEventosEntrada(pessoa.getId());
						if (eventos.isEmpty()) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							Date date1 = sdf.parse("2009-12-31");
							Date date2 = sdf.parse("2010-01-31");
							Evento evento = new Evento(new Date(), null, "OK", pessoa);
						}
					}
				} else {
					retorno.put("mensagem", "Número de cartão não encontrado na base de dados,"
							+ " tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");

				}
			} catch (Exception e) {

			}
		}
		return retorno;

	}

	/**
	 * @author ARTUR
	 * @param linhaDigitavel
	 * @return
	 * 
	 */
	public Plastico consultarPlastico(String linhaDigitavel) {
		try {
			Plastico plasticoEncontrado = null;
			plasticoEncontrado = plasticoDao.findByDigitableLine(linhaDigitavel);
			return plasticoEncontrado;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public boolean alunoAtivo(Long pessoaId) {
		try {
			Aluno aluno;
			boolean estaAtivo = false;
			List<Aluno> alunos = alunoDao.findByPersonId(pessoaId);
			if (!alunos.isEmpty()) {
				aluno = alunos.get(0);
				if (aluno.getStatus().equals(ATIVO)) {
					estaAtivo = true;
				}
			}
			return estaAtivo;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public List<Evento> consultarEventosEntrada(Long pessoaId) {
		List<Evento> eventos = new ArrayList<Evento>();
		try {
			eventos = eventoDao.findEventByPersonAndDate(pessoaId, Calendar.getInstance().getTime());
			return eventos;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return eventos;
		}
	}

	public String comparaEntrada(Date now) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date entrada;
		String status = "";
		try {
			entrada = sdf.parse(horaEntrada);
			String hora = getHours() + now.getMinutes() + now.getSeconds();
			now = sdf.parse();
			if (now..after(entrada)) {
				status = "NOK";
			} else	status = "OK";
		} catch (ParseException e) {
			e.printStackTrace();
			status = "NOK";
		}
		return status;	
	}

	public String comparaSaida() {

	}

	public PlasticoDao getPlasticoDao() {
		return plasticoDao;
	}

	public void setPlasticoDao(PlasticoDao plasticoDao) {
		this.plasticoDao = plasticoDao;
	}

	public PessoaDao getPessoaDao() {
		return pessoaDao;
	}

	public void setPessoaDao(PessoaDao pessoaDao) {
		this.pessoaDao = pessoaDao;
	}

	public AlunoDao getAlunoDao() {
		return alunoDao;
	}

	public void setAlunoDao(AlunoDao alunoDao) {
		this.alunoDao = alunoDao;
	}
}
