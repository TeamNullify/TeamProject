package model;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import View.UI;
import View.UserInfo;
import View.WindowFrame;

public class Project {

    private static final String JSON_FILE_EXTENSION = ".json";
    private UI ui;
    private JPanel filePanel;
    public static JFrame projectFrame = new JFrame("Project");
    public JTextArea fileTextArea;
    private WindowFrame window;

    public Project(WindowFrame window) {
        //this.ui = ui;
        this.window = window;
}

    public void openProjectFrame() {
        projectFrame.setSize(500, 500);
        projectFrame.setLocationRelativeTo(null);

        JButton loadButton = new JButton("Load Project");
        JButton saveButton = new JButton("Save Project");
        JButton backButton = new JButton("Back");
        JButton createFolderButton = new JButton("Create Folder");

        loadButton.addActionListener(e -> loadFile());
        saveButton.addActionListener(e -> saveFile());
        backButton.addActionListener(e -> {
            projectFrame.setVisible(false);
            window.showWindow();
        });
        createFolderButton.addActionListener(e -> createFolder());

        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        toolbarPanel.add(loadButton);
        toolbarPanel.add(saveButton);
        toolbarPanel.add(createFolderButton);
        toolbarPanel.add(backButton);

        filePanel = new JPanel();
        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.Y_AXIS));
        fileTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(fileTextArea);
        filePanel.add(scrollPane);

        //JPanel mainPanel = new JPanel(new BorderLayout());
        //mainPanel.add(toolbarPanel, BorderLayout.SOUTH);
        //mainPanel.add(filePanel, BorderLayout.CENTER);

        //projectFrame.add(mainPanel);
        projectFrame.add(toolbarPanel, BorderLayout.SOUTH);
        projectFrame.add(filePanel, BorderLayout.CENTER);

        //projectFrame.add(mainPanel);

        projectFrame.setVisible(true);

        displayFiles(fileTextArea);
    }

    public void displayFiles(JTextArea fileTextArea) {
        JSONParser parser = new JSONParser();

        try (FileReader fileReader = new FileReader(UserInfo.JSON_FILE_PATH)) {
            Object obj = parser.parse(fileReader);
            if (obj instanceof JSONArray) {
                JSONArray projectArray = (JSONArray) obj;
                if (!projectArray.isEmpty()) {
                    projectArray.sort((a, b) -> {
                        String fileNameA = (String) ((JSONObject) a).get("fileName");
                        String fileNameB = (String) ((JSONObject) b).get("fileName");
                        if (fileNameA != null && fileNameB != null) {
                            return fileNameA.compareToIgnoreCase(fileNameB);
                        } else {
                            return 0;
                        }
                    });
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

                    for (Object projectObj : projectArray) {
                        if (projectObj instanceof JSONObject) {
                            JSONObject projectJson = (JSONObject) projectObj;
                            String fileName = (String) projectJson.get("fileName");
                            String filePath = (String) projectJson.get("filePath");
                            if (fileName != null && filePath != null) {
                                if (isFolder(filePath)) {
                                    JButton folderButton = new JButton(fileName + " (Folder)");
                                    folderButton.addActionListener(e -> openFolder(filePath));
                                    buttonPanel.add(folderButton);
                                } else {
                                    JButton fileButton = new JButton(fileName);
                                    fileButton.addActionListener(e -> openFile(filePath));
                                    buttonPanel.add(fileButton);
                                }
                            }
                        }
                    }

                    JPanel buttonContainerPanel = new JPanel(new BorderLayout());
                    buttonContainerPanel.add(buttonPanel, BorderLayout.NORTH);

                    fileTextArea.setText("");
                    fileTextArea.setEditable(false);

                    JPanel contentPanel = new JPanel(new BorderLayout());
                    //contentPanel.add(fileTextArea, BorderLayout.CENTER);
                    contentPanel.add(buttonContainerPanel, BorderLayout.NORTH);

                    JScrollPane scrollPane = new JScrollPane(contentPanel);
                    fileTextArea.setText("");
                    fileTextArea.setEditable(false);

                    // Clear the existing components from the filePanel
                    filePanel.removeAll();

                    // Add the scroll pane to the filePanel
                    //filePanel.add(scrollPane, BorderLayout.CENTER);
                    filePanel.add(scrollPane);
                    filePanel.add(buttonContainerPanel);

                    // Revalidate the filePanel to update the layout
                    filePanel.revalidate();
                } else {
                    fileTextArea.setText("No projects available.");
                }
            } else {
                fileTextArea.setText("No projects available.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean isFolder(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isDirectory();
    }

    public void openFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            String fileExtension = getFileExtension(file.getName());

            if (fileExtension != null) {
                switch (fileExtension) {
                    case "txt":
                        openTextFile(file);
                        break;
                    case "docx":
                        openFileWithDefaultApplication(file);
                        break;
                    case "xlsx":
                        openFileWithDefaultApplication(file);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Unsupported file format: " + fileExtension);
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Unknown file extension.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "File not found: " + filePath);
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }

    private void openTextFile(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            StringBuilder fileContent = new StringBuilder();
            int data;
            while ((data = fileReader.read()) != -1) {
                fileContent.append((char) data);
            }

            JTextArea textArea = new JTextArea(fileContent.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);

            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(e -> saveTextFile(file, textArea.getText()));

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.add(saveButton);

            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.add(scrollPane, BorderLayout.CENTER);
            contentPanel.add(buttonPanel, BorderLayout.SOUTH);

            JFrame frame = new JFrame(file.getName());
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(contentPanel);
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveTextFile(File file, String content) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
            fileWriter.flush();
            JOptionPane.showMessageDialog(null, "File saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openFileWithDefaultApplication(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile() {
        JSONParser parser = new JSONParser();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(UserInfo.JSON_FILE_PATH);
            Object obj = parser.parse(fileReader);
            JSONArray projectArray = (JSONArray) obj;
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String selectedFilePath = selectedFile.getAbsolutePath();
                String selectedFileName = selectedFile.getName();
                boolean fileIsFolder = isFolder(selectedFilePath);

                if (!fileIsFolder && !selectedFilePath.equals(UserInfo.JSON_FILE_PATH)) {
                    System.out.println("Selected file: " + selectedFilePath);
                    displayFiles(fileTextArea);
                } else if (fileIsFolder) {
                    JOptionPane.showMessageDialog(null, "Selected item is a folder. Please select a file.");
                } else if (selectedFilePath.equals(UserInfo.JSON_FILE_PATH)) {
                    JOptionPane.showMessageDialog(null, "Cannot select the project file itself. Please select another file.");
                }

                boolean fileExists = false;
                for (Object projectObj : projectArray) {
                    if (projectObj instanceof JSONObject) {
                        JSONObject projectJson = (JSONObject) projectObj;
                        String fileName = (String) projectJson.get("fileName");
                        if (fileName != null && selectedFileName.equals(fileName)) {
                            fileExists = true;
                            break;
                        }
                    }
                }

                if (!fileExists && !fileIsFolder && !selectedFilePath.equals(UserInfo.JSON_FILE_PATH)) {
                    JSONObject projectJson = new JSONObject();
                    projectJson.put("fileName", selectedFileName);
                    projectJson.put("filePath", selectedFilePath);
                    projectArray.add(projectJson);

                    try (FileWriter fileWriter = new FileWriter(UserInfo.JSON_FILE_PATH)) {
                        fileWriter.write(projectArray.toJSONString());
                        fileWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (fileIsFolder || selectedFilePath.equals(UserInfo.JSON_FILE_PATH)) {
                    // Do nothing, as the file selected is not valid for adding to the project list.
                } else {
                    System.out.println("File with the same name already exists.");
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveFile() {
        JSONParser parser = new JSONParser();
        JSONArray projectArray;
        boolean fileExists = false;
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            if (!fileName.endsWith(JSON_FILE_EXTENSION)) {
                JOptionPane.showMessageDialog(null, "Invalid file extension. Please save with a '.json' extension.");
                return;
            }

            FileReader fileReader = null;
            FileWriter fileWriter = null;
            try {
                fileReader = new FileReader(UserInfo.JSON_FILE_PATH);
                Object obj = parser.parse(fileReader);
                projectArray = (JSONArray) obj;

                for (Object projectObj : projectArray) {
                    if (projectObj instanceof JSONObject) {
                        JSONObject projectJson = (JSONObject) projectObj;
                        String existingFileName = (String) projectJson.get("fileName");
                        if (existingFileName != null && fileName.equals(existingFileName)) {
                            fileExists = true;
                            break;
                        }
                    }
                }

                if (!fileExists) {
                    JSONObject projectJson = new JSONObject();
                    projectJson.put("fileName", fileName);
                    projectJson.put("filePath", selectedFile.getAbsolutePath());
                    projectArray.add(projectJson);

                    fileWriter = new FileWriter(UserInfo.JSON_FILE_PATH);
                    fileWriter.write(projectArray.toJSONString());
                    fileWriter.flush();
                    JOptionPane.showMessageDialog(null, "Project saved successfully.");

                    displayFiles(fileTextArea);
                } else {
                    JOptionPane.showMessageDialog(null, "Project with the same name already exists.");
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            } finally {
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void createFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showDialog(null, "Create Folder");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String folderName = selectedFile.getName();
            String folderPath = selectedFile.getAbsolutePath();

            if (isFolder(folderPath)) {
                JOptionPane.showMessageDialog(null, "Folder with the same name already exists.");
            } else {
                if (selectedFile.mkdir()) {
                    JSONObject projectJson = new JSONObject();
                    projectJson.put("fileName", folderName + " (Folder)");
                    projectJson.put("filePath", folderPath);
                    saveFolderToJSON(projectJson);
                    displayFiles(fileTextArea);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to create folder.");
                }
            }
        }
    }

    private void saveFolderToJSON(JSONObject folderJson) {
        JSONParser parser = new JSONParser();
        JSONArray projectArray;
        boolean fileExists = false;

        FileReader fileReader = null;
        FileWriter fileWriter = null;
        try {
            fileReader = new FileReader(UserInfo.JSON_FILE_PATH);
            Object obj = parser.parse(fileReader);
            projectArray = (JSONArray) obj;

            for (Object projectObj : projectArray) {
                if (projectObj instanceof JSONObject) {
                    JSONObject projectJson = (JSONObject) projectObj;
                    String existingFileName = (String) projectJson.get("fileName");
                    if (existingFileName != null && folderJson.get("fileName").equals(existingFileName)) {
                        fileExists = true;
                        break;
                    }
                }
            }

            if (!fileExists) {
                projectArray.add(folderJson);

                fileWriter = new FileWriter(UserInfo.JSON_FILE_PATH);
                fileWriter.write(projectArray.toJSONString());
                fileWriter.flush();
                JOptionPane.showMessageDialog(null, "Folder created and added to the project list.");
            } else {
                JOptionPane.showMessageDialog(null, "Project with the same name already exists.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void openFolder(String folderPath) {
        if (isFolder(folderPath)) {
            FolderFrame folderFrame = new FolderFrame();
            folderFrame.setVisible(true);
            projectFrame.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Folder not found: " + folderPath);
        }
    }
}
