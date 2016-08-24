package jar;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import jar.dao.AlunoDao;
import jar.dao.EventoDao;
import jar.dao.PessoaDao;
import jar.dao.PlasticoDao;
import jar.model.Aluno;
import jar.model.Evento;
import jar.model.Pessoa;
import jar.model.Plastico;
import jar.util.PropertyHandler;
import jar.util.Status;
import jar.util.TipoPessoa;

public class CatracaController {

	private static final String ATIVO = Status.ATIVO.toString();
	private static final String ALUNO = TipoPessoa.ALUNO.toString();
	private String horaEntrada;
	private String horaSaida;
	private PlasticoDao plasticoDao;
	private PessoaDao pessoaDao;
	private AlunoDao alunoDao;
	private EventoDao eventoDao;
	static Logger LOGGER = Logger.getLogger(CatracaController.class);

	public CatracaController() {

		LOGGER.info("Inicializando classes de DAO ...");
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

		horaEntrada = PropertyHandler.getInstance().getValue("hora.entrada");
		horaSaida = PropertyHandler.getInstance().getValue("hora.saida");

		LOGGER.info("Hora Entrada cadastrada: " + horaEntrada);
		LOGGER.info("Hora Saída cadastrada: " + horaSaida);

	}

	public HashMap<String, Object> novoEvento(String linhaDigitavel) {

		LOGGER.info("***** Criando novo evento ******");
		HashMap<String, Object> retorno = new HashMap<String, Object>();

		// Caso a linha digitável esteja vazia, ERRO
		if ((linhaDigitavel.equals("")) || (linhaDigitavel == null)) {
			retorno.put("mensagem",
					"Leitura falhou, tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");
			LOGGER.error(retorno.get("mensagem"));
		} else {

			try {
				// Busca plástico através da linha digitïável
				LOGGER.info("Buscando Cartão: " + linhaDigitavel);
				Plastico plastico = consultarPlastico(linhaDigitavel);

				// Se o plástico existe e está ativo
				if ((plastico != null) && (plastico.getStatus().equals(ATIVO))) {
					LOGGER.debug("Cartão " + plastico.getLinhaDigitavel() + " encontrado e Ativo.");

					// Se a pessoa existe E ela é aluno E ela está ativa
					Pessoa pessoa = pessoaDao.findById(plastico.getPessoaId());
					if ((pessoa != null) && (pessoa.getTipoPessoa().equals(ALUNO)) && alunoAtivo(pessoa.getId())) {
						LOGGER.info("Pessoa existe e é um aluno ativo!");

						LOGGER.info("Buscando eventos cadastrados para a pessoa");
						List<Evento> eventos = consultarEventosEntrada(pessoa.getId());
						if (eventos == null || eventos.isEmpty()) {
							LOGGER.info("Não há eventos para o dia. Registrando nova entrada...");
							Date now = new Date();

							String status = comparaEntrada(now);
							Evento evento = new Evento(now, null, status, pessoa.getId());
							LOGGER.info("Salvando novo envento entrada");
							eventoDao.save(evento);
							LOGGER.info("Evento criado com sucesso...");
							String mensagem = info(pessoa, evento, "ENTRADA"+status);
							retorno.put("mensagem", mensagem);
							retorno.put("status", status);
							LOGGER.info(mensagem);
						} else {
							Evento evento;

							if (eventos.size() > 1) {
								evento = eventos.get(eventos.size() - 1);
								LOGGER.info(
										"Existe mais de 1 evento para este cartão no dia de hoje, apenas o evento mais recente será atualizado");
							} else {
								evento = eventos.get(0);
							}

							Date now = new Date();

							LOGGER.info("Validando horário de Saída para criar status do Evento.");
							String status = comparaSaida(now);
							LOGGER.info("Status = " + status);
							evento.setDataHoraSaida(now);
							evento.setStatus(status);
							LOGGER.info("Salvando novo envento saída");
							update(evento);
							String mensagem = info(pessoa, evento, "SAIDA"+status);
							retorno.put("mensagem", mensagem);
							retorno.put("status", status);
							LOGGER.info(mensagem);
						}
					} else{
						retorno.put("mensagem", (Object) "Aluno não encontrado ou INATIVO na base de dados. \n"
								+ "Tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");
						LOGGER.warn(retorno.get("mensagem"));
					}
				} else {
					retorno.put("mensagem", (Object) "O numero da carteirinha ["+ linhaDigitavel +"] não foi encontrado na base de dados. \n"
							+ "Tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");
					LOGGER.warn(retorno.get("mensagem"));
					

				}
			} catch (Exception e) {
				retorno.put("mensagem", "Houve um erro ao registradar as informações. \n"
						+ "Tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");
				LOGGER.error(retorno.get("mensagem"), e);
			}
		}
		return retorno;

	}

