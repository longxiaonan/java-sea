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




