package gui;

import filters.*;
import image.PixelImage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * This GUI class creates all the buttons and the image field and handles all the
 * relations and actions for the buttons.
 * @author Varun Parbhakar
 * @version 11-24-2021
 */
public class SnapShopGUI extends JFrame {

    // Declaring JButtons
    private JButton edgeDetectButton, edgeHighlightButton, flipHorizontalButton,
            flipVerticalButton, grayscaleButton, sharpenButton, softenButton,
            openButton, saveAsButton, closeImageButton;
    // Declaring JPanel
    private JPanel imageEffectPanel, imageDisplayPanel, imageUtilPanel;
    // Declaring String
    private String currentOpenDirectory = ".";
    // JFileChooser
    private JFileChooser fileChooser;
    // PixelImage
    private PixelImage myImage;
    // Filter
    private Filter myFilter;


    /**
     * Initializes the JFrame, along with JButtons and JPanels
     */
    SnapShopGUI() {
        //Setting the JPanel
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("TCSS 305 - Programming Assignment 3(1922396)");
        this.setLayout(new BorderLayout());
        this.setSize(695, 125);
        this.setVisible(true);

        //Initializing buttons
        edgeDetectButton = new JButton("Edge Detect");
        edgeHighlightButton = new JButton("Edge Highlight");
        flipHorizontalButton = new JButton("Flip Horizontal");
        flipVerticalButton = new JButton("Flip Vertical");
        grayscaleButton = new JButton("GrayScale");
        sharpenButton = new JButton("Sharpen");
        softenButton = new JButton("Soften");

        openButton = new JButton("Open...");
        openButton.setIcon(new ImageIcon("icons/open.gif"));
        saveAsButton = new JButton("Save As...");
        saveAsButton.setIcon(new ImageIcon("icons/save.gif"));
        closeImageButton = new JButton("Close Image");
        closeImageButton.setIcon(new ImageIcon("icons/close.gif"));

        imageEffectPanel = new JPanel();
        imageDisplayPanel = new JPanel();
        imageUtilPanel = new JPanel();

        //This call disables the buttons except the open
        setButtonPanelAccess(false);

    }

    /**
     * This method adds the buttons to the button effect panel, and it also prepares the
     * canvas for the initial launch.
     */
    public void start() {
        //Adding Filter Buttons
        JButton[] effectButtons = {edgeDetectButton, edgeHighlightButton, flipHorizontalButton, flipVerticalButton,
                grayscaleButton, sharpenButton, softenButton};
        for (JButton button : effectButtons) {
            imageEffectPanel.add(button);
        }

        clearCanvas();
        //Adding Utility Buttons
        imageUtilPanel.add(openButton);
        imageUtilPanel.add(saveAsButton);
        imageUtilPanel.add(closeImageButton);

        actionListenerSetup();

        //Adding Panels to the JFrame
        this.add(imageEffectPanel, BorderLayout.NORTH);
        this.add(imageDisplayPanel, BorderLayout.CENTER);
        this.add(imageUtilPanel, BorderLayout.SOUTH);

    }

    /**
     * Sets the all the button to the passed in values.
     *
     * @param value (Boolean; value which sets the state of the buttons)
     */
    private void setButtonPanelAccess(boolean value) {
        //Filters Buttons
        edgeDetectButton.setEnabled(value);
        edgeHighlightButton.setEnabled(value);
        flipHorizontalButton.setEnabled(value);
        flipVerticalButton.setEnabled(value);
        grayscaleButton.setEnabled(value);
        sharpenButton.setEnabled(value);
        softenButton.setEnabled(value);

        //Utility Buttons
        saveAsButton.setEnabled(value);
        closeImageButton.setEnabled(value);
    }

    /**
     * This method clears the canvas and removes any items from the imageDisplayPanel.
     */
    public void clearCanvas() {
        //Clears the panel
        imageDisplayPanel.removeAll();
    }

    /**
     * This method is solely responsible for setting clearing the canvas and painting a new
     * image.
     */
    public void setImage() {
        JLabel s = new JLabel();
        clearCanvas();
        s.setIcon((new ImageIcon(myImage)));
        imageDisplayPanel.add(s);
        this.pack();

    }

    /**
     * Sets up all the actionlisteners for every button in this class.
     */
    public void actionListenerSetup() {
        /*
         * These first two buttons are special button since they need to check for
         * file selection and other potential null exceptions.
         */
        openButton.addActionListener((e) -> {
            fileChooser = new JFileChooser(new File(currentOpenDirectory));
            fileChooser.showOpenDialog(null);
            currentOpenDirectory = fileChooser.getCurrentDirectory().toString();
            try {
                if (fileChooser.getSelectedFile() != null) {
                    myImage = PixelImage.load(fileChooser.getSelectedFile());
                    setImage();
                    setButtonPanelAccess(true);

                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "The file you have selected is not valid image",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Save Button
        saveAsButton.addActionListener((e) -> {
            fileChooser = new JFileChooser(new File(currentOpenDirectory));
            fileChooser.showSaveDialog(null);
            currentOpenDirectory = fileChooser.getCurrentDirectory().toString();
            try {
                if (fileChooser.getSelectedFile() != null) {
                    myImage.save(fileChooser.getSelectedFile());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //

        //Edge Highlight Button
        closeImageButton.addActionListener((e) -> {
            clearCanvas();
            this.pack();
            setButtonPanelAccess(false);
        });

        //Edge Highlight Button
        edgeHighlightButton.addActionListener((e) -> {
            myFilter = new EdgeHighlightFilter();
            myFilter.filter(myImage);
            setImage();
        });


        // Edge Detect Button
        edgeDetectButton.addActionListener((e) -> {
            myFilter = new EdgeDetectFilter();
            myFilter.filter(myImage);
            setImage();
        });

        // Flip Horizontal Button
        flipHorizontalButton.addActionListener((e) -> {
            myFilter = new FlipHorizontalFilter();
            myFilter.filter(myImage);
            setImage();
        });

        // Flip Vertical Button
        flipVerticalButton.addActionListener((e) -> {
            myFilter = new FlipVerticalFilter();
            myFilter.filter(myImage);
            setImage();
        });

        // Grayscale Button
        grayscaleButton.addActionListener((e) -> {
            myFilter = new GrayscaleFilter();
            myFilter.filter(myImage);
            setImage();
        });

        // Sharpen Button
        sharpenButton.addActionListener((e) -> {
            myFilter = new SharpenFilter();
            myFilter.filter(myImage);
            setImage();
        });

        // Soft Button
        softenButton.addActionListener((e) -> {
            myFilter = new SoftenFilter();
            myFilter.filter(myImage);
            setImage();
        });
    }

}
