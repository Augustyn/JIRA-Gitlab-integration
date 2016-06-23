package pl.hycom.jira.plugins.gitlab.integration.ao;

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

import com.atlassian.activeobjects.external.ActiveObjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class GitlabComManDaoImpl implements GitlabComManDao
{
    @Autowired
    private ActiveObjects entityManager;

    public GitlabComEntity getProject(String projectName) {
        return entityManager.get(GitlabComEntity.class,projectName);    //returns null if no entities exist
    }

    public List<GitlabComEntity> getAllProjects() {
        ArrayList<GitlabComEntity> result = new ArrayList<GitlabComEntity>();
        GitlabComEntity projectList[] = entityManager.find(GitlabComEntity.class);
        for(GitlabComEntity proj : projectList){
            result.add(proj);
        }
        return result;
    }

    public boolean findProject(String insertedProject) {
        boolean equals = false;
        List<GitlabComEntity> result = this.getAllProjects();
        for(GitlabComEntity proj : result){
            if(insertedProject.equalsIgnoreCase(proj.toString())){
                equals = true;
            }
            else {
                equals = false;
            }
        }
        return equals;
    }
}
