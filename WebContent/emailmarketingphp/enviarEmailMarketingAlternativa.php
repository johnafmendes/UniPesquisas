<?php
include_once ("banco.php");
require_once("phpMailer/class.phpmailer.php");
//require_once("tempo.php");
set_time_limit(0);
    global $numeroEmails;
    $numeroEmails = 0;
    //startExec();
    $tempoInicio = endExec();
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
    echo "<br>E-Mail Alternativa ... <br>";
	if (mysql_num_rows($DBEmpresas)!=0){
	   while($registroEmpresa = mysql_fetch_array ($DBEmpresas)){
           echo "Empresa: " . $registroEmpresa['idempresa']."<br>";

           //2 - pega a lista de candidatos por instituicao
           $query = "SELECT c.idcandidato, c.email, em.idemailmarketing, ema.idemailmarketingalternativa, c.password, c.nome ";
           $query .= "FROM emailmarketingalternativa ema ";
           $query .= "INNER JOIN pesquisapergunta pp ON pp.idpergunta=ema.idpergunta ";
           $query .= "INNER JOIN respostapesquisaescolaridade rpe ON rpe.idpesquisapergunta=pp.idpesquisapergunta ";
           $query .= "INNER JOIN candidato c ON c.idcandidato=rpe.idcandidato ";
           $query .= "INNER JOIN emailmarketing em ON ema.idemailmarketing=em.idemailmarketing ";
           $query .= "INNER JOIN candidatoempresa ce ON ce.idcandidato=c.idcandidato ";
           $query .= "WHERE em.idempresa = ".$registroEmpresa['idempresa'] ." AND em.status = 1 AND rpe.resposta=( ";
           $query .= "SELECT alternativa ";
           $query .= "FROM emailmarketingalternativa ";
           $query .= "WHERE idemailmarketingalternativa=ema.idemailmarketingalternativa) AND pp.idpesquisa = ( ";
           $query .= "SELECT idpesquisa ";
           $query .= "FROM emailmarketingalternativa ";
           $query .= "WHERE idemailmarketingalternativa=ema.idemailmarketingalternativa) AND pp.idpergunta = ( ";
           $query .= "SELECT idpergunta ";
           $query .= "FROM emailmarketingalternativa ";
           $query .= "WHERE idemailmarketingalternativa=ema.idemailmarketingalternativa) AND c.idcandidato ";
           $query .= "NOT IN (SELECT ca.idcandidato ";
           $query .= "FROM candidatoemalternativa ca ";
           $query .= "INNER JOIN emailmarketingalternativa ema1 ON ema1.idemailmarketingalternativa=ca.idemailmarketingalternativa ";
           $query .= "WHERE ca.idemailmarketingalternativa = ema.idemailmarketingalternativa) ";
           $query .= "AND pp.idpesquisapergunta=rpe.idpesquisapergunta AND ce.receberemail = 1 AND ";
           $query .= "ce.idempresa = " . $registroEmpresa['idempresa'] . " AND IF (ema.idstatuscandidato IS NOT NULL, ";
           $query .= "ce.idstatuscandidato=ema.idstatuscandidato, ce.idstatuscandidato IN (SELECT idstatuscandidato ";
           $query .= "FROM statuscandidato WHERE idempresa=". $registroEmpresa['idempresa'] . "))";
           $query .= " UNION ";
           $query .= "SELECT c.idcandidato, c.email, em.idemailmarketing, ema.idemailmarketingalternativa, c.password, c.nome ";
           $query .= "FROM emailmarketingalternativa ema ";
           $query .= "INNER JOIN pesquisapergunta pp ON pp.idpergunta=ema.idpergunta ";
           $query .= "INNER JOIN respostapesquisainstituicao rpi ON rpi.idpesquisapergunta=pp.idpesquisapergunta ";
           $query .= "INNER JOIN candidato c ON c.idcandidato=rpi.idcandidato ";
           $query .= "INNER JOIN emailmarketing em ON ema.idemailmarketing=em.idemailmarketing ";
           $query .= "INNER JOIN candidatoempresa ce ON ce.idcandidato=c.idcandidato ";
           $query .= "WHERE em.idempresa = ".$registroEmpresa['idempresa'] ." AND em.status = 1 AND rpi.resposta=( ";
           $query .= "SELECT alternativa ";
           $query .= "FROM emailmarketingalternativa ";
           $query .= "WHERE idemailmarketingalternativa=ema.idemailmarketingalternativa) AND pp.idpesquisa = ( ";
           $query .= "SELECT idpesquisa ";
           $query .= "FROM emailmarketingalternativa ";
           $query .= "WHERE idemailmarketingalternativa=ema.idemailmarketingalternativa) AND pp.idpergunta = ( ";
           $query .= "SELECT idpergunta ";
           $query .= "FROM emailmarketingalternativa ";
           $query .= "WHERE idemailmarketingalternativa=ema.idemailmarketingalternativa) AND c.idcandidato ";
           $query .= "NOT IN (SELECT ca.idcandidato ";
           $query .= "FROM candidatoemalternativa ca ";
           $query .= "INNER JOIN emailmarketingalternativa ema1 ON ema1.idemailmarketingalternativa=ca.idemailmarketingalternativa ";
           $query .= "WHERE ca.idemailmarketingalternativa = ema.idemailmarketingalternativa) ";
           $query .= "AND pp.idpesquisapergunta=rpi.idpesquisapergunta AND ce.receberemail = 1 AND ";
           $query .= "ce.idempresa = " . $registroEmpresa['idempresa'] . " AND IF (ema.idstatuscandidato IS NOT NULL, ";
           $query .= "ce.idstatuscandidato=ema.idstatuscandidato, ce.idstatuscandidato IN (SELECT idstatuscandidato ";
           $query .= "FROM statuscandidato WHERE idempresa=". $registroEmpresa['idempresa'] . "))";
           $query .= " UNION ";
           $query .= "SELECT c.idcandidato, c.email, em.idemailmarketing, ema.idemailmarketingalternativa, c.password, c.nome ";
           $query .= "FROM emailmarketingalternativa ema ";
           $query .= "INNER JOIN pesquisapergunta pp ON pp.idpergunta=ema.idpergunta ";
           $query .= "INNER JOIN respostapesquisacurso rpc ON rpc.idpesquisapergunta=pp.idpesquisapergunta ";
           $query .= "INNER JOIN candidato c ON c.idcandidato=rpc.idcandidato ";
           $query .= "INNER JOIN emailmarketing em ON ema.idemailmarketing=em.idemailmarketing ";
           $query .= "INNER JOIN candidatoempresa ce ON ce.idcandidato=c.idcandidato ";
           $query .= "WHERE em.idempresa = ".$registroEmpresa['idempresa'] ." AND em.status = 1 AND rpc.resposta=( ";
           $query .= "SELECT alternativa ";
           $query .= "FROM emailmarketingalternativa ";
           $query .= "WHERE idemailmarketingalternativa=ema.idemailmarketingalternativa) AND pp.idpesquisa = ( ";
           $query .= "SELECT idpesquisa ";
           $query .= "FROM emailmarketingalternativa ";
           $query .= "WHERE idemailmarketingalternativa=ema.idemailmarketingalternativa) AND pp.idpergunta = ( ";
           $query .= "SELECT idpergunta ";
           $query .= "FROM emailmarketingalternativa ";
           $query .= "WHERE idemailmarketingalternativa=ema.idemailmarketingalternativa) AND c.idcandidato ";
           $query .= "NOT IN (SELECT ca.idcandidato ";
           $query .= "FROM candidatoemalternativa ca ";
           $query .= "INNER JOIN emailmarketingalternativa ema1 ON ema1.idemailmarketingalternativa=ca.idemailmarketingalternativa ";
           $query .= "WHERE ca.idemailmarketingalternativa = ema.idemailmarketingalternativa) ";
           $query .= "AND pp.idpesquisapergunta=rpc.idpesquisapergunta AND ce.receberemail = 1 AND ";
           $query .= "ce.idempresa = " . $registroEmpresa['idempresa'] . " AND IF (ema.idstatuscandidato IS NOT NULL, ";
           $query .= "ce.idstatuscandidato=ema.idstatuscandidato, ce.idstatuscandidato IN (SELECT idstatuscandidato ";
           $query .= "FROM statuscandidato WHERE idempresa=". $registroEmpresa['idempresa'] . "))";

           //echo $query;
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
                      sleep(1);//espera 1 segundos para cada email.
                      //$j = 0;
                      //flush();
                  //}
                  //3 - pega o email e assunto a enviar
                  $query = "SELECT assunto, mensagem ";
                  $query .= "FROM emailmarketing ";
                  $query .= "WHERE idemailmarketing = " . $registroCandidato['idemailmarketing'];
                  
                  $DBEmail = mysql_query ($query, $DB);
                  if (!$DBEmail) {
                     die("Erro ao efetuar Busca!");
                  }

	              if (mysql_num_rows($DBEmail)!=0){
	                 $registroEmail = mysql_fetch_array ($DBEmail);
                  }
                  
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
	              $mail->Subject = $registroEmail['assunto'];
	              $mail->Body = $registroEmail['mensagem'];
	              $mail->Body .= '<br><br>Se não deseja mais receber nossos e-mails, clique em ';
                  $mail->Body .= '<a href="http://www.unipesquisas.com/pesquisa/CancelarAssinatura.jsf?email=';
                  $mail->Body .= $registroCandidato['email'] . '&id=' . $registroCandidato['password'];
                  $mail->Body .= '&idEmpresa='.$registroEmpresa['idempresa'].'>';
		          $mail->Body .= 'Cancelar</a><br>';
	              $mail->AddAddress($registroCandidato['email'], $registroCandidato['nome']);
	              if(!$mail->Send()) {
                     echo 'Mail error: '.$mail->ErrorInfo;
	              } else {
		             $query = "INSERT INTO candidatoemalternativa ";
				     $query .= "(idcandidato, idemailmarketingalternativa) ";
				     $query .= "Values (".$registroCandidato['idcandidato'].", ".$registroCandidato['idemailmarketingalternativa'].")";

				     //echo $query;
				     $DBCandidatoEMAlternativa = mysql_query ($query, $DB);
				     if (!$DBCandidatoEMAlternativa) {
                        echo 'Erro ao Inserir no Banco';
				     }else{
				           echo 'Enviado para: '.$registroCandidato['email']." - email: ". $registroCandidato['idemailmarketing']."<br>";
                     }
                     $numeroEmails = $numeroEmails + 1;
                  }
                  mysql_free_result($DBEmail);
	          }
           }
           mysql_free_result($DBEmailCandidatos);
       }
    }
	//echo $svResultado;
	mysql_free_result($DBEmpresas);
    mysql_free_result($DBConfiguracoesEmail);
    $tempo = endExec();
    $tempofinal = $tempo-$tempoInicio;
    $query = "INSERT INTO envioemailmarketing (idenvioemailmarketing, data, numeroemails, tempo) ";
    $query .= "VALUES (null, '".date("Y/m/d")."', ".$numeroEmails.", ".$tempofinal.") ";
    $DBEnvioEmails = mysql_query ($query, $DB);
    if (!$DBEnvioEmails) {
       echo '<br>Erro ao Inserir dados de envio no Banco<br>';
       echo $query;
    }
?>
