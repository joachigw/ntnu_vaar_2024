FROM gcc:latest

COPY c.c /app/

WORKDIR /app

RUN gcc -o c.out c.c

CMD ["./c.out"]