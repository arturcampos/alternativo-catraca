package app.control;

import java.text.DateFormat;
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
	private static final String horaEntrada = "19:00:00";
	private static final String horaSaida = "22:00:00";
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

		// Caso a linha digit�vel esteja vazia, ERRO
		if ((linhaDigitavel.equals("")) || (linhaDigitavel == null)) {
			retorno.put("mensagem",
						"Leitura falhou, tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");
		} else {
			try {
				// Busca pl�stico atrav�s da linha digit�vel
				System.out.println("Buscando Cart�o...\n");
				Plastico plastico = consultarPlastico(linhaDigitavel);

				// Se o pl�stico existe e est� ativo
				if ((plastico != null) && (plastico.getStatus().equals(ATIVO))) {
					System.out.println("Cart�o encontrado! \n");

					// Se a pessoa existe E ela � aluno E ela est� ativa
					Pessoa pessoa = plastico.getPessoa();
					if ((pessoa != null) && (pessoa.getTipopessoa().equals(ALUNO)) && alunoAtivo(pessoa.getId())) {
						System.out.println("Pessoa Existe e � aluno ativo");
						List<Evento> eventos = consultarEventosEntrada(pessoa.getId());
						if (eventos.isEmpty()) {
							System.out.println("N�o h� eventos para o dia. Registrando entrada...");
							Date now = new Date();

							System.out.println("Validando hor�rio de entrada para criar status do Evento.");
							String status = comparaEntrada(now);
							System.out.println(status);
							Evento evento = new Evento(now, null, status, pessoa);
							eventoDao.save(evento);
							System.out.println("Evento criado com sucesso...");
							String mensagem = info(pessoa, evento, "ENTRADA");
							retorno.put("mensagem", mensagem);
						} else {
							Evento evento = eventos.get(0);

							Date now = new Date();

							String status = comparaSaida(now);
							evento.setDatahorasaida(now);
							evento.setStatus(status);
							eventoDao.update(evento);
							String mensagem = info(pessoa, evento, "SAIDA");
							retorno.put("mensagem", mensagem);
						}
					}
				} else {
					retorno.put("mensagem",
								"N�mero de cart�o n�o encontrado na base de dados,"
												+ " tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");

				}
			} catch (Exception e) {
				e.printStackTrace();
				retorno.put("mensagem", "Houve um erro ao registradar as informa��es"
										+ " tente novamente ou entre em contato com a Equipe do Futuro-Alternativo.");
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

			mensagem = pessoa.getNome() + " Você saindo antes do fim das Aulas de hoje \nSaida registrada: "
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

	private String comparaEntrada(Date now) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date entrada;
		String status = "";
		entrada = sdf.parse(horaEntrada);
		if (now.getHours() == entrada.getHours()) {
			if (now.getMinutes() <= entrada.getMinutes()) {
				status = "OK";
			} else
				status = "NOK";
		} else if (now.getHours() < entrada.getHours()) {
			status = "OK";
		} else
			status = "NOK";

		return status;

	}

	private String comparaSaida(Date now)throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date saida;
		String status = "";
			saida = sdf.parse(horaSaida);
			if (now.getHours() == saida.getHours()) {
				if (now.getMinutes() >= saida.getMinutes()) {
					status = "OK";
				} else
					status = "NOK";
			} else if(now.getHours() < saida.getHours()){
				status = "NOK";
		}else status = "OK";

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
