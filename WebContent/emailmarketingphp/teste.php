<?php
require_once("tempo.php");
  set_time_limit(0);
  startExec();
  global $tempoInicio;
  $tempoInicio = endExec();
  $j=0;
  for($i=0;$i<500;$i++) {
    echo "<br>Email" . $i;
    if(endExec() >= 220){
      break;
    }
    $j++;
    if($j == 100) {
      sleep(10);
      $j = 0;
      flush();
    }
  }
  $tempo = endExec();
  echo $tempo;
include("teste2.php");
?>
