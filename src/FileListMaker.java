import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import javax.swing.JFileChooser;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileListMaker
{
    private static Scanner in = new Scanner(System.in);
    private static ArrayList<String> lines = new ArrayList<>();
    private static String line = "";
    private static BufferedReader inFile;
    private static PrintWriter outFile;
    private static String curFileName = "";
    private static boolean hasAFile = false;
    private static boolean newUnsavedFile = false;
    private static boolean fileSaveFlag = false;

    public static void main(String[] args)
    {
        String menuPrompt = "A - Add  I - Insert  D - Delete  V - View  Q - Quit  M - Move  O - Open  S - Save  C - Clear";
        String cmd = "";

        boolean done = false;
        boolean confirmQuit = false;

        do {
            displayList();

            cmd = SafeInput.getRegExString(in, menuPrompt, "[AaIiDdVvQqMmOoSsCc]");

            cmd = cmd.toUpperCase();

            try {
                switch (cmd) {
                    case "A":
                        addItem();
                        break;

                    case "I":
                        insertItem();
                        break;

                    case "D":
                        deleteItem();
                        break;

                    case "M":
                        moveItem();
                        break;

                    case "O":
                        if (newUnsavedFile || fileSaveFlag) {
                            String prompt = "Are you sure, You have an unsaved File. Opening a new one";
                            boolean saveYN = SafeInput.getYNConfirm(in, prompt);

                            if (saveYN)
                            {
                                saveFile();
                                openFile();
                            }
                        } else {
                            openFile();
                        }
                        break;

                    case "S":
                        saveFile();
                        break;

                    case "C":
                        clearList();
                        break;

                    case "V":
                        viewList();
                        break;

                    case "Q":
                        confirmQuit = SafeInput.getYNConfirm(in, "You have unsaved changes. Save before quitting? ");

                        if (confirmQuit) {
                            done = true;
                        }
                        break;
                                }
                            }
                            catch(IOException e)
                            {
                                System.out.print("File error");
                            }



            } while (!done) ;
        }

        private static void insertItem()
        {
            int insertIndex = 0;

            insertIndex = SafeInput.getRangedInt(in, "Enter the number for the insertion", 1, lines.size() + 1);
            insertIndex = insertIndex - 1;

            line = SafeInput.getNonZeroLengthString(in, "Enter the new item to add to the list");
            lines.add(insertIndex, line);
            fileSaveFlag = true;
        }

        private static void viewList()
        {
            displayList();
        }

        private static void addItem()
        {
            line = SafeInput.getNonZeroLengthString(in, "Enter the new item to add to the list");
            lines.add(line);
            fileSaveFlag = true;
        }

        private static void deleteItem()
        {
            int itemToDelete = 0;

            itemToDelete = SafeInput.getRangedInt(in, "Enter the number of the item to delete", 1, lines.size());
            itemToDelete = itemToDelete - 1;

            lines.remove(itemToDelete);
            fileSaveFlag = true;
        }

        private static void displayList()
        {
            System.out.println("====================================================");

            if (lines.size() > 0)
            {
                int itemNum = 1;

                for (String l : lines)
                {
                    System.out.println(itemNum + ". " + l);
                    itemNum++;
                }
            }
            else
            {
                System.out.println("Currently, the list is empty!");
            }

            System.out.println("====================================================");
        }

        private static void openFile() throws FileNotFoundException, IOException
        {
            JFileChooser chooser = new JFileChooser();
            Path path = Paths.get(System.getProperty("user.dir"), "\\src");

            chooser.setCurrentDirectory(new File(path.toString()));

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                inFile = new BufferedReader(new FileReader(chooser.getSelectedFile()));

                lines.clear();

                while (inFile.ready())
                {
                    lines.add(inFile.readLine());
                }

                inFile.close();

                curFileName = chooser.getSelectedFile().getName();
                hasAFile = true;
                newUnsavedFile = false;
                fileSaveFlag = false;
            }
        }

        private static void saveFile() throws FileNotFoundException, IOException
        {
            if (!hasAFile)
            {
                curFileName = SafeInput.getNonZeroLengthString(in, "Enter the file name");
                curFileName = curFileName + ".txt";


            }

            Path path = Paths.get(System.getProperty("user.dir"), "\\src\\", curFileName);

            outFile = new PrintWriter(new BufferedWriter(new FileWriter(curFileName)));

            for (String ln : lines)
            {
                outFile.write(ln, 0, ln.length());
                outFile.write('\n');
            }

            outFile.close();

            System.out.println("List saved to file: " + curFileName);
        }

        private static void clearList()
        {
            if (lines.size() > 0)
            {
                lines.clear();
                fileSaveFlag = true;
            }
            else
            {
                System.out.print("The list is empty");
            }
        }

        private static void moveItem()
        {
            if (lines.size() > 0)
            {
                int itemToMove = SafeInput.getRangedInt(in, "Item to move", 1, lines.size());
                int newPosition = SafeInput.getRangedInt(in, "New position", 1, lines.size());

                String movedItem = lines.remove(itemToMove - 1);
                lines.add(newPosition - 1, movedItem);

                fileSaveFlag = true;
            }
            else
            {
                System.out.print("The list is empty");
            }
        }
    }
