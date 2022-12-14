import requests
from bs4 import BeautifulSoup
from pathlib import Path
import pymysql
import csv
import re
import time
import random

aromaimg = {}
foodimg = {}
urllist = {
    "aroma-flower" : "wine21.com/02_images/icon/ico-flower.png",
    "aroma-stone" : "wine21.com/02_images/icon/ico-stone.png",
    "aroma-lemon" : "wine21.com/02_images/icon/ico-lemon.png",
    "aroma-fruit" : "wine21.com/02_images/icon/ico-fruit.png",
    "aroma-berry" : "wine21.com/02_images/icon/ico-berry.png",
    "aroma-apple" : "wine21.com/02_images/icon/ico-apple.png",
    "aroma-ripen" : "wine21.com/02_images/icon/ico-ripen.png",
    "aroma-cinnamon" : "wine21.com/02_images/icon/ico-cinnamon.png",
    "aroma-almond" : "wine21.com/02_images/icon/ico-almond.png",
    "aroma-oak" : "wine21.com/02_images/icon/ico-oak.png",
    "aroma-croissant" : "wine21.com/02_images/icon/ico-corissant.png",
    "aroma-pineapple" : "wine21.com/02_images/icon/ico-pineapple.png",
    "aroma-paprika" : "wine21.com/02_images/icon/ico-paprika.png",
    "aroma-herbal" : "wine21.com/02_images/icon/ico-herbal.png",
    "food-chellfish" : "wine21.com/02_images/icon/ico-food-chellfish.png",
    "food-dry": "wine21.com/02_images/icon/ico-food-dry.png",
    "food-dry2": "wine21.com/02_images/icon/ico-food-dry.png",
    "food-fruit": "wine21.com/02_images/icon/ico-food-fruit.png",
    "food-noodle": "wine21.com/02_images/icon/ico-food-noodle.png",
    "food-chicken": "wine21.com/02_images/icon/ico-food-chicken.png",
    "food-pig": "wine21.com/02_images/icon/ico-food-pig.png",
    "food-raisin": "wine21.com/02_images/icon/ico-food-raisin.png",
    "food-bibimbap": "wine21.com/02_images/icon/ico-food-bibimbap.png",
    "food-salami": "wine21.com/02_images/icon/ico-food-salami.png",
    "food-salad": "wine21.com/02_images/icon/ico-food-salad.png",
    "food-fish": "wine21.com/02_images/icon/ico-food-fish.png",
    "food-champagne": "wine21.com/02_images/icon/ico-food-champagne.png",
    "food-cow": "wine21.com/02_images/icon/ico-food-cow.png",
    "food-asia": "wine21.com/02_images/icon/ico-food-asia.png",
    "food-sheep": "wine21.com/02_images/icon/ico-food-sheep.png",
    "food-cheese": "wine21.com/02_images/icon/ico-food-cheese.png",
    "food-cake": "wine21.com/02_images/icon/ico-food-cake.png",
    "food-fried": "wine21.com/02_images/icon/ico-food-fried.png",
    "food-pizza": "wine21.com/02_images/icon/ico-food-pizza.png",
    "food-walnut": "wine21.com/02_images/icon/ico-food-walnut.png",
}

class Wine:
    def __init__(self, id):
        self.id = id
        self.name = ""
        self.type = ""
        self.region = ""
        self.region2 = ""
        self.price = 0
        self.capacity = 0
        self.sweetness = 0
        self.acidity = 0
        self.body = 0
        self.tannin = 0
        self.manufacturer = ""
        self.variety = ""
        self.temperature = ""
        self.alcohol  = 0.0
        self.aroma = []
        self.food = []
        self.url = ""

    #id , name, type, region, region2
    #price, capacity, sweetness, acidity, body, tannin
    #manufacturer, variety, temperature, alcohol
    #aroma count, aromas
    #food count, foods
    def __str__(self): 
        rtstring = f"{self.id}, {self.name}, {self.type}, {self.region}, {self.region2}, {self.price}, {self.capacity}, {self.sweetness}, {self.acidity}, {self.body}, {self.tannin}, {self.manufacturer}, {self.variety}, {self.temperature}, {self.alcohol}, {len(self.aroma)}, {self.aroma}, {len(self.food)}, {self.food}"
        return rtstring

    def __repr__(self):
        return self.__str__()

    def check_valid(self):
        if self.name == "":
            return False
        elif self.price == 0:
            return False
        elif self.capacity == 0:
            return False
        elif len(self.aroma) == 0:
            return False
        elif len(self.food) == 0:
            return False
        return True

    #using insert db
    def get_wdata(self):
        return f"{self.id}, \"{self.name}\", \"{self.type}\", \"{self.region}\", \"{self.region2}\", {self.price}, {self.capacity}, {self.sweetness}, {self.acidity}, {self.body}, {self.tannin}, \"{self.manufacturer}\", \"{self.variety}\", \"{self.temperature}\", {self.alcohol}"

