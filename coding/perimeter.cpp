#include <stdio.h>

int main(void)

{
	int length, width, perimeter;
	printf("Enter the Length:");
	scanf("%d", &length);
	printf("Enter the width:");
	scanf("%d", &width);
	perimeter= length * 2 + width * 2;
	printf("The Perimeter is %d", perimeter);
	return 0;	
}