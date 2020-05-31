package nagel.metapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MetService {
    //Dept List
    @GET("/public/collection/v1/departments")
    Call<MetFeed.DeptList> getDepartments();

    //Object List
    @GET("/public/collection/v1/objects")
    Call<MetFeed.Objects> getObjects(@Query("departmentIds") int departmentId);

    //Metadata of single object
    @GET("/public/collection/v1/objects/{objectID}")
    Call<MetFeed.ObjectInfo> getObjectInfo(@Path("objectID") int objectID);

}
