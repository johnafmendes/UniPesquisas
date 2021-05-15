-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema unipesquisas
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema unipesquisas
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `unipesquisas` DEFAULT CHARACTER SET utf8 ;
USE `unipesquisas` ;

-- -----------------------------------------------------
-- Table `unipesquisas`.`empresa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`empresa` (
  `idempresa` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `endereco` VARCHAR(255) NULL,
  `bairro` VARCHAR(45) NULL,
  `cidade` VARCHAR(45) NULL,
  `estado` VARCHAR(45) NULL,
  `cep` VARCHAR(45) NULL,
  `site` VARCHAR(255) NULL,
  `email` VARCHAR(255) NULL,
  `ddd` VARCHAR(45) NULL,
  `telefone` VARCHAR(45) NULL,
  `responsavel` VARCHAR(45) NULL,
  `logomarca` VARCHAR(255) NULL,
  `status` INT NOT NULL,
  `arquivo` VARCHAR(255) NULL,
  PRIMARY KEY (`idempresa`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`areacurso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`areacurso` (
  `idareacurso` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `area` VARCHAR(255) NOT NULL,
  `idempresa` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idareacurso`),
  INDEX `fk_areacurso_idempresa_idx` (`idempresa` ASC) VISIBLE,
  CONSTRAINT `fk_areacurso_idempresa`
    FOREIGN KEY (`idempresa`)
    REFERENCES `unipesquisas`.`empresa` (`idempresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`escolaridade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`escolaridade` (
  `idescolaridade` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `escolaridade` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idescolaridade`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`candidato`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`candidato` (
  `idcandidato` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `datanascimento` DATE NOT NULL,
  `dddres` VARCHAR(45) NULL,
  `telefoneres` VARCHAR(45) NULL,
  `dddcel` VARCHAR(45) NULL,
  `telefonecel` VARCHAR(45) NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(45) NULL,
  `idescolaridade` INT UNSIGNED NOT NULL,
  `sexo` TINYINT NOT NULL,
  PRIMARY KEY (`idcandidato`),
  INDEX `fk_candidato_idescolaridade_idx` (`idescolaridade` ASC) VISIBLE,
  CONSTRAINT `fk_candidato_idescolaridade`
    FOREIGN KEY (`idescolaridade`)
    REFERENCES `unipesquisas`.`escolaridade` (`idescolaridade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`instituicao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`instituicao` (
  `idinstituicao` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `instituicao` VARCHAR(255) NOT NULL,
  `endereco` VARCHAR(255) NULL,
  `bairro` VARCHAR(45) NULL,
  `cidade` VARCHAR(45) NULL,
  `estado` VARCHAR(45) NULL,
  `cep` VARCHAR(45) NULL,
  `diretor` VARCHAR(45) NULL,
  `dddtel` VARCHAR(45) NULL,
  `telefone` VARCHAR(45) NULL,
  `dddcel` VARCHAR(45) NULL,
  `telefonecel` VARCHAR(45) NULL,
  `email` VARCHAR(255) NULL,
  `idempresa` INT UNSIGNED NOT NULL,
  `sigla` VARCHAR(45) NULL,
  PRIMARY KEY (`idinstituicao`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`curso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`curso` (
  `idcurso` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `curso` VARCHAR(255) NOT NULL,
  `idarea` INT UNSIGNED NOT NULL,
  `idinstituicao` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idcurso`),
  INDEX `fk_curso_idarea_idx` (`idarea` ASC) VISIBLE,
  INDEX `fk_curso_idinstituicao_idx` (`idinstituicao` ASC) VISIBLE,
  CONSTRAINT `fk_curso_idarea`
    FOREIGN KEY (`idarea`)
    REFERENCES `unipesquisas`.`areacurso` (`idareacurso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_curso_idinstituicao`
    FOREIGN KEY (`idinstituicao`)
    REFERENCES `unipesquisas`.`instituicao` (`idinstituicao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`pergunta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`pergunta` (
  `idpergunta` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `pergunta` TEXT NOT NULL,
  `alta` TEXT NOT NULL,
  `altb` TEXT NOT NULL,
  `altc` TEXT NOT NULL,
  `altd` TEXT NOT NULL,
  `alte` TEXT NOT NULL,
  `idempresa` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idpergunta`),
  INDEX `fk_pergunta_idempresa_idx` (`idempresa` ASC) VISIBLE,
  CONSTRAINT `fk_pergunta_idempresa`
    FOREIGN KEY (`idempresa`)
    REFERENCES `unipesquisas`.`empresa` (`idempresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '		';


-- -----------------------------------------------------
-- Table `unipesquisas`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`usuario` (
  `idusuario` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `idempresa` INT UNSIGNED NOT NULL,
  `status` TINYINT NOT NULL,
  PRIMARY KEY (`idusuario`),
  INDEX `fk_usuario_idempresa_idx` (`idempresa` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_idempresa`
    FOREIGN KEY (`idempresa`)
    REFERENCES `unipesquisas`.`empresa` (`idempresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`pesquisa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`pesquisa` (
  `idpesquisa` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `titulo` TEXT NOT NULL,
  `descricao` TEXT NOT NULL,
  `data` DATE NOT NULL,
  `idusuario` INT UNSIGNED NOT NULL,
  `idempresa` INT UNSIGNED NOT NULL,
  `status` TINYINT NOT NULL,
  PRIMARY KEY (`idpesquisa`),
  INDEX `fk_pesquisa_idemmpresa_idx` (`idempresa` ASC) VISIBLE,
  INDEX `fk_pesquisa_idusuario_idx` (`idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_pesquisa_idemmpresa`
    FOREIGN KEY (`idempresa`)
    REFERENCES `unipesquisas`.`empresa` (`idempresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pesquisa_idusuario`
    FOREIGN KEY (`idusuario`)
    REFERENCES `unipesquisas`.`usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`progresso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`progresso` (
  `idprogressodetalhado` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `observacao` TEXT NOT NULL,
  `idcandidato` INT UNSIGNED NOT NULL,
  `idusuario` INT UNSIGNED NOT NULL,
  `idempresa` INT UNSIGNED NOT NULL,
  `data` DATE NOT NULL,
  PRIMARY KEY (`idprogressodetalhado`),
  INDEX `fk_progresso_idempresa_idx` (`idempresa` ASC) VISIBLE,
  INDEX `fk_progresso_idcandidato_idx` (`idcandidato` ASC) VISIBLE,
  INDEX `fk_progresso_idusuario_idx` (`idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_progresso_idempresa`
    FOREIGN KEY (`idempresa`)
    REFERENCES `unipesquisas`.`empresa` (`idempresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_progresso_idcandidato`
    FOREIGN KEY (`idcandidato`)
    REFERENCES `unipesquisas`.`candidato` (`idcandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_progresso_idusuario`
    FOREIGN KEY (`idusuario`)
    REFERENCES `unipesquisas`.`usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`smsmarketing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`smsmarketing` (
  `idsmsmarketing` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `assunto` TEXT NOT NULL,
  `mensagem` TEXT NOT NULL,
  `data` DATE NOT NULL,
  `idusuario` INT UNSIGNED NOT NULL,
  `idempresa` INT UNSIGNED NOT NULL,
  `status` TINYINT NOT NULL,
  PRIMARY KEY (`idsmsmarketing`),
  INDEX `fk_smsmarketing_idempresa_idx` (`idempresa` ASC) VISIBLE,
  INDEX `fk_smsmarketing_idusuario_idx` (`idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_smsmarketing_idempresa`
    FOREIGN KEY (`idempresa`)
    REFERENCES `unipesquisas`.`empresa` (`idempresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_smsmarketing_idusuario`
    FOREIGN KEY (`idusuario`)
    REFERENCES `unipesquisas`.`usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`candidatocurso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`candidatocurso` (
  `idcandidatocurso` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idcandidato` INT UNSIGNED NOT NULL,
  `idcurso` INT UNSIGNED NOT NULL,
  `matriculado` TINYINT NOT NULL,
  `ordem` INT NULL,
  `confirmado` INT NULL,
  `outrocurso` TEXT NULL,
  PRIMARY KEY (`idcandidatocurso`),
  INDEX `fk_candidatocurso_idcandidato_idx` (`idcandidato` ASC) VISIBLE,
  INDEX `fk_candidatocurso_idcurso_idx` (`idcurso` ASC) VISIBLE,
  CONSTRAINT `fk_candidatocurso_idcandidato`
    FOREIGN KEY (`idcandidato`)
    REFERENCES `unipesquisas`.`candidato` (`idcandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_candidatocurso_idcurso`
    FOREIGN KEY (`idcurso`)
    REFERENCES `unipesquisas`.`curso` (`idcurso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`statuscandidato`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`statuscandidato` (
  `idstatuscandidato` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `status` TEXT NULL,
  `idempresa` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idstatuscandidato`),
  INDEX `fk_statuscandidato_idempresa_idx` (`idempresa` ASC) VISIBLE,
  CONSTRAINT `fk_statuscandidato_idempresa`
    FOREIGN KEY (`idempresa`)
    REFERENCES `unipesquisas`.`empresa` (`idempresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`candidatoempresa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`candidatoempresa` (
  `idcandidatoempresa` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idcandidato` INT UNSIGNED NOT NULL,
  `idempresa` INT UNSIGNED NOT NULL,
  `receberemail` TINYINT NULL,
  `recebersms` TINYINT NULL,
  `codigo` VARCHAR(45) NULL,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idcandidatoempresa`),
  INDEX `fk_candidatoempresa_idempresa_idx` (`idempresa` ASC) VISIBLE,
  INDEX `fk_candidatoempresa_idcandidato_idx` (`idcandidato` ASC) VISIBLE,
  INDEX `fk_candidatoempresa_idstatuscandidato_idx` (`idstatuscandidato` ASC) VISIBLE,
  CONSTRAINT `fk_candidatoempresa_idempresa`
    FOREIGN KEY (`idempresa`)
    REFERENCES `unipesquisas`.`empresa` (`idempresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_candidatoempresa_idcandidato`
    FOREIGN KEY (`idcandidato`)
    REFERENCES `unipesquisas`.`candidato` (`idcandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_candidatoempresa_idstatuscandidato`
    FOREIGN KEY (`idstatuscandidato`)
    REFERENCES `unipesquisas`.`statuscandidato` (`idstatuscandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`candidatoinstituicao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`candidatoinstituicao` (
  `idcandidatoinstituicao` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idcandidato` INT UNSIGNED NOT NULL,
  `idinstituicao` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idcandidatoinstituicao`),
  INDEX `fk_candidatoinstituicao_idcandidato_idx` (`idcandidato` ASC) VISIBLE,
  INDEX `fk_candidatoinstituicao_idinstituicao_idx` (`idinstituicao` ASC) VISIBLE,
  CONSTRAINT `fk_candidatoinstituicao_idcandidato`
    FOREIGN KEY (`idcandidato`)
    REFERENCES `unipesquisas`.`candidato` (`idcandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_candidatoinstituicao_idinstituicao`
    FOREIGN KEY (`idinstituicao`)
    REFERENCES `unipesquisas`.`instituicao` (`idinstituicao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`candidatopesquisacurso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`candidatopesquisacurso` (
  `idcandidatopesquisacurso` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idcandidato` INT UNSIGNED NOT NULL,
  `idpesquisacurso` INT UNSIGNED NOT NULL,
  `status` TINYINT NOT NULL,
  PRIMARY KEY (`idcandidatopesquisacurso`),
  INDEX `fk_candidatopesquisacurso_idcandidato_idx` (`idcandidato` ASC) VISIBLE,
  CONSTRAINT `fk_candidatopesquisacurso_idcandidato`
    FOREIGN KEY (`idcandidato`)
    REFERENCES `unipesquisas`.`candidato` (`idcandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`candidatopesquisaescolaridade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`candidatopesquisaescolaridade` (
  `idcandidatopesquisaescolaridade` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idcandidato` INT UNSIGNED NOT NULL,
  `idpesquisaescolaridade` INT UNSIGNED NOT NULL,
  `status` TINYINT NOT NULL,
  PRIMARY KEY (`idcandidatopesquisaescolaridade`),
  INDEX `fk_candidatopesquisaescolaridade_idcandidato_idx` (`idcandidato` ASC) VISIBLE,
  CONSTRAINT `fk_candidatopesquisaescolaridade_idcandidato`
    FOREIGN KEY (`idcandidato`)
    REFERENCES `unipesquisas`.`candidato` (`idcandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`candidatopesquisainstituicao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`candidatopesquisainstituicao` (
  `idcandidatopesquisainstituicao` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idcandidato` INT UNSIGNED NOT NULL,
  `idpesquisainstituicao` INT UNSIGNED NOT NULL,
  `status` TINYINT NOT NULL,
  PRIMARY KEY (`idcandidatopesquisainstituicao`),
  INDEX `fk_candidatopesquisainstituicao_idcandidato_idx` (`idcandidato` ASC) VISIBLE,
  CONSTRAINT `fk_candidatopesquisainstituicao_idcandidato`
    FOREIGN KEY (`idcandidato`)
    REFERENCES `unipesquisas`.`candidato` (`idcandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`configuracoesempresa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`configuracoesempresa` (
  `idconfiguracoesempresa` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  `idempresa` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idconfiguracoesempresa`),
  INDEX `fk_configuracoesempresa_idempresa_idx` (`idempresa` ASC) VISIBLE,
  CONSTRAINT `fk_configuracoesempresa_idempresa`
    FOREIGN KEY (`idempresa`)
    REFERENCES `unipesquisas`.`empresa` (`idempresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`configuracoessistema`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`configuracoessistema` (
  `idconfiguracoessistema` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `emailsmtp` VARCHAR(255) NULL,
  `emailfrom` VARCHAR(255) NULL,
  `emaillogin` VARCHAR(255) NULL,
  `emailsenha` VARCHAR(45) NULL,
  `emailsmtpporta` VARCHAR(45) NULL,
  `status` TINYINT NULL,
  `emailssl` INT NULL,
  `emailtls` INT NULL,
  `emailautenticacaosmtp` INT NULL,
  PRIMARY KEY (`idconfiguracoessistema`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`emailmarketing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`emailmarketing` (
  `idemailmarketing` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `assunto` TEXT NOT NULL,
  `mensagem` TEXT NOT NULL,
  `data` DATE NOT NULL,
  `idusuario` INT UNSIGNED NOT NULL,
  `idempresa` INT UNSIGNED NOT NULL,
  `status` TINYINT NOT NULL,
  PRIMARY KEY (`idemailmarketing`),
  INDEX `fk_emailmarketing_idusuario_idx` (`idusuario` ASC) VISIBLE,
  INDEX `fk_emailmarketing_idempresa_idx` (`idempresa` ASC) VISIBLE,
  CONSTRAINT `fk_emailmarketing_idusuario`
    FOREIGN KEY (`idusuario`)
    REFERENCES `unipesquisas`.`usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_emailmarketing_idempresa`
    FOREIGN KEY (`idempresa`)
    REFERENCES `unipesquisas`.`empresa` (`idempresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`emailmarketingalternativa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`emailmarketingalternativa` (
  `idemailmarketingalternativa` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idemailmarketing` INT UNSIGNED NOT NULL,
  `idpergunta` INT UNSIGNED NOT NULL,
  `alternativa` TEXT NULL,
  `idpesquisa` INT UNSIGNED NOT NULL,
  `data` DATE NULL,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idemailmarketingalternativa`),
  INDEX `fk_emailmarketingalternativa_idpesquisa_idx` (`idpesquisa` ASC) VISIBLE,
  INDEX `fk_emailmarketingalternativa_idpergunta_idx` (`idpergunta` ASC) VISIBLE,
  INDEX `fk_emailmarketingalternativa_idemailmarketing_idx` (`idemailmarketing` ASC) VISIBLE,
  INDEX `fk_emailmarketingalternativa_idstatuscandidato_idx` (`idstatuscandidato` ASC) VISIBLE,
  CONSTRAINT `fk_emailmarketingalternativa_idpesquisa`
    FOREIGN KEY (`idpesquisa`)
    REFERENCES `unipesquisas`.`pesquisa` (`idpesquisa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_emailmarketingalternativa_idpergunta`
    FOREIGN KEY (`idpergunta`)
    REFERENCES `unipesquisas`.`pergunta` (`idpergunta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_emailmarketingalternativa_idemailmarketing`
    FOREIGN KEY (`idemailmarketing`)
    REFERENCES `unipesquisas`.`emailmarketing` (`idemailmarketing`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_emailmarketingalternativa_idstatuscandidato`
    FOREIGN KEY (`idstatuscandidato`)
    REFERENCES `unipesquisas`.`statuscandidato` (`idstatuscandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`emailmarketingcurso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`emailmarketingcurso` (
  `idemailmarketingcurso` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idemailmarketing` INT UNSIGNED NOT NULL,
  `idcurso` INT UNSIGNED NOT NULL,
  `data` DATE NOT NULL,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idemailmarketingcurso`),
  INDEX `fk_emailmarketingcurso_idcurso_idx` (`idcurso` ASC) VISIBLE,
  INDEX `fk_emailmarketingcurso_idemailmarketing_idx` (`idemailmarketing` ASC) VISIBLE,
  CONSTRAINT `fk_emailmarketingcurso_idcurso`
    FOREIGN KEY (`idcurso`)
    REFERENCES `unipesquisas`.`curso` (`idcurso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_emailmarketingcurso_idemailmarketing`
    FOREIGN KEY (`idemailmarketing`)
    REFERENCES `unipesquisas`.`emailmarketing` (`idemailmarketing`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`emailmarketingescolaridade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`emailmarketingescolaridade` (
  `idemailmarketingescolaridade` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idemailmarketing` INT UNSIGNED NOT NULL,
  `idescolaridade` INT UNSIGNED NOT NULL,
  `data` DATE NOT NULL,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idemailmarketingescolaridade`),
  INDEX `fk_emailmarketingescolaridade_idemailmarketing_idx` (`idemailmarketing` ASC) VISIBLE,
  INDEX `fk_emailmarketingescolaridade_idescolaridade_idx` (`idescolaridade` ASC) VISIBLE,
  INDEX `fk_emailmarketingescolaridade_idstatuscandidato_idx` (`idstatuscandidato` ASC) VISIBLE,
  CONSTRAINT `fk_emailmarketingescolaridade_idemailmarketing`
    FOREIGN KEY (`idemailmarketing`)
    REFERENCES `unipesquisas`.`emailmarketing` (`idemailmarketing`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_emailmarketingescolaridade_idescolaridade`
    FOREIGN KEY (`idescolaridade`)
    REFERENCES `unipesquisas`.`escolaridade` (`idescolaridade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_emailmarketingescolaridade_idstatuscandidato`
    FOREIGN KEY (`idstatuscandidato`)
    REFERENCES `unipesquisas`.`statuscandidato` (`idstatuscandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`emailmarketinginstituicao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`emailmarketinginstituicao` (
  `idemailmarketinginstituicao` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idemailmarketing` INT UNSIGNED NOT NULL,
  `idinstituicao` INT UNSIGNED NOT NULL,
  `data` DATE NOT NULL,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idemailmarketinginstituicao`),
  INDEX `fk_emailmarketinginstituicao_idemailmarketing_idx` (`idemailmarketing` ASC) VISIBLE,
  INDEX `fk_emailmarketinginstituicao_idinstituicao_idx` (`idinstituicao` ASC) VISIBLE,
  INDEX `fk_emailmarketinginstituicao_idstatuscandidato_idx` (`idstatuscandidato` ASC) VISIBLE,
  CONSTRAINT `fk_emailmarketinginstituicao_idemailmarketing`
    FOREIGN KEY (`idemailmarketing`)
    REFERENCES `unipesquisas`.`emailmarketing` (`idemailmarketing`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_emailmarketinginstituicao_idinstituicao`
    FOREIGN KEY (`idinstituicao`)
    REFERENCES `unipesquisas`.`instituicao` (`idinstituicao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_emailmarketinginstituicao_idstatuscandidato`
    FOREIGN KEY (`idstatuscandidato`)
    REFERENCES `unipesquisas`.`statuscandidato` (`idstatuscandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`emailpadrao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`emailpadrao` (
  `idemailpadrao` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `assuntoboasvindas` TEXT NULL,
  `mensagemboasvindas` TEXT NULL,
  `assuntolembretepesquisa` TEXT NULL,
  `mensagemlembretepesquisa` TEXT NULL,
  PRIMARY KEY (`idemailpadrao`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`envioemailmarketing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`envioemailmarketing` (
  `idenvioemailmarketing` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `data` DATE NULL,
  `numeroemails` INT NULL,
  `tempo` VARCHAR(45) NULL,
  PRIMARY KEY (`idenvioemailmarketing`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`pesquisacurso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`pesquisacurso` (
  `idpesquisacurso` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idpesquisa` INT UNSIGNED NOT NULL,
  `idcurso` INT UNSIGNED NOT NULL,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idpesquisacurso`),
  INDEX `fk_pesquisacurso_idpesquisa_idx` (`idpesquisa` ASC) VISIBLE,
  INDEX `fk_pesquisacurso_idcurso_idx` (`idcurso` ASC) VISIBLE,
  INDEX `fk_pesquisacurso_idstatuscandidato_idx` (`idstatuscandidato` ASC) VISIBLE,
  CONSTRAINT `fk_pesquisacurso_idpesquisa`
    FOREIGN KEY (`idpesquisa`)
    REFERENCES `unipesquisas`.`pesquisa` (`idpesquisa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pesquisacurso_idcurso`
    FOREIGN KEY (`idcurso`)
    REFERENCES `unipesquisas`.`curso` (`idcurso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pesquisacurso_idstatuscandidato`
    FOREIGN KEY (`idstatuscandidato`)
    REFERENCES `unipesquisas`.`statuscandidato` (`idstatuscandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`pesquisaescolaridade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`pesquisaescolaridade` (
  `idpesquisaescolaridade` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idpesquisa` INT UNSIGNED NOT NULL,
  `idescolaridade` INT UNSIGNED NOT NULL,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idpesquisaescolaridade`),
  INDEX `fk_pesquisaescolaridade_idpesquisa_idx` (`idpesquisa` ASC) VISIBLE,
  INDEX `fk_pesquisaescolaridade_idescolaridade_idx` (`idescolaridade` ASC) VISIBLE,
  INDEX `fk_pesquisaescolaridade_idstatuscandidato_idx` (`idstatuscandidato` ASC) VISIBLE,
  CONSTRAINT `fk_pesquisaescolaridade_idpesquisa`
    FOREIGN KEY (`idpesquisa`)
    REFERENCES `unipesquisas`.`pesquisa` (`idpesquisa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pesquisaescolaridade_idescolaridade`
    FOREIGN KEY (`idescolaridade`)
    REFERENCES `unipesquisas`.`escolaridade` (`idescolaridade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pesquisaescolaridade_idstatuscandidato`
    FOREIGN KEY (`idstatuscandidato`)
    REFERENCES `unipesquisas`.`statuscandidato` (`idstatuscandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`pesquisainstituicao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`pesquisainstituicao` (
  `idpesquisainstituicao` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idpesquisa` INT UNSIGNED NOT NULL,
  `idinstituicao` INT UNSIGNED NOT NULL,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idpesquisainstituicao`),
  INDEX `fk_pesquisainstituicao_idpesquisa_idx` (`idpesquisa` ASC) VISIBLE,
  INDEX `fk_pesquisainstituicao_idinstituicao_idx` (`idinstituicao` ASC) VISIBLE,
  INDEX `fk_pesquisainstituicao_idstatuscandidato_idx` (`idstatuscandidato` ASC) VISIBLE,
  CONSTRAINT `fk_pesquisainstituicao_idpesquisa`
    FOREIGN KEY (`idpesquisa`)
    REFERENCES `unipesquisas`.`pesquisa` (`idpesquisa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pesquisainstituicao_idinstituicao`
    FOREIGN KEY (`idinstituicao`)
    REFERENCES `unipesquisas`.`instituicao` (`idinstituicao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pesquisainstituicao_idstatuscandidato`
    FOREIGN KEY (`idstatuscandidato`)
    REFERENCES `unipesquisas`.`statuscandidato` (`idstatuscandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`pesquisapergunta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`pesquisapergunta` (
  `idpesquisapergunta` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idpesquisa` INT UNSIGNED NOT NULL,
  `idpergunta` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idpesquisapergunta`),
  INDEX `fk_pesquisapergunta_idpesquisa_idx` (`idpesquisa` ASC) VISIBLE,
  INDEX `fk_pesquisapergunta_idpergunta_idx` (`idpergunta` ASC) VISIBLE,
  CONSTRAINT `fk_pesquisapergunta_idpesquisa`
    FOREIGN KEY (`idpesquisa`)
    REFERENCES `unipesquisas`.`pesquisa` (`idpesquisa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pesquisapergunta_idpergunta`
    FOREIGN KEY (`idpergunta`)
    REFERENCES `unipesquisas`.`pergunta` (`idpergunta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`respostapesquisa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`respostapesquisa` (
  `idrespostapesquisa` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idpesquisapergunta` INT UNSIGNED NOT NULL,
  `idcandidato` INT UNSIGNED NOT NULL,
  `resposta` TEXT NOT NULL,
  `data` DATE NOT NULL,
  PRIMARY KEY (`idrespostapesquisa`),
  INDEX `fk_respostapesquisa_idpesquisapergunta_idx` (`idpesquisapergunta` ASC) VISIBLE,
  INDEX `fk_respostapesquisa_idcandidato_idx` (`idcandidato` ASC) VISIBLE,
  CONSTRAINT `fk_respostapesquisa_idpesquisapergunta`
    FOREIGN KEY (`idpesquisapergunta`)
    REFERENCES `unipesquisas`.`pesquisapergunta` (`idpesquisapergunta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_respostapesquisa_idcandidato`
    FOREIGN KEY (`idcandidato`)
    REFERENCES `unipesquisas`.`candidato` (`idcandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`respostapesquisacurso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`respostapesquisacurso` (
  `idrespostapesquisacurso` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idpesquisacurso` INT UNSIGNED NOT NULL,
  `idpesquisapergunta` INT UNSIGNED NOT NULL,
  `idcandidato` INT UNSIGNED NOT NULL,
  `resposta` TEXT NOT NULL,
  `data` DATE NOT NULL,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idrespostapesquisacurso`),
  INDEX `fk_respostapesquisacurso_idpesquisacurso_idx` (`idpesquisacurso` ASC) VISIBLE,
  INDEX `fk_respostapesquisacurso_idpesquisapergunta_idx` (`idpesquisapergunta` ASC) VISIBLE,
  INDEX `fk_respostapesquisacurso_idcandidato_idx` (`idcandidato` ASC) VISIBLE,
  CONSTRAINT `fk_respostapesquisacurso_idpesquisacurso`
    FOREIGN KEY (`idpesquisacurso`)
    REFERENCES `unipesquisas`.`pesquisacurso` (`idpesquisacurso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_respostapesquisacurso_idpesquisapergunta`
    FOREIGN KEY (`idpesquisapergunta`)
    REFERENCES `unipesquisas`.`pesquisapergunta` (`idpesquisapergunta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_respostapesquisacurso_idcandidato`
    FOREIGN KEY (`idcandidato`)
    REFERENCES `unipesquisas`.`candidato` (`idcandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`respostapesquisaescolaridade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`respostapesquisaescolaridade` (
  `idrespostapesquisaescolaridade` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idpesquisaescolaridade` INT UNSIGNED NOT NULL,
  `idpesquisapergunta` INT UNSIGNED NOT NULL,
  `idcandidato` INT UNSIGNED NOT NULL,
  `resposta` TEXT NOT NULL,
  `data` DATE NOT NULL,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idrespostapesquisaescolaridade`),
  INDEX `fk_respostapesquisaescolaridade_idpesquisaescolaridade_idx` (`idpesquisaescolaridade` ASC) VISIBLE,
  INDEX `fk_respostapesquisaescolaridade_idpesquisapergunta_idx` (`idpesquisapergunta` ASC) VISIBLE,
  INDEX `fk_respostapesquisaescolaridade_idcandidato_idx` (`idcandidato` ASC) VISIBLE,
  INDEX `fk_respostapesquisaescolaridade_idstatuscandidato_idx` (`idstatuscandidato` ASC) VISIBLE,
  CONSTRAINT `fk_respostapesquisaescolaridade_idpesquisaescolaridade`
    FOREIGN KEY (`idpesquisaescolaridade`)
    REFERENCES `unipesquisas`.`pesquisaescolaridade` (`idpesquisaescolaridade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_respostapesquisaescolaridade_idpesquisapergunta`
    FOREIGN KEY (`idpesquisapergunta`)
    REFERENCES `unipesquisas`.`pesquisapergunta` (`idpesquisapergunta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_respostapesquisaescolaridade_idcandidato`
    FOREIGN KEY (`idcandidato`)
    REFERENCES `unipesquisas`.`candidato` (`idcandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_respostapesquisaescolaridade_idstatuscandidato`
    FOREIGN KEY (`idstatuscandidato`)
    REFERENCES `unipesquisas`.`statuscandidato` (`idstatuscandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`respostapesquisainstituicao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`respostapesquisainstituicao` (
  `idrespostapesquisainstituicao` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idpesquisainstituicao` INT UNSIGNED NOT NULL,
  `idpesquisapergunta` INT UNSIGNED NOT NULL,
  `idcandidato` INT UNSIGNED NOT NULL,
  `resposta` TEXT NOT NULL,
  `data` DATE NOT NULL,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idrespostapesquisainstituicao`),
  INDEX `fk_respostapesquisainstituicao_idpesquisainstituicao_idx` (`idpesquisainstituicao` ASC) VISIBLE,
  INDEX `fk_respostapesquisainstituicao_idpesquisapergunta_idx` (`idpesquisapergunta` ASC) VISIBLE,
  INDEX `fk_respostapesquisainstituicao_idcandidato_idx` (`idcandidato` ASC) VISIBLE,
  INDEX `fk_respostapesquisainstituicao_idstatuscandidato_idx` (`idstatuscandidato` ASC) VISIBLE,
  CONSTRAINT `fk_respostapesquisainstituicao_idpesquisainstituicao`
    FOREIGN KEY (`idpesquisainstituicao`)
    REFERENCES `unipesquisas`.`pesquisainstituicao` (`idpesquisainstituicao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_respostapesquisainstituicao_idpesquisapergunta`
    FOREIGN KEY (`idpesquisapergunta`)
    REFERENCES `unipesquisas`.`pesquisapergunta` (`idpesquisapergunta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_respostapesquisainstituicao_idcandidato`
    FOREIGN KEY (`idcandidato`)
    REFERENCES `unipesquisas`.`candidato` (`idcandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_respostapesquisainstituicao_idstatuscandidato`
    FOREIGN KEY (`idstatuscandidato`)
    REFERENCES `unipesquisas`.`statuscandidato` (`idstatuscandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`smsmarketingalternativa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`smsmarketingalternativa` (
  `idsmsmarketingalternativa` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idsmsmarketing` INT UNSIGNED NOT NULL,
  `idpergunta` INT UNSIGNED NOT NULL,
  `alternativa` TEXT NOT NULL,
  `idpesquisa` INT UNSIGNED NOT NULL,
  `data` DATE NOT NULL,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idsmsmarketingalternativa`),
  INDEX `fk_smsmarketingalternativa_idsmsmarketing_idx` (`idsmsmarketing` ASC) VISIBLE,
  INDEX `fk_smsmarketingalternativa_idpergunta_idx` (`idpergunta` ASC) VISIBLE,
  INDEX `fk_smsmarketingalternativa_idpesquisa_idx` (`idpesquisa` ASC) VISIBLE,
  INDEX `fk_smsmarketingalternativa_idstatuscandidato_idx` (`idstatuscandidato` ASC) VISIBLE,
  CONSTRAINT `fk_smsmarketingalternativa_idsmsmarketing`
    FOREIGN KEY (`idsmsmarketing`)
    REFERENCES `unipesquisas`.`smsmarketing` (`idsmsmarketing`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_smsmarketingalternativa_idpergunta`
    FOREIGN KEY (`idpergunta`)
    REFERENCES `unipesquisas`.`pergunta` (`idpergunta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_smsmarketingalternativa_idpesquisa`
    FOREIGN KEY (`idpesquisa`)
    REFERENCES `unipesquisas`.`pesquisa` (`idpesquisa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_smsmarketingalternativa_idstatuscandidato`
    FOREIGN KEY (`idstatuscandidato`)
    REFERENCES `unipesquisas`.`statuscandidato` (`idstatuscandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`smsmarketingcurso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`smsmarketingcurso` (
  `idsmsmarketingcurso` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idsmsmarketing` INT UNSIGNED NOT NULL,
  `idcurso` INT UNSIGNED NOT NULL,
  `data` DATE NOT NULL,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idsmsmarketingcurso`),
  INDEX `fk_smsmarketingcurso_idsmsmarketing_idx` (`idsmsmarketing` ASC) VISIBLE,
  INDEX `fk_smsmarketingcurso_idcurso_idx` (`idcurso` ASC) VISIBLE,
  INDEX `fk_smsmarketingcurso_idstatuscandidato_idx` (`idstatuscandidato` ASC) VISIBLE,
  CONSTRAINT `fk_smsmarketingcurso_idsmsmarketing`
    FOREIGN KEY (`idsmsmarketing`)
    REFERENCES `unipesquisas`.`smsmarketing` (`idsmsmarketing`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_smsmarketingcurso_idcurso`
    FOREIGN KEY (`idcurso`)
    REFERENCES `unipesquisas`.`curso` (`idcurso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_smsmarketingcurso_idstatuscandidato`
    FOREIGN KEY (`idstatuscandidato`)
    REFERENCES `unipesquisas`.`statuscandidato` (`idstatuscandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`smsmarketingescolaridade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`smsmarketingescolaridade` (
  `idsmsmarketingescolaridade` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idsmsmarketing` INT UNSIGNED NOT NULL,
  `idescolaridade` INT UNSIGNED NOT NULL,
  `data` DATE NOT NULL,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idsmsmarketingescolaridade`),
  INDEX `fk_smsmarketingescolaridade_idsmsmarketing_idx` (`idsmsmarketing` ASC) VISIBLE,
  INDEX `fk_smsmarketingescolaridade_idescolaridade_idx` (`idescolaridade` ASC) VISIBLE,
  INDEX `fk_smsmarketingescolaridade_idstatuscandidato_idx` (`idstatuscandidato` ASC) VISIBLE,
  CONSTRAINT `fk_smsmarketingescolaridade_idsmsmarketing`
    FOREIGN KEY (`idsmsmarketing`)
    REFERENCES `unipesquisas`.`smsmarketing` (`idsmsmarketing`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_smsmarketingescolaridade_idescolaridade`
    FOREIGN KEY (`idescolaridade`)
    REFERENCES `unipesquisas`.`escolaridade` (`idescolaridade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_smsmarketingescolaridade_idstatuscandidato`
    FOREIGN KEY (`idstatuscandidato`)
    REFERENCES `unipesquisas`.`statuscandidato` (`idstatuscandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `unipesquisas`.`smsmarketinginstituicao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unipesquisas`.`smsmarketinginstituicao` (
  `idsmsmarketinginstituicao` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idsmsmarketing` INT UNSIGNED NOT NULL,
  `idinstituicao` INT UNSIGNED NOT NULL,
  `data` DATE NOT NULL,
  `idstatuscandidato` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idsmsmarketinginstituicao`),
  INDEX `fk_smsmarketinginstituicao_idsmsmarketing_idx` (`idsmsmarketing` ASC) VISIBLE,
  INDEX `fk_smsmarketinginstituicao_idinstituicao_idx` (`idinstituicao` ASC) VISIBLE,
  INDEX `fk_smsmarketinginstituicao_idstatuscandidato_idx` (`idstatuscandidato` ASC) VISIBLE,
  CONSTRAINT `fk_smsmarketinginstituicao_idsmsmarketing`
    FOREIGN KEY (`idsmsmarketing`)
    REFERENCES `unipesquisas`.`smsmarketing` (`idsmsmarketing`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_smsmarketinginstituicao_idinstituicao`
    FOREIGN KEY (`idinstituicao`)
    REFERENCES `unipesquisas`.`instituicao` (`idinstituicao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_smsmarketinginstituicao_idstatuscandidato`
    FOREIGN KEY (`idstatuscandidato`)
    REFERENCES `unipesquisas`.`statuscandidato` (`idstatuscandidato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
