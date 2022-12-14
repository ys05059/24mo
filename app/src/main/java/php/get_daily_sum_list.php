<?php
error_reporting(E_ALL);
ini_set('display_errors',1);

include('db_con.php');

$data = get_daily($pdo);

if(count($data) == 0){
    $result = array("status" => '404',"data" =>$data);
}else{
    $result = array("status" => '200',"data" => $data);
}

$json = json_encode($result, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
print_r($json);
?>

<?php
function get_daily($pdo){
        $result = array();

        $sql = <<<EOT
            select sdate as date, SUM(W.price * S.quantity) as amount
            from WINE W, SALES S
            where W.wid = S.wid
            and S.sdate BETWEEN DATE_ADD(NOW(),INTERVAL -7 DAY) AND NOW()
            group by date
            order by date ASC
        EOT;

        $stmt = $pdo->prepare($sql);
        $stmt->execute();

        while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            array_push($result,
                array(
                    "date" => $date,
                    "amount" => $amount));
        }
        return $result;
    }
?>