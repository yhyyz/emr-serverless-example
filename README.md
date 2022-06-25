### EMR Serverless Example

```shell
# mysql to s3 exmpale 编译，注意编译使用provided
mvn clean package -Dscope.type=provided

# main emr.serverless.MySQL2S3  
# 参数 mysql链接 例如： jdbc:mysql://xxxx:3306/cdc_test_db 
# 参数 表名  例如：user_tb_01 
# 参数 用户名  例如：admin 
# 参数 密码  例如：admin123456 
# 参数 写入到S3的路径  例如： s3://xxxx/test/01/

```