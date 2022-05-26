package Controlador;

import Modelo.Materia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MateriaData {

    private Connection con = null;

    public MateriaData(Conexion conexion) {
        try {
            con = conexion.getConexion();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void guardarMateria(Materia materia) {
        String sql = "INSERT INTO materia (nombre, añoMateria, activo) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAnioMateria());
            ps.setInt(3, materia.isActivo() ? 1 : 0); // if reducido
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                materia.setIdMateria(rs.getInt(1));

                JOptionPane.showMessageDialog(null, "Materia añadida con exito.");
            } else {
                JOptionPane.showMessageDialog(null, "La materia no fue añadido.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo añadir la materia.");
        }

    }

    public Materia buscarMateria(int id) {
        Materia materia = new Materia();
        String sql = "SELECT nombre  FROM materia WHERE idMateria = ? AND activo= 1";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                materia.setIdMateria(id);
                materia.setNombre(rs.getString("nombre"));
                materia.setActivo(rs.getBoolean(1));

                JOptionPane.showMessageDialog(null, "Materia encontrada con exito.");
            } else {
                JOptionPane.showMessageDialog(null, "La materia no fue encontrada.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo buscar la materia.");
        }

        return materia;
    }

    public Materia modificarMateria(int id, Materia materia) {
        String sql = "UPDATE materia SET nombre = ?, añoMateria = ? WHERE  idMateria = ? AND activo = 1";
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAnioMateria());
            ps.setInt(3, id);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Modificacion Exitosa.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo modificar.");
        }
        return materia;
    }

    public void eliminarMateria(int id) {

        try {
            String sql = "UPDATE materia SET activo = 0 WHERE idMateria = ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, " Se eliminó la materia.");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " No se pudo eliminar la materia.");
        }
    }

    public List<Materia> listarMaterias() {

        List<Materia> materias = new ArrayList<>();
        try {
            String sql = "SELECT * FROM materia WHERE activo = 1 ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Materia materia = new Materia();

                materia.setIdMateria(rs.getInt(1));
                materia.setNombre(rs.getString(2));
                materia.setActivo(rs.getBoolean(3));
                materias.add(materia);
            }
            ps.close();
            JOptionPane.showMessageDialog(null, "Lista devuelta con exito.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, " La lista no fue devuelta.");
        }
        return materias;
    }

    public void activarMateria(int id) {

        try {
            String sql = "UPDATE materia SET activo = 1 WHERE idMateria = ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, " Se activo la materia.");

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, " La materia no pudo ser eliminada.");
        }
    }
}
