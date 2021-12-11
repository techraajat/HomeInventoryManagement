package HomeInventory;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import com.toedter.calendar.*;

import java.io.*;
import java.util.*;
import java.text.*;
import java.awt.print.*;
public class HomeInventory_1 extends JFrame
{
    // Toolbar
    JToolBar inventoryToolBar = new JToolBar();
    JButton newButton = new JButton(new ImageIcon("new.gif"));
    JButton deleteButton = new JButton(new ImageIcon("delete.gif"));
    JButton saveButton = new JButton(new ImageIcon("save.gif"));
    JButton previousButton = new JButton(new ImageIcon("previous.gif"));
    JButton nextButton = new JButton(new ImageIcon("next.gif"));
    JButton printButton = new JButton(new ImageIcon("print.gif"));
    JButton exitButton = new JButton();

    JLabel itemLabel = new JLabel();
    JTextField itemTextField = new JTextField();
    JLabel locationLabel = new JLabel();
    JComboBox<Object> locationComboBox = new JComboBox<>();
    JCheckBox markedCheckBox = new JCheckBox();
    JLabel serialLabel = new JLabel();
    JTextField serialTextField = new JTextField();
    JLabel priceLabel = new JLabel();
    JTextField priceTextField = new JTextField();
    JLabel dateLabel = new JLabel();
    JDateChooser dateDateChooser = new JDateChooser();
    JLabel storeLabel = new JLabel();
    JTextField storeTextField = new JTextField();
    JLabel noteLabel = new JLabel();
    JTextField noteTextField = new JTextField();
    JLabel photoLabel = new JLabel();
    static JTextArea photoTextArea = new JTextArea();
    JButton photoButton = new JButton();
    JPanel searchPanel = new JPanel();
    JButton[] searchButton = new JButton[26];
    PhotoPanel photoPanel = new PhotoPanel();
    static final int maximumEntries = 300;
    static int numberEntries;
    static InventoryItem[] myInventory = new InventoryItem[maximumEntries];
    int currentEntry;
    static final int entriesPerPage = 2;
    static int lastPage;
    public static void main(String[] args)
    {
// create frame
        new HomeInventory_1().setVisible(true);
    }
    public HomeInventory_1()
    {
// frame constructor
        setTitle("Home Inventory Manager");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent evt)
            {
                try {
                    exitForm();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints;
        inventoryToolBar.setFloatable(false);
        inventoryToolBar.setBackground(Color.BLUE);
        inventoryToolBar.setOrientation(SwingConstants.VERTICAL);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.gridheight = 8;
        gridConstraints.fill = GridBagConstraints.VERTICAL;
        getContentPane().add(inventoryToolBar, gridConstraints);
        inventoryToolBar.addSeparator();
        Dimension bSize = new Dimension(70, 50);
        newButton.setText("New");
        sizeButton(newButton, bSize);
        newButton.setToolTipText("Add New Item");
        newButton.setHorizontalTextPosition(SwingConstants.CENTER);
        newButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        newButton.setFocusable(false);
        inventoryToolBar.add(newButton);
        newButton.addActionListener(this::newButtonActionPerformed);
        deleteButton.setText("Delete");
        sizeButton(deleteButton, bSize);
        deleteButton.setToolTipText("Delete Current Item");
        deleteButton.setHorizontalTextPosition(SwingConstants.CENTER);
        deleteButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        deleteButton.setFocusable(false);
        inventoryToolBar.add(deleteButton);
        deleteButton.addActionListener(this::deleteButtonActionPerformed);
        saveButton.setText("Save");
        sizeButton(saveButton, bSize);
        saveButton.setToolTipText("Save Current Item");
        saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
        saveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        saveButton.setFocusable(false);
        inventoryToolBar.add(saveButton);
        saveButton.addActionListener(this::saveButtonActionPerformed);
        inventoryToolBar.addSeparator();
        previousButton.setText("Previous");
        sizeButton(previousButton, bSize);
        previousButton.setToolTipText("Display Previous Item");
        previousButton.setHorizontalTextPosition(SwingConstants.CENTER);
        previousButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        previousButton.setFocusable(false);
        inventoryToolBar.add(previousButton);
        previousButton.addActionListener(this::previousButtonActionPerformed);
        nextButton.setText("Next");
        sizeButton(nextButton, bSize);
        nextButton.setToolTipText("Display Next Item");
        nextButton.setHorizontalTextPosition(SwingConstants.CENTER);
        nextButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        nextButton.setFocusable(false);
        inventoryToolBar.add(nextButton);
        nextButton.addActionListener(this::nextButtonActionPerformed);
        inventoryToolBar.addSeparator();
        printButton.setText("Print");
        sizeButton(printButton, bSize);
        printButton.setToolTipText("Print Inventory List");
        printButton.setHorizontalTextPosition(SwingConstants.CENTER);
        printButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        printButton.setFocusable(false);
        inventoryToolBar.add(printButton);
        printButton.addActionListener(this::printButtonActionPerformed);
        exitButton.setText("Exit");
        sizeButton(exitButton, bSize);
        exitButton.setToolTipText("Exit Program");
        exitButton.setFocusable(false);
        inventoryToolBar.add(exitButton);
        exitButton.addActionListener(e -> {
            try {
                exitButtonActionPerformed();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        itemLabel.setText("Inventory Item");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 0;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(itemLabel, gridConstraints);
        itemTextField.setPreferredSize(new Dimension(400, 25));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 0;
        gridConstraints.gridwidth = 5;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(itemTextField, gridConstraints);
        itemTextField.addActionListener(this::itemTextFieldActionPerformed);
        locationLabel.setText("Location");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 1;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(locationLabel, gridConstraints);
        locationComboBox.setPreferredSize(new Dimension(270, 25));
        locationComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        locationComboBox.setEditable(true);
        locationComboBox.setBackground(Color.WHITE);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 1;
        gridConstraints.gridwidth = 3;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(locationComboBox, gridConstraints);
        locationComboBox.addActionListener(this::locationComboBoxActionPerformed);
        markedCheckBox.setText("Marked?");
        markedCheckBox.setFocusable(false);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 5;
        gridConstraints.gridy = 1;
        gridConstraints.insets = new Insets(10, 10, 0, 0);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(markedCheckBox, gridConstraints);
        serialLabel.setText("Serial Number");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 2;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(serialLabel, gridConstraints);
        serialTextField.setPreferredSize(new Dimension(270, 25));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 2;
        gridConstraints.gridwidth = 3;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(serialTextField, gridConstraints);
        serialTextField.addActionListener(this::serialTextFieldActionPerformed);
        priceLabel.setText("Purchase Price");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 3;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(priceLabel, gridConstraints);
        priceTextField.setPreferredSize(new Dimension(160, 25));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 2;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(priceTextField, gridConstraints);
        priceTextField.addActionListener(this::priceTextFieldActionPerformed);
        dateLabel.setText("Date Purchased");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 4;
        gridConstraints.gridy = 3;
        gridConstraints.insets = new Insets(10, 10, 0, 0);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(dateLabel, gridConstraints);
        dateDateChooser.setPreferredSize(new Dimension(120, 25));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 5;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 2;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(dateDateChooser, gridConstraints);
        dateDateChooser.addPropertyChangeListener(this::dateDateChooserPropertyChange);
        storeLabel.setText("Store/Website");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 4;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(storeLabel, gridConstraints);
        storeTextField.setPreferredSize(new Dimension(400, 25));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 4;
        gridConstraints.gridwidth = 5;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(storeTextField, gridConstraints);
        storeTextField.addActionListener(this::storeTextFieldActionPerformed);
        noteLabel.setText("Note");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 5;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(noteLabel, gridConstraints);
        noteTextField.setPreferredSize(new Dimension(400, 25));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 5;
        gridConstraints.gridwidth = 5;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(noteTextField, gridConstraints);
        noteTextField.addActionListener(this::noteTextFieldActionPerformed);
        photoLabel.setText("Photo");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 6;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(photoLabel, gridConstraints);
        photoTextArea.setPreferredSize(new Dimension(350, 35));
        photoTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
        photoTextArea.setEditable(false);
        photoTextArea.setLineWrap(true);
        photoTextArea.setWrapStyleWord(true);
        photoTextArea.setBackground(new Color(252, 252, 178));
        photoTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        photoTextArea.setFocusable(false);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 6;
        gridConstraints.gridwidth = 4;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(photoTextArea, gridConstraints);
        photoButton.setText("...");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 6;
        gridConstraints.gridy = 6;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(photoButton, gridConstraints);
        photoButton.addActionListener(this::photoButtonActionPerformed);
        searchPanel.setPreferredSize(new Dimension(240, 160));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Item Search"));
        searchPanel.setLayout(new GridBagLayout());
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 7;
        gridConstraints.gridwidth = 3;
        gridConstraints.insets = new Insets(10, 0, 10, 0);
        gridConstraints.anchor = GridBagConstraints.CENTER;
        getContentPane().add(searchPanel, gridConstraints);
        int x = 0, y = 0;
// create and position 26 buttons
        for (int i = 0; i < 26; i++)
        {
// create new button
            searchButton[i] = new JButton();
// set text property
            searchButton[i].setText(String.valueOf((char) (65 + i)));
            searchButton[i].setFont(new Font("Arial", Font.BOLD, 12));
            searchButton[i].setMargin(new Insets(-10, -10, -10, -10));
            sizeButton(searchButton[i], new Dimension(37, 27));
            searchButton[i].setBackground(Color.YELLOW);
            searchButton[i].setFocusable(false);
            gridConstraints = new GridBagConstraints();
            gridConstraints.gridx = x;
            gridConstraints.gridy = y;
            searchPanel.add(searchButton[i], gridConstraints);
// add method
            searchButton[i].addActionListener(this::searchButtonActionPerformed);
            x++;
// six buttons per row
            if (x % 6 == 0)
            {
                x = 0;
                y++;
            }
        }
        photoPanel.setPreferredSize(new Dimension(240, 160));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 4;
        gridConstraints.gridy = 7;
        gridConstraints.gridwidth = 3;
        gridConstraints.insets = new Insets(10, 0, 10, 10);
        gridConstraints.anchor = GridBagConstraints.CENTER;
        getContentPane().add(photoPanel, gridConstraints);
        pack();
        Dimension screenSize =
                Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int) (0.5 * (screenSize.width - getWidth())), (int) (0.5 * (screenSize.height -
                getHeight())), getWidth(), getHeight());
        int n;
// open file for entries
        try
        {
            BufferedReader inputFile = new BufferedReader(new FileReader("inventory.txt"));
            numberEntries =
                    Integer.parseInt(inputFile.readLine());
            if (numberEntries != 0)
            {
                for (int i = 0; i < numberEntries; i++)
                {
                    myInventory[i] = new InventoryItem();
                    InventoryItem.description = inputFile.readLine();
                    InventoryItem.location = inputFile.readLine();
                    InventoryItem.serialNumber = inputFile.readLine();
                    InventoryItem.marked =
                            Boolean.parseBoolean(inputFile.readLine());
                    InventoryItem.purchasePrice =
                            inputFile.readLine();
                    InventoryItem.purchaseDate = inputFile.readLine();
                    InventoryItem.purchaseLocation =
                            inputFile.readLine();
                    InventoryItem.note = inputFile.readLine();
                    InventoryItem.photoFile = inputFile.readLine();
                }
            }
// read in combo box elements
            n = Integer.parseInt(inputFile.readLine());
            if (n != 0)
            {
                for (int i = 0; i < n; i++)
                {
                    locationComboBox.addItem(inputFile.readLine());
                }
            }
            inputFile.close();
            currentEntry = 1;
            showEntry(currentEntry);
        }
        catch (Exception ex)
        {
            numberEntries = 0;
            currentEntry = 0;
        }
        if (numberEntries == 0)
        {
            newButton.setEnabled(false);
            deleteButton.setEnabled(false);
            nextButton.setEnabled(false);
            previousButton.setEnabled(false);
            printButton.setEnabled(false);
        }
    }
    private void exitForm() throws Exception {
        if (JOptionPane.showConfirmDialog(null, "Any unsaved changes will be lost.\nAre you "
                        + "sure you want to exit?", "Exit Program", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
            return;
// write entries back to file
        PrintWriter outputFile = new PrintWriter(new BufferedWriter(new FileWriter("inventory.txt")));
        outputFile.println(numberEntries);
        if (numberEntries != 0)
        {
            for (int i = 0; i < numberEntries; i++)
            {
                outputFile.println(InventoryItem.description);
                outputFile.println(InventoryItem.location);
                outputFile.println(InventoryItem.serialNumber);
                outputFile.println(InventoryItem.marked);
                outputFile.println(InventoryItem.purchasePrice);
                outputFile.println(InventoryItem.purchaseDate);
                outputFile.println(InventoryItem.purchaseLocation);
                outputFile.println(InventoryItem.note);
                outputFile.println(InventoryItem.photoFile);
            }
        }
// write combo box entries
        outputFile.println(locationComboBox.getItemCount());
        if (locationComboBox.getItemCount() != 0)
        {
            for (int i = 0; i < locationComboBox.getItemCount(); i++)
                outputFile.println(locationComboBox.getItemAt(i));
        }
        outputFile.close();
        System.exit(0);
    }
    private void newButtonActionPerformed(ActionEvent e)
    {
        checkSave();
        blankValues();
    }
    private void deleteButtonActionPerformed(ActionEvent e)
    {
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?",
                "Delete Inventory Item", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
            return;
        deleteEntry(currentEntry);
        if (numberEntries == 0)
        {
            currentEntry = 0;
            blankValues();
        }
        else
        {
            currentEntry--;
            if (currentEntry == 0)
                currentEntry = 1;
            showEntry(currentEntry);
        }
    }
    private void saveButtonActionPerformed(ActionEvent e)
    {
// check for description
        itemTextField.setText(itemTextField.getText().trim());
        if (itemTextField.getText().equals(""))
        {
            JOptionPane.showConfirmDialog(null, "Must have item description.", "Error",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            itemTextField.requestFocus();
            return;
        }
        if (newButton.isEnabled())
        {
// delete edit entry then resave
            deleteEntry(currentEntry);
        }
// capitalize first letter
        String s = itemTextField.getText();
        itemTextField.setText(s.substring(0, 1).toUpperCase() + s.substring(1));
        numberEntries++;
// determine new current entry location based on description
        currentEntry = 1;
        if (numberEntries != 1)
        {
            do
            {
                if
                (itemTextField.getText().compareTo(InventoryItem.description) < 0)
                    break;
                currentEntry++;
            }
            while (currentEntry < numberEntries);
        }
// move all entries below new value down one position unless at end
        if (currentEntry != numberEntries)
        {
            for (int i = numberEntries; i >= currentEntry + 1; i--)
            {
                myInventory[i - 1] = myInventory[i - 2];
                myInventory[i - 2] = new InventoryItem();
            }
        }
        myInventory[currentEntry - 1] = new InventoryItem();
        InventoryItem.description = itemTextField.getText();
        InventoryItem.location =
                Objects.requireNonNull(locationComboBox.getSelectedItem()).toString();
        InventoryItem.marked = markedCheckBox.isSelected();
        InventoryItem.serialNumber = serialTextField.getText();
        InventoryItem.purchasePrice = priceTextField.getText();
        InventoryItem.purchaseDate =
                dateToString(dateDateChooser.getDate());
        InventoryItem.purchaseLocation = storeTextField.getText();
        InventoryItem.photoFile = photoTextArea.getText();
        InventoryItem.note = noteTextField.getText();
        showEntry(currentEntry);
        newButton.setEnabled(numberEntries < maximumEntries);
        deleteButton.setEnabled(true);
        printButton.setEnabled(true);
    }
    private void previousButtonActionPerformed(ActionEvent e)
    {
        checkSave();
        currentEntry--;
        showEntry(currentEntry);
    }
    private void nextButtonActionPerformed(ActionEvent e)
    {
        checkSave();
        currentEntry++;
        showEntry(currentEntry);
    }
    private void printButtonActionPerformed(ActionEvent e)
    {
        lastPage = 1 + (numberEntries - 1) / entriesPerPage;
        PrinterJob inventoryPrinterJob = PrinterJob.getPrinterJob();
        inventoryPrinterJob.setPrintable(new Inventorying());
        if (inventoryPrinterJob.printDialog())
        {
            try
            {
                inventoryPrinterJob.print();
            }
            catch (PrinterException ex)
            {
                JOptionPane.showConfirmDialog(null, ex.getMessage(), "Print Error",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void exitButtonActionPerformed() throws Exception {
        exitForm();
    }
    private void photoButtonActionPerformed(ActionEvent e)
    {
        JFileChooser openChooser = new JFileChooser();
        openChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        openChooser.setDialogTitle("Open Photo File");
        openChooser.addChoosableFileFilter(new FileNameExtensionFilter("Photo Files",
                "jpg"));
        if (openChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            showPhoto(openChooser.getSelectedFile().toString());
    }
    private void searchButtonActionPerformed(ActionEvent e)
    {
        int i;
        if (numberEntries == 0)
            return;
// search for item letter
        String letterClicked = e.getActionCommand();
        i = 0;
        do
        {
            if (InventoryItem.description.substring(0, 1).equals(letterClicked))
            {
                currentEntry = i + 1;
                showEntry(currentEntry);
                return;
            }
            i++;
        }
        while (i < numberEntries);
        JOptionPane.showConfirmDialog(null, "No " + letterClicked + " inventory items.",
                "None Found", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
    }
    private void itemTextFieldActionPerformed(ActionEvent e)
    {
        locationComboBox.requestFocus();
    }
    private void locationComboBoxActionPerformed(ActionEvent e)
    {
// If in list - exit method
        if (locationComboBox.getItemCount() != 0)
        {
            for (int i = 0; i < locationComboBox.getItemCount(); i++)
            {
                if (Objects.requireNonNull(locationComboBox.getSelectedItem()).toString().equals(locationComboBox.getItemAt(i).toString()))
                {
                    serialTextField.requestFocus();
                    return;
                }
            }
        }
// If not found, add to list box
        locationComboBox.addItem(locationComboBox.getSelectedItem());
        serialTextField.requestFocus();
    }
    private void serialTextFieldActionPerformed(ActionEvent e)
    {
        priceTextField.requestFocus();
    }
    private void priceTextFieldActionPerformed(ActionEvent e)
    {
        dateDateChooser.requestFocus();
    }
    private void dateDateChooserPropertyChange(PropertyChangeEvent e)
    {
        storeTextField.requestFocus();
    }
    private void storeTextFieldActionPerformed(ActionEvent e)
    {
        noteTextField.requestFocus();
    }
    private void noteTextFieldActionPerformed(ActionEvent e)
    {
        photoButton.requestFocus();
    }
    private void sizeButton(JButton b, Dimension d)
    {
        b.setPreferredSize(d);
        b.setMinimumSize(d);
        b.setMaximumSize(d);
    }
    private void showEntry(int j)
    {
// display entry j (1 to numberEntries)
        itemTextField.setText(InventoryItem.description);
        locationComboBox.setSelectedItem(InventoryItem.location);
        markedCheckBox.setSelected(InventoryItem.marked);
        serialTextField.setText(String.valueOf(InventoryItem.serialNumber));
        priceTextField.setText(InventoryItem.purchasePrice);
        dateDateChooser.setDate(stringToDate(InventoryItem.purchaseDate));
        storeTextField.setText(InventoryItem.purchaseLocation);
        noteTextField.setText(InventoryItem.note);
        showPhoto(InventoryItem.photoFile);
        nextButton.setEnabled(true);
        previousButton.setEnabled(true);
        if (j == 1)
            previousButton.setEnabled(false);
        if (j == numberEntries)
            nextButton.setEnabled(false);
        itemTextField.requestFocus();
    }
    private Date stringToDate(String s)
    {
        int m = Integer.parseInt(s.substring(0, 2)) - 1;
        int d = Integer.parseInt(s.substring(3, 5));
        int y = Integer.parseInt(s.substring(6)) - 1900;
        return(new Date(y, m, d));
    }
    private String dateToString(Date dd)

    {
        String yString = String.valueOf(dd.getYear() + 1900);
        int m = dd.getMonth() + 1;
        String mString = new DecimalFormat("00").format(m);
        int d = dd.getDate();
        String dString = new DecimalFormat("00").format(d);
        return(mString + "/" + dString + "/" + yString);
    }
    private void showPhoto(String photoFile)
    {
        if (!photoFile.equals(""))
        {
            try
            {
                photoTextArea.setText(photoFile);
            }
            catch (Exception ex)
            {
                photoTextArea.setText("");
            }
        }
        else
        {
            photoTextArea.setText("");
        }
        photoPanel.repaint();
    }
    private void blankValues()
    {
// blank input screen
        newButton.setEnabled(false);
        deleteButton.setEnabled(false);
        saveButton.setEnabled(true);
        previousButton.setEnabled(false);
        nextButton.setEnabled(false);
        printButton.setEnabled(false);
        itemTextField.setText("");
        locationComboBox.setSelectedItem("");
        markedCheckBox.setSelected(false);
        serialTextField.setText("");
        priceTextField.setText("");
        dateDateChooser.setDate(new Date());
        storeTextField.setText("");
        noteTextField.setText("");
        photoTextArea.setText("");
        photoPanel.repaint();
        itemTextField.requestFocus();
    }
    private void deleteEntry(int j)
    {
// delete entry j
        if (j != numberEntries)
        {
// move all entries under j up one level
            for (int i = j; i < numberEntries; i++)
            {
                myInventory[i - 1] = new InventoryItem();
                myInventory[i - 1] = myInventory[i];
            }
        }
        numberEntries--;
    }
    private void checkSave()
    {
        boolean edited = false;
        if (!InventoryItem.description.equals(itemTextField.getText()))
            edited = true;
        else if (!InventoryItem.location.equals(Objects.requireNonNull(locationComboBox.getSelectedItem()).toString()))
            edited = true;
        else if (InventoryItem.marked != markedCheckBox.isSelected())
            edited = true;
        else if (!InventoryItem.serialNumber.equals(serialTextField.getText()))
            edited = true;
        else if (!InventoryItem.purchasePrice.equals(priceTextField.getText()))
            edited = true;
        else if (!InventoryItem.purchaseDate.equals(dateToString(dateDateChooser.getDate())))
            edited = true;
        else if (!InventoryItem.purchaseLocation.equals(storeTextField.getText()))
            edited = true;
        else if (!InventoryItem.note.equals(noteTextField.getText()))
            edited = true;
        else if (!InventoryItem.photoFile.equals(photoTextArea.getText()))
            edited = true;
        if (edited)
        {
            if (JOptionPane.showConfirmDialog(null, "You have edited this item. Do you want to "
                            + "save the changes?", "Save Item", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                saveButton.doClick();
        }
    }
}