#include <stdio.h>
int main (void)
{
	int english, math, filipino, science, total_score, average;
	printf("english:");
	scanf("%d", &english);
	printf("math:");
	scanf("%d", &math);
	printf("filipino:");
	scanf("%d", &filipino);
	printf("science:");
	scanf("%d", &science);
	
	total_score = english + math + filipino + science;
	average = total_score /4;
	
	printf("\nthe total score: %d", total_score);
    printf("\naverage: %d", average);
    
    return 0;
}