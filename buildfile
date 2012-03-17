# Version number for this release
VERSION_NUMBER = "1.0.0"

# Group identifier for your projects
GROUP = "org.apache.ode.sandbox"

JACKSON = group('jackson-core', 'jackson-annotations', 'jackson-databind', :under=>'com.fasterxml.jackson.core', :version=>'2.0.0-RC2') 

ODE_OMODEL = 'org.apache.ode:ode-bpel-obj:jar:1.3.5'
ODE_COMPILER = 'org.apache.ode:ode-bpel-compiler:jar:1.3.5'

repositories.remote << "http://repo1.maven.org/maven2/"

desc "ODE OModel-jackson project"
define "omodel-jackson" do

  project.version = VERSION_NUMBER
  project.group = GROUP

  compile.with JACKSON, ODE_OMODEL
  resources
  test.compile.with JACKSON, ODE_OMODEL, ODE_COMPILER
  test.resources
  package(:jar)
end
