package comum.model.messages;

/**
 * <p>
 * Classe responssável por conter em apenas um ponto as menssagens apresentada
 * em alertas em todo o sistema.
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public class Mensagens {

	public final static String LOGIN_ADMINISTRADOR_PADRAO = "HINOTORI";
	
	// --------------------------------------------------------------------------//
	// ************************** SYSTEMA ***************************************//
	// --------------------------------------------------------------------------//


	// --------------------------------------------------------------------------//
	// ************************** GERAL *****************************************//
	// --------------------------------------------------------------------------//
	public final static String ALERTA = "Alerta";
	public final static String CONCLUIDO = "Concluído";
	public final static String ERRO = "Erro";
	public final static String AVISO = "Aviso";
	
	public final static String CEP_INVALIDO = "Cep não encontrado.";
	public final static String CEP_SERVICO_INDISPONIVEL = "Serviço indisponível.";
	public final static String CEP_VAZIO = "Cep vazio.";
	public final static String CEP_VIACEP_ERRO = "Erro ao consultar o cep.";
	public final static String CEP_POSTMON_ERRO = "Erro ao consultar o cep.";

	// --------------------------------------------------------------------------//
	// ************************** BANCO DE DADOS ********************************//
	// --------------------------------------------------------------------------//
	// ERROS DE BANCO DE DADOS
	public final static String BD_ERRO_INSERT = "Erro ao inserir o novo registro.";
	public final static String BD_ERRO_UPDATE = "Erro ao atualizar o novo registro.";
	public final static String BD_ERRO_DELETE = "Erro ao apagar o novo registro.";
	public final static String BD_ERRO_SELECT = "Erro ao pesquisar o registro.";
	public final static String BD_ERRO_SELECT_ALL = "Erro ao carregar os registros.";
	public final static String BD_ERRO_SALVAR_IMAGEM = "Erro ao salvar a imagem.";
	public final static String BD_ERRO_CARREGAR_IMAGEM = "Erro ao carregar a imagens.";
	public final static String BD_ERRO_APAGAR_IMAGEM = "Erro ao apagar a imagem.";
	public final static String BD_ERRO_CARREGAR_ENDERECO = "Erro ao carregar o endereço.";
	public final static String BD_ERRO_SALVAR_ENDERECO = "Erro ao salvar o endereço.";
	public final static String BD_ERRO_CARREGAR_CONTATO = "Erro ao carregar o contato.";
	public final static String BD_ERRO_SALVAR_CONTATO = "Erro ao salvar o contato.";
	public final static String BD_ERRO_USR_VALIDAR_LOGIN = "Erro tentar validar o login.";

	// --------------------------------------------------------------------------//
	// ************************** LOGUIN ****************************************//
	// --------------------------------------------------------------------------//
	// ERROS AO TENTAR LOGAR NO SISTEMA
	public final static String LOGIN_ERRO_USER_LOGIN = "Necessário selecionar um usuário.";
	public final static String LOGIN_ERRO_USER_LOGIN_NAO_ENCONTRADO = "Login informado não encontrado.";
	public final static String LOGIN_ERRO_USER_SENHA = "Senha incorreta, favor informa-la novamente.";
	public final static String LOGIN_ERRO_USER_SENHA_VAZIA = "Necessário informar uma senha.";
	
	// --------------------------------------------------------------------------//
	// ************************** IMAGEM ****************************************//
	// --------------------------------------------------------------------------//
	public final static String IMG_SELECIONE = "Selecione uma imagem.";
	public final static String IMG_ERRO_CARREGAR = "Não foi possível carregar a imagem.";

	// --------------------------------------------------------------------------//
	// ************************** CADASTROS *************************************//
	// --------------------------------------------------------------------------//
	public final static String CADASTRO_SALVAR = "Não foi possivel salvar, alguns campos não conseguiram ser validados, favor verificar o cadastro.";
	public final static String CADASTRO_EXCLUIR = "Não foi possivel realizar a exclusão.";

	// ERROS DO CADASTRO DE USUARIO
	public final static String CAD_USR_NOME_VAZIO = "Nome não pode ser vazio.";
	public final static String CAD_USR_LOGIN_VAZIO = "Loguin não pode ser vazio.";
	public final static String CAD_USR_LOGIN_UTILLIZADO = "Loguin já cadastrado por outro usuário.";
	public final static String CAD_USR_SENHA_VAZIA = "Senha não pode ser vazia.";

	// ERROS DO CADASTRO DE CLIENTE
	public final static String CAD_CLI_NOME_VAZIO = "O NOME não pode ser vazio.";
	public final static String CAD_CLI_CPF = "O CPF informado é inválido.";
	public final static String CAD_CLI_CNPJ = "O CNPJ informado é inválido.";
	public final static String CAD_CLI_EXISTE_CPF = "O CPF informado já cadastrado para o cliente %s.\nFavor informar um CPF diferente.";
	public final static String CAD_CLI_EXISTE_CNPJ = "O CNPJ informado já cadastrado para o cliente %s.\nFavor informar um CNPJ diferente.";
	public final static String CAD_CLI_TIPO_CLIENTE_VAZIO = "Necessário selecionar um tipo de cliente.";
	public final static String CAD_RAZAO_SOCIAL_VAZIA = "Devido ao tipo do cliente ser JURÍDICO, necessário informar a razão social.";
	
	// ERROS DO CADASTRO DA EMPRESA
	public final static String CAD_EMP_RAZAO_VAZIO = "RAZÂO SOCIAL não pode ser vazio.";
	public final static String CAD_EMP_FANTASIA_VAZIO = "NOME FANTASIA não pode ser vazio.";

	// ERROS DO CADASTRO DO PRODUTO
	public final static String CAD_PROD_DESCRICAO_VAZIO = "Descrição do produto não pode ser vazio.";
	public final static String CAD_PROD_CODIGO_BARRAS = "O CÓDIGO DE BARRAS informado não é um código de barras EAN válido.";

	// ERROS DO CADASTRO DE CONTATO
	public final static String CAD_CONTATO_NOME_VAZIO = "O NOME do contato não pode ser vazio.";
	public final static String CAD_CONTATO_VAZIO = "Ao menos um tipo de contato deve ser informado.";
	public final static String CAD_CONTATO_TELEFONE = "O TELEFONE informado é inválido.";
	public final static String CAD_CONTATO_CELULAR = "O CELULAR informado é inválido.";
	public final static String CAD_CONTATO_EMAIL = "O EMAIL informado é inválido.";

	// ERROS DO CADASTRO DE ENDERECO
	public final static String CAD_ENDERECO_ENDERECO_VAZIO = "O ENDEREÇO não pode ser vazio.";
	public final static String CAD_ENDERECO_CEP = "O CEP informado não é valido.";
	public final static String CAD_ENDERECO_BAIRRO = "O BAIRRO não foi informado.";
	public final static String CAD_ENDERECO_CIDADE = "A CIDADE informada não foi encontrada.";
	
	// ERROS DO CADASTRO DE GRUPO E SUB GRUPO
	public final static String CAD_GRUPO_DESCRICAO_VAZIO = "A descrição não pode ser vazio.";
	
	
	

}
