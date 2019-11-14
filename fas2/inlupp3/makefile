all:
	find org/ioopm/calculator/ |grep .java|xargs javac -d classes

run:
	java -cp classes org.ioopm.calculator.Calculator

test: Test.java
	javac -d classes Test.java

runTest: classes/Test.class
	java -cp classes -ea Test

clean:
	rm -rf classes; rm *.class
