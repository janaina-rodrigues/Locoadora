

public class Principal {
	private static String menuHome[] = { "Filme", "Locacao", "Cliente" };
	private static String menuFilmes[] = { "Cadastrar filme", "Alterar", "Excluir", "Listar", "Pesquisar Pelo Nome","Pesquisar por genero" };
	private static String menuCliente[] = { "Cadastrar cliente", "Alterar", "Excluir", "Listar", "Pesquisar Pelo Nome","Pesquisar por cpf" };
	private static String menuLocacao[] = { "Listar filmes por Genero", "Alugar", "RenovarLocacao" };

	public static void main(String[] args) {

		menuLocadora();
	}

	private static void menuLocadora() {
		do {

			switch (LTPUtils.menuOptions("LOCADORA DE FILMES", menuHome)) {
			case 1:
				menuFilme();
				break;
			case 2:
				menuLocacao();
				break;
			case 3:
				menucliente();
				break;
			case 0:
				return;
			}
		} while (true);

	}

	private static void menucliente() {
		Cliente cliente = new Cliente();
		do {

			switch (LTPUtils.menuOptions("GESTÃO DE CLIENTES", menuCliente)) {
			case 1:
				cliente.cadastrar();
				break;
			case 2:
				Cliente.listarTodosClientes();
				cliente.alterarPorCpf(LTPUtils.getString("Informe o CPF do Cliente que deseja alterar: "));
				break;
			case 3:
				cliente.excluirPorCpf(LTPUtils.getString("Informe o CPF do Cliente que deseja excluir: "));
				break;
			case 4:
				Cliente.listarTodosClientes();
				break;
			case 5:
				cliente.pesquisarPorNome(LTPUtils.getString("Informe o Nome do Cliente que deseja buscar: "));
				break;
			case 6:
				cliente.BuscarClientePorCpf(LTPUtils.getString("Informe o CPF do Cliente que deseja buscar: "));
				break;
			case 0:
				return;
			}
		} while (true);

	}

	private static void menuFilme() {
		Filme filme = new Filme();
		do {

			switch (LTPUtils.menuOptions("GESTÃO DE FILMES", menuFilmes)) {
			case 1:
				filme.cadastrar();
				break;
			case 2:
				Filme.listarTodosFilmes();
				Filme.alterarPorId(LTPUtils.getIntPositivo("Informe o id do Filme que deseja alterar: "));
				break;
			case 3:
				Filme.listarTodosFilmes();
				Filme.excluirPorId(LTPUtils.getIntPositivo("Informe o id do Filme que deseja alterar: "));
				break;
			case 4:
				Filme.listarTodosFilmes();
				break;
			case 5:
				filme.pesquisarPorNome(LTPUtils.getString("Informe o Nome do Filme que deseja buscar: "));
				break;
			case 6:
				filme.pesquisarPorGenero(LTPUtils.getString("Informe o Genero do filme que deseja buscar: "));
				break;
			case 0:
				return;
			}
		} while (true);

	}

	private static void menuLocacao() {

		Locacao locacao = new Locacao();

		do {

			switch (LTPUtils.menuOptions("LOCAÇÃO DE FILMES", menuLocacao)) {
			case 1:
				locacao.listarFilmePorGenero();
				break;
			case 2:
				locacao.alugar();
				break;
			case 3:
				locacao.renovar();
				break;
			case 0:
				return;
			}
		} while (true);

	}
}
