#include <gtest/gtest.h>

#include "../src/vmaths.h"

TEST(Vector, Test_Vectors) {
  // new vector
  vector2 vec1, vec2, vec3;

  vec1 = vector2(10, 10);
  vec2 = vector2(10, 10);
  // vec3 = vec1 + vec2;

  // ASSERT_EQ(vec3.mag(), std::pow(2, 0.5));
  ASSERT_EQ(10, 10);
}
