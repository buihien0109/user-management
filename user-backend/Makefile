clean:
	mvn clean:clean

build-jar:
	mvn clean package -DskipTests

build-image:
	docker build -t buihien0109/user-backend:latest .

push-image:
	docker push buihien0109/user-backend:latest

all:
	mvn clean package -DskipTests;
	docker build -t buihien0109/user-backend:latest .;
	docker push buihien0109/user-backend:latest