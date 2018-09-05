import java.sql.SQLException;
import java.util.List;

public interface InterfaceDao<T extends EntityDao>
{
      T get(int value)throws SQLException;
      List<T> getAll()throws SQLException;
      boolean insert(T entity)throws SQLException;
      boolean update(T entity)throws SQLException;
      boolean delete(T entity)throws SQLException;
}
