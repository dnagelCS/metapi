package nagel.metapi;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

public class MetFrame extends JFrame {
    MetService service;
    MetController controller;

    private JPanel departmentsPanel;
    private JComboBox<MetFeed.DeptList.Department> deptComboBox;
    private JPanel objectPanel;
    private JLabel image;
    private JLabel title;
    private JLabel period;
    private JLabel date;
    private JLabel culture;
    private JLabel medium;
    private JPanel arrowPanel;
    private BasicArrowButton previous;
    private BasicArrowButton next;

    private  int index = 0;
    MetFeed.DeptList.Department selectedDept;
    MetFeed.Objects objects;

    public MetFrame() {
        setSize(700,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MET Virtual Tour");
        setLayout(new BorderLayout());

        departmentsPanel = new JPanel();
        departmentsPanel.setLayout(new BoxLayout(departmentsPanel, BoxLayout.Y_AXIS));
        deptComboBox = new JComboBox<>();
        departmentsPanel.add(deptComboBox);

        //send deptComboBox containing Dept List to controller
        controller.requestDeptList(deptComboBox);

        //when click on specific dept --> object list should be displayed
        deptComboBox.addActionListener(actionEvent -> getObjects());

        objectPanel = new JPanel();
        objectPanel.setLayout(new BoxLayout(objectPanel, BoxLayout.Y_AXIS));
        image = new JLabel();
        title = new JLabel();
        period = new JLabel();
        date = new JLabel();
        culture = new JLabel();
        medium = new JLabel();
        objectPanel.add(image);
        objectPanel.add(title);
        objectPanel.add(period);
        objectPanel.add(date);
        objectPanel.add(culture);
        objectPanel.add(medium);

        //try getMaximumSize()
        arrowPanel = new JPanel();
        arrowPanel.setLayout(new FlowLayout());
        previous = new BasicArrowButton(BasicArrowButton.LEFT);
        previous.addActionListener(actionEvent -> getPreviousObject());
        next = new BasicArrowButton(BasicArrowButton.RIGHT);
        next.addActionListener(actionEvent -> getNextObject());
        arrowPanel.add(previous);
        arrowPanel.add(next);

        add(departmentsPanel, BorderLayout.WEST);
        add(objectPanel, BorderLayout.EAST);    //maybe change to center so pic can go on right
        add(arrowPanel, BorderLayout.SOUTH);

        service = new MetServiceFactory().getInstance();
        controller = new MetController(service, image, title,
                period, date, culture, medium);
    }

    private void getObjects() {
        selectedDept = (MetFeed.DeptList.Department) deptComboBox.getSelectedItem();
        int deptID = selectedDept.departmentId;
        //send deptID to controller
        controller.requestObjects(deptID);
    }

    private void getPreviousObject() {
        //disable at start of objectID list
        if(index == 0) {
            previous.setEnabled(false);
        }
        else {
            previous.setEnabled(true);
        }
        //send object ID to controller
        controller.requestObjectInfo(index);
    }

    private void getNextObject() {
        //disable at end of objectID list
        if(index == objects.objectIDs.size() - 1) {
            next.setEnabled(false);
        }
        else {
            next.setEnabled(true);
        }
        //send object ID to controller
        controller.requestObjectInfo(index);
    }

    public static void main(String[] args) {
        new MetFrame().setVisible(true);
    }
}
