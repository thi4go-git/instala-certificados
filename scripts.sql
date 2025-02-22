-- Tabela de configuração do sistema(Deverá possuir somente 1 registro)
create table configuracao_certificado (
  senha_master varchar not null,
  local_pasta varchar,
  senha_certificado varchar,
  user_email varchar,
  pass_email varchar,
  smtp_email varchar,
  smtp_port_email varchar,
  tls_email varchar,
  assunto_email varchar,
  mensagem_padrao_email varchar
);

-- Tabela configuracao_certificado:
insert into configuracao_certificado 
(senha_master) values ('sua senha aqui');

-- Tabela certificados
create table certificado (
  id serial primary key,
  nome varchar,
  alias varchar,
  dtVencimento date,
  hrVencimento varchar,
  descricao_vencimento varchar,
  expira INTEGER,
  imagemCertificado bytea not null 
);

-- Tabela contato certificado (Responsável pelo certificado)
create table contato_certificado (
  id serial primary key,
  id_certificado INTEGER REFERENCES certificado(id), 
  nome_contato varchar,
  telefone_contato varchar,
  celular_contato varchar,
  email_contato varchar,
  observacao varchar
);


-- Tabela de logs do sistema:
create table log_certificado (
  id serial primary key,
  tipo_log varchar not null,
  data_log TIMESTAMP not null,
  usuario varchar not null,
  ip_usuario varchar not null,
  detalhes varchar
);

