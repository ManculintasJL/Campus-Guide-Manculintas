#include <stdio.h>

int main(void)

{
	int year, age, currentyear = 2026;
	printf("Enter the birthyear:");
	scanf("%d", &year);
	age= currentyear - year;
	printf("Your age is %d", age);
	return 0;
}