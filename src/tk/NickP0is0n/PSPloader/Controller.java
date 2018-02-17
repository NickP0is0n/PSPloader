package tk.NickP0is0n.PSPloader;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Controller {
    private static Path path;
    private static Path path2;
    private static File SelectedFile;
    private static File SelectedDir;


    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTextField isofield;

    @FXML
    private JFXButton isobutton;

    @FXML
    private JFXButton msbutton;

    @FXML
    private JFXButton installbutton;

    private void ShowMaterialMessage(String title, String body) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(title));
        content.setBody(new Text(body));
        JFXButton button = new JFXButton("ОК");
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        content.setActions(button);
        dialog.show();
    }

    @FXML
    void installclick(ActionEvent event) {
        if(isofield.getText().equalsIgnoreCase("")) ShowMaterialMessage("Ошибка", "Укажите путь к файлу ISO.");
        else
        {
            Path output = Paths.get(SelectedDir.toPath() + "\\ISO\\" + SelectedFile.getName());
            File outputDir = new File(SelectedDir.toPath() + "\\ISO");
            if(!outputDir.exists())
            {
                outputDir.mkdir();
                ShowMaterialMessage("Предупреждение", "Папка ISO в корне Memory Stick отсутствовала\n" +
                        "и была создана программой.");
            }
            Path input = Paths.get(SelectedFile.toString());
            boolean copySuccessfully = true;
            try {
                Files.copy(input, output, REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                copySuccessfully = false;
                ShowMaterialMessage("Ошибка", "Произошла ошибка при записи игры.");
            }
            if (copySuccessfully) ShowMaterialMessage("Успех!", "Игра успешно записана на вашу консоль.\n" +
                    "Приятной игры!");
            System.out.println(output.toString());
        }
    }

    @FXML
    File isoclick(ActionEvent event) {
        FileChooser choose = new FileChooser();
        choose.setTitle("Open ISO game file");
        choose.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("ISO Files", "*.iso"));
        SelectedFile = choose.showOpenDialog(null);
        isofield.setText(SelectedFile.getAbsolutePath());
        return SelectedFile;
    }

    @FXML
    File msclick(ActionEvent event) {
        DirectoryChooser choose = new DirectoryChooser();
        choose.setTitle("Select Memory Stick");
        SelectedDir = choose.showDialog(null);
        System.out.println(SelectedDir);
        return SelectedDir;
    }

}
