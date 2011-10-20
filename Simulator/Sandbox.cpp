// Sandbox.cpp : Defines the entry point for the console application.
//

#include"intabread.h"
using namespace std;

int main()
{
	/*INTAB1 IT1;
	IT1.FileFill("intab1.txt");
	INTAB2 IT2;
	IT2.FileFill("intab2.txt");
	INTAB3 IT3;
	IT3.FileFill("intab3.txt");
	INTAB4 IT4;
	IT4.FileFill("intab4.txt");

	INTAB IT(IT1,IT2,IT3,IT4);*/

	INTAB IT;
	IT.FileFill("intab1.txt","intab2.txt","intab3.txt","intab4.txt");

	cout<<IT.intab1.time[10]<<" - "<<IT.intab1.Thrust[10]<<" - "<<IT.intab1.Cda1[10]<<endl;
	cout<<IT.intab2.alpha[5]<<" - "<<IT.intab2.Re[22]<<" - "<<IT.intab2.CD[3][12]<<endl;
	cout<<IT.intab3.CNa<<" - "<<IT.intab3.Xcp<<endl;
	cout<<IT.intab4.Alt[15]<<" - "<<IT.intab4.Wx[15]<<" - "<<IT.intab4.Wy[15]<<endl;

	return 0;
}

