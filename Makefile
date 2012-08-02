all: build

build:
	@mkdir -p bin
	javac 2>&1 src/*.java -d bin -classpath lib/protobuf-java-2.4.1.jar

generate:
	protoc -I=./ --java_out=./src *.proto

start: 
	@java -classpath lib/protobuf-java-2.4.1.jar:bin GLSProtoServer


