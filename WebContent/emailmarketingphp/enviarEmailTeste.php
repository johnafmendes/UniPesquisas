<?php
include_once ("banco.php");
require_once("phpMailer/class.phpmailer.php");
set_time_limit(0);
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
	$mail->Subject = "Teste de Email";
	$mail->Body = "Teste de Email";
	$mail->AddAddress("johnafmendes@gmail.com");
	if(!$mail->Send()) {
       echo 'Mail error: '.$mail->ErrorInfo;
	} else {
   	   echo "Email Enviado com Sucesso.";
    }
?>
