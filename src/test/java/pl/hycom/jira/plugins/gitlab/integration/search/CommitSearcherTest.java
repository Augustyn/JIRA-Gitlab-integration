package pl.hycom.jira.plugins.gitlab.integration.search;

/*
 * <p>Copyright (c) 2016, Damian Deska
 * Project:  gitlab-integration.</p>
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p>
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */

import lombok.extern.log4j.Log4j;
import org.apache.velocity.runtime.directive.Parse;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by Damian Deska on 5/17/16.
 */

@Log4j
public class CommitSearcherTest {

    @InjectMocks
    private CommitSearcher commitSearcher = new CommitSearcher();

    @Test
    public void searchCommitsTest() throws ParseException, IOException{
        String fieldName = "author_name";
        String fieldValue = "kamilrogowski";
        commitSearcher.searchCommits(fieldName, fieldValue);
    }

}