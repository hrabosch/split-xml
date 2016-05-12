/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.split;

import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;

/**
 *
 * @author hrabosch
 */
public enum Messages {
    MISSING_CONFIGURATION("Missing configuration!", "Something is missing. Check if all informations are provided."),
    ROOT_ELEMENT_IS_NOT_SELECTED("Root element is missing!", "You have to select a root element from file structure."),
    ITEM_ELEMENT_IS_NOT_SELECTED("Item element is missing!", "You have to select a item element from file structure."),
    SPLIT_FINISHED("Complete!","File has been splited to %d files!");  
    
    private String title;
    private String description;

    private Messages(String errorTitle, String errorDesc) {
        this.title = errorTitle;
        this.description = errorDesc;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    
    public String formatMessage(Object... args) {
        return format(description, args);
    }
    
    
}
