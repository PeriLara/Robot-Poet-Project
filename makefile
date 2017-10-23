


all: project.jar


project.jar: src/*
	mkdir -p bin
	javac src/main/Main.java -d ./bin -cp src
	jar cvfm project.jar MANIFEST.MF -C bin/ .


clean:
	rm -fr bin
	rm -f project.jar