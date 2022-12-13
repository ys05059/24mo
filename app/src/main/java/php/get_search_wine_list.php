<?php
    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('db_con.php');                      // DB 연결
    include('get_wine_detail.php');             // 와인 상세정보 받아오는 php문

    //POST 값을 읽어온다, Tag가 비어있을 경우 '레드'로 설정
    class searchParameter{
        public $name;
        public $min_price,$max_price,$type,$region,$alcohol,$food,$sweetness,$acidity,$body,$tannin;
    }
    $para = new searchParameter();

    $para->name        = isset($_POST['name'])         ? $_POST['name']        : '';
    $para->min_price   = isset($_POST['min_price'])    ? $_POST['min_price']   : 0;
    $para->max_price   = isset($_POST['max_price'])    ? $_POST['max_price']   : 0;
    $para->type        = isset($_POST['type'])         ? $_POST['type']        : '';
    $para->region      = isset($_POST['region'])       ? $_POST['region']      : '';
    $para->alcohol     = isset($_POST['alcohol'])      ? $_POST['alcohol']     : 0;
    $para->food        = isset($_POST['food'])         ? $_POST['food']        : '';
    $para->sweetness   = isset($_POST['sweetness'])    ? $_POST['sweetness']   : 0;
    $para->acidity     = isset($_POST['acidity'])      ? $_POST['acidity']     : 0;
    $para->body        = isset($_POST['body'])         ? $_POST['body']        : 0;
    $para->tannin       = isset($_POST['tannin'])      ? $_POST['tannin']      : 0;

    echo 'name : '.$para->name.'<br>';
    // echo 'Tag2 : '.$Tag2.'<br>';

    // 검색 조건 만족하는 와인 배열 반환
    $wid_list = search_Wine($pdo,$para);

    // 와인 정보 채우기
    $data = get_wine_detail($pdo,$wid_list);

    // 검색 결과 없을 때 처리 따로 해줘야함


    // json으로 반환
    // header('Content-Type: application/json; charset=utf8');
    $result = array("status" => '200',"data" => $data);
    $json = json_encode($result, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    print_r($json);
    echo"<pre>".$json."<pre/>";



?>

<?php
    function search_Wine($pdo,$para){
        $wid_list = array();
        $sql = set_search_query($para);

        echo $sql;

        // $stmt = $pdo->prepare($sql);
        // $stmt->execute();
        // while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
        //     extract($row);
        //     array_push($wid_list,$Wid);
        // }
        $wid_list = chooseThree_Random($sql);
        return $wid_list;
    }

    // 검색 쿼리 설정
    function set_search_query($para){
        // 음식 설정 없을 때
        if($para->food === ''){
            $sql = <<<EOT
            SELECT W.Wid
            FROM WINE W
            WHERE  1=1
            EOT;
        } // 음식 설정 있을 때
        else {
            $sql = <<<EOT
            SELECT W.Wid
            FROM WINE W, FOOD F, FOOD_IMAGE FI
            WHERE W.WID = F.WID
                AND F.FNAME = FI.FNAME
            EOT;
            $sql = $sql.food_to_query($para->food);
        }

        // 이름 설정
        if (!$para->name ===''){
            $sql = $sql." AND W.name  LIKE '%$para->name%'";
        }// 가격대 설정
        if(!($para->min_price ==0 && $para->$max_price == 0 )){                                // 가격대를 설정했을 경우
            if($para->min_price ==0){                                                          // 최대값만 설정했을 경우
                $sql = $sql." AND W.prcie < $para->max_price";
            }else if($para->max_price == 0 ){                                                  // 최솟값만 설정했을 경우
                $sql = $sql." AND W.prcie > $para->min_price";
            }else{                                                                              // 둘다 설정했을 경우
                $sql = $sql." AND W.prcie BETWEEN $para->min_price AND $para->max_price";
            }
        }// 타입 설정
        if(!($para->type === '')){
            $sql = $sql." AND W.`type` ='$para->type'";
        }// 지역 설정
        if(!($para->region === '')){
            $sql = $sql." AND W.region ='$para->region'";
        }// 알코올 설정
        switch($para->alcohol){
            case 1:                                                                             // 1 -> 도수 낮음
                $sql = $sql." AND W.alcohol BETWEEN 1 AND 12";
                break;
            case 2:                                                                             // 2 -> 도수 중간
                $sql = $sql." AND W.alcohol BETWEEN 12 AND 15";
                break;
            case 3:                                                                             // 3 -> 도수 높음
                $sql = $sql." AND W.alcohol > 15";
                break;
            default: break;                                                                     // 0 -> 상관없음
        }// 당도 설정
        switch($para->sweetness){
            case 1:                                                                             // 1 -> 당도 낮음
                $sql = $sql." AND W.sweetness BETWEEN 1 AND 2";
                break;
            case 2:                                                                             // 2 -> 당도 중간
                $sql = $sql." AND W.sweetness BETWEEN 2 AND 4";
                break;
            case 3:                                                                             // 3 -> 당도 높음
                $sql = $sql." AND W.sweetness = 5 ";
                break;
            default: break;                                                                     // 0 -> 상관없음
        }// 산도 설정
        switch($para->acidity){
            case 1:                                                                             // 1 -> 산도 낮음
                $sql = $sql." AND W.acidity BETWEEN 1 AND 2";
                break;
            case 2:                                                                             // 2 -> 산도 중간
                $sql = $sql." AND W.acidity BETWEEN 2 AND 4";
                break;
            case 3:                                                                             // 3 -> 산도 높음
                $sql = $sql." AND W.acidity = 5 ";
                break;
            default: break;                                                                     // 0 -> 상관없음
        }// body 설정
        switch($para->body){
            case 1:                                                                             // 1 -> 바디 낮음
                $sql = $sql." AND W.body BETWEEN 1 AND 2";
                break;
            case 2:                                                                             // 2 -> 바디 중간
                $sql = $sql." AND W.body BETWEEN 2 AND 4";
                break;
            case 3:                                                                             // 3 -> 바디 높음
                $sql = $sql." AND W.body = 5 ";
                break;
            default: break;                                                                     // 0 -> 상관없음
        }// 타닌 설정
        switch($para->tannin){
            case 1:                                                                             // 1 -> 타닌 낮음
                $sql = $sql." AND W.tannin BETWEEN 1 AND 2";
                break;
            case 2:                                                                             // 2 -> 타닌 중간
                $sql = $sql." AND W.tannin BETWEEN 2 AND 4";
                break;
            case 3:                                                                             // 3 -> 타닌 높음
                $sql = $sql." AND W.tannin = 5 ";
                break;
            default: break;                                                                     // 0 -> 상관없음
        }
        return $sql;
    }

    // food 카테고리에 따른 쿼리 설정
    function food_to_query($food){
        switch($food){
            case "고기" :
                $query = " AND (FI.F_URL LIKE '%chicken%' OR FI.F_URL LIKE '%cow%' OR FI.F_URL LIKE '%pig%' OR FI.F_URL LIKE '%sheep%')";
                break;
            case "해산물" :
                $query = " AND (FI.F_URL LIKE '%chellfish%' OR FI.F_URL LIKE '%fish%')";
                break;
            case "치즈" :
                $query = " AND FI.F_URL LIKE '%cheese%'";
                break;
            case "과일" :
                $query = " AND FI.F_URL LIKE '%fruit%'";
                break;
            case "디저트" :
                $query = " AND FI.F_URL LIKE '%cake%'";
                break;
            case "기타" :
                $query = " AND (FI.F_URL LIKE '%asia%' OR FI.F_URL LIKE '%noodle%' OR FI.F_URL LIKE '%pizza%' OR FI.F_URL LIKE '%bibimbap%'
                 OR FI.F_URL LIKE '%dry%' OR FI.F_URL LIKE '%salad%' OR FI.F_URL LIKE '%champagne%'OR FI.F_URL LIKE '%fried%')";
                break;
            default :
                $query = "";
            break;
        }
        return $query;
    }
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
?>