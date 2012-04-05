import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonPolyGraph {
	
	@org.junit.Test
	public void testGraphWithInheritance() {
        ObjectMapper om = new ObjectMapper();
        om.disable(MapperFeature.AUTO_DETECT_CREATORS);
        om.disable(MapperFeature.AUTO_DETECT_GETTERS);
        om.disable(MapperFeature.AUTO_DETECT_IS_GETTERS);
        om.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        
        om.enable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
        om.enable(SerializationFeature.INDENT_OUTPUT);
        om.enableDefaultTypingAsProperty(DefaultTyping.NON_FINAL, "@class");

        Process p = new Process();
        Scope s = new Scope(p, null);
        FaultHandler fh = new FaultHandler(p);
        Catch c = new Catch(p, s);
        fh.catchBlocks.add(c);
        s.faultHandlers.add(fh);
        
        try {
			String json = om.writeValueAsString(p);
			System.out.println(json);
			Process restored = om.readValue(json, Process.class);
			System.out.println(om.generateJsonSchema(Process.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	public static class Base {
		public int id;
		public Base owner;
		
		public Base(Process owner) {
	        this.owner = owner;
	        if (owner == null) {
	            id = 0;
	        } else {
	            id = ++owner.childIdCounter;
	            owner.children.add(this);
	        }
	        assert id == 0 || owner != null;
		}
		
		private Base() {}
	}
	
	public static class Process extends Base {
	    int childIdCounter = 0;
	    List<Base> children = new ArrayList<Base>();
	    
	    public Process() {
	    	super(null);
	    }
	}
	
	public static abstract class Activity extends Base {
		private Activity parent;
		public Activity(Process owner, Activity parent) {
			super(owner);
			this.parent = parent;
		}
		private Activity() {
			super();
		}
	}
	
	public static class Scope extends Activity {
		public final List<FaultHandler> faultHandlers = new ArrayList<FaultHandler>();
		public Scope(Process owner, Activity parent) {
			super(owner, parent);
		}
		private Scope() {
			super();
		}
	}
	
	public static class FaultHandler extends Base {
		public final List<Catch> catchBlocks = new ArrayList<Catch>();
	    
		public FaultHandler(Process owner) {
	        super(owner);
	    }

		private FaultHandler() {}
		
	}
	
	public static class Catch extends Scope {
	    public Catch(Process owner, Activity parent) {
	        super(owner, parent);
	    }
		
	    private Catch() {};
	}

}
