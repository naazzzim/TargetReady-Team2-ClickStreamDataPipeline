package streaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Producer {
  def main(args:Array[String]): Unit = {

    /** Defining a spark Session */
    val spark = SparkSession.builder().appName("clickStreamConsumer")
      .master("local[*]")
      .config("spark.sql.shuffle.partitions", "2")
      .getOrCreate()

    /** setting log level to error */
    spark.sparkContext.setLogLevel("ERROR")

//    /** defined schema for the dataset */
//    val schema  = StructType(
//      List(
//        StructField("item_id",StringType,nullable = true),
//        StructField("item_price",FloatType,nullable = true),
//        StructField("product_type",StringType,nullable = true),
//        StructField("department_name",StringType,nullable = true)
//      )
//    )

    /** read the dataset from input directory */
  val df  = spark
//    .readStream
    .read
    .format("csv")
//    .schema(schema)
    .option("header","true")
    .load("data/Input/item/item_data.csv")

  /** altering the dataframe inorder to send through kafka topic "writeStream" */
    val dfSend  = df.select(concat(
      col("item_id"),
      lit(","),
      col("item_price"),
      lit(","),
      col("product_type"),
      lit(","),
      col("department_name"))
      .as("value")
    )

      /** sending the data through kafka */
      dfSend
      .selectExpr("CAST(value AS STRING)")
      .write
      .format("kafka")
      .option("kafka.bootstrap.servers","localhost:9092")
      .option("topic","writeStream")
//      .option("","")
      .save()


  }
}
