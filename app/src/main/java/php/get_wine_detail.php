<?php
    function get_wine_detail($pdo,$wid_list){
        $result = array();
        //와인 기본 정보 쿼리
        $detail_sql = <<<EOT
            SELECT W.WID ,W.NAME ,W.`type` ,W.REGION ,W.REGION2 ,W.PRICE , W.CAPACITY , W.SWEETNESS
            , W.ACIDITY ,W.BODY ,W.TANNIN ,W.MANUFACTURER ,W.VARIETY ,W.TEMPERATURE ,W.ALCOHOL ,WIU.W_URL
            FROM WINE W, WINE_IMAGE_URL WIU
            WHERE W.WID  = WIU .WID
        EOT;
        // 아로마 이미지 쿼리
        $aroma_img_sql = <<<EOT
        SELECT AI.ANAME ,AI.A_URL
        FROM WINE W,AROMA A ,AROMA_IMAGE AI
        WHERE W.WID = A.WID
            AND A.ANAME = AI.ANAME
        EOT;
        // 음식 이미지 쿼리
        $food_img_sql = <<<EOT
        SELECT FI.FNAME ,FI.F_URL
        FROM WINE W, FOOD F  ,FOOD_IMAGE FI
        WHERE W.WID = F.WID
            AND F.FNAME = FI.FNAME
        EOT;

        foreach($wid_list as $Wid){
            $stmt = $pdo->prepare($detail_sql.' AND W.WID = '.$Wid);
            $stmt->execute();
            while($row=$stmt->fetch(PDO::FETCH_ASSOC)){

                extract($row);
                $data = array("Wid" =>$WID,
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
            $stmt = $pdo->prepare($aroma_img_sql.' AND W.WID = '.$Wid);
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
            $stmt = $pdo->prepare($food_img_sql.' AND W.WID = '.$Wid);
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
            array_push($result,$data);
        }
        return $result;
    }

?>