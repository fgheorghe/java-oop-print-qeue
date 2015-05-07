all: compile-mac run

# Compiling for Mac.
compile-mac:
	cd src && javac -classpath .:./Abstract:./Class:./Exception:./Final:./Interface ./Final/Main.java -Xlint:unchecked
	mv ./src/Abstract/*.class ./bin/
	mv ./src/Exception/*.class ./bin/
	mv ./src/Final/*.class ./bin/
	mv ./src/Interface/*.class ./bin/
	mv ./src/Class/*.class ./bin/
run:
	cd bin && java Main