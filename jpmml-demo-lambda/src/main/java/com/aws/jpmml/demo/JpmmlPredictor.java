package com.aws.jpmml.demo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dmg.pmml.ClusteringModel;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.IOUtil;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.ClusterClassificationMap;
import org.jpmml.evaluator.ClusteringModelEvaluator;
import org.jpmml.evaluator.FieldValue;
import org.jpmml.evaluator.ModelEvaluator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

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

public class JpmmlPredictor implements RequestHandler<IrisDataRequest, IrisDataResponse> {

	private static String bucketName = "gitansh-emr-data";
	private static String key = "pmmloutput/part-00000";

	public IrisDataResponse handleRequest(IrisDataRequest request, Context context) {
		context.getLogger().log("Input: " + request.toString());

		try {

			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(new EnvironmentVariableCredentialsProvider()).build();
			S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, key));
			InputStream objectData = object.getObjectContent();
			PMML pmml = createPMMLfromFile(objectData);
			// Process the objectData stream.
			objectData.close();

			ModelEvaluator<ClusteringModel> modelEvaluator = new ClusteringModelEvaluator(pmml);
			printArgumentsOfModel(context, modelEvaluator);

			Map<FieldName, FieldValue> arguments = readArgumentsFromLine(request, modelEvaluator);

			// modelEvaluator.verify();

			Map<FieldName, ?> results = modelEvaluator.evaluate(arguments);

			FieldName targetName = modelEvaluator.getTargetField();
			Object targetValue = results.get(targetName);

			ClusterClassificationMap nodeMap = (ClusterClassificationMap) targetValue;
			IrisDataResponse response = new IrisDataResponse();
			response.setClusterId(nodeMap.getDisplayValue());
			return response;
		} catch (Exception e)
		{
			e.printStackTrace();
			// TODO Auto-generated catch block
			context.getLogger().log("error: " + e.getMessage());
			return null;
		}

	}

	public Map<FieldName, FieldValue> readArgumentsFromLine(IrisDataRequest request,
			ModelEvaluator<ClusteringModel> modelEvaluator) {
		Map<FieldName, FieldValue> arguments = new LinkedHashMap<FieldName, FieldValue>();

		FieldValue sepalLength = modelEvaluator.prepare(new FieldName("field_0"),
				request.sepalLength.isEmpty() ? 0 : request.sepalLength);
		FieldValue sepalWidth = modelEvaluator.prepare(new FieldName("field_1"),
				request.sepalWidth.isEmpty() ? 0 : request.sepalWidth);
		FieldValue petalLength = modelEvaluator.prepare(new FieldName("field_2"),
				request.petalLength.isEmpty() ? 0 : request.petalLength);
		FieldValue petalWidth = modelEvaluator.prepare(new FieldName("field_3"),
				request.petalWidth.isEmpty() ? 0 : request.petalWidth);

		arguments.put(new FieldName("field_0"), sepalLength);
		arguments.put(new FieldName("field_1"), sepalWidth);
		arguments.put(new FieldName("field_2"), petalLength);
		arguments.put(new FieldName("field_3"), petalWidth);

		return arguments;
	}

	public void printArgumentsOfModel(Context context, ModelEvaluator<ClusteringModel> modelEvaluator) {
		context.getLogger().log("### Active Fields of Model ####");
		for (FieldName fieldName : modelEvaluator.getActiveFields()) {
			context.getLogger().log("Field Name: " + fieldName);
		}
	}

	public PMML createPMMLfromFile(InputStream fileName) throws Exception {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(fileName);
		
		Node pmmlNode = doc.getElementsByTagName("PMML").item(0);
		NamedNodeMap attr = pmmlNode.getAttributes();
		Node nodeAttr = attr.getNamedItem("xmlns");
		nodeAttr.setTextContent("http://www.dmg.org/PMML-4_1");
		((Element)pmmlNode).setAttribute("version","4.1");
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StringWriter writer = new StringWriter();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(writer);
		transformer.transform(source, result);
		
		String pmmlString = writer.toString();
		InputStream stream = new ByteArrayInputStream(pmmlString.getBytes(StandardCharsets.UTF_8));
		return IOUtil.unmarshal(stream);
	}

}

