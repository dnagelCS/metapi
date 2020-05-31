package nagel.metapi;

import java.util.List;

public class MetFeed {

    class DeptList {
        List<Department> departments;

        class Department {
            int departmentId;
            String displayName;
        }
    }

    class Objects {
        int total;
        List<Integer> objectIDs;
    }

    class ObjectInfo {
        int objectID;
        String primaryImage;
        String title;
        String culture;
        String period;
        String objectDate;
        String medium;
    }
}
