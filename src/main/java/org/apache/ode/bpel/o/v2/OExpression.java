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

/**
 * Base class for compiled expressions. The exact form of a compiled expression is
 * dependent on the compiler implementation.
 */
public abstract class OExpression extends OBase {
    static final long serialVersionUID = -1L  ;

    public OExpressionLanguage expressionLanguage;

    public OExpression(OProcess owner) {
        super(owner);
    }

    /** Get the expression language used to generate this expression. */
    public OExpressionLanguage getExpressionLanguage() {
        return expressionLanguage;
    }

}
