package nagel.metapi;

import java.util.List;

public class MetFeed {

    static class DeptList {
        List<Department> departments;

        static class Department {
            int departmentId;
            String displayName;

            @Override
            public String toString() {
                return displayName;
            }
        }
    }

    static class Objects {
        int total;
        List<Integer> objectIDs;
    }

    static class ObjectInfo {
        int objectID;
        String primaryImage;
        String title;
        String culture;
        String period;
        String objectDate;
        String medium;
    }
}
