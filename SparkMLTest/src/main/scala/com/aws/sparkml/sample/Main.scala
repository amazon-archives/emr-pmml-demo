package com.aws.sparkml.sample

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

import org.apache.spark.mllib.clustering.KMeans

object Main {
  
/*
 * Copyright 2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */
  
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf(true).setAppName("spark-pmml-demo")
    val sc = new SparkContext(conf)
    val inputFile: String = args(0)
    val outputFile: String = args(1)


    val iris_data = sc.textFile(inputFile)
    val parsedData = iris_data.map(_.split(",").dropRight(1).map(_.toDouble)).map(Vectors.dense(_)).cache()
    
    
    val numClusters = 3
    val numIterations = 40
    val clusters = KMeans.train(parsedData, numClusters, numIterations)
    clusters.toPMML(sc, outputFile)
    
    }

}