#idx s~e parsing
def parser(s, e):
    idx = s
    result = []
    aroma = []
    food = []
    while idx < e:
        idx+=1
        print(idx)
        wine = Wine(idx)
        url = "https://www.wine21.com/13_search/wine_view.html?Idx=" + str(idx) + "&lq=LIST"
        try:
            page = requests.get(url, timeout=5)
        except:
            time.sleep(10)
            page = requests.get(url, timeout=5)
        soup = BeautifulSoup(page.text, 'html.parser')
        if soup.title is None:
            continue
        tmp = get_wdata(soup, wine)
        if tmp.check_valid():
            result.append(tmp)
    return result


    # id, name, type, region, region2, price, capacity, 
    # sweetness, acidity, body, tannin, manufacturer, variety, temperature, alcohol
    # from wine21.com
def get_wdata(soup, wine):
    wine.name = soup.select_one("body > section > div.inner > div.clear > div.wine-top-right > dl > dt").get_text()
    wine.type = soup.select_one("body > section > div.inner > div.clear > div.wine-top-right > div.bagde-box > p").findChild().get_text()

    try:
        url = soup.select_one("body > section > div.inner > div.clear > div.wine-top-left > div.swiper-container > div.swiper-wrapper > div > img")['src']
    except:
        url = ""
    wine.url = url

    try:
        region = soup.select_one("body > section > div.inner > div.clear > div.wine-top-right > div.bagde-box > p > span:nth-child(2)").get_text()
    except:
        region = ""
    wine.region = region

    try:
        region2 = soup.select_one("body > section > div.inner > div.clear > div.wine-top-right > div.bagde-box > p > span:nth-child(3)").get_text()
    except:
        region2 = ""
    wine.region2 = region2

    price = soup.select_one("body > section > div.inner > div.clear > div.wine-top-right > p.wine-price > strong").get_text()
    try:
        price = int(re.sub(r'[^0-9]', '', price))
        wine.price = price
    except:
        wine.price = 0


    capacity = soup.select_one("body > section > div.inner > div.clear > div.wine-top-right > p.wine-price").get_text()
    point = capacity.find("ml")
    try:
        capacity = int(capacity[point-3:point])
    except:
        capacity = 0
    wine.capacity = capacity

    #하위 a태그에 on class가 몇개있는지
    sweetness = soup.select_one("body > section > div.inner > div.clear > div.wine-top-right > div.wine-components > ul > li:nth-child(1) > div")
    wine.sweetness = counting(sweetness)
    acidity = soup.select_one("body > section > div.inner > div.clear > div.wine-top-right > div.wine-components > ul > li:nth-child(2) > div")
    wine.acidity = counting(acidity)
    body = soup.select_one("body > section > div.inner > div.clear > div.wine-top-right > div.wine-components > ul > li:nth-child(3) > div")
    wine.body = counting(body)
    tannin = soup.select_one("body > section > div.inner > div.clear > div.wine-top-right > div.wine-components > ul > li:nth-child(4) > div")
    wine.tannin = counting(tannin)

    #aroma 하위 list요소 하나씩 가져오기
    aroma = soup.select_one("body > section > div.inner > div.clear > div.wine-top-right > div.wine-top-right-inner > div:nth-child(1) > div.wine-matching-list.swiper-container > ul")
    if aroma is not None:
        wine.aroma = get_child(aroma, True)
    food = soup.select_one("body > section > div.inner > div.clear > div.wine-top-right > div.wine-top-right-inner > div:nth-child(2) > div.wine-matching-list.swiper-container > ul")
    if food is not None:
        wine.food = get_child(food, False)

    #info 박스에서 해당 dl의 dt text가 맞다면 dd text값 저장
    info = soup.select_one("#detail > div > div").find_all('dl')
    for a in info:
        if a.dt.get_text() =="생산자":
            wine.manufacturer = a.dd.get_text()
        elif a.dt.get_text() =="주요품종":
            wine.variety = a.dd.get_text()
        elif a.dt.get_text() =="음용온도":
            wine.temperature = a.dd.get_text()
        elif a.dt.get_text() =="알코올":
            tmp = a.dd.get_text()
            point = -1
            if tmp.find('~') > 0:
                point = tmp.find('~')
                wine.alcohol = tmp[:point]
            else:
                wine.alcohol = tmp[:-1]
        
    return wine

#당도, 산도, 바디, 타닌 세기
def counting(div):
    tmps = div.findChildren()
    cnt = 0
    for tmp in tmps:
        attrs = tmp.get('class')
        if attrs == None:
            continue
        for attr in attrs:
            if attr == 'on':
                cnt+=1
    return cnt

#aroma, food list만큼 가져오기
def get_child(ul, isaroma):
    result = []
    tmps = ul.findChildren()
    if tmps == None:
        return result
    for tmp in tmps:
        if tmp.name == 'li':
            name = tmp.get_text()
            em = tmp.em['class'][0]
            result.append(name)
            if isaroma:
                if name not in aromaimg:
                    aromaimg[name] = urllist[em]
            else:
                if name not in foodimg:
                    foodimg[name] = urllist[em]
    return result


