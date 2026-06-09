import java.util.ArrayList;

public class Mision {

    private String idMision;
    private String nombre;
    private String tipoMision;
    private ArrayList<Astronauta> astronautas;
    private String tipoNave;
    private int duracion;
    private boolean experimentosPrevios;
    private double costoTotal;

    public Mision(String idMision, String nombre, String tipoMision,
                  String tipoNave, int duracion, boolean experimentosPrevios) {
        this.idMision = idMision;
        this.nombre = nombre;
        this.tipoMision = tipoMision;
        this.tipoNave = tipoNave;
        this.duracion = duracion;
        this.experimentosPrevios = experimentosPrevios;
        this.astronautas = new ArrayList<>();
        this.costoTotal = 0;
    }

    public String getIdMision() { return idMision; }
    public String getNombre() { return nombre; }
    public String getTipoMision() { return tipoMision; }
    public ArrayList<Astronauta> getAstronautas() { return astronautas; }
    public String getTipoNave() { return tipoNave; }
    public int getDuracion() { return duracion; }
    public boolean isExperimentosPrevios() { return experimentosPrevios; }
    public double getCostoTotal() { return costoTotal; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTipoMision(String tipoMision) { this.tipoMision = tipoMision; }
    public void setTipoNave(String tipoNave) { this.tipoNave = tipoNave; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public void setExperimentosPrevios(boolean experimentosPrevios) { this.experimentosPrevios = experimentosPrevios; }

    public void agregarAstronauta(Astronauta a) {
        astronautas.add(a);
    }

    public boolean tieneAstronauta(String idAstronauta) {
        for (Astronauta a : astronautas) {
            if (a.getIdAstronauta().equals(idAstronauta)) {
                return true;
            }
        }
        return false;
    }

    public void calcularCostoMision() {
        double costo = 0;

        for (Astronauta a : astronautas) {
            costo += a.getSalario();
        }

        if (tipoNave.equalsIgnoreCase("Automatica") || tipoNave.equalsIgnoreCase("Automática")) {
            costo += 150000;
        } else if (tipoNave.equalsIgnoreCase("Manual")) {
            costo += 85000;
        }

        if (experimentosPrevios) {
            costo += 20000;
        }

        this.costoTotal = costo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== MISIÓN =====");
        sb.append("\n  ID              : ").append(idMision);
        sb.append("\n  Nombre          : ").append(nombre);
        sb.append("\n  Tipo de misión  : ").append(tipoMision);
        sb.append("\n  Tipo de nave    : ").append(tipoNave);
        sb.append("\n  Duración        : ").append(duracion).append(" días");
        sb.append("\n  Exp. previos    : ").append(experimentosPrevios ? "Sí" : "No");
        sb.append("\n  Astronautas     :");
        for (Astronauta a : astronautas) {
            sb.append("\n    - [").append(a.getIdAstronauta()).append("] ")
              .append(a.getNombre()).append(" | Salario: $")
              .append(String.format("%,.2f", a.getSalario()));
        }
        sb.append("\n  Costo Total     : $").append(String.format("%,.2f", costoTotal));
        sb.append("\n==================");
        return sb.toString();
    }
}
