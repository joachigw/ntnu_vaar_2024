FROM python:latest

COPY python.py /app/
COPY pythonrequirements.txt /app

WORKDIR /app

CMD ["python", "python.py"]