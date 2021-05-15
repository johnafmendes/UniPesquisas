<?php
   $host = "localhost";
	$dbname = "mydb5";
	$user = "root";
	$passwd = "root";

/*    $host = "localhost";
	$dbname = "unipesqu_db";
	$user = "unipesqu_sistema";
	$passwd = "";*/

	$bancoexiste = false;
			
	$DB = mysql_connect($host,$user,$passwd); 

	if(!$DB) {
		die ("Erro ao conectar ao MySQL");
	}
	if(!mysql_select_db($dbname)) {
		echo "Erro ao selecionar o Banco!<br>";
		echo "Verificando se o Banco: $banco consta no MySQL...<br>";
		
		/*$db_list = mysql_list_dbs($DB) or die(sql_error());
		$numrows = mysql_numRows($db_list);
		for ($i=0; $i<$numrows; $i++) {
    		if (mysql_db_name($db_list, $i) == $dbname) {
					 $bancoexiste = true;
				}
  	   }*/
      	/*if (!$bancoexiste) {
			echo "Banco Inexistente!<br>";
			echo "Criando um novo Banco...<br>";
			mysql_create_db($dbname,$DB) or die ("Não foi possível criar o banco: $DB");
			echo "Banco criado com sucesso!<br>";
			echo "Criando as Tabelas...<br>";
			
			$query = "CREATE TABLE Cad_Cliente ( ";
			$query .= "cdCliente mediumint(8) unsigned NOT NULL auto_increment, ";
			$query .= "Nome varchar(80) NOT NULL, Endereco varchar(80) DEFAULT NULL, ";
			$query .= "Cidade varchar(40) DEFAULT NULL, Estado varchar(2) DEFAULT NULL, ";
			$query .= "Cep varchar(8) DEFAULT NULL, Login varchar(10) NOT NULL, ";
			$query .= "Senha varchar(10) NOT NULL, PRIMARY KEY (cdCliente))";
			//$query = "DROP TABLE CadCliente";

			//echo $query;
			$DBCliente = mysql_db_query ($dbname, $query);
			if (!$DBCliente) {die("Erro ao criar tabela!");}
						
			echo "Tabela Cad_Cliente criada com sucesso!<br>";
			
			$vNome = "John";
			$vLogin = "root";
			$vSenha = "123";
			echo "Cadastrando o usuario [$vLogin] com a senha [$vSenha]...<br>";
	
			$query = "INSERT INTO Cad_Cliente (cdCliente, Nome, Login, Senha) ";
			$query .= "VALUES (NULL, '$vNome', '$vLogin', '$vSenha') ";
			echo $query;
	
			$DBCliente = mysql_query ($query, $DB);
			if (!$DBCliente) {die ("Erro ao Inserir Dados");}
			
			echo "Usuario [$vLogin] foi criado com sucesso!<br>";
			echo "O SISTEMA SERA REINICIADO EM MENOS DE 5 SEGUNDOS!<br>";
			$str = " <HTML>";
			$str .= " <HEAD>";
   		$str .= ' <META HTTP-EQUIV="Refresh" CONTENT="5"; URL=/">';
			$str .= "</HEAD>";

			die ($str);
		}*/
	}
?>
