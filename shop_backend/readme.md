# 项目结构树
<pre>└── <font color="#A6A6D8"><b>src</b></font>
    ├── <font color="#A6A6D8"><b>main</b></font>
    │   ├── <font color="#A6A6D8"><b>java</b></font>
    │   │   └── <font color="#A6A6D8"><b>org</b></font>
    │   │       └── <font color="#A6A6D8"><b>csu</b></font>
    │   │           └── <font color="#A6A6D8"><b>mypetstore</b></font>
    │   │               └── <font color="#A6A6D8"><b>api</b></font>
    │   │                   ├── <font color="#A6A6D8"><b>common</b></font>// 定义回复的函数,码值
    │   │                   ├── <font color="#A6A6D8"><b>controller</b></font>// api
    │   │                   │   └── <font color="#A6A6D8"><b>front</b></font>
    │   │                   ├── <font color="#A6A6D8"><b>entity</b></font>//每个entity与table对应
    │   │                   ├── <font color="#A6A6D8"><b>persistence</b></font>//mapper类
    │   │                   ├── <font color="#A6A6D8"><b>service</b></font>// 用mybatis plus来与数据库交互
    │   │                   │   └── <font color="#A6A6D8"><b>impl</b></font>
    │   │                   └── <font color="#A6A6D8"><b>vo</b></font>//有些数据跨越多个表,采用VO
    │   └── <font color="#A6A6D8"><b>resources</b></font>
    │       ├── <font color="#A6A6D8"><b>data</b></font>
    │       ├── <font color="#A6A6D8"><b>static</b></font>
    │       └── <font color="#A6A6D8"><b>templates</b></font>
    └── <font color="#A6A6D8"><b>test</b></font>// 下面用.rest 文件测试control对应api ，也可以用postman
        └── <font color="#A6A6D8"><b>java</b></font>
            └── <font color="#A6A6D8"><b>org</b></font>
                └── <font color="#A6A6D8"><b>csu</b></font>
                    └── <font color="#A6A6D8"><b>mypetstore</b></font>
                        └── <font color="#A6A6D8"><b>api</b></font></pre>

# 项目API
## account用户
(admin应该与这个区分),这个会频繁修改
mapping:/account 

### 登陆
post /account/login
请求参数格式：x-www-form-urlencoded
请求参数说明：

| 参数名称     | 说明  | 类型     | 是否必须 | 备注        |
| -------- | --- | ------ | ---- | --------- |
| username | 用户名 | string | 是    | 1~16位非空字符 |
| password | 密码  | string | 是    | 1~16位非空字符 |

### 注册
post /account/register

请求参数格式：x-www-form-urlencoded
请求参数说明：

| 参数名称     | 说明  | 类型     | 是否必须 | 备注        |
| -------- | --- | ------ | ---- | --------- |
| username | 用户名 | string | 是    | 1~16位非空字符 |
| password | 密码  | string | 是    | 1~16位非空字符 |

## catalog 
category,product,item
mapping: /catalog

### 获取所有category
get /catalog/categories



### 获取某一category
get /catalog/categories/{categoryId}  
e.g.   
/catalog/categories/CATS

### 获取某一category的所有product
/catalog/categories/{categoryId}/products  
e.g.  
/catalog/categories/CATS/products  

### 获取某一product的所有item
/catalog/products/{productId}/item  
e.g.   
/catalog/products/AV-CB-01/item

# TODO
1. jwt返回了但是还没有使用  
2. 开发订单等admin增删改查的接口, 开发可以与其他的完全隔绝开,最后再做jwt的添加与校验