	private String info(Pessoa pessoa, Evento evento, String tipoMsg) {
		String mensagem = null;

		if (tipoMsg.equals("ENTRADAOK")) {

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dataFormatada = df.format(evento.getDataHoraEntrada());

			mensagem = "Bem vindo " + pessoa.getNome() + "\nEntrada registrada: " + dataFormatada;
			LOGGER.info(mensagem);
		} else if (tipoMsg.equals("ENTRADANOK")) {

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dataFormatada = df.format(evento.getDataHoraEntrada());

			mensagem = pessoa.getNome() + " Você está entrando no SEGUNDO HORÁRIO \nEntrada registrada: " + dataFormatada;
			LOGGER.info(mensagem);


		}else if (tipoMsg.equals("SAIDANOK")) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dataFormatada = df.format(evento.getDataHoraSaida());

			mensagem = pessoa.getNome() + " você está saindo antes do fim das Aulas de hoje \nSaida registrada: "
					+ dataFormatada;
			LOGGER.info(mensagem);
		} else if (tipoMsg.equals("SAIDAOK")) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dataFormatada = df.format(evento.getDataHoraSaida());

			mensagem = pessoa.getNome() + " Até a próxima aula \nSaida registrada: "
					+ dataFormatada;
			LOGGER.info(mensagem);

		}else{
			mensagem = "Não foi especificado tipo de mensagem";
			LOGGER.warn(mensagem);
		}
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
			LOGGER.error("Erro ao consultar plastico", e);
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
				} else {
					estaAtivo = false;
				}
			} else {
				estaAtivo = false;
			}

			return estaAtivo;
		} catch (Exception e) {
			LOGGER.error("Erro!!", e);
			return false;
		}
	}

	private List<Evento> consultarEventosEntrada(Long pessoaId) {
		List<Evento> eventos = new ArrayList<Evento>();
		try {
			eventos = eventoDao.findEventByPersonAndDate(pessoaId, new Date());
			return eventos;
		} catch (Exception e) {
			LOGGER.error("Erro!!", e);
			return eventos;
		}
	}

	private void update(Evento novoEvento) {
		try {
			Evento eventoSalvo = eventoDao.findById(novoEvento.getId());
			if (novoEvento.equals(eventoSalvo)) {
				LOGGER.info("Atualizando evento com data final e status ...");
				eventoDao.update(novoEvento);
			} else {
				LOGGER.info("Evento Não encontrado para atualização ..");
			}
		} catch (SQLException e) {
			LOGGER.error("Erro ao atualizar evento ", e);
		}
	}

	private String comparaEntrada(Date now) throws ParseException {
		LOGGER.info("Validando horário de entrada para criar status do Evento.");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date entrada;
		String status = "";
		entrada = sdf.parse(horaEntrada);
		if (now.getHours() == entrada.getHours()) {
			if (now.getMinutes() <= entrada.getMinutes()) {
				status = "OK";
				LOGGER.info("Entrada OK");
			} else {
				status = "NOK";
				LOGGER.info("Entrada no segundo horário...");
			}
		} else if (now.getHours() < entrada.getHours()) {
			status = "OK";
			LOGGER.info("Entrada OK");
		} else {
			status = "NOK";
			LOGGER.info("Entrada no segundo horário...");
		}

		return status;

	}

	private String comparaSaida(Date now) throws ParseException {
		LOGGER.info("Comparando datas");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date saida;
		String status = "";
		saida = sdf.parse(horaSaida);
		if (now.getHours() == saida.getHours()) {
			if (now.getMinutes() >= saida.getMinutes()) {
				status = "OK";
				LOGGER.info("Saída OK");
			} else
				status = "NOK";
			LOGGER.info("Saída antes do fim da aula...");
		} else if (now.getHours() < saida.getHours()) {
			status = "NOK";
			LOGGER.info("Saída antes do fim da aula...");

		} else {
			status = "OK";
			LOGGER.info("Saída OK");
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
