package nagel.metapi;

import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MetControllerTest {

    @Test
    public void requestDeptList() {
        //given
        MetService service = mock(MetService.class);
        JLabel label = mock(JLabel.class);
        JComboBox<MetFeed.DeptList.Department> deptComboBox = mock(JComboBox.class);
        JButton arrowButton = mock(JButton.class);
        MetController controller = new MetController(service, deptComboBox,
                label, label, label, label, label, label, arrowButton, arrowButton);
        Call<MetFeed.DeptList> call = mock(Call.class);
        doReturn(call).when(service).getDepartments();

        //when
        controller.requestDeptList();

        //then
        verify(service).getDepartments();
        verify(service.getDepartments()).enqueue(any());
    }

    @Test
    public void onResponseDept() {
        //given
        MetService service = mock(MetService.class);
        JLabel label = mock(JLabel.class);
        JComboBox<MetFeed.DeptList.Department> deptComboBox = mock(JComboBox.class);
        JButton arrowButton = mock(JButton.class);
        MetController controller = new MetController(service, deptComboBox,
                label, label, label, label, label, label, arrowButton, arrowButton);
        Call<MetFeed.DeptList> call = mock(Call.class);
        Response<MetFeed.DeptList> response = mock(Response.class);

        MetFeed.DeptList deptList = new MetFeed.DeptList();
        MetFeed.DeptList.Department dept = new MetFeed.DeptList.Department();

        dept.displayName = "Department 1";
        dept.departmentId = 1;

        List<MetFeed.DeptList.Department> departments = new ArrayList<>();
        departments.add(dept);
        deptList.departments = departments;

        doReturn(deptList).when(response).body();

        //when
        controller.getCallDept().onResponse(call, response);

        //then
        verify(deptComboBox).addItem(deptList.departments.get(0));
    }

    @Test
    public void requestObjects() {
        //given
        MetService service = mock(MetService.class);
        JLabel label = mock(JLabel.class);
        JComboBox<MetFeed.DeptList.Department> deptComboBox = mock(JComboBox.class);
        JButton arrowButton = mock(JButton.class);
        MetController controller = new MetController(service, deptComboBox,
                label, label, label, label, label, label, arrowButton, arrowButton);
        Call<MetFeed.Objects> call = mock(Call.class);
        doReturn(call).when(service).getObjects(1);

        //when
        controller.requestObjects(1);

        //then
        verify(service).getObjects(1);
        verify(service.getObjects(1)).enqueue(any());
    }

    @Test
    public void onResponseObjects() {
        //given
        MetService service = mock(MetService.class);
        JLabel label = mock(JLabel.class);
        JComboBox<MetFeed.DeptList.Department> deptComboBox = mock(JComboBox.class);
        JButton arrowButton = mock(JButton.class);
        MetController controller = new MetController(service, deptComboBox,
                label, label, label, label, label, label, arrowButton, arrowButton);
        Call<MetFeed.Objects> call = mock(Call.class);
        Call<MetFeed.ObjectInfo> objectInfoCall = mock(Call.class);
        Response<MetFeed.Objects> response = mock(Response.class);

        MetFeed.Objects objects = new MetFeed.Objects();
        ArrayList<Integer> objIdList = new ArrayList<>();
        objIdList.add(1999);
        objects.objectIDs = objIdList;

        doReturn(objects).when(response).body();
        doReturn(objectInfoCall).when(service).getObjectInfo(objects.objectIDs.get(0));

        //when
        controller.getCallObjects().onResponse(call, response);

        //then
        assertEquals(controller.objectIDs, objects.objectIDs);
        verify(service).getObjectInfo(1999);
    }

    @Test
    public void requestObjectInfo() {
        //given
        MetService service = mock(MetService.class);
        JLabel label = mock(JLabel.class);
        JComboBox<MetFeed.DeptList.Department> deptComboBox = mock(JComboBox.class);
        JButton arrowButton = mock(JButton.class);
        MetController controller = new MetController(service, deptComboBox,
                label, label, label, label, label, label, arrowButton, arrowButton);
        Call<MetFeed.ObjectInfo> call = mock(Call.class);
        List<Integer> objectIDs = mock(List.class);
        controller.objectIDs = objectIDs;
        doReturn(1).when(controller.objectIDs).size();
        doReturn(1).when(controller.objectIDs).get(0);
        doReturn(call).when(service).getObjectInfo(1);

        //when
        controller.requestObjectInfo(0);

        //then
        verify(service).getObjectInfo(1);
        verify(service.getObjectInfo(1)).enqueue(any());
    }

    @Test 
    public void onResponseObjectInfo() {
        //given
        MetService service = mock(MetService.class);
        JLabel label = mock(JLabel.class);
        JComboBox<MetFeed.DeptList.Department> deptComboBox = mock(JComboBox.class);
        JButton arrowButton = mock(JButton.class);
        MetController controller = new MetController(service, deptComboBox,
                label, label, label, label, label, label, arrowButton, arrowButton);
        Call<MetFeed.ObjectInfo> call = mock(Call.class);
        Response<MetFeed.ObjectInfo> response = mock(Response.class);

        MetFeed.ObjectInfo metadata = new MetFeed.ObjectInfo();
        metadata.title = "title";
        metadata.period = "period";
        metadata.objectDate = "date";
        metadata.culture = "culture";
        metadata.medium = "medium";
        metadata.primaryImage = "";

        doReturn(metadata).when(response).body();

        //when
        controller.getCallObjectInfo().onResponse(call, response);

        //then
        verify(label).setText("Title: " + metadata.title);
        verify(label).setText("Period: " + metadata.period);
        verify(label).setText("Date: " + metadata.objectDate);
        verify(label).setText("Culture: " + metadata.culture);
        verify(label).setText("Medium: " + metadata.medium);
        verify(label).setText("No image");
    }
}
