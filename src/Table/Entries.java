package Table;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class Entries {
    public String name;
    public String measuredValue;
    public String errorValue;
    public String unit;
    public String type;

    public Entries(String name, String measuredValue, String errorValue, String unit, String type) {
        this.name = name;
        this.measuredValue = measuredValue;
        this.errorValue = errorValue;
        this.unit = unit;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public String getMeasuredValue() {
        return this.measuredValue;
    }

    public String getErrorValue() {
        return this.errorValue;
    }

    public String getUnit() {
        return this.unit;
    }

    public String getType() {
        return this.type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMeasuredValue(String measuredValue) {
        this.measuredValue = measuredValue;
    }

    public void setErrorValue(String errorValue) {
        this.errorValue = errorValue;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<Entries> readRepo() {
        ArrayList toReturn = new ArrayList();

        try {
            File file = new File(System.getProperty("user.dir") + "//repo.txt");
            FileReader reader = new FileReader(file);
            String[] arr = new String[]{"", "", "", "", ""};
            int arrIndx = 0;

            int i;
            while((i = reader.read()) != -1) {
                if ((char)i == ',') {
                    ++arrIndx;
                } else if ((char)i == '\n') {
                    arrIndx = 0;
                    toReturn.add(new Entries(arr[0], arr[1], arr[2], arr[3], arr[4]));
                    arr = new String[]{"", "", "", "", ""};
                } else {
                    arr[arrIndx] = arr[arrIndx] + (char)i;
                }
            }

            reader.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return toReturn;
    }

    public static void removeEntry(String name) {
    }

    public static Entries searchEntry(String name) {
        ArrayList<Entries> list = readRepo();

        for(int i = 0; i <= list.size() - 1; ++i) {
            if (((Entries)list.get(i)).getName().equals(name)) {
                return (Entries)list.get(i);
            }
        }

        return null;
    }

    public static void updateEntry(String name) {
    }

    public static void hilight(TableView<Entries> tableView, String name) {
        System.out.println("FIRST");
        tableView.setRowFactory((tv) -> {
            return new TableRow<Entries>() {
                public void updateItem(Entries item, boolean empty) {
                    System.out.println("INSIDE");
                    super.updateItem(item, empty);
                    if (item == null) {
                        System.out.println("1");
                        this.setStyle("");
                    } else if (item.getName().equals(name)) {
                        System.out.println("2");
                        System.out.println("()()()()" + name);
                        this.setStyle("-fx-background-color: tomato;");
                    } else {
                        System.out.println("3");
                        this.setStyle("");
                    }

                }
            };
        });
    }
}
