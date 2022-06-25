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

# 注意，EMR上写入S3, 不需要使用s3a,只有本地调试才需要，打包的时候也不需要将S3A的依赖打进去，比如hadoop-aws,aws-java-sdk-s3都不需要，具体看pom文件即可
# 打包后使用 ***-jar-with-dependencies.jar的包，这里可以有所需的依赖，比如mysql connector，本例中的connector是链接mysql8.x的

```