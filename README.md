# MicroLottoAPI




### Libraries
- Spring Data JPA (for win information)
- Spring Data Elasticsearch (for indexing store information)
- Lombok
- Jsoup (data crawling)
- Spring Boot 2.4.4
- Gradle



### Packages
- com.devh.micro.lotto.configuration : java configuration package
- com.devh.micro.lotto.constant : constant package
- com.devh.micro.lotto.controller : REST API controller package
- com.devh.micro.lotto.dto : DTO Package
- com.devh.micro.lotto.entity : Entity Package
- com.devh.micro.lotto.projection : Spring Data JPA Projection interface package
- com.devh.micro.lotto.repository : Spring Data Repository package
- com.devh.micro.lotto.result : Unifying ResponseEntity
- com.devh.micro.lotto.scheduler : Data scraping scheduler package
- com.devh.micro.lotto.service : services to process request.
- com.devh.micro.lotto.util : utility




## URL specification
```console

1. GET micro-lotto/number-count-list ( 역대 번호별 선택 횟수 정보 )
{
    "result-json": {
        "numberCountData": {
            "1": 162,
            "2": 152,
            "3": 151,
            "4": 161,
            "5": 146,
            "6": 146,
            "7": 147,
            "8": 147,
            "9": 123,
            "10": 157,
            "11": 150,
            "12": 160,
            "13": 161,
            "14": 152,
            "15": 146,
            "16": 148,
            "17": 162,
            "18": 154,
            "19": 148,
            "20": 157,
            "21": 148,
            "22": 124,
            "23": 131,
            "24": 149,
            "25": 140,
            "26": 153,
            "27": 166,
            "28": 134,
            "29": 128,
            "30": 144,
            "31": 151,
            "32": 134,
            "33": 159,
            "34": 164,
            "35": 144,
            "36": 146,
            "37": 151,
            "38": 154,
            "39": 158,
            "40": 155,
            "41": 132,
            "42": 145,
            "43": 174,
            "44": 144,
            "45": 148
        }
    }
}


2. GET micro-lotto/store-information/address1 ( 행정구역 1에 해당하는 1등 배출 판매점 분포 )
{
    "result-json": [
        {
            "address1": "강원",
            "total": 1064
        },
        {
            "address1": "경기",
            "total": 9263
        },
        {
            "address1": "경남",
            "total": 2378
        },
        {
            "address1": "경북",
            "total": 1606
        },
        {
            "address1": "광주",
            "total": 1048
        },
        {
            "address1": "대구",
            "total": 1787
        },
        {
            "address1": "대전",
            "total": 1107
        },
        {
            "address1": "동행복권(dhlottery.co.kr)",
            "total": 125
        },
        {
            "address1": "부산",
            "total": 2627
        },
        {
            "address1": "서울",
            "total": 7928
        },
        {
            "address1": "서울특별시",
            "total": 11
        },
        {
            "address1": "세종",
            "total": 94
        },
        {
            "address1": "울산",
            "total": 904
        },
        {
            "address1": "인천",
            "total": 2131
        },
        {
            "address1": "전남",
            "total": 1141
        },
        {
            "address1": "전북",
            "total": 1168
        },
        {
            "address1": "제주",
            "total": 400
        },
        {
            "address1": "충남",
            "total": 1781
        },
        {
            "address1": "충북",
            "total": 1179
        }
    ]
}

3. GET micro-lotto/store-information/address2/충남 ( 행정구역 1이 충남인 행정구역 2의 1등 배출 판매점 분포 )
{
    "result-json": [
        {
            "address2": "계룡시",
            "total": 21
        },
        {
            "address2": "공주시",
            "total": 64
        },
        {
            "address2": "금산군",
            "total": 26
        },
        {
            "address2": "논산시",
            "total": 93
        },
        {
            "address2": "당진시",
            "total": 123
        },
        {
            "address2": "보령시",
            "total": 83
        },
        {
            "address2": "부여군",
            "total": 39
        },
        {
            "address2": "서산시",
            "total": 155
        },
        {
            "address2": "서천군",
            "total": 58
        },
        {
            "address2": "아산시",
            "total": 395
        },
        {
            "address2": "연기군",
            "total": 3
        },
        {
            "address2": "예산군",
            "total": 58
        },
        {
            "address2": "천안시",
            "total": 537
        },
        {
            "address2": "청양군",
            "total": 10
        },
        {
            "address2": "태안군",
            "total": 29
        },
        {
            "address2": "홍성군",
            "total": 87
        }
    ]
}

4. GET micro-lotto/store-information/address3/충남/연기군 ( 행정구역 1이 충남, 행정구역 2가 연기군인 행정구역 3의 1등 배출 판매점 분포 )
{
    "result-json": [
        {
            "rowId": "386_2_47",
            "rank": 2,
            "method": "",
            "storeNumber": 47,
            "storeName": "세종로또복권방",
            "storeAddress": "충남 연기군 금남면 용포리 88-17",
            "storeAddress1": "충남",
            "storeAddress2": "연기군",
            "storeAddress3": "금남면",
            "storeLatitude": 36.4622889,
            "storeLongitude": 127.280414,
            "storePhone": "",
            "storeMapId": "14410089",
            "turn": 386,
            "mapForElasticsearch": {
                "store_address1": "충남",
                "method": "",
                "store_address3": "금남면",
                "store_address2": "연기군",
                "store_address": "충남 연기군 금남면 용포리 88-17",
                "turn": 386,
                "store_map_id": "14410089",
                "store_phone": "",
                "store_number": 47,
                "rank": 2,
                "store_name": "세종로또복권방",
                "row_id": "386_2_47",
                "store_location": {
                    "lon": 127.280414,
                    "lat": 36.4622889
                }
            }
        },
        {
            "rowId": "471_1_5",
            "rank": 1,
            "method": "auto",
            "storeNumber": 5,
            "storeName": "세종로또복권방",
            "storeAddress": "충남 연기군 금남면 용포리 88-17",
            "storeAddress1": "충남",
            "storeAddress2": "연기군",
            "storeAddress3": "금남면",
            "storeLatitude": 36.4622889,
            "storeLongitude": 127.280414,
            "storePhone": "",
            "storeMapId": "14410089",
            "turn": 471,
            "mapForElasticsearch": {
                "store_address1": "충남",
                "method": "auto",
                "store_address3": "금남면",
                "store_address2": "연기군",
                "store_address": "충남 연기군 금남면 용포리 88-17",
                "turn": 471,
                "store_map_id": "14410089",
                "store_phone": "",
                "store_number": 5,
                "rank": 1,
                "store_name": "세종로또복권방",
                "row_id": "471_1_5",
                "store_location": {
                    "lon": 127.280414,
                    "lat": 36.4622889
                }
            }
        },
        {
            "rowId": "489_2_44",
            "rank": 2,
            "method": "",
            "storeNumber": 44,
            "storeName": "세종로또복권방",
            "storeAddress": "충남 연기군 금남면 용포리88-17",
            "storeAddress1": "충남",
            "storeAddress2": "연기군",
            "storeAddress3": "금남면",
            "storeLatitude": 36.4622889,
            "storeLongitude": 127.280414,
            "storePhone": "",
            "storeMapId": "14410089",
            "turn": 489,
            "mapForElasticsearch": {
                "store_address1": "충남",
                "method": "",
                "store_address3": "금남면",
                "store_address2": "연기군",
                "store_address": "충남 연기군 금남면 용포리88-17",
                "turn": 489,
                "store_map_id": "14410089",
                "store_phone": "",
                "store_number": 44,
                "rank": 2,
                "store_name": "세종로또복권방",
                "row_id": "489_2_44",
                "store_location": {
                    "lon": 127.280414,
                    "lat": 36.4622889
                }
            }
        }
    ]
}

```
