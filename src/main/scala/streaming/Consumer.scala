package streaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger.ProcessingTime
//import org.apache.spark
object Consumer {
  def main(args:Array[String]): Unit = {

    /** defining a spark session */
    val spark = SparkSession.builder().appName("clickStreamConsumer")
      .master("local[*]")
      .config("spark.sql.shuffle.partitions","2")
      .getOrCreate()

    /** setting log level to ERROR */
    spark.sparkContext.setLogLevel("ERROR")

    /** reading from kafka topic "writeStream" */
    val df  = spark.readStream.format("kafka")
      .option("kafka.bootstrap.servers","localhost:9092")
      .option("subscribe","writeStream")
      .load()
      .selectExpr("CAST(value AS STRING)")
      /** writing the read dataframe to the output directory */
      .writeStream
      .trigger(ProcessingTime("30 seconds"))
      .format("csv")
      .option("path","data/output")
      .option("checkpointLocation","data/outputCheck")
      .start()

    df.awaitTermination()




  }
}
