<?php
    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('db_con.php');                      // DB 연결
    include('get_wine_detail.php');             // 와인 상세정보 받아오는 php문

    //POST 값을 읽어온다, Tag가 비어있을 경우 '레드'로 설정

    $Tag1 = isset($_POST['Tag1']) ? $_POST['Tag1'] : '로제';
    $Tag2 = isset($_POST['Tag2']) ? $_POST['Tag2'] : '가장 많이 팔리는';

    // echo 'Tag1 : '.$Tag1.'<br>';
    // echo 'Tag2 : '.$Tag2.'<br>';

    // 와인 3개 추천 알고리즘 -> Wid가 3개 들어가 있는 배열 반환
    $wid_list = recommend_Three($pdo,$Tag1,$Tag2);

    // 와인 정보 채우기
    $data = get_wine_detail($pdo,$wid_list);

    // json으로 반환
    // header('Content-Type: application/json; charset=utf8');
    $result = array("status" => '200',"data" => $data);
    $json = json_encode($result, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    print_r($json);
    // echo"<pre>".$json."<pre/>";
?>

<?php
    function recommend_Three($pdo,$Tag1,$Tag2){
        $sql = <<<EOT
            SELECT W.Wid
            FROM WINE W
            WHERE  W.`type`  = '$Tag1'
            EOT;
        // 추천 알고리즘 -> Wid로 3개 선택
        switch($Tag2){
            case '달달한':
                $sql = $sql."AND W.SWEETNESS >3 ";
                $data = chooseThree_PriceRange($pdo,$sql);
                break;

            case '도수가 낮은':
                $sql = $sql."AND W.ALCOHOL BETWEEN 1 AND 12 ";
                $data = chooseThree_PriceRange($pdo,$sql);
                break;

            case '선물하기 좋은' :
                switch($Tag1){
                    case '레드':
                        $sql = $sql." AND W.REGION2 IN ('보르도', '나파 밸리') "."AND W.PRICE > 15000";
                        $data = chooseThree_PriceRange($pdo,$sql);
                        break;

                    case '화이트':
                        $sql = $sql." AND W.REGION2 IN ('알자스','꼬뜨 드 본','샤블리')" ;
                        $data = chooseThree_PriceRange($pdo,$sql);
                        break;

                    case '로제':
                        $sql = $sql."AND W.SWEETNESS >=2 "."AND W.PRICE BETWEEN 20000 AND 70000";
                        $data = chooseThree_Random($pdo,$sql);
                        break;
                    case '스파클링':
                        $sql = $sql."AND W.SWEETNESS >=2 "."AND W.PRICE BETWEEN 20000 AND 70000";
                        $data = chooseThree_Random($pdo,$sql);
                        break;
                    default :
                        echo 'Tag1가 잘못 선택 되었습니다';
                        break;
                }
                break;

            case '가장 많이 팔리는':
                $data = chooseThree_TopSelling($pdo,$Tag1);
                break;
            default :
                // echo 'Tag1가 잘못 선택 되었습니다';
                break;
        }
        // echo 'data : '.json_encode($data).'<br>';
        return $data;
    }

    // sql 결과에서 추천 와인 3개 반환하는 함수
    function chooseThree_PriceRange($pdo,$sql){
        $temp = array();
        $result = array();
        // 3만원 이하 와인 1개 추가
        $stmt = $pdo->prepare($sql." AND W.PRICE < 30000");
        $stmt->execute();
            // 조건에 해당하는 와인 전부 받아오기
        while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            array_push($temp,$Wid);
        }
            // 그 중에서 1개 랜덤 선택하기
        $temp_index = array_rand($temp);
        array_push($result,$temp[$temp_index]);
        $temp = array();

        // 3 ~10만원 와인 1개 추가
        $stmt = $pdo->prepare($sql." AND W.PRICE BETWEEN 30000 AND 99999");
        $stmt->execute();
        while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            array_push($temp,$Wid);
        }
        $temp_index = array_rand($temp);
        array_push($result,$temp[$temp_index]);

        $temp = array();
        // 10만원 이상 와인 1개 추가
        $stmt = $pdo->prepare($sql." AND W.PRICE BETWEEN 100000 AND 300000");
        $stmt->execute();
        while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            array_push($temp,$Wid);
        }
        $temp_index = array_rand($temp);
        array_push($result,$temp[$temp_index]);


        return $result;
    }

    // 랜덤으로 3개 뽑는 함수
    function chooseThree_Random($pdo, $sql){
        $temp = array();
        $result = array();

        $stmt = $pdo->prepare($sql);
        $stmt->execute();
        while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            array_push($temp,$Wid);
        }
        // 3개 이하일 경우 3개 그대로 반환
        if(count($temp) <=3){
            return $temp;
        }
        // 4개 이상일 경우 그 중에서 3개 랜덤 선택하기
        else{
            shuffle($temp);
            for($i = 0;$i<3;$i= $i+1){
                array_push($result, $temp[$i]);
            }
            return $result;
        }
    }

    function chooseThree_TopSelling($pdo,$Tag1){
        $sql = <<<EOT
                SELECT W.WID
                FROM WINE W ,SALES S
                WHERE W.WID  = S.WID
                AND W.`type` = '$Tag1'
                ORDER BY S.QUANTITY DESC
                LIMIT 3
                EOT;
        $result = array();

        $stmt = $pdo->prepare($sql);
        $stmt->execute();
        while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            array_push($result,$WID);
        }
        return $result;
    }
?>
