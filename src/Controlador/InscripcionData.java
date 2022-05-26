package Controlador;

import Modelo.Alumno;
import Modelo.Inscripcion;
import Modelo.Materia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;



public class InscripcionData {

    private Connection con = null;
    private Conexion conexion;
    

    public InscripcionData(Conexion conexion) {
        try{
            con = conexion.getConexion();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error en la conexion");
        }
    }

    
    public void inscribir(Inscripcion insc) {
        String sql = "INSERT INTO inscripcion(idAlumno, idMateria, nota) VALUES (?, ?, ?)";
            try{
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, insc.getIdAlumno());
                ps.setInt(2, insc.getIdMateria());
                ps.setDouble(3, insc.getNota());
                
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                
                if(rs.next()){
                    insc.setIdInscripcion(rs.getInt(1));
                    JOptionPane.showMessageDialog(null, "Inscripcion agregada con exito.");
                }else{
                    JOptionPane.showMessageDialog(null, "No pudo ser añadido correctamente.");
                }
            }    
            catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error al inscribir alumno.");
            }
    }
    
    public List<Inscripcion> obtenerMCursadas(){
        List<Inscripcion> cursadas = new ArrayList<>();
          
        try {
            String sql = "SELECT * FROM inscripcion;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Inscripcion insc;
            
            while(rs.next()){
                insc = new Inscripcion();
                insc.setIdInscripcion(rs.getInt("id"));
                
                Alumno a = buscarAlumno(rs.getInt("idAlumno"));
                insc.setIdAlumno(a.getIdAlumno());
                
                Materia m = buscarMateria(rs.getInt("idMateria"));
                insc.setIdMateria(m.getIdMateria());
                insc.setNota(rs.getInt("nota"));

                cursadas.add(insc);
            }      
            ps.close();
        }catch (SQLException ex) {
            JOptionPane.showInternalMessageDialog(null, "Error al obtener materias.");
        }
        return cursadas;
    }
    public List<Inscripcion> obtenerMCursadasPorAlumno(int id){
        List<Inscripcion> cursadas = new ArrayList<>();  

        try {
            String sql = "SELECT * FROM inscripcion WHERE idAlumno = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            Inscripcion insc;
            
            while(rs.next()){
                
                insc = new Inscripcion();
                insc.setIdInscripcion(rs.getInt("idInscripcion"));
                
                Alumno a = buscarAlumno(rs.getInt("idAlumno"));
                insc.setIdAlumno(a.getIdAlumno());
                
                Materia m=buscarMateria(rs.getInt("idMateria"));
                insc.setIdMateria(m.getIdMateria());
                insc.setNota(rs.getInt("nota"));
               
                cursadas.add(insc);
            }      
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener materias.");
        }
        return cursadas;
    }

    
    public Alumno buscarAlumno(int id){
        AlumnoData ad = new AlumnoData(conexion);
        return ad.buscarAlumno(id);
        
    }
    
    public Materia buscarMateria(int id){
        MateriaData md=new MateriaData(conexion);
        return md.buscarMateria(id);
    
    }
    
    public List<Materia> obtenerMateriasCursadas(int id){
        List<Materia> materias = new ArrayList<Materia>();  

        try {
            String sql = "SELECT idMateria, nombre FROM inscripcion,"
                    + " materia WHERE inscripcion.idMateria = materia.id\n" +
                "AND inscripcion.idAlumno = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Materia materia;
            while(rs.next()){
                materia = new Materia();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materias.add(materia);
            }      
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener materias.");
        }
        return materias;
    }
    
    public List<Materia> obtenerMateriasNOCursadas(int id){
    List<Materia> materias = new ArrayList<Materia>();
          
        try {
            String sql = "SELECT * FROM materia WHERE id not in "
                    + "(SELECT idMateria FROM inscripcion WHERE idAlumno =?);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            Materia materia;
            
            while(resultSet.next()){
                materia = new Materia();
                materia.setIdMateria(resultSet.getInt("id"));
                materia.setNombre(resultSet.getString("nombre"));
                materias.add(materia);
            }      
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener materias.");
        }
        
        return materias;  
    }
    
    public void borrarInscripcionMateriaAlumno(int idAlumno,int idMateria){
    
        try {
            
            String sql = "DELETE FROM inscripcion WHERE idAlumno =? and idMateria =?;";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
               
            ps.executeUpdate();
             
            ps.close();
    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al borrar.");
        }
    }
    
    public void actualizarNota(int idAlumno,int idMateria, int nota){
    
        try {
            
            String sql = "UPDATE inscripcion SET nota = ? WHERE idAlumno =? and idMateria =?;";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,nota);
            ps.setInt(2, idAlumno);
            ps.setInt(3, idMateria);
           
            ps.executeUpdate(); 
            
            ps.close();
    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar nota.");
        }
    }
 

        public List<Alumno> obtenerAlumnosXMateria(int idMateria){
        List<Alumno> alumnosMateria = new ArrayList<>();  

        try {
            String sql = "SELECT a.idAlumno,nombre,apellido,fechaNac,activo "
                    + "FROM inscripcion i,alumno a WHERE i.idAlumno = a.idAlumno AND idMateria = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,idMateria);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                Alumno alum = new Alumno();
                alum.setIdAlumno(rs.getInt("idAlumno"));
                alum.setNombre(rs.getString("nombre"));
                alum.setApellido(rs.getString("apellido"));
                alum.setFechaNac(rs.getDate("fechaNac").toLocalDate());
                alum.setActivo(true);
                
                alumnosMateria.add(alum);
     
            }      
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener materias.");
        }
        return alumnosMateria;
    }
    }
