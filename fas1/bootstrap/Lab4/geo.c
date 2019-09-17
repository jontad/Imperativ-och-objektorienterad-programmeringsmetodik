#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

typedef struct point point_t;
typedef struct rectangle rectangle_t;


struct point
{
  int x;
  int y;
};
struct rectangle
{
  point_t top_corner;
  point_t low_corner;
};


void translate(point_t *p1, point_t *p2)
{
  p1->x += p2->x;
  p1->y += p2->y;
}

void print_point(point_t *p)
{
  printf("(%d, %d)", p->x, p->y);
}

void print_rect(rectangle_t *p)
{
  printf("rectangle(upper_left=");
  print_point(&p->top_corner);
  printf(", lower_right");
  print_point(&p->low_corner);
  printf(")\n");
}

point_t make_point(int input_x, int input_y)
{
  point_t point = { point.x = input_x, point.y = input_y };
  return point;
}

rectangle_t make_rect(int input_top_x, int input_top_y, int input_low_x, int input_low_y)
{
  rectangle_t rectangle = { rectangle.top_corner = make_point(input_top_x, input_top_y), rectangle.low_corner = make_point(input_low_x, input_low_y) };
  return rectangle;
}

int area_rect(rectangle_t *rect)
{
  int x1 = rect->top_corner.x;
  int x2 = rect->low_corner.x;
  int y1 = rect->top_corner.y;
  int y2 = rect->low_corner.y;
  int area = (abs(x1 - x2) * abs(y1 - y2));
  return area;
}

bool intersects_rect(rectangle_t *R1, rectangle_t *R2)
{
  int y1 = R1->top_corner.y;
  int y2 = R1->low_corner.y;
  int y3 = R2->top_corner.y;
  int y4 = R2->low_corner.y;

  int x1 = R1->top_corner.x;
  int x2 = R1->low_corner.x;
  int x3 = R2->top_corner.x;
  int x4 = R2->low_corner.x;
  
  if ((x1 > x4) || (x2 > x3)) // Kollar om ena är ovanför den andra
    {
      return false;
    }
  if ((y1 > y4) || (y2 > y3)) // Kollar om ena är vid sidan om den andra
    {
      return false;
    }
  else // Annars överlappar de.
    {
      return true;
    }
}


int main(void)
{
  rectangle_t test_rectangle1 = make_rect(2, 2, 0, 0);
  rectangle_t test_rectangle2 = make_rect(2, 1, 3, 3);
  print_rect(&test_rectangle1);
  print_rect(&test_rectangle1);
  int test_area = area_rect(&test_rectangle1);
  printf("%d\n", test_area);
  bool test_intersect = intersects_rect(&test_rectangle1, &test_rectangle2);
  printf("Intersects: %d\n", test_intersect);
}
