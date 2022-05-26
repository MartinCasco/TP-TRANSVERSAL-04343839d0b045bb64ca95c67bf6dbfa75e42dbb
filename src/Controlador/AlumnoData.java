package Controlador;

import Modelo.Alumno;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AlumnoData {

    private Connection con = null;

    public AlumnoData(Conexion conexion) {
        try {
            con = conexion.getConexion();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la conexion");
        }
    }

    public void guardarAlumno(Alumno alumno) {

        String sql = "INSERT INTO alumno (apellido, nombre, fechaNac, activo) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, alumno.getApellido());
            ps.setString(2, alumno.getNombre());
            ps.setDate(3, Date.valueOf(alumno.getFechaNac()));//localDate a Date
            ps.setInt(4, alumno.isActivo() ? 1 : 0); // if reducido
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                alumno.setIdAlumno(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Alumno añadido con exito.");
            } else {
                JOptionPane.showMessageDialog(null, "El alumno no fue añadido.");
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo añadir el alumno.");
        }

    }

    public Alumno buscarAlumno(int id) {
        Alumno alumno = new Alumno();
        String sql = "SELECT apellido, nombre, fechaNac FROM alumno WHERE idAlumno=? AND activo = 1";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                alumno.setIdAlumno(id);
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFechaNac(rs.getDate("fechaNac").toLocalDate());
                //alumno.setActivo(rs.getBoolean("activo"));

                JOptionPane.showMessageDialog(null, "Alumno encontrado.");
            } else {
                JOptionPane.showMessageDialog(null, "No hay nadie con ese id.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo buscar el alumno por problemas de conexion.");
        }

        return alumno;
    }

    public Alumno modificarAlumno(int id, Alumno alumno) {

        String sql = "UPDATE alumno SET apellido = ?, nombre = ?, fechaNac = ? WHERE  idAlumno = ? AND activo = 1";
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, alumno.getApellido());
            ps.setString(2, alumno.getNombre());
            ps.setDate(3, Date.valueOf(alumno.getFechaNac()));
            ps.setInt(4, id);
            int exito = ps.executeUpdate();
            
            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Modificado Exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "El alumno no se encuentra activo");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo modificar el alumno.");
        }
        return alumno;
    }

    public List<Alumno> listarAlumnos() {

        List<Alumno> alumnos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM alumno WHERE activo = 1 ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Alumno alumno = new Alumno();

                alumno.setIdAlumno(rs.getInt(1));
                alumno.setApellido(rs.getString(2));
                alumno.setNombre(rs.getString(3));
                alumno.setFechaNac(rs.getDate(4).toLocalDate());
                alumno.setActivo(rs.getBoolean(5));
                alumnos.add(alumno);
            }
            ps.close();
            JOptionPane.showMessageDialog(null, "Lista devuelta con exito.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, " Error en la busqueda.");
        }
        return alumnos;
    }

    public void eliminarAlumno(int id) {

        try {
            String sql = "UPDATE alumno SET activo = 0 WHERE idAlumno = ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, " Se eliminó el alumno.");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " No se pudo eliminar el alumno.");
        }
    }

    public void activarAlumno(int id) {

        try {
            String sql = "UPDATE alumno SET activo = 1 WHERE idAlumno = ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, " Se activo el alumno.");

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, " No se pudo activar el alumno.");
        }
    }

}
