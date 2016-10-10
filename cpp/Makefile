CPP_FILES = $(wildcard src/*.cpp)
OBJ_FILES = $(CPP_FILES:.cpp=.o)

TEST_CPP_FILES = $(wildcard tests/*.cpp)
TEST_OBJ_FILES = $(TEST_CPP_FILES:.cpp=.o)

GTEST_DIR=googletest-release-1.8.0
#GTEST_DIR_UP=../$(GTEST_DIR)
GTEST=$(GTEST_DIR)/googletest
GTEST_HEADERS=$(GTEST)/include

TARGET = rocketc
CC = g++

all: build copy

test_%.o : test_%.cpp
	$(CC) -pthread -I$(GTEST_HEADERS) -L$(GTEST) -lgtest -c $< -o $@

%.o: %.cpp
	$(CC) -O2 -c -o $@ $<

tests: $(TEST_OBJ_FILES)
	$(CC) -pthread -I$(GTEST_HEADERS) -L$(GTEST) -o runtests $(TEST_OBJ_FILES) -lgtest

build: $(OBJ_FILES)
	$(CC) -O2 $(OBJ_FILES) -o $(TARGET)

debug: $(OBJ_FILES)
	$(CC) -g $(OBJ_FILES) -o $(TARGET)

copy:
	mkdir -p ../simulator/
	cp -f $(TARGET) ../simulator/$(TARGET)

clean:
	rm -f $(OBJ_FILES)
	rm -f $(TEST_OBJ_FILES)
	rm -f $(TARGET)
