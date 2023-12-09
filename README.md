############################### VARIÁVEIS DE AMBIENTE ##############################
Para executar o projeto é necessário ter um arquivo .env na raiz
do projeto.

Exemplo de conteúdo do arquivo .env:
URL_BANCO=jdbc:postgresql://meuIp:porta/bancoNome
USUARIO_BANCO=user
SENHA_BANCO=pass
VERSION=Version 1.0
INFO_RODAPE=By Thiago Jr. 62-981204102


############################## BANCO DE DADOS ##############################
-> 1 passo criar tabela configuracao_certificado:

create table configuracao_certificado (
senha_master varchar not null,
local_pasta varchar not null,
senha_certificado varchar not null
);

-> 2 passo preencher tabela configuracao_certificado:

insert into configuracao_certificado 
(senha_master,local_pasta,senha_certificado)
values ('senha','C:\Users\Thiago\Desktop\CERTIFICADOS','senha');

-> 3 passo criar table certificados:

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

-> 4 passo criar table contato_certificado:

create table contato_certificado (
id serial primary key,
id_certificado INTEGER,
nome_contato varchar,
telefone_contato varchar,
celular_contato varchar,
email_contato varchar,
observacao varchar
);
