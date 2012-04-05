# Version number for this release
VERSION_NUMBER = "1.0.0"

# Group identifier for your projects
GROUP = "org.apache.ode.sandbox"

JACKSON      = group('jackson-core', 'jackson-annotations', 'jackson-databind', :under=>'com.fasterxml.jackson.core', :version=>'2.0.0-RC3-SNAPSHOT')
SMILE        = 'com.fasterxml.jackson.dataformat:jackson-dataformat-smile:jar:2.0.0-SNAPSHOT'
ODE_OMODEL   = 'org.apache.ode:ode-bpel-obj:jar:1.3.5'
ODE_COMPILER = 'org.apache.ode:ode-bpel-compiler:jar:1.3.5'
ODE_UTILS    = 'org.apache.ode:ode-utils:jar:1.3.5'
WSDL4J       = "wsdl4j:wsdl4j:jar:1.6.1"
JAXEN               = "jaxen:jaxen:jar:1.1.1"
SAXON               = group("saxon", "saxon-xpath", "saxon-dom", "saxon-xqj", :under=>"net.sf.saxon", :version=>"9.1.0.8")
COMMONS             = struct(
  :codec            =>"commons-codec:commons-codec:jar:1.3",
  :collections      =>"commons-collections:commons-collections:jar:3.2.1",
  :dbcp             =>"commons-dbcp:commons-dbcp:jar:1.2.2",
  :fileupload       =>"commons-fileupload:commons-fileupload:jar:1.1.1",
  :httpclient       =>"commons-httpclient:commons-httpclient:jar:3.1",
  :lang             =>"commons-lang:commons-lang:jar:2.4",
  :logging          =>"commons-logging:commons-logging:jar:1.1.1",
  :io               =>"commons-io:commons-io:jar:1.4",
  :pool             =>"commons-pool:commons-pool:jar:1.4",
  :primitives       =>"commons-primitives:commons-primitives:jar:1.0",
  :beanutils        =>"commons-beanutils:commons-beanutils:jar:1.8.2"
)

DOZER = 'net.sf.dozer:dozer:jar:5.3.1'
LOG4J               = "log4j:log4j:jar:1.2.13"

repositories.remote << "http://repo1.maven.org/maven2/"
repositories.remote << "http://svn.apache.org/repos/asf/servicemix/m2-repo"
repositories.remote << "https://oss.sonatype.org/content/repositories/snapshots/"

desc "ODE OModel-jackson project"
define "omodel-jackson" do

  project.version = VERSION_NUMBER
  project.group = GROUP

  compile.with JACKSON, SMILE, ODE_OMODEL, WSDL4J, ODE_UTILS, LOG4J #, 'org.slf4j:slf4j-log4j12:jar:1.5.10'
  resources
  test.compile.with JACKSON, ODE_OMODEL, ODE_COMPILER, "commons-logging:commons-logging:jar:1.1.1", "xerces:xercesImpl:jar:2.9.0", 'org.apache.ode:ode-bpel-schemas:jar:1.3.5', "xalan:xalan:jar:2.7.1", COMMONS.collections, JAXEN, SAXON
  test.resources
  package(:jar)
end
