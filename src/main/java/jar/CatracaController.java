package jar;

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

public class CatracaController {

	private static final String ATIVO;
	private static final String ALUNO;
	private static final String horaEntrada;
	private static final String horaSaida;
	private PlasticoDao plasticoDao;
	private PessoaDao pessoaDao;
	private AlunoDao alunoDao;
	private EventoDao eventoDao;
	static Logger logger = Logger.getLogger(CatracaController.class);

	static {
		logger.info("Iniciando bloco estático...");
		Properties prop = AbstractPropertyReader.propertyReader();
		horaEntrada = prop.getProperty("hora.entrada");
		horaSaida = prop.getProperty("hora.saida");

		logger.info("Hora Entrada cadastrada: " + horaEntrada);
		logger.info("Hora Saída cadastrada: " + horaSaida);

		ATIVO = Status.ATIVO.toString();
		ALUNO = TipoPessoa.ALUNO.toString();
	}

	public CatracaController() {
		logger.info("Inicializando classes de DAO ...");
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
		logger.info("*****Criando novo evento ...******");
		HashMap<String, Object> retorno = new HashMap<String, Object>();

		// Caso a linha digitável esteja vazia, ERRO
		if ((linhaDigitavel.equals("")) || (linhaDigitavel == null)) {
			retorno.put("mensagem",
					"Leitura falhou, tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");
			logger.error(retorno.get("mensagem"));
		} else {
			try {
				// Busca plástico através da linha digitïável
				logger.info("Buscando Cartão...");
				Plastico plastico = consultarPlastico(linhaDigitavel);

				// Se o plástico existe e está ativo
				if ((plastico != null) && (plastico.getStatus().equals(ATIVO))) {
					logger.info("Cartão encontrado!");

					// Se a pessoa existe E ela é aluno E ela está ativa
					Pessoa pessoa = plastico.getPessoa();
					if ((pessoa != null) && (pessoa.getTipopessoa().equals(ALUNO)) && alunoAtivo(pessoa.getId())) {
						logger.info("Pessoa Existe e é um aluno ativo");
						logger.info("Buscando Eventos ...");
						List<Evento> eventos = consultarEventosEntrada(pessoa.getId());
						if (eventos.isEmpty()) {
							logger.info("Não hã eventos para o dia. Registrando nova entrada...");
							Date now = new Date();

							logger.info("Validando horário de entrada para criar status do Evento.");
							String status = comparaEntrada(now);
							logger.info("Status = " + status);
							Evento evento = new Evento(now, null, status, pessoa);
							logger.info("Salvando novo envento entrada");
							eventoDao.save(evento);
							logger.info("Evento criado com sucesso...");
							String mensagem = info(pessoa, evento, "ENTRADA");
							retorno.put("mensagem", mensagem);
							logger.info(mensagem);
						} else {
							Evento evento;

							if (eventos.size() > 1) {
								evento = eventos.get(eventos.size()-1);
								logger.info(
										"Existe mais de 1 evento para este cartão no dia de hoje, apenas o evento mais recente será atualizado");
							} else {
								evento = eventos.get(0);
							}

							Date now = new Date();

							logger.info("Validando horário de Saída para criar status do Evento.");
							String status = comparaSaida(now);
							logger.info("Status = " + status);
							evento.setDatahorasaida(now);
							evento.setStatus(status);
							logger.info("Salvando novo envento saída");
							eventoDao.update(evento);
							String mensagem = info(pessoa, evento, "SAIDA");
							retorno.put("mensagem", mensagem);
							logger.info(mensagem);
						}
					}
				} else {
					retorno.put("mensagem", (Object) "Número de cartão não encontrado na base de dados,"
							+ " tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");

				}
			} catch (Exception e) {
				e.printStackTrace();
				retorno.put("mensagem", "Houve um erro ao registradar as informações"
						+ " tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");
				System.err.println(retorno.get("mensagem"));
			}
		}
		return retorno;

	}

	private String info(Pessoa pessoa, Evento evento, String tipoMsg) {
		String mensagem = null;

		if (tipoMsg.equals("ENTRADA")) {

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dataFormatada = df.format(evento.getDatahoraentrada());

			mensagem = "Bem vindo " + pessoa.getNome() + "\nEntrada registrada: " + dataFormatada;
		} else if (tipoMsg.equals("SAIDA")) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dataFormatada = df.format(evento.getDatahorasaida());

			mensagem = pessoa.getNome() + " você está saindo antes do fim das Aulas de hoje \nSaida registrada: "
					+ dataFormatada;
		} else
			mensagem = "Não foi especificado tipo de mensagem";
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

	private String comparaEntrada(Date now) throws ParseException {
		logger.info("Comparando datas");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date entrada;
		String status = "";
		entrada = sdf.parse(horaEntrada);
		if (now.getHours() == entrada.getHours()) {
			if (now.getMinutes() <= entrada.getMinutes()) {
				status = "OK";
				logger.info("Entrada OK");
			} else
				status = "NOK";
			logger.info("Entrada no segundo horário...");
		} else if (now.getHours() < entrada.getHours()) {
			status = "OK";
			logger.info("Entrada OK");
		} else
			status = "NOK";
		logger.info("Entrada no segundo horário...");

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
				logger.info("Saída OK");
			} else
				status = "NOK";
			logger.info("Saída antes do fim da aula...");
		} else if (now.getHours() < saida.getHours()) {
			status = "NOK";
			logger.info("Saída antes do fim da aula...");

		} else {
			status = "OK";
			logger.info("Saída OK");
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
