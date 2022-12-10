<?php
error_reporting(E_ALL);
ini_set('display_errors',1);

include('db_con.php');


//POST 값을 읽어온다, Wid가 비어있을 경우 ''로 설정
$Wid=isset($_POST['Wid']) ? $_POST['Wid'] : '161502';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
// if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android){
    if ($Wid != "" ){

        // 와인 기본 정보 쿼리
        $sql = <<<EOT
            SELECT W.WID ,W.NAME ,W.`type` ,W.REGION ,W.REGION2 ,W.PRICE , W.CAPACITY , W.SWEETNESS
            , W.ACIDITY ,W.BODY ,W.TANNIN ,W.MANUFACTURER ,W.VARIETY ,W.TEMPERATURE ,W.ALCOHOL ,WIU.W_URL
            FROM WINE W, WINE_IMAGE_URL WIU
            WHERE W.WID  = WIU .WID
            AND W.WID = $Wid
        EOT;
        $stmt = $pdo->prepare($sql);
        $stmt->execute();

        if ($stmt->rowCount() == 0){

            // echo "'";
            // echo $Wid;
            // echo "'은 찾을 수 없습니다.";
        }
        else{
            while($row=$stmt->fetch(PDO::FETCH_ASSOC)){

                extract($row);
                $data = array("Wid" =>$Wid,
                    "name"=>$NAME,
                    "type" => $type,
                    "region" => $REGION,
                    "region2" => $REGION2,
                    "price" => $PRICE,
                    "capacity" => $CAPACITY,
                    "sweetness"=>$SWEETNESS,
                    "acidity"=>$ACIDITY,
                    "body"=>$BODY,
                    "tannin"=>$TANNIN,
                    "manufacturer"=>$MANUFACTURER,
                    "variety"=>$VARIETY,
                    "temperature"=>$TEMPERATURE,
                    "alcohol"=>$ALCOHOL,
                    "image_url" =>$W_URL
                );
            }

            // Aroma 이미지 배열 넣기
            $sql = <<<EOT
                    SELECT AI.ANAME ,AI.A_URL
                    FROM WINE W,AROMA A ,AROMA_IMAGE AI
                    WHERE W.WID = A.WID
                        AND A.ANAME = AI.ANAME
                        AND W.WID = $Wid
            EOT;
            $stmt = $pdo->prepare($sql);
            $stmt->execute();

            $Aroma_arr =array();
            while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
                extract($row);
                array_push($Aroma_arr,
                    array(
                        "name" => $ANAME,
                        "url" => $A_URL));
            }
            $data["aroma_arr"] = $Aroma_arr;

            // Food 이미지 배열 넣기
            $sql = <<<EOT
                    SELECT FI.FNAME ,FI.F_URL
                    FROM WINE W, FOOD F  ,FOOD_IMAGE FI
                    WHERE W.WID = F.WID
                        AND F.FNAME = FI.FNAME
                        AND W.WID = $Wid
            EOT;
            $stmt = $pdo->prepare($sql);
            $stmt->execute();

            $Food_arr =array();
            while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
                extract($row);
                array_push($Food_arr,
                    array(
                        "name" => $FNAME,
                        "url" => $F_URL));
            }
            $data["food_arr"] = $Food_arr;

            $json = json_encode($data, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
            print_r($json);
        }
    }
// }
?>