/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.ode.bpel.o.v2;

import java.util.HashMap;
import java.util.Map;

/**
 * Compiled representation of an expression language dependency.
 */
public class OExpressionLanguage extends OBase {
    private static final long serialVersionUID = 1L;
    public String expressionLanguageUri;
    public final Map<String,String> properties = new HashMap<String,String>();

    public OExpressionLanguage(OProcess owner, Map<String,String> properties) {
        super(owner);
        if (properties != null)
            this.properties.putAll(properties);
    }

    public boolean equals(Object obj) {
        if (obj instanceof OExpressionLanguage) return ((OExpressionLanguage)obj).expressionLanguageUri.equals(expressionLanguageUri);
        else return super.equals(obj);
    }

    public int hashCode() {
        return expressionLanguageUri.hashCode();
    }
}