#wdata from csv
#type <class 'str'>
def read_csv():
    result = {}
    with open(Path(__file__).with_name('wdata.csv'), 'r', encoding='UTF-8') as csvfile:
        reader = csv.reader(csvfile, delimiter='\t', quotechar='|')
        for row in reader:
            if len(row)==0:
                continue
            result.append('1'.join(row))
    return result

#wdata to csv
def save_csv(wlist):
    with open(Path(__file__).with_name('wdata.csv'), 'w', encoding='UTF-8') as csvfile:
        writer = csv.DictWriter(csvfile, fieldnames=['id', 'name', 'type', 'region', 'region2', 'price', 'capacity', 
        'sweetness', 'acidity', 'body', 'tannin', 'manufacturer', 'variety', 'temperature', 'alcohol', 'aroma', 'food', 'url'])
        writer.writeheader()
        for wdata in wlist:
            tmp = wdata.__dict__
            writer.writerow(tmp)

#WI to WIU
def WItoWIU():
    db = pymysql.connect(
                    host='first-database.cyad2xjmjeuv.ap-northeast-2.rds.amazonaws.com',
                    user='admin',
                    passwd='1q2w3e4r',
                    port=3306,
                    db='first_database',
                    charset='utf8'
                    )
    sql = "SELECT W.Wid, w_url FROM WINE W, WINE_IMAGE WI WHERE W.Wid = WI.Wid"
    sql_list = []

    with db:
        with db.cursor() as cur:
            cur.execute(sql)
            result = cur.fetchall()
            for data in result:
                cur.execute("INSERT INTO WINE_IMAGE_URL VALUES("+ str(data[0]) + ", \"" + data[1] + "\")")
                print(data[0])
            cur.close()
        db.commit()
    db.close()


#n개의 데이터 추가 
def insert_sales(size):
    db = pymysql.connect(
                    host='first-database.cyad2xjmjeuv.ap-northeast-2.rds.amazonaws.com',
                    user='admin',
                    passwd='1q2w3e4r',
                    port=3306,
                    db='first_database',
                    charset='utf8'
                    )
    widlist = []
    sql = "SELECT Wid FROM WINE LIMIT 1000"
    day = ['01','02','03','04','05','06','07','08','09','10']

    with db:
        with db.cursor() as cur:
            cur.execute(sql)
            widlist = cur.fetchall()
            sql_list = []
            for i in range(size):
                wid = random.choice(widlist)
                wid = wid[0]
                quantity = random.randrange(1,3)
                sql = "INSERT INTO SALES VALUES ({0}, {1}, {2})".format(wid, quantity, "\'2022-12-{0}\'".format(random.choice(day)))
                sql_list.append(sql)
            for sql in sql_list:
                print(sql)
                cur.execute(sql)
        db.commit()

    


def insert_db(data):
    # AWS RDS 연결하기
    db = pymysql.connect(
                    host='first-database.cyad2xjmjeuv.ap-northeast-2.rds.amazonaws.com',
                    user='admin',
                    passwd='1q2w3e4r',
                    port=3306,
                    db='first_database',
                    charset='utf8'
                    )
    cursor = db.cursor()

    for a in aromaimg.items():
        try:
            sql = "INSERT INTO AROMA_IMAGE VALUES(\"" + a[0] + "\", \"" + a[1] + "\")"
            print(sql)
            #cursor.execute(sql)
        except:
            continue

    for f in foodimg.items():
        try:
            sql = "INSERT INTO FOOD_IMAGE VALUES(\"" + f[0] + "\", \"" + f[1] + "\")"
            print(sql)
            #cursor.execute(sql)
        except:
            continue

    for d in data:
        try:
            values = d.get_wdata()
            sql = "INSERT INTO WINE VALUES(" + values +")"
            print(sql)
            #cursor.execute(sql)
            for a in d.aroma:
                sql = "INSERT INTO AROMA VALUES("+ str(d.id) +", \"" + a + "\")"
                print(sql)
                #cursor.execute(sql)
            for f in d.food:
                sql = "INSERT INTO FOOD VALUES("+ str(d.id) +", \"" + f + "\")"
                print(sql)
                #cursor.execute(sql)
            try:
                sql = "INSERT INTO WINE_IMAGE_URL VALUES("+ str(d.id) +", \"" + d.url + "\")"
                print(sql)
                #cursor.execute(sql)
            except:
                continue
        except:
            continue

    db.commit()
    

    cursor.close()
    db.close()

if __name__ == "__main__":
    # wdata = read_csv()
    # print (type(wdata))
    # for w in wdata:
    #     print(w)
    #     print(type(w))
    #wdata = parser(140000, 172633)
    wdata = parser(140000, 140010)
    insert_db(wdata)

    #get url data on mysql
    #WItoWIU()
    #insert_sales()
    