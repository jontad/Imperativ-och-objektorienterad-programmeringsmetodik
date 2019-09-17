#include <stdio.h>

struct point
{
  int x;
  int y;
};

typedef struct point point_t;


int main(void)
{

  point_t p1 = { .x = 10 };
  point_t p2 = { .y = -42 };
  point_t p3 = { };

  printf("point(x=%d,y=%d)\n", p1.x, p1.y);
  printf("point(x=%d,y=%d)\n", p2.x, p2.y);
  printf("point(x=%d,y=%d)\n", p3.x, p3.y);
}
