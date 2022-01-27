package pl.targosz.invoicing.config;

import java.net.URL;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import pl.targosz.invoicing.services.FileService;

public class FileConfiguration {

    public static final String INVOICES_DB_PATH = "invoices.json";

    public static final URL INVOICES_DB_URL = FileService.class.getResource("/invoices.json");

    public static final URL ID_DB_URL = FileService.class.getResource("/invoices.json");

    public static String ID_DB_PATH = "ids.json";

}
