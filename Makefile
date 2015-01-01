make: target\config target\resources compile jar

target\config: src/main/config
	cp -r src/main/config target/

target\resources: src/main/resources
	cp -r src/main/resources target/

compile: src/main/java/com/clinchergt/jtetris
	javac src/main/java/com/clinchergt/jtetris/*.java -d target/

jar: target/resources target/config target/com/clinchergt/jtetris
	cd target; jar cmvf ../META-INF/MANIFEST.MF JTetris.jar com/clinchergt/jtetris/*.class resources/ config/

clean:
	rm -r target
	mkdir target
