#include <iostream>
#include <string>
using namespace std;


//Variables globales
int PIN1 = 1234;
int PIN2 = 2345;
int PIN3 = 3456;
int PIN4 = 4567;
int PIN5 = 5678;
int saldo_inicial = 150000;
int intentos_maximos = 3;
int transacciones = 0;
int transaccion1 = 0, transaccion2 = 0, transaccion3 = 0, transaccion4 = 0, transaccion5 = 0;
string tipo1 = "", tipo2 = "", tipo3 = "", tipo4 = "", tipo5 = "";
int saldo1 = 0, saldo2 = 0, saldo3 = 0, saldo4 = 0, saldo5 = 0;


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
    cout << "Ha excedido el numero maximo de intentos." << endl;
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
    cout << "¿Desea volver al menu principal? Digite 1 para Si o 0 para No: "<< endl;
    cin >> opcion;
    cout << endl;
    if (opcion == 0) {
        cout << "Gracias por usar el Simulador de Cajero Automatico." << endl;
        return 0;
    }
    return 1;
}

//Registrar transacciones realizadas
void registrarTransaccion(string tipo, int monto){

    //Guardar el tipo y monto en variables de cada transaccion
    if(transacciones == 0){
        tipo1 = tipo;
        transaccion1 = monto;
        saldo1 = saldo_inicial;
    }
    else if(transacciones == 1){
        tipo2 = tipo;
        transaccion2 = monto;
        saldo2 = saldo_inicial;
    }
    else if(transacciones == 2){
        tipo3 = tipo;
        transaccion3 = monto;
        saldo3 = saldo_inicial;
    }
    else if(transacciones == 3){
        tipo4 = tipo;
        transaccion4 = monto;
        saldo4 = saldo_inicial;
    }
    else if(transacciones == 4){
        tipo5 = tipo;
        transaccion5 = monto;
        saldo5 = saldo_inicial;
    }

    // Aumentar el contador de transacciones realizadas
    transacciones++;
}

//Funcion para retirar dinero
int retirarDinero(){
    if (transacciones >= 5) {
        cout << endl;
        cout << "Ha alcanzado el limite de transacciones permitidas (5). Volviendo al Menu Principal." << endl;
        return 1;
    }
    cout << endl;
    cout << "RETIRAR DINERO" << endl;
    cout << "------------------------------------" << endl;
    cout << "Ingrese monto a retirar: ₡ ";
    int monto;
    cin >> monto;
    // Validar que el monto sea positivo y no exceda el saldo
    if (monto > saldo_inicial || monto <= 0) {
        cout << "Error, el monto no es valido. Su saldo es ₡ " << saldo_inicial << endl;
    } else {
        saldo_inicial -= monto; // Resta al saldo inicial
        cout << "------------------------------------" << endl;
        cout << "Retiro exitoso." << endl;
        cout << "Saldo actual: ₡ " << saldo_inicial << endl;
        registrarTransaccion("Retiro", monto);
        }
    cout << endl;
    int opcion;
    cout << "¿Desea volver al menu principal? Digite 1 para Si o 0 para No: "<< endl;
    cin >> opcion;
    cout << endl;
    if (opcion == 0) {
        cout << "Gracias por usar el Simulador de Cajero Automatico." << endl;
        return 0;
    }
    return 1;
}

//Funcion para depositar dinero
int depositarDinero(){
    if (transacciones >= 5) {
        cout << endl;
        cout << "Ha alcanzado el limite de transacciones permitidas (5). Volviendo al Menu Principal." << endl;
        return 1;
    }
    cout << endl;
    cout << "DEPOSITAR DINERO" << endl;
    cout << "------------------------------------" << endl;
    cout << "Ingrese monto a depositar: ₡ ";
    int monto;
    cin >> monto;
    // Validar que el monto sea positivo
    if (monto <= 0) {
        cout << "Error, el monto no es valido. Intente nuevamente" << endl;
    } else {
        saldo_inicial += monto; // Suma al saldo inicial
        cout << "------------------------------------" << endl;
        cout << "Deposito exitoso." << endl;
        cout << "Saldo actual: ₡ " << saldo_inicial << endl;
        registrarTransaccion("Deposito", monto);
    }
    cout << endl;
    int opcion;
    cout << "¿Desea volver al menu principal? Digite 1 para Si o 0 para No: "<< endl;
    cin >> opcion;
    cout << endl;
    if (opcion == 0) {
        cout << "Gracias por usar el Simulador de Cajero Automatico." << endl;
        return 0;
    }
    return 1;
}


//Funcion para mostrar historial de transacciones
int historialTransacciones(){
    cout << endl;
    cout << "HISTORIAL DE TRANSACCIONES" << endl;
    cout << "------------------------------------" << endl;
    if (transacciones == 0) {
        cout << "No existen transacciones registradas" << endl;
    } else {
        cout << "Tipo       | Monto      | Saldo despues" << endl;
        cout << "-----------|------------|---------------" << endl;
        if(transacciones >= 1){
            cout << tipo1 << "     | ₡ " << transaccion1 << "   | ₡ " << saldo1 << endl;
        }
        if(transacciones >= 2){
            cout << tipo2 << "     | ₡ " << transaccion2 << "   | ₡ " << saldo2 << endl;
        }
        if(transacciones >= 3){
            cout << tipo3 << "   | ₡ " << transaccion3 << "   | ₡ " << saldo3 << endl;
        }
        if(transacciones >= 4){
            cout << tipo4 << "   | ₡ " << transaccion4 << "   | ₡ " << saldo4 << endl;
        }
        if(transacciones == 5){
            cout << tipo5 << "   | ₡ " << transaccion5 << "   | ₡ " << saldo5 << endl;
        }
    }
    cout << endl;
    int opcion;
    cout<< "¿Desea volver al menu principal? Digite 1 para Si o 0 para No: "<< endl;
    cin >> opcion;
    cout << endl;
    if (opcion == 0) {
        cout << "Gracias por usar el Simulador de Cajero Automatico." << endl;
        return 0;
    }
    return 1;
}


int main(){
    int opcion;
    //Llamar funcion para verificar el pin
    if(validarPIN() == false){
        cout << "Saliendo del sistema." << endl;
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
                //Llamar funcion para consultar el saldo
                if(consultarSaldo() == 0){
                    return 0;
                }
                break;
            case 2:
                //Llamar funcion para retirar dinero
                if (retirarDinero() == 0){
                return 0;
                }
                break;
            case 3:
                //Llamar funcion para depositar dinero
                if (depositarDinero() == 0){
                return 0;
                }
                break;
            case 4:
                //Llamar funcion para ver historial de transacciones
                if (historialTransacciones() == 0){
                    return 0;
                }
                break;
            case 5:
                cout << endl;
                cout << "Gracias por usar el Simulador de Cajero Automatico. Regrese pronto..." << endl;
                return 0;
            default:
                cout << "Opcion invalida, vuelva a intentarlo." << endl;
        }
    }while(opcion != 5);


    return 0;
}
