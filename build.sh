# Build frontend, move to resources
rm -rf backend/src/main/resources/META-INF/resources/*
cd frontend
tsc
mv ./dist ../backend/src/main/resources/META-INF/resources/dist
cp -r ./pages ../backend/src/main/resources/META-INF/resources

# create jar
cd ../backend
./mvnw clean package || { echo "Maven build failed"; exit 1; }

cd ../docker
docker compose down -v && docker compose up --build
