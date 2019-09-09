概述，在大数据的收集场景中，我们希望找到一款工具，对海量的数据进行收集和检索
使用的工具有es和mongo，本项目对es进行整合测试。

其他知识:
9300端口： ES节点之间通讯使用
9200端口： ES节点 和 外部 通讯使用

默认常用的api:
查看所有节点&插件:
GET:http://localhost:9200/_nodes?pretty
查看所有数据:
GET:http://localhost:9200/_search
查看mapping信息:
http://your_cluster:9200/_index/_type/_id?pretty
GET:http://localhost:9200/cityindex/city/gFY702UBnUMlRnE3owkZ?pretty
> _id为http://localhost:9200/_search数据中的id

常用的api操作参考:
https://blog.csdn.net/lijingyao8206/article/details/78614536
最全api操作参考:
https://blog.csdn.net/trusause/article/details/79583873
https://blog.csdn.net/qq_38974634/article/details/80740430
FunctionScoreQuery参考：
https://blog.csdn.net/wwd0501/article/details/78652850
https://blog.csdn.net/weixin_40341116/article/details/80913045
setting & mapping参考：
https://www.cnblogs.com/zlslch/p/6474424.html

jpa操作方式参考:
https://blog.csdn.net/larger5/article/details/79777319
注解解释参考:
https://blog.csdn.net/tianyaleixiaowu/article/details/72833940
Elasticsearch 和插件 elasticsearch-head安装参考:
https://www.bysocket.com/?p=1744
系列教材(分词器):
http://www.sojson.com/blog/82.html

spring data elasticsearch的api:
https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html
https://github.com/spring-projects/spring-data-elasticsearch

elasticsearch的api:
https://endymecy.gitbooks.io/elasticsearch-guide-chinese/content/java-api/get-api.html


整合参考：
https://juejin.im/post/5d390fece51d45777a126280?utm_source=gold_browser_extension


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

