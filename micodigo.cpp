#include <iostream>

using namespace std;


//Variables globales
int PIN1 = 1234;
int PIN2 = 2345; 
int PIN3 = 3456;
int PIN4 = 4567;
int PIN5 = 5678;
int saldo_inicial = 150000; 
int intentos_maximos = 3; 
int transacciones = 5; 

int main(){ 
    int opcion;
    //Llamamos una funcion para verificar el pin
    if(validarPIN() == false){
        return 0;
    }

    do{
        cout << endl;
        cout << "====================================" << endl;
        cout << "---SIMULADOR DE CAJERO AUTOMATICO---" << endl;
        cout << "====================================" << endl;
        cout << "----------MENU DE OPCIONES----------" << endl;
        cout << "====================================" << endl;
        cout << "1. Consultar saldo" << endl;
        cout << "2. Retirar dinero" << endl;
        cout << "3. Depositar dinero" << endl;
        cout << "4. Ver historial de transacciones" << endl;
        cout << "5. Salir" << endl;
        cout << "Seleccione una opcion: ";
        cin >> opcion;

        switch(opcion){
            case 1:
                if(consultarSaldo() == 0){
                    return 0;
                }
                break;
            case 2:
                //Llamamos la funcion para retirar dinero
                break;
            case 3:
                //Llamamos la funcion para depositar dinero
                break;
            case 4:
                //Llamamos la funcion para ver el historial de transacciones
                break;
            case 5:
                cout << "Gracias por usar el Simulador de Cajero Automatico. Regrese pronto..." << endl;
                return 0;
            default:
                cout << "Opcion invalida, vuelva a intentarlo." << endl;
        }
    }while(opcion != 5);


    return 0;
}

//Funcion

//Funcion para validar el pin
bool validarPIN(){
    int pin;
    int intentos = 0;
    while (intentos < intentos_maximos) {
        cout << "Por favor, ingrese el numero de PIN (4 digitos): ";
        cin >> pin;
        if (pin == PIN1 || pin == PIN2 || pin == PIN3 || pin == PIN4 || pin == PIN5) {
            return true; // El PIN es valido
        } else {
            intentos++;
            cout << "PIN incorrecto. Intentos restantes: " << (intentos_maximos - intentos) << endl;
        }
    }
    cout << "Ha excedido el numero maximo de intentos. Su tarjeta ha sido bloqueada." << endl;
    return false; // El numero de intentos excedidos
}

//Funcion para consultar el saldo
int consultarSaldo(){
    cout << endl;
    cout << "CONSULTAR SALDO" << endl;
    cout << "------------------------------------" << endl;
    cout << "Su saldo actual es: ₡ " << saldo_inicial << endl;
    cout << endl;
    int opcion;
    cout << "¿Desea volver al menu principal? Digite 1 para Si o 0 para No: ";
    cin >> opcion;
    cout << endl;
    if (opcion == 0) {
        cout << "Gracias por usar el Simulador de Cajero Automatico." << endl;
        return 0;
    return 1;
}
