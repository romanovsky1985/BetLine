build:
	javac -cp ./src -d ./build ./src/betline/core/*.java ./src/betline/sport/icehockey/*.java ./src/Demo.java

package:
	jar cvf ./betline.jar -C ./build ./betline
demo:
	java -cp ./build Demo
clean:
	rm -r ./build
	rm betline.jar

