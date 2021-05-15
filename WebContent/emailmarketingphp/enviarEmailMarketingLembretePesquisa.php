<?php
include_once ("banco.php");
require_once("phpMailer/class.phpmailer.php");
require_once("tempo.php");
set_time_limit(0);
    global $numeroEmails;
    startExec();
//0 - pega configuracoes de email
    $query = "SELECT * ";
    $query .= "FROM configuracoessistema ";

    $DBConfiguracoesEmail = mysql_query ($query, $DB);
    if (!$DBConfiguracoesEmail) {
       die("Erro ao efetuar Busca!");
    }

    if (mysql_num_rows($DBConfiguracoesEmail)!=0){
	   $registroConfigEmail = mysql_fetch_array ($DBConfiguracoesEmail);
    }
                  
//1 - pega a lista de empresas ativas
    $query = "SELECT idempresa ";
	$query .= "FROM empresa ";
	$query .= "WHERE status = 1";
	//echo $query;

	$DBEmpresas = mysql_query ($query, $DB);
	if (!$DBEmpresas) {
       die("Erro ao efetuar Busca!");
	}

	if (mysql_num_rows($DBEmpresas)!=0){
	   while($registroEmpresa = mysql_fetch_array ($DBEmpresas)){
           echo "Empresa: " . $registroEmpresa['idempresa']."<br>";

           //2 - pega a lista de candidatos por instituicao
           $query = "SELECT c.email, c.password, c.nome ";
           $query .= "FROM pesquisaescolaridade pe ";
           $query .= "INNER JOIN pesquisa p ON pe.idpesquisa=p.idpesquisa ";
           $query .= "INNER JOIN escolaridade e ON e.idescolaridade=pe.idescolaridade ";
           $query .= "INNER join candidato c ON c.idescolaridade=pe.idescolaridade ";
           $query .= "INNER JOIN candidatoempresa ce ON ce.idcandidato=c.idcandidato ";
           $query .= "WHERE p.status = 1 AND c.receberemail = 1 AND pe.idpesquisaescolaridade NOT IN (SELECT idpesquisaescolaridade ";
           $query .= "FROM candidatopesquisaescolaridade WHERE idcandidato=c.idcandidato) AND p.idempresa = ".$registroEmpresa['idempresa'];
           $query .= " AND ce.idempresa = ".$registroEmpresa['idempresa'];
           $query .= " UNION ";
           $query .= "SELECT c.email, c.password, c.nome ";
           $query .= "FROM pesquisainstituicao pi ";
           $query .= "INNER JOIN pesquisa p ON pi.idpesquisa=p.idpesquisa ";
           $query .= "INNER JOIN instituicao i ON i.idinstituicao=pi.idinstituicao ";
           $query .= "INNER JOIN candidatoinstituicao ci ON ci.idinstituicao=i.idinstituicao ";
           $query .= "INNER JOIN candidato c ON c.idcandidato=ci.idcandidato ";
           $query .= "WHERE p.status = 1 AND c.receberemail = 1 AND pi.idpesquisainstituicao NOT IN ";
           $query .= "(SELECT idpesquisainstituicao ";
           $query .= "FROM candidatopesquisainstituicao ";
           $query .= "WHERE idcandidato = c.idcandidato) AND p.idempresa = ".$registroEmpresa['idempresa']." AND i.idinstituicao IN ( ";
           $query .= "SELECT ci.idinstituicao ";
           $query .= "FROM candidatoinstituicao ci ";
           $query .= "INNER JOIN instituicao i ON ci.idinstituicao=i.idinstituicao ";
           $query .= "WHERE ci.idcandidato = c.idcandidato AND i.idempresa = ".$registroEmpresa['idempresa'].") ";
           $query .= "UNION ";
           $query .= "SELECT can.email, can.password, can.nome ";
           $query .= "FROM pesquisacurso pc ";
           $query .= "INNER JOIN pesquisa p ON pc.idpesquisa=p.idpesquisa ";
           $query .= "INNER JOIN curso c ON c.idcurso=pc.idcurso ";
           $query .= "INNER JOIN candidatocurso cc ON cc.idcurso=c.idcurso ";
           $query .= "INNER JOIN candidato can ON can.idcandidato=cc.idcandidato ";
           $query .= "WHERE p.status = 1 AND can.receberemail = 1 AND pc.idpesquisacurso NOT IN ";
           $query .= "(SELECT idpesquisacurso ";
           $query .= "FROM candidatopesquisacurso ";
           $query .= "WHERE idcandidato = can.idcandidato) AND p.idempresa = ".$registroEmpresa['idempresa']." AND c.idcurso IN ";
           $query .= "(SELECT cc.idcurso ";
           $query .= "FROM candidatocurso cc ";
           $query .= "INNER JOIN curso c ON cc.idcurso=c.idcurso ";
           $query .= "INNER JOIN instituicao i ON c.idinstituicao=i.idinstituicao ";
           $query .= "WHERE cc.idcandidato = can.idcandidato AND i.idempresa = ".$registroEmpresa['idempresa'].")";

           

           $DBEmailCandidatos = mysql_query ($query, $DB);
           if (!$DBEmailCandidatos) {
              die("Erro ao efetuar Busca!");
           }

	       if (mysql_num_rows($DBEmailCandidatos)!=0){
              $j=0;//contador emails enviados por tempo
	          while($registroCandidato = mysql_fetch_array ($DBEmailCandidatos)){
                  //limita o envio a 23 horas e 58 minutos
                  if(endExec() >= 86280){//86280 segundos = 23 horas e 58 minutos
                      break;
                  }
                  //$j++;
                  //if($j == 17) {//envia 17 emails e espera 60 segundos = total 1000 emails por hora é a expectativa
                      sleep(1);//espera 1 segundos para cada email
                      //$j = 0;
                      //flush();
                  //}
                  //3 - monta email
                  
                  $mail = new PHPMailer();
	              $mail->IsSMTP();		// Ativar SMTP
	              $mail->SMTPDebug = 1;		// Debugar: 1 = erros e mensagens, 2 = mensagens apenas
	              if($registroConfigEmail['emailautenticacaosmtp'] == 1){
                     $mail->SMTPAuth = true;		// Autenticação ativada
                  }
	              if($registroConfigEmail['emailssl'] == 1){
                     $mail->SMTPSecure = 'ssl';	// SSL REQUERIDO pelo GMail
                  }
                  if($registroConfigEmail['emailtls'] == 1){
                     $mail->SMTPSecure = 'tls';
                  }
                  $mail->IsHTML(true);
	              $mail->Host = $registroConfigEmail['emailsmtp'];	// SMTP utilizado
	              $mail->Port = $registroConfigEmail['emailsmtpporta'];  		// A porta 465 deverá estar aberta em seu servidor
	              $mail->Username = $registroConfigEmail['emaillogin'];
	              $mail->Password = $registroConfigEmail['emailsenha'];
	              $mail->SetFrom($registroConfigEmail['emailfrom'], 'UniPesquisas');
	              $mail->Subject = 'Lembrete de Pesquisas a Responder';
	              $mail->Body = '<html>';
	              $mail->Body .= '<body>';
                  $mail->Body .= '<p align="center"><font face="Arial">Lembrete UniPesquisas!<br>';
		          $mail->Body .= '<br>';
		          $mail->Body .= 'Acesse o UniPesquisas através do link:<br>';
                  $mail->Body .= '<br>';
		          $mail->Body .= '<a href="http://www.unipesquisas.com/pesquisa">';
		          $mail->Body .= 'http://www.unipesquisas.com/pesquisa</a><br>';
		          $mail->Body .= '<br>';
		          $mail->Body .= 'Responda as perguntas de sua Instituição de Ensino</font></p>';
                  $mail->Body .= '<br><br>Se não deseja mais receber nossos e-mails, clique em ';
                  $mail->Body .= '<a href="http://www.unipesquisas.com/pesquisa/CancelarAssinatura.jsf?email=';
                  $mail->Body .= $registroCandidato['email'] . '&id=' . $registroCandidato['password'] . '>';
		          $mail->Body .= 'Cancelar</a><br>';
	              $mail->Body .= '</body>';
	              $mail->Body .= '</html>';
	              $mail->AddAddress($registroCandidato['email'], $registroCandidato['nome']);
	              if(!$mail->Send()) {
                     echo 'Mail error: '.$mail->ErrorInfo;
	              } else {
	                echo 'Enviado para: '.$registroCandidato['email']."<br>";
                  }
                  $numeroEmails = $numeroEmails + 1;
	          }
           }
           mysql_free_result($DBEmailCandidatos);
       }
    }
	//echo $svResultado;
	mysql_free_result($DBEmpresas);
    mysql_free_result($DBConfiguracoesEmail);
    $tempo = endExec();
    $query = "INSERT INTO envioemailmarketing ";
    $query .= "VALUES (null, '".date("Y/m/d")."', ".$numeroEmails.", ".$tempo.") ";
    //echo $query;
    $DBEnvioEmails = mysql_query ($query, $DB);
    if (!$DBEnvioEmails) {
       echo 'Erro ao Inserir dados de envio no Banco';
    }
?>
