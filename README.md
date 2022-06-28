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

# main emr.serverless.MySQL2Redshift
# 参数 mysql链接 例如： jdbc:mysql://xxxx:3306/cdc_test_db 
# 参数 表名  例如：user_tb_01 
# 参数 用户名  例如：admin 
# 参数 密码  例如：admin123456 
# 参数 写入到S3的临时路径  例如： s3://xxxx/tmp/01/
# 参数 redshift url 例如：jdbc:redshift://xxxxxxx.us-east-1.redshift.amazonaws.com:5439/dev?user=admin&password=Admin123456
# 参数 redshift_iam_role 例如：arn:aws:iam::xxxxx:role/redshift-spec
# 参数 写入到Redshift表名称,不存在自动创建 例如：test_tb

# 注意pom中的redshift相关依赖, 编译时会打进去

# 命令行提交
aws emr-serverless start-job-run \
	--region  us-east-1 \
    --application-id 00f1hlvhgop3r609 \
    --execution-role-arn arn:aws:iam::xxxxx:role/serverless-exec-role \
    --job-driver '{
        "sparkSubmit": {
            "entryPoint": "s3://xxxx/emr-serverless-example-1.0-SNAPSHOT-jar-with-dependencies.jar",
            "entryPointArguments": ["jdbc:mysql://xxxxx.us-east-1.rds.amazonaws.com:3306/xxxx","user_tb_01","admin","admin123456","s3://xxxxx/testserverless/tmp/","jdbc:redshift://xxxx.us-east-1.redshift.amazonaws.com:5439/dev?user=admin&password=Admin123456","arn:aws:iam::xxxx:role/redshift-spec"，"test_tb_01"],
            "sparkSubmitParameters": "--class emr.serverless.MySQL2Redshift  --conf spark.hadoop.hive.metastore.client.factory.class=com.amazonaws.glue.catalog.metastore.AWSGlueDataCatalogHiveClientFactory"
        }
    }' \
    --configuration-overrides '{
        "monitoringConfiguration": {
           "s3MonitoringConfiguration": {
             "logUri": "s3://xxxxx/logs"
           }
        }
    }'
```