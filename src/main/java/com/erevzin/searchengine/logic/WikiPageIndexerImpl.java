package com.erevzin.searchengine.logic;

import com.erevzin.searchengine.model.WikiPage;
import org.springframework.stereotype.Service;

@Service
public class WikiPageIndexerImpl implements WikiPageIndexer {

    //public static Pattern docStart = Pattern.compile("\\{");
    //public static Pattern stringPattern = Pattern.compile("\"");

    public WikiPage index(String line){
        //split id from content
        if(line.indexOf("\":") == -1){
            return new WikiPage(); //TODO: change to read till the end of the document instead of line by line
        }
        String wikiPageId = line.substring(2,line.indexOf("\":"));
        String wikiPageContent =  line.substring(line.indexOf("\":")+4, line.length()-2); //instead of 4 - any number of spaces and then double quotes

        //tokenize content

        //index each token

        return new WikiPage(wikiPageId, wikiPageContent);
    }
}
