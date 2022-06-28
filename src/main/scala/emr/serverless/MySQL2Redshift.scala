package emr.serverless

import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

object MySQL2Redshift {

  def main(args: Array[String]): Unit = {
    val url = args(0)
    val table = args(1)
    val user = args(2)
    val pwd = args(3)
    val s3_path = args(4)
    //"jdbc:redshift://redshifthost:5439/database?user=username&password=pass
    val redshift_conn = args(5)
    val iam_role = args(6)
    val redshift_table = args(7)
    val conf = new SparkConf()
      .setAppName("MySQL to Redshift")
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

    df.write
      .format("io.github.spark_redshift_community.spark.redshift")
      .option("url", redshift_conn)
      .option("dbtable", redshift_table)
      .option("aws_iam_role",iam_role)
      .option("tempdir", s3_path)
      .mode(SaveMode.Overwrite)
      .save()

    spark.stop()
  }


}
