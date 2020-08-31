### 包结构：

controller：

​	测试类入口, 包括导入导出测试，导出合并测试

entity：

​	测试时需要的实体类

relase：

​	将导入导出简单封装

helpful：

​	参考用的工具类

converter：

    导出导入时，进行类型转换，比如：1和男，2和女 相互转换



### 在vue - element ui中使用

``` html
<template>
    <el-table style="width: 100%"
              stripe border
              :height="table.height"
              :data="table.list"
              size="mini"
              :highlight-current-row="true"
              :fit="true"
              :cell-style="tableCellStyle"
              :span-method="cellMerge"
              >
        ...
    </el-table>
</template>
<script>
export default {
  methods: {
    tableCellStyle ({row, column, rowIndex, columnIndex}) {
      if (columnIndex >= 8) {
        return {background: 'lavender'}
      }
      return ''
    },
    count (rows) {
      this.arra = new Map()
      this.mergeArr = []
      for (var j = 0; j < rows.length; j++) {
        let code = rows[j].detailId
        if (this.arra.has(code)) {
          let num = this.arra.get(code)
          num++
          this.arra.delete(code)
          this.arra.set(code, num)
        } else {
          this.arra.set(code, 1)
        }
      }
      for (let key of this.arra.keys()) {
        this.mergeArr.push(this.arra.get(key))
        for (var i = 0; i < this.arra.get(key) - 1; i++) {
          this.mergeArr.push(0)
        }
      }
    },
    cellMerge ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex <= 7) { // 用于设置要合并的列
        const row1 = this.mergeArr[rowIndex]
        const col1 = row1 > 0 ? 1 : 0
        return {
          rowspan: row1, // 合并的行数
          colspan: col1 // 合并的列数，设为０则直接不显示
        }
      }
    },
    // 获取列表数据
    requestList () {
      Api.listPickInfo({search: search}, response => {
        let status = response.status || 0
        let body = response.data || []
        if (status >= 200 && status <= 204) {
          this.count(body.data)
          this.table.list = body.data
        }
      }
    }
    ...
  }
}
```



测试访问：
http://localhost:8080/

文件上传大小限制：

Spring Boot1.4版本后配置更改为:

spring.http.multipart.maxFileSize = 10Mb

spring.http.multipart.maxRequestSize=100Mb

Spring Boot2.0之后的版本配置修改为:

spring.servlet.multipart.max-file-size = 10MB

spring.servlet.multipart.max-request-size=100MB
