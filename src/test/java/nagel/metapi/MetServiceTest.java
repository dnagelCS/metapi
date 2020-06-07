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

        List<MetFeed.DeptList.Department> departments = deptList.departments;
        assertFalse(departments.isEmpty());
        MetFeed.DeptList.Department department = departments.get(0);
        assertNotNull(department.displayName);
        assert (department.departmentId > 0);
    }

    @Test
    public void getObjectsList() throws IOException {
        //given
        MetService service = new MetServiceFactory().getInstance();

        //when
        Response<MetFeed.Objects> response = service.getObjects(18).execute();

        //then
        assertTrue(response.toString(), response.isSuccessful());
        MetFeed.Objects objects = response.body();
        assertNotNull(objects);
        assert (objects.total > 0);

        List<Integer> objectIDs = objects.objectIDs;
        assertFalse(objectIDs.isEmpty());
        int objectID = objectIDs.get(0);
        assert (objectID > 0);
    }

    @Test
    public void getObjectInfo() throws IOException {
        //given
        MetService service = new MetServiceFactory().getInstance();

        //when
        Response<MetFeed.ObjectInfo> response = service.getObjectInfo(501607).execute();

        //then
        assertTrue(response.toString(), response.isSuccessful());
        MetFeed.ObjectInfo objectInfo = response.body();
        assertNotNull(objectInfo);

        assertNotNull(objectInfo.primaryImage);
        assertNotNull(objectInfo.title);
        assertNotNull(objectInfo.period);
        assertNotNull(objectInfo.objectDate);
        assertNotNull(objectInfo.culture);
        assertNotNull(objectInfo.medium);
    }
}
