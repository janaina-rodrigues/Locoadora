import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Filme {
	private static String NOME_ARQUIVO = "filme.loc";

	int id;
	char ativo;
	char alugado;
	String nome;
	String genero;
	String ano;
	float valorLocacao;

	public Filme() {
		this.alugado = 'N';
		this.ativo = 'S';
		this.id = 0;
	}

	public void cadastrar() {

		String nome = null;
		String genero = null;
		String ano = null;
		float valorLocacao = 0;

		System.out.println("\nCADASTRO DE FILMES =====================================");

		do {
			nome = LTPUtils.getString("Digite o Nome do Filme: ");

			genero = LTPUtils.getString("Digite o Genero do Filme: ");

			ano = validarAno(LTPUtils.getString("Digite o Ano do Filme: "));

			valorLocacao = obterValorLocacao(ano);

			this.getFilme(nome);
			// Se encontrou o cliente jÃ¡ cadastrado, mostra os dados
			if (this.id != -1) {
				this.mostrarDadosFilme();
				System.err.println("Filme já¡ cadastrado. Cadastre outro!");
			}

		} while (this.id != -1);

		this.id = autoIncrement();
		this.nome = nome;
		this.genero = genero;
		this.ano = ano;
		this.valorLocacao = valorLocacao;

		if (this.id != -1 && LTPUtils.recebeSouN("Deseja Salvar (S/N)? ") == 'S') {
			salvar();
		}

	}

	public static void alterarPorId(int id) {
		// Recupera o Brand Atual
		Filme atual = Filme.getFilmePorId(id);

		if (atual != null) {

			// Receber o novo
			Filme novo = new Filme();

			// Alterar gênero
			if (LTPUtils.recebeSouN("GENERO: " + atual.genero + "\nAtualizar (S/N)? ") == 'S') {
				novo.genero = LTPUtils.getStringUpperCase("Digite o gênero para atualziar: ");
			} else {
				novo.genero = atual.genero;
			}

			// alterar ano
			if (LTPUtils.recebeSouN("Ano: " + atual.ano + "\nAtualizar (S/N)? ") == 'S') {
				novo.ano = validarAno(LTPUtils.getStringUpperCase("Digite o ano para atualizar: "));
			} else {
				novo.ano = atual.ano;
			}

			novo.id = atual.id;

			novo.alugado = atual.alugado;
			novo.nome = atual.nome;
			novo.valorLocacao = atual.valorLocacao;

			// Se existe, irá desativar o atual
			excluirPorId(id);

			novo.salvar();

			System.out.println("ID (" + id + "): alterado com sucesso!");
		} else {
			System.err.println("ID não encontrado!");
		}

	}

	public static Filme getFilmePorId(int id) {

		try {
			RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");
			Filme f = new Filme();

			while (true) {
				f.ativo = arq.readChar();
				f.id = arq.readInt();
				f.alugado = arq.readChar();
				f.nome = arq.readUTF();
				f.genero = arq.readUTF();
				f.ano = arq.readUTF();
				f.valorLocacao = arq.readFloat();
				if (f.ativo == 'S' && f.id == id) {
					return f;
				}
			}

		} catch (EOFException e) {
			return null;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
		return null;
	}

	public static void excluirPorId(int id) {
		try {

			long pointer = getPointerBrandById(id);

			if (pointer != -1) {

				RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");

				arq.seek(pointer);

				arq.writeChar('N');

				arq.close();

				System.out.println("ID (" + id + "): excluído com sucesso!");

			} else {
				System.err.println("ID não encontrado!");
			}

		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
	}

	public static long getPointerBrandById(int id) {

		long pointer;

		try {
			RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");
			Filme f = new Filme();

			while (true) {
				pointer = arq.getFilePointer();

				f.ativo = arq.readChar();
				f.id = arq.readInt();
				f.alugado = arq.readChar();
				f.nome = arq.readUTF();
				f.genero = arq.readUTF();
				f.ano = arq.readUTF();
				f.valorLocacao = arq.readFloat();
				if (f.ativo == 'S' && f.id == id) {
					return pointer;
				}
			}

		} catch (EOFException e) {
			return -1;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
		return -1;
	}

	public static void listarTodosFilmes() {
		try {
			RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");

			Filme f = new Filme();

			while (true) {
				f.ativo = arq.readChar();
				f.id = arq.readInt();
				f.alugado = arq.readChar();
				f.nome = arq.readUTF();
				f.genero = arq.readUTF();
				f.ano = arq.readUTF();
				f.valorLocacao = arq.readFloat();
				if (f.ativo == 'S') {
					f.mostrarDadosFilme();
				}
			}

		} catch (EOFException e) {
			System.out.println("=====================================");
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
	}

	public void pesquisarPorNome(String nome) {
		getFilme(nome);
		if (nome.equals(this.nome)) {
			mostrarDadosFilme();
		} else {
			System.err.println("Filme " + nome + " Não Entrado!");
		}
	}

	public void pesquisarPorGenero(String genero) {
		try {
			RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");
			Filme f = new Filme();

			while (true) {
				f.ativo = arq.readChar();
				f.id = arq.readInt();
				f.alugado = arq.readChar();
				f.nome = arq.readUTF();
				f.genero = arq.readUTF();
				f.ano = arq.readUTF();
				f.valorLocacao = arq.readFloat();

				if (f.ativo == 'S' && f.genero.equalsIgnoreCase(genero)) {
					this.setFilme(f.id, f.alugado, f.nome, f.genero, f.ano, f.valorLocacao);
					mostrarDadosFilme();
				}
			}

		} catch (EOFException e) {
			this.id = -1;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

		if (this.id == -1) {
			System.err.println("Erro: Gênero não encontrado");
		}
	}

	public static int autoIncrement() {
		int id = 0;

		try {
			RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");
			Filme f = new Filme();

			while (true) {
				f.ativo = arq.readChar();
				f.id = arq.readInt();
				f.alugado = arq.readChar();
				f.nome = arq.readUTF();
				f.genero = arq.readUTF();
				f.ano = arq.readUTF();
				f.valorLocacao = arq.readFloat();

				id = f.id;
			}

		} catch (EOFException e) {
			return ++id;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

		return ++id;
	}

	private static String validarAno(String ano) {
		do {

			if (!isNumeric(ano) || ano.length() != 4 || Integer.valueOf(ano) < 1900 || Integer.valueOf(ano) > 2019) {
				System.err.println("Ano inválido digite um ano válido.");
				break;
			}
		} while (!isNumeric(ano) || ano.length() != 4 || Integer.valueOf(ano) < 1900 || Integer.valueOf(ano) > 2019);

		return ano;
	}

	private float obterValorLocacao(String ano) {

		int auxAno = Integer.valueOf(ano);

		if (auxAno >= 1900 && auxAno <= 1999) {
			return 3;
		} else if (auxAno >= 2000 && auxAno <= 2010) {
			return 5;
		} else if (auxAno >= 2011 && auxAno <= 2019) {
			return 10;
		}
		return 0;
	}

	private void mostrarDadosFilme() {
		System.out.println("DADOS FILME =========");
		System.out.println("ID                : " + this.id);
		System.out.println("Alugado           : " + this.alugado);
		System.out.println("Nome              : " + this.nome);
		System.out.println("Genero            : " + this.genero);
		System.out.println("Ano               : " + this.ano);
		System.out.println("Valor Locação   : " + LTPUtils.formatacaoReal(this.valorLocacao));
	}

	public void getFilme(String nome) {

		try {
			RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");
			Filme f = new Filme();

			while (true) {
				f.ativo = arq.readChar();
				f.id = arq.readInt();
				f.alugado = arq.readChar();
				f.nome = arq.readUTF();
				f.genero = arq.readUTF();
				f.ano = arq.readUTF();
				f.valorLocacao = arq.readFloat();

				if (f.alugado == 'N' && f.ativo == 'S' && f.nome.equalsIgnoreCase(nome)) {
					this.setFilme(f.id, f.alugado, f.nome, f.genero, f.ano, f.valorLocacao);
					break;
				}
			}

			arq.close();

		} catch (EOFException e) {
			this.id = -1;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

	}

	private void setFilme(int id, char alugado, String nome, String genero, String ano, float valorLocacao) {
		this.ativo = 'S';
		this.id = id;
		this.alugado = alugado;
		this.nome = nome;
		this.genero = genero;
		this.ano = ano;
		this.valorLocacao = valorLocacao;

	}

	public void salvar() {
		try {
			RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");

			arq.seek(arq.length());
			arq.writeChar(this.ativo);
			arq.writeInt(this.id);
			arq.writeChar(this.alugado);
			arq.writeUTF(this.nome);
			arq.writeUTF(this.genero);
			arq.writeUTF(this.ano);
			arq.writeFloat(this.valorLocacao);

			arq.close();

			System.out.println("Arquivo: " + NOME_ARQUIVO + " salvo com sucesso!");
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

	}

	public static boolean isNumeric(final CharSequence cs) {
		if (isEmpty(cs)) {
			return false;
		}
		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

}
