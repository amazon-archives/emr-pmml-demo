
import java.io.IOException;

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
