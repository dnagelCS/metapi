package nagel.metapi;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

//Request data from MetService and populate the view/frame
public class MetController {

    private MetService service;
    private List<MetFeed.DeptList.Department> departments;
    private List<Integer> objectIDs;
    JComboBox<MetFeed.DeptList.Department> deptComboBox;
    JLabel image;
    JLabel title;
    JLabel period;
    JLabel date;
    JLabel culture;
    JLabel medium;

    public MetController(MetService service, JComboBox<MetFeed.DeptList.Department> deptComboBox, JLabel image,
                         JLabel title, JLabel period,
                         JLabel date, JLabel culture,
                         JLabel medium) {
        this.service = service;
        this.deptComboBox = deptComboBox;
        this.image = image;
        this.title = title;
        this.period = period;
        this.date = date;
        this.culture = culture;
        this.medium = medium;
    }

    //DEPT LIST
    public void requestDeptList() {
        service.getDepartments().enqueue(new Callback<MetFeed.DeptList>() {
            @Override
            public void onResponse(Call<MetFeed.DeptList> call, Response<MetFeed.DeptList> response) {
                MetFeed.DeptList deptList = response.body();
                departments = deptList.departments;
                //add every dept in list to deptComboBox
                for (MetFeed.DeptList.Department department : departments) {
                    deptComboBox.addItem(department);
                }
            }

            @Override
            public void onFailure(Call<MetFeed.DeptList> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //OBJECTS
    public void requestObjects(int deptID) {
        service.getObjects(deptID).enqueue(new Callback<MetFeed.Objects>() {
            @Override
            public void onResponse(Call<MetFeed.Objects> call, Response<MetFeed.Objects> response) {
                MetFeed.Objects objects = response.body();
                assert objects != null;
                objectIDs = objects.objectIDs;
                //automatically begin with 1st object ID
                requestObjectInfo(0);
            }

            @Override
            public void onFailure(Call<MetFeed.Objects> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //OBJECT METADATA
    public void requestObjectInfo(int objectID) {
        service.getObjectInfo(objectIDs.get(objectID)).enqueue(new Callback<MetFeed.ObjectInfo>() {
            @Override
            public void onResponse(Call<MetFeed.ObjectInfo> call, Response<MetFeed.ObjectInfo> response) {
                MetFeed.ObjectInfo objectInfo = response.body();
                assert objectInfo != null;
                image.setSize(100,100);
                if(objectInfo.primaryImage.equals("")) {
                    image.setIcon(null);
                    image.setText("No image");
                }
                else {
                    try {
                        URL url = new URL(objectInfo.primaryImage);
                        BufferedImage buffImage = ImageIO.read(url);
                        Image resizedImage = buffImage.getScaledInstance(
                                image.getWidth(), image.getHeight(), Image.SCALE_SMOOTH);
                        image.setIcon(new ImageIcon(resizedImage));
                    } catch(IOException exc) {
                        exc.printStackTrace();
                    }
                }
                title.setText(objectInfo.title);
                period.setText(objectInfo.period);
                date.setText(objectInfo.objectDate);
                culture.setText(objectInfo.culture);
                medium.setText(objectInfo.medium);
            }

            @Override
            public void onFailure(Call<MetFeed.ObjectInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
