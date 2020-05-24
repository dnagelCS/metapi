package nagel.metapi;

import org.junit.Test;
import retrofit2.Response;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class MetServiceTest {

    @Test
    public void getDepartments() throws IOException {
        //given
        MetService service = new MetServiceFactory().getInstance();

        //when
        Response<MetFeed.DeptList> response = service.getDepartments().execute();

        //then
        assertTrue(response.toString(), response.isSuccessful());
        MetFeed.DeptList deptList = response.body();
        assertNotNull(deptList);

        List<MetFeed.Department> departments = deptList.departments;
        assertFalse(departments.isEmpty());
        MetFeed.Department department = departments.get(0);
        assertNotNull(department.displayName);
        assert(department.departmentId > 0);
    }

    @Test
    public void getObjectsList() throws IOException {
        //given
        MetService service = new MetServiceFactory().getInstance();

        //when
        Response<MetFeed.ObjectsList> response = service.getObjects(18).execute();

        //then
        assertTrue(response.toString(), response.isSuccessful());
        MetFeed.ObjectsList objectsList = response.body();
        assertNotNull(objectsList);
        assert(objectsList.total > 0);

        List<Integer> objectIDs = objectsList.objectIDs;
        assertFalse(objectIDs.isEmpty());
        int objectID = objectIDs.get(0);
        assert(objectID > 0);
    }

    @Test
    public void getObjectInfo() throws IOException {
        //given
        MetService service = new MetServiceFactory().getInstance();

        //when
        Response<MetFeed.Object> response = service.getObjectInfo(501607).execute();

        //then
        assertTrue(response.toString(), response.isSuccessful());
        MetFeed.Object object = response.body();
        assertNotNull(object);

        assertNotNull(object.primaryImage);
        assertNotNull(object.title);
        assertNotNull(object.period);
        assertNotNull(object.objectDate);
        assertNotNull(object.culture);
        assertNotNull(object.medium);
    }
}
