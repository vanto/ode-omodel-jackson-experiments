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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.wsdl.Operation;

/**
 * Compiled rerperesentation of the BPEL <code>&lt;pick&gt;</code> and
 * <codE>&lt;receive&gt;</code> activities. Because the latter is essentially
 * a simplified version of the former, at run-time we do not distinguish
 * between the two.
 */
public class OPickReceive extends org.apache.ode.bpel.o.v2.OActivity {
    static final long serialVersionUID = -1L  ;
    public final List<OnMessage> onMessages  = new ArrayList<OnMessage>();
    public final List<OnAlarm> onAlarms = new ArrayList<OnAlarm>();

    public boolean createInstanceFlag;

    public OPickReceive(OProcess owner, org.apache.ode.bpel.o.v2.OActivity parent) {
        super(owner, parent);
    }

    private OPickReceive() {
    	this(null, null);
    }

    public static class OnAlarm extends OBase {
        static final long serialVersionUID = -1L  ;
        public org.apache.ode.bpel.o.v2.OActivity activity;
        public OExpression forExpr;
        public OExpression untilExpr;

        public OnAlarm(OProcess owner) {
            super(owner);
        }
        
        private OnAlarm() {
        	this(null);
        }
    }

    public static class OnMessage extends OBase {
        static final long serialVersionUID = -1L  ;

        /** Correlations to initialize. */
        public final List<org.apache.ode.bpel.o.v2.OScope.CorrelationSet> initCorrelations = new ArrayList<org.apache.ode.bpel.o.v2.OScope.CorrelationSet>();

        /** Correlations to match on. */
        public List<org.apache.ode.bpel.o.v2.OScope.CorrelationSet> matchCorrelations = new ArrayList<org.apache.ode.bpel.o.v2.OScope.CorrelationSet>();
        // left out for backward-compatibility, java serialization is lenient about scope
        private org.apache.ode.bpel.o.v2.OScope.CorrelationSet matchCorrelation;

        /** Correlations to join on. */
        public final List<org.apache.ode.bpel.o.v2.OScope.CorrelationSet> joinCorrelations = new ArrayList<org.apache.ode.bpel.o.v2.OScope.CorrelationSet>();
        // left out for backward-compatibility, java serialization is lenient about scope
        private org.apache.ode.bpel.o.v2.OScope.CorrelationSet joinCorrelation;

        public org.apache.ode.bpel.o.v2.OPartnerLink partnerLink;
        public Operation operation;
        public org.apache.ode.bpel.o.v2.OScope.Variable variable;
        public org.apache.ode.bpel.o.v2.OActivity activity;

        /** OASIS addition for disambiguating receives (optional). */
        public String messageExchangeId = "";

        public String route = "one";

        public OnMessage(OProcess owner) {
            super(owner);
        }
        
        private OnMessage() {
        	this(null);
        }

        public String getCorrelatorId() {
            return partnerLink.getId() + "." + operation.getName();
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();

            // backward compatibility; matchCorrelation could have a value if read from old definition
            if (matchCorrelations == null) matchCorrelations = new ArrayList<org.apache.ode.bpel.o.v2.OScope.CorrelationSet>();
            if( matchCorrelation != null ) {
                matchCorrelations.add(matchCorrelation);
            }
            // backward compatibility; joinCorrelations could be null if read from old definition
            if( joinCorrelations == null ) {
                try {
                    Field field = OnMessage.class.getDeclaredField("joinCorrelations");
                    field.setAccessible(true);
                    field.set(this, new ArrayList<org.apache.ode.bpel.o.v2.OScope.CorrelationSet>());
                } catch( NoSuchFieldException nfe ) {
                    throw new IOException(nfe.getMessage());
                } catch( IllegalAccessException iae ) {
                    throw new IOException(iae.getMessage());
                }
            }
            // backward compatibility; joinCorrelation could have a value if read from old definition
            if( joinCorrelation != null ) {
                joinCorrelation.hasJoinUseCases = true;
                joinCorrelations.add(joinCorrelation);
            }
        }
    }
}