package Modelo;

import Controlador.AlumnoData;
import Controlador.Conexion;
import Controlador.InscripcionData;
import Controlador.MateriaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TPTRANSVERSAL {

    public static void main(String[] args) throws SQLException {

        Conexion con = new Conexion();
    /*    AlumnoData ad = new AlumnoData(con);

        Alumno a1 = new Alumno("Tobar", "Gala", LocalDate.of(2004, 12, 18), true);
        Alumno a2 = new Alumno("Villegas", "Sofia", LocalDate.of(2003, 02, 2), false);
        Alumno a3 = new Alumno("Gonzalez", "Luciano", LocalDate.of(2001, 07, 8), true);
        ad.guardarAlumno(a1);
        ad.guardarAlumno(a2);
        ad.guardarAlumno(a3);
        ad.modificarAlumno(6, a1);
        ad.activarAlumno(6);
        ad.eliminarAlumno(6);
        System.out.println(ad.listarAlumnos());
        System.out.println(" " + ad.buscarAlumno(4));
        System.out.println(" " + ad.listarAlumnos());

        MateriaData md = new MateriaData(con);

        Materia m1 = new Materia("Ciencias Naturales", 4, true);
        Materia m2 = new Materia("Ciencias Politicas", 3, false);

        md.guardarMateria(m1);
        md.guardarMateria(m2);
        md.modificarMateria(5, m2);
        md.eliminarMateria(5);
        md.activarMateria(5);
        System.out.println(" " + m1);
        System.out.println(" " + md.listarMaterias());
        System.out.println(md.buscarMateria(5));
      */
    
        Inscripcion insc = new Inscripcion();
        InscripcionData id = new InscripcionData(con);
        
        List <Alumno> lista = id.obtenerAlumnosXMateria(1);
        
        for(Alumno a : lista){
            System.out.println(a.getApellido());
            System.out.println(a.getFechaNac());
        }
        

        
    }

}
