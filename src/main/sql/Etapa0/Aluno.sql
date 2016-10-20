use Etapa0;

IF( object_id('Aluno', 'U') IS NOT NULL) DROP TABLE Aluno;

CREATE TABLE Aluno(
	nAluno int NOT NULL,
	nome varchar(250) NOT NULL,
	Primary Key(nAluno)
);