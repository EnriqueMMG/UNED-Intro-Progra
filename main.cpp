#include <iostream>

using namespace std;

int main()
{

    int intOpcion = 0;

    do
    {
        system("cls");
        cout << "---------Calculadora---------" << endl;
        cout << "[1] Suma " << endl;
        cout << "[2] Resta " << endl;
        cout << "[3] Multiplicaci\242n " << endl;
        cout << "[4] Divisi\242n " << endl;
        cout << "[5] Salir " << endl;
        cin >> intOpcion;

        switch (intOpcion)
        {
            case 1:
            cout << "Es una suma";
            break;
            case 2:
            cout << "Es una resta";
            break;
            case 3:
            cout << "Es una multiplicaci\242n";
            break;
            case 4:
            cout << "Es una divisi\242n";
            break;
            default:
            cout << "No es una opci\242n";
            break;
        }
        cout << endl;
        system("pause");

    }
    while (intOpcion != 5);




    return 0;
}*/

/*int main()
{

    int sumatoria = 0;

    for (int contador = 1; contador <=5; contador++)
    {
        sumatoria = sumatoria + contador;
        cout << sumatoria << endl;
    }



    return 0;
}*/

/*int main()
{
    int contador = 1;
    int sumatoria = 0;

    do
    {
        sumatoria = sumatoria + contador;
        contador++;
        cout << sumatoria << endl;
    }
    while( contador <= 5 );

    return 0;
}*/

/*
int main()
{
    int contador = 1;
    int sumatoria = 0;

    while( contador <= 5 )
    {
        sumatoria = sumatoria + contador;
        contador++;
        cout << sumatoria << endl;
    }


    return 0;
}*/
