
import java.io.IOException;

/*
 * Copyright 2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.aws.jpmml.demo.IrisDataRequest;
import com.aws.jpmml.demo.IrisDataResponse;
import com.aws.jpmml.demo.JpmmlPredictor;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {

   
    @BeforeClass
    public static void createInput() throws IOException {
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testLambdaFunctionHandler() {
        JpmmlPredictor handler = new JpmmlPredictor();
        Context ctx = createContext();
        IrisDataRequest request = new IrisDataRequest();
       
        request.setSepalLength("6.3");
        request.setSepalWidth("2.7");
        request.setPetalLength("4.9");
        request.setPetalWidth("1.8");
    	
        
        IrisDataResponse output = handler.handleRequest(request, ctx);
        if(output != null)
        	System.out.println(output.getClusterId());

        // TODO: validate output here if needed.
        
    }
}
