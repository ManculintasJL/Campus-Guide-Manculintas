#include <stdio.h>

int main(void)

{
	int p1, p2, p3, p4, total;
	printf("Enter p1:");
	scanf("%d", &p1);
	printf("Enter p2:");
	scanf("%d", &p2);
	printf("Enter p3:");
	scanf("%d", &p3);
	printf("Enter p4:");
	scanf("%d", &p4);
	total= p1 + p2 + p3 + p4;
	printf("The total is %d", total);
	return 0;
}