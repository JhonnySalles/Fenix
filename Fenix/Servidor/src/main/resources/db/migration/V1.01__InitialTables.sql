CREATE TABLE paises (
  Id bigint(20) NOT NULL AUTO_INCREMENT,
  Nome varchar(150) DEFAULT NULL,
  Situacao enum('ATIVO','INATIVO','EXCLUÍDO') DEFAULT 'ATIVO',
  PRIMARY KEY (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE estados (
  Id bigint(20) NOT NULL AUTO_INCREMENT,
  CodigoIBGE int(3) DEFAULT NULL,
  Nome varchar(150) DEFAULT NULL,
  Sigla varchar(2) DEFAULT NULL,
  Situacao enum('ATIVO','INATIVO','EXCLUÍDO') DEFAULT 'ATIVO',
  IdPais bigint(20) NOT NULL,
  PRIMARY KEY (Id),
  KEY FK_ESTADO_PAIS (IdPais),
  CONSTRAINT FK_ESTADO_PAIS FOREIGN KEY (IdPais) REFERENCES paises (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE cidades (
  Id bigint(20) NOT NULL AUTO_INCREMENT,
  Ddd varchar(3) DEFAULT NULL,
  Nome varchar(150) DEFAULT NULL,
  Situacao enum('ATIVO','INATIVO','EXCLUÍDO') DEFAULT 'ATIVO',
  IdEstado bigint(20) NOT NULL,
  PRIMARY KEY (Id),
  KEY FK_CIDADE_ESTADO (IdEstado),
  CONSTRAINT FK_CIDADE_ESTADO FOREIGN KEY (IdEstado) REFERENCES estados (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE bairros (
  Id bigint(20) NOT NULL AUTO_INCREMENT,
  Nome varchar(150) DEFAULT NULL,
  IdCidade bigint(20) NOT NULL,
  Situacao enum('ATIVO','INATIVO','EXCLUÍDO') DEFAULT 'ATIVO',
  PRIMARY KEY (Id),
  KEY FK_BAIRRO_CIDADE (IdCidade),
  CONSTRAINT FK_BAIRRO_CIDADE FOREIGN KEY (IdCidade) REFERENCES cidades (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE empresas (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  CNPJ varchar(255) DEFAULT NULL,
  DataCadastro datetime DEFAULT NULL,
  NomeFantasia varchar(255) DEFAULT NULL,
  RazaoSocial varchar(255) DEFAULT NULL,
  Situacao enum('ATIVO','INATIVO','EXCLUIDO') DEFAULT 'ATIVO',
  IdBairro bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_EMPRESA_BAIRRO (IdBairro),
  CONSTRAINT FK_EMPRESA_BAIRRO FOREIGN KEY (IdBairro) REFERENCES bairros (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE empresas_imagens (
  Id bigint(20) NOT NULL AUTO_INCREMENT,
  caminho longtext,
  Extenssao varchar(10) DEFAULT NULL,
  Imagem longblob,
  Nome varchar(250) DEFAULT NULL,
  Tamanho enum('ORIGINAL','PEQUENA','MEDIA') DEFAULT NULL,
  IdEmpresa bigint(20) DEFAULT NULL,
  PRIMARY KEY (Id),
  KEY FK_EMPRESA_IMAGEM (IdEmpresa),
  CONSTRAINT FK_EMPRESA_IMAGEM FOREIGN KEY (IdEmpresa) REFERENCES empresas (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE usuarios (
  Id bigint(20) NOT NULL AUTO_INCREMENT,
  DataCadastro datetime DEFAULT NULL,
  DataUltimaAlteracao datetime DEFAULT NULL,
  NomeSobrenome varchar(250) DEFAULT NULL,
  Situacao enum('ATIVO','INATIVO','EXCLUIDO') DEFAULT 'ATIVO',
  Login char(20) DEFAULT NULL,
  Nivel enum('USUARIO','ADMINISTRADOR','TOTAL') DEFAULT NULL,
  Observacao longtext,
  Senha varchar(250) DEFAULT NULL,
  PRIMARY KEY (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO usuarios ( Id, Login, NomeSobrenome, Senha, DataCadastro, Nivel, Situacao )
VALUES ( 0, "ADMINISTRATOR", "ADMINISTRATOR", "6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B", NOW(), "TOTAL", "ATIVO");

CREATE TABLE usuarios_imagens (
  Id bigint(20) NOT NULL AUTO_INCREMENT,
  caminho longtext,
  Extenssao varchar(10) DEFAULT NULL,
  Imagem longblob,
  Nome varchar(250) DEFAULT NULL,
  Tamanho enum('ORIGINAL','PEQUENA','MEDIA') DEFAULT NULL,
  IdUsuario bigint(20) DEFAULT NULL,
  PRIMARY KEY (Id),
  KEY FK_USUARIO_IMAGEM (IdUsuario),
  CONSTRAINT FK_USUARIO_IMAGEM FOREIGN KEY (IdUsuario) REFERENCES usuarios (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE clientes (
  Id bigint(20) NOT NULL AUTO_INCREMENT,
  DataCadastro datetime DEFAULT NULL,
  DataUltimaAlteracao datetime DEFAULT NULL,
  NomeSobrenome varchar(250) DEFAULT NULL,
  Situacao enum('ATIVO','INATIVO','EXCLUIDO') DEFAULT 'ATIVO',
  CNPJ varchar(15) DEFAULT NULL,
  CPF varchar(15) DEFAULT NULL,
  Enquadramento enum('CLIENTE','FORNECEDOR','AMBOS') DEFAULT NULL,
  Observacao longtext,
  RazaoSocial varchar(255) DEFAULT NULL,
  Tipo enum('FISICO','JURIDICO','AMBOS') DEFAULT NULL,
  UNIQUE KEY UK_CLIENTE_CNPJ (CNPJ),
  UNIQUE KEY UK_CLIENTE_CPF (CPF),
  PRIMARY KEY (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE enderecos (
  Id bigint(20) NOT NULL AUTO_INCREMENT,
  Endereco varchar(150) DEFAULT NULL,
  Numero varchar(10) DEFAULT NULL,
  CEP varchar(10) DEFAULT NULL,
  Complemento varchar(150) DEFAULT NULL,
  Observacao longtext,
  DataCadastro datetime DEFAULT NULL,
  Padrao tinyint(1) DEFAULT NULL,
  Situacao enum('ATIVO','INATIVO','EXCLUIDO') DEFAULT 'ATIVO',
  Tipo enum('RESIDENCIAL','COMERCIAL','COBRANCA','ENTREGA','OUTROS') DEFAULT NULL,
  IdBairro bigint(20) DEFAULT NULL,
  IdCliente bigint(20) DEFAULT NULL,
  IdEmpresa bigint(20) DEFAULT NULL,
  PRIMARY KEY (Id),
  KEY FK_BAIRRO_ENDERECO (IdBairro),
  KEY FK_CLIENTE_ENDERECO (IdCliente),
  KEY FK_EMPRESA_ENDERECO (IdEmpresa),
  CONSTRAINT FK_CLIENTE_ENDERECO FOREIGN KEY (IdCliente) REFERENCES clientes (Id),
  CONSTRAINT FK_EMPRESA_ENDERECO FOREIGN KEY (IdEmpresa) REFERENCES empresas (id),
  CONSTRAINT FK_BAIRRO_ENDERECO FOREIGN KEY (IdBairro) REFERENCES bairros (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE contatos (
  Id bigint(20) NOT NULL AUTO_INCREMENT,
  DataCadastro datetime DEFAULT NULL,
  DataUltimaAlteracao datetime DEFAULT NULL,
  NomeSobrenome varchar(250) DEFAULT NULL,
  Situacao enum('ATIVO','INATIVO','EXCLUIDO') DEFAULT 'ATIVO',
  Celular varchar(15) DEFAULT NULL,
  Email varchar(250) DEFAULT NULL,
  Observacao longtext,
  Padrao tinyint(1) DEFAULT NULL,
  Telefone varchar(15) DEFAULT NULL,
  Tipo enum('RESIDENCIAL','COMERCIAL') DEFAULT NULL,
  IdUsuario bigint(20) DEFAULT NULL,
  IdCliente bigint(20) DEFAULT NULL,
  IdEmpresa bigint(20) DEFAULT NULL,
  PRIMARY KEY (Id),
  KEY FK_USUARIO_CONTATO (IdUsuario),
  KEY FK_CLIENTE_CONTATO (IdCliente),
  KEY FK_EMPRESA_CONTATO (IdEmpresa),
  CONSTRAINT FK_CLIENTE_CONTATO FOREIGN KEY (IdCliente) REFERENCES clientes (Id),
  CONSTRAINT FK_EMPRESA_CONTATO FOREIGN KEY (IdEmpresa) REFERENCES empresas (id),
  CONSTRAINT FK_USUARIO_CONTATO FOREIGN KEY (IdUsuario) REFERENCES usuarios (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE ncm (
  Id bigint(20) NOT NULL AUTO_INCREMENT,
  ncm varchar(8) NOT NULL,
  Descricao longtext,
  Situacao enum('ATIVO','INATIVO','EXCLUÍDO') DEFAULT 'ATIVO',
  PRIMARY KEY (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE produtos (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  CodigoBarras varchar(35) DEFAULT NULL,
  DataCadastro datetime DEFAULT NULL,
  DataUltimaAlteracao datetime DEFAULT NULL,
  Descricao varchar(250) NOT NULL,
  idGrupo bigint(20) DEFAULT NULL,
  Marca varchar(250) DEFAULT NULL,
  Observacao longtext,
  Peso double DEFAULT NULL,
  Situacao enum('ATIVO','INATIVO','EXCLUIDO') DEFAULT 'ATIVO',
  TipoProduto enum('PRODUZIDO','MATERIAPRIMA','SERVICO','PRODUTOFINAL') DEFAULT NULL,
  Unidade varchar(4) DEFAULT NULL,
  Volume double DEFAULT NULL,
  idNcm bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_PRODUTO_NCM (idNcm),
  CONSTRAINT FK_PRODUTO_NCM FOREIGN KEY (idNcm) REFERENCES ncm (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE produtos_imagens (
  Id bigint(20) NOT NULL AUTO_INCREMENT,
  caminho longtext,
  Extenssao varchar(10) DEFAULT NULL,
  Imagem longblob,
  Nome varchar(250) DEFAULT NULL,
  Tamanho enum('ORIGINAL','PEQUENA','MEDIA') DEFAULT NULL,
  IdProduto bigint(20) DEFAULT NULL,
  PRIMARY KEY (Id),
  KEY FK_PRODUTO_IMAGEM (IdProduto),
  CONSTRAINT FK_PRODUTO_IMAGEM FOREIGN KEY (IdProduto) REFERENCES produtos (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE grupos (
  Id bigint(20) NOT NULL AUTO_INCREMENT,
  Cor varchar(10) DEFAULT NULL,
  Descricao varchar(250) DEFAULT NULL,
  Situacao enum('ATIVO','INATIVO','EXCLUÍDO') DEFAULT 'ATIVO',
  PRIMARY KEY (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE sub_grupos (
  Id bigint(20) NOT NULL AUTO_INCREMENT,
  Cor varchar(10) DEFAULT NULL,
  Descricao varchar(250) DEFAULT NULL,
  Situacao enum('ATIVO','INATIVO','EXCLUÍDO') DEFAULT NULL,
  PRIMARY KEY (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE grupo_subgrupo_imagens (
  Id bigint(20) NOT NULL AUTO_INCREMENT,
  caminho longtext,
  Extenssao varchar(10) DEFAULT NULL,
  Imagem longblob,
  Nome varchar(250) DEFAULT NULL,
  Tamanho enum('ORIGINAL','PEQUENA','MEDIA') DEFAULT NULL,
  IdSubGrupo bigint(20) DEFAULT NULL,
  IdGrupo bigint(20) DEFAULT NULL,
  PRIMARY KEY (Id),
  KEY FK_SUBGRUPO_IMAGEM (IdSubGrupo),
  KEY FK_GRUPO_IMAGEM (IdGrupo),
  CONSTRAINT FK_GRUPO_IMAGEM FOREIGN KEY (IdGrupo) REFERENCES grupos (Id),
  CONSTRAINT FK_SUBGRUPO_IMAGEM FOREIGN KEY (IdSubGrupo) REFERENCES sub_grupos (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE grupo_subgrupos (
  idGrupo bigint(20) NOT NULL,
  idSubGrupo bigint(20) NOT NULL,
  PRIMARY KEY (idGrupo,idSubGrupo),
  KEY FK_GRUPO_GRUPOSUBGRUPO (idGrupo),
  KEY FK_SUBGRUPO_GRUPOSUBGRUPO (idSubGrupo),
  CONSTRAINT FK_GRUPOSUBGRUPO_GRUPO FOREIGN KEY (IdGrupo) REFERENCES grupos (Id),
  CONSTRAINT FK_GRUPOSUBGRUPO_SUBGRUPO FOREIGN KEY (idSubGrupo) REFERENCES sub_grupos (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
