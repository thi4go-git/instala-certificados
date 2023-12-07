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
-> 1 passo criar essa tabela:

create table configuracao_certificado (
senha_master varchar not null,
local_pasta varchar not null,
senha_certificado varchar not null
);

-> 2 passo preencher essa tabela:

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

