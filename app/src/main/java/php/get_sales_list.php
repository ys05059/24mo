<?php
error_reporting(E_ALL);
ini_set('display_errors',1);

include('db_con.php');
$date=isset($_POST['date']) ? $_POST['date'] : '2022-12-08';

$data = get_daily($pdo,$date);
if(count($data) == 0){
    $result = array("status" => '404',"data" =>$data);
}else{
    $result = array("status" => '200',"data" => $data);
}

$json = json_encode($result, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
print_r($json);
?>

<?php
// 하루에 판매된 모든 아이템 반환
    function get_daily($pdo, $date){
        $result = array();

        $sql = <<<EOT
            select S.sdate, W.WID, S.quantity
            from WINE W, SALES S
            where W.Wid = S.Wid
            AND S.SDATE = '$date'
            GROUP BY W.WID
            ORDER BY S.QUANTITY DESC
        EOT;

        $stmt = $pdo->prepare($sql);
        $stmt->execute();

        while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            array_push($result,
                array(
                    "date" => $sdate,
                    "wid" => $WID,
                    "quantity" => $quantity));
        }
        return $result;
    }
?>