
### 下面通过es默认的api进行操作
#### 获取刚刚添加的信息：
GET: http://localhost:9200/product/book/7
获取结果：
{
"_index": "product",
"_type": "book",
"_id": "7",
"_version": 2,
"found": true,
"_source":{
    "id": "7",
    "name": "海洋污染控制",
    "message": "针对工业化过程中的污染，通过新科技避免海洋的污染控制",
    "type": "环境"
    }
}
#### 更新书本信息：
PUT: http://localhost:9200/product/book/7
body参数：
 {
 "id":"7",
 "name":"海洋污染控制1",
 "message":"针对工业化过程中的污染，通过新科技避免海洋的污染控制",
 "type":"环境"
}
#### 再次获取结果：
GET: http://localhost:9200/product/book/7
获取的结果：
{
"_index": "product",
"_type": "book",
"_id": "7",
"_version": 2,
"found": true,
"_source":{
    "id": "7",
    "name": "海洋污染控制1",
    "message": "针对工业化过程中的污染，通过新科技避免海洋的污染控制",
    "type": "环境"
    }
}
#### 删除该书本信息：
DELETE: http://localhost:9200/product/book/7
反馈的结果：
{
"_index": "product",
"_type": "book",
"_id": "7",
"_version": 3,
"result": "deleted",
"_shards":{
    "total": 2,
          "successful": 1,
          "failed": 0
          },
"_seq_no": 3,
"_primary_term": 1
}
#### 再次获取该书本信息：
GET: http://localhost:9200/product/book/7
404报错，且反馈的结果：
{
"_index": "product",
"_type": "book",
"_id": "7",
"found": false
}


测试:
### 通过我们的api进行测试，swagger的地址如下:
http://localhost:8080/swagger-ui.html#/
添加书本信息：
POST：http://localhost:9200/product/book/7
body参数：
 {
 "id":"7",
 "name":"海洋污染控制",
 "message":"针对工业化过程中的污染，通过新科技避免海洋的污染控制",
 "type":"环境"
}
#### 添加
POST: http://127.0.0.1:8080/book/insert
body参数：
 {
 "id":"8",
 "name":"会计专家",
 "message":"会计专业经典案例",
 "type":"会计"
}
#### 删除
删除所有book数据和结构
http://127.0.0.1:9200/product
删除所有book数据
http://127.0.0.1:8080/book/deleteAll

