<?php
    $dbhost = 'first-database.cyad2xjmjeuv.ap-northeast-2.rds.amazonaws.com';
    $dbport = '3306';
    $dbname = 'first_database';                               # DATABASE 이름
    $charset = 'utf8' ;
    $username ='admin';                                      # MySQL 계정 아이디
    $password = '';                                   # MySQL 계정 패스워드 # pw는 삭제했음 -> 힌트 군대

    $options = array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8');
    try {

        $con = "mysql:host={$dbhost};port={$dbport};dbname={$dbname};charset={$charset}";
    } catch(PDOException $e) {

        die("Failed to connect to the database: " . $e->getMessage());
    }

    $pdo = new PDO($con,$username,$password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);

    // if(function_exists('get_magic_quotes_gpc') && get_magic_quotes_gpc()) {
    //     function undo_magic_quotes_gpc(&$array) {
    //         foreach($array as &$value) {
    //             if(is_array($value)) {
    //                 undo_magic_quotes_gpc($value);
    //             }
    //             else {
    //                 $value = stripslashes($value);
    //             }
    //         }
    //     }

    //     undo_magic_quotes_gpc($_POST);
    //     undo_magic_quotes_gpc($_GET);
    //     undo_magic_quotes_gpc($_COOKIE);
    // }

    header('Content-Type: text/html; charset=utf-8');
    #session_start();
?>