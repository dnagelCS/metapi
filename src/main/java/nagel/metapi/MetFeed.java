package nagel.metapi;

import java.util.List;

public class MetFeed {

    class DeptList {
        List<Department> departments;
    }

    class Department {
        int departmentId;
        String displayName;
    }

    class ObjectsList {
        int total;
        List<Integer> objectIDs;
    }

    class Object {
        int objectID;
        String primaryImage;
        String title;
        String culture;
        String period;
        String objectDate;
        String medium;
    }
}
