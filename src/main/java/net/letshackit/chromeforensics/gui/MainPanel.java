/*
 * Copyright 2016 Animesh Shaw ( a.k.a. Psycho_Coder).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.letshackit.chromeforensics.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import net.letshackit.chromeforensics.core.ChromeForensics;
import net.letshackit.chromeforensics.core.Utils;
import net.letshackit.chromeforensics.core.export.ExportType;
import net.letshackit.chromeforensics.gui.tools.FileViewer;
import net.letshackit.chromeforensics.gui.tools.SQLiteDataBrowser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainPanel extends JPanel {

    private static final MainPanel mainPanel = new MainPanel();

    protected JToolBar toolBar;
    protected JButton autoLoadData;
    protected JButton manuallyLoadData;
    protected JButton exitButton;
    protected JButton exportTSV;
    protected JButton exportHTML;
    protected JButton helpButton;
    protected JButton aboutButton;
    protected JPanel visits;
    protected JPanel mostVisitedSites;
    protected JPanel urls;
    protected JPanel logins;
    protected JPanel downloads;
    protected JPanel keywords;
    protected JPanel bookmarks;
    protected JPanel cookies;
    protected JPanel preferences;
    protected JPanel favicons;
    protected JPanel customQuery;
    protected JTable visitsTable;
    protected JTabbedPane tabbedPane;

    final static Logger logger = LogManager.getLogger(MainPanel.class);

    protected ChromeForensics cf;

    private int WIDTH = 1000;
    private int HEIGHT = 600;

    private Map<Integer, JPanel> tabbedPanelDetails;

    /**
     * MainPanel constructor to initialize the components.
     */
    private MainPanel() {
        initComponents();
    }

    /**
     * Single instance of this MainPanel. The MainPanel uses the Singleton
     * pattern.
     *
     * @return returns a singleton instance of this Component
     */
    public static MainPanel getInstance() {
        return mainPanel;
    }

    /**
     * Function that initializes the components and other functionality.
     */
    private void initComponents() {
        /* Base Initialization */
        setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = screenSize.width;
        HEIGHT = screenSize.height - 40;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        cf = new ChromeForensics();

        /* Toolbar Code Started */
        initToolBar();
        add(toolBar, BorderLayout.NORTH);
        /*Toolbar Code ends*/

        /* JTabbedPane Code Started*/
        initTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        /* JTabbedPane Code Ended*/

        add(new StatusBar(), BorderLayout.SOUTH);
    }

    private void initToolBar() {
        toolBar = new JToolBar();
        toolBar.setOrientation(JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);
        toolBar.setPreferredSize(new Dimension(getWidth(), 40));

        manuallyLoadData = new JButton();
        manuallyLoadData.setIcon(Utils.createImageIcon("images/loaddata.png", "Load Data"));
        manuallyLoadData.setToolTipText("Manually locate the chrome data files folder.");
        toolBar.add(manuallyLoadData);

        autoLoadData = new JButton();
        autoLoadData.setIcon(Utils.createImageIcon("images/autosearch.png", "Auto Search and Load Data"));
        autoLoadData.setToolTipText("Automatically search and load chrome files.");
        toolBar.add(autoLoadData);

        toolBar.add(new JToolBar.Separator());

        exportTSV = new JButton("Export to");
        exportTSV.setIcon(Utils.createImageIcon("images/csv.png", "Export results to CSV"));
        exportTSV.setToolTipText("Export Results to CSV");
        exportTSV.setHorizontalTextPosition(SwingConstants.LEFT);
        exportTSV.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                ExportDialog export = new ExportDialog(ExportType.TSV);
                export.setVisible(true);
            }
        });
        toolBar.add(exportTSV);

        exportHTML = new JButton("Export to");
        exportHTML.setIcon(Utils.createImageIcon("images/html.png", "Export results to HTML"));
        exportHTML.setToolTipText("Export results to HTML.");
        exportHTML.setHorizontalTextPosition(SwingConstants.LEFT);
        toolBar.add(exportHTML);

        toolBar.add(new JToolBar.Separator());

        helpButton = new JButton();
        helpButton.setIcon(Utils.createImageIcon("images/help.png", "Need Help? Click Me!"));
        helpButton.setToolTipText("Need Help? Click Me!");
        toolBar.add(helpButton);

        aboutButton = new JButton();
        aboutButton.setIcon(Utils.createImageIcon("images/about.png", "About this tool!"));
        aboutButton.setToolTipText("About this tool!");
        toolBar.add(aboutButton);

        toolBar.add(new JToolBar.Separator());

        exitButton = new JButton();
        exitButton.setIcon(Utils.createImageIcon("images/exit.png", "Exit Application."));
        exitButton.setToolTipText("Exit Application");
        exitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                ChromeForensicsGui.getInstance().dispose();
            }
        });
        toolBar.add(exitButton);
    }

    private void initTabbedPane() {
        visits = new JPanel();
        mostVisitedSites = new JPanel();
        urls = new JPanel();
        logins = new JPanel();
        downloads = new JPanel();
        keywords = new JPanel();
        bookmarks = new JPanel();
        cookies = new JPanel();
        preferences = new JPanel();
        favicons = new JPanel();
        customQuery = new JPanel();

        SQLiteDataBrowser dbBrowser = new SQLiteDataBrowser();
        FileViewer fileViewer = FileViewer.getInstance();

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Visited Sites", visits);
        tabbedPane.addTab("Most Visited Sites", mostVisitedSites);
        tabbedPane.addTab("All Urls", urls);
        tabbedPane.addTab("Logins", logins);
        tabbedPane.addTab("Downloads", downloads);
        tabbedPane.addTab("Keyword Search Terms", keywords);
        tabbedPane.addTab("Bookmarks", bookmarks);
        tabbedPane.addTab("Cookies", cookies);
        tabbedPane.addTab("Preferences", preferences);
        tabbedPane.addTab("Favicons", favicons);
        tabbedPane.addTab("Custom SQL Query", customQuery);
        tabbedPane.addTab("SQLite Data Browser", dbBrowser);
        tabbedPane.addTab("File Viewer", fileViewer);

        tabbedPanelDetails = new HashMap<>();
        tabbedPanelDetails.put(0, visits);
        tabbedPanelDetails.put(1, mostVisitedSites);
        tabbedPanelDetails.put(2, urls);
        tabbedPanelDetails.put(3, logins);
        tabbedPanelDetails.put(4, downloads);
        tabbedPanelDetails.put(5, keywords);
        tabbedPanelDetails.put(6, bookmarks);
        tabbedPanelDetails.put(7, cookies);
        tabbedPanelDetails.put(8, preferences);
        tabbedPanelDetails.put(9, favicons);
        tabbedPanelDetails.put(10, customQuery);
        tabbedPanelDetails.put(11, dbBrowser);
        tabbedPanelDetails.put(12, fileViewer);
    }

    /**
     * Get the Width of the MainPanel.
     *
     * @return returns the height of the Component.
     */
    @Override
    public final int getWidth() {
        return WIDTH;
    }

    /**
     * Get the Height of the MainPanel.
     *
     * @return returns the height of the Component.
     */
    @Override
    public final int getHeight() {
        return HEIGHT;
    }

    /**
     * Returns the selected index of the tab.
     *
     * @return int
     */
    public int getSelectedTabIndex() {
        return tabbedPane.getSelectedIndex();
    }

    /**
     * Focuses the tab at index i
     *
     * @param i sets the current selected tab.
     */
    public void setSelectedTabIndex(int i) {
        tabbedPane.setSelectedIndex(i);
    }

    /**
     * @return
     */
    public Map<Integer, JPanel> getTabbedPaneComponentDetails() {
        return tabbedPanelDetails;
    }

    /**
     * Status Bar to be added to the Main Panel
     *
     * @author Psycho_Coder
     */
    final class StatusBar extends JPanel {

        public StatusBar() {
            setPreferredSize(new Dimension(getWidth(), 30));
            setBackground(Color.DARK_GRAY);
        }
    }
}
