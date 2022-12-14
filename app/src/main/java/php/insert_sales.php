<?php
error_reporting(E_ALL);
ini_set('display_errors',1);

include('db_con.php');
$date = isset($_POST['date']) ? $_POST['date'] : "";
$wid = isset($_POST['wid']) ? (int)$_POST['wid'] : 0 ;
$quantity = isset($_POST['quantity']) ? (int)$_POST['quantity']: 0;

$sql = "INSERT INTO SALES VALUES($wid,$quantity,'$date')";

if ($date !== "" && $wid !=0 && $quantity !=0){
    try {
        $pdo->beginTransaction();
        $stmt = $pdo->prepare($sql);
        $stmt->execute();
        $pdo->commit();
        $result = array("status" => '200');
    }catch (Exception $e){
        $pdo->rollback();
        echo ("error");
        throw $e;
        $result = array("status" => '404 insert 오류');
    }
}else{
    $result = array("status" => '404 전달 값 오류');
}

$json = json_encode($result, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
print_r($json);
?>