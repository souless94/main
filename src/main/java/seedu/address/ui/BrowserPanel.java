package seedu.address.ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.common.base.Charsets;
import com.google.common.eventbus.Subscribe;
import com.google.common.io.Resources;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String ONLINE_PAGE_URL =
        "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    public static final String OFFLINE_PAGE_URL =
        "Timetable.html";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }


    /**
     * Loads the Timetable.html file with the timetable of the person selected.
     *
     * Does not require a internet connection.
     */
    private void loadOfflinePersonPage(Person person) {
        URL timetablePage = MainApp.class.getResource(FXML_FILE_FOLDER + OFFLINE_PAGE_URL);
        try {
            String location = Resources.toString(timetablePage, Charsets.UTF_8);
            Document document = Jsoup.parse(location, "UTF-8");
            Element element = document.getElementById("timetable");
            element.attr("value", person.getTimetable().getTimetableAsString());
            Platform.runLater(() -> browser.getEngine().loadContent(document.toString()));
        } catch (IOException e) {
            loadPage(timetablePage.toExternalForm());
            e.printStackTrace();
        }
    }

    /**
     * checks if internet connection is available
     */
    //@@author souless94 -reused
    //Solution below gotten from Marcus Junius Brutus
    // from https://stackoverflow.com/questions/1402005/how-
    // to-check-if-internet-connection-is-present-in-java
    private static boolean isInternetAvailable() {
        try {
            final URL url = new URL(ONLINE_PAGE_URL);
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }
    //@@ author

    /**
     * Loads the Timetable.html file with the timetable of the person selected.
     *
     * Loads from online first, then if there is no internet connect, load from offline
     */
    private void loadPersonPage(Person person) {
        if (isInternetAvailable()) {
            String timetableString = person.getTimetable().getTimetableAsString();
            URL defaultPage = MainApp.class.getResource(ONLINE_PAGE_URL);
            loadPage(ONLINE_PAGE_URL + timetableString);
        } else {
            loadOfflinePersonPage(person);
        }

    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection());
    }
}
