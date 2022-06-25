package emr.serverless

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object MySQL2S3 {

  def main(args: Array[String]): Unit = {
    val url = args(0)
    val table = args(1)
    val user = args(2)
    val pwd = args(3)
    val s3_path = args(4)
    val conf = new SparkConf()
      .setAppName("MySQL to S3")
//     .setMaster("local[6]")
//     .set("spark.hadoop.fs.s3a.aws.credentials.provider", "org.apache.hadoop.fs.s3a.SimpleAWSCredentialsProvider")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")

    val spark = SparkSession
      .builder()
      .config(conf)
      .enableHiveSupport()
      .getOrCreate()

    val df = spark.read
    .format("jdbc")
    .option("driver", "com.mysql.jdbc.Driver")
    .option("url", url)
    .option("dbtable",table)
    .option("user", user)
    .option("password", pwd)
    .load()

    df.write.mode("overwrite")parquet(s3_path)

    spark.stop()
  }


}
