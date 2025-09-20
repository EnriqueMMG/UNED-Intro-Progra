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
    bool continuar = true;
    //Si el pin es incorrecto se cierra el programa
    if(validarPIN() == false){
        return 0;
    }



    while (continuar) {
        mostrarMenu();
        cin >> opcion;
        switch (opcion) {
            case 1:
                continuar = consultarSaldo();
                break;
            case 2:
                continuar = retirarDinero();
                break;
            case 3:
                continuar = depositarDinero();
                break;
            case 4:
                continuar = verHistorialTransacciones();
                break;
            case 5:
                salir();
                continuar = false;
                break;
            default:
                cout << "Opcion invalida. Por favor, intente de nuevo." << endl;
        }
    }
    return 0;
}


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


//Funcion para mostrar la pantalla principal 
void mostrarMenu() {

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
    cout<< endl << endl;
    cout << "Seleccione una opcion: ";

}

