#include <stdio.h>

int main(void)

{
	float price, amount, cost, change, total;
	int quantity;
	
	printf("Enter the Price:");
	scanf("%f", &price);
	printf("Enter the Quanity:");
	scanf("%d", &quantity);
	total = price * quantity;
	printf("the total is %.f\n", total);
	printf("Enter the amount paid:");
	scanf("%f", &amount);
	change = total - amount;
	printf("the change is %.f", change);
	
	
	return 0;
}