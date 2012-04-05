package org.apache.ode.bpel.o.v2;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.ode.bpel.compiler.BpelCompiler;
import org.apache.ode.bpel.compiler.BpelCompiler20;
import org.apache.ode.bpel.compiler.DefaultResourceFinder;
import org.apache.ode.bpel.compiler.ResourceFinder;
import org.apache.ode.bpel.compiler.bom.BpelObjectFactory;
import org.apache.ode.bpel.o.OBase;
import org.apache.ode.bpel.o.OProcess;
import org.apache.ode.bpel.o.Serializer;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.dataformat.smile.SmileGenerator;

public class JacksonTest {

	private org.apache.ode.bpel.o.OProcess oprocess;
	private ObjectMapper om;

	@Before
	public void setup() throws Exception {
		URL bpelURL = getClass().getResource("/HelloWorld2/HelloWorld2.bpel");
        File suDir = new File(bpelURL.toURI()).getParentFile(); 
        ResourceFinder wf = new DefaultResourceFinder(new File(bpelURL.toURI()).getAbsoluteFile().getParentFile(), suDir.getAbsoluteFile());

        InputSource isrc = new InputSource(bpelURL.openStream());
        isrc.setSystemId(bpelURL.toString());

        org.apache.ode.bpel.compiler.bom.Process process = BpelObjectFactory.getInstance().parse(isrc, bpelURL.toURI());

        BpelCompiler compiler = new BpelCompiler20();
        compiler.setResourceFinder(wf);
        
        oprocess = compiler.compile(process, wf, 1L);
        
        SmileFactory factory = new SmileFactory();
        factory.configure(SmileGenerator.Feature.CHECK_SHARED_NAMES, true);
        factory.configure(SmileGenerator.Feature.CHECK_SHARED_STRING_VALUES, true);
        
        //om = new ObjectMapper(factory);
        om = new ObjectMapper();
        om.disable(MapperFeature.AUTO_DETECT_CREATORS);
        om.disable(MapperFeature.AUTO_DETECT_GETTERS);
        om.disable(MapperFeature.AUTO_DETECT_IS_GETTERS);
        om.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        
        om.enable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
        //om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        om.enable(SerializationFeature.INDENT_OUTPUT);
        om.addMixInAnnotations(OBase.class, MixIn.class);
        om.addMixInAnnotations(org.apache.ode.bpel.o.v2.OBase.class, MixIn.class);
        om.enableDefaultTypingAsProperty(DefaultTyping.NON_FINAL, "@class");
//        om.addMixInAnnotations(org.apache.ode.bpel.o.OSequence.class, MixIn.class);
//        om.addMixInAnnotations(org.apache.ode.bpel.o.OScope.class, MixIn.class);
//        om.addMixInAnnotations(org.apache.ode.bpel.o.OAssign.class, MixIn.class);
//        om.addMixInAnnotations(org.apache.ode.bpel.o.OAssign.Copy.class, MixIn.class);
//        om.addMixInAnnotations(org.apache.ode.bpel.o.OAssign.Expression.class, MixIn.class);
//        om.addMixInAnnotations(org.apache.ode.bpel.elang.xpath20.o.OXPath20ExpressionBPEL20.class, MixIn.class);

        //Mapper mapper = new DozerBeanMapper();
        //OProcess destObject =  mapper.map(oprocess, OProcess.class);
        
        System.out.println(oprocess);
		//_compiler.setProcessWSDL(new URI(_wsdlURI));
		//_compiler.compile(new File(bpelURL.toURI()), 1L);
	}

	@Test
	public void test() {
		try {
	        Serializer fileHeader = new Serializer(System.currentTimeMillis());
	        OutputStream bos = new BufferedOutputStream(new FileOutputStream("helloworld.cbp"));
	        fileHeader.writeOProcess(oprocess, bos);
	        bos.close();
	        
	        bos = new BufferedOutputStream(new FileOutputStream("helloworld.smile"));
	        om.writeValue(bos, oprocess);
	        bos.close();
	
	        om.writeValue(new File("helloworld.json"), oprocess);
	        
	        //org.apache.ode.bpel.o.v2.OProcess op2 = om.readValue(om.writeValueAsBytes(oprocess), org.apache.ode.bpel.o.v2.OProcess.class);
	        org.apache.ode.bpel.o.v2.OProcess op2 = om.readValue(om.writeValueAsString(oprocess).replaceAll("org.apache.ode.bpel.o", "org.apache.ode.bpel.o.v2"), org.apache.ode.bpel.o.v2.OProcess.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
	//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="_id")
	@JsonIgnoreProperties(value = {"nested"})
	interface MixIn {
		//@JsonIgnore public final Set<OAgent> nested = null;
		//@JsonIgnore OProcess getOwner();
		//@JsonIgnore OProcess getParent();
	}

}
