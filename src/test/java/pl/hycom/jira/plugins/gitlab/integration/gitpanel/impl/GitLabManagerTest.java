package pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl;

/*
 * <p>Copyright (c) 2016, Authors
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

//TODO end tests

/**
 * Created by Damian Deska on 4/17/16.
 */
@Log4j
@RunWith(MockitoJUnitRunner.class)
public class GitLabManagerTest {

    @InjectMocks
    private GitLabManager gitLabManager;


    @Before
    public void setUp() {
    }

    @Test
    public void parseCommitData() {
        gitLabManager.parseCommitData();

    }

}
