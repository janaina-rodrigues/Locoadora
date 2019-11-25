import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;

import javax.xml.bind.Validator;

public class Cliente {
	
	private static String NOME_ARQUIVO = "cliente.loc";

	char ativo;
	int idade;
	String nome;
	String cpf;
	String telefone;
	
	public Cliente() {
		this.ativo = 'S';
	}
	
	public void cadastrar() {
		int idade = 0; 
		String nome = null;
		String telefone = null;
		String cpf = null;
		
		System.out.println("\nCADASTRO DE CLIENTES =====================================");

		do {
			
			idade= LTPUtils.getIntPositivo("Digite a idade: ");
			if(idade >= 18) {
				
				this.idade = idade;
				nome = LTPUtils.getString("Digite o Nome: ");
				
				cpf = validarCpf(LTPUtils.getString("Digite o Cpf: "));
				
				telefone = LTPUtils.getString("Digite o Telefone: ");
				
	            this.getCliente(nome);
             //Se encontrou o cliente já cadastrado, mostra os dados
				if (this.cpf != null) {
					this.mostrarDadosCliente();
					System.err.println("Cliente já cadastrado. Cadastre outro!");
				}
			}else {
				System.err.println("Cadastro permitido para idade a partir de 18 anos");
				break;
			}
		} while (this.cpf != null);
		
		this.idade = idade;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
	
		if (this.idade >=18 && this.cpf != null && LTPUtils.recebeSouN("Deseja Salvar (S/N)? ") == 'S') {
			salvar();
		}
		
		
	}

	private String validarCpf(String cpf) {
		do {
			if(isCpf(cpf)) { 
				return cpf ;
			}else {
				System.err.println("\nCPF INVÁLIDO! Digite um número de cpf válido");
				return null;
			}
			
		}while(isCpf(cpf) == false);
	}


	public void alterarPorCpf(String cpf) {
		// Recupera o Brand Atual
		        
           if(isCpf(cpf)) {		      
				Cliente atual = Cliente.getClientePorCpf(cpf);

				if (atual != null) {

				      // Receber o novo
						Cliente novo = new Cliente();

						// Trocar nome
						if (LTPUtils.recebeSouN("NOME: " + atual.nome + "\nTrocar (S/N)? ") == 'S') {
							novo.nome = LTPUtils.getStringUpperCase("Novo NOME: ");
						} else {
							novo.nome = atual.nome;
						}

						// Trocar telefone
						if (LTPUtils.recebeSouN("TELEFONE: " + atual.telefone + "\nTrocar (S/N)? ") == 'S') {
							novo.telefone = LTPUtils.getStringUpperCase("Novo TELEFONE: ");
						} else {
							novo.telefone = atual.telefone;
						}
						
						
						// Atualizar idade
						if (LTPUtils.recebeSouN("IDADE: " + atual.telefone + "\nATUALIZAR IDADE (S/N)? ") == 'S') {
						
							int idade = LTPUtils.getIntPositivo("Digite a idade atual: ");
							
							if(idade >=18) {
								novo.idade = idade;
							}else {
								System.err.println("Cadastro permitido para idade a partir de 18 anos");
							}
						} else {
							novo.idade = atual.idade;
						}

						novo.cpf = atual.cpf;

						// Se existe,  desativar o atual
						excluirPorCpf(cpf);

						novo.salvar();

						System.out.println("Cliente de cpf(" + cpf + "): alterado com sucesso!");
					
				} else {
					System.err.println("CPF não encontrado!");
				}
           }else {
        	   System.err.println("\nCPF INVÁLIDO! Digite um número de cpf válido");
           }
	}


