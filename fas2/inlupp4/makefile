

all:
	find org/ioopm/calculator/ |grep .java|xargs javac -d classes

run:
	java -cp classes org.ioopm.calculator.Calculator

clean:
	rm -rf classes; rm *.class


comp:
	javac TestSuite.java

test:	comp
	java org.junit.runner.JUnitCore TestSuite

text: 
	java -cp classes org.ioopm.calculator.Calculator < input.txt > output.txt


diff:	text
	diff expected_output.txt output.txt

rm:
	rm org/ioopm/calculator/ast/*.java~
	rm org/ioopm/calculator/parser/*.java~
	rm org/ioopm/calculator/*.java~	
