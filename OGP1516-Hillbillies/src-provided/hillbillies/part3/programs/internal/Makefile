
ANTLRPATH = $(shell pwd)/../../../../../lib/antlr-4.5.3-complete.jar
CLASSPATH = ".:./generated:${ANTLRPATH}:$$CLASSPATH"



all: clean code

code:
	java -jar ${ANTLRPATH} \
             -o generated \
             -package hillbillies.part3.programs.internal.generated \
             -visitor HillbilliesTaskLang.g

build: code
	export CLASSPATH=${CLASSPATH}; \
          javac generated/*.java

test: build
	export CLASSPATH=${CLASSPATH}; \
          cd ../../../../ ; \
          for program in ../resources/programs/*.txt; do \
            echo " ----- $$program ------------------------------"; \
            java 'org.antlr.v4.runtime.misc.TestRig' hillbillies.part3.programs.internal.generated.HillbilliesTaskLang program <$$program; \
          done

clean:
	rm -rf generated

