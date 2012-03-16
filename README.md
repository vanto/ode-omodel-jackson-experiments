# Goal 

This is test project in order to evaluate different approaches that lead 
to a dynamic OModel for Apache ODE. 

# Problem Statement 

In Apache ODE, BPEL processes are compiled to an in-memory object model 
(OModel). This model is being used during runtime, the runtime execution 
state maintains references to the OModel. In order to keep the memory 
footprint manageable, the OModel is loaded (hydration) and unloaded 
(dehydration) on demand. Thus, there are certain requirements to be 
fulfilled by the OModel: 

 1. OModel must be evolvable. OModel graph instances may live longer 
    than a certain OModel version. It must be possible that newer OModel 
    versions can still load old OModel serializations. 

 2. Low memory footprint. The OModel is an object graph, each object 
    should use as little memory as possible.

 3. Fast field access. Since the OModel is read frequently, read access 
    should be as fast as possible.

 4. Fast loading. The OModel must be serialized and unserialized as fast 
    as possible. Also, the file size should be as small as possible (no 
    compression, just focus in IO and performance). 


The OModel in its current form uses public fields and Java's built-in 
serialization. This leads to massive problems with binary compatibility 
when the OModel structure evolves. 

# Considerations

 * Requirement 1 is the most important issue. Using a Map as the 
   underlying data structure makes the content structure of a class 
   changeable without affecting the class structure itself. Using a 
   Map, however, can negatively effect requirement 2 and 3.
    
 * Requirement 2+3: TODO: Are there alternatives to Maps that still satisfy 
   requirement 1? Which Map implementation would serve best?

 * Requirement 4: According to a [JVM serializers benchmark](https://github.com/eishay/jvm-serializers/wiki), 
   Jackson is a good candiate for this refactoring effort. It is fast, 
   provides a data mapping to basic Java types, can deal with object graphds 
   (ObjectIdentity, since 2.0) and provides a fast and small binary 
   serialization (Smile).

   