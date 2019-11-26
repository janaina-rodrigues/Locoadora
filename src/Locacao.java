import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Locacao {
	private static String NOME_ARQUIVO = "locacao.loc";
	
	int id;
	int idFilme;
	String cliente;
	String dataDevolucao;
	float valorTotalLocacao;
	boolean renovar;
	
	public Locacao() {
		this.id = 0;
	}


	public void alugar() {
		Filme.listarTodosFilmes();
	    Filme auxFilme;
		do {
			do {

				this.idFilme= LTPUtils.getIntPositivo("Informe o ID da Filme que deseja alugar: ");

				auxFilme = Filme.getFilmePorId(this.idFilme) ; 
				if (auxFilme == null) {
					System.err.println("Informe um ID Válido!");
				} else {
					System.out.println("Filme Selecionado: " + auxFilme.nome);
				}
			} while (auxFilme == null);

		
		} while (this.id != -1);
		this.id = autoIncrement();
			
		if (LTPUtils.recebeSouN("Deseja Alugar o filme" + auxFilme.nome +" (S/N)? ") == 'S') {
			auxFilme.alugado = 'S';
			auxFilme.salvarFilme();
			salvarLocacao();
		}
	}


	public void renovar() {
		// TODO Auto-generated method stub
		
	}
	
	public void salvarLocacao() {
		try {
			RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");

			arq.seek(arq.length());
			arq.writeInt(this.id);
			arq.writeInt(this.idFilme);
			arq.writeUTF(this.cliente);
			arq.writeUTF(this.dataDevolucao);
			arq.writeFloat(this.valorTotalLocacao);

			arq.close();

			System.out.println("Arquivo: " + NOME_ARQUIVO + " salvo com sucesso!");
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

	}
	
	public static int autoIncrement() {
		int id = 0;

		try {
			RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");
			Locacao l = new Locacao();

			while (true) {
				
				l.id = arq.readInt();
				l.idFilme = arq.readInt();
				l.cliente = arq.readUTF();
				l.dataDevolucao = arq.readUTF();
				l.valorTotalLocacao = arq.readFloat();
			

				id = l.id;
			}

		} catch (EOFException e) {
			return ++id;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

		return ++id;
	}

}
