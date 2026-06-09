public class Astronauta {

    private String idAstronauta;
    private String nombre;
    private String cedula;
    private String profesion;
    private String estado;
    private String paisOrigen;
    private double salario;

    public Astronauta(String idAstronauta, String nombre, String cedula,
                      String profesion, String estado, String paisOrigen, double salario) {
        this.idAstronauta = idAstronauta;
        this.nombre = nombre;
        this.cedula = cedula;
        this.profesion = profesion;
        this.estado = estado;
        this.paisOrigen = paisOrigen;
        this.salario = salario;
    }

    public String getIdAstronauta() { return idAstronauta; }
    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }
    public String getProfesion() { return profesion; }
    public String getEstado() { return estado; }
    public String getPaisOrigen() { return paisOrigen; }
    public double getSalario() { return salario; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public void setProfesion(String profesion) { this.profesion = profesion; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setPaisOrigen(String paisOrigen) { this.paisOrigen = paisOrigen; }
    public void setSalario(double salario) { this.salario = salario; }

    @Override
    public String toString() {
        return "===== ASTRONAUTA =====" +
               "\n  ID          : " + idAstronauta +
               "\n  Nombre      : " + nombre +
               "\n  Cédula      : " + cedula +
               "\n  Profesión   : " + profesion +
               "\n  Estado      : " + estado +
               "\n  País origen : " + paisOrigen +
               "\n  Salario     : $" + String.format("%,.2f", salario) +
               "\n=====================";
    }
}
