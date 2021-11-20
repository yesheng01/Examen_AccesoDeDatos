/**
 * PACKAGE_NAME
 * Nombre_project: Examen_AccesoDeDatos
 * alumnos
 * Created by: sheng
 * Date : 18/11/2021
 * Description:
 **/
public class alumnos {
    int id;
    String nombre;
    String curso;
    String fecha_nacimiento;
    String colegio;

    public alumnos(int id, String nombre, String curso, String fecha_nacimiento, String colegio) {
        this.id = id;
        this.nombre = nombre;
        this.curso = curso;
        this.fecha_nacimiento = fecha_nacimiento;
        this.colegio = colegio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }
}
