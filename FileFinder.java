import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;


public class FileFinder implements ActionListener {
    JFrame window;
    DefaultListModel<String> myList;
    //DefaultListModel<String> myList2;
    JList<String> jList;
    JButton createNewFolder;
    JButton openFolder;
    JButton home;
    InfoLoaderWriter infoLoaderWriter;
    String userName;
    int index;

    public  FileFinder(String userName){
        this.userName=userName;
        infoLoaderWriter =new InfoLoaderWriter("Ahmed");

        buildInitialUi();
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== createNewFolder) {
            String folderName = JOptionPane.showInputDialog("insert folderName");
            if (!folderName.equals(null)){
                myList.addElement(folderName);
            try {
                infoLoaderWriter.updateMapFolder(userName, folderName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // openFolder.setBorderPainted(true);
        }
        }
        else if (e.getSource()== openFolder) {
            if (jList.getSelectedIndex()!=-1){
                window.setVisible(false);
                openFolder(jList.getSelectedValue());
            }
        }
        else{
            window.setVisible(false);
        }
    }

    public void openFolder(String folderName) {
        DefaultListModel myList2 =new DefaultListModel<>();;
        fillFiles(myList2,folderName);
        JList<String> list2= new JList<>(myList2);;
        list2.setFixedCellHeight(25);
        list2.setFixedCellWidth(25);

        JFrame window2 = new JFrame(folderName);
        window2.setSize(400, 300);
        window2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window2.setLocationRelativeTo(null);
        window2.setLayout(new BorderLayout());

        JPanel panel2= new JPanel();
        panel2.setPreferredSize(new Dimension(50,50));

        JScrollPane scrollPane = new JScrollPane(list2,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton openFile = new JButton("open file");
        openFile.setBounds(50,100,100,50);
        openFile.setLocation(200,200);
        //openFile.setBorderPainted(false);

        JButton createNewFile= new JButton("create file");
        createNewFile.setBounds(50,100,100,50);
        createNewFile.setLocation(300,200);

        JButton back= new JButton("back");
        back.setBounds(50,100,100,50);
        back.setLocation(200,200);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window2.setVisible(false);
                window.setVisible(true);
            }
        });
       // String content;
        createNewFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog("insert fileName");
                if (!fileName.equals(null)){

                String content = JOptionPane.showInputDialog("insert description");
                if (!content.equals(null)) {
                    myList2.addElement(fileName);
                    try {
                        infoLoaderWriter.updateMapFile(userName, folderName, fileName, content);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    // openFile.setBorderPainted(true);
                }
            }
            }
        });

        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int i=0;
                if (list2.getSelectedIndex()!=-1){
                   Folder folder= infoLoaderWriter.getMap().get(userName).getFolders().get(index);
                   Iterator<File>iterator=folder.getFiles().iterator();
                   int counter=0;
                    i=0;
                   while (iterator.hasNext()) {
                       if (iterator.next().getName().equals(list2.getSelectedValue())) {
                           i = counter;
                           break;
                       }
                       counter++;
                   }
                }

                JOptionPane.showMessageDialog(null,infoLoaderWriter.getMap().get(userName).getFolders().get(index).getFiles().get(i).getContent());
            }
        });

        panel2.add(back);
        panel2.add(createNewFile);
        panel2.add(openFile);
        window2.add(panel2,BorderLayout.SOUTH);
        window2.add(scrollPane);
        window2.setVisible(true);
    }
    private void buildInitialUi(){
        window = new JFrame("Project ");
        window.setSize(400, 300);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());

        JPanel panel1= new JPanel();
        panel1.setPreferredSize(new Dimension(50,50));

        openFolder = new JButton("open folder");
        openFolder.setBounds(50,100,100,50);
        openFolder.setLocation(200,200);
        //openFolder.setBorderPainted(false);

        createNewFolder = new JButton("create folder");
        createNewFolder.setBounds(50,100,100,50);
        createNewFolder.setLocation(300,200);

        home=new JButton("Home");
        home.setBounds(50,100,100,50);
        home.setLocation(100,200);

        myList=new DefaultListModel<>();
        fillFolders();

        jList = new JList<>(myList);
        jList.setFixedCellHeight(25);
        jList.setFixedCellWidth(25);
        JScrollPane scrollPane = new JScrollPane(jList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        window.add(panel1,BorderLayout.SOUTH);
        panel1.add(createNewFolder);
        panel1.add(openFolder);
        panel1.add(home);
        window.add(scrollPane);
        window.setVisible(true);

        createNewFolder.addActionListener(this);
        openFolder.addActionListener(this);
        home.addActionListener(this);
    }
    private ListModel<String> fillFolders(){
        Iterator<Folder> iterator=infoLoaderWriter.getMap().get("Ahmed").getFolders().iterator();

        while (iterator.hasNext()) {
            String s=iterator.next().getName();
            myList.addElement(s);
        }

        return myList;
    }
    private void fillFiles(DefaultListModel listModel, String name){
        Iterator<Folder> iterator=infoLoaderWriter.getMap().get("Ahmed").getFolders().iterator();
        String findFolder;
        Folder folder;
        int count=0;
          /////
         index=0;
         ////
        while (iterator.hasNext()){
            folder=iterator.next();
            findFolder=folder.getName();
            if (findFolder.equals(name)) {
                index = count;
                break;
            }
            count++;
        }
        Iterator<File> iterator1=  infoLoaderWriter.getMap().get("Ahmed").getFolders().get(index).getFiles().iterator();
        while (iterator1.hasNext()){
            String s=iterator1.next().getName();
            listModel.addElement(s);
        }
      //  return listModel;
    }
}
