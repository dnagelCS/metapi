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

    private  int index;
    MetFeed.DeptList.Department selectedDept;
    MetFeed.Objects objects;

    public MetFrame() {
        setSize(700,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MET Virtual Tour");
        setLayout(new BorderLayout());

        departmentsPanel = new JPanel();
        departmentsPanel.setLayout(new FlowLayout());
        deptComboBox = new JComboBox<>();
        deptComboBox.isEditable();
        departmentsPanel.add(deptComboBox);

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

        arrowPanel = new JPanel();
        arrowPanel.setLayout(new FlowLayout());
        previous = new BasicArrowButton(BasicArrowButton.WEST);
        /* Is there a way to resize the button? It seems that the class
        BasicArrowButton overrides the method setSize(). */
        previous.setSize(200,200);
        previous.addActionListener(actionEvent -> getPreviousObject());
        next = new BasicArrowButton(BasicArrowButton.EAST);
        next.addActionListener(actionEvent -> getNextObject());
        arrowPanel.add(previous);
        arrowPanel.add(next);

        add(departmentsPanel, BorderLayout.WEST);
        add(objectPanel, BorderLayout.CENTER);
        add(arrowPanel, BorderLayout.SOUTH);

        service = new MetServiceFactory().getInstance();
        controller = new MetController(service, deptComboBox, image, title,
                period, date, culture, medium, previous, next);
        //send deptComboBox containing Dept List to controller
        controller.requestDeptList();
    }

    private void getObjects() {
        selectedDept = (MetFeed.DeptList.Department) deptComboBox.getSelectedItem();
        int deptID = selectedDept.departmentId;
        //send deptID to controller
        controller.requestObjects(deptID);
        index = 0;
    }

    private void getPreviousObject() {
        index--;
        //send object ID to controller
        controller.requestObjectInfo(index);
    }

    private void getNextObject() {
        index++;
        //send object ID to controller
        controller.requestObjectInfo(index);
    }

    public static void main(String[] args) {
        new MetFrame().setVisible(true);
    }
}
