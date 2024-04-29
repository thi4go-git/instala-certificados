############################### VARIÁVEIS DE AMBIENTE #############################
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

-> 2 passo inserir/definir a senha master via SQL. 
Tabela configuracao_certificado:

insert into configuracao_certificado 
(senha_master) values ('sua senha aqui');

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
id_certificado INTEGER REFERENCES certificado(id), 
nome_contato varchar,
telefone_contato varchar,
celular_contato varchar,
email_contato varchar,
observacao varchar
);


############################## MENSAGEM PADRÃO EMAIL ##############################
Prezado cliente,

Esperamos que esta mensagem o encontre bem. Gostaríamos de lembrar sobre a 
renovação do seu certificado contábil, cujo prazo está próximo de expirar.
Garantir a atualização é essencial para manter a conformidade legal e assegurar a 
continuidade de seus serviços contábeis sem interrupções.

Ficamos à disposição para qualquer dúvida ou assistência necessária durante esse processo. 
Agradecemos pela confiança em nossos serviços e aguardamos sua pronta ação.

Atenciosamente,
[Nome da Empresa de Contabilidade]
