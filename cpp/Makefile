CPP_FILES = $(wildcard *.cpp)
OBJ_FILES = $(CPP_FILES:.cpp=.o)
TARGET = rocketc
CC = g++

all: build copy

%.o: %.cpp
	$(CC) -O2 -c -o $@ $<

build: $(OBJ_FILES)
	$(CC) -O2 $(OBJ_FILES) -o $(TARGET)

debug: $(OBJ_FILES)
	$(CC) -g $(OBJ_FILES) -o $(TARGET)

copy:
	mkdir -p ../simulator/
	cp -f $(TARGET) ../simulator/$(TARGET)

clean:
	rm $(OBJ_FILES)
	rm $(TARGET)
