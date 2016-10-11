CPP_FILES = $(wildcard src/*.cpp)
OBJ_FILES = $(CPP_FILES:.cpp=.o)

TEST_CPP_FILES = $(wildcard tests/*.cpp)
TEST_OBJ_FILES = $(TEST_CPP_FILES:.cpp=.o)

GTEST_DIR=googletest-release-1.8.0
#GTEST_DIR_UP=../$(GTEST_DIR)
GTEST=$(GTEST_DIR)/googletest
GTEST_HEADERS=$(GTEST)/include

TARGET_BIN = rocketc
TARGET_TEST = runtests
CC = g++

all: build copy

test_%.o : test_%.cpp
	$(CC) -pthread -I$(GTEST_HEADERS) -L$(GTEST) -lgtest -c $< -o $@

%.o: %.cpp
	$(CC) -O2 -c -o $@ $<

tests: build $(TEST_OBJ_FILES)
	$(CC) -pthread -I$(GTEST_HEADERS) -L$(GTEST) $(OBJ_FILES) -o $(TARGET_TEST) $(TEST_OBJ_FILES) -lgtest
	./$(TARGET_TEST)

build: $(OBJ_FILES) main.cpp
	$(CC) -O2 $(OBJ_FILES) main.cpp -o $(TARGET_BIN)

debug: $(OBJ_FILES)
	$(CC) -g $(OBJ_FILES) -o $(TARGET_BIN)

copy:
	mkdir -p ../simulator/
	cp -f $(TARGET_BIN) ../simulator/$(TARGET_BIN)

clean:
	rm -f $(OBJ_FILES) $(TEST_OBJ_FILES) $(TARGET_BIN) $(TARGET_TEST)
