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
 * Base model class for forEach activity.
 */
public class OForEach extends OActivity {
    static final long serialVersionUID = -1L;

    public OScope.Variable counterVariable;
    public boolean parallel;
    public OExpression startCounterValue;
    public OExpression finalCounterValue;
    public CompletionCondition completionCondition;

    public OScope innerScope;

    public OForEach(OProcess owner, OActivity parent) {
        super(owner, parent);
    }

    public String toString() {
        return "+{OForEach : " + name +
                ", counterName=" + counterVariable.name +
                ", parallel=" + parallel +
                ", startCounterValue=" + startCounterValue +
                ", finalCounterValue=" + finalCounterValue +
                ", completionCondition=" + (completionCondition == null ? "" : completionCondition.branchCount) + "}";
    }

    public static class CompletionCondition extends OBase {
        static final long serialVersionUID = -1L;

        public boolean successfulBranchesOnly;
        public OExpression branchCount;

        public CompletionCondition(OProcess owner) {
            super(owner);
        }
    }
}
