package pl.hycom.jira.plugins.gitlab.integration.model;

/**
 * Created by anon on 19.04.2016.
 */

import lombok.Data;

@Data
public class Commit {
    private String id;
    private String short_id;
    private String title;
    private String author_name;
    private String author_email;
    private String created_at;
    private String message;
}

