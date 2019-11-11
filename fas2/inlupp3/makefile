all:
	javac -d classes org/ioopm/calculator/ast/*.java

run:
	java -cp classes org.ioopm.calculator.Calculator

test: Test.java
	javac -d classes Test.java

runTest: classes/Test.class
	java -cp classes Test

clean:
	rm -rf classes; rm *.class
