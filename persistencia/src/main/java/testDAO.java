import mx.DC.DAO.AlumnoDAO;
import mx.DC.persistence.HibernateUtil;
import mx.DC.entity.Alumno;

public class testDAO {

    public static void main(String[] args) {
        AlumnoDAO alumnoDAO = new AlumnoDAO(HibernateUtil.getEntityManager());



        for (Alumno alumno : alumnoDAO.findAll()) {
            System.out.println(alumno + "|| id [" + alumno.getId()+ "]");
        }
    }
}
