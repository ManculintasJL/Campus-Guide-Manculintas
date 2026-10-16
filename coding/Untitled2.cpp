#include <stdio.h>
int main (void)
{
	int num1, num2, total;
	printf("ENTER NUMBER:");
	scanf("%d", &num1);
	printf("ENTER NUMBER:");
	scanf("%d", &num2);
	
	total = num1 * num2;
	
	printf("total: %d", total);
	return 0;
}