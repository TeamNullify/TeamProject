package model;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import View.UI;
import View.UserInfo;

import org.json.simple.JSONArray;


public class Project {

    private static final String JSON_FILE_EXTENSION = ".json";
    private UI ui ;

    // Constructor to receive the UI object reference
    public Project(UI ui) {
        this.ui = ui;
    }

    public void openProjectFrame() {
        JFrame projectFrame = new JFrame("Project");
        projectFrame.setSize(400, 300);
        projectFrame.setLocationRelativeTo(null);

        JButton loadButton = new JButton("Load Project");
        JButton saveButton = new JButton("Save Project");
        JButton backButton = new JButton("Back");

        loadButton.addActionListener(e -> loadFile());
        saveButton.addActionListener(e -> saveFile());
        backButton.addActionListener(e -> {
            projectFrame.setVisible(false);
            ui.window.setVisible(true);
        });

        JPanel panel = new JPanel();
        panel.add(loadButton);
        panel.add(saveButton);
        panel.add(backButton);
        projectFrame.add(panel);

        projectFrame.setVisible(true);
    }

    public void loadFile() {
        JSONParser parser = new JSONParser();

        try (FileReader fileReader = new FileReader(UserInfo.JSON_FILE_PATH)) {
            Object obj = parser.parse(fileReader);
            if (obj instanceof JSONArray) {
                JSONArray projectArray = (JSONArray) obj;
                if (!projectArray.isEmpty()) {
                    JFileChooser fileChooser = new JFileChooser();
                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        String selectedFileName = selectedFile.getName();
                        for (Object projectObj : projectArray) {
                            if (projectObj instanceof JSONObject) {
                                JSONObject projectJson = (JSONObject) projectObj;
                                String fileName = (String) projectJson.get("fileName");
                                if (selectedFileName.equals(fileName)) {
                                    // Process the selected file
                                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("No projects available.");
                }
            }
        } catch (IOException | ParseException e) {
            ((Throwable) e).printStackTrace();
        }
    }

    public void saveFile() {
        JSONParser parser = new JSONParser();
        JSONArray projectArray;

        try (FileReader fileReader = new FileReader(UserInfo.JSON_FILE_PATH)) {
            Object obj = parser.parse(fileReader);
            if (obj instanceof JSONArray) {
                projectArray = (JSONArray) obj;
            } else {
                projectArray = new JSONArray();
            }
        } catch (IOException | ParseException e) {
            projectArray = new JSONArray();
        }

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.endsWith(JSON_FILE_EXTENSION)) {
                filePath += JSON_FILE_EXTENSION;
            }

            // Check if the file already exists in the projectArray
            boolean fileExists = false;
            for (Object projectObj : projectArray) {
                if (projectObj instanceof JSONObject) {
                    JSONObject projectJson = (JSONObject) projectObj;
                    String existingFileName = (String) projectJson.get("fileName");
                    if (fileName.equals(existingFileName)) {
                        fileExists = true;
                        break;
                    }
                }
            }

            if (fileExists) {
                System.out.println("File with the same name already exists.");
            } else {
                // Add the file to the projectArray
                JSONObject projectJson = new JSONObject();
                projectJson.put("fileName", fileName);
                projectJson.put("filePath", filePath);
                projectArray.add(projectJson);

                // Save the updated projectArray to the JSON file
                try (FileWriter file = new FileWriter(UserInfo.JSON_FILE_PATH)) {
                    file.write(projectArray.toJSONString());
                    System.out.println("Saved file: " + filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
