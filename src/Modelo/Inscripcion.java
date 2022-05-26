
package Modelo;


public class Inscripcion {
    
    private int idInscripcion;
    private Alumno alumno;
    private Materia materia;
    private double nota;

    public Inscripcion(int idInscripcion, Alumno alumno, Materia materia, double nota) {
        this.idInscripcion = idInscripcion;
        this.alumno = alumno;
        this.materia = materia;
        this.nota = nota;
    }
    
    public Inscripcion() {
    }
    
    public Inscripcion(double nota) {
        this.nota = nota;
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public int getIdAlumno() {
        return alumno.getIdAlumno();
    }

    public void setIdAlumno(int idAlumno) {
        this.alumno.setIdAlumno(idAlumno);
    }

    public int getIdMateria() {
        return materia.getIdMateria();
    }

    public void setIdMateria(int idMateria) {
        this.materia.setIdMateria(idMateria);
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
    
    
    
}
