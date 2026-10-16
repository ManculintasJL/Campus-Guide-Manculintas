#include <stdio.h>

int main(void)

{
	int side1, side2, side3, triangle;
	printf("Input side1:");
	scanf("%d", &side1);
	printf("Input side2:");
	scanf("%d", &side2);
	printf("Input side3:");
	scanf("%d", &side3);
	triangle= side1 + side2 + side3;
	printf("The size is %d", triangle);
	return 0;
}