	public void excluirPorCpf(String cpf) {
		try {
           
			if(isCpf(cpf)) {
			long pointer = getPointerClientePorCpf(cpf);

			if (pointer > -1) {
				
					RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");

					arq.seek(pointer);
					
					arq.writeChar('N');

					arq.close();

					System.out.println("Cliente de cpf(" + cpf + "): excluído com sucesso!");
				

			} else {
				System.err.println("Cpf não encontrado!");
			}
		}else {
			
		}
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
		
		
	}
	
	public static long getPointerClientePorCpf(String cpf) {

		long pointer = -1;

		try {
			RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");
			Cliente c = new Cliente();

			while (true) {
				pointer = arq.getFilePointer();
				c.ativo = arq.readChar();
				c.nome = arq.readUTF();	
				c.cpf = arq.readUTF();
				c.idade = arq.readInt();
				c.telefone = arq.readUTF();
				if (c.ativo == 'S' && c.cpf.equals(cpf)) {
					return pointer;
			}

		}
		} catch (EOFException e) {
			return -1;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
		return pointer;
	}


	public static void listarTodosClientes() {
		try {
			RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");
			Cliente c = new Cliente();

			while (true) {
				c.ativo = arq.readChar();
				c.nome = arq.readUTF();	
				c.cpf = arq.readUTF();
				c.idade = arq.readInt();
				c.telefone = arq.readUTF();
				if (c.ativo == 'S') {
					c.mostrarDadosCliente();
				}
			}

		} catch (EOFException e) {
			System.out.println("=====================================");
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
	}
		


	public void pesquisarPorNome(String nome) {
		getCliente(nome);
		mostrarDadosCliente();
		
	}


	public void BuscarClientePorCpf(String cpf) {
	
		Cliente cliente = Cliente.getClientePorCpf(cpf);
		if(cliente != null) {
			System.out.println("DADOS DO CLIENTE =========");
			System.out.println("Nome           : " + cliente.nome);
			System.out.println("Cpf            : " + cliente.cpf);
			System.out.println("Idade          : " + cliente.idade);
			System.out.println("Telefone       : " + cliente.telefone);
		}else {
			System.err.println("Não existe cliente cadastrado com o cpf informado. Digite outro cpf.");
			
		}
	}
	
	public void mostrarDadosCliente() {
		System.out.println("CLIENTE =========");
		System.out.println("Nome           : " + this.nome);
		System.out.println("Cpf            : " + this.cpf);
		System.out.println("Idade          : " + this.idade);
		System.out.println("Telefone       : " + this.telefone);
	}
	
	public void setCliente(String cpf, String nome, String telefone, int idade) {
		this.ativo = 'S';
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.idade = idade;
	}
	
	public void getCliente(String nome) {

		try {
			RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");
			Cliente c = new Cliente();

			while (true) {
				c.ativo = arq.readChar();
				c.nome = arq.readUTF();	
				c.cpf = arq.readUTF();
				c.idade = arq.readInt();
				c.telefone = arq.readUTF();
				
				if (c.ativo == 'S' && c.nome.equalsIgnoreCase(nome)) {
					this.setCliente(c.cpf, c.nome, c.telefone, c.idade);
					break;
				}
			}

			arq.close();

		} catch (EOFException e) {
			this.cpf = null;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
		
	}
	
	public static Cliente getClientePorCpf(String cpf) {

		try {
			RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");
			Cliente c = new Cliente();


			while (true) {
				c.ativo = arq.readChar();
				c.nome = arq.readUTF();	
				c.cpf = arq.readUTF();
				c.idade = arq.readInt();
				c.telefone = arq.readUTF();
				if (c.ativo == 'S' && c.cpf.equals(cpf)) {
					return c;
				}
			}

		} catch (EOFException e) {
			return null;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
		return null;
	}
	
	public void salvar() {
		try {
			RandomAccessFile arq = new RandomAccessFile(NOME_ARQUIVO, "rw");

			arq.seek(arq.length());
			arq.writeChar(this.ativo);
			arq.writeUTF(this.nome);
			arq.writeUTF(this.cpf);
			arq.writeInt(this.idade);
			arq.writeUTF(this.telefone);

			arq.close();

			System.out.println("Arquivo: " + NOME_ARQUIVO + " salvo com sucesso!");
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

	}
	
	    public  boolean isCpf(String cpf) {
	        // considera-se erro CPF's formados por uma sequencia de numeros iguais
	        if (cpf.equals("00000000000") ||
	            cpf.equals("11111111111") ||
	            cpf.equals("22222222222") || cpf.equals("33333333333") ||
	            cpf.equals("44444444444") || cpf.equals("55555555555") ||
	            cpf.equals("66666666666") || cpf.equals("77777777777") ||
	            cpf.equals("88888888888") || cpf.equals("99999999999") ||
	            (cpf.length() != 11))
	            return(false);
	          
	        char dig10, dig11;
	        int sm, i, r, num, peso;
	          
	        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
	        try {
	        // Calculo do 1o. Digito Verificador
	            sm = 0;
	            peso = 10;
	            for (i=0; i<9; i++) {              
	        // converte o i-esimo caractere do CPF em um numero:
	        // por exemplo, transforma o caractere '0' no inteiro 0         
	        // (48 eh a posicao de '0' na tabela ASCII)         
	            num = (int)(cpf.charAt(i) - 48); 
	            sm = sm + (num * peso);
	            peso = peso - 1;
	            }
	          
	            r = 11 - (sm % 11);
	            if ((r == 10) || (r == 11))
	                dig10 = '0';
	            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico
	          
	        // Calculo do 2o. Digito Verificador
	            sm = 0;
	            peso = 11;
	            for(i=0; i<10; i++) {
	            num = (int)(cpf.charAt(i) - 48);
	            sm = sm + (num * peso);
	            peso = peso - 1;
	            }
	          
	            r = 11 - (sm % 11);
	            if ((r == 10) || (r == 11))
	                 dig11 = '0';
	            else dig11 = (char)(r + 48);
	          
	        // Verifica se os digitos calculados conferem com os digitos informados.
	            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
	                 return(true);
	            else return(false);
	                } catch (InputMismatchException erro) {
	                return(false);
	            }
	    }


}
