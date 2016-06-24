package pl.hycom.jira.plugins.gitlab.integration.model;

/**
 * Created by anon on 19.04.2016.
 */

import lombok.Data;
import org.apache.lucene.document.Document;

@Data
public class Commit {
    private String id;
    private String short_id;
    private String title;
    private String author_name;
    private String author_email;
    private String created_at;
    private String message;

    private String issue;
    public Commit(Document doc){
        this.id = doc.get("id");
        //FIXME: pozostałe mapowanie
    }
}
