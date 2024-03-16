FROM gcc:latest

COPY cpp.cpp /app/

WORKDIR /app

RUN g++ -o cpp.out cpp.cpp

CMD ["./cpp.out"]