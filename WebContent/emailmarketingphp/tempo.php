<?php

   /*
    *
    * @file: exec_time.php
    *
    * @author: Angelito M. Goulart
    *
    * @date: 22/11/2011
    *
    * @description: calculates the time of execution of a script
    *
    * @use: include this file in the top of the script, call function startExec() and
    * the end of script call the function endExec().
    *
    * Function endExec print results in the screen.
    *
    */

   global $time;

   /* Get current time */
   function getTime(){
      $microtime = explode(" ", microtime());
      $time = $microtime[0] + $microtime[1];
      return $time;
   }

   /* Calculate start time */
   function startExec(){
      global $time;
      $time = getTime();
   }

   /*
    * Calculate end time of the script,
    * execution time and print
    * result in the screen
    */
   function endExec(){
      global $time;
      $finalTime = getTime();
      $execTime = $finalTime - $time;
      echo 'Execution time: ' . number_format($execTime, 6) . ' ms';
      return number_format($execTime, 0);
   }

?>
