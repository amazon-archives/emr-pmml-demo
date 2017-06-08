package com.aws.sparkml.sample

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

import org.apache.spark.mllib.clustering.KMeans

object Main {
  
/*
 * Copyright 2012-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Amazon Software License (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
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