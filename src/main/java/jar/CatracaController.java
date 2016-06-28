package jar;

import jar.dao.AlunoDao;
import jar.dao.EventoDao;
import jar.dao.PessoaDao;
import jar.dao.PlasticoDao;
import jar.model.Aluno;
import jar.model.Evento;
import jar.model.Pessoa;
import jar.model.Plastico;
import jar.util.AbstractPropertyReader;
import jar.util.Status;
import jar.util.TipoPessoa;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CatracaController {

	private static final String ATIVO = Status.ATIVO.toString();
	private static final String ALUNO = TipoPessoa.ALUNO.toString();
	private String horaEntrada;
	private String horaSaida;
	private PlasticoDao plasticoDao;
	private PessoaDao pessoaDao;
	private AlunoDao alunoDao;
	private EventoDao eventoDao;
	static Logger logger = Logger.getLogger(CatracaController.class);
	private Properties prop = null;


	public CatracaController() {

		logger.info("Inicializando classes de DAO ...");
		synchronized (CatracaController.class) {
			if (plasticoDao == null) {
				plasticoDao = new PlasticoDao();
			}
			if (pessoaDao == null) {
				pessoaDao = new PessoaDao();
			}
			if (alunoDao == null) {
				alunoDao = new AlunoDao();
			}
			if (eventoDao == null) {
				eventoDao = new EventoDao();
			}
		}

		if(prop == null){
			prop = AbstractPropertyReader.propertyReader();
		}
		horaEntrada = prop.getProperty("hora.entrada");
		horaSaida = prop.getProperty("hora.saida");

		logger.info("Hora Entrada cadastrada: " + horaEntrada);
		logger.info("Hora Sa�da cadastrada: " + horaSaida);



	}

	public HashMap<String, Object> novoEvento(String linhaDigitavel) {

		logger.info("***** Criando novo evento ******");
		HashMap<String, Object> retorno = new HashMap<String, Object>();

		// Caso a linha digit�vel esteja vazia, ERRO
		if ((linhaDigitavel.equals("")) || (linhaDigitavel == null)) {
			retorno.put("mensagem",
						"Leitura falhou, tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");
			logger.error(retorno.get("mensagem"));
		} else {

			try {
				// Busca pl�stico atrav�s da linha digit��vel
				logger.info("Buscando Cart�o: " + linhaDigitavel);
				Plastico plastico = consultarPlastico(linhaDigitavel);

				// Se o pl�stico existe e est� ativo
				if ((plastico != null) && (plastico.getStatus().equals(ATIVO))) {
					logger.debug("Cart�o " + plastico.getLinhaDigitavel() + " encontrado e Ativo.");

					// Se a pessoa existe E ela � aluno E ela est� ativa
					Pessoa pessoa = pessoaDao.findById(plastico.getPessoaId());
					if ((pessoa != null) && (pessoa.getTipoPessoa().equals(ALUNO)) && alunoAtivo(pessoa.getId())) {
						logger.info("Pessoa existe e � um aluno ativo!");
						logger.info("Buscando eventos cadastrados para a pessoa");
						List<Evento> eventos = consultarEventosEntrada(pessoa.getId());
						if (eventos == null || eventos.isEmpty()) {
							logger.info("N�o h� eventos para o dia. Registrando nova entrada...");
							Date now = new Date();

							String status = comparaEntrada(now);
							Evento evento = new Evento(now, null, status, pessoa.getId());
							logger.info("Salvando novo envento entrada");
							eventoDao.save(evento);
							logger.info("Evento criado com sucesso...");
							String mensagem = info(pessoa, evento, "ENTRADA");
							retorno.put("mensagem", mensagem);
							logger.info(mensagem);
						} else {
							Evento evento;

							if (eventos.size() > 1) {
								evento = eventos.get(eventos.size() - 1);
								logger.info("Existe mais de 1 evento para este cart�o no dia de hoje, apenas o evento mais recente ser� atualizado");
							} else {
								evento = eventos.get(0);
							}

							Date now = new Date();

							logger.info("Validando hor�rio de Sa�da para criar status do Evento.");
							String status = comparaSaida(now);
							logger.info("Status = " + status);
							evento.setDataHoraSaida(now);
							evento.setStatus(status);
							logger.info("Salvando novo envento sa�da");
							update(evento);
							String mensagem = info(pessoa, evento, "SAIDA");
							retorno.put("mensagem", mensagem);
							logger.info(mensagem);
						}
					}
				} else {
					retorno.put("mensagem",
								(Object) "N�mero de cart�o n�o encontrado na base de dados,"
												+ " tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");

				}
			} catch (Exception e) {
				e.printStackTrace();
				retorno.put("mensagem", "Houve um erro ao registradar as informa��es"
										+ " tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");
				logger.error(retorno.get("mensagem"), e);
			}
		}
		return retorno;

	}

	private String info(Pessoa pessoa, Evento evento, String tipoMsg) {
		String mensagem = null;

		if (tipoMsg.equals("ENTRADA")) {

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dataFormatada = df.format(evento.getDataHoraEntrada());

			mensagem = "Bem vindo " + pessoa.getNome() + "\nEntrada registrada: " + dataFormatada;
		} else if (tipoMsg.equals("SAIDA")) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dataFormatada = df.format(evento.getDataHoraSaida());

			mensagem = pessoa.getNome() + " voc� est� saindo antes do fim das Aulas de hoje \nSaida registrada: "
						+ dataFormatada;
		} else
			mensagem = "N�o foi especificado tipo de mensagem";
		return mensagem;
	}

	/**
	 * @author ARTUR
	 * @param linhaDigitavel
	 * @return
	 *
	 */
	private Plastico consultarPlastico(String linhaDigitavel) {
		try {
			Plastico plasticoEncontrado = null;
			plasticoEncontrado = plasticoDao.findByDigitableLine(linhaDigitavel);
			return plasticoEncontrado;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	private boolean alunoAtivo(Long pessoaId) {
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

	private List<Evento> consultarEventosEntrada(Long pessoaId) {
		List<Evento> eventos = new ArrayList<Evento>();
		try {
			eventos = eventoDao.findEventByPersonAndDate(pessoaId, Calendar.getInstance().getTime());
			return eventos;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return eventos;
		}
	}

	private void update(Evento novoEvento) {
		try{
			Evento eventoSalvo = eventoDao.findById(novoEvento.getId());
			if (novoEvento.equals(eventoSalvo)) {
				logger.info("Atualizando evento com data final e status ...");
				eventoDao.update(novoEvento);
			} else {
				logger.info("Evento N�o encontrado para atualiza��o ..");
			}
		}catch(SQLException e){
			logger.error("Erro ao atualizar evento ", e);
		}
	}

	private String comparaEntrada(Date now) throws ParseException {
		logger.info("Validando hor�rio de entrada para criar status do Evento.");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date entrada;
		String status = "";
		entrada = sdf.parse(horaEntrada);
		if (now.getHours() == entrada.getHours()) {
			if (now.getMinutes() <= entrada.getMinutes()) {
				status = "OK";
				logger.info("Entrada OK");
			} else{
				status = "NOK";
			logger.info("Entrada no segundo hor�rio...");
			}
		} else if (now.getHours() < entrada.getHours()) {
			status = "OK";
			logger.info("Entrada OK");
		} else{
			status = "NOK";
			logger.info("Entrada no segundo hor�rio...");
		}

		return status;

	}

	private String comparaSaida(Date now) throws ParseException {
		logger.info("Comparando datas");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date saida;
		String status = "";
		saida = sdf.parse(horaSaida);
		if (now.getHours() == saida.getHours()) {
			if (now.getMinutes() >= saida.getMinutes()) {
				status = "OK";
				logger.info("Sa�da OK");
			} else
				status = "NOK";
			logger.info("Sa�da antes do fim da aula...");
		} else if (now.getHours() < saida.getHours()) {
			status = "NOK";
			logger.info("Sa�da antes do fim da aula...");

		} else {
			status = "OK";
			logger.info("Sa�da OK");
		}
		return status;
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
